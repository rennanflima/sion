/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.CargoConcurso;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.SituacaoInscricao;
import br.ufac.sion.model.vo.FiltroInscritos;
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

    public List<Inscricao> findIncricoesAtivasByCandidato(Candidato candidato);

    public List<Inscricao> findByConcurso(FiltroInscritos filtrox);

    public List<Inscricao> findByConcursoAndConfirmadasESubJudice(FiltroInscritos filtro);

    public List<Inscricao> findByConcursoAndPNE(FiltroInscritos filtro);

    public Map<Date, Long> inscricoesPorData(Concurso concurso, SituacaoInscricao situacao);
    
    public int contaInscricoesConfirmadasESubJudice(FiltroInscritos filtro);
    
    public int contaInscricoes(FiltroInscritos filtro);
    
    public int contaInscricoesPNE(FiltroInscritos filtro);
    
    public Long encontrarQuantidadeDeInscricoesConfirmadasESubJudice(Concurso concurso);
    
    public Long encontrarQuantidadeDeInscricoesNaoConfirmadas(Concurso concurso);
    
    public Long encontrarQuantidadeDeInscricoes(Concurso concurso);
    
    public Long encontrarQuantidadeDeInscricoesPNE(Concurso concurso);
    
    public Long encontrarQuantidadeDeInscricoesSubJudice(Concurso concurso);
    
    public Long encontrarQuantidadeDeInscricoesConfirmadas(Concurso concurso);

    public Long encontrarQuatidadeDeInscricoesPorCargo(CargoConcurso cargoConcurso, String status);
    
}
