package ar.edu.unq.desapp.grupoD.backenddesappapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.unq.desapp.grupoD.backenddesappapi.exceptions.OperationNotFoundException;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoD.backenddesappapi.model.dto.ProcessTransactionDTO;
import ar.edu.unq.desapp.grupoD.backenddesappapi.repositories.OperationRepository;
import ar.edu.unq.desapp.grupoD.backenddesappapi.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
public class TransactionServiceTest {

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Operation pendingOperation;
    private ProcessTransactionDTO confirmTransactionDTO;
    private ProcessTransactionDTO cancelTransactionDTO;
    private User user;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        pendingOperation = new Operation();
        pendingOperation.setOperationId(1L);
        pendingOperation.setStatus(Operation.TransactionStatus.PENDING);
        pendingOperation.setUser(user);

        confirmTransactionDTO = new ProcessTransactionDTO();
        confirmTransactionDTO.setOperationId(1L);
        confirmTransactionDTO.setProcessType(ProcessTransactionDTO.ProcessAccion.CONFIRMAR_RECEPCION);
        pendingOperation.setUser(user);

        cancelTransactionDTO = new ProcessTransactionDTO();
        cancelTransactionDTO.setOperationId(1L);
        cancelTransactionDTO.setProcessType(ProcessTransactionDTO.ProcessAccion.CANCELAR);

        when(operationRepository.findById(1L)).thenReturn(Optional.of(pendingOperation));
    }

    @Test
    public void testConfirmTransaction() {
        Operation operation = transactionService.processTransaction(confirmTransactionDTO);

        assertNotNull(operation);
        assertEquals(Operation.TransactionStatus.CONFIRMED, operation.getStatus());
        verify(operationRepository, times(1)).save(operation);
    }

    @Test
    public void testCancelTransaction() {
        Operation operation = transactionService.processTransaction(cancelTransactionDTO);

        assertNotNull(operation);
        assertEquals(Operation.TransactionStatus.CANCELED_BY_USER, operation.getStatus());
        verify(operationRepository, times(1)).save(operation);
    }

    @Test
    public void testOperationNotFound() {
        when(operationRepository.findById(2L)).thenReturn(Optional.empty());

        ProcessTransactionDTO invalidTransactionDTO = new ProcessTransactionDTO();
        invalidTransactionDTO.setOperationId(2L);
        invalidTransactionDTO.setProcessType(ProcessTransactionDTO.ProcessAccion.CONFIRMAR_RECEPCION);

        assertThrows(OperationNotFoundException.class, () -> {
            transactionService.processTransaction(invalidTransactionDTO);
        });
    }
}
