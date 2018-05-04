/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.OrgaoExpedidorFacadeLocal;
import br.ufac.sion.model.OrgaoExpedidor;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author rennan.lima
 */
@Named
@ViewScoped
public class CadastroOrgaoExpedidorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private OrgaoExpedidorFacadeLocal orgaoExpedidorFacade;

    private OrgaoExpedidor orgaoExpedidor = new OrgaoExpedidor();

    @PostConstruct
    public void inicializar() {
        limpar();
    }

    public CadastroOrgaoExpedidorBean() {
    }

    public OrgaoExpedidor getOrgaoExpedidor() {
        return orgaoExpedidor;
    }

    public void setOrgaoExpedidor(OrgaoExpedidor orgaoExpedidor) {
        this.orgaoExpedidor = orgaoExpedidor;
    }

    public void salvar() {
        try {
            this.orgaoExpedidorFacade.save(orgaoExpedidor);
            FacesUtil.addSuccessMessage("Orgão Expedidor salvo com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar o orgão expedidor: " + e.getMessage());
        }
    }

    public void limpar() {
        this.orgaoExpedidor = new OrgaoExpedidor();
    }

    public boolean isEditando(){
        return this.orgaoExpedidor.getId() != null;
    }
}
