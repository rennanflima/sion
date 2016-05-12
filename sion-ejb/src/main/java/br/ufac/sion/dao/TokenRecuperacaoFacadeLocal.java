/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.TokenRecuperacao;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface TokenRecuperacaoFacadeLocal {

    public TokenRecuperacao save(TokenRecuperacao tokenRecuperacao);

    public void remove(TokenRecuperacao tokenRecuperacao);

    public TokenRecuperacao findByToken(String token);

    public List<TokenRecuperacao> findAll();
    
}
