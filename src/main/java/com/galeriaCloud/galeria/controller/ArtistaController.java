package com.galeriaCloud.galeria.controller;

import com.galeriaCloud.galeria.model.Artista;
import com.galeriaCloud.galeria.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("artistas")
public class ArtistaController {

    @Autowired
    private ArtistaRepository artistaRepository;


    @GetMapping
    public ResponseEntity<List<Artista>> getArtista(){
        List<Artista> artistas = artistaRepository.findAll();
        return ResponseEntity.ok(artistas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> getById(@PathVariable Integer id){
        return artistaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<Artista> create(@RequestBody Artista artista){
        Artista novoArtista = artistaRepository.save(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoArtista);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtista(@PathVariable Integer id) {
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        artistaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
