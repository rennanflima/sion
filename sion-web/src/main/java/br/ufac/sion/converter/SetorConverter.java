/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.SetorFacadeLocal;
import br.ufac.sion.model.Setor;
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
@FacesConverter(forClass = Setor.class, managed = true)
public class SetorConverter implements Converter {

    @EJB
    private SetorFacadeLocal setorFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        if (StringUtils.isBlank(value)) {
            return null;
        }
        return this.setorFacade.findById(new Long(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((Setor) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return "";
    }

}
