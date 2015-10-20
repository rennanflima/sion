/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.ContaBancaria;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface ContaBancariaFacadeLocal {

    public ContaBancaria save(ContaBancaria contaBancaria);

    public void remove(ContaBancaria contaBancaria);

    public ContaBancaria findById(Long id);

    public List<ContaBancaria> findAll();

}
