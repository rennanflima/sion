/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Estado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface EstadoFacadeLocal {

    public Estado save(Estado estado);

    public void remove(Estado estado);

    public Estado findById(Long id);

    public List<Estado> findAll();
    
    public Estado findByUf(String uf);

}
