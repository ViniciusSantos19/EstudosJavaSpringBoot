package br.com.ifba.edu.demo.br.com.ifba.edu.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifba.edu.demo.br.com.ifba.edu.demo.modelo.Aluno;

@RestController
public class AlunoController {
	
	@Autowired
	private AlunoRepository repositorio;
	
	@RequestMapping("/posts")
	public List<Aluno> listar(){
		return repositorio.findAll();
	}
	
	@RequestMapping("/posts/{id}")
	public Aluno getAluno(@PathVariable(value="id") long id){
		return repositorio.findById(id);
	}
	
	
	
}
