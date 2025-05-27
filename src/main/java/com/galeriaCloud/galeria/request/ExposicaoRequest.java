package com.galeriaCloud.galeria.request;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExposicaoRequest {

    private String id;
    private String nome;
    private String descricao;
    private LocalDateTime data;
    private String artistaNome;
    private List<String> obrasIds;

}

