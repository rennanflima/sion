/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.FuncionarioFacadeLocal;
import br.ufac.sion.model.Funcionario;
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
@FacesConverter(forClass = Funcionario.class)
public class FuncionarioConverter implements Converter {

    private FuncionarioFacadeLocal funcionarioFacade;

    public FuncionarioConverter() {
        this.funcionarioFacade = lookupFuncionarioFacadeLocal();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return this.funcionarioFacade.findById(new Long(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((Funcionario) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return "";
    }

    private FuncionarioFacadeLocal lookupFuncionarioFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FuncionarioFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-1.0-SNAPSHOT/FuncionarioFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
