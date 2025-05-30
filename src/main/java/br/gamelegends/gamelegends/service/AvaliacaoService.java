package br.gamelegends.gamelegends.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.gamelegends.gamelegends.model.Avaliacao;
import br.gamelegends.gamelegends.model.AvaliacaoRepository;

@Service
public class AvaliacaoService {

    final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public Avaliacao save(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public List<Avaliacao> findAll() {
        return avaliacaoRepository.findAll();
    }

    public Optional<Avaliacao> findById(Long id) {
        return avaliacaoRepository.findById(id);
    }

    public Avaliacao update(Long id, Avaliacao novaAvaliacao) {
        return avaliacaoRepository.findById(id).map(avaliacao -> {
            avaliacao.setComentario(novaAvaliacao.getComentario());
            avaliacao.setAvalia(novaAvaliacao.getAvalia());
            return avaliacaoRepository.save(avaliacao);
        }).orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));
    }

    public void delete(Long id) {
        avaliacaoRepository.deleteById(id);
    }
}