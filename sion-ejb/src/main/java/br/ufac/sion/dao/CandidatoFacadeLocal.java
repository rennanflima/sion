/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.vo.FiltroCandidatos;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface CandidatoFacadeLocal {

    public Candidato save(Candidato candidato);

    public void remove(Candidato candidato);

    public Candidato findById(Long id);

    public List<Candidato> findAll();
    
    public Candidato findByCPF(String cpf);
    
    public Candidato findByEmail(String email);
    
    public Candidato findByCPFWithInscricoes(String cpf);

    public List<Candidato> buscaTodosComPaginacao(FiltroCandidatos filtro);
    
    public int contaCandidatos(FiltroCandidatos filtro);
}
