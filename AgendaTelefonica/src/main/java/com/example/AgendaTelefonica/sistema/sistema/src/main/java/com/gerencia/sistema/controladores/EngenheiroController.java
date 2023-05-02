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

import com.gerencia.sistema.dtos.EngenheiroDto;
import com.gerencia.sistema.dtos.EngenheiroDtoSemCrea;
import com.gerencia.sistema.servicos.EngenheiroService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/Engenheiros")
public class EngenheiroController {
	
	@Autowired
	private EngenheiroService service;
	
	@GetMapping
	public List<EngenheiroDtoSemCrea> listartodos(){
		return service.listarEngenheiros();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<EngenheiroDto> atualizar(@PathVariable Long id, @RequestBody EngenheiroDto dto){
		return service.atualizar(id, dto);
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<EngenheiroDto> deletar(@PathVariable Long id){
		return service.deletar(id);
	}
	
	@PostMapping
	public ResponseEntity<EngenheiroDto> cadastrar(@RequestBody EngenheiroDto dto, UriComponentsBuilder uriBuilder){
		return service.cadastrar(dto, uriBuilder);
	}
	
}
