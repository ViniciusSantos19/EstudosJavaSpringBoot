package com.gerencia.sistema.entidades;

import com.gerencia.sistema.dtos.EngenheiroDto;

import jakarta.persistence.Column;
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

@Entity(name = "engenheiros")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Engenheiro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false, unique = true)
	private String CREA;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Especialidade especialidade=Especialidade.CIVIL;

	
	public Engenheiro(EngenheiroDto dto) {
		this.nome = dto.nome();
		this.CREA = dto.CREA();
		this.especialidade = dto.especialidade();
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

	public String getCREA() {
		return CREA;
	}

	public void setCREA(String cREA) {
		CREA = cREA;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
	
	
}
