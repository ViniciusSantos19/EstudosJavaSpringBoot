package com.example.AgendaTelefonica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AgendaTelefonica.entidades.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, Long>{
	
	public List<Telefone> findAll();
	
	public Optional<Telefone> findByDono(Long id);
	
}
