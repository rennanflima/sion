/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.model.Boleto;
import br.ufac.sion.model.Empresa;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.service.BoletoService;
import br.ufac.sion.service.InscricaoService;
import br.ufac.sion.util.NegocioException;
import br.ufac.sion.util.boleto.EmissorBoleto;
import br.ufac.sion.util.jsf.FacesProducer;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.OutputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class ComprovanteInscricaoBean implements Serializable {

    @EJB
    private BoletoService boletoService;

    @EJB
    private InscricaoService inscricaoService;

    @EJB
    private EmissorBoleto bopepoEmissorBoleto;

    private Inscricao inscricao;
    private Empresa empresa;

    private String atendimentoEspecial;

    private String candidatoPNE;

    private Boleto cobranca;

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
        this.cobranca = new Boleto();
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

    public void emitir() {
        Empresa cedente = inscricao.getCargoConcurso().getConcurso().getContaBancaria().getCedente();
        cobranca.setDataVencimento(inscricao.getCargoConcurso().getConcurso().getDataVencimentoBoleto());
        cobranca.setValor(inscricao.getCargoConcurso().getValor());
        cobranca.setSacado(inscricao);
        inscricao.setBoleto(cobranca);
        try {
            cobranca = boletoService.salvar(cobranca);

            byte[] pdf = this.bopepoEmissorBoleto.gerarBoleto(cedente, cobranca);
            enviarBoleto(pdf);
        } catch (NegocioException ex) {
            System.out.println(ex.getMessage());
            //FacesUtil.addErrorMessage(ex.getMessage());
        }

    }

    private void enviarBoleto(byte[] pdf) {
        HttpServletResponse response = FacesProducer.getHttpServletResponse();
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=boleto" + cobranca.getSacado().getNumero() + ".pdf");

            OutputStream output = response.getOutputStream();
            output.write(pdf);
            response.flushBuffer();
        } catch (Exception e) {
            throw new RuntimeException("Erro gerando boleto", e);
        }

        FacesContext.getCurrentInstance().responseComplete();
    }

}
