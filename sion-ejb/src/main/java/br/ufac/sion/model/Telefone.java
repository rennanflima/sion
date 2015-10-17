/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author Rennan
 */
@Embeddable
public class Telefone {

    private String prefixo;
    private String numero;
    private String ramal;
    private TipoTelefone tipo;

    public Telefone() {

    }

    public Telefone(String prefixo, String numero, String ramal, TipoTelefone tipo) {
        this.prefixo = prefixo;
        this.numero = numero;
        this.ramal = ramal;
        this.tipo = tipo;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    @Enumerated(EnumType.STRING)
    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }

}
