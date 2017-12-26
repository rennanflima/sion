/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.OrgaoExpedidor;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface OrgaoExpedidorFacadeLocal {

    public OrgaoExpedidor save(OrgaoExpedidor orgaoExpedidor);

    public void remove(OrgaoExpedidor orgaoExpedidor);

    public OrgaoExpedidor findById(Long id);

    public List<OrgaoExpedidor> findAll();
}
