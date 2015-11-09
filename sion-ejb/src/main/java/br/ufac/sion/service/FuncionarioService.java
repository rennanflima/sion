/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.model.Funcionario;
import br.ufac.sion.util.GeraSenha;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.util.EnviaEmail;
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
}
