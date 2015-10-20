/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.mbs;

import br.ufac.sion.dao.ContaBancariaFacadeLocal;
import br.ufac.sion.model.ContaBancaria;
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
public class PesquisaContaBean implements Serializable {

    @EJB
    private ContaBancariaFacadeLocal contaBancariaFacade;

    private List<ContaBancaria> contas = new ArrayList<>();

    private ContaBancaria contaBancariaParaExcluir;

    @PostConstruct
    public void inicializar() {
        this.contas = contaBancariaFacade.findAll();
    }

    public PesquisaContaBean() {
    }

    public List<ContaBancaria> getContas() {
        return contas;
    }

    public ContaBancaria getContaBancariaParaExcluir() {
        return contaBancariaParaExcluir;
    }

    public void setContaBancariaParaExcluir(ContaBancaria contaBancariaParaExcluir) {
        this.contaBancariaParaExcluir = contaBancariaParaExcluir;
    }

    public void excluir() {
        try {
            contaBancariaFacade.remove(contaBancariaParaExcluir);
            FacesUtil.addSuccessMessage("A conta '" + contaBancariaParaExcluir.getDescricao() + "' foi excluída com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
}
