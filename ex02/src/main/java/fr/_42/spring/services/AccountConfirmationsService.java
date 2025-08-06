package fr._42.spring.services;

import fr._42.spring.models.AccountConfirmation;
import fr._42.spring.models.Confirmation;
import fr._42.spring.models.User;
import fr._42.spring.repositories.AccountConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@Service
public class AccountConfirmationsService {
    private final AccountConfirmationRepository accountConfirmationRepository;
    private final UsersService usersService;
    private final EmailService emailService;

    @Autowired
    public AccountConfirmationsService(
            AccountConfirmationRepository accountConfirmationRepository,
            UsersService usersService,
            EmailService emailService
    ) {
        this.accountConfirmationRepository = accountConfirmationRepository;
        this.usersService = usersService;
        this.emailService = emailService;
    }

    public AccountConfirmation createConfirmation(Long userId, String UserEmail) {
        AccountConfirmation confirmation = new AccountConfirmation();
        confirmation.setUserId(userId);
        confirmation.setConfirmationCode(UUID.randomUUID().toString());

        String confirmationUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/confirm/")
                .path(confirmation.getConfirmationCode())
                .toUriString();

        emailService.sendEmail(
                usersService.getUserById(userId).get().getEmail(),
                "Confirm your account",
                "Please confirm your account by clicking on the following link: " + confirmationUrl
        );
        return accountConfirmationRepository.save(confirmation);
    }

    public void confirmUser(String confirmationCode, User user) {
        User dbUser = usersService.getUserByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!usersService.validatePassword(user.getPassword(), dbUser.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (dbUser.getConfirmation() == Confirmation.CONFIRMED) {
            throw new IllegalArgumentException("User already confirmed");
        }
        AccountConfirmation accountConf = accountConfirmationRepository.findByUserId(dbUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Confirmation not found"));

        if (accountConf.getConfirmationCode().equals(confirmationCode)) {
            dbUser.setConfirmation(Confirmation.CONFIRMED);
            usersService.updateUser(dbUser.getId(), dbUser.getFirstName(), dbUser.getLastName(), dbUser.getEmail(), dbUser.getPhoneNumber(), dbUser.getRole());
            accountConfirmationRepository.delete(accountConf);
            return;
        }
        throw new IllegalArgumentException("Invalid confirmation code");
    }

    public void isConfirmationCodeValid(String confirmationCode) {
        accountConfirmationRepository.findByConfirmationCode(confirmationCode)
                .orElseThrow(() -> new IllegalArgumentException("Confirmation code is not valid"));
    }
}
