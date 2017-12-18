/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.converter;

import br.ufac.sion.dao.PermissaoFacadeLocal;
import br.ufac.sion.model.Permissao;
import java.util.Iterator;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author rennan.lima
 */
@FacesConverter(forClass = Permissao.class, managed = true)
public class PermissaoConverter implements Converter {

    @EJB
    private PermissaoFacadeLocal permissaoFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Permissao retorno = null;

        if (value != null && !value.equals("")) {
            retorno = this.permissaoFacade.findById(new Long(value));
        }
        if (component instanceof PickList) {
            Object dualList = ((PickList) component).getValue();
            DualListModel dl = (DualListModel) dualList;
            for (Iterator iterator = dl.getSource().iterator(); iterator.hasNext();) {
                Object o = iterator.next();
                String id = (new StringBuilder()).append(((Permissao) o).getId()).toString();
                if (value.equals(id)) {
                    retorno = (Permissao) o;
                    break;
                }
            }
            if (retorno == null) {
                for (Iterator iterator1 = dl.getSource().iterator(); iterator1.hasNext();) {
                    Object o = iterator1.next();
                    String id = (new StringBuilder()).append(((Permissao) o).getId()).toString();
                    if (value.equals(id)) {
                        retorno = (Permissao) o;
                        break;
                    }
                }

            }
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Long codigo = ((Permissao) value).getId();
            String retorno = (codigo == null ? null : codigo.toString());

            return retorno;
        }
        return "";
    }

}
