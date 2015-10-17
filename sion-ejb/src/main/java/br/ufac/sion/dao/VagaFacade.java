/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Vaga;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rennan
 */
@Stateless
public class VagaFacade extends AbstractFacade<Vaga> implements VagaFacadeLocal {
    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VagaFacade() {
        super(Vaga.class);
    }
    
}
