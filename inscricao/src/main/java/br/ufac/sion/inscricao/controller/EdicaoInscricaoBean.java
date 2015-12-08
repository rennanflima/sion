/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.dao.LocalidadeFacadeLocal;
import br.ufac.sion.dao.NivelFacadeLocal;
import br.ufac.sion.inscricao.security.UsuarioSistema;
import br.ufac.sion.inscricao.util.jsf.FacesProducer;
import br.ufac.sion.inscricao.util.jsf.FacesUtil;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.Insencao;
import br.ufac.sion.model.Localidade;
import br.ufac.sion.model.NecessidadeEspecial;
import br.ufac.sion.model.Nivel;
import br.ufac.sion.service.InscricaoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author rennan.lima
 */
@ManagedBean
@ViewScoped
public class EdicaoInscricaoBean implements Serializable {
    
    @EJB
    private LocalidadeFacadeLocal localidadeFacade;

    @EJB
    private InscricaoService inscricaoService;

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    @EJB
    private NivelFacadeLocal nivelFacade;

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    private Nivel nivel;

    private Inscricao inscricao;

    private Concurso concurso;

    private Candidato candidato;
    
    private Localidade local;

    private List<Nivel> niveis;
    
    private List<Localidade> localidades;

    private List<CargoConcurso> cargosConcurso;

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.candidato = candidatoFacade.findByCPF(getUsuarioLogado().getUsuario().getCpf());
            this.concurso = inscricao.getCargoConcurso().getConcurso();
            this.localidades = localidadeFacade.findByConcurso(concurso);
            this.local = inscricao.getCargoConcurso().getLocalidade();
            this.nivel = inscricao.getCargoConcurso().getCargo().getNivel();
            carregarNiveis();
            carregarCargos();
        }
    }

    public EdicaoInscricaoBean() {
        limpar();
    }

    public List<Nivel> getNiveis() {
        return niveis;
    }

    public List<CargoConcurso> getCargosConcurso() {
        return cargosConcurso;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public List<Localidade> getLocalidades() {
        return localidades;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public Localidade getLocal() {
        return local;
    }

    public void setLocal(Localidade local) {
        this.local = local;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void salvar() {
        try {
            this.inscricao.setCandidato(candidato);
            inscricao = this.inscricaoService.salvar(inscricao);
            FacesProducer.getExternalContext().redirect("comprovanteInscricao.xhtml?inscricao=" + inscricao.getId());
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao realizar a inscrição: " + e.getMessage());
        }
    }

    private void limpar() {
        this.concurso = new Concurso();
        this.nivel = new Nivel();
        this.inscricao = new Inscricao();
        this.inscricao.setNecessidadeEspecial(new NecessidadeEspecial());
        this.inscricao.setInsencao(new Insencao());
        this.niveis = new ArrayList<>();
        this.cargosConcurso = new ArrayList<>();
        this.local = new Localidade();
        this.localidades = new ArrayList<>();
    }

    public void carregarNiveis(){
        this.niveis.clear();
        this.niveis = nivelFacade.findByLocalidadeAndConcurso(local,concurso);
    }
    
    public void carregarCargos() {
        this.cargosConcurso.clear();
        this.cargosConcurso = cargoConcursoFacade.findByConcursoAndNivelAndLocalidade(concurso, nivel, local);
    }

    private UsuarioSistema getUsuarioLogado() {
        UsuarioSistema usuario = null;

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

        if (auth != null && auth.getPrincipal() != null) {
            usuario = (UsuarioSistema) auth.getPrincipal();
        }

        return usuario;
    }

    public boolean isEditando() {
        return this.inscricao.getId() != null;
    }
}
