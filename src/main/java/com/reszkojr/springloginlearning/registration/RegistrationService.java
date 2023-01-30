package com.reszkojr.springloginlearning.registration;

import com.reszkojr.springloginlearning.appuser.AppUser;
import com.reszkojr.springloginlearning.appuser.AppUserRole;
import com.reszkojr.springloginlearning.appuser.AppUserService;
import com.reszkojr.springloginlearning.registration.token.ConfirmationToken;
import com.reszkojr.springloginlearning.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator validator;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) {
        boolean isEmailValid = validator.test(request.getEmail());

        if (!isEmailValid) {
            throw new IllegalStateException();
        }

        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        // If token was already confirmed
        if (confirmationToken.getConfirmedAt() != null) throw new IllegalStateException("token expired");

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getUser().getEmail());

        return "confirmed";

    }
}
