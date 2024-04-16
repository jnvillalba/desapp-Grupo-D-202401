package ar.edu.unq.desapp.grupoD.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {}