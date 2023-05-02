package com.gerencia.sistema.controladores;

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

import com.gerencia.sistema.dtos.ProjetoDto;
import com.gerencia.sistema.servicos.ProjetoService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/Projetos")
public class ProjetoController {
	

	@Autowired
	private ProjetoService service;
	
	@GetMapping
	public List<ProjetoDto> listartodos(){
		return service.listarProjetos();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProjetoDto> atualizar(@PathVariable Long id, @RequestBody ProjetoDto dto){
		return service.atualizar(id, dto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<ProjetoDto> deletar(@PathVariable Long id){
		return service.deletar(id);
	}
	
	@PostMapping
	public ResponseEntity<ProjetoDto> cadastrar(@RequestBody ProjetoDto dto, UriComponentsBuilder uriBuilder){
		return service.cadastrar(dto, uriBuilder);
	}
	
}
