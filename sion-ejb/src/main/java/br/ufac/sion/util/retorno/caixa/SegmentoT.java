/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.retorno.caixa;

import java.math.BigDecimal;
import java.util.Date;
import org.jrimum.texgit.Record;

/**
 *
 * @author rennan.lima
 */
public class SegmentoT {

    private Record registro;
    private SegmentoU segmentoU;

    public SegmentoT(Record registro) {
        if (registro != null) {
            this.registro = registro;
            this.segmentoU = new SegmentoU(registro.getInnerRecords().get(0));
        } else {
            throw new IllegalArgumentException("Registro de transação não foi informado.");
        }
    }


    public String getNossoNumero() {
        return registro.getValue("NossoNumeroComDigito");
    }
    
    public Integer getCarteira() {
        return registro.getValue("Carteira");
    }

    public String getNumeroDoDocumento() {
        return registro.getValue("NumDocumento");
    }

    public Date getDataDoVencimento() {
        return registro.getValue("Vencimento");
    }

    public BigDecimal getValor() {
        return registro.getValue("Valor");
    }

    public SegmentoU getSegmentoU() {
        return segmentoU;
    }

}
