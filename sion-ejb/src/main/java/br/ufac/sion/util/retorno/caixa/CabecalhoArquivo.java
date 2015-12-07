/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.retorno.caixa;

import java.util.Date;
import org.jrimum.texgit.Record;

/**
 *
 * @author rennan.lima
 */
public class CabecalhoArquivo {
 
    private Record registro;

    public CabecalhoArquivo(Record registro) {
        if (registro != null) {
            this.registro = registro;
        } else {
            throw new IllegalArgumentException("Registro de cabeçalho do arquivo não informado.");
        }
    }
    
    public String getNomeEmpresa(){
        return registro.getValue("Empresa-Nome");
    }
    
    public String getCNPJEmpresa(){
        return registro.getValue("Empresa-CPRF");
    }
    
    public Date getDataGeracaoArquivo(){
        return registro.getValue("Arquivo-GeracaoData");
    }
}
