/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.model.Boleto;
import br.ufac.sion.model.SituacaoBoleto;
import br.ufac.sion.util.NegocioException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class BoletoService {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    public Boleto salvar(Boleto cobranca) throws NegocioException {
        cobranca.setSituacao(SituacaoBoleto.PENDENTE);
        Boleto b = perquisarPorInscricao(cobranca);
        try {
            if (b == null) {
                b = em.merge(cobranca);
            } else {
                b = em.merge(b);
            }
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }

        return b;
    }

    private Boleto perquisarPorInscricao(Boleto boleto) {
        Boleto b = null;
        try {
            b = em.createQuery("from Boleto b where b.sacado = :inscricao", Boleto.class)
                .setParameter("inscricao", boleto.getSacado())
                .getSingleResult();
        } catch (Exception e) {
        }
        return b;
    }
}
