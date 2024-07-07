package com.virtualize.varejodasorte.api.entity.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.virtualize.varejodasorte.api.entity.enums.ChaveDescricao;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public class ChaveDescricaoHolder<I extends Serializable, T extends ChaveDescricao<I>> implements ChaveDescricao<I> {

    private final I chave;
    private final Optional<T> content;
    private final String descricao;
    private final Object detail;

    private ChaveDescricaoHolder(I chave) {
        this.chave = chave;
        this.content = Optional.empty();
        this.descricao = null;
        this.detail = "";
    }

    private ChaveDescricaoHolder(I chave, T content) {
        this.chave = chave;
        this.content = Optional.ofNullable(content);
        this.descricao = content.getDescricao();
        this.detail = "";
    }

    private ChaveDescricaoHolder(I chave, Optional<T> content) {
        this.chave = chave;
        this.content = content;
        this.descricao = content.isPresent() ? content.get().getDescricao() : null;
        this.detail = "";
    }

    private ChaveDescricaoHolder(I chave, String descricao) {
        this.chave = chave;
        this.content = Optional.empty();
        this.descricao = descricao;
        this.detail = "";
    }

    private ChaveDescricaoHolder(I chave, String descricao, Object detail) {
        this.chave = chave;
        this.content = Optional.empty();
        this.descricao = descricao;
        this.detail = detail;
    }

    public static <I extends Serializable, T extends ChaveDescricao<I>> ChaveDescricaoHolder<I, T> forChave(I chave) {
        return new ChaveDescricaoHolder<>(chave);
    }

    public static <I extends Serializable, T extends ChaveDescricao<I>> ChaveDescricaoHolder<I, T> forChaveContent(I chave, T content) {
        return new ChaveDescricaoHolder<>(chave, content);
    }

    public static <I extends Serializable, T extends ChaveDescricao<I>> ChaveDescricaoHolder<I, T> forChaveContent(I chave, Optional<T> content) {
        return new ChaveDescricaoHolder<>(chave, content);
    }

    public static <I extends Serializable, T extends ChaveDescricao<I>> ChaveDescricaoHolder<I, T> forContent(T content) {
        return new ChaveDescricaoHolder<>(content.getChave(), content);
    }

    public static <I extends Serializable, T extends ChaveDescricao<I>> ChaveDescricaoHolder<I, T> forChaveDescricao(I chave, String descricao) {
        return new ChaveDescricaoHolder<>(chave, descricao);
    }

    public static <I extends Serializable, T extends ChaveDescricao<I>> ChaveDescricaoHolder<I, T> forChaveDescricaoDetail(I chave, String descricao, Object detail) {
        return new ChaveDescricaoHolder<>(chave, descricao, detail);
    }

    public static <I extends Serializable, T extends ChaveDescricao<I>> ChaveDescricaoHolder<I, T> forChaveDescricao(ChaveDescricao<I> chaveDescricao) {
        return new ChaveDescricaoHolder<>(chaveDescricao.getChave(), chaveDescricao.getDescricao());
    }

    @Override
    public I getChave() {
        return this.chave;
    }

    @Override
    public String getDescricao() {
        if (this.content.isPresent()) {
            return this.content.get().getDescricao();
        }
        return this.descricao;
    }

    @Override
    public Boolean isPresent() {
        if (this.content.isPresent()) {
            return this.content.isPresent();
        } else {
            return getDescricao() != null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || obj.getClass() != this.getClass())
            return false;

        ChaveDescricaoHolder<I, T> chaveDescricao = (ChaveDescricaoHolder<I, T>) obj;

        return Objects.equals(this.getChave(), chaveDescricao.getChave()) &&
                Objects.equals(this.getDescricao(), chaveDescricao.getDescricao());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getChave()).append(this.getDescricao()).build();
    }
}
