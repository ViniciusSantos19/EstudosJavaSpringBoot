package exemplo.Consultorio.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import exemplo.Consultorio.Dtos.ConsultaDto;
import exemplo.Consultorio.erros.InsertAgendaExcption;
import exemplo.Consultorio.service.ConsultaService;

@RestController
@RequestMapping("/Consultas")
public class ConsultaController {
	
	@Autowired
	private ConsultaService service;
	
	@PostMapping("/{idPaciente}/{idMedico}")
	public ResponseEntity<ConsultaDto> inserir(@PathVariable Long idPaciente,
			@PathVariable Long idMedico, 
			UriComponentsBuilder uriBuilder) throws InsertAgendaExcption{
		LocalDateTime dataHoraConsulta = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String formattedDateTime = dataHoraConsulta.format(formatter);
		dataHoraConsulta = LocalDateTime.parse(formattedDateTime);

		return service.agendarConsulta(idPaciente, idMedico, dataHoraConsulta, uriBuilder);
	}
	
}
