package fr._42.spring.repositories;

import fr._42.spring.models.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HallsRepository extends JpaRepository<Hall, Long> {
    Optional<Hall> findBySerialNumber(String serialNumber);

    boolean existsBySerialNumber(String serialNumber);
}
