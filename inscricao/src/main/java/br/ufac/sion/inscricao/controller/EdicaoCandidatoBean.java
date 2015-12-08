/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CidadeFacadeLocal;
import br.ufac.sion.dao.EstadoFacadeLocal;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.inscricao.util.jsf.FacesUtil;
import br.ufac.sion.model.BracoDominante;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Cidade;
import br.ufac.sion.model.Endereco;
import br.ufac.sion.model.Escolaridade;
import br.ufac.sion.model.Estado;
import br.ufac.sion.model.EstadoCivil;
import br.ufac.sion.model.RG;
import br.ufac.sion.model.Sexo;
import br.ufac.sion.model.Telefone;
import br.ufac.sion.model.TipoTelefone;
import br.ufac.sion.service.CandidatoService;
import br.ufac.sion.service.CepService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author rennan.lima
 */
@ManagedBean
@ViewScoped
public class EdicaoCandidatoBean implements Serializable{
    
    @EJB
    private CepService cepService;

    @EJB
    private CidadeFacadeLocal cidadeFacade;

    @EJB
    private EstadoFacadeLocal estadoFacade;

    @EJB
    private CandidatoService candidatoService;

    private Candidato candidato;

    private Estado estado;

    private List<Estado> estados;
    private List<Cidade> cidades;
    private List<Sexo> sexos;
    private List<EstadoCivil> estadosCivil;
    private List<Escolaridade> escolaridades;
    private List<BracoDominante> bracosDominante;

    private Telefone celular;
    private Telefone residencial;
    private Telefone outro;

    public void inicializar() {
        this.sexos = Arrays.asList(Sexo.values());
        this.estadosCivil = Arrays.asList(EstadoCivil.values());
        this.escolaridades = Arrays.asList(Escolaridade.values());
        this.bracosDominante = Arrays.asList(BracoDominante.values());
        if (FacesUtil.isNotPostback()) {
            this.estados = estadoFacade.findAll();
            if (this.estado != null) {
                carregarCidades();
            }
        }
    }

    public EdicaoCandidatoBean() {
        limpar();
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;

        if (this.candidato != null) {
            if(this.candidato.getRg() == null) {
                this.candidato.setRg(new RG());
            }
            this.estado = this.candidato.getEndereco().getCidade().getEstado();
            popularTelefones();
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

    public List<EstadoCivil> getEstadosCivil() {
        return estadosCivil;
    }

    public List<Escolaridade> getEscolaridades() {
        return escolaridades;
    }

    public List<BracoDominante> getBracosDominante() {
        return bracosDominante;
    }

    public Telefone getCelular() {
        return celular;
    }

    public void setCelular(Telefone celular) {
        this.celular = celular;
    }

    public Telefone getResidencial() {
        return residencial;
    }

    public void setResidencial(Telefone residencial) {
        this.residencial = residencial;
    }

    public Telefone getOutro() {
        return outro;
    }

    public void setOutro(Telefone outro) {
        this.outro = outro;
    }

    public void salvar() {
        try {
            salvarTelefones();
            this.candidato = this.candidatoService.editar(candidato);
            FacesUtil.addSuccessMessage("Candidato alterado com sucesso!");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao alterar o candidato: " + e.getMessage());
        }
    }

    public void limpar() {
        this.candidato = new Candidato();
        this.candidato.setRg(new RG());
        this.candidato.setEndereco(new Endereco());
        this.estado = null;
        this.cidades = new ArrayList<>();
        this.celular = new Telefone();
        this.residencial = new Telefone();
        this.outro = new Telefone();
    }

    public boolean isEditando() {
        return this.candidato.getId() != null;
    }

    public void carregarCidades() {
        this.cidades.clear();
        this.cidades = cidadeFacade.findByEstado(estado);
    }

    public void consultaCep() throws NegocioException {
        String cep = this.candidato.getEndereco().getCep();
        this.candidato.setEndereco(cepService.consultarCep(cep));
        this.estado = this.candidato.getEndereco().getCidade().getEstado();
        carregarCidades();
    }

    public void popularTelefones() {
        for (Telefone tel : candidato.getTelefones()) {
            if (tel.getTipo().equals(TipoTelefone.CELULAR)) {
                this.celular = tel;
            }
            if (tel.getTipo().equals(TipoTelefone.TRABALHO)) {
                this.outro = tel;
            }
            if (tel.getTipo().equals(TipoTelefone.RESIDENCIAL)) {
                this.residencial = tel;
            }
        }
    }

    public void salvarTelefones() {
        this.candidato.getTelefones().clear();
        if (StringUtils.isNotBlank(celular.getPrefixo()) && StringUtils.isNotBlank(celular.getNumero())) {
            this.celular.setTipo(TipoTelefone.CELULAR);
            this.candidato.getTelefones().add(celular);
        }
        if (StringUtils.isNotBlank(residencial.getPrefixo()) && StringUtils.isNotBlank(residencial.getNumero())) {
            this.residencial.setTipo(TipoTelefone.RESIDENCIAL);
            this.candidato.getTelefones().add(residencial);
        }
        if (StringUtils.isNotBlank(outro.getPrefixo()) && StringUtils.isNotBlank(outro.getNumero())) {
            this.outro.setTipo(TipoTelefone.OUTRO);
            this.candidato.getTelefones().add(outro);
        }
    }
}
