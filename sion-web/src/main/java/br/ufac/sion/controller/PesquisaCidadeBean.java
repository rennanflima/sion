/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CidadeFacadeLocal;
import br.ufac.sion.dao.EstadoFacadeLocal;
import br.ufac.sion.model.Cidade;
import br.ufac.sion.model.Estado;
import br.ufac.sion.model.vo.FiltroCidades;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author rennan.lima
 */
@Named
@ViewScoped
public class PesquisaCidadeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private CidadeFacadeLocal cidadeFacade;

    @EJB
    private EstadoFacadeLocal estadoFacade;

    private FiltroCidades filtro = new FiltroCidades();

    private LazyDataModel<Cidade> cidadesLazy;

    private List<Estado> estados;

    private Cidade cidadeSelecionadaParaExcluir;

    public PesquisaCidadeBean() {
    }

    @PostConstruct
    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.estados = estadoFacade.findAll();
        }
        this.cidadesLazy = new LazyDataModel<Cidade>() {
            private static final long serialVersionUID = 1L;

            @Override
            public List<Cidade> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);

                setRowCount(cidadeFacade.contaCidades(filtro));

                return cidadeFacade.buscaTodosComPaginacao(filtro);
            }
        };
    }

    public LazyDataModel<Cidade> getCidadesLazy() {
        return cidadesLazy;
    }

    public FiltroCidades getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroCidades filtro) {
        this.filtro = filtro;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public Cidade getCidadeSelecionadaParaExcluir() {
        return cidadeSelecionadaParaExcluir;
    }

    public void setCidadeSelecionadaParaExcluir(Cidade cidadeSelecionadaParaExcluir) {
        this.cidadeSelecionadaParaExcluir = cidadeSelecionadaParaExcluir;
    }

    public void limparFiltro() {
        filtro.setNome(null);
        filtro.setEstado(null);
    }
    
    public void excluir() {
        try {
            cidadeFacade.remove(cidadeSelecionadaParaExcluir);
            FacesUtil.addSuccessMessage("A cidade '" + cidadeSelecionadaParaExcluir.getNome() + "' foi excluída com sucesso.");
            inicializar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage(e.getMessage());
        }
    }
}
