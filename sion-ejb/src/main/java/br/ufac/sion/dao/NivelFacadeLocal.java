/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Nivel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface NivelFacadeLocal {

    void create(Nivel nivel);

    void edit(Nivel nivel);

    void remove(Nivel nivel);

    Nivel find(Object id);

    List<Nivel> findAll();

    List<Nivel> findRange(int[] range);

    int count();
    
}
