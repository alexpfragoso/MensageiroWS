package com.alexpfragosoatgmail.mensageirows.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Alex Fragoso on 27/03/2018.
 */

public class Mensagem implements Comparable<Mensagem>,Serializable {

    private String id;
    @SerializedName("origem_id")
    private String origemId;
    @SerializedName("destino_id")
    private String destinoId;
    private String assunto;
    private String corpo;
    private Contato origem;
    private Contato destino;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigemId() {
        return origemId;
    }

    public void setOrigemId(String origemId) {
        this.origemId = origemId;
    }

    public String getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(String destinoId) {
        this.destinoId = destinoId;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public Contato getOrigem() {
        return origem;
    }

    public void setOrigem(Contato origem) {
        this.origem = origem;
    }

    public Contato getDestino() {
        return destino;
    }

    public void setDestino(Contato destino) {
        this.destino = destino;
    }

    @Override
    public int compareTo(@NonNull Mensagem mensagem) {
        return this.getId().compareTo(mensagem.getId());

    }
}
