/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Inscricao;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface InscricaoFacadeLocal {

    void create(Inscricao inscricao);

    void edit(Inscricao inscricao);

    void remove(Inscricao inscricao);

    Inscricao find(Object id);

    List<Inscricao> findAll();

    List<Inscricao> findRange(int[] range);

    int count();
    
}
