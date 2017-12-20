/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.enuns;

/**
 *
 * @author Rennan
 */
public enum BracoDominante {

    DESTRO("Destro"),
    CANHOTO("Canhoto");

    private String descricao;

    BracoDominante(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
