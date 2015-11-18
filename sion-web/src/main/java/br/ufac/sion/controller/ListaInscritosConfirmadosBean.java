/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.util.jsf.FacesProducer;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class ListaInscritosConfirmadosBean implements Serializable {

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    private Concurso concurso;

    private LazyDataModel<Inscricao> inscritosConfirmados;

    @PostConstruct
    public void inicializar() {
        this.inscritosConfirmados = new LazyDataModel<Inscricao>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<Inscricao> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

                setRowCount(inscricaoFacade.encontrarQuantidadeDeInscricoesConfirmadas(concurso).intValue());

                return inscricaoFacade.findByConcursoAndConfirmadas(concurso, first, pageSize);
            }

        };

    }

    public ListaInscritosConfirmadosBean() {
        recuperaConcursoSessao();
    }

    public LazyDataModel<Inscricao> getInscritosConfirmados() {
        return inscritosConfirmados;
    }

    private void recuperaConcursoSessao() {
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }
}
