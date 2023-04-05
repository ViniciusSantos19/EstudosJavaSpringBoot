package com.example.AgendaTelefonica.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
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
import com.example.AgendaTelefonica.repository.NumeroRepository;

import jakarta.validation.Valid;


@Service
public class AgendaService {

	@Autowired
	AgendaRespository repository;
	@Autowired
	NumeroRepository repositoryNumero;
	
	private Numero getNumero(NumeroDTO numeroDTO) {
		Numero numero = new Numero();
		numero.setCategoria(numeroDTO.categoria());
		numero.setId(numeroDTO.id());
		numero.setNumero(numeroDTO.numero());
		return numero;
		
	}
	
	private NumeroDTO cadastrarNumero(NumeroDTO numeroDto, Long id) {
		Optional<Agenda> AgendaOptional = repository.findById(id);
		Agenda agenda = null;
		if(AgendaOptional.isEmpty()) {
			return null;
		}
		agenda = AgendaOptional.get();
		Numero numero = this.getNumero(numeroDto);
		numero.setDono(agenda);
		repositoryNumero.save(numero);
		return new NumeroDTO(numero.getId(),
				numero.getNumero(),
				numero.getCategoria());
		
	}
	
	private List<AgendaDTO> converteAgendas(List<Agenda> lista){
		List<AgendaDTO> AgendasDtoList = new ArrayList<AgendaDTO>();
		for(Agenda Agenda: lista) {
			List<NumeroDTO> NumerosDTO = Agenda.getTelefones().
					stream().map(t -> new NumeroDTO(
							t.getId(),
							t.getNumero(),
							t.getCategoria()
							)).collect(Collectors.toList());
			
			AgendaDTO AgendaDto = new AgendaDTO(Agenda.getId(), 
					Agenda.getNome(),
					Agenda.getSobrenome(), 
					Agenda.getEmail(), 
					Agenda.getEndereco(), NumerosDTO);
			
			AgendasDtoList.add(AgendaDto);
			
		}
		return AgendasDtoList;
	}
	
	private List<NumeroDTO> converteListaNumero(List<Numero> lista) {
		if(lista == null) {	
			return null;
		}
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
		Agenda Agenda= null;
		if(AgendaOptitional.isPresent()) {
			
			Agenda = AgendaOptitional.get();
			Agenda.setEmail(AgendaDto.email());
			Agenda.setEndereco(AgendaDto.endereco());
			Agenda.setId(id);
			Agenda.setNome(AgendaDto.nome());
			Agenda.setSobrenome(AgendaDto.sobrenome());
			
			//List<NumeroDTO> listaDTO = AgendaDto.telefones().stream().map(n -> (NumeroDTO) cadastrarNumero(n,id)).collect(Collectors.toList());
			if(AgendaDto.telefones() != null) {	
				atualizarNumeros(AgendaDto.telefones(), Agenda);
			}
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
	
	private void atualizarNumeros(List<NumeroDTO> numerosDto, Agenda agenda) {
	    numerosDto.stream().forEach(numeroDto -> {
	        if (numeroDto.id() == null) {
	            Numero numero = new Numero();
	            numero.setCategoria(numeroDto.categoria());
	            numero.setDono(agenda);
	            numero.setNumero(numeroDto.numero());
	            repositoryNumero.save(numero);
	        } else {
	            repositoryNumero.findById(numeroDto.id()).ifPresent(numero -> {
	                numero.setNumero(numeroDto.numero());
	                numero.setCategoria(numeroDto.categoria());
	                repositoryNumero.save(numero);
	            });
	        }
	    });
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
			Agenda.getTelefones().stream().forEach(e -> repositoryNumero.deleteById(e.getId()));
			repository.delete(Agenda);
			return ent;
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	private Agenda getAgenda(AgendaDTO agendaDTO) {
		Agenda agenda = new Agenda();
		agenda.setEmail(agendaDTO.email());
		agenda.setEndereco(agendaDTO.endereco());
		//agenda.setId(agendaDTO.id());
		agenda.setNome(agendaDTO.nome());
		agenda.setSobrenome(agendaDTO.sobrenome());
		agenda.setTelefones(converteListaNumerosDTO(agendaDTO.telefones(), agenda));
		return agenda;
	}
	
	private List<Numero> converteListaNumerosDTO(List<NumeroDTO> lista, Agenda dono){
		Function<NumeroDTO,Numero> converte = dto -> {
			Numero Numero = new Numero();
			Numero.setCategoria(dto.categoria());
			Numero.setId(dto.id());
			Numero.setNumero(dto.numero());
			Numero.setDono(dono);
			return Numero;
		};
		if(lista != null) {
			return lista.stream().map(converte).collect(Collectors.toList());
		}
		return null;
		
	}
	
	public ResponseEntity<AgendaDTO> cadastrar(@RequestBody @Valid AgendaDTO agendaDto, UriComponentsBuilder uriBuilder) {
		Agenda agenda = this.getAgenda(agendaDto);
		repository.save(agenda);
		List<NumeroDTO> listaDTO = agendaDto.telefones().stream().map(n -> cadastrarNumero(n, agenda.getId())).collect(Collectors.toList());
		listaDTO.stream().forEach(e -> System.err.println(e.id()));
		URI uri=uriBuilder.path("/Agendas/{id}").buildAndExpand(agenda.getId()).toUri();
		return ResponseEntity.created(uri).body(new AgendaDTO(
				agenda.getId(), 
				agenda.getNome(),
				agenda.getSobrenome(), 
				agenda.getEmail(), 
				agenda.getEndereco(), 
				listaDTO
				));
	}
	
}
