/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.MembroComissao;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface MembroComissaoFacadeLocal {

    public MembroComissao save(MembroComissao membroComissao);

    public void remove(MembroComissao membroComissao);

    public MembroComissao findById(Long id);

    public List<MembroComissao> findAll();
    
    public List<MembroComissao> findWithFiltro(MembroComissao filtro);

}
