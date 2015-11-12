/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.SituacaoInscricao;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface InscricaoFacadeLocal {

    public Inscricao save(Inscricao inscricao);

    public void remove(Inscricao inscricao);

    public Inscricao findById(Long id);

    public List<Inscricao> findAll();
    
    public List<Inscricao> findByCandidato(Candidato candidato);
    
    public List<Inscricao> findByConcurso(Concurso concurso);
    
    public List<Inscricao> findByConcursoAndConfirmadas(Concurso concurso);
    
    public List<Inscricao> findByConcursoAndPNE(Concurso concurso);
   
    public Map<Date, Long> inscricoesPorData(Concurso concurso, SituacaoInscricao situacao);
}
