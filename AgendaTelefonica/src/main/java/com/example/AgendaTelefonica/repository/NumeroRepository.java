package com.example.AgendaTelefonica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AgendaTelefonica.entidades.Numero;

public interface NumeroRepository extends JpaRepository<Numero, Long>{
	
	public List<Numero> findAll();
	
	public Optional<Numero> findByDono(Long id);
	
}
