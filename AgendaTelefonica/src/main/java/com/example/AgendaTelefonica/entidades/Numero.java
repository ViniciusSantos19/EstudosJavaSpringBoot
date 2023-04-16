package com.example.AgendaTelefonica.entidades;

import com.example.AgendaTelefonica.DTOS.NumeroDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name = "Numeros")
public class Numero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String numero;
	
	@Enumerated(EnumType.STRING)
	private Categoria categoria;

	public Numero(Long id, String numero, Categoria categoria) {
		this.id = id;
		this.numero = numero;
		this.categoria = categoria;
	}

	public Numero(NumeroDTO dto){
		this.id = dto.id();
		this.numero = dto.numero();
		this.categoria = dto.categoria();
	}

	public Numero(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
