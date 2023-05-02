package com.gerencia.sistema.entidades;

import com.gerencia.sistema.dtos.ProjetoDto;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "projetos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private double custo;
	@Enumerated(EnumType.STRING)
	private Categoria categoria=Categoria.PÚBLICO;
	
	public Projeto(ProjetoDto  dto) {
		this.nome = dto.nome();
		this.custo = dto.custo();
		this.categoria = dto.categoria();
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

	public double getCusto() {
		return custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	
}
