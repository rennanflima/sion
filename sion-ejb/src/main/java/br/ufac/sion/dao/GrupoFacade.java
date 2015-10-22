/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Grupo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class GrupoFacade extends AbstractFacade<Grupo, Long> implements GrupoFacadeLocal{
    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    public GrupoFacade() {
        super(Grupo.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
