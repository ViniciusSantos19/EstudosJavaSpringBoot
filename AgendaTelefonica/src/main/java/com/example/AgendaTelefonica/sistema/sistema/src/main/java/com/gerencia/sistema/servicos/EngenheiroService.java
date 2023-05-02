package com.gerencia.sistema.servicos;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.gerencia.sistema.dtos.EngenheiroDto;
import com.gerencia.sistema.dtos.EngenheiroDtoSemCrea;
import com.gerencia.sistema.entidades.Engenheiro;
import com.gerencia.sistema.repositorios.EngenheiroRepository;

import jakarta.validation.Valid;

@Service
public class EngenheiroService {
	
	@Autowired
	private EngenheiroRepository repository;
	
	
	private List<EngenheiroDtoSemCrea> converteListaEngenheiro(List<Engenheiro> lista){
		return lista.stream().map(a -> new EngenheiroDtoSemCrea(a.getNome(),  a.getEspecialidade())).collect(Collectors.toList());
	}
	
	public List<EngenheiroDtoSemCrea> listarEngenheiros() {
		return this.converteListaEngenheiro(repository.findAll());
	}
	
	public ResponseEntity<EngenheiroDto> cadastrar(@Valid EngenheiroDto engenheiroDto, UriComponentsBuilder uriBuilder){
		Engenheiro engenheiro = new Engenheiro(engenheiroDto);
		URI uri = uriBuilder.path("/Engenheiros/{id}").buildAndExpand(engenheiro.getId()).toUri();
		repository.save(engenheiro);
		return ResponseEntity.created(uri).body(new EngenheiroDto(engenheiro.getNome(), engenheiro.getCREA(), engenheiro.getEspecialidade()));
	}
	
	public ResponseEntity<EngenheiroDto> atualizar(Long id, EngenheiroDto engehneiroDto){
		Optional<Engenheiro> opEng = repository.findById(id);
		if(opEng.isPresent()) {
			Engenheiro eng = opEng.get();
			eng.setNome(engehneiroDto.nome());
			eng.setEspecialidade(engehneiroDto.especialidade());
			repository.save(eng);
			return new ResponseEntity<EngenheiroDto>(new EngenheiroDto(eng.getNome(), eng.getCREA(), eng.getEspecialidade()), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<EngenheiroDto> deletar(Long id){
		Optional<Engenheiro> opEng = repository.findById(id);
		if(opEng.isPresent()) {
			Engenheiro eng = opEng.get();
			ResponseEntity<EngenheiroDto> ent =  new ResponseEntity<EngenheiroDto>(new EngenheiroDto(eng.getNome(), eng.getCREA(), eng.getEspecialidade()), HttpStatus.OK);
			
			repository.delete(eng);
			return ent;
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
