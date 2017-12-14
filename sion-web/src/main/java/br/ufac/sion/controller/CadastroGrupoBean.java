/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.GrupoFacadeLocal;
import br.ufac.sion.dao.PermissaoFacadeLocal;
import br.ufac.sion.model.Grupo;
import br.ufac.sion.model.Permissao;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DualListModel;

/**
 *
 * @author rennan.lima
 */
@ManagedBean
@ViewScoped
public class CadastroGrupoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private GrupoFacadeLocal grupoFacadeLocal;

    @EJB
    private PermissaoFacadeLocal permissaoFacade;
    
    private Grupo grupo;

    private DualListModel<Permissao> dualListModelPermissoes;
    
    @PostConstruct
    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            List<Permissao> todasPermissoes = this.permissaoFacade.findAll();
            List<Permissao> permissoesGrupo = new ArrayList<>();
            if(isEditando()){
                Grupo g = this.grupoFacadeLocal.findGrupoWithPermissoes(this.grupo.getId());
                if(g != null){
                    permissoesGrupo = g.getPermissoes();
                    for (Permissao p : permissoesGrupo) {
                        if(todasPermissoes.contains(p)){
                            todasPermissoes.remove(p);
                        }
                    }
                }
            }
            this.dualListModelPermissoes = new DualListModel<>(todasPermissoes, permissoesGrupo);
        }
        
    }

    public CadastroGrupoBean() {
        limpar();
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public DualListModel<Permissao> getDualListModelPermissoes() {
        return dualListModelPermissoes;
    }

    public void setDualListModelPermissoes(DualListModel<Permissao> dualListModelPermissoes) {
        this.dualListModelPermissoes = dualListModelPermissoes;
    }

    public void salvar() {
        try {
            this.grupo.setPermissoes(dualListModelPermissoes.getTarget());
            this.grupoFacadeLocal.save(grupo);
            FacesUtil.addSuccessMessage("Grupo salvo com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar a grupo: " + e.getMessage());
        }
    }

    public void limpar() {
        this.grupo = new Grupo();
    }

    public boolean isEditando(){
        return this.grupo.getId() != null;
    }
}
