/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import br.ufac.sion.model.enuns.SituacaoInscricao;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rennan.lima
 */
@Converter(autoApply = true)
public class SituacaoInscricaoConverter implements AttributeConverter<SituacaoInscricao, String>{

    @Override
    public String convertToDatabaseColumn(SituacaoInscricao attribute) {
        return attribute.toString();
    }

    @Override
    public SituacaoInscricao convertToEntityAttribute(String dbData) {
        return SituacaoInscricao.valueOf(dbData);
    }
    
}
