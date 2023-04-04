package com.example.Eng2.controller;

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

import com.example.Eng2.dtos.FilmeDto;
import com.example.Eng2.service.FilmeService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/filmes")
public class FilmeController {
	
	@Autowired
	FilmeService service;
	
	@GetMapping
	public List<FilmeDto> getAll(){
		return service.getAll();
	}
	
	@PostMapping
	public ResponseEntity<FilmeDto> cadastrar(@RequestBody @Valid FilmeDto filmeDto, UriComponentsBuilder uriBuilder) {
		return service.cadastrar(filmeDto, uriBuilder);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<FilmeDto> atualizar(@PathVariable Long id, @RequestBody @Valid FilmeDto filmeDto){
		return service.atualizar(id,filmeDto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<FilmeDto> apagar(@PathVariable(value="id")  Long id){
		return service.apagar(id);
	}
	@GetMapping("/{id}")
	public FilmeDto getById(@PathVariable(value="id")  Long id) {
		return service.getById(id);
	}
	
}
