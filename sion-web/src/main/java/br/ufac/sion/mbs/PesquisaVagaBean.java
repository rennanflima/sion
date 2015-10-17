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
import java.util.ArrayList;
import java.util.List;
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
public class PesquisaVagaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private VagaFacadeLocal vagaFacade;
    
    private List<Vaga> vagas = new ArrayList<>();
    
    private Vaga vagaSelecionadaParaExcluir;

    public PesquisaVagaBean() {
    }
    
    @PostConstruct
    public void inicializar() {
        this.vagas = vagaFacade.findAll();
    }

    public List<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(List<Vaga> vagas) {
        this.vagas = vagas;
    }

    public Vaga getVagaSelecionadaParaExcluir() {
        return vagaSelecionadaParaExcluir;
    }

    public void setVagaSelecionadaParaExcluir(Vaga vagaSelecionadaParaExcluir) {
        this.vagaSelecionadaParaExcluir = vagaSelecionadaParaExcluir;
    }
    
    public void excluir() {
        try {
            vagaFacade.remove(vagaSelecionadaParaExcluir);
            FacesUtil.addSuccessMessage("A vaga '" + vagaSelecionadaParaExcluir.getTipo() + "' foi excluída com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
}
