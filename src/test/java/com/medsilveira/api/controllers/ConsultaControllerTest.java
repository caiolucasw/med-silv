package com.medsilveira.api.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.medsilveira.api.dto.consultas.AgendamentoConsultaDTO;
import com.medsilveira.api.dto.consultas.ConsultaDetalheDTO;
import com.medsilveira.api.enums.Especialidade;
import com.medsilveira.api.services.ConsultaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ConsultaService consultaService;

  @Autowired
  private JacksonTester<AgendamentoConsultaDTO> agendamentoConsultaDTOJson;
  @Autowired
  private JacksonTester<ConsultaDetalheDTO> consultaDetalheDTOJson;

  @Test
  @DisplayName("Should return code 400 if the data is invalid")
  @WithMockUser
  void agendar_shouldReturn400Code() throws Exception {
    // You can use MockMvc for HTTP request simulation

    var response = mockMvc.perform(MockMvcRequestBuilders.post("/consultas"))
        .andReturn().getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

  }

  @Test
  @DisplayName("Should return code 200 if the appointment is successfully scheduled")
  @WithMockUser
  void agendar_shouldReturnConsultaDetalheDTOAndCode200WithHttpRequest() throws Exception {

    LocalDateTime dataConsulta = LocalDateTime.now().plusDays(1);
    var appointmentRequestData = new AgendamentoConsultaDTO(1L, 1L, dataConsulta, Especialidade.CARDIOLOGIA);
    var appointmentDetails = new ConsultaDetalheDTO(null, 1L, 1L, dataConsulta);

    when(consultaService.agendar(appointmentRequestData)).thenReturn(appointmentDetails);

    // You can use MockMvc for HTTP request simulation
    var response = mockMvc
        .perform(
            MockMvcRequestBuilders.post("/consultas")
                .content(agendamentoConsultaDTOJson
                    .write(new AgendamentoConsultaDTO(1L, 1L, dataConsulta, Especialidade.CARDIOLOGIA)).getJson())
                .contentType(MediaType.APPLICATION_JSON))
        .andReturn().getResponse();

    var expectedResponse = consultaDetalheDTOJson.write(appointmentDetails).getJson();

    assertThat(response.getContentAsString()).isEqualTo(expectedResponse);

  }
}