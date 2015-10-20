/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Rennan
 */
@Embeddable
public class Endereco implements Serializable {

    @Column(name = "end_logradouro", length = 100)
    private String logradouro;
    @Column(name = "end_numero")
    private Integer numero;
    @Column(name = "end_complemento", length = 60)
    private String complemento;
    @Column(name = "end_bairro", length = 30)
    private String bairro;
    @Column(name = "end_cep", length = 10)
    private String cep;
    @ManyToOne
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidade = new Cidade();

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

}
