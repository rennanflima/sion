/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Inscricao;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class InscricaoFacade extends AbstractFacade<Inscricao, Long> implements InscricaoFacadeLocal {
    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InscricaoFacade() {
        super(Inscricao.class);
    }

    @Override
    public Inscricao save(Inscricao entity) {
        throw new UnsupportedOperationException("Operação não suportada! Para salvar utilize InscricaoService."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Inscricao entity) {
        throw new UnsupportedOperationException("Operação não suportada! Não é possível excluir uma inscrição."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Inscricao> findByCandidato(Candidato candidato){
        return em.createQuery("SELECT i FROM Inscricao i WHERE i.candidato = :candidato", Inscricao.class)
                .setParameter("candidato", candidato)
                .getResultList();
    }
    
}
