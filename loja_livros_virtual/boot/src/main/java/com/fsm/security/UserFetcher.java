package com.fsm.security;

import com.fsm.security.UserState;
import com.fsm.security.repositories.UserJdbcRepository;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

@Singleton // <1>
class UserFetcher{

    private final UserJdbcRepository userJdbcRepository;

    UserFetcher(UserJdbcRepository userJdbcRepository) { // <2>
        this.userJdbcRepository = userJdbcRepository;
    }

    public Optional<UserState> findByUsername(@NotBlank @NonNull String username) {
        return userJdbcRepository.findByUsername(username).map(UserState.class::cast);
    }
}
