package com.example.AgendaTelefonica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AgendaTelefonica.entidades.Pessoa;

public interface PessoaRespository extends JpaRepository<Pessoa, Long>{
	
public List<Pessoa> findAll();
	
	public Optional<Pessoa> findByNome(String nome);
	
	public Optional<Pessoa> findByTelefones(String telefone);
	
	public Optional<Pessoa> findById(Long id);
	
}
