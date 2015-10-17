/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.mbs;

import br.ufac.sion.dao.LocalidadeFacadeLocal;
import br.ufac.sion.model.Localidade;
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
public class CadastroLocalidadeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private LocalidadeFacadeLocal localidadeFacade;

    private Localidade localidade;

    @PostConstruct
    public void inicializar() {
        limpar();
    }

    public CadastroLocalidadeBean() {
    }

    public Localidade getLocalidade() {
        return localidade;
    }

    public void setLocalidade(Localidade localidade) {
        this.localidade = localidade;
    }

    public void salvar() {
        try {
            this.localidadeFacade.save(localidade);
            FacesUtil.addSuccessMessage("Localidade salva com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar o localidade: " + e.getMessage());
        }
    }

    public void limpar() {
        this.localidade = new Localidade();
    }

    public boolean isEditando(){
        return this.localidade.getId() != null;
    }
}
