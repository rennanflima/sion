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
public enum BancosSuportados {

    BANCO_DO_BRASIL("Banco do Brasil"),
    CAIXA_ECONOMICA_FEDERAL("Caixa Econômica Federal"),
    BANCO_BRADESCO("Banco Bradesco");

    private String descricao;

    BancosSuportados(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
