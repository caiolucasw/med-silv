package com.medsilveira.api.controllers;

import com.medsilveira.api.dto.medicos.MedicoAtualizaDadosDTO;
import com.medsilveira.api.dto.medicos.MedicoCadastroDTO;
import com.medsilveira.api.dto.medicos.MedicoDetalheDTO;
import com.medsilveira.api.dto.medicos.MedicoListItemDTO;
import com.medsilveira.api.repositories.MedicoRepository;
import com.medsilveira.api.entities.Medico;
import jakarta.validation.Valid;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @GetMapping
    public ResponseEntity<Page<MedicoListItemDTO>> listar(@PageableDefault(size = 10) Pageable pagination) {
        Page<MedicoListItemDTO> medicos = repository.findAllByAtivo(pagination, 1).map(MedicoListItemDTO::new);
        return ResponseEntity.ok(medicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicoDetalheDTO> detalhar(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new MedicoDetalheDTO(medico));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<MedicoDetalheDTO> cadastrar(@RequestBody @Valid MedicoCadastroDTO dadosMedico,
            UriComponentsBuilder uriBuilder) {
        Medico medico = new Medico(dadosMedico);
        repository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new MedicoDetalheDTO(medico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MedicoDetalheDTO> atualizar(@PathVariable Long id,
            @RequestBody MedicoAtualizaDadosDTO dadosMedico) {

        Medico medico = repository.getReferenceById(id);
        medico.atualizarInformacoes(dadosMedico);
        return ResponseEntity.ok(new MedicoDetalheDTO(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity remover(@PathVariable Long id) {
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
