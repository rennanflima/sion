package br.ufac.sion.inscricao.security;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@ManagedBean
@RequestScoped
public class Seguranca {

    @EJB
    private CandidatoFacadeLocal candidatoFacade;

    public String getNomeUsuario() {
        String nome = null;

        UsuarioSistema usuarioLogado = getUsuarioLogado();

        if (usuarioLogado != null) {
            nome = (candidatoFacade.findByCPF(usuarioLogado.getUsuario().getCpf())).getNome();
        }

        return nome;
    }

    private UsuarioSistema getUsuarioLogado() {
        UsuarioSistema usuario = null;

        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

        if (auth != null && auth.getPrincipal() != null) {
            usuario = (UsuarioSistema) auth.getPrincipal();
        }

        return usuario;
    }

}
