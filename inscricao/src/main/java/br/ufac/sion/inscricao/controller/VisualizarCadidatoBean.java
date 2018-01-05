/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.inscricao.security.UsuarioSistema;
import br.ufac.sion.model.Candidato;
import java.io.Serializable;
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
public class VisualizarCadidatoBean implements Serializable {

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    private Candidato candidato;

    public void inicializar() {
        this.candidato = candidatoFacade.findByCPF(getUsuarioLogado().getCandidato().getCpf());
    }

    public VisualizarCadidatoBean() {
        candidato = new Candidato();
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
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
