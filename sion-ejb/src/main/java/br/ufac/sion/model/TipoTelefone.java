/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

/**
 *
 * @author Rennan
 */
public enum TipoTelefone {

    RESIDENCIAL("Residencial"),
    TRABALHO("Trabalho"),
    CELULAR("Celular"),
    OUTRO("Outro");

    private String descricao;

    TipoTelefone(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
