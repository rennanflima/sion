/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.service.ConcursoService;
import br.ufac.sion.util.jsf.FacesUtil;
import br.ufac.sion.util.jsf.Sion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class EstatisticaConcursoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    @EJB
    private ConcursoService concursoService;
    
    @Inject  @Sion
    private HttpServletResponse response;
    
    @Inject  @Sion
    private HttpServletRequest request;

    @Inject
    private FacesContext facesContext;
    
    private Concurso concurso;

    private String status = "";
    private String titulo = "Quantificação de Inscritos Por Cargo";

    private List<CargoConcurso> cargosConcurso = new ArrayList<>();

    public EstatisticaConcursoBean() {
        recuperaConcursoSessao();
//        this.response = FacesProducer.getHttpServletResponse();
//        this.facesContext = FacesProducer.getFacesContext();
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

    public String getTitulo() {
        if (status.equals("CONFIRMADA")) {
            return "Quantificação de Inscritos Confirmados Por Cargo";
        }
        return titulo;
    }

    public void recuperaConcursoSessao() {
        HttpSession session = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }

    public String getQuantidadeInscritosPorCargo(CargoConcurso cargoConcurso) {
        if (status.equals("CONFIRMADA")) {
            return inscricaoFacade.encontrarQuatidadeDeInscricoesPorCargo(cargoConcurso, status).toString();
        } else {
            return inscricaoFacade.encontrarQuatidadeDeInscricoesPorCargo(cargoConcurso, status).toString();
        }
    }

    public void imprimeEstatistica(){
        try {
            //        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            concursoService.geraRelatorioEstatisticaIncritos(concurso, status, response);
            facesContext.responseComplete();
        } catch (NegocioException ex) {
            Logger.getLogger(EstatisticaConcursoBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.addErrorMessage(ex.getMessage());
        }

    }

    public void limparFiltro() {
        this.status = "";
    }
}
