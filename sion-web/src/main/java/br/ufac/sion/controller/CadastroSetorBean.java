/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CargoFacadeLocal;
import br.ufac.sion.dao.SetorFacadeLocal;
import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.Setor;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private CargoFacadeLocal cargoFacade;

    @EJB
    private SetorFacadeLocal setorFacade;

    private List<Cargo> cargos = new ArrayList<>();
    
    private boolean marcarTodos;

    private Setor setor;

    public CadastroSetorBean() {
        limpar();
    }

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.cargos = cargoFacade.findAll();
        }
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public boolean isMarcarTodos() {
        if (setor.getCargos().size() < cargos.size()) {
            marcarTodos = false;
        }
        return marcarTodos;
    }

    public void setMarcarTodos(boolean marcarTodos) {
        this.marcarTodos = marcarTodos;
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

    public boolean isEditando(){
        return this.setor != null;
    }
    
    public void selecionarTodos() {
        if (marcarTodos) {
            this.setor.setCargos(cargos);
        } else {
            this.setor.getCargos().clear();
            this.cargos = cargoFacade.findAll();
        }
    }
}
