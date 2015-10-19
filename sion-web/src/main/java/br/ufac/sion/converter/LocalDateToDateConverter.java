/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rennan
 */
@FacesConverter(value = "localDateConverter")
public class LocalDateToDateConverter extends DateTimeConverter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return super.getAsString(context, component, value);
        }
        if (value instanceof LocalDate) {
            LocalDate ldate = (LocalDate) value;
            Instant instant = ldate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            return super.getAsString(context, component, date);
        } else {
            throw new IllegalArgumentException(String.format("value=%s não é uma instância de LocalDate", value));
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        LocalDate ldate = null;
        Date date = null;
        Object o = super.getAsObject(context, component, value);
        if (o == null) {
            return null;
        }
        if (o instanceof Date) {
            date = (Date) o;
            Instant instant = Instant.ofEpochMilli(date.getTime());
            ldate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
            return ldate;
        } else {
            throw new IllegalArgumentException(String.format("value=%s não pode ser convertido para"
                    + " LocalDate, result super.getAsObject=%s", value, o));
        }
    }

}
