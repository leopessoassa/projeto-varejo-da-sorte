package com.virtualize.varejo_da_sorte.api.configuration.security.to;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTO {
    private Long cpf;
    private Integer matricula;
    private String name;
    private String ipRemoto;
    private String ol;
    private String[] permissions;
}
