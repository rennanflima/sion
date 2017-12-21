/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.enuns.SituacaoInscricao;
import br.ufac.sion.service.ConcursoService;
import br.ufac.sion.util.jsf.FacesUtil;
import br.ufac.sion.util.jsf.Sion;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Rennan
 */
@Named
@RequestScoped
public class GerenciarConcursoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");

    @EJB
    private ConcursoService concursoService;

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    @Inject  @Sion
    private HttpServletResponse response;

    @Inject  @Sion
    private HttpServletRequest request;
    
    @Inject
    private FacesContext facesContext;

    private LineChartModel model;
    private Concurso concurso;
    private Long totalCargos;
    private Long totalInscricoes;
    private Long totalInscricoesConfirmadas;
    private Long totalInscricoesPNE;
    private Long totalInscricoesSubJudice;

    private PieChartModel inscricaoPie;

    public GerenciarConcursoBean() {
        recuperaConcursoSessao();
//        this.response = FacesProducer.getHttpServletResponse();
//        this.facesContext = FacesProducer.getFacesContext();
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

    public Long getTotalCargos() {
        return totalCargos = cargoConcursoFacade.findQuantidadeCargoByConcurso(concurso);
    }

    public Long getTotalInscricoes() {
        return totalInscricoes = inscricaoFacade.encontrarQuantidadeDeInscricoes(concurso);
    }

    public Long getTotalInscricoesPNE() {
        return totalInscricoesPNE = inscricaoFacade.encontrarQuantidadeDeInscricoesPNE(concurso);
    }

    public Long getTotalInscricoesSubJudice() {
        return totalInscricoesSubJudice = inscricaoFacade.encontrarQuantidadeDeInscricoesSubJudice(concurso);
    }

    public Long getTotalInscricoesConfirmadas() {
        return totalInscricoesConfirmadas = inscricaoFacade.encontrarQuantidadeDeInscricoesConfirmadas(concurso);
    }

    public LineChartModel getModel() {
        return model;
    }

    public PieChartModel getInscricaoPie() {
        inscricaoPie = new PieChartModel();

        inscricaoPie.set("Inscrições não confirmadas", inscricaoFacade.encontrarQuantidadeDeInscricoesNaoConfirmadas(concurso));
        inscricaoPie.set("Inscrições confirmadas", inscricaoFacade.encontrarQuantidadeDeInscricoesConfirmadasESubJudice(concurso));

        inscricaoPie.setTitle("Incrições");
        inscricaoPie.setLegendPosition("e");
        inscricaoPie.setShowDataLabels(true);

        return inscricaoPie;
    }

    public void recuperaConcursoSessao() {
        HttpSession session = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
        System.out.println("Sessão (GerenciarConcursoBean): "+(session==null));
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
        System.out.println("ID: "+this.concurso.getId());
    }

    public void removeConcursoSessao() {
        HttpSession session = request.getSession();
        session.removeAttribute("concursoGerenciado");
    }

    public void preRender() {
        this.model = new LineChartModel();

        adicionarSerie("Total de inscrições", null);
        adicionarSerie("Inscrições confirmadas", SituacaoInscricao.CONFIRMADA);

        model.setAnimate(true);
        model.setTitle("Inscrições por Dia");
        model.setLegendPosition("nw");
        model.setShowPointLabels(true);

        model.getAxes().put(AxisType.X, new CategoryAxis("Data de inscrição"));
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Quantidade de inscritos");
        yAxis.setMin(0);
    }

    private void adicionarSerie(String rotulo, SituacaoInscricao status) {

        Map<Date, Long> quantidadePorData = inscricaoFacade.inscricoesPorData(concurso, status);

        ChartSeries series = new ChartSeries();
        series.setLabel(rotulo);

        for (Date data : quantidadePorData.keySet()) {
            series.set(DATE_FORMAT.format(data), quantidadePorData.get(data));
        }
        this.model.addSeries(series);
    }

    public void imprimeRelacaoInscritos() {        
        try {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            concursoService.geraRelatorioInscritos(concurso, response);
            facesContext.responseComplete();
        } catch (NegocioException ex) {
            Logger.getLogger(EstatisticaConcursoBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void imprimeRelacaoInscritosDeferidos(){
        try {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            concursoService.geraRelatorioInscritosDeferidos(concurso, response);
            facesContext.responseComplete();
        } catch (NegocioException ex) {
            Logger.getLogger(EstatisticaConcursoBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void imprimeRelacaoInscritosDeferidosPNE(){
        try {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            concursoService.geraRelatorioInscritosDeferidosPNE(concurso, response);
            facesContext.responseComplete();
        } catch (NegocioException ex) {
            Logger.getLogger(EstatisticaConcursoBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void imprimeListaPresenca(){
        try {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            concursoService.geraRelatorioListaPresenca(concurso, response);
            facesContext.responseComplete();
        } catch (NegocioException ex) {
            Logger.getLogger(EstatisticaConcursoBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void imprimeRelacaoInscritosIndeferidos(){
        try {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            concursoService.geraRelatorioInscritosIndeferidos(concurso, response);
            facesContext.responseComplete();
        } catch (NegocioException ex) {
            Logger.getLogger(EstatisticaConcursoBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesUtil.addErrorMessage(ex.getMessage());
        }
    }
}
