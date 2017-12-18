/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.inscricao.converter;

import br.ufac.sion.dao.CargoConcursoFacadeLocal;
import br.ufac.sion.model.CargoConcurso;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author rennan.lima
 */
@FacesConverter(forClass = CargoConcurso.class, managed = true)
public class CargoConcursoConverter implements Converter {

    @EJB
    private CargoConcursoFacadeLocal cargoConcursoFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        CargoConcurso retorno = null;

        if (value != null) {
            retorno = cargoConcursoFacade.findById(new Long(value));
        }

        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((CargoConcurso) value).getId();
            return (codigo == null ? null : codigo.toString());
        }
        return "";
    }
}
