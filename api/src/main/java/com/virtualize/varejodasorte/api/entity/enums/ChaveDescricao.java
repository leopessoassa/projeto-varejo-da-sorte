package com.virtualize.varejodasorte.api.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.virtualize.varejodasorte.api.entity.utils.ChaveDescricaoHolder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Optional;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Schema(title = "ChaveDescricao")
public interface ChaveDescricao<T extends Serializable> {

    static <I extends Serializable> ChaveDescricao<I> forChave(I chave) {
        return ChaveDescricaoHolder.forChave(chave);
    }

    static <I extends Serializable, T extends ChaveDescricao<I>> ChaveDescricao<I> forChaveContent(I chave, T content) {
        return ChaveDescricaoHolder.forChaveContent(chave, content);
    }

    static <I extends Serializable, T extends ChaveDescricao<I>> ChaveDescricao<I> forChaveContent(I chave,
                                                                                                   Optional<T> content) {
        return ChaveDescricaoHolder.forChaveContent(chave, content);
    }

    static <I extends Serializable, T extends ChaveDescricao<I>> ChaveDescricao<I> forContent(T content) {
        return ChaveDescricaoHolder.forContent(content);
    }

    static <I extends Serializable> ChaveDescricao<I> forChaveDescricao(I chave, String descricao) {
        return ChaveDescricaoHolder.forChaveDescricao(chave, descricao);
    }

    static <I extends Number> ChaveDescricao<I> forChaveDescricao(ChaveDescricao<I> chaveDescricao) {
        return ChaveDescricaoHolder.forChaveDescricao(chaveDescricao);
    }

    T getChave();

    String getDescricao();

    default Boolean isPresent() {
        return getDescricao() != null;
    }

    default String toStringChaveDescricao() {
        return this.getChave() != null ? this.getChave() + " - " + this.getDescricao() : this.getDescricao();
    }

}
