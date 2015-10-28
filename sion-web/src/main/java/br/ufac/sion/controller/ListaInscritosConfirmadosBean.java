/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.util.jsf.FacesProducer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class ListaInscritosConfirmadosBean implements Serializable {

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    private Concurso concurso;

    private List<Inscricao> inscritosConfirmados;
    
    @PostConstruct
    public void inicializar() {
        this.inscritosConfirmados = inscricaoFacade.findByConcursoAndConfirmadas(concurso);
    }

    public ListaInscritosConfirmadosBean() {
        inscritosConfirmados = new ArrayList<>();
        recuperaConcursoSessao();
    }

    public List<Inscricao> getInscritosConfirmados() {
        return inscritosConfirmados;
    }

    private void recuperaConcursoSessao() {
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }
}
