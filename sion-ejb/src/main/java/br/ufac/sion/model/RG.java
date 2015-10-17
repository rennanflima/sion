/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model;

import br.ufac.sion.model.util.LocalDateConverter;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

/**
 *
 * @author Rennan
 */
@Embeddable
public class RG {

    private String numero;
    private String orgaoExpedidor;
    private LocalDate dataExpedicao;

    @Column(name = "rg_numero", length = 20)
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Column(name = "rg_orgao_expedidor", length = 20)
    public String getOrgaoExpedidor() {
        return orgaoExpedidor;
    }

    public void setOrgaoExpedidor(String orgaoExpedidor) {
        this.orgaoExpedidor = orgaoExpedidor;
    }

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "rg_data_expedicao")
    public LocalDate getDataExpedicao() {
        return dataExpedicao;
    }

    public void setDataExpedicao(LocalDate dataExpedicao) {
        this.dataExpedicao = dataExpedicao;
    }

}
