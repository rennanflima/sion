/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller;

import br.ufac.sion.dao.FuncionarioFacadeLocal;
import br.ufac.sion.dto.AuditoriaDTO;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.ArquivoRetorno;
import br.ufac.sion.model.Boleto;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.ContaBancaria;
import br.ufac.sion.model.Empresa;
import br.ufac.sion.model.Funcionario;
import br.ufac.sion.model.Grupo;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.Localidade;
import br.ufac.sion.model.Nivel;
import br.ufac.sion.model.Setor;
import br.ufac.sion.model.TokenRecuperacao;
import br.ufac.sion.model.Usuario;
import br.ufac.sion.model.Vaga;
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
public class AuditoriaFuncionariosBean implements Serializable {

    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;

    @EJB
    private AuditoriaService auditoriaService;

    private DateTimeFormatter formatador;

    private List<AuditoriaDTO> auditoriasDTO;
    private List<Funcionario> funcionarios;
    private Map<String, Class> mapClasses;
    private List<String> listaClasses;
    private List<String> classesSelecionadas;

    private FiltroAuditoria filtroAuditoria;
    
    private AuditoriaDTO auditoriaSelecionada;

    private boolean admin;
    
    private boolean sistema;

    private boolean marcarTodos;

    @PostConstruct
    public void init() {
        auditoriaSelecionada = new AuditoriaDTO();
        auditoriasDTO = new ArrayList<>();
        classesSelecionadas = new ArrayList<>();
        formatador = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(new Locale("pt", "BR"));
        funcionarios = funcionarioFacade.findAll();
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

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public List<String> getClassesSelecionadas() {
        return classesSelecionadas;
    }

    public void setClassesSelecionadas(List<String> classesSelecionadas) {
        this.classesSelecionadas = classesSelecionadas;
    }

    public List<String> getListaClasses() {
        return listaClasses;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isSistema() {
        return sistema;
    }

    public void setSistema(boolean sistema) {
        this.sistema = sistema;
    }

    public boolean isMarcarTodos() {
        if (classesSelecionadas.size() < listaClasses.size()) {
            marcarTodos = false;
        } else if (classesSelecionadas.size() == listaClasses.size()) {
            marcarTodos = true;
        }
        return marcarTodos;
    }

    public void setMarcarTodos(boolean marcarTodos) {
        this.marcarTodos = marcarTodos;
    }

    public AuditoriaDTO getAuditoriaSelecionada() {
        return auditoriaSelecionada;
    }

    public void setAuditoriaSelecionada(AuditoriaDTO auditoriaSelecionada) {
        this.auditoriaSelecionada = auditoriaSelecionada;
    }

    public void pesquisarHistorico() {
        try {
            auditoriasDTO.clear();
            if(sistema){
                filtroAuditoria.setLogin("Sion - tarefa automatizada!");
            }
            if (admin) {
                filtroAuditoria.setLogin("admin");
            }
            if (!classesSelecionadas.isEmpty()) {
                for (String key : classesSelecionadas) {
                    filtroAuditoria.setClasse(mapClasses.get(key));
                    filtroAuditoria.setEntidade(key);
                    auditoriasDTO.addAll(auditoriaService.findAll(filtroAuditoria));
                }
            } else {
                for (String key : listaClasses) {
                    filtroAuditoria.setClasse(mapClasses.get(key));
                    filtroAuditoria.setEntidade(key);
                    auditoriasDTO.addAll(auditoriaService.findAll(filtroAuditoria));
                }
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
        listaClasses.add("Arquivo de Retorno");
        listaClasses.add("Boleto");
        listaClasses.add("Candidato");
        listaClasses.add("Cargo");
        listaClasses.add("Concurso");
        listaClasses.add("Contas Bancárias");
        listaClasses.add("Empresa");
        listaClasses.add("Funcionário");
        listaClasses.add("Grupo");
        listaClasses.add("Inscrição");
        listaClasses.add("Localidade");
        listaClasses.add("Nível");
        listaClasses.add("Setor");
        listaClasses.add("Tipo de Vaga");
        listaClasses.add("Token de Recuperação");
        listaClasses.add("Usuário");
    }

    private void populaMapClasses() {
        mapClasses = new HashMap<String, Class>();
        mapClasses.put("Arquivo de Retorno", ArquivoRetorno.class);
        mapClasses.put("Boleto", Boleto.class);
        mapClasses.put("Candidato", Candidato.class);
        mapClasses.put("Cargo", Cargo.class);
        mapClasses.put("Concurso", Concurso.class);
        mapClasses.put("Contas Bancárias", ContaBancaria.class);
        mapClasses.put("Empresa", Empresa.class);
        mapClasses.put("Funcionário", Funcionario.class);
        mapClasses.put("Grupo", Grupo.class);
        mapClasses.put("Inscrição", Inscricao.class);
        mapClasses.put("Localidade", Localidade.class);
        mapClasses.put("Nível", Nivel.class);
        mapClasses.put("Setor", Setor.class);
        mapClasses.put("Tipo de Vaga", Vaga.class);
        mapClasses.put("Token de Recuperação", TokenRecuperacao.class);
        mapClasses.put("Usuário", Usuario.class);
    }

    public void limparFiltro() {
        filtroAuditoria = new FiltroAuditoria();
        admin = false;
        sistema = false;
        if (!classesSelecionadas.isEmpty()) {
            classesSelecionadas.clear();
        }
        if (!auditoriasDTO.isEmpty()) {
            auditoriasDTO.clear();
        }
    }

    public void selecionarTodos() {
        if (marcarTodos) {
            classesSelecionadas.addAll(listaClasses);
        } else {
            classesSelecionadas.clear();
        }
    }
}
