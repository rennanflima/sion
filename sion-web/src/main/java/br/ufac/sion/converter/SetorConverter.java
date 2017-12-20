/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.SetorFacadeLocal;
import br.ufac.sion.model.Setor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author rennan.lima
 */
@FacesConverter(forClass = Setor.class)
public class SetorConverter implements Converter {

    private SetorFacadeLocal setorFacade;

    public SetorConverter() {
        this.setorFacade = lookupSetorFacadeLocal();
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (StringUtils.isBlank(value)) {
            return null;
        }
        return this.setorFacade.findByIdWithCargo(new Long(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((Setor) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return "";
    }
 
    
    private SetorFacadeLocal lookupSetorFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (SetorFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-1.0-SNAPSHOT/SetorFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
