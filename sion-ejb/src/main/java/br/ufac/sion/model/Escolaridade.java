/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

/**
 *
 * @author rennan.lima
 */
public enum Escolaridade {

    NIVEL_FUNDAMENTAL("Nível Fundamental (1º Grau)"),
    NIVEL_MEDIO("Nível Médio (2º Grau)"),
    NIVEL_SUPERIOR_INCOMPLETO("Nível Superior Incompleto"),
    NIVEL_SUPERIOR_COMPLETO("Nível Superior Completo"),
    POS_GRADUACAO("Pós-graduação"),
    MESTRADO("Mestrado"),
    DOUTORADO("Doutorado");

    private String descricao;

    Escolaridade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
