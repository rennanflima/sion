/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rennan
 */
@Embeddable
public class RG implements Serializable {

    @Column(name = "rg_numero", length = 20)
    private String numero;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "rg_orgao_expedidor_id")
    private OrgaoExpedidor orgaoExpedidor;
    @Column(name = "rg_data_expedicao")
    private LocalDate dataExpedicao;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "rg_estado_id")
    private Estado estado = new Estado();

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public OrgaoExpedidor getOrgaoExpedidor() {
        return orgaoExpedidor;
    }

    public void setOrgaoExpedidor(OrgaoExpedidor orgaoExpedidor) {
        this.orgaoExpedidor = orgaoExpedidor;
    }

    public LocalDate getDataExpedicao() {
        return dataExpedicao;
    }

    public void setDataExpedicao(LocalDate dataExpedicao) {
        this.dataExpedicao = dataExpedicao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return this.numero + " " + this.orgaoExpedidor + "/" + this.getEstado().getSigla() + " - " +this.dataExpedicao;
    }

}
