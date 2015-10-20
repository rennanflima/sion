/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Empresa;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface EmpresaFacadeLocal {

    public Empresa save(Empresa empresa);

    public void remove(Empresa empresa);

    public Empresa findById(Long id);

    public List<Empresa> findAll();

}
