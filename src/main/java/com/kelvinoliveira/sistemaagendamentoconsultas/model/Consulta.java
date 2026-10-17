package com.kelvinoliveira.sistemaagendamentoconsultas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Representa uma consulta médica agendada no sistema.
 * <p>
 * Entidade de domínio persistida em banco relacional. Uma consulta possui
 * identificador, nome do paciente, tipo, data/hora e status. O ciclo de vida
 * é: AGENDADA → CANCELADA (via cancelamento).
 */
@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do paciente é obrigatório")
    @Column(name = "paciente_nome", nullable = false, length = 200)
    private String pacienteNome;

    @NotBlank(message = "O tipo de consulta é obrigatório")
    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @NotNull(message = "A data e hora da consulta são obrigatórias")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @NotNull(message = "O status da consulta é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusConsulta status;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    protected Consulta() {
        this.status = StatusConsulta.AGENDADA;
    }

    public Consulta(String pacienteNome, String tipo, LocalDateTime dataHora) {
        this.pacienteNome = pacienteNome;
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.status = StatusConsulta.AGENDADA;
    }

    @PrePersist
    void aoPersistir() {
        if (criadoEm == null) {
            criadoEm = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
