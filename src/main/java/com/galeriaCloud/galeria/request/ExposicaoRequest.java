package com.galeriaCloud.galeria.request;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExposicaoRequest {

    private String nome;
    private String descricao;
    private LocalDateTime data;
    private String id;

    private Long artistaId;
    private String artistaNome;

    private List<String> obrasIds;

}

