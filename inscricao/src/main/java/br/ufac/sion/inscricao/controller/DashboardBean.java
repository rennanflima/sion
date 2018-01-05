/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.inscricao.security.UsuarioLogado;
import br.ufac.sion.inscricao.security.UsuarioSistema;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.service.InscricaoService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class DashboardBean implements Serializable {

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    @EJB
    private InscricaoService inscricaoService;

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    @UsuarioLogado
    private UsuarioSistema usuario;

    private List<Concurso> concursos;
    private Candidato candidato;
    private List<Inscricao> inscricoes;

    @PostConstruct
    public void inicializar() {
        this.candidato = candidatoFacade.findByCPF(getUsuarioLogado().getCandidato().getCpf());
        this.concursos = concursoFacade.findByInscricoesAbertas();
        this.inscricoes = inscricaoFacade.findByCandidato(candidato);
    }

    public List<Concurso> getConcursos() {
        return concursos;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }
    
    private UsuarioSistema getUsuarioLogado() {
        UsuarioSistema usuario = null;

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

        if (auth != null && auth.getPrincipal() != null) {
            usuario = (UsuarioSistema) auth.getPrincipal();
        }

        return usuario;
    }

}
