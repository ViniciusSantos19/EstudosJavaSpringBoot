package exemplo.Consultorio.service;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import exemplo.Consultorio.Dtos.ConsultaDto;
import exemplo.Consultorio.Dtos.MedicoDto;
import exemplo.Consultorio.Dtos.PacienteDto;
import exemplo.Consultorio.entidades.Consulta;
import exemplo.Consultorio.entidades.Medico;
import exemplo.Consultorio.entidades.Paciente;
import exemplo.Consultorio.erros.InsertAgendaExcption;
import exemplo.Consultorio.repositorios.ConsultaRepository;
import exemplo.Consultorio.repositorios.MedicoRepository;
import exemplo.Consultorio.repositorios.PacienteRepository;

@Service
public class ConsultaService {
	
		@Autowired
	    private ConsultaRepository consultaRepository;
	    
	    @Autowired
	    private PacienteRepository pacienteRepository;
	    
	    @Autowired
	    private MedicoRepository medicoRepository;
	    
	    public ResponseEntity<ConsultaDto> agendarConsulta(Long idPaciente, Long idMedico, LocalDateTime dataHoraConsulta, UriComponentsBuilder uriBuilder) throws InsertAgendaExcption {
	        // Verificar se paciente existe e não está inativo
	        Optional<Paciente> pacienteOptional = pacienteRepository.findById(idPaciente);
	        System.out.println(dataHoraConsulta.getDayOfMonth());
	        System.out.println(pacienteOptional.get().getNome());
	        if (!pacienteOptional.isPresent() || pacienteOptional.get().isAtivo() == false) {
	           throw new InsertAgendaExcption("Paciente não encontrado ou inativo");
	        }
	        Paciente paciente = pacienteOptional.get();
	        
	        // Verificar se médico existe e não está inativo
	        Optional<Medico> medicoOptional = medicoRepository.findById(idMedico);
	        if (!medicoOptional.isPresent() || medicoOptional.get().isAtivo() == false) {
	        	throw new InsertAgendaExcption("Medico não encontrado ou inativo");
	        }
	        Medico medico = medicoOptional.get();
	        
	        // Verificar se horário da consulta está dentro do horário de funcionamento
	        LocalTime horaConsulta = dataHoraConsulta.toLocalTime();
	        if (horaConsulta.isBefore(LocalTime.of(7, 0)) || horaConsulta.isAfter(LocalTime.of(19, 0))) {
	        	throw new InsertAgendaExcption("horário inválido");
	        }
	        
	        // Verificar se há outra consulta agendada para o mesmo paciente no mesmo dia
	        LocalDate dataConsulta = dataHoraConsulta.toLocalDate();
	        if (consultaRepository.existsByPacienteAndDataHoraConsultaBetween(paciente, 
	                dataConsulta.atStartOfDay(), dataConsulta.plusDays(1).atStartOfDay())) {
	        	throw new InsertAgendaExcption("Já tem outra consulta do mesmo paciente");
	        }
	        
	        // Verificar se o médico já possui outra consulta agendada na mesma data/hora
	        if (consultaRepository.existsByMedicoAndDataHoraConsulta(medico, dataHoraConsulta)) {
	        	throw new InsertAgendaExcption("O médico já possui uma consulta na mesma data/hora");
	        }
	        
	        // Criar a consulta e salvá-la no banco de dados
	        Consulta consulta = new Consulta();
	        consulta.setPaciente(paciente);
	        consulta.setMedico(medico);
	        consulta.setDataHora(dataHoraConsulta);
	        
	        consultaRepository.save(consulta);
	        
	        URI url = uriBuilder.path("/Consultas/{id}").buildAndExpand(consulta.getId()).toUri();
	        return ResponseEntity.created(url).body(converteEmConsultaDto(consulta));
	    }
	
	    private ConsultaDto converteEmConsultaDto(Consulta consulta) {
	        PacienteDto pacienteDto = new PacienteDto(consulta.getPaciente().getNome(), consulta.getPaciente().getTelefone(), consulta.getPaciente().getEmail(), consulta.getPaciente().getCpf());
	        MedicoDto medicoDto = new MedicoDto(consulta.getMedico().getNome(), consulta.getMedico().getTelefone(), consulta.getMedico().getEmail(), consulta.getMedico().getCrm(), consulta.getMedico().getEspecialidade());
	        LocalDateTime dataHoraConsulta = consulta.getDataHora();
	        return new ConsultaDto(pacienteDto, medicoDto, dataHoraConsulta);
	    }
	    
}
