package com.kelvinoliveira.sistemaagendamentoconsultas.exception;

/**
 * Lançada quando uma operação de consulta viola uma regra de negócio
 * (por exemplo, agendar no passado ou cancelar consulta já cancelada).
 */
public class ConsultaInvalidaException extends RuntimeException {

    public ConsultaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
