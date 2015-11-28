/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Localidade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class LocalidadeFacade extends AbstractFacade<Localidade, Long> implements LocalidadeFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocalidadeFacade() {
        super(Localidade.class);
    }

    @Override
    public List<Localidade> findByConcurso(Concurso concurso) {
        return em.createQuery("SELECT DISTINCT(l) FROM CargoConcurso cc JOIN cc.localidade l WHERE cc.concurso = :concurso", Localidade.class)
                .setParameter("concurso", concurso)
                .getResultList();
    }

}
