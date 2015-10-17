/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.model.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

/**
 *
 * @author Rennan
 */
@Convert
public class LocalDateConverter implements AttributeConverter<LocalDate, Date>{

	@Override
	public Date convertToDatabaseColumn(LocalDate attribute) {
		Instant instant = attribute.atStartOfDay(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		Instant instant = Instant.ofEpochMilli(dbData.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
	}

}
