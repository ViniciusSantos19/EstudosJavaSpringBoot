package com.gerencia.sistema.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gerencia.sistema.dtos.AtuacaoDto;
import com.gerencia.sistema.servicos.AtuacaoService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/Atuacoes")
public class AtuacaoController {
	
	@Autowired
	private AtuacaoService service;
	
	@PostMapping
	public ResponseEntity<AtuacaoDto> cadastrar(@RequestBody AtuacaoDto dto, UriComponentsBuilder uriBuilder){
		return service.inserir(dto, uriBuilder);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<AtuacaoDto> deleter(@PathVariable Long id){
		return service.deletar(id);
	}
	
	@GetMapping
	public List<AtuacaoDto> listar(){
		return service.listar();
	}
	
}
