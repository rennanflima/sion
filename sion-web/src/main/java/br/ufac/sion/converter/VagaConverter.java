/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.VagaFacadeLocal;
import br.ufac.sion.model.Vaga;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author rennan.lima
 */
@FacesConverter(forClass = Vaga.class, managed = true)
public class VagaConverter implements Converter{

    @EJB
    private VagaFacadeLocal vagaFacade;

    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if(StringUtils.isBlank(value)){
            return null;
        }
        return this.vagaFacade.findById(new Long(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((Vaga) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return "";
    }
}
