/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.inscricao.security.UsuarioLogado;
import br.ufac.sion.inscricao.security.UsuarioSistema;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Inscricao;
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
 * @author rennan.lima
 */
@Named
@ViewScoped
public class PaginaAcompanhamentoBean implements Serializable {

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    @UsuarioLogado
    private UsuarioSistema usuario;

    private Candidato candidato;

    private List<Inscricao> inscricoes;

    @PostConstruct
    public void inicializar() {
        this.candidato = candidatoFacade.findByCPF(getUsuarioLogado().getCandidato().getCpf());
        this.inscricoes = inscricaoFacade.findIncricoesAtivasByCandidato(candidato);
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public Candidato getCandidato() {
        return candidato;
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
