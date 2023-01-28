package com.reszkojr.springloginlearning.registration;

import com.reszkojr.springloginlearning.appuser.AppUser;
import com.reszkojr.springloginlearning.appuser.AppUserRole;
import com.reszkojr.springloginlearning.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService service;
    private EmailValidator validator;

    public String register(RegistrationRequest request) {
        boolean isEmailValid = validator.test(request.getEmail());

        if (!isEmailValid) {
            throw new IllegalStateException();
        }
        return service.signUpUser(new AppUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), AppUserRole.USER));
    }
}
