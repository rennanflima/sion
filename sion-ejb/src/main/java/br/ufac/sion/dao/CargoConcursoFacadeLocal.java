/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.CargoConcurso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface CargoConcursoFacadeLocal {

    void create(CargoConcurso cargoConcurso);

    void edit(CargoConcurso cargoConcurso);

    void remove(CargoConcurso cargoConcurso);

    CargoConcurso find(Object id);

    List<CargoConcurso> findAll();

    List<CargoConcurso> findRange(int[] range);

    int count();
    
}
