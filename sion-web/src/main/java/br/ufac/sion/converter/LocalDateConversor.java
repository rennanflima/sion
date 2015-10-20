/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author rennan.lima
 */
@FacesConverter(value = "localDateConversor", forClass = LocalDate.class)
public class LocalDateConversor implements Converter {

    private static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value != null) {
            return LocalDate.parse(value, pattern);
        }
        return null;
    }

    public String getAsString(FacesContext ctx, UIComponent component, Object value) {

        if (value != null && !"".equals(value)) {
            LocalDate data = (LocalDate) value;
            if (data != null) {
                return data.format(pattern);
            }
        }
        return "";
    }
}
