/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.model.Funcionario;
import br.ufac.sion.util.GeraSenha;
import br.ufac.sion.exception.NegocioException;
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

    public void salvar(Funcionario funcionario) throws NegocioException {

        geraSenha = new GeraSenha();

        try {
            if (funcionario.getId() == null) {
                funcionario.getUsuario().setSenha(geraSenha.geraSenhaEncriptada());
            }
            em.merge(funcionario);
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
