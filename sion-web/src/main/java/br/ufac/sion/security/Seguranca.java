package br.ufac.sion.security;

import br.ufac.sion.dao.FuncionarioFacadeLocal;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.faces.context.FacesContext;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca {

    @EJB
    private FuncionarioFacadeLocal funcionarioFacade;

    public String getNomeUsuario() {
        String nome = null;

        UsuarioSistema usuarioLogado = getUsuarioLogado();

        if (usuarioLogado != null) {
            if (usuarioLogado.getUsuario().getLogin().equals("admin")) {
                nome = "Administrador";
            } else {
                nome = usuarioLogado.getUsuario().getNome();
            }
        }

        return nome;
    }

    @Produces
    @UsuarioLogado
    private UsuarioSistema getUsuarioLogado() {
        UsuarioSistema usuario = null;

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

        if (auth != null && auth.getPrincipal() != null) {
            usuario = (UsuarioSistema) auth.getPrincipal();
        }

        return usuario;
    }

}
