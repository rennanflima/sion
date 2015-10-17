/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.ArquivoRetorno;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface ArquivoRetornoFacadeLocal {

    public void save(ArquivoRetorno arquivoRetorno);

    public void remove(ArquivoRetorno arquivoRetorno);

    public ArquivoRetorno findById(Long id);

    public List<ArquivoRetorno> findAll();

}
