/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.controller;

import br.ufac.sion.inscricao.util.jsf.FacesUtil;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Usuario;
import br.ufac.sion.service.CandidatoService;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Rennan
 */
@Named
@ViewScoped
public class CadastroCandidatoBean implements Serializable {

    @EJB
    private CandidatoService candidatoService;

    private Candidato candidato;

    public CadastroCandidatoBean() {
        limpar();
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public void salvar() {
        try {
            this.candidato = this.candidatoService.salvar(candidato);
            FacesUtil.addSuccessMessage("Candidato salvo com sucesso!");
            limpar();
        } catch (Exception e) {
            FacesUtil.addErrorMessage("Erro ao salvar o candidato: " + e.getMessage());
        }
    }

    public void limpar() {
        this.candidato = new Candidato();
        this.candidato.setUsuario(new Usuario());
    }

    public boolean isEditando() {
        return this.candidato.getId() != null;
    }
}
