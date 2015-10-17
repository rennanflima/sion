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
 * @author rennan.lima
 */
@Local
public interface CargoVagaFacadeLocal {

    public void save(CargoVaga cargoVaga);

    public void remove(CargoVaga cargoVaga);

    public CargoVaga findById(Long id);

    public List<CargoVaga> findAll();

}
