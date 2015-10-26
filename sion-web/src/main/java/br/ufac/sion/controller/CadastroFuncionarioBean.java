/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CargoFacadeLocal;
import br.ufac.sion.dao.GrupoFacadeLocal;
import br.ufac.sion.dao.SetorFacadeLocal;
import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.Funcionario;
import br.ufac.sion.model.Grupo;
import br.ufac.sion.model.Setor;
import br.ufac.sion.service.FuncionarioService;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author rennan.lima
 */
@ManagedBean
@ViewScoped
public class CadastroFuncionarioBean implements Serializable {

    @EJB
    private FuncionarioService funcionarioService;

    @EJB
    private CargoFacadeLocal cargoFacade;

    private static final long serialVersionUID = 1L;

    @EJB
    private SetorFacadeLocal setorFacade;

    @EJB
    private GrupoFacadeLocal grupoFacade;

    private List<Setor> setores = new ArrayList<>();

    private List<Grupo> grupos = new ArrayList<>();

    private List<Cargo> cargos = new ArrayList<>();

    private Funcionario funcionario;

    private Setor setor;

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            setores = setorFacade.findAll();
            grupos = grupoFacade.findAll();

        }
    }

    public CadastroFuncionarioBean() {
        limpar();
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;

        if (funcionario != null) {
            setor = funcionario.getSetor();
        }
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public void salvar() {
        try {
            this.funcionario.setSetor(setor);
            this.funcionarioService.salvar(funcionario);
            FacesUtil.addSuccessMessage("Funcionário salvo com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar o funcionário: " + e.getMessage());
        }
    }

    public void limpar() {
        this.setor = null;
        this.funcionario = new Funcionario();
    }

    public boolean isEditando() {
        return this.funcionario.getId() != null;
    }

}
