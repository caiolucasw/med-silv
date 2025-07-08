package com.medsilveira.api.entities;

import com.medsilveira.api.dto.pacientes.PacienteAtualizaDadosDTO;
import com.medsilveira.api.dto.pacientes.PacienteCadastroDTO;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Paciente")
@Table(name = "paciente")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Paciente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;
  private String email;
  private String cpf;
  private String telefone;

  @Embedded
  private Endereco endereco;

  private Integer ativo;

  public Paciente(PacienteCadastroDTO dadosPaciente) {
    this.nome = dadosPaciente.nome();
    this.email = dadosPaciente.email();
    this.cpf = dadosPaciente.cpf();
    this.telefone = dadosPaciente.telefone();
    this.endereco = new Endereco(dadosPaciente.endereco());
    this.ativo = 1;
  }

  public void atualizarInformacoes(PacienteAtualizaDadosDTO dadosPaciente) {
    if (dadosPaciente.nome() != null) {
      this.nome = dadosPaciente.nome();
    }
    if (dadosPaciente.telefone() != null) {
      this.telefone = dadosPaciente.telefone();
    }
    if (dadosPaciente.endereco() != null) {
      this.endereco.atualizarInformacoes(dadosPaciente.endereco());
    }
  }

}
