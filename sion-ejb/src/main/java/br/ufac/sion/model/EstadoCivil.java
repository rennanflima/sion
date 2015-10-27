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
public enum EstadoCivil {
    CASADO("Casado(a)"),
    DIVORCIADO("Divorciado(a)"),
    SEPARADO("Separado(a)"),
    SOLTEIRO("Solteiro(a)"),
    VIUVO("Viúvo(a)"),
    OUTROS("Outro");
    
    private String descricao;

    EstadoCivil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
    
    
}
