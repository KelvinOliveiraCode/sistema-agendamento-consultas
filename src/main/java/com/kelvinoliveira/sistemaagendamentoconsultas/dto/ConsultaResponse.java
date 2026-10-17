package com.kelvinoliveira.sistemaagendamentoconsultas.dto;

import java.time.LocalDateTime;

/**
 * DTO de saída que representa uma consulta já persistida.
 */
public record ConsultaResponse(
        Long id,
        String pacienteNome,
        String tipo,
        LocalDateTime dataHora,
        String status,
        LocalDateTime criadoEm
) {
}
