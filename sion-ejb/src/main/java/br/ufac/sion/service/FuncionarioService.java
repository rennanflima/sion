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
import br.ufac.sion.service.util.InfoEmail;
import java.io.IOException;
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

    @EJB
    private EnviaEmailService enviaEmailService;

    private GeraSenha geraSenha;

    private InfoEmail infoEmail;

    public void salvar(Funcionario funcionario) throws NegocioException {
        infoEmail = new InfoEmail();
        geraSenha = new GeraSenha();
        try {
            if (funcionario.getId() == null) {
                String senha = geraSenha.geraSenha();
                funcionario.getUsuario().setSenha(geraSenha.ecripta(senha));

                infoEmail.setPara(funcionario.getEmail());
                infoEmail.setAssunto("Acesso ao SION");

                enviaEmailService.processaEnvioDeEmail(infoEmail, funcionario.getNome(), funcionario.getUsuario().getLogin(), senha);
            }
            em.merge(funcionario);
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

    public void esquecerSenha(Integer mat, String login) throws NegocioException {
        infoEmail = new InfoEmail();
        geraSenha = new GeraSenha();
        try {
            Funcionario funcionario = funcionarioFacade.findByMatricula(mat);
            if (funcionario != null) {
                if (login.equals(funcionario.getUsuario().getLogin())) {
                    String senha = geraSenha.geraSenha();
                    funcionario.getUsuario().setSenha(geraSenha.ecripta(senha));
                    em.merge(funcionario);

                    infoEmail.setPara(funcionario.getEmail());
                    infoEmail.setAssunto("Recuperação de Senha");
                    
                    enviaEmailService.processaEnvioDeEmail(infoEmail, funcionario.getNome(), funcionario.getUsuario().getLogin(), senha);
                } else {
                    throw new NegocioException("O login informado não corresponde ao que foi cadastrado");
                }
            } else {
                throw new NegocioException("Não foi encontrado(a) nenhum(a) funcionário(a) cadastrado(a) com essa matrícula");
            }
        } catch (IOException ex) {
            throw new NegocioException("Arquivo de configuração não encontrado.");
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

}
