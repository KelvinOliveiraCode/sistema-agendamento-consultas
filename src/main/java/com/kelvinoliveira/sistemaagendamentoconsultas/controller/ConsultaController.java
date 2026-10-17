package com.kelvinoliveira.sistemaagendamentoconsultas.controller;

import com.kelvinoliveira.sistemaagendamentoconsultas.dto.ConsultaRequest;
import com.kelvinoliveira.sistemaagendamentoconsultas.dto.ConsultaResponse;
import com.kelvinoliveira.sistemaagendamentoconsultas.service.ConsultaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

/**
 * Controller REST que expõe as operações de agendamento, listagem e
 * cancelamento de consultas.
 */
@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    /**
     * Agenda uma nova consulta.
     */
    @PostMapping
    public ResponseEntity<ConsultaResponse> agendar(@Valid @RequestBody ConsultaRequest request) {
        ConsultaResponse resposta = service.agendar(request);
        return ResponseEntity.created(URI.create("/consultas/" + resposta.id())).body(resposta);
    }

    /**
     * Lista todas as consultas.
     */
    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    /**
     * Cancela uma consulta pelo identificador.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        service.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
