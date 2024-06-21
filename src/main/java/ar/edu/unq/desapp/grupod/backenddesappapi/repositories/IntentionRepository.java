package ar.edu.unq.desapp.grupod.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.Intention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntentionRepository extends JpaRepository<Intention, Long> {
}