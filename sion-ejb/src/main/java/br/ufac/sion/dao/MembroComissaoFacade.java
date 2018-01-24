/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.MembroComissao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Rennan
 */
@Stateless
public class MembroComissaoFacade extends AbstractFacade<MembroComissao, Long> implements MembroComissaoFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MembroComissaoFacade() {
        super(MembroComissao.class);
    }

    @Override
    public List<MembroComissao> findWithFiltro(MembroComissao filtro) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(MembroComissao.class);

        if (filtro.getFuncionario() != null) {
            criteria.add(Restrictions.eq("funcionario", filtro.getFuncionario()));
        }

        if (filtro.getConcurso() != null) {
            criteria.add(Restrictions.eq("concurso", filtro.getConcurso()));
        }

        return criteria.list();
    }

}
