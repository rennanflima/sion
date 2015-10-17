/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Localidade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rennan
 */
@Stateless
public class LocalidadeFacade extends AbstractFacade<Localidade> implements LocalidadeFacadeLocal {
    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocalidadeFacade() {
        super(Localidade.class);
    }
    
}
