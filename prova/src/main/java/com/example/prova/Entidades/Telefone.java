package com.example.prova.Entidades;

import com.example.prova.DTOS.EmailDTO;
import com.example.prova.DTOS.TelefoneDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "Telefones2")
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String numero;
    @Enumerated(EnumType.STRING)
    private CategoriaTelefone categoria;

    public Telefone(TelefoneDTO dto){
        this.numero = dto.numero();
        this.categoria = dto.categoria();
    }

}
