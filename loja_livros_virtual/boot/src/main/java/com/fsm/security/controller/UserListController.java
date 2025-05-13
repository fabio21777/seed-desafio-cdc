package com.fsm.security.controller;

import com.fsm.security.dto.UserDTO;
import com.fsm.security.dto.UserListDTO;
import com.fsm.security.entities.User;
import com.fsm.security.entities.UserRole;
import com.fsm.security.repositories.UserJdbcRepository;
import com.fsm.security.repositories.UserRoleJdbcRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/user")
@Secured("ROLE_ADMIN")
@ExecuteOn(TaskExecutors.BLOCKING)
public class UserListController {

    private final UserJdbcRepository userJdbcRepository;

    private final UserRoleJdbcRepository userRoleJdbcRepository;

    public UserListController(UserJdbcRepository userJdbcRepository, UserRoleJdbcRepository userRoleJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
        this.userRoleJdbcRepository = userRoleJdbcRepository;
    }

    @Get
    public List<UserListDTO> listUsers() {
        //find all users
        return userJdbcRepository.findAllFetchRole();

    }
}
