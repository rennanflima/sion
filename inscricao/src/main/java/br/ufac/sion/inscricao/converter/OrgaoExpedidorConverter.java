/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.converter;

import br.ufac.sion.dao.OrgaoExpedidorFacadeLocal;
import br.ufac.sion.model.OrgaoExpedidor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Rennan
 */
@FacesConverter(forClass = OrgaoExpedidor.class)
public class OrgaoExpedidorConverter implements Converter {

    OrgaoExpedidorFacadeLocal orgaoExpedidorFacade;

    public OrgaoExpedidorConverter() {
        this.orgaoExpedidorFacade = lookupOrgaoExpedidorFacadeLocal();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        OrgaoExpedidor retorno = null;
        if (StringUtils.isNotBlank(value)) {
            retorno = this.orgaoExpedidorFacade.findById(new Long(value));

            if (retorno == null) {
                String descricaoErro = "A Orgão Expedidor não existe.";
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        descricaoErro, descricaoErro);
                throw new ConverterException(message);
            }
        }
        return retorno;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((OrgaoExpedidor) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return null;
    }

    private OrgaoExpedidorFacadeLocal lookupOrgaoExpedidorFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (OrgaoExpedidorFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-2.0/OrgaoExpedidorFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
