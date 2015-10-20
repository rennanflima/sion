/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.mbs;

import br.ufac.sion.dao.ContaBancariaFacadeLocal;
import br.ufac.sion.dao.EmpresaFacadeLocal;
import br.ufac.sion.model.BancosSuportados;
import br.ufac.sion.model.ContaBancaria;
import br.ufac.sion.model.Empresa;
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
public class CadastroContaBean implements Serializable {

    @EJB
    private EmpresaFacadeLocal empresaFacade;

    @EJB
    private ContaBancariaFacadeLocal contaBancariaBancariaFacade;

    private ContaBancaria contaBancaria;

    private List<Empresa> empresas = new ArrayList<>();

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.empresas = empresaFacade.findAll();
        }
    }

    public CadastroContaBean() {
        limpar();
    }

    public ContaBancaria getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(ContaBancaria contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void salvar() {
        try {
            this.contaBancariaBancariaFacade.save(contaBancaria);
            FacesUtil.addSuccessMessage("Conta salva com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar a conta: " + e.getMessage());
        }
    }

    public BancosSuportados[] getBancos() {
        return BancosSuportados.values();
    }

    public void limpar() {
        this.contaBancaria = new ContaBancaria();
    }

    public boolean isEditando() {
        return this.contaBancaria.getId() != null;
    }
}
