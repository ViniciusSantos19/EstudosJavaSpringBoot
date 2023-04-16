package com.example.prova.Entidades;

import com.example.prova.DTOS.EmailDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "Emails")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String endereco;
    @Enumerated(EnumType.STRING)
    private CategoriaEmail categoria;

    public Email(EmailDTO dto){
        this.endereco = dto.endereco();
        this.categoria = dto.categoria();
    }

}
