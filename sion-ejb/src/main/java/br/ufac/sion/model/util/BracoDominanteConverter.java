/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import br.ufac.sion.model.enuns.BracoDominante;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rennan.lima
 */
@Converter(autoApply = true)
public class BracoDominanteConverter implements AttributeConverter<BracoDominante, String>{

    @Override
    public String convertToDatabaseColumn(BracoDominante attribute) {
        return attribute.toString();
    }

    @Override
    public BracoDominante convertToEntityAttribute(String dbData) {
        return BracoDominante.valueOf(dbData);
    }
    
}
