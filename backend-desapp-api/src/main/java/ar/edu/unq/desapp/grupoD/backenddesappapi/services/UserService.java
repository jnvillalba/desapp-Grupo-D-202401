package ar.edu.unq.desapp.grupoD.backenddesappapi.services;

import ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions.OperationNotFoundException;
import ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions.UserNotFoundException;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.*;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ActiveDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.OperationReportDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.RequestReportDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.UserDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(UserDTO userDTO) {
        User user = userDTO.toModel();
        return userRepository.save(user);
    }

    public User getUser(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public OperationReportDTO generateReport(RequestReportDTO request) {
        List<Operation> operations = getOperationsForUserAndDateRange(request);

        List<ActiveDTO> activeDTOs = mapOperationsToActiveDTOs(operations, request.getDolarBlue());

        double totalPriceInPesosARG = calculateTotalPriceInPesos(activeDTOs);
        double totalValueInDollars = calculateTotalValueInDollars(activeDTOs);

        return new OperationReportDTO(LocalDateTime.now(), totalValueInDollars, totalPriceInPesosARG,activeDTOs);
    }

    private List<Operation> getOperationsForUserAndDateRange(RequestReportDTO request) {
        return userRepository.findOperationsByUserIdAndDateRange(
                request.getUserId(),
                request.getStartDate().atStartOfDay(),
                request.getEndDate().atStartOfDay());
    }

    private List<ActiveDTO> mapOperationsToActiveDTOs(List<Operation> operations, Float dolarBlue) {
        return operations.stream()
                .map(operation -> new ActiveDTO().toActiveDTO(operation.getCryptoActive(), dolarBlue))
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
