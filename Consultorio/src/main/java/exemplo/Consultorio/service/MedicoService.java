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
import exemplo.Consultorio.entidades.Medico;
import exemplo.Consultorio.repositorios.MedicoRepository;

@Service
public class MedicoService {
	
	@Autowired
	private MedicoRepository repository;
	
	private List<MedicoDto> converteEmMedicoDto(List<Medico> lista){
		return lista.stream().map(a -> new MedicoDto(a.getNome(),
				a.getTelefone(),
				a.getEmail(),
				a.getCrm(),
				a.getEspecialidade())).collect(Collectors.toList());
	}
	
	public List<MedicoDto> listarMedicos() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
        Page<Medico> medicos = repository.findAll(pageable);
        List<Medico> lista = medicos.getContent();
        return this.converteEmMedicoDto(lista);
    }
	
	   public ResponseEntity<MedicoDto> updateMedico(MedicoDto medicoDto, Long id){
	        Optional<Medico> medicoOptional = repository.findById(id);
	        if(medicoOptional.isPresent()){
	            Medico medico = medicoOptional.get();
	            medico.setId(id);
	            medico.setNome(medicoDto.nome());
	            medico.setTelefone(medicoDto.telefone());
	            repository.save(medico);
	            return new ResponseEntity<MedicoDto>(new MedicoDto(medico.getNome(),
	    				medico.getTelefone(),
	    				medico.getEmail(),
	    				medico.getCrm(),
	    				medico.getEspecialidade()),HttpStatus.OK);
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	   
	   public ResponseEntity<MedicoDto> deleteMedico(Long id){
	        Optional<Medico> medicoOptional = repository.findById(id);
	        if(medicoOptional.isPresent()){
	            Medico medico = medicoOptional.get();
	            medico.setId(id);
	            medico.setNome("inativo");
	            medico.setEmail("Inativo");
	            medico.setTelefone("Inativo");
	            
	            ResponseEntity<MedicoDto> ent = new ResponseEntity<MedicoDto>(new MedicoDto(medico.getNome(),
	    				medico.getTelefone(),
	    				medico.getEmail(),
	    				medico.getCrm(),
	    				medico.getEspecialidade()),HttpStatus.OK);
	            
	            repository.save(medico);
	            
	            return ent;
	        }
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	
}
