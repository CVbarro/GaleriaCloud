package com.galeriaCloud.galeria.controller;

import com.galeriaCloud.galeria.model.Exposicao;
import com.galeriaCloud.galeria.model.Artista;
import com.galeriaCloud.galeria.model.ItemObra;
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

    public ExposicaoController(ExposicaoRepository exposicaoRepository, ArtistaRepository artistaRepository, ObraRepository obraRepository) {
        this.exposicaoRepository = exposicaoRepository;
        this.artistaRepository = artistaRepository;
        this.obraRepository = obraRepository;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ExposicaoRequest request) {
        // Buscar artista pelo nome agora
        Optional<Artista> artistaOpt = artistaRepository.findByNome(request.getArtistaNome());
        if (artistaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Artista não encontrado");
        }

        Artista artista = artistaOpt.get();

        List<ItemObra> itensObra = new ArrayList<>();
        for (String obraId : request.getObrasIds()) {
            Optional<Obra> obraOpt = obraRepository.findById(Integer.valueOf(obraId));
            if (obraOpt.isPresent()) {
                Obra obra = obraOpt.get();

                ItemObra item = new ItemObra();
                item.setId(String.valueOf(obra.getId()));
                item.setTitulo(obra.getTitulo());
                item.setImagemUrl(obra.getImagemURL());

                itensObra.add(item);
            } else {
                return ResponseEntity.badRequest().body("Obra não encontrada: ID " + obraId);
            }
        }

        Exposicao exposicao = new Exposicao();
        exposicao.setId(request.getId());
        exposicao.setNome(request.getNome());
        exposicao.setDescricao(request.getDescricao());
        exposicao.setData(request.getData() != null ? request.getData() : LocalDateTime.now());
        exposicao.setArtistaNome(artista.getNome());
        exposicao.setObras(itensObra);

        exposicaoRepository.save(exposicao);
        return ResponseEntity.ok().body(exposicao);
    }

    // Buscar exposicoes por nome do artista
    @GetMapping("/artista/{artistaNome}")
    public ResponseEntity<List<Exposicao>> listarPorArtista(@PathVariable String artistaNome) {
        return ResponseEntity.ok(exposicaoRepository.findByArtistaNome(artistaNome));
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


