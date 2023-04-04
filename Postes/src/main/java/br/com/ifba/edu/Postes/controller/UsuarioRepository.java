package br.com.ifba.edu.Postes.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ifba.edu.Postes.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByNome(String usuario);
	
	
	
}
