/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.model.Concurso;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author rennan.lima
 */
@ManagedBean
@ViewScoped
public class ConcursosAbertosBean implements Serializable {

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    private List<Concurso> concursos;
    

    @PostConstruct
    public void inicializar() {
        this.concursos = concursoFacade.findByInscricoesAbertas();

    }

    public List<Concurso> getConcursos() {
        return concursos;
    }
}
