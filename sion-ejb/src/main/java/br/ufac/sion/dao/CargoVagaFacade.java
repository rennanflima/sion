/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.CargoVaga;
import br.ufac.sion.model.Concurso;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class CargoVagaFacade extends AbstractFacade<CargoVaga, Long> implements CargoVagaFacadeLocal {
    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CargoVagaFacade() {
        super(CargoVaga.class);
    }

    @Override
    public List<CargoVaga> findByConcurso(Concurso concurso) {
        return em.createQuery("from CargoVaga where cargoConcurso.concurso = :concurso", CargoVaga.class)
                .setParameter("concurso", concurso)
                .getResultList();
    }

    
}
