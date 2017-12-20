/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import br.ufac.sion.model.enuns.TipoTelefone;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author rennan.lima
 */

@Converter(autoApply = true)
public class TipoTelefoneConverter implements AttributeConverter<TipoTelefone, String>{

    @Override
    public String convertToDatabaseColumn(TipoTelefone attribute) {
        return attribute.toString();
    }

    @Override
    public TipoTelefone convertToEntityAttribute(String dbData) {
        return TipoTelefone.valueOf(dbData);
    }
    
}
