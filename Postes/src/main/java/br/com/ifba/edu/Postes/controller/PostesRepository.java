package br.com.ifba.edu.Postes.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifba.edu.Postes.modelo.Post;

public interface PostesRepository  extends JpaRepository<Post, Long>{

	public List<Post> findByTitulo(String titulo);
	
	public List<Post> findByUsuarioNome(String nome);
	
}
