/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.exception.ArquivoRetornoException;
import br.ufac.sion.service.ArquivoRetornoService;
import br.ufac.sion.service.util.ArquivoRetornoDetalhe;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author rennan.lima
 */
@ManagedBean
@RequestScoped
public class ArquivoRetornoBean {

    @EJB
    private ArquivoRetornoService arquivoRetornoService;

    private ArquivoRetornoDetalhe arquivoRetornoDetalhe;

    private UploadedFile arquivo;

    public ArquivoRetornoBean() {
        this.arquivoRetornoDetalhe = null;
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
        try {
            if (arquivo != null) {
                System.out.println("Arquivo: " + arquivo.getFileName());
                this.arquivoRetornoDetalhe = arquivoRetornoService.carregar(arquivo.getFileName(), arquivo.getInputstream());
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

}
