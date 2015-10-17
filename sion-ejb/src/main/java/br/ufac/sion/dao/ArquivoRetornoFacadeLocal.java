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
 * @author Rennan
 */
@Local
public interface ArquivoRetornoFacadeLocal {

    void create(ArquivoRetorno arquivoRetorno);

    void edit(ArquivoRetorno arquivoRetorno);

    void remove(ArquivoRetorno arquivoRetorno);

    ArquivoRetorno find(Object id);

    List<ArquivoRetorno> findAll();

    List<ArquivoRetorno> findRange(int[] range);

    int count();
    
}
