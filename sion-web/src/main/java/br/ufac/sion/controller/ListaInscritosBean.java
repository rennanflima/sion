/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.vo.FiltroInscritos;
import br.ufac.sion.service.InscricaoService;
import br.ufac.sion.util.jsf.FacesProducer;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class ListaInscritosBean implements Serializable {

    @EJB
    private InscricaoService inscricaoService;
    
    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;
    
    @EJB
    private InscricaoFacadeLocal inscricaoFacade;
    
    private Concurso concurso;
    private FiltroInscritos filtro = new FiltroInscritos();
    private List<CargoConcurso> cargosConcurso;
    private LazyDataModel<Inscricao> inscritos;
    
    private Inscricao inscricao;
    
    @PostConstruct
    public void inicializar() {
        //this.inscritos = inscricaoFacade.findByConcurso(concurso);
        cargosConcurso = cargoConcursoFacade.findByConcurso(concurso);
        this.inscritos = new LazyDataModel<Inscricao>() {
            
            private static final long serialVersionUID = 1L;
            
            @Override
            public List<Inscricao> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {
                
                filtro.setPrimeiroRegistro(first);
                filtro.setQuantidadeRegistros(pageSize);
                filtro.setConcurso(concurso);
                
                setRowCount(inscricaoFacade.contaInscricoes(filtro));
                
                return inscricaoFacade.findByConcurso(filtro);
            }
            
        };
    }
    
    public List<CargoConcurso> getCargosConcurso() {
        return cargosConcurso;
    }
    
    public void limparFiltro() {
        filtro.setCargo(new CargoConcurso());
        filtro.setNome(null);
        filtro.setCpf(null);
        filtro.setNumeroInscricao(null);
    }
    
    public ListaInscritosBean() {
        recuperaConcursoSessao();
        limparFiltro();
        inscricao = new Inscricao();
    }
    
    public FiltroInscritos getFiltro() {
        return filtro;
    }
    
    public void setFiltro(FiltroInscritos filtro) {
        this.filtro = filtro;
    }
    
    public LazyDataModel<Inscricao> getInscritos() {
        return inscritos;
    }
    
    public Inscricao getInscricao() {
        return inscricao;
    }
    
    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }
    
    private void recuperaConcursoSessao() {
        HttpSession session = FacesProducer.getHttpServletRequest().getSession();
        this.concurso = (Concurso) session.getAttribute("concursoGerenciado");
    }
    
    public void limparInscricao(){
        this.inscricao = new Inscricao();
    }
    
    public void confirmaInscricao() {
        try {
            inscricaoService.confirmaInscricao(inscricao);
            FacesUtil.addSuccessMessage("Inscrição de número '" + inscricao.getNumero() + "' foi confirmada com sucesso.");
            this.inscricao = new Inscricao();
            RequestContext.getCurrentInstance().execute("PF('confirmacaoDialog').hide();");
        } catch (NegocioException ex) {
            FacesUtil.addErrorMessage(ex.getMessage());
        }
    }
}
