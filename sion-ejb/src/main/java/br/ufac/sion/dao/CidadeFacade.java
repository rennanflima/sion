/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Cidade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
        return em.createQuery("from Cidades cid where cid.nome like :cidade and cid.estado.id = :estado order by cid.nome", Cidade.class)
                .setParameter("cidade", nome)
                .setParameter("estado", id)
                .getSingleResult();
    }

}
