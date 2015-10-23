/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Funcionario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface FuncionarioFacadeLocal {

    public Funcionario save(Funcionario funcionario);

    public void remove(Funcionario funcionario);

    public Funcionario findById(Long id);

    public List<Funcionario> findAll();
    
    public Funcionario findByLogin(String login);
}
