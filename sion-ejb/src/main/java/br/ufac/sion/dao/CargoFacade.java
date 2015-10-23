/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Cargo;
import br.ufac.sion.model.Nivel;
import br.ufac.sion.model.Setor;
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
public class CargoFacade extends AbstractFacade<Cargo, Long> implements CargoFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CargoFacade() {
        super(Cargo.class);
    }

    @Override
    public List<Cargo> findByDescricaoAndNivel(Cargo filtro) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Cargo.class);

        if (StringUtils.isNotBlank(filtro.getDescricao())) {
            criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
        }

        if (filtro.getNivel() != null) {
            criteria.add(Restrictions.eq("nivel", filtro.getNivel()));
        }

        return criteria.addOrder(Order.asc("descricao")).list();
    }

    @Override
    public List<Cargo> findByNivel(Nivel nivel) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Cargo.class)
                .add(Restrictions.eq("nivel", nivel));
        
        return criteria.addOrder(Order.asc("descricao")).list();
        
    }
}
