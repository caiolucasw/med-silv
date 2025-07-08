package com.medsilveira.api.controllers;

import com.medsilveira.api.dto.pacientes.PacienteAtualizaDadosDTO;
import com.medsilveira.api.dto.pacientes.PacienteCadastroDTO;
import com.medsilveira.api.dto.pacientes.PacienteDetalheDTO;
import com.medsilveira.api.dto.pacientes.PacienteListItemDTO;
import com.medsilveira.api.repositories.PacienteRepository;
import com.medsilveira.api.entities.Paciente;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

  @Autowired
  private PacienteRepository repository;

  @GetMapping
  public ResponseEntity<Page<PacienteListItemDTO>> listar(@PageableDefault(size = 10) Pageable pagination) {
    Page<PacienteListItemDTO> pacientes = repository.findAllByAtivo(pagination, 1).map(PacienteListItemDTO::new);
    return ResponseEntity.ok(pacientes);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PacienteDetalheDTO> detalhar(@PathVariable Long id) {
    Paciente paciente = repository.getReferenceById(id);
    return ResponseEntity.ok(new PacienteDetalheDTO(paciente));
  }

  @PostMapping
  @Transactional
  public ResponseEntity<PacienteDetalheDTO> cadastrar(@RequestBody @Valid PacienteCadastroDTO dadosPaciente,
      UriComponentsBuilder uriBuilder) {
    Paciente paciente = new Paciente(dadosPaciente);
    repository.save(paciente);
    var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
    return ResponseEntity.created(uri).body(new PacienteDetalheDTO(paciente));
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<PacienteDetalheDTO> atualizar(@PathVariable Long id,
      @RequestBody PacienteAtualizaDadosDTO dadospaciente) {

    Paciente paciente = repository.getReferenceById(id);
    paciente.atualizarInformacoes(dadospaciente);
    return ResponseEntity.ok(new PacienteDetalheDTO(paciente));
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity remover(@PathVariable Long id) {
    repository.delete(id);
    return ResponseEntity.noContent().build();
  }
}
