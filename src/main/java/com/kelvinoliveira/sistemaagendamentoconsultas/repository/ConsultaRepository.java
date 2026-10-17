package com.kelvinoliveira.sistemaagendamentoconsultas.repository;

import com.kelvinoliveira.sistemaagendamentoconsultas.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório JPA de consultas. Fornece operações de CRUD padrão sobre a
 * entidade {@link Consulta}.
 */
@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
}
