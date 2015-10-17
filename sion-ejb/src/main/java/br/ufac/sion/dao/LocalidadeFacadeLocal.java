/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Localidade;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface LocalidadeFacadeLocal {

    public void save(Localidade localidade);

    public void remove(Localidade localidade);

    public Localidade findById(Long id);

    public List<Localidade> findAll();
    
}
