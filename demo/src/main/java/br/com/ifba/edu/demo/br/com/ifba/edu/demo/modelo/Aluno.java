package br.com.ifba.edu.demo.br.com.ifba.edu.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="alunos")
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idAluno;
	private String nome;
	private double prova1;
	private double prova2;
	private double trabalho;
	private double media;

	public Aluno() {
		
	}

	public String getNome() {
		return nome;
	}

	public double getProva1() {
		return prova1;
	}

	public double getProva2() {
		return prova2;
	}

	public double getTrabalho() {
		return trabalho;
	}

	public void setMeida(Double media) {
		this.media = media;
	}
	
	public Double getMedia() {
		return media;
	}
	
}
