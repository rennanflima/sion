/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.inscricao.security.UsuarioSistema;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author rennan.lima
 */
@Named
@ViewScoped
public class PesquisaConcursoInscricaoAbertaBean implements Serializable {

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    private List<Concurso> concursos;

    private List<Inscricao> inscricoes;

    private Candidato candidato;

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
