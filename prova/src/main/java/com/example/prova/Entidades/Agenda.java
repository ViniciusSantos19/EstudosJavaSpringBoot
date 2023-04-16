package com.example.prova.Entidades;

import com.example.prova.DTOS.AgendaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Agendas2")
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Agenda_id")
    private List<Email> listaEmails;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Agenda_id")
    private List<Telefone> listaTelefones;

    public Agenda(AgendaDTO dto){
        this.nome = dto.nome();
        this.listaEmails = dto.listaEmail().stream().map(Email::new).collect(Collectors.toList());
        this.listaTelefones = dto.listaTelefone().stream().map(Telefone::new).collect(Collectors.toList());
    }
}
