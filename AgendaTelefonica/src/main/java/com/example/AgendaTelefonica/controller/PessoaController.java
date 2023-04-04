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

import com.example.AgendaTelefonica.DTOS.PessoaDTOS;
import com.example.AgendaTelefonica.services.PessoaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	PessoaService service;
	
	@GetMapping
	public List<PessoaDTOS> getAll(){
		return service.getAllPessoas();
	}
	
	/*@GetMapping("/{telefones}")
	public PessoaDTOS getByTelefone(@PathVariable(value="telefones")  String telefone) {
		return service.getByTelefone(telefone);
	}*/
	
	@GetMapping("/{nome}")
	public PessoaDTOS getByNome(@PathVariable(value="nome")  String nome) {
		return service.getByNome(nome);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PessoaDTOS> atualizar(@PathVariable Long id, @RequestBody @Valid PessoaDTOS pessoaDto){
		return service.atualizar(id,pessoaDto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<PessoaDTOS> apagar(@PathVariable(value="id")  Long id){
		return service.deletar(id);
	}
	
	@PostMapping
	public ResponseEntity<PessoaDTOS> cadastrar(@RequestBody @Valid PessoaDTOS pessoaDto, UriComponentsBuilder uriBuilder) {
		return service.cadastrar(pessoaDto, uriBuilder);
	}
	
}
