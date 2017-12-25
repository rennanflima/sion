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
public enum StatusConcurso {
    
    CORFIRMACAO_PENDENTE("Confirmação pendente"),
    AUTORIZADO("Autorizado"), 
    INSCRICOES_ABERTAS("Inscrições abertas"), 
    INSCRICOES_ENCERRADAS("Inscrições encerradas"),
    EM_ANDAMENTO("Em andamento"), 
    ENCERRADO("Encerrado");

    private String descricao;

    StatusConcurso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
