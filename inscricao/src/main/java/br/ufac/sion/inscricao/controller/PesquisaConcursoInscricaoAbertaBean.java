/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.inscricao.security.UsuarioSistema;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.service.InscricaoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class PesquisaConcursoInscricaoAbertaBean implements Serializable {

    @EJB
    private ConcursoFacadeLocal concursoFacade;
    
    @EJB
    private InscricaoService inscricaoService;
    
    @EJB
    private CandidatoFacadeLocal candidatoFacade;
    
    private List<Concurso> concursos;
    private Inscricao inscricao;
    private Candidato candidato;
    private List<Inscricao> concursosInscrito;
    
    @PostConstruct
    public void inicializar() {
        this.inscricao = null;
        this.concursosInscrito = new ArrayList<>();
        this.candidato = candidatoFacade.findByCPF(getUsuarioLogado().getCandidato().getCpf());
        this.concursos = concursoFacade.findByInscricoesAbertas();
        for (Concurso c : concursos) {
            System.out.println("Concurso: "+c.getTitulo());
            this.inscricao = inscricaoService.pesquisarPorCandidatoEConcurso(candidato, c);
            if(inscricao != null){
                this.concursosInscrito.add(inscricao);
            }
            this.inscricao = null;
        }
        
    }

    public List<Concurso> getConcursos() {
        return concursos;
    }

    public List<Inscricao> getConcursosInscrito() {
        return concursosInscrito;
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
