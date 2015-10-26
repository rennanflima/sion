package br.ufac.sion.inscricao.security;

import br.ufac.sion.model.Candidato;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.User;

public class UsuarioSistema extends User {

    private static final long serialVersionUID = 1L;

    private Candidato usuario;

    public UsuarioSistema(Candidato usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getCpf(), usuario.getSenha(), authorities);
        this.usuario = usuario;
    }

    public Candidato getUsuario() {
        return usuario;
    }

}
