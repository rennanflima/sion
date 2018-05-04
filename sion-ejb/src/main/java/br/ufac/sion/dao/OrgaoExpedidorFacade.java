/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.OrgaoExpedidor;
import br.ufac.sion.model.vo.FiltroOrgaoExpedidor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class OrgaoExpedidorFacade extends AbstractFacade<OrgaoExpedidor, Long> implements OrgaoExpedidorFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrgaoExpedidorFacade() {
        super(OrgaoExpedidor.class);
    }

    @Override
    public List<OrgaoExpedidor> findByNomeAndSigla(FiltroOrgaoExpedidor filtro) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(OrgaoExpedidor.class);

        if (StringUtils.isNotBlank(filtro.getNome())) {
            criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
        }

        if (StringUtils.isNotBlank(filtro.getSigla())) {
            criteria.add(Restrictions.ilike("sigla", filtro.getSigla(), MatchMode.ANYWHERE));
        }

        return criteria.addOrder(Order.asc("nome")).list();
    }
}
