package com.kelvinoliveira.sistemaagendamentoconsultas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO de entrada para agendamento de uma nova consulta.
 */
public record ConsultaRequest(
        @NotBlank(message = "O nome do paciente é obrigatório") String pacienteNome,
        @NotBlank(message = "O tipo de consulta é obrigatório") String tipo,
        @NotNull(message = "A data e hora da consulta são obrigatórias") LocalDateTime dataHora
) {
}
