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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.gamelegends.gamelegends.model.Projetos;
import br.gamelegends.gamelegends.service.ProjetosService;
import br.gamelegends.gamelegends.rest.response.MessageResponse;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/projetos")
public class ProjetosController {

    final ProjetosService projetosService;

    public ProjetosController(ProjetosService _projetosService) {
        this.projetosService = _projetosService;
    }

    // GET: Retorna todos os projetos
    @GetMapping
    public ResponseEntity<List<Projetos>> getAllProjetos() {
        return ResponseEntity.status(HttpStatus.OK).body(projetosService.findAll());
    }

    // POST: Cria um projeto sem foto
    @PostMapping("createSemFoto")
    public ResponseEntity<?> createSemFoto(@ModelAttribute Projetos projetos) {
        if (projetosService.existsByName(projetos.getNomeProjeto())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Produto já cadastrado!"));
        }

        projetosService.save(projetos);
        return ResponseEntity.ok().body(new MessageResponse("Produto cadastrado com sucesso!"));
    }

    // POST: Cria um projeto com foto
    @PostMapping("createComFoto")
    public ResponseEntity<?> createComFoto(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @ModelAttribute Projetos projetos) {
        projetosService.createComFoto(file, projetos);
        return ResponseEntity.ok().body(new MessageResponse("Produto cadastrado com sucesso!"));
    }

    // POST: Salva um novo projeto com validações
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

        // Salva o projeto no banco de dados
        return ResponseEntity.status(HttpStatus.CREATED).body(projetosService.save(projetos));
    }
}