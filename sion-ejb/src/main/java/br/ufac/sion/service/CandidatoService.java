/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.model.Candidato;
import br.ufac.sion.util.GeraSenha;
import br.ufac.sion.exception.NegocioException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class CandidatoService {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    private GeraSenha geraSenha;

    public Candidato salvar(Candidato candidato) throws NegocioException {
        geraSenha = new GeraSenha();

        candidato.setPermissao("CANDIDATO");
        candidato.setSenha(geraSenha.ecripta(candidato.getSenha()));
        try {
            candidato = em.merge(candidato);
            return candidato;
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

    public Candidato editar(Candidato candidato) throws NegocioException {
        try {
            return em.merge(candidato);

        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
