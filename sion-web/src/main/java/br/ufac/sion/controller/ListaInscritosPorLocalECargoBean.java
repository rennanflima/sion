/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.dao.LocalidadeFacadeLocal;
import br.ufac.sion.dao.NivelFacadeLocal;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.Localidade;
import br.ufac.sion.model.Nivel;
import br.ufac.sion.model.vo.FiltroInscritosRelatorio;
import br.ufac.sion.util.jsf.FacesProducer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class ListaInscritosPorLocalECargoBean implements Serializable {

    @EJB
    private LocalidadeFacadeLocal localidadeFacade;

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    @EJB
    private NivelFacadeLocal nivelFacade;

    private Concurso concurso;
    private Localidade local;
    private Nivel nivel;
    private FiltroInscritosRelatorio filtro = new FiltroInscritosRelatorio();
    private List<CargoConcurso> cargosConcurso;

    private List<Nivel> niveis;
    private List<Localidade> localidades;
    private LazyDataModel<Inscricao> inscritosPorLocalECargo;

    @PostConstruct
    public void inicializar() {
        this.localidades = localidadeFacade.findByConcurso(concurso);
        this.inscritosPorLocalECargo = new LazyDataModel<Inscricao>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<Inscricao> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);
                filtro.setConcurso(concurso);

                setRowCount(inscricaoFacade.contaInscricoesByCargoAndLocalidade(filtro));

                return inscricaoFacade.findByCargoAndLocalidade(filtro);
            }

        };

    }

    public Localidade getLocal() {
        return local;
    }

    public void setLocal(Localidade local) {
        this.local = local;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    
    public List<Nivel> getNiveis() {
        return niveis;
    }

    public void setNiveis(List<Nivel> niveis) {
        this.niveis = niveis;
    }

    public List<Localidade> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidade> localidades) {
        this.localidades = localidades;
    }

    public void limparFiltro() {
        filtro.setCargo(new CargoConcurso());
        local = new Localidade();
        nivel = new Nivel();
        this.niveis = new ArrayList<>();
        this.localidades = new ArrayList<>();
        this.cargosConcurso = new ArrayList<>();
    }

    public FiltroInscritosRelatorio getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroInscritosRelatorio filtro) {
        this.filtro = filtro;
    }

    public List<CargoConcurso> getCargosConcurso() {
        return cargosConcurso;
    }

    public ListaInscritosPorLocalECargoBean() {
        recuperaConcursoSessao();
        limparFiltro();
    }

    public LazyDataModel<Inscricao> getInscritosPorLocalECargo() {
        return inscritosPorLocalECargo;
    }

    private void recuperaConcursoSessao() {
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }

    public void carregarNiveis() {
        this.nivel = new Nivel();
        this.niveis.clear();
        this.niveis = nivelFacade.findByLocalidadeAndConcurso(this.local, this.concurso);
    }

    public void carregarCargos() {
        this.cargosConcurso.clear();
        this.cargosConcurso = cargoConcursoFacade.findByConcursoAndNivelAndLocalidade(concurso, this.nivel, this.local);
    }

}
