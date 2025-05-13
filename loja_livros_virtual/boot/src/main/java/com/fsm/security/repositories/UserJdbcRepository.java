package com.fsm.security.repositories;

import com.fsm.security.dto.UserListDTO;
import com.fsm.security.entities.User;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES) // <1>
public interface UserJdbcRepository extends CrudRepository<User, Long> { // <2>

    Optional<User> findByUsername(@NonNull @NotBlank String username);


   @Query(
           value = """
                   select u.username username, string_agg(r.authority, ',' ) roles
                      from "user" u
                      join user_role  us on us.id_user_id = u.id
                      join role r on r.id = us.id_role_id
                      group by u.username
                   """
   )
    List<UserListDTO> findAllFetchRole();
}
