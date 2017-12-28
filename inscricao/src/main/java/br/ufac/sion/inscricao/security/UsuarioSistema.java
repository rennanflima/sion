package br.ufac.sion.inscricao.security;

import br.ufac.sion.model.Candidato;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.User;

public class UsuarioSistema extends User {

    private static final long serialVersionUID = 1L;

    private Candidato candidato;

    public UsuarioSistema(Candidato candidato, Collection<? extends GrantedAuthority> authorities) {
        super(candidato.getUsuario().getLogin(), candidato.getUsuario().getSenha(), authorities);
        this.candidato = candidato;
    }

    public Candidato getCandidato() {
        return candidato;
    }

}
