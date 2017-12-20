/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.model.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface UsuarioFacadeLocal {

    public Usuario save(Usuario usuario);

    public void remove(Usuario usuario);

    public Usuario findById(Long id);

    public List<Usuario> findAll();

    public Usuario findByLogin(String login);

    public void alterarSenha(String oldSenha, String senha, Usuario usuario) throws NegocioException;
    
    public Usuario findByLoginWithPermissoes(String login);
    
    public Usuario findByLoginWithGrupo(String login);
    

}
