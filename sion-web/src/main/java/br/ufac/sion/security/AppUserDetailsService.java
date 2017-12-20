package br.ufac.sion.security;

import br.ufac.sion.dao.GrupoFacadeLocal;
import br.ufac.sion.dao.UsuarioFacadeLocal;
import br.ufac.sion.model.Grupo;
import br.ufac.sion.model.Permissao;
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

    GrupoFacadeLocal grupoFacade = lookupGrupoFacadeLocal();

    UsuarioFacadeLocal usuarioFacade = lookupUsuarioFacadeLocal();

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Usuario usuario = usuarioFacade.findByLogin(login);

        UsuarioSistema user = null;

        if (usuario != null) {
            usuario.setUltimoAcesso(LocalDateTime.now());
            usuario = usuarioFacade.save(usuario);
            user = new UsuarioSistema(usuario, getGrupos(usuario));
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }

        return user;
    }

    private Collection<GrantedAuthority> getGrupos(Usuario usuario) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println("entra metodo");
        
        usuario = usuarioFacade.findByLoginWithPermissoes(usuario.getLogin());
        if (usuario != null && usuario.getPermissoes() != null) {
            System.out.println("Permissões");
            for (Permissao p : usuario.getPermissoes()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + p.getNome().toUpperCase()));
                System.out.println("ROLE_" + p.getNome().toUpperCase());
            }
        }
        usuario = usuarioFacade.findByLoginWithGrupo(usuario.getLogin());
        System.out.println("Nome usuario: "+usuario.getLogin());
        System.out.println("QTD grupos: "+usuario.getGrupos().size());
        if (usuario != null && usuario.getGrupos() != null) {
            for (Grupo grupo : usuario.getGrupos()) {
                System.out.println("Grupo");
                Grupo g = grupoFacade.findGrupoWithPermissoes(grupo.getId());
                System.out.println("Nome Grupo: "+ g.getNome());
                System.out.println("Qtd permissao: "+g.getPermissoes().size());
                if (g != null && g.getPermissoes() != null) {
                    System.out.println("Permissões do Grupo");
                    for (Permissao p : g.getPermissoes()) {
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + p.getNome().toUpperCase()));
                        System.out.println("ROLE_" + p.getNome().toUpperCase());
                    }
                }
                authorities.add(new SimpleGrantedAuthority("ROLE_" + grupo.getNome().toUpperCase()));
                System.out.println("ROLE_" + grupo.getNome().toUpperCase());
            }
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

    private GrupoFacadeLocal lookupGrupoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (GrupoFacadeLocal) c.lookup("java:global/sion-ear/sion-ejb-1.0-SNAPSHOT/GrupoFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
