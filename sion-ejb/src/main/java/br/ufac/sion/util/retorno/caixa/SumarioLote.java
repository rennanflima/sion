/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.retorno.caixa;

import java.math.BigDecimal;
import org.jrimum.texgit.Record;

/**
 *
 * @author rennan.lima
 */
public class SumarioLote {

    private Record registro;

    public SumarioLote(Record registro) {
        if (registro != null) {
            this.registro = registro;
        } else {
            throw new IllegalArgumentException("Registro de sumário do lote não informado.");
        }
    }
    
    public Integer getQuantidadeDeRegistros(){
        return registro.getValue("QtdRegistros");
    }
    
    public Integer getQuantidadeDeTitulosEmCobrancaSimples() {
        return registro.getValue("QuantidadeDeTitulosEmCobrancaSimples");
    }

    public BigDecimal getValorTotalEmCobrancaSimples() {
        return registro.getValue("ValorTotalEmCobrancaSimples");
    }
    
    public Integer getQuantidadeDeTitulosEmCobrancaCaucionada() {
        return registro.getValue("QuantidadeDeTitulosEmCobrancaCaucionada");
    }

    public BigDecimal getValorTotalEmCobrancaCaucionada() {
        return registro.getValue("ValorTotalEmCobrancaCaucionada");
    }
    
    public Integer getQuantidadeDeTitulosEmCobrancaDescontada() {
        return registro.getValue("QuantidadeDeTitulosEmCobrancaDescontada");
    }

    public BigDecimal getValorTotalEmCobrancaDescontada() {
        return registro.getValue("ValorTotalEmCobrancaDescontada");
    }
}
