package com.fsm.security.controller;

import com.fsm.security.PasswordEncoder;
import com.fsm.security.dto.UserDTO;
import com.fsm.security.entities.Role;
import com.fsm.security.entities.User;
import com.fsm.security.entities.UserRole;
import com.fsm.security.entities.UserRoleId;
import com.fsm.security.repositories.RoleJdbcRepository;
import com.fsm.security.repositories.UserJdbcRepository;
import com.fsm.security.repositories.UserRoleJdbcRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.micronaut.security.annotation.Secured;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

@Controller("/user")
@Secured("ROLE_ADMIN")
@ExecuteOn(TaskExecutors.BLOCKING)
public class UserCreateController {

    private static final Logger LOG = LoggerFactory.getLogger(UserCreateController.class);

    private final UserJdbcRepository userJdbcRepository;

    private final RoleJdbcRepository roleJdbcRepository;

    private final UserRoleJdbcRepository userRoleJdbcRepository;

    private final PasswordEncoder passwordEncoder;


    public UserCreateController(UserJdbcRepository userJdbcRepository, RoleJdbcRepository roleJdbcRepository, UserRoleJdbcRepository userRoleJdbcRepository, PasswordEncoder passwordEncoder) {
        this.userJdbcRepository = userJdbcRepository;
        this.roleJdbcRepository = roleJdbcRepository;
        this.userRoleJdbcRepository = userRoleJdbcRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Post
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<?> createUser(@Body @Valid UserDTO userRequest){
        LOG.info("Creating user {}", userRequest.username());
        User entity = userRequest.toEntity(passwordEncoder);
        User userSaved = userJdbcRepository.save(entity);
        LOG.info("User created {}", userSaved);

        LOG.info("Creating Roles");

        List<Role> roles = roleJdbcRepository.findAllAuthority(userRequest.roles());

        List<UserRole> userRoles = roles.stream().map(role -> {
            UserRoleId userRoleId = new UserRoleId(userSaved, role);
            return new UserRole(userRoleId);
        }).toList();
        userRoleJdbcRepository.saveAll(userRoles);
        URI locationOfNewuser = UriBuilder.of("/user")
                .path(userSaved.id().toString())
                .build();
        return HttpResponse.created(locationOfNewuser);
    }
}
