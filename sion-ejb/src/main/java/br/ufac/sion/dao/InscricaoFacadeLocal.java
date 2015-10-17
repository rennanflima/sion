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
 * @author rennan.lima
 */
@Local
public interface InscricaoFacadeLocal {

    public void save(Inscricao inscricao);

    public void remove(Inscricao inscricao);

    public Inscricao findById(Long id);

    public List<Inscricao> findAll();

}
