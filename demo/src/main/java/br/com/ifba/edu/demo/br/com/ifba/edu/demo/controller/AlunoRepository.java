package br.com.ifba.edu.demo.br.com.ifba.edu.demo.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifba.edu.demo.br.com.ifba.edu.demo.modelo.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long>{

	Aluno findById(long id);
	
}
