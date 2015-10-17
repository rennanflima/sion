/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Vaga;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface VagaFacadeLocal {

    void create(Vaga vaga);

    void edit(Vaga vaga);

    void remove(Vaga vaga);

    Vaga find(Object id);

    List<Vaga> findAll();

    List<Vaga> findRange(int[] range);

    int count();
    
}
