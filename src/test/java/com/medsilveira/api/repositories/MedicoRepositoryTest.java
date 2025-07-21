package com.medsilveira.api.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.medsilveira.api.dto.DadosEnderecoDTO;
import com.medsilveira.api.dto.medicos.MedicoCadastroDTO;
import com.medsilveira.api.dto.pacientes.PacienteCadastroDTO;
import com.medsilveira.api.entities.Consulta;
import com.medsilveira.api.entities.Medico;
import com.medsilveira.api.entities.Paciente;
import com.medsilveira.api.enums.Especialidade;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {

  @Autowired
  private MedicoRepository medicoRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  @DisplayName("""
      Scenario 1: Should return null if there are no doctors available for the given date
      """)
  void testFindRandomMedicoDateFreeScenario1() {
    // Implement test logic here

    var nextTuesday = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY))
        .withHour(10)
        .withMinute(0)
        .withSecond(0);

    var doctor = cadastrarMedico("Doctor", "doctor@silv.med", "123456", Especialidade.CARDIOLOGIA);
    var pacient = cadastrarPaciente("Pacient", "pacient@email.com", "00000000000");
    cadastrarConsulta(doctor, pacient, nextTuesday);

    var randomDoctor = medicoRepository.findRandomMedicoByEspecialidadeAndAvailable(Especialidade.CARDIOLOGIA,
        nextTuesday);
    assertThat(randomDoctor).isNull();
  }

  @Test
  @DisplayName("""
      Scenario 2: Should return the doctor if he is available for the given date
      """)
  void testFindRandomMedicoDateFreeScenario2() {

    // given
    var nextTuesday = LocalDate.now()
        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
        .atTime(10, 0);
    var doctor = cadastrarMedico("Doctor", "doctor@silv.med", "123456", Especialidade.CARDIOLOGIA);

    // when or act
    var freeDoctor = medicoRepository.findRandomMedicoByEspecialidadeAndAvailable(Especialidade.CARDIOLOGIA,
        nextTuesday);

    // then or assert
    assertThat(freeDoctor).isEqualTo(doctor);
  }

  private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
    entityManager.persist(new Consulta(null, medico, paciente, data));
  }

  private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
    var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
    entityManager.persist(medico);
    return medico;
  }

  private Paciente cadastrarPaciente(String nome, String email, String cpf) {
    var paciente = new Paciente(dadosPaciente(nome, email, cpf));
    entityManager.persist(paciente);
    return paciente;
  }

  private MedicoCadastroDTO dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
    return new MedicoCadastroDTO(
        nome,
        email,
        "2299999999",
        crm,
        especialidade,
        dadosEndereco());
  }

  private PacienteCadastroDTO dadosPaciente(String nome, String email, String cpf) {
    return new PacienteCadastroDTO(
        nome,
        email,
        "21229999999",
        cpf,
        dadosEndereco());
  }

  private DadosEnderecoDTO dadosEndereco() {
    return new DadosEnderecoDTO(
        "rua 123",
        "bairro 123",
        "00000000",
        "São Paulo",
        "SP",
        null,
        null);
  }
}
