package com.example.AgendaTelefonica.DTOS;

import java.util.List;

public record AgendaDTO(Long id,
		String nome,
		String sobrenome,
		String email,
		String endereco,
		List<NumeroDTO> telefones) {

}
