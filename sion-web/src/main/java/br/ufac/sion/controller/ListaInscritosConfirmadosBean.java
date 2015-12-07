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
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    private Concurso concurso;
    private FiltroInscritos filtro = new FiltroInscritos();
    private List<CargoConcurso> cargosConcurso;

    private LazyDataModel<Inscricao> inscritosConfirmados;

    @PostConstruct
    public void inicializar() {
        cargosConcurso = cargoConcursoFacade.findByConcurso(concurso);
        this.inscritosConfirmados = new LazyDataModel<Inscricao>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<Inscricao> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

//                setRowCount(inscricaoFacade.encontrarQuantidadeDeInscricoesConfirmadasESubJudice(concurso).intValue());
//                return inscricaoFacade.findByConcursoAndConfirmadasESubJudice(concurso, first, pageSize);
                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);
                filtro.setConcurso(concurso);

                setRowCount(inscricaoFacade.contaInscricoesConfirmadasESubJudice(filtro));
                
                return inscricaoFacade.findByConcursoAndConfirmadasESubJudice(filtro);
            }

        };

    }
    
    public void limparFiltro(){
        filtro.setCargo(new CargoConcurso());
        filtro.setNome(null);
        filtro.setCpf(null);
        filtro.setNumeroInscricao(null);
    }

    public FiltroInscritos getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroInscritos filtro) {
        this.filtro = filtro;
    }

    public List<CargoConcurso> getCargosConcurso() {
        return cargosConcurso;
    }

    public ListaInscritosConfirmadosBean() {
        recuperaConcursoSessao();
        limparFiltro();
    }

    public LazyDataModel<Inscricao> getInscritosConfirmados() {
        return inscritosConfirmados;
    }

    private void recuperaConcursoSessao() {
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }
}
