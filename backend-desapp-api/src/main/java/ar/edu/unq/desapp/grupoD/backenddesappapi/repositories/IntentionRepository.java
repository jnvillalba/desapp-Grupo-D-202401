package ar.edu.unq.desapp.grupoD.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Intention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntentionRepository extends JpaRepository<Intention, Long> {}