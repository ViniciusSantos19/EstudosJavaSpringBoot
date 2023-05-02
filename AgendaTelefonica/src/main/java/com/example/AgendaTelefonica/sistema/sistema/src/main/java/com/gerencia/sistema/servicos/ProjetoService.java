package com.gerencia.sistema.servicos;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.gerencia.sistema.dtos.ProjetoDto;
import com.gerencia.sistema.entidades.Projeto;
import com.gerencia.sistema.repositorios.ProjetoRepository;

import jakarta.validation.Valid;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository repository;
	
	
	private List<ProjetoDto> converteListaProjeto(List<Projeto> lista){
		return lista.stream().map(a -> new ProjetoDto(a.getNome(), a.getCusto(), a.getCategoria())).collect(Collectors.toList());
	}
	
	public List<ProjetoDto> listarProjetos() {
		return this.converteListaProjeto(repository.findAll());
	}
	
	public ResponseEntity<ProjetoDto> cadastrar(@Valid ProjetoDto ProjetoDto, UriComponentsBuilder uriBuilder){
		Projeto projeto = new Projeto(ProjetoDto);
		URI uri = uriBuilder.path("/Projetos/{id}").buildAndExpand(projeto.getId()).toUri();
		repository.save(projeto);
		return ResponseEntity.created(uri).body(new ProjetoDto(projeto.getNome(), projeto.getCusto(), projeto.getCategoria()));
	}
	
	public ResponseEntity<ProjetoDto> atualizar(Long id, ProjetoDto projetoDto){
		Optional<Projeto> opEng = repository.findById(id);
		if(opEng.isPresent()) {
			Projeto projeto = opEng.get();
			projeto.setNome(projetoDto.nome());
			projeto.setCategoria(projetoDto.categoria());
			
			repository.save(projeto);
			return new ResponseEntity<ProjetoDto>(new ProjetoDto(projeto.getNome(), projeto.getCusto(), projeto.getCategoria()), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<ProjetoDto> deletar(Long id){
		Optional<Projeto> opEng = repository.findById(id);
		if(opEng.isPresent()) {
			Projeto projeto = opEng.get();
			ResponseEntity<ProjetoDto> ent =  new ResponseEntity<ProjetoDto>(new ProjetoDto(projeto.getNome(), projeto.getCusto(), projeto.getCategoria()), HttpStatus.OK);
			
			repository.delete(projeto);
			return ent;
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
