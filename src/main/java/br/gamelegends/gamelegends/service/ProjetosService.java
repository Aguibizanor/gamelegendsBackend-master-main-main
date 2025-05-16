package br.gamelegends.gamelegends.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import br.gamelegends.gamelegends.model.Projetos;
import br.gamelegends.gamelegends.model.ProjetosRepository;
import jakarta.transaction.Transactional;

@Service 
public class ProjetosService {

    final ProjetosRepository projetosRepository;

    public ProjetosService(ProjetosRepository _projetosRepository) {
        this.projetosRepository = _projetosRepository;
    }

    // Método para listar todos os projetos
    public List<Projetos> findAll () {
        return projetosRepository.findAll();
    }

    // Método para salvar um projeto
    @Transactional
    public Projetos save(Projetos projetos) {
        return projetosRepository.save(projetos);
    }

    // Método para verificar se um projeto com o nome já existe
    public boolean existsByName(String nomeProjeto) {
        return projetosRepository.existsByNomeProjeto(nomeProjeto);
    }

    // Método para criar um projeto sem foto (caso precise lógica extra)
    public Projetos create(Projetos projetos) {
        if (projetos == null) {
            throw new IllegalArgumentException("O projeto não pode ser nulo.");
        }
        return projetosRepository.save(projetos);
    }

    // Método para criar um projeto com foto
    @Transactional
    public void createComFoto(MultipartFile file, Projetos projetos) {
        if (projetos == null) {
            throw new IllegalArgumentException("O projeto não pode ser nulo.");
        }
        if (file != null && !file.isEmpty()) {
            String nomeArquivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            // Caminho absoluto para uploads/imagens-projetos na raiz do projeto
            String pastaDestino = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "imagens-projetos" + File.separator;
            File diretorio = new File(pastaDestino);
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }
            File destino = new File(pastaDestino + nomeArquivo);
            try {
                file.transferTo(destino);
                // Caminho relativo para ser salvo no banco e acessado pelo frontend
                projetos.setImagemUrl("uploads/imagens-projetos/" + nomeArquivo);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar a imagem: " + e.getMessage());
            }
        }
        projetosRepository.save(projetos);
    }
}