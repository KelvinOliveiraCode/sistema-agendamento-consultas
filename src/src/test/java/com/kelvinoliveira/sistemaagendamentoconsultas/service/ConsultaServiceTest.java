package com.kelvinoliveira.sistemaagendamentoconsultas.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kelvinoliveira.sistemaagendamentoconsultas.dto.ConsultaRequest;
import com.kelvinoliveira.sistemaagendamentoconsultas.dto.ConsultaResponse;
import com.kelvinoliveira.sistemaagendamentoconsultas.exception.ConsultaInvalidaException;
import com.kelvinoliveira.sistemaagendamentoconsultas.exception.ConsultaNotFoundException;
import com.kelvinoliveira.sistemaagendamentoconsultas.model.StatusConsulta;
import com.kelvinoliveira.sistemaagendamentoconsultas.repository.ConsultaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

/**
 * Testa o servi\u00e7o de dom\u00ednio de consultas contra o banco embutido H2.
 */
@DataJpaTest
@Import(ConsultaService.class)
@Transactional
class ConsultaServiceTest {

    @Autowired
    private ConsultaService service;

    @Autowired
    private ConsultaRepository repository;

    private ConsultaRequest requestValida() {
        return new ConsultaRequest("Maria Silva", "Consulta clínica geral",
                LocalDateTime.now().plusDays(3));
    }

    @Test
    void agendar_comDadosValidos_deveSalvarERetornarResposta() {
        ConsultaRequest request = requestValida();
        ConsultaResponse resposta = service.agendar(request);

        assertThat(resposta.id()).isNotNull();
        assertThat(resposta.pacienteNome()).isEqualTo("Maria Silva");
        assertThat(resposta.tipo()).isEqualTo("Consulta clínica geral");
        assertThat(resposta.dataHora()).isEqualTo(request.dataHora());
        assertThat(resposta.status()).isEqualTo("AGENDADA");
        assertThat(resposta.criadoEm()).isNotNull();
    }

    @Test
    void agendar_comDadosValidos_deveDefinirStatusAgendada() {
        ConsultaResponse resposta = service.agendar(requestValida());
        assertThat(resposta.status()).isEqualTo(StatusConsulta.AGENDADA.name());
    }

    @Test
    void agendar_persisteConsultaNoBanco() {
        service.agendar(requestValida());
        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    void agendar_comDataPassada_deveLancarExcecao() {
        ConsultaRequest request = new ConsultaRequest("Maria Silva", "Consulta",
                LocalDateTime.now().minusDays(1));
        assertThatThrownBy(() -> service.agendar(request))
                .isInstanceOf(ConsultaInvalidaException.class)
                .hasMessageContaining("passada");
    }

    @Test
    void agendar_comPacienteNomeVazio_deveLancarExcecao() {
        ConsultaRequest request = new ConsultaRequest("", "Consulta",
                LocalDateTime.now().plusDays(1));
        assertThatThrownBy(() -> service.agendar(request))
                .isInstanceOf(ConsultaInvalidaException.class)
                .hasMessageContaining("nome do paciente");
    }

    @Test
    void agendar_comTipoVazio_deveLancarExcecao() {
        ConsultaRequest request = new ConsultaRequest("Maria Silva", "",
                LocalDateTime.now().plusDays(1));
        assertThatThrownBy(() -> service.agendar(request))
                .isInstanceOf(ConsultaInvalidaException.class)
                .hasMessageContaining("tipo de consulta");
    }

    @Test
    void listar_semConsultas_deveRetornarListaVazia() {
        List<ConsultaResponse> consultas = service.listar();
        assertThat(consultas).isEmpty();
    }

    @Test
    void listar_comDuasConsultas_deveRetornarTodas() {
        service.agendar(requestValida());
        service.agendar(new ConsultaRequest("João Souza", "Retorno",
                LocalDateTime.now().plusDays(4)));

        List<ConsultaResponse> consultas = service.listar();
        assertThat(consultas).hasSize(2);
    }

    @Test
    void cancelar_consultaExistente_deveAlterarStatusParaCancelada() {
        ConsultaResponse criada = service.agendar(requestValida());
        ConsultaResponse cancelada = service.cancelar(criada.id());

        assertThat(cancelada.id()).isEqualTo(criada.id());
        assertThat(cancelada.status()).isEqualTo(StatusConsulta.CANCELADA.name());
    }

    @Test
    void cancelar_consultaInexistente_deveLancarExcecao() {
        assertThatThrownBy(() -> service.cancelar(999L))
                .isInstanceOf(ConsultaNotFoundException.class);
    }

    @Test
    void cancelar_consultaJaCancelada_deveLancarExcecao() {
        ConsultaResponse criada = service.agendar(requestValida());
        service.cancelar(criada.id());
        assertThatThrownBy(() -> service.cancelar(criada.id()))
                .isInstanceOf(ConsultaInvalidaException.class)
                .hasMessageContaining("já cancelada");
    }
}
