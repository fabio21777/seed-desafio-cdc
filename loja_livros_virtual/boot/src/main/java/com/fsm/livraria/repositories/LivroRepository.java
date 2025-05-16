package com.fsm.livraria.repositories;

import com.fsm.exceptions.exception.NotFoundError;
import com.fsm.livraria.domain.Livro;
import com.fsm.livraria.dto.livro.LivroList;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface LivroRepository extends CrudRepository<Livro, Long> {

    boolean existsByTitulo(String titulo);

    boolean existsByTituloAndUuidNot(String titulo, UUID uuid);

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndUuidNot(String isbn, UUID uuid);

    @Query(value = "SELECT livro_.titulo as title, livro_.uuid as uuid FROM Livro livro_",
            countQuery = "SELECT COUNT(livro_) FROM Livro livro_")
    Page<LivroList> findAllLivros(Pageable pageable);

    @Join("autor")
    @Join("categoria")
    Optional<Livro> findByUuid(UUID uuid);

    default Livro findByUuidOrThrow(UUID uuid) {
        return findByUuid(uuid)
                .orElseThrow(() -> new NotFoundError("Livro não encontrado"));
    }

}
