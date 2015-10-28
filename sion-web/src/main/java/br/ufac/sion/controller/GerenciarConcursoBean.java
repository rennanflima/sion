/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.model.Concurso;
import br.ufac.sion.util.jsf.FacesProducer;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class GerenciarConcursoBean implements Serializable {

    private Concurso concurso;

    public GerenciarConcursoBean() {
        recuperaConcursoSessao();
    }

    
    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }
    
    public void recuperaConcursoSessao(){
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }
    
    public void removeConcursoSessao(){
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        session.removeAttribute("concursoGerenciado");
    }
}
