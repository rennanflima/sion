/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.vo;

import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Localidade;
import br.ufac.sion.model.Nivel;

/**
 *
 * @author Rennan
 */
public class FiltroInscritosRelatorio {

    private CargoConcurso cargo;
    private Concurso concurso;

    private int primeiroRegistro;
    private int quantidadeRegistros;


    public CargoConcurso getCargo() {
        return cargo;
    }

    public void setCargo(CargoConcurso cargo) {
        this.cargo = cargo;
    }

    public Concurso getConcurso() {
        return concurso;
    }

    public void setConcurso(Concurso concurso) {
        this.concurso = concurso;
    }

    public int getPrimeiroRegistro() {
        return primeiroRegistro;
    }

    public void setPrimeiroRegistro(int primeiroRegistro) {
        this.primeiroRegistro = primeiroRegistro;
    }

    public int getQuantidadeRegistros() {
        return quantidadeRegistros;
    }

    public void setQuantidadeRegistros(int quantidadeRegistros) {
        this.quantidadeRegistros = quantidadeRegistros;
    }

}
