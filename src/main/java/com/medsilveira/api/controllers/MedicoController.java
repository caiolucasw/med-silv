package com.medsilveira.api.controllers;

import com.medsilveira.api.dto.MedicoAtualizaDadosDTO;
import com.medsilveira.api.dto.MedicoCadastroDTO;
import com.medsilveira.api.dto.MedicoListItemDTO;
import com.medsilveira.api.repositories.MedicoRepository;
import com.medsilveira.api.entities.Medico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @GetMapping
    public Page<MedicoListItemDTO> listar(@PageableDefault(size = 10) Pageable pagination) {
       Page<Medico> medicos =  repository.findAllByAtivo(pagination, 1);
       return medicos.map(MedicoListItemDTO::new);
    }

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid MedicoCadastroDTO dadosMedico) {
        Medico medico = new Medico(dadosMedico);
        repository.save(medico);
    }

    @PutMapping("/{id}")
    @Transactional
    public void atualizar(@PathVariable Long id, @RequestBody MedicoAtualizaDadosDTO dadosMedico) {

        Medico medico = repository.getReferenceById(id);
        medico.atualizarInformacoes(dadosMedico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void remover(@PathVariable Long id) {
        repository.delete(id);
    }
}
