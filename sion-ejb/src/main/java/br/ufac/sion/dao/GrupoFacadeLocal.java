/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Grupo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface GrupoFacadeLocal {

    public Grupo save(Grupo grupo);

    public void remove(Grupo grupo);

    public Grupo findById(Long id);

    public List<Grupo> findAll();

    
}
