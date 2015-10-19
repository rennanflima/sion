/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Concurso;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class ConcursoFacade extends AbstractFacade<Concurso, Long> implements ConcursoFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConcursoFacade() {
        super(Concurso.class);
    }

    @Override
    public Concurso findConcursoWithCargo(Long id) {
        return em.createQuery("SELECT c FROM Concurso c LEFT JOIN FETCH c.cargos cc WHERE c.id = :codigo", Concurso.class)
                .setParameter("codigo", id)
                .getSingleResult();
    }

}
