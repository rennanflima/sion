/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import br.ufac.sion.model.enuns.Escolaridade;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rennan.lima
 */
@Converter(autoApply = true)
public class EscolaridadeConverter implements AttributeConverter<Escolaridade, String> {

    @Override
    public String convertToDatabaseColumn(Escolaridade attribute) {
        if (attribute != null) {
            return attribute.toString();
        }
        return "";
    }

    @Override
    public Escolaridade convertToEntityAttribute(String dbData) {
        return Escolaridade.valueOf(dbData);
    }

}
