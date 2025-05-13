package com.fsm.security;

import com.fsm.security.repositories.UserRoleJdbcRepository;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class AuthoritiesFetcher {
    private final UserRoleJdbcRepository userRoleJdbcRepository;

    public AuthoritiesFetcher(UserRoleJdbcRepository userRoleJdbcRepository) {
        this.userRoleJdbcRepository = userRoleJdbcRepository;
    }

    public List<String> findAuthoritiesByUsername(String username) {
        return userRoleJdbcRepository.findAllAuthoritiesByUsername(username);
    }
}
