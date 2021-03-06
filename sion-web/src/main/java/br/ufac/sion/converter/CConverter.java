/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.CargoFacadeLocal;
import br.ufac.sion.model.Cargo;
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
@FacesConverter(forClass = Cargo.class, value = "cConverter")
public class CConverter implements Converter {

    private CargoFacadeLocal cargoFacade;

    public CConverter() {
        this.cargoFacade = lookupVagaFacadeLocal();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Cargo retorno = null;

        if (value != null && !value.equals("")) {
            retorno = this.cargoFacade.findById(new Long(value));
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((Cargo) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return "";
    }

    private CargoFacadeLocal lookupVagaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (CargoFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-2.0/CargoFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}