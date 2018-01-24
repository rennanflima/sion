/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Setor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface SetorFacadeLocal {

    public Setor save(Setor setor);

    public void remove(Setor setor);

    public Setor findById(Long id);

    public List<Setor> findAll();
    
}
