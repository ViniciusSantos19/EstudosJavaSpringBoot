package com.example.AgendaTelefonica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.AgendaTelefonica.DTOS.AgendaDTO;
import com.example.AgendaTelefonica.services.AgendaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/Agendas")
public class AgendaController {

	@Autowired
	AgendaService service;
	
	@GetMapping
	public List<AgendaDTO> getAll(){
		return service.getAllAgendas();
	}

	@GetMapping("/nome/{nome}")
	public AgendaDTO getByNome(@PathVariable(value="nome")  String nome) {
		return service.getByNome(nome);
	}

	@GetMapping("/id/{id}")
	public AgendaDTO getById(@PathVariable(value="id")  Long id) {
		return service.getById(id);
	}
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<AgendaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AgendaDTO AgendaDto){
		return service.atualizar(id,AgendaDto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<AgendaDTO> apagar(@PathVariable(value="id")  Long id){
		return service.deletar(id);
	}
	
	@PostMapping
	public ResponseEntity<AgendaDTO> cadastrar(@RequestBody @Valid AgendaDTO AgendaDto, UriComponentsBuilder uriBuilder) {
		return service.cadastrar(AgendaDto, uriBuilder);
	}
	
}
