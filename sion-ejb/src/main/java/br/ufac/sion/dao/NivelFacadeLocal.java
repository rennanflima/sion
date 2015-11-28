/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Localidade;
import br.ufac.sion.model.Nivel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rennan.lima
 */
@Local
public interface NivelFacadeLocal {

    public Nivel save(Nivel nivel);

    public void remove(Nivel nivel);

    public Nivel findById(Long id);

    public List<Nivel> findAll();
    
    public List<Nivel> findByLocalidadeAndConcurso(Localidade local, Concurso concurso);
    
}
