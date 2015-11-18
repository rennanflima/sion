/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util;

import br.ufac.sion.exception.NegocioException;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author rennan.lima
 */
public class EnviaEmail {

    public void enviaLoginESenha(String para, String assunto, String login, String senha) throws Exception {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Gmail
         */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("noreplay@gmail.com", "122");
                    }
               });
        

        /**
         * Ativa Debug para sessão
         */
        session.setDebug(true);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreplay@gmail.com")); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(para);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);//Assunto
            message.setText("Este é um e-mail automático. Não é necessário respondê-lo.\n\n"
                    + "Segue abaixo suas informações de acesso ao Sistema de Inscrições Online (SION): \n\n"
                    + "Login: " + login + "\nSenha: " + senha + "\n\nEstas informações são necessárias para o seu acesso ao sistema, portanto deverá fornecê-la sempre que utilizá-lo."
                    + " Esta senha foi gerada automaticamente e poderá ser trocada dentro do sistema.\n\n Atenciosamente,\n\nSistema de Inscrições Online - SION.");

            Transport.send(message);
            System.out.println("Mensagem Enviada!!!");
        } catch (MessagingException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
