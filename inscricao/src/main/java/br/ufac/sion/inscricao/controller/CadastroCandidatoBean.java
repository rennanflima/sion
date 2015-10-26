/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.dao.CidadeFacadeLocal;
import br.ufac.sion.dao.EstadoFacadeLocal;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Cidade;
import br.ufac.sion.model.Endereco;
import br.ufac.sion.model.Estado;
import br.ufac.sion.model.RG;
import br.ufac.sion.model.Sexo;
import br.ufac.sion.model.TipoTelefone;
import br.ufac.sion.service.CepService;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class CadastroCandidatoBean implements Serializable {

    @EJB
    private CepService cepService;

    @EJB
    private CidadeFacadeLocal cidadeFacade;

    @EJB
    private EstadoFacadeLocal estadoFacade;

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    private Candidato candidato;

    private Estado estado;

    private List<Estado> estados;
    private List<Cidade> cidades;
    private List<Sexo> sexos;
    private List<TipoTelefone> tiposTelefone;

    public void inicializar() {
        this.sexos = Arrays.asList(Sexo.values());
        this.tiposTelefone = Arrays.asList(TipoTelefone.values());
        if (FacesUtil.isNotPostback()) {
            this.estados = estadoFacade.findAll();

            if (this.estado != null) {
                carregarCidades();
            }
        }
    }

    public CadastroCandidatoBean() {
        limpar();
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;

        if (this.candidato != null) {
            this.estado = this.candidato.getEndereco().getCidade().getEstado();
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

    public List<Sexo> getSexos() {
        return sexos;
    }

    public List<TipoTelefone> getTiposTelefone() {
        return tiposTelefone;
    }

    public void salvar() {
        try {
            this.candidatoFacade.save(candidato);
            FacesUtil.addSuccessMessage("Candidato salvo com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar o candidato: " + e.getMessage());
        }
    }

    public void limpar() {
        this.candidato = new Candidato();
        this.candidato.setRg(new RG());
        this.candidato.setEndereco(new Endereco());
        this.candidato.adicionaTelefoneVazio();
        this.estado = null;
        this.cidades = new ArrayList<>();
    }

    public boolean isEditando() {
        return this.candidato.getId() != null;
    }

    public void carregarCidades() {
        this.cidades.clear();
        this.cidades = cidadeFacade.findByEstado(estado);
    }

    public void consultaCep() {
        String cep = this.candidato.getEndereco().getCep();
        this.candidato.setEndereco(cepService.consultarCep(cep));
        this.estado = this.candidato.getEndereco().getCidade().getEstado();
        carregarCidades();
    }

}
