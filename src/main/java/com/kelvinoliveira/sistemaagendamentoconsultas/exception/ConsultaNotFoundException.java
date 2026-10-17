package com.kelvinoliveira.sistemaagendamentoconsultas.exception;

/**
 * Lançada quando uma consulta não é encontrada pelo identificador informado.
 */
public class ConsultaNotFoundException extends RuntimeException {

    public ConsultaNotFoundException(Long id) {
        super("Consulta não encontrada com id: " + id);
    }
}
