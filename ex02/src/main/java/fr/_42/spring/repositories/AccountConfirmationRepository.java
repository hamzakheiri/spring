package fr._42.spring.repositories;

import fr._42.spring.models.AccountConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountConfirmationRepository extends JpaRepository<AccountConfirmation, Long> {
    Optional<AccountConfirmation> findByUserId(Long aLong);

    Optional<AccountConfirmation> findByConfirmationCode(String confirmationCode);
}
