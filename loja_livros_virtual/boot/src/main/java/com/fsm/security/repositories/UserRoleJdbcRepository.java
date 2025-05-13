package com.fsm.security.repositories;

import com.fsm.security.entities.Role;
import com.fsm.security.entities.User;
import com.fsm.security.entities.UserRole;
import com.fsm.security.entities.UserRoleId;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Set;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface UserRoleJdbcRepository extends CrudRepository<UserRole, UserRoleId> { // <2>

    @Query("""
    SELECT authority FROM role 
    INNER JOIN user_role ON user_role.id_role_id = role.id 
    INNER JOIN "user" user_ ON user_role.id_user_id = user_.id 
    WHERE user_.username = :username""")
    List<String> findAllAuthoritiesByUsername(@NotBlank String username);

    @Query("""
            SELECT * FROM user_role
            INNER JOIN role ON user_role.id_role_id = role.id
            WHERE authority IN (:roles)
            """)
    List<Role> findAllAuthority(@NotEmpty Set<String> roles);
}
