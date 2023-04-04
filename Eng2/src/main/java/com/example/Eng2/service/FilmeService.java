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

import com.example.Eng2.controller.FilmeRepository;
import com.example.Eng2.dtos.FilmeDto;
import com.example.Eng2.entity.Filme;

import jakarta.validation.Valid;

@Service
public class FilmeService {
	
	@Autowired
	private FilmeRepository repository;
	
	public List<FilmeDto> getAll(){
		return converterFilmes(repository.findAll());
	}
	
	public FilmeDto getById(Long id){
		Optional<Filme> filmeOptitional= repository.findById(id);
		Filme filme = null;
		if(filmeOptitional.isPresent()) {
			filme = filmeOptitional.get();
			FilmeDto filmeDto = new FilmeDto(
					filme.getId(),
					filme.getNome(),
					filme.getSinopse(),
					filme.getImagem()
					);
			return filmeDto;
		}
		return null;
	}
	
	
	private List<FilmeDto> converterFilmes(List<Filme> lista){
		return lista.stream().map( filme -> new FilmeDto(
				filme.getId(),
				filme.getNome(),
				filme.getSinopse(),
				filme.getImagem()
				)).collect(Collectors.toList());
	}
	
	public Filme getFilme(FilmeDto filmeDto) {
		Filme filme = new Filme();
		filme.setImagem(filmeDto.imagen());
		filme.setNome(filmeDto.nome());
		filme.setSinopse(filmeDto.sinopse());
		//dieta.setId(dietaDto.id());
		return filme;
	}
	
	public ResponseEntity<FilmeDto> cadastrar(@RequestBody @Valid FilmeDto filmeDto, UriComponentsBuilder uriBuilder) {
		Filme filme = this.getFilme(filmeDto);
		repository.save(filme);
		URI uri=uriBuilder.path("/posts/{id}").buildAndExpand(filme.getId()).toUri();
		return ResponseEntity.created(uri).body(new FilmeDto(
				filme.getId(),
				filme.getNome(),
				filme.getSinopse(),
				filme.getImagem()
				));
	}
	
	public ResponseEntity<FilmeDto> apagar(Long id) {
		Optional<Filme> filmeOptitional= repository.findById(id);
		Filme filme = null;
		if(filmeOptitional.isPresent()) {
			filme = filmeOptitional.get();
			ResponseEntity<FilmeDto>  ent=new ResponseEntity<FilmeDto>(new FilmeDto(
					filme.getId(),
					filme.getNome(),
					filme.getSinopse(),
					filme.getImagem()
					), HttpStatus.OK);
			repository.delete(filme);
			return ent;
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	public ResponseEntity<FilmeDto> atualizar(Long id, @Valid FilmeDto filmeDto) {
		Optional<Filme> filmeOptitional= repository.findById(id);
		Filme filme= null;
		if(filmeOptitional.isPresent()) {
			filme = filmeOptitional.get();
			filme.setImagem(filmeDto.imagen());
			filme.setNome(filmeDto.nome());
			filme.setSinopse(filmeDto.sinopse());
			repository.save(filme);
			return new ResponseEntity<FilmeDto>(new FilmeDto(
					filme.getId(),
					filme.getNome(),
					filme.getSinopse(),
					filme.getImagem()
				), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
}
