/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Funcionario;
import br.ufac.sion.model.Usuario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class FuncionarioFacade extends AbstractFacade<Funcionario, Long> implements FuncionarioFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FuncionarioFacade() {
        super(Funcionario.class);

    }

    public Funcionario findByLogin(String login) {

        Funcionario funcionario = null;

        try {
            funcionario = em.createQuery("from Funcionario where usuario.login = :login", Funcionario.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum funcionario econtrado com o e-mail informado.");
        }
        return funcionario;
    }

    @Override
    public Funcionario save(Funcionario entity) {
        throw new UnsupportedOperationException("Operação não suportada! Para salvar utilize FuncionarioService."); //To change body of generated methods, choose Tools | Templates.
    }

}
