package com.example.prova.Respository;

import com.example.prova.Entidades.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Long>{
    @Override
    public List<Agenda> findAll();

    @Override
    Optional<Agenda> findById(Long aLong);

    Optional<Agenda> findByNome(String nome);
}
