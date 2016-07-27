/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.model.Concurso;
import br.ufac.sion.service.ConcursoService;
import br.ufac.sion.util.jsf.FacesProducer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author rennan.lima
 */
@ManagedBean
@RequestScoped
public class RelatorioConcursoBean implements Serializable {

    @EJB
    private ConcursoService concursoService;

    private Concurso concurso;

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

    public String imprimeRelacaoInscritos() throws JRException, IOException {
        byte[] relatorio = concursoService.geraPDFRelacaoInscritos(null);
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        ec.responseReset();
        ec.setResponseContentType("application/pdf");
        ec.setResponseContentLength(relatorio.length);
        System.out.println("Tamanho PDF: " + relatorio.length);
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"relacao_inscritosˆ_" + concurso.getId() + ".pdf\"");
        OutputStream output = ec.getResponseOutputStream();
        output.write(relatorio);
        fc.responseComplete();
        return null;
    }

}
