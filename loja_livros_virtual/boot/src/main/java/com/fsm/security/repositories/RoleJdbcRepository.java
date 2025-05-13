package com.fsm.security.repositories;

import com.fsm.security.entities.Role;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface RoleJdbcRepository extends CrudRepository<Role, Long> {

    Role save(String authority);

    Optional<Role> findByAuthority(String authority);

    @Query("""
            SELECT * FROM role
            WHERE authority IN (:authority)
           """)
    List<Role> findAllAuthority(@NotEmpty Set<String> authority);

}
