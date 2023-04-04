package com.example.Eng2.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.Eng2.controller.DietaRepository;
import com.example.Eng2.dtos.DietaDto;
import com.example.Eng2.entity.Dieta;

import jakarta.validation.Valid;

@Service
public class DietaService {
	
	@Autowired
	DietaRepository repository;
	
	private List<DietaDto> converteDieta(List<Dieta> lista){
		return lista.stream().map( dieta -> new DietaDto(
				dieta.getId(),
				dieta.getTitulo(),
				dieta.getCapa(),
				dieta.getCategoria()
				)).collect(Collectors.toList());
	}
	
	public List<DietaDto> getAll(){
		return converteDieta(repository.findAll());
	}
	
	public Dieta getDieta(DietaDto dietaDto) {
		Dieta dieta = new Dieta();
		dieta.setCapa(dietaDto.capa());
		dieta.setCategoria(dietaDto.categoria());
		dieta.setTitulo(dietaDto.titulo());
		//dieta.setId(dietaDto.id());
		return dieta;
	}
	
	public ResponseEntity<DietaDto> cadastrar(@RequestBody @Valid DietaDto dietaDto, UriComponentsBuilder uriBuilder) {
		Dieta dieta =this.getDieta(dietaDto);
		repository.save(dieta);
		URI uri=uriBuilder.path("/posts/{id}").buildAndExpand(dieta.getId()).toUri();
		return ResponseEntity.created(uri).body(new DietaDto(
				dieta.getId(),
				dieta.getTitulo(),
				dieta.getCapa(),
				dieta.getCategoria()
				));
	}
	
	public ResponseEntity<DietaDto> apagar(Long id) {
		Optional<Dieta> dietaOptitional= repository.findById(id);
		Dieta dieta=null;
		if(dietaOptitional.isPresent()) {
			dieta=dietaOptitional.get();
			ResponseEntity<DietaDto>  ent=new ResponseEntity<DietaDto>(new DietaDto(
					dieta.getId(),
					dieta.getTitulo(),
					dieta.getCapa(),
					dieta.getCategoria()
					), HttpStatus.OK);
			repository.delete(dieta);
			return ent;
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	public ResponseEntity<DietaDto> atualizar(Long id, @Valid DietaDto dietaDto) {
		Optional<Dieta> dietaOptitional= repository.findById(id);
		Dieta dieta=null;
		if(dietaOptitional.isPresent()) {
			dieta=dietaOptitional.get();
			dieta.setCapa(dietaDto.capa());
			dieta.setCategoria(dietaDto.categoria());
			dieta.setTitulo(dietaDto.titulo());
			repository.save(dieta);
			return new ResponseEntity<DietaDto>(new DietaDto(
					dieta.getId(),
					dieta.getTitulo(),
					dieta.getCapa(),
					dieta.getCategoria()
				), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
}
