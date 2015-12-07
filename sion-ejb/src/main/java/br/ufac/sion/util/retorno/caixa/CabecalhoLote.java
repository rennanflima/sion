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
public class CabecalhoLote {

    private Record registro;

    public CabecalhoLote(Record registro) {
        if (registro != null) {
            this.registro = registro;
        } else {
            throw new IllegalArgumentException("Registro de cabeçalho do lote não informado.");
        }
    }
    
    public Integer getNumeroRetorno(){
        return registro.getValue("Retorno-Numero");
    }
    
    public String getMensagem1(){
        return registro.getValue("Mensagem1");
    }
    
    public String getMensagem2(){
        return registro.getValue("Mensagem2");
    }
    
    public Date getDataDaGravacao(){
        return registro.getValue("Retorno-DataGravacao");
    }
    
    public Date getDataDoCredito(){
        return registro.getValue("Retorno-DataCredito");
    }
}
