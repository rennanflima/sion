/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
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
public class EstatisticaConcursoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    private Concurso concurso;


    private String status = "";

    private List<CargoConcurso> cargosConcurso = new ArrayList<>();

    public EstatisticaConcursoBean() {
        recuperaConcursoSessao();
    }

    @PostConstruct
    public void inicializar() {
        this.cargosConcurso = cargoConcursoFacade.findByConcurso(concurso);
    }

    public List<CargoConcurso> getCargosConcurso() {
        return cargosConcurso;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void recuperaConcursoSessao() {
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }

    public String getQuantidadeInscritosPorCargo(CargoConcurso cargoConcurso) {
        if (status.equals("CONFIRMADA")) {
            return inscricaoFacade.encontrarQuatidadeDeInscricoesPorCargo(cargoConcurso,status).toString();
        } else {
            return inscricaoFacade.encontrarQuatidadeDeInscricoesPorCargo(cargoConcurso,status).toString();
        }
    }
    
    public void limparFiltro(){
        this.status = "";
    }
}
