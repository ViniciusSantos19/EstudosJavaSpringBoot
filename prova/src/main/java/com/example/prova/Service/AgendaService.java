package com.example.prova.Service;

import com.example.prova.DTOS.AgendaDTO;
import com.example.prova.DTOS.EmailDTO;
import com.example.prova.DTOS.TelefoneDTO;
import com.example.prova.Entidades.Agenda;
import com.example.prova.Entidades.Email;
import com.example.prova.Entidades.Telefone;
import com.example.prova.Respository.AgendaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository repository;

    private List<AgendaDTO> converteAgenda(List<Agenda> lista){
        return lista.stream().map(a -> new AgendaDTO(a.getId(),
                a.getNome(),
                this.converteEmail(a.getListaEmails()),
               this.converteTelefone( a.getListaTelefones())
        )).collect(Collectors.toList());
    }

    private List<TelefoneDTO> converteTelefone(List<Telefone> lista){
        return lista.stream().map(a -> new TelefoneDTO(
                a.getNumero(),
                a.getCategoria()
        )).collect(Collectors.toList());
    }

    private List<EmailDTO> converteEmail(List<Email> lista){
        return lista.stream().map(a -> new EmailDTO(
                a.getEndereco(),
                a.getCategoria()
        )).collect(Collectors.toList());
    }

    public List<AgendaDTO> getAll(){
        return this.converteAgenda(repository.findAll());
    }

    public AgendaDTO findByNome(String nome){
        Optional<Agenda> agendaOptional = repository.findByNome(nome);
        if(agendaOptional.isPresent()){
            Agenda agenda = agendaOptional.get();

            return new AgendaDTO(agenda.getId(),
                    agenda.getNome(),
                    this.converteEmail(agenda.getListaEmails()),
                    this.converteTelefone(agenda.getListaTelefones()));
        }
        return null;
    }

    public AgendaDTO findById(Long id){
        Optional<Agenda> agendaOptional = repository.findById(id);
        if(agendaOptional.isPresent()){
            Agenda agenda = agendaOptional.get();

            return new AgendaDTO(agenda.getId(),
                    agenda.getNome(),
                    this.converteEmail(agenda.getListaEmails()),
                    this.converteTelefone(agenda.getListaTelefones()));
        }
        return null;
    }

    public ResponseEntity<AgendaDTO> insertIntoAgenda(@RequestBody @Valid AgendaDTO agendaDto,
                                                      UriComponentsBuilder uriBuilder){
        Agenda agenda = new Agenda(agendaDto);
        repository.save(agenda);
        URI uri=uriBuilder.path("/Agendas/{id}").buildAndExpand(agenda.getId()).toUri();
        return ResponseEntity.created(uri).body(new AgendaDTO(
                agenda.getId(),
                agenda.getNome(),
                this.converteEmail(agenda.getListaEmails()),
                this.converteTelefone(agenda.getListaTelefones())
        ));
    }

    public ResponseEntity<AgendaDTO> updateAgendas(AgendaDTO agendaDto, Long id){
        Optional<Agenda> agendaOptional = repository.findById(id);
        if(agendaOptional.isPresent()){
            Agenda agenda = new Agenda(agendaDto);
            agenda.setId(id);
            repository.save(agenda);
            return new ResponseEntity<AgendaDTO>(new AgendaDTO(
                    agenda.getId(),
                    agenda.getNome(),
                    this.converteEmail(agenda.getListaEmails()),
                    this.converteTelefone(agenda.getListaTelefones())
            ),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<AgendaDTO> deletar(Long id){
        Optional<Agenda> agendaOptional = repository.findById(id);
        if(agendaOptional.isPresent()){
            Agenda agenda = agendaOptional.get();
            ResponseEntity<AgendaDTO> ent = new ResponseEntity<AgendaDTO>(new AgendaDTO(
                    agenda.getId(),
                    agenda.getNome(),
                    this.converteEmail(agenda.getListaEmails()),
                    this.converteTelefone(agenda.getListaTelefones())
            ),HttpStatus.OK);
            repository.delete(agenda);
            return ent;
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
