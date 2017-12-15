package br.ufac.sion.security;

import br.ufac.sion.dao.UsuarioFacadeLocal;
import br.ufac.sion.model.Grupo;
import br.ufac.sion.model.Usuario;
import java.time.LocalDateTime;
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
    
    UsuarioFacadeLocal usuarioFacade = lookupUsuarioFacadeLocal();

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioFacade.findByLogin(login);

        UsuarioSistema user = null;
        usuario.setUltimoAcesso(LocalDateTime.now());
        
        if (usuario != null) {
            usuario = usuarioFacade.save(usuario);
            user = new UsuarioSistema(usuario, getGrupos(usuario));
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
        return user;
    }

    private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        for (Grupo grupo : usuario.getGrupos()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+grupo.getNome().toUpperCase()));
        }
        return authorities;
    }

    private UsuarioFacadeLocal lookupUsuarioFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (UsuarioFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-1.0-SNAPSHOT/UsuarioFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
