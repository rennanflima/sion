/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 *
 * @author Rennan
 */
@Embeddable
public class Insencao implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean requerInsencao = false;
    private boolean insencaoConfirmada = false;
    private String motivoNegacao;
    private PercentualInsencao percentualInsencao;

    @Column(name = "requer_insencao")
    public boolean isRequerInsencao() {
        return requerInsencao;
    }

    public void setRequerInsencao(boolean requerInsencao) {
        this.requerInsencao = requerInsencao;
    }

    @Column(name = "insencao_confirmada")
    public boolean isInsencaoConfirmada() {
        return insencaoConfirmada;
    }

    public void setInsencaoConfirmada(boolean insencaoConfirmada) {
        this.insencaoConfirmada = insencaoConfirmada;
    }

    @Column(name = "motivo_negacao")
    public String getMotivoNegacao() {
        return motivoNegacao;
    }

    public void setMotivoNegacao(String motivoNegacao) {
        this.motivoNegacao = motivoNegacao;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "percentual_insencao")
    public PercentualInsencao getPercentualInsencao() {
        return percentualInsencao;
    }

    public void setPercentualInsencao(PercentualInsencao percentualInsencao) {
        this.percentualInsencao = percentualInsencao;
    }

}
