package ar.edu.unq.desapp.grupoD.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupoD.backenddesappapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}