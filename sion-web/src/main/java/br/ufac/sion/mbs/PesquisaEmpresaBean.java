/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.mbs;

import br.ufac.sion.dao.EmpresaFacadeLocal;
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
public class PesquisaEmpresaBean implements Serializable {
    
    @EJB
    private EmpresaFacadeLocal empresaFacade;

    private List<Empresa> empresas = new ArrayList<>();
    
    private Empresa empresaParaExcluir;
    
    @PostConstruct
    public void inicializar(){
        this.empresas = empresaFacade.findAll();
    }

    public PesquisaEmpresaBean() {
        
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public Empresa getEmpresaParaExcluir() {
        return empresaParaExcluir;
    }

    public void setEmpresaParaExcluir(Empresa empresaParaExcluir) {
        this.empresaParaExcluir = empresaParaExcluir;
    }
    
    public void excluir() {
        try {
            empresaFacade.remove(empresaParaExcluir);
            FacesUtil.addSuccessMessage("A empresa '" + empresaParaExcluir.getNomeFantasia() + "' foi excluída com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
    
}
