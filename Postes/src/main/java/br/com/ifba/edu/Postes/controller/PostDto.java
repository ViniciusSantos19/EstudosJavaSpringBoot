package br.com.ifba.edu.Postes.controller;


import java.util.List;
import java.util.stream.Collectors;

import br.com.ifba.edu.Postes.modelo.Categoria;
import br.com.ifba.edu.Postes.modelo.Post;
import br.com.ifba.edu.Postes.modelo.Usuario;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

public class PostDto {
	
	private Long id;
	private String titulo;
	private String texto;
	private String usuario;
	private Categoria categoria;
	
	
	
	public PostDto(Post post) {
		this.categoria = post.getCategoria();
		this.id = post.getId();
		this.texto = post.getTexto();
		this.titulo = post.getTitulo();
		this.usuario = post.getUsuario().getNome();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public static List<PostDto> converte(List<Post> posts){
		return posts.stream().map(PostDto::new).collect(Collectors.toList());
	}
	
	public Post converter(UsuarioRepository userRepositorio) {
		Usuario user = userRepositorio.findByNome(usuario);
		Post post = new Post(titulo, texto, user );
		return post;
	}
	
}
