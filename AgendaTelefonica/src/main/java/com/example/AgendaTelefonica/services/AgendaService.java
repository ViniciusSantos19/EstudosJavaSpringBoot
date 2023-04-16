package com.example.AgendaTelefonica.services;

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

import com.example.AgendaTelefonica.DTOS.AgendaDTO;
import com.example.AgendaTelefonica.DTOS.NumeroDTO;
import com.example.AgendaTelefonica.entidades.Agenda;
import com.example.AgendaTelefonica.entidades.Numero;
import com.example.AgendaTelefonica.repository.AgendaRespository;

import jakarta.validation.Valid;


@Service
public class AgendaService {

	@Autowired
	AgendaRespository repository;

	private List<AgendaDTO> converteAgendas(List<Agenda> lista){
		return lista.stream().map(n -> new AgendaDTO(
				n.getId(),
				n.getNome(),
				n.getSobrenome(),
				n.getEmail(),
				n.getEndereco(),
				this.converteListaNumero(n.getTelefones())
		)).collect(Collectors.toList());
	}
	
	private List<NumeroDTO> converteListaNumero(List<Numero> lista) {
		return lista.stream().map(
				t -> new NumeroDTO(
						t.getId(),
						t.getNumero(),
						t.getCategoria()
				)).collect(Collectors.toList());
	}
	
	public List<AgendaDTO>getAllAgendas() {
		return this.converteAgendas(repository.findAll());
	}
	
	public AgendaDTO getById(Long id){
		Optional<Agenda> AgendaOptional = repository.findById(id);
		if(AgendaOptional.isPresent()){
			Agenda agenda = AgendaOptional.get();

			List<NumeroDTO> NumerosDto = this.converteListaNumero(agenda.getTelefones());

			AgendaDTO AgendaDto = new AgendaDTO(agenda.getId(),
					agenda.getNome(),
					agenda.getSobrenome(),
					agenda.getEmail(),
					agenda.getEndereco(), NumerosDto);

			return AgendaDto;

		}
		return null;
	}

	public AgendaDTO getByNome(String nome) {
		Optional<Agenda> AgendaOptional = repository.findByNome(nome);
		Agenda Agenda = null;
		if(AgendaOptional.isPresent()) {
			Agenda = AgendaOptional.get();
			
			List<NumeroDTO> NumerosDto = this.converteListaNumero(Agenda.getTelefones());
			
			AgendaDTO AgendaDto = new AgendaDTO(Agenda.getId(), 
					Agenda.getNome(),
					Agenda.getSobrenome(), 
					Agenda.getEmail(), 
					Agenda.getEndereco(), NumerosDto);
			
			return AgendaDto;
			
		}
		return null;
	}
	
	public ResponseEntity<AgendaDTO> atualizar(Long id,@Valid  AgendaDTO AgendaDto) {
		Optional<Agenda> AgendaOptitional= repository.findById(id);
		if(AgendaOptitional.isPresent()) {
			
			Agenda Agenda = new Agenda(AgendaDto);
			Agenda.setId(id);
			repository.save(Agenda);
			//listaDTO.stream().forEach(e -> System.err.println(e.id()));
			return new ResponseEntity<AgendaDTO>(new AgendaDTO(Agenda.getId(), 
					Agenda.getNome(),
					Agenda.getSobrenome(), 
					Agenda.getEmail(), 
					Agenda.getEndereco(), converteListaNumero(Agenda.getTelefones()) ), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<AgendaDTO> deletar(Long id) {
		Optional<Agenda> AgendaOptitional= repository.findById(id);
		Agenda Agenda= null;
		if(AgendaOptitional.isPresent()) {
			
			Agenda = AgendaOptitional.get();
			ResponseEntity<AgendaDTO>  ent = new ResponseEntity<AgendaDTO>(new AgendaDTO(Agenda.getId(), 
					Agenda.getNome(),
					Agenda.getSobrenome(), 
					Agenda.getEmail(), 
					Agenda.getEndereco(), this.converteListaNumero(Agenda.getTelefones() )), HttpStatus.OK);
			repository.delete(Agenda);
			return ent;
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<AgendaDTO> cadastrar(@RequestBody @Valid AgendaDTO agendaDto, UriComponentsBuilder uriBuilder) {
		Agenda agenda = new Agenda(agendaDto);
		repository.save(agenda);
		URI uri=uriBuilder.path("/Agendas/{id}").buildAndExpand(agenda.getId()).toUri();
		return ResponseEntity.created(uri).body(new AgendaDTO(
				agenda.getId(), 
				agenda.getNome(),
				agenda.getSobrenome(), 
				agenda.getEmail(), 
				agenda.getEndereco(), 
				this.converteListaNumero(agenda.getTelefones())
				));
	}
	
}
