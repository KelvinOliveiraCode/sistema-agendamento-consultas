package com.kelvinoliveira.sistemaagendamentoconsultas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kelvinoliveira.sistemaagendamentoconsultas.dto.ConsultaRequest;
import com.kelvinoliveira.sistemaagendamentoconsultas.dto.ConsultaResponse;
import com.kelvinoliveira.sistemaagendamentoconsultas.exception.ConsultaInvalidaException;
import com.kelvinoliveira.sistemaagendamentoconsultas.exception.ConsultaNotFoundException;
import com.kelvinoliveira.sistemaagendamentoconsultas.service.ConsultaService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Testa os endpoints REST do controller de consultas, com o servi\u00e7o
 * simulado (MockBean).
 */
@WebMvcTest(ConsultaController.class)
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @MockBean
    private ConsultaService service;

    private ConsultaRequest requestValida() {
        return new ConsultaRequest("Maria Silva", "Consulta clínica geral",
                LocalDateTime.now().plusDays(3));
    }

    @Test
    void agendar_comDadosValidos_deveRetornar201() throws Exception {
        ConsultaResponse resposta = new ConsultaResponse(1L, "Maria Silva",
                "Consulta clínica geral", requestValida().dataHora(), "AGENDADA",
                LocalDateTime.now());
        when(service.agendar(any(ConsultaRequest.class))).thenReturn(resposta);

        mockMvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValida())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pacienteNome").value("Maria Silva"))
                .andExpect(jsonPath("$.status").value("AGENDADA"));
    }

    @Test
    void agendar_comPacienteNomeVazio_deveRetornar400() throws Exception {
        ConsultaRequest request = new ConsultaRequest("", "Consulta",
                LocalDateTime.now().plusDays(1));
        mockMvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void agendar_comTipoVazio_deveRetornar400() throws Exception {
        ConsultaRequest request = new ConsultaRequest("Maria Silva", "",
                LocalDateTime.now().plusDays(1));
        mockMvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void agendar_comDataPassada_deveRetornar400() throws Exception {
        ConsultaRequest request = new ConsultaRequest("Maria Silva", "Consulta",
                LocalDateTime.now().minusDays(1));
        when(service.agendar(any(ConsultaRequest.class)))
                .thenThrow(new ConsultaInvalidaException("Não é possível agendar consulta em data/hora passada"));
        mockMvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listar_deveRetornar200ComLista() throws Exception {
        when(service.listar()).thenReturn(List.of(
                new ConsultaResponse(1L, "Maria Silva", "Consulta",
                        LocalDateTime.now().plusDays(1), "AGENDADA", LocalDateTime.now())));
        mockMvc.perform(get("/consultas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void listar_vazio_deveRetornarListaVazia() throws Exception {
        when(service.listar()).thenReturn(List.of());
        mockMvc.perform(get("/consultas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void cancelar_consultaExistente_deveRetornar204() throws Exception {
        when(service.cancelar(eq(1L))).thenReturn(new ConsultaResponse(1L, "Maria",
                "Consulta", LocalDateTime.now().plusDays(1), "CANCELADA", LocalDateTime.now()));
        mockMvc.perform(delete("/consultas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void cancelar_consultaInexistente_deveRetornar404() throws Exception {
        doThrow(new ConsultaNotFoundException(999L)).when(service).cancelar(eq(999L));
        mockMvc.perform(delete("/consultas/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cancelar_consultaJaCancelada_deveRetornar400() throws Exception {
        doThrow(new ConsultaInvalidaException("já cancelada")).when(service).cancelar(eq(1L));
        mockMvc.perform(delete("/consultas/1"))
                .andExpect(status().isBadRequest());
    }
}
