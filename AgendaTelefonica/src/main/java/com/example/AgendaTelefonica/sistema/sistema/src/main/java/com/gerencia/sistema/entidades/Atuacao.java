package com.gerencia.sistema.entidades;

import com.gerencia.sistema.dtos.AtuacaoDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "atuacoes")
@AllArgsConstructor
@Setter
@Getter
public class Atuacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engenheiro_id")
	private Engenheiro engenheiro;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "projeto_id")
	private Projeto projeto;
	private int duracao;
	
	public Atuacao() {}
	
	public Atuacao(AtuacaoDto dto) {
		this.duracao = dto.duracao();
		this.engenheiro = new Engenheiro(dto.engenheiro());
		this.projeto = new Projeto(dto.projeto());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Engenheiro getEngenheiro() {
		return engenheiro;
	}

	public void setEngenheiro(Engenheiro engenheiro) {
		this.engenheiro = engenheiro;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public int getDuracao() {
		return duracao;
	}

	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	
	

}
