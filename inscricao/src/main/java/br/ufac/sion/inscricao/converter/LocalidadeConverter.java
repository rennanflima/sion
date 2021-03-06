/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.converter;

import br.ufac.sion.dao.LocalidadeFacadeLocal;
import br.ufac.sion.model.Localidade;
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
@FacesConverter(forClass = Localidade.class)
public class LocalidadeConverter implements Converter {

    private LocalidadeFacadeLocal localidadeFacade;

    public LocalidadeConverter() {
        this.localidadeFacade = lookupLocalidadeFacadeLocal();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return this.localidadeFacade.findById(new Long(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        if (value != null) {
            Long codigo = ((Localidade) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return null;
    }

    private LocalidadeFacadeLocal lookupLocalidadeFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (LocalidadeFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-2.0/LocalidadeFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
