package exemplo.Consultorio.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import exemplo.Consultorio.Dtos.MedicoDto;
import exemplo.Consultorio.Dtos.PacienteDto;
import exemplo.Consultorio.entidades.Medico;
import exemplo.Consultorio.entidades.Paciente;
import exemplo.Consultorio.repositorios.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository repository;
	
	private List<PacienteDto> converteEmPacienteDto(List<Paciente> lista){
		return lista.stream().map(a -> new PacienteDto(a.getNome(),
				a.getTelefone(),
				a.getEmail(),
				a.getCpf())).collect(Collectors.toList());
	}
	
	public List<PacienteDto> listarMedicos() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
        Page<Paciente> pacientes = repository.findAll(pageable);
        List<Paciente> lista = pacientes.getContent();
        return this.converteEmPacienteDto(lista);
    }
	
	   public ResponseEntity<PacienteDto> updateMedico(PacienteDto pacienteDto, Long id){
	        Optional<Paciente> pacienteOptional = repository.findById(id);
	        if(pacienteOptional.isPresent()){
	            Paciente paciente = pacienteOptional.get();
	            paciente.setId(id);
	            paciente.setNome(pacienteDto.nome());
	            paciente.setTelefone(pacienteDto.telefone());
	            repository.save(paciente);
	            return new ResponseEntity<PacienteDto>( new PacienteDto(
	            		paciente.getNome(),
	    				paciente.getTelefone(),
	    				paciente.getEmail(),
	    				paciente.getCpf()),HttpStatus.OK);
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	   
	   public ResponseEntity<PacienteDto> deleteMedico(Long id){
	        Optional<Paciente> pacienteOptional = repository.findById(id);
	        if(pacienteOptional.isPresent()){
	            Paciente paciente = pacienteOptional.get();
	            paciente.setId(id);
	            paciente.setNome("inativo");
	            paciente.setEmail("Inativo");
	            paciente.setTelefone("Inativo");
	            
	            ResponseEntity<PacienteDto> ent = new ResponseEntity<PacienteDto>(new PacienteDto(
	            		paciente.getNome(),
	    				paciente.getTelefone(),
	    				paciente.getEmail(),
	    				paciente.getCpf()),HttpStatus.OK);
	            
	            repository.save(paciente);
	            
	            return ent;
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	
}
