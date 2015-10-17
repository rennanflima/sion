/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Concurso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface ConcursoFacadeLocal {

    void create(Concurso concurso);

    void edit(Concurso concurso);

    void remove(Concurso concurso);

    Concurso find(Object id);

    List<Concurso> findAll();

    List<Concurso> findRange(int[] range);

    int count();
    
}
