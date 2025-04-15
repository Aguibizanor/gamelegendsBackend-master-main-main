package br.gamelegends.gamelegends.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gamelegends.gamelegends.model.Projetos;
import br.gamelegends.gamelegends.model.ProjetosRepository;
import jakarta.transaction.Transactional;

@Service 
public class ProjetosService {

		final ProjetosRepository projetosRepository;
		
		public ProjetosService(ProjetosRepository _projetosRepository) {
			this.projetosRepository = _projetosRepository;
		}
		

		public List<Projetos> fintAll () {
			return projetosRepository.findAll();
		}
		
		@Transactional
		public Projetos save(Projetos projetos) {
			return projetosRepository.save(projetos);
		}
}
