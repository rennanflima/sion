/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.StatusConcurso;
import java.util.List;
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

    @Override
    public List<Concurso> findByInscricoesAbertas() {
        return em.createQuery("FROM Concurso c WHERE status = :situacao", Concurso.class)
                .setParameter("situacao", StatusConcurso.INSCRICOES_ABERTAS)
                .getResultList();
    }
    
    @Override
    public List<Concurso> findByAberto() {
        return em.createQuery("FROM Concurso c WHERE status = :situacao", Concurso.class)
                .setParameter("situacao", StatusConcurso.ABERTO)
                .getResultList();
    }

    @Override
    public List<Concurso> findByOutrasFases() {
        return em.createQuery("FROM Concurso c WHERE status != :aberto and status != :inscricaoAberta", Concurso.class)
                .setParameter("aberto", StatusConcurso.ABERTO)
                .setParameter("inscricaoAberta", StatusConcurso.INSCRICOES_ABERTAS)
                .getResultList();
    }
    
    @Override
    public Concurso save(Concurso entity) {
         throw new UnsupportedOperationException("Operação não suportada! Para salvar utilize ConcursoService."); //To change body of generated methods, choose Tools | Templates.
    }
    

}
