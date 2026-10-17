package com.kelvinoliveira.sistemaagendamentoconsultas.exception;

import com.kelvinoliveira.sistemaagendamentoconsultas.exception.ConsultaInvalidaException;
import com.kelvinoliveira.sistemaagendamentoconsultas.exception.ConsultaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Tratador global de exceções do controller. Converte exceções de domínio e
 * validação em respostas HTTP padronizadas com corpo JSON.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConsultaNotFoundException.class)
    public ResponseEntity<Map<String, Object>> naoEncontrado(ConsultaNotFoundException ex) {
        return erro(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ConsultaInvalidaException.class)
    public ResponseEntity<Map<String, Object>> invalida(ConsultaInvalidaException ex) {
        return erro(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacaoFalhou(MethodArgumentNotValidException ex) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", LocalDateTime.now());
        corpo.put("status", HttpStatus.BAD_REQUEST.value());
        corpo.put("erro", "Erro de validação");
        corpo.put("detalhes", ex.getBindingResult().getFieldErrors().stream()
                .collect(java.util.stream.Collectors.toMap(
                        campo -> campo.getField(),
                        campo -> campo.getDefaultMessage(),
                        (a, b) -> b)));
        return ResponseEntity.badRequest().body(corpo);
    }

    private ResponseEntity<Map<String, Object>> erro(HttpStatus status, String mensagem) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        corpo.put("timestamp", LocalDateTime.now());
        corpo.put("status", status.value());
        corpo.put("erro", mensagem);
        return ResponseEntity.status(status).body(corpo);
    }
}
