/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.util.jsf.FacesProducer;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Rennan
 */
@ManagedBean
@RequestScoped
public class DashboardBean implements Serializable {

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM");

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;
    
    private LineChartModel model;
    
    private Concurso concurso;


    @PostConstruct
    public void init() {
        recuperaConcursoSessao();
        createAnimatedModels();
    }

    private void createAnimatedModels() {
        this.model = initLinearModel();
        this.model.setTitle("Inscrições");
        this.model.setAnimate(true);
        this.model.setLegendPosition("se");
        Axis yAxis = this.model.getAxis(AxisType.Y);
    }

    private LineChartModel initLinearModel() {
        LineChartModel lineModel = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Total de inscrições");
        
         Map<Date, Long> totalInscricoes = inscricaoFacade.inscricoesPorData(concurso, null);

        for (Date data : totalInscricoes.keySet()) {
            series1.set(DATE_FORMAT.format(data), totalInscricoes.get(data));
            System.out.println("Data: "+DATE_FORMAT.format(data)+" - "+totalInscricoes.get(data));
        }

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");

        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);

        lineModel.addSeries(series1);
        lineModel.addSeries(series2);

        return lineModel;
    }

    public LineChartModel getModel() {
        return model;
    }
    
    public void recuperaConcursoSessao() {
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }

}
