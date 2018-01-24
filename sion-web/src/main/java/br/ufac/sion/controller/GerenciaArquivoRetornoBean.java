/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.exception.ArquivoRetornoException;
import br.ufac.sion.model.enuns.BancosSuportados;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.service.retorno.ArquivoRetornoBradescoService;
import br.ufac.sion.service.retorno.ArquivoRetornoCaixaService;
import br.ufac.sion.service.util.ArquivoRetornoDetalhe;
import br.ufac.sion.util.jsf.FacesUtil;
import br.ufac.sion.util.jsf.Sion;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rennan.lima
 */
@Named
@RequestScoped
public class GerenciaArquivoRetornoBean {

    @EJB
    private ArquivoRetornoBradescoService arquivoRetornoBradescoService;

    @EJB
    private ArquivoRetornoCaixaService arquivoRetornoCaixaService;

    @Inject  @Sion
    HttpServletRequest request;
    
    private ArquivoRetornoDetalhe arquivoRetornoDetalhe;

    private Concurso concurso;

    private UploadedFile arquivo;

    public GerenciaArquivoRetornoBean() {
        this.arquivoRetornoDetalhe = null;
        recuperaConcursoSessao();
    }

    public ArquivoRetornoDetalhe getArquivoRetornoDetalhe() {
        return arquivoRetornoDetalhe;
    }

    public UploadedFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(UploadedFile arquivo) {
        this.arquivo = arquivo;
    }

    public void upload() {
        BancosSuportados banco = concurso.getContaBancaria().getBanco();
        try {
            if (arquivo != null) {
                System.out.println("Arquivo: " + arquivo.getFileName());
                if (banco.equals(BancosSuportados.BANCO_BRADESCO)) {
                    this.arquivoRetornoDetalhe = this.arquivoRetornoBradescoService.carregar(arquivo.getFileName(), arquivo.getInputstream());
                } else if (banco.equals(BancosSuportados.CAIXA_ECONOMICA_FEDERAL)) {
                    this.arquivoRetornoDetalhe = this.arquivoRetornoCaixaService.carregar(arquivo.getFileName(), arquivo.getInputstream());
                }
                // this.arquivoRetornoDetalhe = processaArquivoRetorno.carregar(concurso, arquivo.getFileName(), arquivo.getInputstream());
                FacesUtil.addSuccessMessage("Inscrições confirmadas com sucesso!");
            } else {
                throw new ArquivoRetornoException("Arquivo não enviado");
            }
        } catch (ArquivoRetornoException | IOException e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

    public boolean isDetalhe() {
        return this.arquivoRetornoDetalhe != null;
    }

    public void recuperaConcursoSessao() {
        HttpSession session = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }

}
