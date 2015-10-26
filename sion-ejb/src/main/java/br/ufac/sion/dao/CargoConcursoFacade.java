/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
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
public class CargoConcursoFacade extends AbstractFacade<CargoConcurso, Long> implements CargoConcursoFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CargoConcursoFacade() {
        super(CargoConcurso.class);
    }

    @Override
    public List<CargoConcurso> findByConcurso(Concurso concurso) {
        return em.createQuery("from CargoConcurso where concurso = :concurso", CargoConcurso.class)
                .setParameter("concurso", concurso)
                .getResultList();
    }

    @Override
    public List<CargoConcurso> findByLocalidade(Localidade localidade) {
        return em.createQuery("from CargoConcurso where localidade = :local", CargoConcurso.class)
                .setParameter("local", localidade)
                .getResultList();
    }

    @Override
    public List<CargoConcurso> findByConcursoAndNivel(Concurso concurso, Nivel nivel) {
        return em.createQuery("from CargoConcurso where concurso = :concurso and cargo.nivel = :nivel", CargoConcurso.class)
                .setParameter("concurso", concurso)
                .setParameter("nivel", concurso)
                .getResultList();
    }

}
