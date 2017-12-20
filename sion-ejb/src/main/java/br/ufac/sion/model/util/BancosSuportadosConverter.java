/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import br.ufac.sion.model.enuns.BancosSuportados;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rennan.lima
 */
@Converter(autoApply = true)
public class BancosSuportadosConverter implements AttributeConverter<BancosSuportados, String>{

    @Override
    public String convertToDatabaseColumn(BancosSuportados attribute) {
        return attribute.toString();
    }

    @Override
    public BancosSuportados convertToEntityAttribute(String dbData) {
        return BancosSuportados.valueOf(dbData);
    }
    
}
