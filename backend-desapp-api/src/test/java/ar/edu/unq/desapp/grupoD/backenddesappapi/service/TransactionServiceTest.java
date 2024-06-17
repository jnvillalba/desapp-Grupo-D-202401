package ar.edu.unq.desapp.grupod.backenddesappapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.ProcessTransactionDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.repositories.OperationRepository;
import ar.edu.unq.desapp.grupod.backenddesappapi.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.cache.CacheManager;
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
    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void clearCache() {
        cacheManager.getCache("cryptoCache").clear();
    }

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
        when(operationRepository.findById(1L)).thenReturn(Optional.of(operation));

        ProcessTransactionDTO dto = new ProcessTransactionDTO();
        dto.setOperationId(1L);
        dto.setProcessType(ProcessTransactionDTO.ProcessAccion.CANCEL);

        Operation result = transactionService.processTransaction(dto);

        verify(operationRepository).save(operation);
        assertEquals(Operation.TransactionStatus.CANCELED_BY_USER, result.getStatus());
    }

    @Test
    void testProcessTransaction_Confirm() {
        when(operationRepository.findById(1L)).thenReturn(Optional.of(operation));

        ProcessTransactionDTO dto = new ProcessTransactionDTO();
        dto.setOperationId(1L);
        dto.setProcessType(ProcessTransactionDTO.ProcessAccion.CONFIRM);

        Operation result = transactionService.processTransaction(dto);

        verify(operationRepository).save(operation);
        assertEquals(Operation.TransactionStatus.CONFIRMED, result.getStatus());
    }
}
