/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.model.CargoConcurso;
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
@FacesConverter(forClass = CargoConcurso.class, value = "cargoConcursoConverter")
public class CargoConcursoConverter implements Converter {

    private CargoConcursoFacadeLocal cargoConcursoFacade ;

    public CargoConcursoConverter() {
        this.cargoConcursoFacade = lookupCargoConcursoFacadeLocal();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        CargoConcurso retorno = null;
        
        if(value != null){
            retorno = cargoConcursoFacade.findById(new Long(value));
        }
        
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value != null){
            Long codigo = ((CargoConcurso) value).getId();
            return (codigo == null ? null : codigo.toString());
        }
        return "";
    }

    private CargoConcursoFacadeLocal lookupCargoConcursoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (CargoConcursoFacadeLocal) c.lookup("java:global/sion-ear-1.0-SNAPSHOT/sion-ejb-1.0-SNAPSHOT/CargoConcursoFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
