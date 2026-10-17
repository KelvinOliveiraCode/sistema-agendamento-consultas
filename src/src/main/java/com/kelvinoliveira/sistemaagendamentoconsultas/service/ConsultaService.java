package com.kelvinoliveira.sistemaagendamentoconsultas.service;

import com.kelvinoliveira.sistemaagendamentoconsultas.dto.ConsultaRequest;
import com.kelvinoliveira.sistemaagendamentoconsultas.dto.ConsultaResponse;
import com.kelvinoliveira.sistemaagendamentoconsultas.exception.ConsultaInvalidaException;
import com.kelvinoliveira.sistemaagendamentoconsultas.exception.ConsultaNotFoundException;
import com.kelvinoliveira.sistemaagendamentoconsultas.model.Consulta;
import com.kelvinoliveira.sistemaagendamentoconsultas.model.StatusConsulta;
import com.kelvinoliveira.sistemaagendamentoconsultas.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço de domínio para agendamento, listagem e cancelamento de consultas.
 */
@Service
@Transactional(readOnly = true)
public class ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    /**
     * Agenda uma nova consulta.
     *
     * @param request dados da consulta a agendar
     * @return resposta com os dados da consulta persistida
     * @throws ConsultaInvalidaException se a data/hora for no passado
     */
    @Transactional
    public ConsultaResponse agendar(ConsultaRequest request) {
        validar(request);
        if (request.dataHora().isBefore(LocalDateTime.now())) {
            throw new ConsultaInvalidaException("Não é possível agendar consulta em data/hora passada");
        }
        Consulta consulta = new Consulta(request.pacienteNome(), request.tipo(), request.dataHora());
        Consulta salva = repository.save(consulta);
        return paraResposta(salva);
    }

    private void validar(ConsultaRequest request) {
        if (request.pacienteNome() == null || request.pacienteNome().isBlank()) {
            throw new ConsultaInvalidaException("O nome do paciente é obrigatório");
        }
        if (request.tipo() == null || request.tipo().isBlank()) {
            throw new ConsultaInvalidaException("O tipo de consulta é obrigatório");
        }
        if (request.dataHora() == null) {
            throw new ConsultaInvalidaException("A data e hora da consulta são obrigatórias");
        }
    }

    /**
     * Lista todas as consultas cadastradas.
     *
     * @return lista de respostas de consulta (pode estar vazia)
     */
    public List<ConsultaResponse> listar() {
        return repository.findAll().stream()
                .map(this::paraResposta)
                .collect(Collectors.toList());
    }

    /**
     * Cancela uma consulta existente.
     *
     * @param id identificador da consulta
     * @return resposta da consulta cancelada
     * @throws ConsultaNotFoundException se a consulta não existir
     * @throws ConsultaInvalidaException se a consulta já estiver cancelada
     */
    @Transactional
    public ConsultaResponse cancelar(Long id) {
        Consulta consulta = repository.findById(id)
                .orElseThrow(() -> new ConsultaNotFoundException(id));
        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new ConsultaInvalidaException("Consulta já cancelada com id: " + id);
        }
        consulta.setStatus(StatusConsulta.CANCELADA);
        return paraResposta(consulta);
    }

    private ConsultaResponse paraResposta(Consulta consulta) {
        return new ConsultaResponse(
                consulta.getId(),
                consulta.getPacienteNome(),
                consulta.getTipo(),
                consulta.getDataHora(),
                consulta.getStatus().name(),
                consulta.getCriadoEm()
        );
    }
}
