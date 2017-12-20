/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.ContaBancaria;
import br.ufac.sion.model.Empresa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class ContaBancariaFacade extends AbstractFacade<ContaBancaria, Long> implements ContaBancariaFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    public ContaBancariaFacade() {
        super(ContaBancaria.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<ContaBancaria> findByEmpresa(Empresa empresa) {
        return em.createQuery("SELECT cb FROM ContaBancaria cb WHERE cb.cedente = :empresa", ContaBancaria.class)
                .setParameter("empresa", empresa)
                .getResultList();
    }

}
