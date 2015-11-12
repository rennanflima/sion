/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.SituacaoInscricao;
import br.ufac.sion.util.jsf.FacesProducer;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author Rennan
 */
@ManagedBean
@RequestScoped
public class GerenciarConcursoBean implements Serializable {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    private LineChartModel model;
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
        model.setTitle("Inscrições");
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

    public LineChartModel getModel() {
        return model;
    }
}
