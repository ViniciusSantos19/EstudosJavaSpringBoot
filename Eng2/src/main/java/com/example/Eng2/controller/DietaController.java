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

import com.example.Eng2.dtos.DietaDto;
import com.example.Eng2.service.DietaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/dietas")
public class DietaController {
	
	@Autowired
	DietaService service;
	
	@GetMapping
	public List<DietaDto> getAll(){
		return service.getAll();
	}
	
	@PostMapping
	public ResponseEntity<DietaDto> cadastrar(@RequestBody @Valid DietaDto postDto, UriComponentsBuilder uriBuilder) {
		return service.cadastrar(postDto, uriBuilder);
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<DietaDto> atualizar(@PathVariable Long id, @RequestBody @Valid DietaDto dietaDto){
		return service.atualizar(id,dietaDto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<DietaDto> apagar(@PathVariable Long id){
		return service.apagar(id);
	}
	
}
