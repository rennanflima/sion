/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Boleto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class BoletoFacade extends AbstractFacade<Boleto, Long> implements BoletoFacadeLocal {
    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BoletoFacade() {
        super(Boleto.class);
    }
    
    @Override
    public Boleto save(Boleto entity) {
         throw new UnsupportedOperationException("Operação não suportada! Para salvar utilize BoletoService."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
