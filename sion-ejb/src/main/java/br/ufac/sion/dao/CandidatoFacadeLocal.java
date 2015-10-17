/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Candidato;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface CandidatoFacadeLocal {

    void create(Candidato candidato);

    void edit(Candidato candidato);

    void remove(Candidato candidato);

    Candidato find(Object id);

    List<Candidato> findAll();

    List<Candidato> findRange(int[] range);

    int count();
    
}
