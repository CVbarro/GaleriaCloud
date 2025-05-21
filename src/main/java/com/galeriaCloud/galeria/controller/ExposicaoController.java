package com.galeriaCloud.galeria.controller;

import com.galeriaCloud.galeria.model.Exposicao;
import com.galeriaCloud.galeria.model.Artista;
import com.galeriaCloud.galeria.repository.ExposicaoRepository;
import com.galeriaCloud.galeria.repository.ArtistaRepository;
import com.galeriaCloud.galeria.request.ExposicaoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/exposicoes")
public class ExposicaoController {

    private final ExposicaoRepository exposicaoRepository;
    private final ArtistaRepository artistaRepository;

    public ExposicaoController(ExposicaoRepository exposicaoRepository, ArtistaRepository artistaRepository) {
        this.exposicaoRepository = exposicaoRepository;
        this.artistaRepository = artistaRepository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ExposicaoRequest request) {
        Optional<Artista> artistaOpt = artistaRepository.findById(Math.toIntExact((request.getArtistaId())));
        if (artistaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Artista não encontrado");
        }

        Artista artista = artistaOpt.get();

        Exposicao exposicao = new Exposicao();
        exposicao.setId(request.getId());
        exposicao.setNome(request.getNome());
        exposicao.setDescricao(request.getDescricao());
        exposicao.setData(request.getData() != null ? request.getData() : LocalDateTime.now());
        exposicao.setArtistaId(String.valueOf(request.getArtistaId()));
        exposicao.setArtistaNome(artista.getNome());
        exposicao.setObrasIds(request.getObrasIds());

        exposicaoRepository.save(exposicao);
        return ResponseEntity.ok().body(exposicao);
    }

    @GetMapping
    public ResponseEntity<List<Exposicao>> listarTodas() {
        List<Exposicao> exposicoes = new ArrayList<>();
        exposicaoRepository.findAll().forEach(exposicoes::add);
        return ResponseEntity.ok(exposicoes);
    }

    @GetMapping("/artista/{artistaId}")
    public ResponseEntity<List<Exposicao>> listarPorArtista(@PathVariable String artistaId) {
        return ResponseEntity.ok(exposicaoRepository.findByArtistaId(artistaId));
    }

    @DeleteMapping("/{exposicaoId}")
    public ResponseEntity<Void> deletar(@PathVariable String exposicaoId) {
        Optional<Exposicao> exposicao = exposicaoRepository.findById(exposicaoId);
        if (exposicao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        exposicaoRepository.delete(exposicao.get());
        return ResponseEntity.noContent().build();
    }
}
