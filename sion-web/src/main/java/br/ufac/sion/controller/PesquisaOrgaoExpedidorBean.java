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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author rennan.lima
 */
@Named
@ViewScoped
public class PesquisaOrgaoExpedidorBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private OrgaoExpedidorFacadeLocal orgaoExpedidorFacade;
    
    private List<OrgaoExpedidor> orgaosExpedidores = new ArrayList<>();
    
    private OrgaoExpedidor orgaoExpedidorSelecionadoParaExcluir;

    public PesquisaOrgaoExpedidorBean() {
    }
    
    @PostConstruct
    public void inicializar() {
        this.orgaosExpedidores = orgaoExpedidorFacade.findAll();
    }

    public List<OrgaoExpedidor> getOrgaosExpedidores() {
        return orgaosExpedidores;
    }

    public void setOrgaosExpedidores(List<OrgaoExpedidor> orgaosExpedidores) {
        this.orgaosExpedidores = orgaosExpedidores;
    }

    public OrgaoExpedidor getOrgaoExpedidorSelecionadoParaExcluir() {
        return orgaoExpedidorSelecionadoParaExcluir;
    }

    public void setOrgaoExpedidorSelecionadoParaExcluir(OrgaoExpedidor orgaoExpedidorSelecionadoParaExcluir) {
        this.orgaoExpedidorSelecionadoParaExcluir = orgaoExpedidorSelecionadoParaExcluir;
    }
    
    public void excluir() {
        try {
            orgaoExpedidorFacade.remove(orgaoExpedidorSelecionadoParaExcluir);
            FacesUtil.addSuccessMessage("O órgão expedidor '" + orgaoExpedidorSelecionadoParaExcluir.getSigla() + "' foi excluído com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
}
