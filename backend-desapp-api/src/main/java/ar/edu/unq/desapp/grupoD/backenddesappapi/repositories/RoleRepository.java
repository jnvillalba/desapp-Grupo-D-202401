package ar.edu.unq.desapp.grupoD.backenddesappapi.repositories;

import java.util.Optional;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
