package com.example.AgendaTelefonica.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.AgendaTelefonica.DTOS.PessoaDTOS;
import com.example.AgendaTelefonica.DTOS.TelefoneDTO;
import com.example.AgendaTelefonica.entidades.Categoria;
import com.example.AgendaTelefonica.entidades.Pessoa;
import com.example.AgendaTelefonica.entidades.Telefone;
import com.example.AgendaTelefonica.repository.PessoaRespository;

import jakarta.validation.Valid;


@Service
public class PessoaService {

	@Autowired
	PessoaRespository repository;
	
	private List<PessoaDTOS> convertePessoas(List<Pessoa> lista){
		List<PessoaDTOS> pessoasDtoList = new ArrayList<PessoaDTOS>();
		for(Pessoa pessoa: lista) {
			List<TelefoneDTO> telefonesDTO = pessoa.getTelefones().
					stream().map(t -> new TelefoneDTO(
							t.getId(),
							pessoa.getId(),
							t.getNumero(),
							t.getCategoria().name()
							)).collect(Collectors.toList());
			
			PessoaDTOS pessoaDto = new PessoaDTOS(pessoa.getId(), 
					pessoa.getNome(),
					pessoa.getSobrenome(), 
					pessoa.getEmail(), 
					pessoa.getEndereco(), telefonesDTO);
			
			pessoasDtoList.add(pessoaDto);
			
		}
		return pessoasDtoList;
	}
	
	private List<TelefoneDTO> converteListaTelefone(List<Telefone> lista, Long id) {
		if(lista == null) {	
			return null;
		}
		return lista.stream().map(
				t -> new TelefoneDTO(
						t.getId(),
						id,
						t.getNumero(),
						t.getCategoria().name()
				)).collect(Collectors.toList());
		
	}
	
	public List<PessoaDTOS>getAllPessoas() {
		return this.convertePessoas(repository.findAll());
	}
	
	public PessoaDTOS getByTelefone(String telefone) {
		Optional<Pessoa> pessoaOptional = repository.findByTelefones(telefone);
		Pessoa pessoa = null;
		if(pessoaOptional.isPresent()) {
			pessoa = pessoaOptional.get();
			
			List<TelefoneDTO> telefonesDto = this.converteListaTelefone(pessoa.getTelefones(),
					pessoa.getId());
			
			PessoaDTOS pessoaDto = new PessoaDTOS(pessoa.getId(), 
					pessoa.getNome(),
					pessoa.getSobrenome(), 
					pessoa.getEmail(), 
					pessoa.getEndereco(), telefonesDto);
			
			return pessoaDto;
			
		}
		return null;
	}
	
	public PessoaDTOS getByNome(String nome) {
		Optional<Pessoa> pessoaOptional = repository.findByNome(nome);
		Pessoa pessoa = null;
		if(pessoaOptional.isPresent()) {
			pessoa = pessoaOptional.get();
			
			List<TelefoneDTO> telefonesDto = this.converteListaTelefone(pessoa.getTelefones(),
					pessoa.getId());
			
			PessoaDTOS pessoaDto = new PessoaDTOS(pessoa.getId(), 
					pessoa.getNome(),
					pessoa.getSobrenome(), 
					pessoa.getEmail(), 
					pessoa.getEndereco(), telefonesDto);
			
			return pessoaDto;
			
		}
		return null;
	}
	
	public ResponseEntity<PessoaDTOS> atualizar(Long id,@Valid  PessoaDTOS pessoaDto) {
		Optional<Pessoa> pessoaOptitional= repository.findById(id);
		Pessoa pessoa= null;
		if(pessoaOptitional.isPresent()) {
			
			pessoa = pessoaOptitional.get();
			pessoa.setEmail(pessoaDto.email());
			pessoa.setEndereco(pessoaDto.endereco());
			pessoa.setId(id);
			pessoa.setNome(pessoaDto.nome());
			pessoa.setSobrenome(pessoaDto.sobrenome());
			pessoa.setTelefones(
					this.converteListaTelefonesDTO(pessoaDto.telefones()));
			repository.save(pessoa);
			return new ResponseEntity<PessoaDTOS>(new PessoaDTOS(pessoa.getId(), 
					pessoa.getNome(),
					pessoa.getSobrenome(), 
					pessoa.getEmail(), 
					pessoa.getEndereco(), this.converteListaTelefone(pessoa.getTelefones(), pessoa.getId())), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<PessoaDTOS> deletar(Long id) {
		Optional<Pessoa> pessoaOptitional= repository.findById(id);
		Pessoa pessoa= null;
		if(pessoaOptitional.isPresent()) {
			
			pessoa = pessoaOptitional.get();
			ResponseEntity<PessoaDTOS>  ent = new ResponseEntity<PessoaDTOS>(new PessoaDTOS(pessoa.getId(), 
					pessoa.getNome(),
					pessoa.getSobrenome(), 
					pessoa.getEmail(), 
					pessoa.getEndereco(), this.converteListaTelefone(pessoa.getTelefones(), pessoa.getId())), HttpStatus.OK);
			repository.delete(pessoa);
			return ent;
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	private Pessoa getPessoa(PessoaDTOS pessoaDTO) {
		Pessoa pessoa = new Pessoa();
		pessoa.setEmail(pessoaDTO.email());
		pessoa.setEndereco(pessoaDTO.endereco());
		pessoa.setId(pessoaDTO.id());
		pessoa.setNome(pessoaDTO.nome());
		pessoa.setSobrenome(pessoaDTO.sobrenome());
		if(pessoaDTO.telefones() != null) {
			pessoa.setTelefones(this.converteListaTelefonesDTO(pessoaDTO.telefones()));
		}
		pessoa.setTelefones(null);
		return pessoa;
	}
	
	private List<Telefone> converteListaTelefonesDTO(List<TelefoneDTO> lista){
		Function<TelefoneDTO,Telefone> converte = dto -> {
			Telefone telefone = new Telefone();
			telefone.setCategoria(Categoria.valueOf(dto.categoria()));
			telefone.setId(dto.id());
			telefone.setNumero(dto.numero());
			Optional<Pessoa> dono = repository.findById(dto.idDono());
			telefone.setDono(dono.get());
			return telefone;
		};
		if(lista != null) {
			return lista.stream().map(converte).collect(Collectors.toList());
		}
		return null;
		
	}
	
	public ResponseEntity<PessoaDTOS> cadastrar(@RequestBody @Valid PessoaDTOS pessoaDto, UriComponentsBuilder uriBuilder) {
		Pessoa pessoa = this.getPessoa(pessoaDto);
		repository.save(pessoa);
		URI uri=uriBuilder.path("/pessoas/{id}").buildAndExpand(pessoa.getId()).toUri();
		return ResponseEntity.created(uri).body(new PessoaDTOS(
				pessoa.getId(), 
				pessoa.getNome(),
				pessoa.getSobrenome(), 
				pessoa.getEmail(), 
				pessoa.getEndereco(), 
				null
				));
	}
	
}
