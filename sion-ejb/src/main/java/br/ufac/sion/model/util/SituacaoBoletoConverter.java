/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import br.ufac.sion.model.enuns.SituacaoBoleto;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rennan.lima
 */
@Converter(autoApply = true)
public class SituacaoBoletoConverter implements AttributeConverter<SituacaoBoleto, String>{

    @Override
    public String convertToDatabaseColumn(SituacaoBoleto attribute) {
        return attribute.toString();
    }

    @Override
    public SituacaoBoleto convertToEntityAttribute(String dbData) {
        return SituacaoBoleto.valueOf(dbData);
    }


}