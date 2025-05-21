package com.galeriaCloud.galeria.controller;

import com.galeriaCloud.galeria.model.Artista;
import com.galeriaCloud.galeria.model.Obra;
import com.galeriaCloud.galeria.repository.ArtistaRepository;
import com.galeriaCloud.galeria.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("obras")
public class ObraController {

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private ObraRepository obraRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Obra obra) {

        if (obra.getArtista() == null || obra.getArtista().getId() == null) {
            return ResponseEntity.badRequest().body("Artista inválido");
        }

        Optional<Artista> artista = artistaRepository.findById(Integer.valueOf(obra.getArtista().getId()));
        if (artista.isEmpty()) {
            return ResponseEntity.badRequest().body("Artista não encontrado");
        }

        obra.setArtista(artista.get());
        return ResponseEntity.ok(obraRepository.save(obra));
    }

}
