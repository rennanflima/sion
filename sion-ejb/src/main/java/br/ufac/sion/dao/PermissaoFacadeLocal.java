/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Permissao;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface PermissaoFacadeLocal {

    public Permissao save(Permissao grupo);

    public void remove(Permissao grupo);

    public Permissao findById(Long id);

    public List<Permissao> findAll();
    
}
