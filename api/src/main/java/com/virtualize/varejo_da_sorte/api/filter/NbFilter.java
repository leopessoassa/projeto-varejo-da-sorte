package com.virtualize.varejo_da_sorte.api.filter;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title = "NbFilter")
public class NbFilter {

    @NotNull(message = "NB não informado.")
    @Parameter(description = "Numero do beneficio.", example = "1234567890")
    private Long nb;
}
