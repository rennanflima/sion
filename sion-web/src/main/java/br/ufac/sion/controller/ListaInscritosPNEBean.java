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
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.vo.FiltroInscritos;
import br.ufac.sion.util.jsf.Sion;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class ListaInscritosPNEBean implements Serializable {

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    @Inject  @Sion
    private HttpServletRequest request;
    
    private Concurso concurso;
    private FiltroInscritos filtro = new FiltroInscritos();
    private List<CargoConcurso> cargosConcurso;
    private LazyDataModel<Inscricao> inscritosPNE;

    @PostConstruct
    public void inicializar() {
        cargosConcurso = cargoConcursoFacade.findByConcurso(concurso);
        this.inscritosPNE = new LazyDataModel<Inscricao>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<Inscricao> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);
                filtro.setConcurso(concurso);

                setRowCount(inscricaoFacade.contaInscricoesPNE(filtro));

                return inscricaoFacade.findByConcursoAndPNE(filtro);
            }

        };
    }

    public ListaInscritosPNEBean() {
        recuperaConcursoSessao();
        limparFiltro();
    }

    public void limparFiltro() {
        filtro.setCargo(new CargoConcurso());
        filtro.setNome(null);
        filtro.setCpf(null);
        filtro.setNumeroInscricao(null);
    }

    public List<CargoConcurso> getCargosConcurso() {
        return cargosConcurso;
    }

    public LazyDataModel<Inscricao> getInscritosPNE() {
        return inscritosPNE;
    }

    public FiltroInscritos getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroInscritos filtro) {
        this.filtro = filtro;
    }

    private void recuperaConcursoSessao() {
        HttpSession session = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }
}
