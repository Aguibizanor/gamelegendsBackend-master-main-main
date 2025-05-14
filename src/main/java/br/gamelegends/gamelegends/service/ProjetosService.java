package br.gamelegends.gamelegends.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
			return projetosRepository.existsByName(nomeProjeto);
		}

		// Método estático para criar um projeto sem foto
		public static Projetos create(Projetos projetos) {
			// Aqui você pode adicionar lógica de validação ou outros processos
			// Exemplo: verificar se o projeto já existe
			if (projetos == null) {
				throw new IllegalArgumentException("O projeto não pode ser nulo.");
			}
			// Retorna o projeto criado (ou salva em algum lugar)
			return projetos;
		}

		// Método estático para criar um projeto com foto
		public static void createComFoto(MultipartFile file, Projetos projetos) {
			// Lógica para salvar a foto (exemplo: salvar em disco ou banco de dados)
			if (file != null && !file.isEmpty()) {
				// Processar o arquivo, como salvar o conteúdo ou gerar um caminho
				System.out.println("Foto recebida: " + file.getOriginalFilename());
			}

			// Salvar o projeto (pode ser ajustado para que o método não seja estático)
			if (projetos == null) {
				throw new IllegalArgumentException("O projeto não pode ser nulo.");
			}

			// Adicione aqui a lógica para salvar o projeto e associar a foto
		}
}