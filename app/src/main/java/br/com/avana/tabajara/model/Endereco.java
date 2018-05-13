package br.com.avana.tabajara.model;

import java.io.Serializable;

public class Endereco extends EnderecoNet implements Serializable{

    private String numero;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}
