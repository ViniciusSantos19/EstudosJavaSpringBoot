package com.gerencia.sistema.dtos;

import com.gerencia.sistema.entidades.Categoria;

public record ProjetoDto(String nome, double custo, Categoria categoria) {

}
