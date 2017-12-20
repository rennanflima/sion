/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.Usuario;
import br.ufac.sion.util.GeraSenha;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario, Long> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    private GeraSenha geraSenha;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario findByLogin(String login) {
        Usuario usuario = null;

        try {
            usuario = em.createQuery("from Usuario where lower(login) = :login", Usuario.class)
                    .setParameter("login", login.toLowerCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            //nenhum usuário econtrado com o e-mail informado
            System.out.println("Nenhum usuário econtrado com o e-mail informado.");
        }
        return usuario;
    }

    public void alterarSenha(String oldSenha, String senha, Usuario usuario) throws NegocioException {
        String temp;
        temp = new GeraSenha().ecripta(oldSenha);
        if (temp.equals(usuario.getSenha())) {
            usuario.setSenha(new GeraSenha().ecripta(senha));
            em.merge(usuario);
        } else {
            throw new NegocioException("Sua senha antiga não corresponde a que está cadastrada");
        }
    }

    public Usuario findByLoginWithPermissoes(String login) {
        Usuario usuario = null;
        try {
            usuario = em.createQuery("SELECT u FROM Usuario u JOIN FETCH u.permissoes p WHERE LOWER(u.login) = :login", Usuario.class)
                    .setParameter("login", login.toLowerCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhuma permissão econtrada com o login informado.");
            return null;
        }
        return usuario;
    }
    
    public Usuario findByLoginWithGrupo(String login) {
        Usuario usuario = null;
        try {
            usuario = em.createQuery("SELECT u FROM Usuario u JOIN FETCH u.grupos g WHERE LOWER(u.login) = :login", Usuario.class)
                    .setParameter("login", login.toLowerCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum grupo econtrado com o login informado.");
            return null;
        }
        return usuario;
    }
    
}
