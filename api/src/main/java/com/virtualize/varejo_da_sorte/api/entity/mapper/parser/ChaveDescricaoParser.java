package com.virtualize.varejo_da_sorte.api.entity.mapper.parser;

import com.virtualize.varejo_da_sorte.api.entity.enums.ChaveDescricao;
import com.virtualize.varejo_da_sorte.api.entity.utils.ChaveDescricaoHolder;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

public abstract class ChaveDescricaoParser<T extends Serializable, D extends ChaveDescricao<T>> {

    public static <T extends Serializable, D extends ChaveDescricao<T>> ChaveDescricaoHolder<T, D> chaveToObject(T chave,
                                                                                                                 Function<T, Optional<D>> findByChave) {
        ChaveDescricaoHolder<T, D> to = null;
        if (chave != null) {
            Optional<D> optional = findByChave.apply(chave);
            to = ChaveDescricaoHolder.forChaveContent(chave, optional);
        }
        return to;
    }

    protected abstract Optional<D> findByChave(T chave);

    protected ChaveDescricaoHolder<T, D> chaveToObject(T chave) {
        ChaveDescricaoHolder<T, D> to = null;
        if (chave != null) {
            Optional<D> optional = findByChave(chave);
            to = ChaveDescricaoHolder.forChaveContent(chave, optional);
        }
        return to;
    }

}
