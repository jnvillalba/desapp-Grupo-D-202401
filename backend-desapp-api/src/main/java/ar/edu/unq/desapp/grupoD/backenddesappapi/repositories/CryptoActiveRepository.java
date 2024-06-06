package ar.edu.unq.desapp.grupod.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.CryptoActive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoActiveRepository extends JpaRepository<CryptoActive, Long> {

    CryptoActive findBySymbol(String symbol);
}