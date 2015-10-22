/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.controller.util;

import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Vaga;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rennan.lima
 */
public class AddCargoVaga {

    private List<CargoConcurso> listaCargos = new ArrayList<>();
    private Vaga tipoVaga;
    private Integer quantidade;

    public List<CargoConcurso> getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(List<CargoConcurso> listaCargos) {
        this.listaCargos = listaCargos;
    }

    public Vaga getTipoVaga() {
        return tipoVaga;
    }

    public void setTipoVaga(Vaga tipoVaga) {
        this.tipoVaga = tipoVaga;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

}
