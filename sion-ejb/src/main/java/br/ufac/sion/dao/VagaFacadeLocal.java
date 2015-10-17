/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Vaga;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface VagaFacadeLocal {

    public void save(Vaga vaga);

    public void remove(Vaga vaga);

    public Vaga findById(Long id);

    public List<Vaga> findAll();

}
