/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.enuns;

/**
 *
 * @author rennan.lima
 */
public enum SituacaoInscricao {
    
    AGUARDANDO_PAGAMENTO("Aguardando pagamento"),
    CONFIRMADA("Confirmada"),
    SUB_JUDICE("Sub judice"),
    CANCELADA("Cancelada");
    
    private String descricao;

    SituacaoInscricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
    
}
