/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.dto.AuditoriaDTO;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.Boleto;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.Usuario;
import br.ufac.sion.service.AuditoriaService;
import br.ufac.sion.service.util.FiltroAuditoria;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.hibernate.envers.RevisionType;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class AuditoriaCandidatosBean implements Serializable {

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    @EJB
    private AuditoriaService auditoriaService;

    private DateTimeFormatter formatador;

    private List<AuditoriaDTO> auditoriasDTO;
    private List<Candidato> candidatos;
    private Map<String, Class> mapClasses;

    private List<String> listaClasses;

    private FiltroAuditoria filtroAuditoria;

    @PostConstruct
    public void init() {
        auditoriasDTO = new ArrayList<>();
        formatador = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("pt", "BR"));
        candidatos = candidatoFacade.findAll();
        populaMapClasses();
        populaListaClasses();
        limparFiltro();
    }

    public FiltroAuditoria getFiltroAuditoria() {
        return filtroAuditoria;
    }

    public void setFiltroAuditoria(FiltroAuditoria filtroAuditoria) {
        this.filtroAuditoria = filtroAuditoria;
    }

    public List<AuditoriaDTO> getAuditoriasDTO() {
        return auditoriasDTO;
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public List<String> getListaClasses() {
        return listaClasses;
    }

    public void pesquisarHistorico() {
        try {
            auditoriasDTO.clear();
            for (String key : listaClasses) {
                filtroAuditoria.setClasse(mapClasses.get(key));
                filtroAuditoria.setEntidade(key);
                auditoriasDTO.addAll(auditoriaService.findAll(filtroAuditoria));
            }
        } catch (NegocioException ex) {
            FacesUtil.addErrorMessage("Ocorreu um erro ao buscar o histórico do funcionário: " + ex.getMessage());
        }
    }

    public RevisionType[] getTiposRevisao() {
        return RevisionType.values();
    }

    private void populaListaClasses() {
        listaClasses = new ArrayList<>();
        listaClasses.add("Candidato");
        listaClasses.add("Inscricao");
        listaClasses.add("Boleto");
        listaClasses.add("Usuario");
        listaClasses.add("Token de Recuperação");
    }

    private void populaMapClasses() {
        mapClasses = new HashMap<>();
        mapClasses.put("Candidato", Candidato.class);
        mapClasses.put("Inscricao", Inscricao.class);
        mapClasses.put("Boleto", Boleto.class);
        mapClasses.put("Usuario", Usuario.class);
    }

    public void limparFiltro() {
        filtroAuditoria = new FiltroAuditoria();
        if (!auditoriasDTO.isEmpty()) {
            auditoriasDTO.clear();
        }
    }
}
