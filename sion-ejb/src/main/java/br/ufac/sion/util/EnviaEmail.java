/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util;

import br.ufac.sion.exception.NegocioException;
import java.util.Date;
import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author rennan.lima
 */
public class EnviaEmail {

    @Resource(name = "gmailSession")
    public Session mailSession;

    public void enviaLoginESenha(String para, String assunto, String login, String senha) throws NegocioException {
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("cpl.sescacre@gmail.com")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(para);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);//Assunto
            message.setText("Este é um e-mail automático. Não é necessário respondê-lo.\n\n"
                    + "Segue abaixo suas informações de acesso ao Sistema de Inscrições Online (SION): \n\n"
                    + "Login: " + login + "\nSenha: " + senha + "\n\nEstas informações são necessárias para o seu acesso ao sistema, portanto deverá fornecê-la sempre que utilizá-lo."
                    + " Esta senha foi gerada automaticamente e poderá ser trocada dentro do sistema.\n\n Atenciosamente,\n\nSistema de Inscrições Online - SION.");

            message.setHeader("X-Mailer", "My Mailer");

            message.setSentDate(new Date());

            /**
             * Método para enviar a mensagem criada
             */
            Transport.send(message);

            System.out.println("Mensagem Enviada!!!");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new NegocioException(e.getMessage());
        }

    }
}
