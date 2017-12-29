/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.util.GeraSenha;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.TokenRecuperacao;
import br.ufac.sion.service.util.InfoEmail;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import br.ufac.sion.dao.TokenRecuperacaoFacadeLocal;
import br.ufac.sion.model.Usuario;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class CandidatoService {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @EJB
    private EnviaEmailService enviaEmailService;

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    @EJB
    private TokenRecuperacaoFacadeLocal tokenRecuperacaoFacade;

    private GeraSenha geraSenha;

    private InfoEmail infoEmail;

    public Candidato salvar(Candidato candidato) throws NegocioException {
        geraSenha = new GeraSenha();
        
        candidato.getUsuario().setNome(candidato.getNome());
        candidato.getUsuario().setSenha(geraSenha.ecripta(candidato.getUsuario().getSenha()));
        candidato.getUsuario().setLogin(candidato.getCpf());
        try {
            candidato = em.merge(candidato);
            return candidato;
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

    public Candidato editar(Candidato candidato) throws NegocioException {
        try {
            return em.merge(candidato);

        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

    public void alterarSenha(String oldSenha, String senha, Candidato candidato) throws NegocioException {
        String temp;
        temp = new GeraSenha().ecripta(oldSenha);
        if (temp.equals(candidato.getUsuario().getSenha())) {
            candidato.getUsuario().setSenha(new GeraSenha().ecripta(senha));
            em.merge(candidato);
        } else {
            throw new NegocioException("Sua senha antiga não corresponde a que está cadastrada");
        }
    }

    public void solicitarNovaSenha(String email) throws NegocioException {
        infoEmail = new InfoEmail();
        Candidato candidato = candidatoFacade.findByEmail(email);
        TokenRecuperacao tokenRecuperacao = new TokenRecuperacao();
        if (candidato != null) {
            try {
                tokenRecuperacao.setToken(Base64.getEncoder().encodeToString(candidato.getUsuario().getEmail().getBytes("utf-8")));
                tokenRecuperacao.setCandidato(candidato);
                tokenRecuperacao.setDataVencimento(LocalDateTime.now().plusHours(5));

                tokenRecuperacao = tokenRecuperacaoFacade.save(tokenRecuperacao);

                infoEmail.setPara(candidato.getUsuario().getEmail());
                infoEmail.setAssunto("Solicitação de Recuperação de Senha - SION");
                infoEmail.setCorpo(geraCorpoEmailSolicitacaoRecuperacaoSenha(candidato.getNome(), tokenRecuperacao.getToken()));
                enviaEmailService.processaEnvioDeEmail(infoEmail);
            } catch (UnsupportedEncodingException e) {
                Logger.getLogger(CandidatoService.class.getName()).log(Level.SEVERE, null, e);
                throw new NegocioException("Ocorreu um erro ao gerar o token: " + e.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(CandidatoService.class.getName()).log(Level.SEVERE, null, ex);
                throw new NegocioException("Ocorreu um erro ao encontrar o template de solicitação de envio de senha: " + ex.getMessage());
            } catch (Exception exc) {
                Logger.getLogger(CandidatoService.class.getName()).log(Level.SEVERE, null, exc);
                throw new NegocioException(exc.getMessage());
            }
        } else {
            throw new NegocioException("Não foi encontrado(a) nenhum(a) candidato(a) cadastrado(a) com esse email!");
        }
    }

    public void alterarSenhaComToken(String senha, TokenRecuperacao tokenRecuperacao) throws NegocioException {
        infoEmail = new InfoEmail();
        try {
            Candidato candidato = tokenRecuperacao.getCandidato();
            candidato.getUsuario().setSenha(new GeraSenha().ecripta(senha));
            em.merge(candidato);
            tokenRecuperacaoFacade.remove(tokenRecuperacao);
            infoEmail.setPara(candidato.getUsuario().getEmail());
            infoEmail.setAssunto("Confirmação de Alteração de Senha - SION");
            infoEmail.setCorpo(geraCorpoEmailConfirmacaoAlteracaoSenha(candidato.getNome()));
            enviaEmailService.processaEnvioDeEmail(infoEmail);
        } catch (IOException ex) {
            Logger.getLogger(CandidatoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new NegocioException("Ocorreu um erro ao encontrar o template de confirmação de alteração de senha: " + ex.getMessage());
        }
    }

    private String geraCorpoEmailSolicitacaoRecuperacaoSenha(String nome, String token) throws IOException {
        InputStream stream = getClass().getResourceAsStream("/redefinir.html");
        byte[] acessoBytes = new byte[stream.available()];
        stream.read(acessoBytes);
        stream.close();
        String body = new String(acessoBytes);
        body = body.replaceAll("@@@NOME_USUARIO@@@", nome);
        body = body.replaceAll("@@@TOKEN@@@", token);
        return body;
    }
    
    private String geraCorpoEmailConfirmacaoAlteracaoSenha(String nome) throws IOException {
        InputStream stream = getClass().getResourceAsStream("/senha_mudada.html");
        byte[] acessoBytes = new byte[stream.available()];
        stream.read(acessoBytes);
        stream.close();
        String body = new String(acessoBytes);
        body = body.replaceAll("@@@NOME_USUARIO@@@", nome);
        return body;
    }
}
