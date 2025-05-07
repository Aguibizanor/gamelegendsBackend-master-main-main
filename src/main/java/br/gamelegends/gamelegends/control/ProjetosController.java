package br.gamelegends.gamelegends.control;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; // Para receber o corpo da requisição
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gamelegends.gamelegends.model.Projetos;
import br.gamelegends.gamelegends.service.ProjetosService;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/projetos")
public class ProjetosController {
	
	final ProjetosService projetosService;
	
	public ProjetosController(ProjetosService _projetosService) {
		this.projetosService = _projetosService;
	}

	// GET: Retorna todos os projetos
	@GetMapping
	public ResponseEntity<List<Projetos>> getAllProjetos() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(projetosService.findAll());
	}
	
	// POST: Salva um novo projeto
	@PostMapping
	public ResponseEntity<Object> saveProjetos(@RequestBody Projetos projetos) {
	    // Validação de campos obrigatórios
	    if (projetos.getNomeProjeto() == null || projetos.getNomeProjeto().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O nome do projeto é obrigatório.");
	    }
	    if (projetos.getDescricao() == null || projetos.getDescricao().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A descrição do projeto é obrigatória.");
	    }
	    if (projetos.getDataInicio() == null || projetos.getDataInicio().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início é obrigatória.");
	    }
	    if (projetos.getTecnologias() == null || projetos.getTecnologias().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("As tecnologias são obrigatórias.");
	    }
	    if (projetos.getGenero() == null || projetos.getGenero().isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O gênero do projeto é obrigatório.");
	    }
	    /*
	    if (projetos.getFoto() == null || projetos.getFoto().length == 0) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A imagem do projeto é obrigatória.");
	    }
	    */

	    // Salva o projeto no banco de dados
	    return ResponseEntity.status(HttpStatus.CREATED)
	            .body(projetosService.save(projetos));
	}
	
}