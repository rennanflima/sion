/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Rennan
 */
public class DataQuantidade implements Serializable{
    
    private Date data;
    private Long quantidade;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
    
    
}
