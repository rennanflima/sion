/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import br.ufac.sion.model.enuns.TipoTelefone;
import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Rennan
 */
@Embeddable
public class Telefone implements Serializable {

    private String prefixo;
    private String numero;
    private TipoTelefone tipo;

    public Telefone() {

    }

    public Telefone(String prefixo, String numero, String ramal, TipoTelefone tipo) {
        this.prefixo = prefixo;
        this.numero = numero;
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

    public TipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(TipoTelefone tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "(" + prefixo + ") " + numero + ", Tipo: " + tipo;
    }

}
