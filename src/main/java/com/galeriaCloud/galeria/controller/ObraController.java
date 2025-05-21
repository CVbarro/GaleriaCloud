package com.galeriaCloud.galeria.controller;

import com.galeriaCloud.galeria.model.Artista;
import com.galeriaCloud.galeria.model.Obra;
import com.galeriaCloud.galeria.repository.ArtistaRepository;
import com.galeriaCloud.galeria.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/obras")
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
        Obra obraSalva = obraRepository.save(obra);
        return ResponseEntity.ok(obraRepository.save(obraSalva));
    }

    @GetMapping
    public ResponseEntity<List<Obra>> getAllObras(){
        List<Obra> obras = obraRepository.findAll();
        return ResponseEntity.ok(obras);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obra> getObra(@PathVariable Integer id){
            return obraRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObra(@PathVariable Integer id){
        if (!obraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        obraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
