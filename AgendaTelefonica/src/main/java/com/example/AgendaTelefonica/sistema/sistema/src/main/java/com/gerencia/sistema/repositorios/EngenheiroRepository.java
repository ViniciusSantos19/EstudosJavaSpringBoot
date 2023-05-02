package com.gerencia.sistema.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gerencia.sistema.entidades.Engenheiro;

public interface EngenheiroRepository extends JpaRepository<Engenheiro, Long>{
	
	
}
