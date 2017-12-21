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
import br.ufac.sion.util.jsf.FacesUtil;
import br.ufac.sion.util.jsf.Sion;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class ListaInscritosBean implements Serializable {

    @EJB
    private InscricaoService inscricaoService;
    
    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;
    
    @EJB
    private InscricaoFacadeLocal inscricaoFacade;
    
    @Inject  @Sion
    private HttpServletRequest request;
    
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
        HttpSession session = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getSession();
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
    
    public void imprimeFormularioInscricao() throws JRException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        JasperPrint jasperPrint = inscricaoService.geraRelatorioFormularioInscricao(inscricao);
        JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

        renderizaPDF(baos, "formulario_inscricao_" + inscricao.getId() + ".pdf");
    }
    
    private void renderizaPDF(ByteArrayOutputStream baos, String nomeArquivo) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();

        response.reset();
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        response.setHeader("Content-disposition", "inline; filename="+nomeArquivo);
        response.getOutputStream().write(baos.toByteArray());
        response.getOutputStream().flush();
        response.getOutputStream().close();

        context.responseComplete();
    }
}
