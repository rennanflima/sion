/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Estado;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rennan
 */
@Stateless
public class EstadoFacade extends AbstractFacade<Estado, Long> implements EstadoFacadeLocal{
    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoFacade() {
        super(Estado.class);
    }

    @Override
    public Estado findByUf(String uf) {
        return em.createQuery("from Estados e where e.sigla = :uf", Estado.class)
                .setParameter("uf", uf)
                .getSingleResult();
    }
    
}
