/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.exception.ArquivoRetornoException;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.enuns.BancosSuportados;
import br.ufac.sion.service.retorno.ArquivoRetornoBradescoService;
import br.ufac.sion.service.retorno.ArquivoRetornoCaixaService;
import br.ufac.sion.service.util.ArquivoRetornoDetalhe;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Rennan
 */
@Named
public class ArquivoRetornoBean implements Serializable {

    @EJB
    private ArquivoRetornoBradescoService arquivoRetornoBradescoService;

    @EJB
    private ArquivoRetornoCaixaService arquivoRetornoCaixaService;

    private ArquivoRetornoDetalhe arquivoRetornoDetalhe;

    private UploadedFile arquivo;

    private BancosSuportados banco;

    public ArquivoRetornoBean() {
        this.arquivoRetornoDetalhe = null;
    }

    public BancosSuportados[] getBancos() {
        return BancosSuportados.values();
    }

    public UploadedFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(UploadedFile arquivo) {
        this.arquivo = arquivo;
    }

    public BancosSuportados getBanco() {
        return banco;
    }

    public void setBanco(BancosSuportados banco) {
        this.banco = banco;
    }

    public ArquivoRetornoDetalhe getArquivoRetornoDetalhe() {
        return arquivoRetornoDetalhe;
    }

    public void upload() {
        try {
            if (arquivo != null) {
                System.out.println("Arquivo: " + arquivo.getFileName());
                switch (this.banco) {
                    case BANCO_BRADESCO:
                        this.arquivoRetornoDetalhe = this.arquivoRetornoBradescoService.carregar(arquivo.getFileName(), arquivo.getInputstream());
                        break;
                    case CAIXA_ECONOMICA_FEDERAL:
                        this.arquivoRetornoDetalhe = this.arquivoRetornoCaixaService.carregar(arquivo.getFileName(), arquivo.getInputstream());
                        break;
                    default:
                        throw new ArquivoRetornoException("Banco não suportado!");
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
}
