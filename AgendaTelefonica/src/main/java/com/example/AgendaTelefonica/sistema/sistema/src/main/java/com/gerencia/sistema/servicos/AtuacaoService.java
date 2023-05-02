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

import com.gerencia.sistema.dtos.AtuacaoDto;
import com.gerencia.sistema.dtos.EngenheiroDto;
import com.gerencia.sistema.dtos.ProjetoDto;
import com.gerencia.sistema.entidades.Atuacao;
import com.gerencia.sistema.repositorios.AtuacaoRepository;

import jakarta.validation.Valid;

@Service
public class AtuacaoService {
	
	@Autowired
	private AtuacaoRepository repository;
	
	private AtuacaoDto converte(Atuacao atuacao) {
		
		EngenheiroDto engDto = new EngenheiroDto(atuacao.getEngenheiro().getNome(), atuacao.getEngenheiro().getCREA(), atuacao.getEngenheiro().getEspecialidade());
		ProjetoDto projDto =  new ProjetoDto(atuacao.getProjeto().getNome(), atuacao.getProjeto().getCusto(), atuacao.getProjeto().getCategoria());
		return   new AtuacaoDto(engDto, projDto, atuacao.getDuracao());
		
	}
	
	public ResponseEntity<AtuacaoDto> inserir (@Valid AtuacaoDto dto, UriComponentsBuilder uriBuilder){
		Atuacao atuacao = new Atuacao(dto);
		URI uri = uriBuilder.path("/Atuacoes/{id}").buildAndExpand(atuacao.getId()).toUri();
		
		
		repository.save(atuacao);
		
		return ResponseEntity.created(uri).body(this.converte(atuacao));
	}
	
	public ResponseEntity<AtuacaoDto> deletar(Long id){
		Optional<Atuacao> opAtuacao = repository.findById(id);
		if(opAtuacao.isPresent()) {
			Atuacao atuacao = opAtuacao.get();
			ResponseEntity<AtuacaoDto> ent =  new ResponseEntity<AtuacaoDto>(this.converte(atuacao), HttpStatus.OK);
			
			repository.delete(atuacao);
			return ent;
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	private List<AtuacaoDto> converteListaAtuacao  (List<Atuacao> lista){
		return lista.stream().map(a -> this.converte(a)).collect(Collectors.toList());
	}
	
	public List<AtuacaoDto> listar (){
		return this.converteListaAtuacao(repository.findAll());
	}
	
}
