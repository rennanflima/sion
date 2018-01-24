/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.dao.FuncionarioFacadeLocal;
import br.ufac.sion.dao.MembroComissaoFacadeLocal;
import br.ufac.sion.dao.PermissaoComissaoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Funcionario;
import br.ufac.sion.model.MembroComissao;
import br.ufac.sion.model.PermissaoComissao;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class CadastroMembroComissaoBean implements Serializable {

    @EJB
    private PermissaoComissaoFacadeLocal permissaoComissaoFacade;

    @EJB
    private MembroComissaoFacadeLocal membroComissaoFacade;

    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    private MembroComissao membroComissao;
    private DualListModel<PermissaoComissao> dualListModelPermissoes;
    private List<Funcionario> funcionarios;
    private List<Concurso> concursos;

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            funcionarios = funcionarioFacade.findAll();
            concursos = concursoFacade.findAll();
            List<PermissaoComissao> todasPermissoes = this.permissaoComissaoFacade.findAll();
            List<PermissaoComissao> permissoesMembro = new ArrayList<>();
            if (isEditando()) {
                permissoesMembro.addAll(membroComissao.getPermissoes());
                for (PermissaoComissao pc : todasPermissoes) {
                    if (todasPermissoes.contains(pc)) {
                        todasPermissoes.remove(pc);
                    }
                }
            }
            this.dualListModelPermissoes = new DualListModel<>(todasPermissoes, permissoesMembro);
        }
    }

    public CadastroMembroComissaoBean() {
        limpar();
    }

    public MembroComissao getMembroComissao() {
        return membroComissao;
    }

    public void setMembroComissao(MembroComissao membroComissao) {
        this.membroComissao = membroComissao;
    }

    public DualListModel<PermissaoComissao> getDualListModelPermissoes() {
        return dualListModelPermissoes;
    }

    public void setDualListModelPermissoes(DualListModel<PermissaoComissao> dualListModelPermissoes) {
        this.dualListModelPermissoes = dualListModelPermissoes;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<Concurso> getConcursos() {
        return concursos;
    }

    public void salvar(){
        try {
            this.membroComissao.setPermissoes(dualListModelPermissoes.getTarget());
            this.membroComissaoFacade.save(membroComissao);
            FacesUtil.addSuccessMessage("Membro da Comissão salvo com sucesso!");
            limpar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar o membro da comissão: " + e.getMessage());
        }
    }
    
    public void limpar() {
        this.membroComissao = new MembroComissao();
    }

    public boolean isEditando() {
        return this.membroComissao.getId() != null;
    }
}
