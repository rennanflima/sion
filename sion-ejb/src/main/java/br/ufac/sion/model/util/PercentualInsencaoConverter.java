/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import br.ufac.sion.model.enuns.PercentualInsencao;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rennan.lima
 */
@Converter(autoApply = true)
public class PercentualInsencaoConverter implements AttributeConverter<PercentualInsencao, String>{

    @Override
    public String convertToDatabaseColumn(PercentualInsencao attribute) {
        return attribute.toString();
    }

    @Override
    public PercentualInsencao convertToEntityAttribute(String dbData) {
        return PercentualInsencao.valueOf(dbData);
    }
    
}
