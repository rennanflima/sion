/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Cidade;
import br.ufac.sion.model.Estado;
import br.ufac.sion.model.vo.FiltroCidades;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rennan
 */
@Local
public interface CidadeFacadeLocal {


    public Cidade save(Cidade cidade);

    public void remove(Cidade cidade);

    public Cidade findById(Long id);

    public List<Cidade> findAll();
    
    public Cidade findByNomeAndIdEstado(String nome, Long id);
    
    public List<Cidade> findByEstado(Estado estado);
    
    public List<Cidade> buscaTodosComPaginacao(FiltroCidades filtro);
    
    public int contaCidades(FiltroCidades filtro);
}
