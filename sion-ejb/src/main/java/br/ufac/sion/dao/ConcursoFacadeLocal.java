/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Concurso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface ConcursoFacadeLocal {

    public Concurso save(Concurso concurso);

    public void remove(Concurso concurso);

    public Concurso findById(Long id);

    public List<Concurso> findAll();

    public Concurso findConcursoWithCargo(Long id);
    
    public List<Concurso> findByInscricoesAbertas();
}
