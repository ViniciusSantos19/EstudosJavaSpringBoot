package com.example.AgendaTelefonica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AgendaTelefonica.entidades.Agenda;

public interface AgendaRespository extends JpaRepository<Agenda, Long>{
	
public List<Agenda> findAll();
	
	public Optional<Agenda> findByNome(String nome);
		
	public Optional<Agenda> findById(Long id);
	
}
