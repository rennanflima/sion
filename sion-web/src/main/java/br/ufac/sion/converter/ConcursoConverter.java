/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.model.Concurso;
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
@FacesConverter(forClass = Concurso.class)
public class ConcursoConverter implements Converter {

    private ConcursoFacadeLocal concursoFacade;

    public ConcursoConverter() {
        this.concursoFacade = lookupConcursoFacadeLocal();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Concurso retorno = null;

        if (value != null && !value.equals("")) {
            retorno = this.concursoFacade.findById(new Long(value));
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((Concurso) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return "";
    }

    private ConcursoFacadeLocal lookupConcursoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ConcursoFacadeLocal) c.lookup("java:global/sion-ear-1.0-SNAPSHOT/sion-ejb-1.0-SNAPSHOT/ConcursoFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
