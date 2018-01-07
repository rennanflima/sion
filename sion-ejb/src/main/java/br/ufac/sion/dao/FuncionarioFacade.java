/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Funcionario;
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

    @Override
    public Funcionario findByLogin(String login) {

        Funcionario funcionario = null;

        try {
            funcionario = em.createQuery("SELECT f FROM Funcionario f JOIN FETCH f.usuario u WHERE u.login = :login AND u.ativo = :ativo", Funcionario.class)
                    .setParameter("login", login)
                    .setParameter("ativo", Boolean.TRUE)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum funcionario encontrado com o login informado.");
        }
        return funcionario;
    }

    @Override
    public Funcionario save(Funcionario entity) {
        throw new UnsupportedOperationException("Operação não suportada! Para salvar utilize FuncionarioService."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Funcionario findByMatricula(Integer matricula) {
        Funcionario funcionario = null;

        try {
            funcionario = em.createQuery("from Funcionario where matricula = :mat", Funcionario.class)
                    .setParameter("mat", matricula)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum funcionario encontrado com a matricula informada.");
        }
        return funcionario;
    }

    @Override
    public Funcionario findFuncionarioWithPermissoes(Long id) {
        try {
            return em.createQuery("SELECT f FROM Funcionario f JOIN FETCH f.usuario u JOIN FETCH u.permissoes p WHERE f.id = :id", Funcionario.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public Funcionario findFuncionarioWithGrupoAndPermissoes(Long id) {
        return em.createQuery("SELECT f FROM Funcionario f JOIN FETCH f.usuario u JOIN FETCH u.grupos g JOIN FETCH u.permissoes p WHERE f.id = :id", Funcionario.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
