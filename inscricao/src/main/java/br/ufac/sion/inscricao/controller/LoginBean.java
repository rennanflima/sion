package br.ufac.sion.inscricao.controller;

import br.ufac.sion.inscricao.util.jsf.FacesProducer;
import br.ufac.sion.inscricao.util.jsf.FacesUtil;
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

    private String username;

    @PostConstruct
    public void init() {
        facesContext = FacesProducer.getFacesContext();
        request = FacesProducer.getHttpServletRequest();
        response = FacesProducer.getHttpServletResponse();
    }

    public void preRender() {
        
        if ("true".equals(request.getParameter("invalid"))) {
            FacesUtil.addErrorMessage("Usuário ou senha inválido!");
        }
    }

    public void login() throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
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
