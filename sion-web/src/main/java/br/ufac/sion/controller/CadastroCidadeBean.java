/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CidadeFacadeLocal;
import br.ufac.sion.dao.EstadoFacadeLocal;
import br.ufac.sion.model.Cidade;
import br.ufac.sion.model.Estado;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class CadastroCidadeBean implements Serializable {

    private static Log log = LogFactory.getLog(CadastroCidadeBean.class);

    @EJB
    private CidadeFacadeLocal cidadeFacade;
    
    @EJB
    private EstadoFacadeLocal estadoFacade;

    private Cidade cidade;
    
    private List<Estado> estados = new ArrayList<>();

    public CadastroCidadeBean() {
        limpar();
    }

    @PostConstruct
    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.estados = estadoFacade.findAll();
        }
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public void salvar() {
        try {
            this.cidadeFacade.save(cidade);
            FacesUtil.addSuccessMessage("Cidade salva com sucesso!");
            limpar();
        } catch (Exception e) {
            log.error("Erro de sistema (sion-web): " + e.getMessage(), e);
            FacesUtil.addErrorMessage("Erro ao salvar a cidade: " + e.getMessage());
        }
    }

    public void limpar() {
        cidade = new Cidade();
    }

    public boolean isEditando() {
        return this.cidade.getId() != null;
    }
}
