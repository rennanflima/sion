/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.vo.FiltroCandidatos;
import br.ufac.sion.service.CandidatoService;
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
 * @author Rennan
 */
@Named
@ViewScoped
public class PesquisaCandidatosBean implements Serializable {

    @EJB
    private CandidatoService candidatoService;

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    private static final long serialVersionUID = 1L;

    private FiltroCandidatos filtro = new FiltroCandidatos();

    private LazyDataModel<Candidato> candidatosLazy;

    private Candidato candidatoSelecionado;

    @PostConstruct
    public void inicializar() {
        this.candidatosLazy = new LazyDataModel<Candidato>() {

            private static final long serialVersionUID = 1L;

            @Override
            public List<Candidato> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {

                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);

                setRowCount(candidatoFacade.contaCandidatos(filtro));

                return candidatoFacade.buscaTodosComPaginacao(filtro);
            }
        };
    }

    public PesquisaCandidatosBean() {
        limparFiltro();
    }

    public LazyDataModel<Candidato> getCandidatosLazy() {
        return candidatosLazy;
    }

    public FiltroCandidatos getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroCandidatos filtro) {
        this.filtro = filtro;
    }

    public void limparFiltro() {
        filtro.setCpf(null);
        filtro.setEmail(null);
        filtro.setNome(null);
    }

    public Candidato getCandidatoSelecionado() {
        return candidatoSelecionado;
    }

    public void setCandidatoSelecionado(Candidato candidatoSelecionado) {
        this.candidatoSelecionado = candidatoSelecionado;
    }

    public void reeviaEmailRecuperacao() {
        try {
            candidatoService.solicitarNovaSenha(candidatoSelecionado.getUsuario().getEmail());
            FacesUtil.addSuccessMessage("Em instantes o candidato receberá um e-mail com instruções para recuperar sua senha.");
        } catch (NegocioException ex) {
            FacesUtil.addErrorMessage(ex.getMessage());
        }
    }
}
