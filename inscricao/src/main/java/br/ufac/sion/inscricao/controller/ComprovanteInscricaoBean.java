/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.model.Empresa;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class ComprovanteInscricaoBean implements Serializable {

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    private Inscricao inscricao;
    private Empresa empresa;

    private String atendimentoEspecial;

    private String candidatoPNE;

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            empresa = inscricao.getCargoConcurso().getConcurso().getContaBancaria().getCedente();
        }
    }

    public ComprovanteInscricaoBean() {
        limpar();
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    private void limpar() {
        this.inscricao = new Inscricao();
        this.empresa = new Empresa();
    }

    public String getAtendimentoEspecial() {
        if (inscricao.getNecessidadeEspecial().isNecessitaAtendimento()) {
            atendimentoEspecial = "Sim. " + inscricao.getNecessidadeEspecial().getQualAtendimento();
        }
        atendimentoEspecial = "Não";

        return atendimentoEspecial;
    }

    public String getCandidatoPNE() {
        if (inscricao.getNecessidadeEspecial().isPortador()) {
            this.candidatoPNE = "Sim. " + inscricao.getNecessidadeEspecial().getQualNecessidadeEspecial();
        }
        candidatoPNE = "Não";

        return candidatoPNE;
    }
}
