/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.SetorFacadeLocal;
import br.ufac.sion.model.Setor;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class CadastroSetorBean implements Serializable {

    private static Log log = LogFactory.getLog(CadastroSetorBean.class);

    @EJB
    private SetorFacadeLocal setorFacade;

    private Setor setor;

    public CadastroSetorBean() {

    }

    @PostConstruct
    public void inicializar() {
        limpar();
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public void salvar() {
        try {
            this.setorFacade.save(setor);
            FacesUtil.addSuccessMessage("Setor salvo com sucesso!");
            limpar();
        } catch (Exception e) {
            log.error("Erro de sistema (sion-web): " + e.getMessage(), e);
            FacesUtil.addErrorMessage("Erro ao salvar o setor: " + e.getMessage());
        }
    }

    public void limpar() {
        setor = new Setor();
    }

    public boolean isEditando() {
        return this.setor.getId() != null;
    }
}
