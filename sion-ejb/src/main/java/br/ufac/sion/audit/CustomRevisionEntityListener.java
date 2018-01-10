/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.audit;

import java.net.InetAddress;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Rennan
 */
public class CustomRevisionEntityListener implements RevisionListener {

    private static Log log = LogFactory.getLog(CustomRevisionEntityListener.class);

    @Override
    public void newRevision(Object revisionEntity) {
        try {
            CustomRevisionEntity customRevisionEntity = (CustomRevisionEntity) revisionEntity;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println("authentication == null : " + (authentication == null));
            if (authentication == null) {
                customRevisionEntity.setUsername("Sion - tarefa automatizada!");
            } else {
                customRevisionEntity.setUsername(authentication.getName());
            }
            customRevisionEntity.setIp(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception ex) {
            log.error("Erro de sistema (sion-web): " + ex.getMessage(), ex);
        }
    }

}
