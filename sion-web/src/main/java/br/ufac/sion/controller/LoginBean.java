package br.ufac.sion.controller;

import br.ufac.sion.util.jsf.FacesProducer;
import br.ufac.sion.util.jsf.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private FacesContext facesContext;

    private HttpServletRequest request;

    private HttpServletResponse response;

    private String email;
    
    @PostConstruct
    public void init(){
        facesContext = FacesProducer.getFacesContext();
        request = FacesProducer.getHttpServletRequest();
        response = FacesProducer.getHttpServletResponse() ;
    }

    public void preRender() {
        if ("true".equals(request.getParameter("invalid"))) {
            FacesUtil.addErrorMessage("Usuário ou senha inválido!");
        }
    }

    public void login() throws ServletException, IOException {
        System.out.println("login");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward(request, response);
        System.out.println("request: "+request);
        System.out.println("response: "+response);
        System.out.println("dispatcher: "+dispatcher);
        facesContext.responseComplete();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
