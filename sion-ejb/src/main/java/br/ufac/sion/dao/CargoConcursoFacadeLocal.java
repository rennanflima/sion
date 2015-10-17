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
 * @author rennan.lima
 */
@Local
public interface CargoConcursoFacadeLocal {

    public void save(CargoConcurso cargoConcurso);

    public void remove(CargoConcurso cargoConcurso);

    public CargoConcurso findById(Long id);

    public List<CargoConcurso> findAll();

}
