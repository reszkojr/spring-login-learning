package com.reszkojr.springloginlearning.registration.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;


@AllArgsConstructor
@Getter
@Setter

@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository repository;

    public void saveConfirmationToken(ConfirmationToken token) {
        repository.save(token);
    }
}
