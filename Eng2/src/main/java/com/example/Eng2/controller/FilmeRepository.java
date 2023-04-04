package com.example.Eng2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Eng2.entity.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long>{
	
	public List<Filme> findAll();
	
	public Optional<Filme> findById(Long id);
	
}
