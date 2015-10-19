/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Boleto;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface BoletoFacadeLocal {

    public Boleto save(Boleto boleto);

    public void remove(Boleto boleto);

    public Boleto findById(Long id);

    public List<Boleto> findAll();

}
