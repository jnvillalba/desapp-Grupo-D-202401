package ar.edu.unq.desapp.grupod.backenddesappapi.services;

import ar.edu.unq.desapp.grupod.backenddesappapi.exceptions.OperationNotFoundException;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.ProcessTransactionDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TransactionService {

    public final OperationRepository operationRepository;

    @Transactional
    public Operation processTransaction(ProcessTransactionDTO trx) {
        Operation operation = operationRepository.findById(trx.getOperationId())
                .orElseThrow(() -> new OperationNotFoundException(trx.getOperationId()));

        User user = operation.getUser();
        if (trx.getProcessType().equals(ProcessTransactionDTO.ProcessAccion.CANCEL)) {
            operation.setStatus(Operation.TransactionStatus.CANCELED_BY_USER);
        } else {
            operation.setStatus(Operation.TransactionStatus.CONFIRMED);
        }
        user.processTransaction(operation);
        operationRepository.save(operation);
        return operation;
    }
}
