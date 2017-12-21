package br.ufac.sion.controller;

import br.ufac.sion.util.jsf.FacesUtil;
import br.ufac.sion.util.jsf.Sion;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject  @Sion
    private HttpServletRequest request;

    @Inject  @Sion
    private HttpServletResponse response;

    private String username;

    @PostConstruct
    public void init() {
//        facesContext = FacesProducer.getFacesContext();
//        request = FacesProducer.getHttpServletRequest();
//        response = FacesProducer.getHttpServletResponse();
    }
    
    public void preRender() {    
        
        if ("true".equals(request.getParameter("invalid"))) {
            FacesUtil.addErrorMessage("Usuário ou senha inválido!");
        }
    }

    public void login() throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.xhtml");
        dispatcher.forward(request, response);
        facesContext.responseComplete();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
