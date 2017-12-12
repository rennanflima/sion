/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.GrupoFacadeLocal;
import br.ufac.sion.model.Grupo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author rennan.lima
 */
@FacesConverter(forClass = Grupo.class, value ="grupoConverter")
public class GrupoConverter implements Converter {

    private GrupoFacadeLocal grupoFacade;

    public GrupoConverter() {
        this.grupoFacade = lookupGrupoFacadeLocal();
    }
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
         Grupo retorno = null;

        if (value != null && !value.equals("")) {
            retorno = this.grupoFacade.findById(new Long(value));
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((Grupo) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());
            
            return retorno;
            
        }
        return "";
    }
 
    
    private GrupoFacadeLocal lookupGrupoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (GrupoFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-1.0-SNAPSHOT/GrupoFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
