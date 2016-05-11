/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service.mdb;

import br.ufac.sion.service.util.InfoEmail;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Rennan
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/emailQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class EnviaEmailMDB implements MessageListener {

    @Resource(mappedName = "mail/gmailSMTP")
    private Session mailSTMP;

    public EnviaEmailMDB() {
    }

    @Override
    public void onMessage(Message message) {
        if (message == null) {
            return;
        }
        InfoEmail infoEmail = null;
        try {
            infoEmail = message.getBody(InfoEmail.class);
            callGlassfishJavaMail(infoEmail);
        } catch (JMSException ex) {
            Logger.getLogger(EnviaEmailMDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void callGlassfishJavaMail(InfoEmail infoEmail) {
        try {
            Multipart multipart = new MimeMultipart();
            javax.mail.Message msg = new MimeMessage(mailSTMP);
            msg.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(infoEmail.getPara()));
            msg.setFrom(new InternetAddress("noreplay@gmail.com"));
            msg.setSubject(infoEmail.getAssunto());
            // The Message
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(infoEmail.getCorpo(), "text/html; charset=utf-8");
            multipart.addBodyPart(messageBodyPart);

            /* The PDF File
            BodyPart boletoBodyPart = new MimeBodyPart();
            boletoBodyPart.setFileName("boleto.pdf");
            boletoBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(pdfBoleto, "application/pdf")));
            multipart.addBodyPart(boletoBodyPart);*/
            // Attach the Multipart Data
            msg.setContent(multipart);
            Transport.send(msg);
        } catch (Exception ex) {
            Logger.getLogger(EnviaEmailMDB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[EnviaEmailMDB-JavaMail GlassFish API] Impossivel enviar email para " + infoEmail.getPara() + " - " + ex.getMessage());
            throw new IllegalStateException("Não foi possível enviar o e-mail, tente mais tarde!!");
        }
    }
}
