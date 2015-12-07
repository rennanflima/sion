/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.retorno.caixa;

import org.jrimum.texgit.Record;

/**
 *
 * @author rennan.lima
 */
public class SumarioArquivo {

    private Record registro;

    public SumarioArquivo(Record registro) {
        if (registro != null) {
            this.registro = registro;
        } else {
            throw new IllegalArgumentException("Registro de sumário do arquivo não informado.");
        }
    }
    
    public Integer getQuantidadeDeLotes(){
        return registro.getValue("QtdLotes");
    }
    
    public Integer getQuantidadeDeRegistros(){
        return registro.getValue("QtdRegistros");
    }
    
    public Integer getQuantidadeDeContas(){
        return registro.getValue("QtdContas");
    }
}
