/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.dao.CandidatoFacadeLocal;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.util.GeraSenha;
import br.ufac.sion.util.NegocioException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class CandidatoService {

    @EJB
    private CandidatoFacadeLocal candidatoFacade;
    
    private GeraSenha geraSenha;

    public Candidato salvar(Candidato candidato) throws NegocioException {
        geraSenha = new GeraSenha();
        
        candidato.setPermissao("CANDIDATO");
        candidato.setSenha(geraSenha.ecripta(candidato.getSenha()));
        try {
            candidato = candidatoFacade.save(candidato);
            return candidato;
        } catch (Exception e) {
            throw  new NegocioException(e.getMessage());
        }
    }
}
