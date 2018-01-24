/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.PermissaoComissao;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface PermissaoComissaoFacadeLocal {

    public PermissaoComissao save(PermissaoComissao nivel);

    public void remove(PermissaoComissao nivel);

    public PermissaoComissao findById(Long id);

    public List<PermissaoComissao> findAll();
}
