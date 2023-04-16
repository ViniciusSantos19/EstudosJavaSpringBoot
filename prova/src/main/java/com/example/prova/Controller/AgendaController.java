package com.example.prova.Controller;

import com.example.prova.DTOS.AgendaDTO;
import com.example.prova.Service.AgendaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/Agendas")
public class AgendaController {
    @Autowired
    private AgendaService service;

    @GetMapping
    public List<AgendaDTO> findAll(){
        return service.getAll();
    }

    @GetMapping("nome/{nome}")
    public AgendaDTO findByNome(@PathVariable(value="nome") String nome){
        return service.findByNome(nome);
    }

    @GetMapping("id/{id}")
    public AgendaDTO findById(@PathVariable(value="id") Long id){
        return service.findById(id);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AgendaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AgendaDTO agendaDto){
        return service.updateAgendas(agendaDto, id);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<AgendaDTO> apagar(@PathVariable(value="id")  Long id){
        return service.deletar(id);
    }

    @PostMapping
    public ResponseEntity<AgendaDTO> cadastrar(@RequestBody @Valid AgendaDTO AgendaDto, UriComponentsBuilder uriBuilder) {
        return service.insertIntoAgenda(AgendaDto, uriBuilder);
    }

}
