/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.PermissaoFacadeLocal;
import br.ufac.sion.model.Localidade;
import br.ufac.sion.model.Permissao;
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
@FacesConverter(forClass = Permissao.class)
public class PermissaoConverter implements Converter {

    private PermissaoFacadeLocal permissaoFacade;

    public PermissaoConverter() {
        this.permissaoFacade = lookupPermissaoFacadeLocal();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Permissao retorno = null;

        if (value != null && !value.equals("")) {
            retorno = this.permissaoFacade.findById(new Long(value));
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((Permissao) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return "";
    }

    private PermissaoFacadeLocal lookupPermissaoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PermissaoFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-1.0-SNAPSHOT/PermissaoFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
