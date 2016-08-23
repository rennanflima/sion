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
import br.ufac.sion.service.ConcursoService;
import br.ufac.sion.util.jsf.FacesProducer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

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

    @EJB
    private ConcursoService concursoService;

    private Concurso concurso;

    private String status = "";
    private String titulo = "Quantificação de Inscritos Por Cargo";

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

    public String getTitulo() {
        if (status.equals("CONFIRMADA")) {
            return "Quantificação de Inscritos Confirmados Por Cargo";
        }
        return titulo;
    }

    public void recuperaConcursoSessao() {
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }

    public String getQuantidadeInscritosPorCargo(CargoConcurso cargoConcurso) {
        if (status.equals("CONFIRMADA")) {
            return inscricaoFacade.encontrarQuatidadeDeInscricoesPorCargo(cargoConcurso, status).toString();
        } else {
            return inscricaoFacade.encontrarQuatidadeDeInscricoesPorCargo(cargoConcurso, status).toString();
        }
    }

    public void imprimeEstatistica() throws JRException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        
        String nomeArquivo = "";
        JasperPrint jasperPrint = null;
        
        if (status.equals("CONFIRMADA")) {
            jasperPrint = concursoService.geraRelatorioEstatisticaIncritosConfirmados(concurso);
            nomeArquivo = "estatistica_inscritos_confirmados_" + concurso.getId();
        } else {
            jasperPrint = concursoService.geraRelatorioEstatisticaIncritos(concurso);
            nomeArquivo = "estatistica_inscritos_" + concurso.getId();
        }
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
        
        response.reset();
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        response.setHeader("Content-disposition", "inline; filename=" + nomeArquivo + ".pdf");
        response.getOutputStream().write(baos.toByteArray());
        response.getOutputStream().flush();
        response.getOutputStream().close();

        context.responseComplete();
    }

    public void limparFiltro() {
        this.status = "";
    }
}
