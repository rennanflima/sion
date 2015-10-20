/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.mbs;

import br.ufac.sion.mbs.util.AddCargoVaga;
import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.dao.CargoFacadeLocal;
import br.ufac.sion.dao.LocalidadeFacadeLocal;
import br.ufac.sion.dao.NivelFacadeLocal;
import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.CargoVaga;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Localidade;
import br.ufac.sion.model.Nivel;
import br.ufac.sion.service.ConcursoService;
import br.ufac.sion.util.NegocioException;
import br.ufac.sion.util.jsf.DateConverter;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rennan
 */
@ManagedBean
@ViewScoped
public class CadastroConcursoBean implements Serializable {

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    @EJB
    private LocalidadeFacadeLocal localidadeFacade;

    @EJB
    private CargoFacadeLocal cargoFacade;

    @EJB
    private NivelFacadeLocal nivelFacade;

    @EJB
    private ConcursoService concursoService;

    private Concurso concurso;

    private AddCargoVaga addQuantidadeVaga;

    private List<CargoVaga> cargosVaga = new ArrayList<>();

    private List<CargoConcurso> cargosConcurso = new ArrayList<>();

    private List<Cargo> cargos = new ArrayList<>();

    private List<Nivel> niveis = new ArrayList<>();

    private List<Localidade> localidades = new ArrayList<>();

    private Nivel nivel = new Nivel();

    private Integer linha;
    
    private Integer linhaCV;
    
    private CargoVaga cargoVagaConcurso;
    
    private CargoVaga cargoVagaConcursoParaExcluir;

    private CargoConcurso cargoConcurso;

    private CargoConcurso cargoConcursoParaExcluir;

    public void inicializar() {
        if (FacesUtil.isNotPostback()) {
            this.niveis = nivelFacade.findAll();
            this.localidades = localidadeFacade.findAll();
            if (isEditando()) {
                this.concurso = concursoService.buscarConcursoComCargos(concurso.getId());
                this.cargosConcurso = cargoConcursoFacade.findByConcurso(concurso);
            }
        }
    }

    public CadastroConcursoBean() {
        limpar();
        limparCargo();
        this.addQuantidadeVaga = new AddCargoVaga();
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

    public CargoConcurso getCargoConcurso() {
        return cargoConcurso;
    }

    public void setCargoConcurso(CargoConcurso cargoConcurso) {
        this.cargoConcurso = cargoConcurso;
        if (this.cargoConcurso.getCargo() != null) {
            this.nivel = this.cargoConcurso.getCargo().getNivel();
        }
    }

    public List<Nivel> getNiveis() {
        return niveis;
    }

    public void setNiveis(List<Nivel> niveis) {
        this.niveis = niveis;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public List<Localidade> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidade> localidades) {
        this.localidades = localidades;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public CargoConcurso getCargoConcursoParaExcluir() {
        return cargoConcursoParaExcluir;
    }

    public void setCargoConcursoParaExcluir(CargoConcurso cargoConcursoParaExcluir) {
        this.cargoConcursoParaExcluir = cargoConcursoParaExcluir;
    }

    public List<CargoConcurso> getCargosConcurso() {
        return cargosConcurso;
    }

    public List<CargoVaga> getCargosVaga() {
        return cargosVaga;
    }

    public void setCargosVaga(List<CargoVaga> cargosVaga) {
        this.cargosVaga = cargosVaga;
    }

    public AddCargoVaga getAddQuantidadeVaga() {
        return addQuantidadeVaga;
    }

    public void setAddQuantidadeVaga(AddCargoVaga addQuantidadeVaga) {
        this.addQuantidadeVaga = addQuantidadeVaga;
    }

    public CargoVaga getCargoVagaConcurso() {
        return cargoVagaConcurso;
    }

    public void setCargoVagaConcurso(CargoVaga cargoVagaConcurso) {
        this.cargoVagaConcurso = cargoVagaConcurso;
    }

    public CargoVaga getCargoVagaConcursoParaExcluir() {
        return cargoVagaConcursoParaExcluir;
    }

    public void setCargoVagaConcursoParaExcluir(CargoVaga cargoVagaConcursoParaExcluir) {
        this.cargoVagaConcursoParaExcluir = cargoVagaConcursoParaExcluir;
    }

    private void limpar() {
        this.concurso = new Concurso();
    }

    public void salvar() {
        try {
            this.concurso = concursoService.salvar(concurso);
            FacesUtil.addSuccessMessage("Concurso salvo com sucesso!");
        } catch (NegocioException e) {
            FacesUtil.addErrorMessage("Erro ao salvar o concurso: " + e.getMessage());
        }
    }

    public void preparaCargoConcurso() {
        int cod = this.concurso.getCargos().size() + 1;
        if (cod < 10) {
            this.cargoConcurso.setCodigo("COD0" + cod);
        } else {
            this.cargoConcurso.setCodigo("COD" + cod);
        }
    }

    public void atualizaLinha(int linha) {
        this.linha = linha;
    }

    public void atualizaLinhaCV(int linha) {
        this.linhaCV = linha;
    }
    
    public void guardaCargoConcurso() {
        this.cargoConcurso.setValor(this.cargoConcurso.getCargo().getNivel().getValor());
        this.cargoConcurso.setConcurso(concurso);
        this.concurso.adicionaCargo(this.cargoConcurso, this.linha);
        FacesUtil.addSuccessMessage("Cargo salvo com sucesso!");
        limparCargo();
        preparaCargoConcurso();
    }

    public void guardaVagaCargoConcurso() {
        CargoVaga vc;

        for (CargoConcurso cc : addQuantidadeVaga.getListaCargos()) {
            vc = new CargoVaga();
            vc.setCargo(cc);
            vc.setQuatidade(addQuantidadeVaga.getQuantidade());
            vc.setTipoVaga(addQuantidadeVaga.getTipoVaga());
            cc.adicionaVaga(vc, linhaCV);
        }
        this.addQuantidadeVaga = new AddCargoVaga();
        this.cargoVagaConcurso = new CargoVaga();
    }
    
    public void removerCargoVagaConcurso() {
        int index = linhaCV;
        this.cargosVaga.remove(index);
        FacesUtil.addSuccessMessage("Vaga excluída com sucesso!");
        this.cargoConcursoParaExcluir = new CargoConcurso();
        this.linhaCV = null;
    }

    public void removerCargoConcurso() {
        int index = linha;
        this.concurso.getCargos().remove(index);
        FacesUtil.addSuccessMessage("Cargo excluído com sucesso!");
        limparCargo();
    }

    public void carregarCargos() {
        this.cargos = cargoFacade.findByNivel(nivel);
    }

    public boolean isEditando() {
        return this.concurso.getId() != null;
    }

    public void limparCargo() {
        this.cargoConcurso = new CargoConcurso();
        this.nivel = new Nivel();
        this.linha = null;
    }
    
    public boolean isEditandoCargo() {
        return this.linha != null;
    }

}
