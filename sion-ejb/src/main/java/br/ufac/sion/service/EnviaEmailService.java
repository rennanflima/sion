/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.service.util.InfoEmail;
import java.io.Serializable;
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
import org.apache.commons.lang3.StringUtils;

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
    @JMSConnectionFactory("jms/emailQueueFactory")
    private JMSContext context;

    @Lock(LockType.WRITE)
    public void processaEnvioDeEmail(InfoEmail infoEmail) throws NegocioException{
        if(infoEmail == null){
            throw new NegocioException("Não foi possível enviar o e-mail. Conteúdo vázio!");
        } else{
            if(StringUtils.isBlank(infoEmail.getPara())){
                throw new NegocioException("Não foi possível enviar o e-mail. Remetente inválido!");
            }
        }
        context.createProducer().send(emailQueue, infoEmail);
    }
}
