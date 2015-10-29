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

    @Column(name = "requer_insencao")
    private boolean requerInsencao = false;
    @Column(name = "insencao_confirmada")
    private boolean insencaoConfirmada = false;
    @Column(name = "motivo_negacao")
    private String motivoNegacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "percentual_insencao")
    private PercentualInsencao percentualInsencao;

    public boolean isRequerInsencao() {
        return requerInsencao;
    }

    public void setRequerInsencao(boolean requerInsencao) {
        this.requerInsencao = requerInsencao;
    }

    public boolean isInsencaoConfirmada() {
        return insencaoConfirmada;
    }

    public void setInsencaoConfirmada(boolean insencaoConfirmada) {
        this.insencaoConfirmada = insencaoConfirmada;
    }

    public String getMotivoNegacao() {
        return motivoNegacao;
    }

    public void setMotivoNegacao(String motivoNegacao) {
        this.motivoNegacao = motivoNegacao;
    }

    public PercentualInsencao getPercentualInsencao() {
        return percentualInsencao;
    }

    public void setPercentualInsencao(PercentualInsencao percentualInsencao) {
        this.percentualInsencao = percentualInsencao;
    }

}
