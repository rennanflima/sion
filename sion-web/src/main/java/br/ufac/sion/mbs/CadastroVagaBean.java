/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.mbs;

import br.ufac.sion.dao.VagaFacadeLocal;
import br.ufac.sion.model.Vaga;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author rennan.lima
 */
@ManagedBean
@ViewScoped
public class CadastroVagaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private VagaFacadeLocal vagaFacade;

    private Vaga vaga;

    @PostConstruct
    public void inicializar() {
        limpar();
    }

    public CadastroVagaBean() {
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public void salvar() {
        try {
            this.vagaFacade.save(vaga);
            FacesUtil.addSuccessMessage("Vaga salva com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar a vaga: " + e.getMessage());
        }
    }

    public void limpar() {
        this.vaga = new Vaga();
    }

    public boolean isEditando() {
        return this.vaga.getId() != null;
    }
}
