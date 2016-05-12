/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.TokenRecuperacao;
import java.time.LocalDateTime;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rennan
 */
@Stateless
public class TokenRecuperacaoFacade extends AbstractFacade<TokenRecuperacao, Long> implements TokenRecuperacaoFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TokenRecuperacaoFacade() {
        super(TokenRecuperacao.class);
    }

    @Override
    public TokenRecuperacao findByToken(String token) {
        TokenRecuperacao tokenRecuperacao = null;
        LocalDateTime agora = LocalDateTime.now();
        try {
            tokenRecuperacao = em.createQuery("select t from TokenRecuperacao as t inner join t.candidato as c where t.token = :token", TokenRecuperacao.class)
                    .setParameter("token", token)
                    .getSingleResult();
            if (tokenRecuperacao != null) {
                if (agora.isAfter(tokenRecuperacao.getDataVencimento())) {
                    remove(tokenRecuperacao);
                    tokenRecuperacao = null;
                }
            }
        } catch (NoResultException e) {
            System.out.println("Nenhuma solicitação encontrada.");
        }
        return tokenRecuperacao;
    }
}
