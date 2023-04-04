package com.example.AgendaTelefonica.DTOS;

import java.util.List;

public record PessoaDTOS(Long id,
		String nome,
		String sobrenome,
		String email,
		String endereco,
		List<TelefoneDTO> telefones) {

}
