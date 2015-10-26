/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author rennan.lima
 */
@FacesConverter(value = "localDateTimeConversor", forClass = LocalDateTime.class)
public class LocalDateTimeConversor implements Converter {

    private static final DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
        if (value != null) {
            return LocalDateTime.parse(value, pattern);
        }
        return null;
    }

    public String getAsString(FacesContext ctx, UIComponent component, Object value) {

        if (value != null && !"".equals(value)) {
            LocalDateTime data = (LocalDateTime) value;
            if (data != null) {
                return data.format(pattern);
            }
        }
        return "";
    }
}
