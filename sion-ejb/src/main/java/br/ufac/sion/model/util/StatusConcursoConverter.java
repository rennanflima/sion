/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import br.ufac.sion.model.enuns.StatusConcurso;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rennan.lima
 */
@Converter(autoApply = true)
public class StatusConcursoConverter implements AttributeConverter<StatusConcurso, String>{

    @Override
    public String convertToDatabaseColumn(StatusConcurso attribute) {
        return attribute.toString();
    }

    @Override
    public StatusConcurso convertToEntityAttribute(String dbData) {
        return StatusConcurso.valueOf(dbData);
    }
    
}
