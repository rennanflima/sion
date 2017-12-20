/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import br.ufac.sion.model.enuns.Sexo;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rennan.lima
 */
@Converter(autoApply = true)
public class SexoConverter implements AttributeConverter<Sexo, String>{

    @Override
    public String convertToDatabaseColumn(Sexo attribute) {
        return attribute.toString();
    }

    @Override
    public Sexo convertToEntityAttribute(String dbData) {
        return Sexo.valueOf(dbData);
    }
    
}
