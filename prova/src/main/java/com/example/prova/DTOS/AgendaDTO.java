package com.example.prova.DTOS;

import java.util.List;

public record AgendaDTO(Long id,
                        String nome,
                        List<EmailDTO> listaEmail,
                        List<TelefoneDTO> listaTelefone) {
}
