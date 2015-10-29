/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Rennan
 */
@Embeddable
public class NecessidadeEspecial implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean portador;
    @Column(name = "qual_pne")
    private String qualNecessidadeEspecial;
    @Column(name = "necessita_atendimento")
    private boolean necessitaAtendimento;
    @Column(name = "qual_atendimento")
    private String qualAtendimento;

    public boolean isPortador() {
        return portador;
    }

    public void setPortador(boolean portador) {
        this.portador = portador;
    }

    public String getQualNecessidadeEspecial() {
        return qualNecessidadeEspecial;
    }

    public void setQualNecessidadeEspecial(String qualNecessidadeEspecial) {
        this.qualNecessidadeEspecial = qualNecessidadeEspecial;
    }

    public boolean isNecessitaAtendimento() {
        return necessitaAtendimento;
    }

    public void setNecessitaAtendimento(boolean necessitaAtendimento) {
        this.necessitaAtendimento = necessitaAtendimento;
    }

    public String getQualAtendimento() {
        return qualAtendimento;
    }

    public void setQualAtendimento(String qualAtendimento) {
        this.qualAtendimento = qualAtendimento;
    }

}
