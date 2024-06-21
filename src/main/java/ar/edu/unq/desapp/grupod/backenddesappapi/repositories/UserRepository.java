package ar.edu.unq.desapp.grupod.backenddesappapi.repositories;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.Operation;
import ar.edu.unq.desapp.grupod.backenddesappapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT o FROM User u JOIN u.operationsList o WHERE u.id = :userId AND o.createdAt BETWEEN :startDate AND :endDate")
    List<Operation> findOperationsByUserIdAndDateRange(@Param("userId") Long userId,
                                                       @Param("startDate") LocalDateTime startDate,
                                                       @Param("endDate") LocalDateTime endDate);

}