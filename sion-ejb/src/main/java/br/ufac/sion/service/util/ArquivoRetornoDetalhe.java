/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service.util;

import br.ufac.sion.model.Inscricao;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rennan.lima
 */
public class ArquivoRetornoDetalhe {

    private LocalDate dataCredito;
    private Integer quantidadeDeTitulosEmCobranca;
    private BigDecimal valorTotalEmCobrancas;
    private Integer totalTitulosPagos;
    private List<Inscricao> incricoesConfirmadas = new ArrayList<>();

    public LocalDate getDataCredito() {
        return dataCredito;
    }

    public void setDataCredito(LocalDate dataCredito) {
        this.dataCredito = dataCredito;
    }

    public Integer getQuantidadeDeTitulosEmCobranca() {
        return quantidadeDeTitulosEmCobranca;
    }

    public void setQuantidadeDeTitulosEmCobranca(Integer quantidadeDeTitulosEmCobranca) {
        this.quantidadeDeTitulosEmCobranca = quantidadeDeTitulosEmCobranca;
    }

    public BigDecimal getValorTotalEmCobrancas() {
        return valorTotalEmCobrancas;
    }

    public void setValorTotalEmCobrancas(BigDecimal valorTotalEmCobrancas) {
        this.valorTotalEmCobrancas = valorTotalEmCobrancas;
    }

    public Integer getTotalTitulosPagos() {
        return totalTitulosPagos;
    }

    public void setTotalTitulosPagos(Integer totalTitulosPagos) {
        this.totalTitulosPagos = totalTitulosPagos;
    }

    public List<Inscricao> getIncricoesConfirmadas() {
        return incricoesConfirmadas;
    }

    public void setIncricoesConfirmadas(List<Inscricao> incricoesConfirmadas) {
        this.incricoesConfirmadas = incricoesConfirmadas;
    }
    
    
}
