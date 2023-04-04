package br.com.ifba.edu.Postes.controller;

import java.net.URI;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.deser.impl.ExternalTypeHandler.Builder;

import br.com.ifba.edu.Postes.modelo.Post;

@RequestMapping("/posts")
@RestController
public class PostController {

	@Autowired
	private PostesRepository repository;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	@GetMapping
	public List<PostDto> listar (){
		return PostDto.converte(repository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<PostDto> cadastrarPost(@RequestBody PostDto postDto,
			UriComponentsBuilder uriBuilder) {
		Post post = postDto.converter(userRepository);
		repository.save(post);
		URI uri= uriBuilder.path("/posts/{id}").buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(uri).body(new PostDto(post));
	}
	
	/*@RequestMapping("/posts1")
	public List<PostDto> listarPorTitulo(String titulo){
		return PostDto.converte(repository.findByTitulo(titulo));
	}*/
	
	/*@RequestMapping("/posts2")
	public List<PostDto> listarPorUsuario(String usuario){
		return PostDto.converte(repository.findByUsuarioNome(usuario));
	}*/
	
}
