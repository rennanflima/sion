/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author Rennan
 */
@ManagedBean
@RequestScoped
public class DashboardBean implements Serializable {

    private LineChartModel model;

    public void preRender() {
        this.model = new LineChartModel();

        adicionarSerie("Inscritos");
        adicionarSerie("Confirmados");
    }

    private void adicionarSerie(String rotulo) {
        LineChartSeries series = new LineChartSeries(rotulo);

        series.set("1", Math.random() * 1000);
        series.set("2", Math.random() * 1000);
        series.set("3", Math.random() * 1000);
        series.set("4", Math.random() * 1000);
        series.set("5", Math.random() * 1000);

        this.model.addSeries(series);
    }

    public LineChartModel getModel() {
        return model;
    }
}
