/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Localidade;
import br.ufac.sion.model.Nivel;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class NivelFacade extends AbstractFacade<Nivel, Long> implements NivelFacadeLocal {
    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NivelFacade() {
        super(Nivel.class);
    }

    @Override
    public List<Nivel> findByLocalidade(Localidade local) {
        return em.createQuery("SELECT DISTINCT(n) FROM CargoConcurso cc JOIN cc.cargo.nivel n WHERE cc.localidade = :local", Nivel.class)
                .setParameter("local", local)
                .getResultList();
    }
    
    
}
