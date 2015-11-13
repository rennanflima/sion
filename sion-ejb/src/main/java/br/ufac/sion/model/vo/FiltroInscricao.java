/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.vo;

import br.ufac.sion.model.CargoConcurso;
import java.io.Serializable;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author rennan.lima
 */
public class FiltroInscricao implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nomeCandidato;
    @CPF
    private String cpf;
    private Integer numeroInscricao;
    private CargoConcurso cargo;
    private int primeiroRegistro;
    private int quantidadeRegistros;
    private String propriedadeOrdenacao;
    private boolean ascendente;

    public String getNomeCandidato() {
        return nomeCandidato;
    }

    public void setNomeCandidato(String nomeCandidato) {
        this.nomeCandidato = nomeCandidato;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Integer getNumeroInscricao() {
        return numeroInscricao;
    }

    public void setNumeroInscricao(Integer numeroInscricao) {
        this.numeroInscricao = numeroInscricao;
    }

    public CargoConcurso getCargo() {
        return cargo;
    }

    public void setCargo(CargoConcurso cargo) {
        this.cargo = cargo;
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

    public String getPropriedadeOrdenacao() {
        return propriedadeOrdenacao;
    }

    public void setPropriedadeOrdenacao(String propriedadeOrdenacao) {
        this.propriedadeOrdenacao = propriedadeOrdenacao;
    }

    public boolean isAscendente() {
        return ascendente;
    }

    public void setAscendente(boolean ascendente) {
        this.ascendente = ascendente;
    }

}
