/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.FuncionarioFacadeLocal;
import br.ufac.sion.model.Funcionario;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class PesquisaFuncionarioBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;

    private List<Funcionario> funcionarios = new ArrayList<>();

    private Funcionario funcionarioSelecionadoParaExcluir;

    @PostConstruct
    public void inicializar() {
        this.funcionarios = funcionarioFacade.findAll();
    }

    public PesquisaFuncionarioBean() {
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Funcionario getFuncionarioSelecionadoParaExcluir() {
        return funcionarioSelecionadoParaExcluir;
    }

    public void setFuncionarioSelecionadoParaExcluir(Funcionario funcionarioSelecionadoParaExcluir) {
        this.funcionarioSelecionadoParaExcluir = funcionarioSelecionadoParaExcluir;
    }

    public void excluir() {
        try {
            funcionarioFacade.remove(funcionarioSelecionadoParaExcluir);
            FacesUtil.addSuccessMessage("O " + funcionarioSelecionadoParaExcluir.getUsuario().getNome() + " foi excluído com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

}
