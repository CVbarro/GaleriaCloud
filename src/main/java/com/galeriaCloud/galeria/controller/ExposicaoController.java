package com.galeriaCloud.galeria.controller;

import com.galeriaCloud.galeria.model.Exposicao;
import com.galeriaCloud.galeria.model.Artista;
import com.galeriaCloud.galeria.model.Obra;
import com.galeriaCloud.galeria.repository.ExposicaoRepository;
import com.galeriaCloud.galeria.repository.ArtistaRepository;
import com.galeriaCloud.galeria.repository.ObraRepository;
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
    private final ObraRepository obraRepository;

    public ExposicaoController(
            ExposicaoRepository exposicaoRepository,
            ArtistaRepository artistaRepository,
            ObraRepository obraRepository
    ) {
        this.exposicaoRepository = exposicaoRepository;
        this.artistaRepository = artistaRepository;
        this.obraRepository = obraRepository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ExposicaoRequest request) {
        Optional<Artista> artistaOpt = artistaRepository.findByNome(request.getArtistaNome());
        if (artistaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Artista não encontrado");
        }

        List<Obra> obras = new ArrayList<>();
        for (Integer obraId : request.getObrasIds()) {
            Optional<Obra> obraOpt = obraRepository.findById(obraId);
            if (obraOpt.isPresent()) {
                obras.add(obraOpt.get());
            } else {
                return ResponseEntity.badRequest().body("Obra não encontrada: ID " + obraId);
            }
        }

        Exposicao exposicao = new Exposicao();
        exposicao.setNome(request.getNome());
        exposicao.setDescricao(request.getDescricao());
        exposicao.setData(request.getData() != null ? request.getData() : LocalDateTime.now());
        exposicao.setArtistaNome(request.getArtistaNome());
        exposicao.setObras(obras);

        exposicaoRepository.save(exposicao);
        return ResponseEntity.status(201).body(exposicao);
    }

    @GetMapping("/artista/{artistaNome}")
    public ResponseEntity<List<Exposicao>> listarPorArtista(@PathVariable String artistaNome) {
        return ResponseEntity.ok(exposicaoRepository.findByArtistaNome(artistaNome));
    }

    @DeleteMapping("/{exposicaoId}")
    public ResponseEntity<Void> deletar(@PathVariable int exposicaoId) {
        Optional<Exposicao> exposicao = exposicaoRepository.findById(String.valueOf(exposicaoId));
        if (exposicao.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        exposicaoRepository.delete(exposicao.get());
        return ResponseEntity.noContent().build();
    }

}
