/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Cidade;
import br.ufac.sion.model.Estado;
import br.ufac.sion.model.vo.FiltroCidades;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Rennan
 */
@Stateless
public class CidadeFacade extends AbstractFacade<Cidade, Long> implements CidadeFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CidadeFacade() {
        super(Cidade.class);
    }

    @Override
    public Cidade findByNomeAndIdEstado(String nome, Long id) {
        return em.createQuery("from Cidade cid where cid.nome like :cidade and cid.estado.id = :estado order by cid.nome", Cidade.class)
                .setParameter("cidade", nome)
                .setParameter("estado", id)
                .getSingleResult();
    }

    @Override
    public List<Cidade> findByEstado(Estado estado) {
        return em.createQuery("from Cidade where estado = :estado", Cidade.class)
                .setParameter("estado", estado)
                .getResultList();
    }

    @Override
    public List<Cidade> buscaTodosComPaginacao(FiltroCidades filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);

        criteria.setFirstResult(filtro.getPrimeiroRegistro());
        criteria.setMaxResults(filtro.getQuantidadeRegistros());

        return criteria.list();
    }

    @Override
    public int contaCidades(FiltroCidades filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);

        criteria.setProjection(Projections.rowCount());

        return ((Number) criteria.uniqueResult()).intValue();
    }

    private Criteria criarCriteriaParaFiltro(FiltroCidades filtro) {
        
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Cidade.class);

        if (StringUtils.isNotEmpty(filtro.getNome())) {
            criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
        }
        if (filtro.getEstado() != null && filtro.getEstado().getId() != null) {
            criteria.add(Restrictions.eq("estado", filtro.getEstado()));
        }
    

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

}
