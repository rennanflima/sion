package br.ufac.sion.inscricao.security;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.model.Candidato;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

    CandidatoFacadeLocal candidatoFacade = lookupCandidatoFacadeLocal();

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        
        Candidato usuario = candidatoFacade.findByCPF(login);

        UsuarioSistema user = null;

        if (usuario != null) {
            user = new UsuarioSistema(usuario, getGrupos(usuario));
        }
        return user;
    }

    private Collection<? extends GrantedAuthority> getGrupos(Candidato usuario) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CANDIDATO"));
        return authorities;
    }

    private CandidatoFacadeLocal lookupCandidatoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (CandidatoFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-1.0-SNAPSHOT/CandidatoFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
