package ar.edu.unq.desapp.grupod.backenddesappapi.services;

import ar.edu.unq.desapp.grupod.backenddesappapi.exceptions.UserNotFoundException;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.*;
import ar.edu.unq.desapp.grupod.backenddesappapi.repositories.UserRepository;
import ar.edu.unq.desapp.grupod.backenddesappapi.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtTokenProvider jwtprovider;

    public User registerUser(UserDTO userDTO) {
        User user = userDTO.toModel();
        return userRepository.save(user);
    }

    public JwtDTO loginUser(LoginDTO loginUsuario) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginUsuario.getEmail());

        String jwt = jwtprovider.generateToken(userDetails);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        return new JwtDTO(jwt, userDetails.getUsername(), authorities);
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
