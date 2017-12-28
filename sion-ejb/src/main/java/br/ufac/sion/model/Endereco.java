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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Rennan
 */
@Embeddable
public class Endereco implements Serializable {

    @NotBlank
    @Column(name = "end_logradouro", length = 100)
    private String logradouro;
    @NotNull
    @Column(name = "end_numero")
    private Integer numero;
    @NotBlank
    @Column(name = "end_complemento", length = 60)
    private String complemento;
    @NotBlank
    @Column(name = "end_bairro", length = 30)
    private String bairro;
    @NotBlank
    @Column(name = "end_cep", length = 10)
    private String cep;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "cidade_id")
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
