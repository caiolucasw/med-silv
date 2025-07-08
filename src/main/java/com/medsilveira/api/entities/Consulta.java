package com.medsilveira.api.entities;

import java.time.LocalDateTime;

import com.medsilveira.api.dto.consultas.CancelamentoConsultaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Consulta")
@Table(name = "consulta")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "medico_id", nullable = false)
  private Medico medico;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "paciente_id", nullable = false)
  private Paciente paciente;

  @Column(name = "data", nullable = false)
  private LocalDateTime dataConsulta;

  private Integer status;

  @Column(name = "motivo_cancelamento")
  private String motivoCancelamento;

  @Column(name = "data_cancelamento")
  private LocalDateTime dataCancelamento;

  public Consulta(Long id, Medico medico, Paciente paciente, LocalDateTime dataConsulta) {
    this.id = id;
    this.medico = medico;
    this.paciente = paciente;
    this.dataConsulta = dataConsulta;
    this.status = 1; // 1 para agendada
  }

  public void cancelar(CancelamentoConsultaDTO dados) {
    this.status = 0;
    this.motivoCancelamento = dados.motivo();
    this.dataCancelamento = LocalDateTime.now();
  }
}
