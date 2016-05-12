/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Candidato;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class CandidatoFacade extends AbstractFacade<Candidato, Long> implements CandidatoFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CandidatoFacade() {
        super(Candidato.class);
    }

    @Override
    public Candidato findByCPF(String cpf) {

        Candidato candidato = null;
        String cpf_formatado = "";
        
        if(cpf.length() == 11){
            cpf_formatado = cpf.substring(0,3) + "." + cpf.substring(3,6) + "." + cpf.substring(6,9) + "-" + cpf.substring(9,11);
        } else {
            cpf_formatado = cpf;
        }
        
        try {
            candidato = em.createQuery("from Candidato where cpf = :cpf", Candidato.class)
                    .setParameter("cpf", cpf_formatado)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum candidato econtrado com o CPF informado.");
        }
        return candidato;
    }

    @Override
    public Candidato save(Candidato entity) {
        throw new UnsupportedOperationException("Operação não suportada! Para salvar utilize CandidatoService.");
    }

    @Override
    public Candidato findByEmail(String email) {
        Candidato candidato = null;
        try {
            candidato = em.createQuery("from Candidato where email = :email", Candidato.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum candidato econtrado com o CPF informado.");
        }
        return candidato;
    }

}
