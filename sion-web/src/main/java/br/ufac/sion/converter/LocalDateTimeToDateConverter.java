/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import java.time.Instant;
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
@FacesConverter(value = "localDateTimeConverter")
public class LocalDateTimeToDateConverter extends DateTimeConverter {

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return super.getAsString(context, component, value);
        }
        if (value instanceof LocalDateTime) {
            LocalDateTime ldate = (LocalDateTime) value;
            System.out.println("LocalDateTime: "+ldate);
            Instant instant = ldate.atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            return super.getAsString(context, component, date);
        } else {
            throw new IllegalArgumentException(String.format("value=%s não é uma instância de LocalDateTime", value));
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        LocalDateTime ldate = null;
        Date date = null;
        Object o = super.getAsObject(context, component, value);
        if (o == null) {
            return null;
        }
        if (o instanceof Date) {
            date = (Date) o;
            System.out.println("Date: "+date);
            Instant instant = Instant.ofEpochMilli(date.getTime());
            ldate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            return ldate;
        } else {
            throw new IllegalArgumentException(String.format("value=%s não pode ser convertido para"
                    + " LocalDateTime, result super.getAsObject=%s", value, o));
        }
    }

}
