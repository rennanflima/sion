/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.ContaBancariaFacadeLocal;
import br.ufac.sion.model.ContaBancaria;
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
@FacesConverter(forClass = ContaBancaria.class)
public class ContaConverter implements Converter {

    private ContaBancariaFacadeLocal contaFacade;

    public ContaConverter() {
        this.contaFacade = lookupVagaFacadeLocal();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        ContaBancaria retorno = null;

        if (value != null && !value.equals("")) {
            retorno = this.contaFacade.findById(new Long(value));
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((ContaBancaria) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return "";
    }

    private ContaBancariaFacadeLocal lookupVagaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ContaBancariaFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-1.0-SNAPSHOT/ContaBancariaFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
