package com.example.AgendaTelefonica.DTOS;

import com.example.AgendaTelefonica.entidades.Categoria;

public record NumeroDTO(Long id,
		String numero,
		Categoria categoria) {

}
