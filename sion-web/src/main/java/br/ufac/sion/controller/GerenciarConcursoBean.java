/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.SituacaoInscricao;
import br.ufac.sion.service.ConcursoService;
import br.ufac.sion.util.jsf.FacesProducer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
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
@ManagedBean
@RequestScoped
public class GerenciarConcursoBean implements Serializable {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");

    @EJB
    private ConcursoService concursoService;

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

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
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }

    public void removeConcursoSessao() {
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
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

    public void imprimeEstatistica() throws JRException, IOException {
        byte[] relatorio = concursoService.geraPDFEstatisticaIncritosConfirmados(concurso);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        ServletOutputStream servletOutputStream = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setContentLength(relatorio.length);
        response.addHeader("Content-Disposition", "attachment; filename=\"estatistica_" + concurso.getId() + ".pdf\"");
        servletOutputStream.write(relatorio);
        servletOutputStream.flush();
        servletOutputStream.close();
        context.renderResponse();
        context.responseComplete();

//        ExternalContext ec = fc.getExternalContext();
//        ec.responseReset();
//        ec.setResponseContentType("application/pdf");
//        ec.setResponseContentLength(relatorio.length);
//        System.out.println("Tamanho PDF: "+relatorio.length);
//        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"estatistica_" + concurso.getId() + ".pdf\"");
//        OutputStream output = ec.getResponseOutputStream();
//        output.write(relatorio);
//        fc.responseComplete();
//        return null;
    }

    public void imprimeRelacaoInscritos() throws JRException, IOException {
//        byte[] relatorio = concursoService.geraPDFRelacaoInscritos(concurso);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        JasperPrint jasperPrint = concursoService.geraPDFRelacaoInscritos(concurso);
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

        response.reset();
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        response.setHeader("Content-disposition", "inline; filename=relacao_inscritos_" + concurso.getId() + ".pdf");
        response.getOutputStream().write(baos.toByteArray());
        response.getOutputStream().flush();
        response.getOutputStream().close();

        context.responseComplete();
    }

}
