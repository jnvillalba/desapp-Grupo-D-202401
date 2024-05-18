package ar.edu.unq.desapp.grupoD.backenddesappapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ProcessTransactionDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.OperationRepository;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
class TransactionServiceTest {

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private TransactionService transactionService;
    private User user;
    private Operation operation;
    @BeforeEach
    void setUp() {
        user = new User();
        operation = new Operation();
        operation.setOperationId(1L);
        operation.setUser(user);
        operation.setStatus(Operation.TransactionStatus.PENDING);
        operation.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testProcessTransaction_Cancel() {
        // Arrange
        when(operationRepository.findById(1L)).thenReturn(Optional.of(operation));

        ProcessTransactionDTO dto = new ProcessTransactionDTO();
        dto.setOperationId(1L);
        dto.setProcessType(ProcessTransactionDTO.ProcessAccion.CANCEL);

        // Act
        Operation result = transactionService.processTransaction(dto);

        // Assert
        verify(operationRepository).save(operation);
        assertEquals(Operation.TransactionStatus.CANCELED_BY_USER, result.getStatus());
    }

    @Test
    void testProcessTransaction_Confirm() {
        // Arrange
        when(operationRepository.findById(1L)).thenReturn(Optional.of(operation));

        ProcessTransactionDTO dto = new ProcessTransactionDTO();
        dto.setOperationId(1L);
        dto.setProcessType(ProcessTransactionDTO.ProcessAccion.CONFIRM);

        // Act
        Operation result = transactionService.processTransaction(dto);

        // Assert
        verify(operationRepository).save(operation);
        assertEquals(Operation.TransactionStatus.CONFIRMED, result.getStatus());
    }


}
