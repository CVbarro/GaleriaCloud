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
        // Verifica se o nome do artista foi informado
        if (obra.getArtistaNome() == null || obra.getArtistaNome().isBlank()) {
            return ResponseEntity.badRequest().body("Nome do artista inválido");
        }

        // Busca o artista pelo nome
        Optional<Artista> artista = artistaRepository.findByNome(obra.getArtistaNome());
        Artista artista1 = artista.get();

        if (artista.isEmpty()) {
            return ResponseEntity.badRequest().body("Artista não encontrado");
        }

        // Setar o nome do artista (pode até repetir a string, mas o relacionamento não existe mais)
        obra.setArtistaNome(artista.get().getNome());
        artista1.getObras().add(obra);
        Obra obraSalva = obraRepository.save(obra);
        return ResponseEntity.ok(obraSalva);
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
