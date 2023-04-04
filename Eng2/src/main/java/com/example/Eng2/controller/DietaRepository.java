package com.example.Eng2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Eng2.entity.Dieta;

public interface DietaRepository extends JpaRepository<Dieta, Long>{
	public List<Dieta> findAll();
	
	public Optional<Dieta> findById(Long id);
}
