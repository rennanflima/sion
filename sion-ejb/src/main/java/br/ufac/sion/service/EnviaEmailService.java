/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.service.util.InfoEmail;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 *
 * @author Rennan
 */
@Singleton
@Startup
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class EnviaEmailService implements Serializable {

    @Resource(mappedName = "jms/emailQueue")
    private Queue emailQueue;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    @Lock(LockType.WRITE)
    public void processaEnvioDeEmail(InfoEmail infoEmail, String nome, String login, String senha) throws IOException {
        infoEmail.setCorpo(geraCorpoEmail(nome, login, senha));
        context.createProducer().send(emailQueue, infoEmail);
    }

    private String geraCorpoEmail(String nome, String login, String senha) throws IOException {
        System.out.println("Caminho: " + getClass().getResourceAsStream("/acesso.html"));
        InputStream stream = getClass().getResourceAsStream("/acesso.html");
        byte[] acessoBytes = new byte[stream.available()];
        stream.read(acessoBytes);
        stream.close();
        String body = new String(acessoBytes);
        body = body.replaceAll("@@@NOME_USUARIO@@@", nome);
        body = body.replaceAll("@@@LOGIN@@@", login);
        body = body.replaceAll("@@@SENHA@@@", senha);
        return body;
    }
}
