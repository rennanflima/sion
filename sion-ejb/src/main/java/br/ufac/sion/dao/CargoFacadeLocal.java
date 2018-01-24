/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.Nivel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface CargoFacadeLocal {

    public Cargo save(Cargo cargo);

    public void remove(Cargo cargo);

    public Cargo findById(Long id);

    public List<Cargo> findAll();

    public List<Cargo> findByDescricaoAndNivel(Cargo cargo);

    public List<Cargo> findByNivel(Nivel nivel);

}
