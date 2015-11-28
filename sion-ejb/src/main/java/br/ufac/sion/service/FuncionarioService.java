/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.dao.FuncionarioFacadeLocal;
import br.ufac.sion.dao.UsuarioFacadeLocal;
import br.ufac.sion.model.Funcionario;
import br.ufac.sion.util.GeraSenha;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.Usuario;
import br.ufac.sion.util.EnviaEmail;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class FuncionarioService {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;

    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    private GeraSenha geraSenha;

    private EnviaEmail email;

    public void salvar(Funcionario funcionario) throws NegocioException {

        geraSenha = new GeraSenha();
        email = new EnviaEmail();

        try {
            if (funcionario.getId() == null) {
                String senha = geraSenha.geraSenha();
                funcionario.getUsuario().setSenha(geraSenha.ecripta(senha));
                email.enviaLoginESenha(funcionario.getEmail(), "Acesso ao SION", funcionario.getUsuario().getLogin(), senha);
            }
            em.merge(funcionario);
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

    public void alterarSenha(String oldSenha, String senha, Funcionario funcionario) throws NegocioException {
        String temp;
        temp = new GeraSenha().ecripta(oldSenha);
        if (temp.equals(funcionario.getUsuario().getSenha())) {
            funcionario.getUsuario().setSenha(new GeraSenha().ecripta(senha));
            em.merge(funcionario);
        } else {
            throw new NegocioException("Sua senha antiga não corresponde a que está cadastrada");
        }
    }

    public void esquecerSenha(Integer mat, String login) throws NegocioException {
        email = new EnviaEmail();
        geraSenha = new GeraSenha();
        try {
            Funcionario funcionario = funcionarioFacade.findByMatricula(mat);
            if (funcionario != null) {
                if (login.equals(funcionario.getUsuario().getLogin())) {
                    String senha = geraSenha.geraSenha();
                    funcionario.getUsuario().setSenha(geraSenha.ecripta(senha));
                    em.merge(funcionario);
                    email.enviaLoginESenha(funcionario.getEmail(), "Recuperação de Senha", funcionario.getUsuario().getLogin(), senha);
                } else {
                    throw new NegocioException("O login informado não corresponde ao que foi cadastrado");
                }
            } else {
                throw new NegocioException("Não foi encontrado(a) nenhum(a) funcionário(a) cadastrado(a) com essa matrícula");
            }
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
