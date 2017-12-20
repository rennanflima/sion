/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import br.ufac.sion.model.enuns.EstadoCivil;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rennan.lima
 */
@Converter(autoApply = true)
public class EstadoCivilConverter implements AttributeConverter<EstadoCivil, String>{

    @Override
    public String convertToDatabaseColumn(EstadoCivil attribute) {
        return attribute.toString();
    }

    @Override
    public EstadoCivil convertToEntityAttribute(String dbData) {
        return EstadoCivil.valueOf(dbData);
    }
    
}