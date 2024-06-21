package ar.edu.unq.desapp.grupod.backenddesappapi.services;

import ar.edu.unq.desapp.grupod.backenddesappapi.exceptions.UserNotFoundException;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desapp.grupod.backenddesappapi.security.jwt.JWTTokenHelper;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private final CustomUserDetailsService customUserDetailsService;

    private final JWTTokenHelper jwtTokenHelper;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toModel();
        return userRepository.save(user);
    }

    public JwtDTO loginUser(LoginDTO loginUsuario) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginUsuario.getEmail());
        doAuthenticate(loginUsuario.getEmail(), loginUsuario.getPassword());
        String jwt = jwtTokenHelper.generateToken(loginUsuario.getEmail());
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return new JwtDTO(jwt, userDetails.getUsername(), authorities);
    }

    private void doAuthenticate(String emailId, String password) {

        log.info("\u001B[33m>>> Authentication of User Credentials\u001B[0m");

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(emailId, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid UserName and Password");
        }
    }


    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public OperationReportDTO generateReport(RequestReportDTO request) {
        List<Operation> operations = getOperationsForUserAndDateRange(request);

        List<ActiveDTO> activeDTOs = mapOperationsToActiveDTOs(operations, request.getDolarBlue());

        Double totalPriceInPesosARG = calculateTotalPriceInPesos(activeDTOs);
        Double totalValueInDollars = calculateTotalValueInDollars(activeDTOs);

        return new OperationReportDTO(LocalDateTime.now(), totalValueInDollars, totalPriceInPesosARG, activeDTOs);
    }

    private List<Operation> getOperationsForUserAndDateRange(RequestReportDTO request) {
        return userRepository.findOperationsByUserIdAndDateRange(
                request.getUserId(),
                request.getStartDate().atStartOfDay(),
                request.getEndDate().atStartOfDay());
    }

    private List<ActiveDTO> mapOperationsToActiveDTOs(List<Operation> operations, Float dolarBlue) {
        return operations.stream()
                .map(operation -> ActiveDTO.toActiveDTO(operation.getCryptoActive(), dolarBlue))
                .toList();
    }


    private Double calculateTotalPriceInPesos(List<ActiveDTO> activeDTOs) {
        return activeDTOs.stream()
                .mapToDouble(ActiveDTO::getPriceInPesosARG)
                .sum();
    }

    private Double calculateTotalValueInDollars(List<ActiveDTO> activeDTOs) {
        return activeDTOs.stream()
                .mapToDouble(ActiveDTO::getCurrentCryptoPrice)
                .sum();
    }

}
