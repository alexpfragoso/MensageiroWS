package com.alexpfragosoatgmail.mensageirows.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Alex Fragoso on 27/03/2018.
 */

public class Contato implements Serializable{

    @SerializedName("nome_completo")
    private String nomeCompleto;
    private String apelido;
    private String id;

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
