package com.example.AgendaTelefonica.entidades;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.AgendaTelefonica.DTOS.AgendaDTO;
import jakarta.persistence.*;

@Entity(name = "Agendas")
public class Agenda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	private String sobrenome;
	private String email;
	private String endereco;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "Agenda_id")
	private List<Numero> numeros= new ArrayList<Numero>();

	public Agenda() {}

	public Agenda(Long id, String nome,
				  String sobrenome,
				  String email,
				  String endereco,
				  List<Numero> numeros) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.endereco = endereco;
		this.numeros = numeros;
	}

	public Agenda(AgendaDTO dto){
		this.id = dto.id();
		this.nome = dto.nome();
		this.sobrenome = dto.sobrenome();
		this.email = dto.email();
		this.endereco = dto.endereco();
		this.numeros = dto.telefones().stream().map(Numero::new).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Numero> getTelefones() {
		return numeros;
	}

	public void setTelefones(List<Numero> numeros) {
		this.numeros = numeros;
	}
	
	
	
}
