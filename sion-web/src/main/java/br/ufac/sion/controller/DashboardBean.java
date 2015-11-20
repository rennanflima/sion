/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.SituacaoInscricao;
import br.ufac.sion.util.jsf.FacesProducer;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Rennan
 */
@ManagedBean
@RequestScoped
public class DashboardBean implements Serializable {

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    private LineChartModel model;

    public LineChartModel getModel() {
        return model;
    }

    public void preRender() {
        this.model = new LineChartModel();

        adicionarSerie("Total de inscrições", null);
        adicionarSerie("Inscrições confirmadas", SituacaoInscricao.CONFIRMADA);

        model.setAnimate(true);
        model.setTitle("Inscrições por Concurso");
        model.setLegendPosition("nw");
        model.setShowPointLabels(true);

        model.getAxes().put(AxisType.X, new CategoryAxis("Concurso"));
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel("Quantidade de inscritos");
        yAxis.setMin(0);
    }

    private void adicionarSerie(String rotulo, SituacaoInscricao status) {

        List<Concurso> concursos = concursoFacade.findAll();

        ChartSeries series = new ChartSeries();
        series.setLabel(rotulo);

        for (Concurso concurso : concursos) {
            if (status != null && status.equals(SituacaoInscricao.CONFIRMADA)) {
                series.set(concurso.getTitulo(), inscricaoFacade.encontrarQuantidadeDeInscricoesConfirmadasESubJudice(concurso));
            } else {
                series.set(concurso.getTitulo(), inscricaoFacade.encontrarQuantidadeDeInscricoes(concurso));
            }

        }
        this.model.addSeries(series);
    }

}
