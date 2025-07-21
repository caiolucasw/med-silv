// Trecho de código suprimido
package com.medsilveira.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medsilveira.api.dto.consultas.AgendamentoConsultaDTO;
import com.medsilveira.api.dto.consultas.CancelamentoConsultaDTO;
import com.medsilveira.api.dto.consultas.ConsultaDetalheDTO;
import com.medsilveira.api.services.ConsultaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("consultas")
public class ConsultaController {

  @Autowired
  private ConsultaService consultaService;

  @PostMapping
  @Transactional
  public ResponseEntity<ConsultaDetalheDTO> agendar(@RequestBody @Valid AgendamentoConsultaDTO dados) {
    var consulta = consultaService.agendar(dados);
    return ResponseEntity.ok(consulta);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity cancelar(@RequestBody @Valid CancelamentoConsultaDTO dados) {
    consultaService.cancelar(dados);
    return ResponseEntity.ok().build();
  }

}