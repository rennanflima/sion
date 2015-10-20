/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.mbs.util;

import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Vaga;

/**
 *
 * @author rennan.lima
 */
public class AddCargoVaga {

    private CargoConcurso[] listaCargos;
    private Vaga tipoVaga;
    private Integer quantidade;

    public CargoConcurso[] getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(CargoConcurso[] listaCargos) {
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
