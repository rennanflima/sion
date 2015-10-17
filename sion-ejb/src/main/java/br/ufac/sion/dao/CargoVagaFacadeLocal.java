/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.CargoVaga;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface CargoVagaFacadeLocal {

    void create(CargoVaga cargoVaga);

    void edit(CargoVaga cargoVaga);

    void remove(CargoVaga cargoVaga);

    CargoVaga find(Object id);

    List<CargoVaga> findAll();

    List<CargoVaga> findRange(int[] range);

    int count();
    
}
