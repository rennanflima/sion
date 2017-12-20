/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.dao.CidadeFacadeLocal;
import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.dao.EstadoFacadeLocal;
import br.ufac.sion.dao.LocalidadeFacadeLocal;
import br.ufac.sion.dao.NivelFacadeLocal;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.inscricao.security.UsuarioSistema;
import br.ufac.sion.inscricao.util.jsf.FacesProducer;
import br.ufac.sion.inscricao.util.jsf.FacesUtil;
import br.ufac.sion.model.enuns.BracoDominante;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Cidade;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Endereco;
import br.ufac.sion.model.enuns.Escolaridade;
import br.ufac.sion.model.Estado;
import br.ufac.sion.model.enuns.EstadoCivil;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.Insencao;
import br.ufac.sion.model.Localidade;
import br.ufac.sion.model.NecessidadeEspecial;
import br.ufac.sion.model.Nivel;
import br.ufac.sion.model.RG;
import br.ufac.sion.model.enuns.Sexo;
import br.ufac.sion.model.Telefone;
import br.ufac.sion.model.enuns.TipoTelefone;
import br.ufac.sion.service.CandidatoService;
import br.ufac.sion.service.CepService;
import br.ufac.sion.service.InscricaoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class InscricaoBean  implements Serializable{
    @EJB
    private CepService cepService;

    @EJB
    private CidadeFacadeLocal cidadeFacade;

    @EJB
    private ConcursoFacadeLocal concursoFacade;
    
    @EJB
    private EstadoFacadeLocal estadoFacade;
    
    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    @EJB
    private CandidatoService candidatoService;
    
    @EJB
    private LocalidadeFacadeLocal localidadeFacade;

    @EJB
    private InscricaoService inscricaoService;

    @EJB
    private NivelFacadeLocal nivelFacade;

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    private Nivel nivel;

    private Inscricao inscricao;
    private Concurso concurso;
    
    private Candidato candidato;
    private Localidade local;

    private Estado estado;
    private Telefone telefone;
    private Telefone telefoneParaExcluir;
    private Integer linha;
    
    private List<Estado> estados;
    private List<Cidade> cidades;
    private List<Sexo> sexos;
    private List<EstadoCivil> estadosCivil;
    private List<Escolaridade> escolaridades;
    private List<BracoDominante> bracosDominante;
    private List<TipoTelefone> tiposTelefone;
    
    private List<Nivel> niveis;
    private List<Localidade> localidades;

    private List<CargoConcurso> cargosConcurso;
    
    public void inicializar() {
        this.sexos = Arrays.asList(Sexo.values());
        this.estadosCivil = Arrays.asList(EstadoCivil.values());
        this.escolaridades = Arrays.asList(Escolaridade.values());
        this.bracosDominante = Arrays.asList(BracoDominante.values());
        this.tiposTelefone = Arrays.asList(TipoTelefone.values());
        if (FacesUtil.isNotPostback()) {
            this.candidato = candidatoFacade.findByCPF(getUsuarioLogado().getUsuario().getCpf());
            if (this.candidato != null) {
                if(this.candidato.getRg() == null) {
                    this.candidato.setRg(new RG());
                }
                this.estado = this.candidato.getEndereco().getCidade().getEstado();
            }
            this.concurso = concursoFacade.findById(concurso.getId());
            this.inscricao = inscricaoService.pesquisarPorCandidatoEConcurso(candidato, concurso);
            this.localidades = localidadeFacade.findByConcurso(concurso);
            if(this.inscricao != null){
                this.local = inscricao.getCargoConcurso().getLocalidade();
                this.nivel = inscricao.getCargoConcurso().getCargo().getNivel();
                carregarNiveis();
                carregarCargos();
            } else {
                limparInscricao();
            }
            this.estados = estadoFacade.findAll();
            if (this.estado != null) {
                carregarCidades();
            }
        }
    }

    public InscricaoBean() {
        limpar();
        limpaTelefone();
        limparInscricao();
    }
        
    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }
    
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public List<Sexo> getSexos() {
        return sexos;
    }

    public List<EstadoCivil> getEstadosCivil() {
        return estadosCivil;
    }

    public List<Escolaridade> getEscolaridades() {
        return escolaridades;
    }

    public List<BracoDominante> getBracosDominante() {
        return bracosDominante;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public Telefone getTelefoneParaExcluir() {
        return telefoneParaExcluir;
    }

    public void setTelefoneParaExcluir(Telefone telefoneParaExcluir) {
        this.telefoneParaExcluir = telefoneParaExcluir;
    }

    public List<TipoTelefone> getTiposTelefone() {
        return tiposTelefone;
    }
    
    public List<Nivel> getNiveis() {
        return niveis;
    }

    public List<CargoConcurso> getCargosConcurso() {
        return cargosConcurso;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Localidade getLocal() {
        return local;
    }

    public void setLocal(Localidade local) {
        this.local = local;
    }
    
    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public List<Localidade> getLocalidades() {
        return localidades;
    }

    public void atualizaLinha(int linha) {
        this.linha = linha;
    }
    
    public boolean isEditandoInsc() {
        return this.inscricao != null && this.inscricao.getId() != null;
    }
    
    public boolean isEditandoTelefone() {
        return this.linha != null;
    }
    
    public void guardaTelefone(){
        this.candidato.adicionaTelefone(this.telefone, this.linha);
        FacesUtil.addSuccessMessage("Telefone salvo com sucesso!");
        limpaTelefone();
    }
    
    public void limpaTelefone(){
        this.telefone = new Telefone();
        this.linha = null;
    }
    
    public void removerCargoConcurso() {
        int index = linha;
        this.candidato.getTelefones().remove(index);
        FacesUtil.addSuccessMessage("Telefone excluído com sucesso!");
        limpaTelefone();
    }
    
    public void limpar() {
        this.concurso = new Concurso();
        this.candidato = new Candidato();
        this.candidato.setRg(new RG());
        this.candidato.setEndereco(new Endereco());
        this.estado = null;
        this.cidades = new ArrayList<>();
        this.localidades = new ArrayList<>();
        this.cargosConcurso = new ArrayList<>();
        this.niveis = new ArrayList<>();
    }
    
    public void limparInscricao(){
        this.nivel = new Nivel();
        this.inscricao = new Inscricao();
        this.inscricao.setCargoConcurso(new CargoConcurso());
        this.inscricao.setNecessidadeEspecial(new NecessidadeEspecial());
        this.inscricao.setInsencao(new Insencao());
        this.local = new Localidade();
    }
    
    public void carregarNiveis() {
        if(this.inscricao == null){
            this.nivel = new Nivel();
            this.niveis.clear();
            this.niveis = nivelFacade.findByLocalidadeAndConcurso(local, concurso);
        } else {
            this.niveis.clear();
            this.niveis = nivelFacade.findByLocalidadeAndConcurso(local,concurso);
        }
    }

    public void salvar(){
        try {
            this.inscricao.setCandidato(this.candidato);
            this.candidato.getInscricoes().add(this.inscricao);
            this.candidato = this.candidatoService.editar(this.candidato);
            this.inscricao = this.inscricaoService.salvar(this.inscricao);
            FacesProducer.getExternalContext().redirect("comprovanteInscricao.xhtml?inscricao=" + this.inscricao.getId());
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao realizar a inscrição: " + e.getMessage());
        }
    }
    
    public void carregarCargos() {
        this.cargosConcurso.clear();
        this.cargosConcurso = cargoConcursoFacade.findByConcursoAndNivelAndLocalidade(concurso, nivel, local);
    }
    
    public void carregarCidades() {
        this.cidades.clear();
        this.cidades = cidadeFacade.findByEstado(estado);
    }

    public void consultaCep() throws NegocioException {
        String cep = this.candidato.getEndereco().getCep();
        this.candidato.setEndereco(cepService.consultarCep(cep));
        this.estado = this.candidato.getEndereco().getCidade().getEstado();
        carregarCidades();
    }
    
    private UsuarioSistema getUsuarioLogado() {
        UsuarioSistema usuario = null;

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

        if (auth != null && auth.getPrincipal() != null) {
            usuario = (UsuarioSistema) auth.getPrincipal();
        }

        return usuario;
    }
    
    public String onFlowProcess(FlowEvent event){
        return event.getNewStep();
    }
}
