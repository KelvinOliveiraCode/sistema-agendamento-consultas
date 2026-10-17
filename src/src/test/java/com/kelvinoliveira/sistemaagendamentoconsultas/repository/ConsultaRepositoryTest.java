package com.kelvinoliveira.sistemaagendamentoconsultas.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kelvinoliveira.sistemaagendamentoconsultas.model.Consulta;
import com.kelvinoliveira.sistemaagendamentoconsultas.model.StatusConsulta;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

/**
 * Testa o reposit\u00f3rio JPA de consultas contra o banco embutido H2.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
class ConsultaRepositoryTest {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private Consulta consulta(String paciente, String tipo) {
        Consulta consulta = new Consulta(paciente, tipo, LocalDateTime.now().plusDays(2));
        return entityManager.persistAndFlush(consulta);
    }

    @Test
    void salvar_eBuscarPorId_deveRetornarConsulta() {
        Consulta salva = consulta("Maria Silva", "Clínica geral");
        Optional<Consulta> encontrada = repository.findById(salva.getId());
        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getPacienteNome()).isEqualTo("Maria Silva");
        assertThat(encontrada.get().getStatus()).isEqualTo(StatusConsulta.AGENDADA);
    }

    @Test
    void buscarPorId_inexistente_deveRetornarVazio() {
        assertThat(repository.findById(999L)).isEmpty();
    }

    @Test
    void buscarTodos_deveRetornarTodasAsConsultas() {
        consulta("Maria Silva", "Clínica geral");
        consulta("João Souza", "Retorno");
        List<Consulta> todas = repository.findAll();
        assertThat(todas).hasSize(2);
    }

    @Test
    void deletar_deveRemoverConsulta() {
        Consulta salva = consulta("Maria Silva", "Clínica geral");
        repository.delete(salva);
        assertThat(repository.findById(salva.getId())).isEmpty();
    }

    @Test
    void existePorId_deveRetornarCorretamente() {
        Consulta salva = consulta("Maria Silva", "Clínica geral");
        assertThat(repository.existsById(salva.getId())).isTrue();
        assertThat(repository.existsById(999L)).isFalse();
    }

    @Test
    void contar_deveRetornarQuantidadeDeConsultas() {
        consulta("Maria Silva", "Clínica geral");
        consulta("João Souza", "Retorno");
        consulta("Ana Costa", "Pediatria");
        assertThat(repository.count()).isEqualTo(3);
    }

    @Test
    void salvar_multiplas_eContar_deveSerConsistente() {
        long antes = repository.count();
        consulta("Maria Silva", "Clínica geral");
        consulta("João Souza", "Retorno");
        assertThat(repository.count()).isEqualTo(antes + 2);
    }
}
