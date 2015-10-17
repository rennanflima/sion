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
 * @author Rennan
 */
@Local
public interface BoletoFacadeLocal {

    void create(Boleto boleto);

    void edit(Boleto boleto);

    void remove(Boleto boleto);

    Boleto find(Object id);

    List<Boleto> findAll();

    List<Boleto> findRange(int[] range);

    int count();
    
}
