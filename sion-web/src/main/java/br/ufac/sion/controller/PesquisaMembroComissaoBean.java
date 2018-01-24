/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.dao.FuncionarioFacadeLocal;
import br.ufac.sion.dao.MembroComissaoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Funcionario;
import br.ufac.sion.model.MembroComissao;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class PesquisaMembroComissaoBean implements Serializable {

    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    @EJB
    private MembroComissaoFacadeLocal membroComissaoFacade;

    private List<MembroComissao> membrosComissao;
    private List<Funcionario> funcionarios;
    private List<Concurso> concursos;

    private MembroComissao filtro;

    private MembroComissao membroComissaoSelecionado;

    public PesquisaMembroComissaoBean() {
        limparFiltro();
    }

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            funcionarios = funcionarioFacade.findAll();
            concursos = concursoFacade.findAll();
        }
    }

    public List<MembroComissao> getMembrosComissao() {
        return membrosComissao;
    }

    public MembroComissao getFiltro() {
        return filtro;
    }

    public void setFiltro(MembroComissao filtro) {
        this.filtro = filtro;
    }

    public MembroComissao getMembroComissaoSelecionado() {
        return membroComissaoSelecionado;
    }

    public void setMembroComissaoSelecionado(MembroComissao membroComissaoSelecionado) {
        this.membroComissaoSelecionado = membroComissaoSelecionado;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<Concurso> getConcursos() {
        return concursos;
    }

    public void pesquisar() {
        this.membrosComissao = membroComissaoFacade.findWithFiltro(filtro);
    }

    public void excluir() {
        try {
            membroComissaoFacade.remove(membroComissaoSelecionado);
            FacesUtil.addSuccessMessage("O membro da comissão '" + membroComissaoSelecionado.getFuncionario().getUsuario().getNome() + "' foi excluído com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }

    public void limparFiltro() {
        this.filtro = new MembroComissao();
    }
}
