package com.virtualize.varejodasorte.api.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ExceptionTO {
    
    private String codigo;
    private String[] mensagem;
    private Map<String, List<String>> mensagensPorCampo;
    
    public ExceptionTO(String codigo) {
        super();
        this.codigo = codigo;
        this.mensagem = new String[0];
        this.mensagensPorCampo = new HashMap<>();
    }
    
    public ExceptionTO(String codigo, String mensagem) {
        super();
        this.codigo = codigo;
        this.mensagem = new String[1];
        this.mensagem[0] = mensagem;
        this.mensagensPorCampo = new HashMap<>();
    }
    
    public ExceptionTO(String codigo, String... mensagem) {
        super();
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.mensagensPorCampo = new HashMap<>();
    }
}
