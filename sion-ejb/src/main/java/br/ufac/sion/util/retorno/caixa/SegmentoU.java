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
public class SegmentoU {

    public static final Integer LIQUIDACAO = 6;

    private Record registro;

    public SegmentoU(Record registro) {
        if (registro != null) {
            this.registro = registro;
        } else {
            throw new IllegalArgumentException("Registro do segmento U não foi informado.");
        }
    }

    public Integer getCodigoMovimento() {
        return registro.getValue("CodigoMovimento");
    }

    public BigDecimal getValorMultaJuros() {
        return registro.getValue("Valor-MultaJuros");
    }

    public BigDecimal getValorDesconto() {
        return registro.getValue("Valor-Desconto");
    }

    public BigDecimal getValorAbatimento() {
        return registro.getValue("Valor-Abatimento");
    }

    public BigDecimal getValorIOF() {
        return registro.getValue("Valor-IOF");
    }

    public BigDecimal getValorPago() {
        return registro.getValue("Valor-Pago");
    }

    public BigDecimal getValorLiquidado() {
        return registro.getValue("Valor-LiquidoCreditado");
    }

    public BigDecimal getValorOutrasDespesas() {
        return registro.getValue("Valor-OutrasDespesas");
    }

    public BigDecimal getValorOutrosCreditos() {
        return registro.getValue("Valor-OutrosCreditos");
    }

    public Date getDataOcorrencia() {
        return registro.getValue("Data-Ocorrencia");
    }

    public Date getDataEfetivacao() {
        return registro.getValue("Data-Efetivacao");
    }
}
