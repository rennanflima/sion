/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.jsf;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rennan.lima
 */
public class FacesProducer {

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }

    public static HttpServletRequest getHttpServletRequest() {
        return ((HttpServletRequest) getExternalContext().getRequest());
    }

    public static HttpServletResponse getHttpServletResponse() {
        return ((HttpServletResponse) getExternalContext().getResponse());
    }

}
