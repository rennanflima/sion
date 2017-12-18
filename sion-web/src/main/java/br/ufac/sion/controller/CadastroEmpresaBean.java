/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CidadeFacadeLocal;
import br.ufac.sion.dao.EmpresaFacadeLocal;
import br.ufac.sion.dao.EstadoFacadeLocal;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.Cidade;
import br.ufac.sion.model.Empresa;
import br.ufac.sion.model.Endereco;
import br.ufac.sion.model.Estado;
import br.ufac.sion.service.CepService;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author rennan.lima
 */
@Named
@ViewScoped
public class CadastroEmpresaBean implements Serializable {

    @EJB
    private CepService cepService;

    @EJB
    private CidadeFacadeLocal cidadeFacade;

    @EJB
    private EstadoFacadeLocal estadoFacade;

    @EJB
    private EmpresaFacadeLocal empresaFacade;

    private Empresa empresa;
    private Estado estado;

    private boolean bloqueiaEndereco = true;

    private List<Estado> estados;
    private List<Cidade> cidades;

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.estados = estadoFacade.findAll();

            if (this.estado != null) {
                carregarCidades();
            }
        }
    }

    public CadastroEmpresaBean() {
        limpar();
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;

        if (this.empresa != null) {
            this.estado = this.empresa.getEndereco().getCidade().getEstado();
        }
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public boolean isBloqueiaEndereco() {
        return bloqueiaEndereco;
    }

    public void salvar() {
        try {
            this.empresaFacade.save(empresa);
            FacesUtil.addSuccessMessage("Empresa salva com sucesso!");
            limpar();
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar a empresa: " + e.getMessage());
        }
    }

    public void limpar() {
        this.empresa = new Empresa();
        this.empresa.setEndereco(new Endereco());
        this.estado = null;
        this.cidades = new ArrayList<>();
    }

    public boolean isEditando() {
        return this.empresa != null;
    }

    public void carregarCidades() {
        this.cidades.clear();
        this.cidades = cidadeFacade.findByEstado(estado);
    }

    public void consultaCep() {
        try {
            String cep = this.empresa.getEndereco().getCep();
            this.empresa.setEndereco(cepService.consultarCep(cep));
            this.estado = this.empresa.getEndereco().getCidade().getEstado();
            carregarCidades();
            bloqueiaEndereco = true;
        } catch (NegocioException ex) {
            FacesUtil.addErrorMessage(ex.getMessage());
            bloqueiaEndereco = false;
        }
    }

}
