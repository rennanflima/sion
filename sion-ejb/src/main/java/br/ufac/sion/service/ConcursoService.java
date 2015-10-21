/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.StatusConcurso;
import br.ufac.sion.util.NegocioException;
import java.time.LocalDateTime;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Rennan
 */
@Stateless
public class ConcursoService {

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    public Concurso salvar(Concurso concurso) throws NegocioException {
        LocalDateTime now = LocalDateTime.now();

        if (concurso.isNovo()) {
            concurso.setStatus(StatusConcurso.ABERTO);
        }
        if (now.isAfter(concurso.getDataInicioInscricao()) && now.isBefore(concurso.getDataTerminoIncricao())) {
            concurso.setStatus(StatusConcurso.INSCRICOES_ABERTAS);
        } else {
            if(now.isAfter(concurso.getDataTerminoIncricao()) && concurso.getStatus().equals(StatusConcurso.INSCRICOES_ABERTAS)){
                concurso.setStatus(StatusConcurso.INSCRICOES_ENCERRADAS);
            }
        }
        if (concurso.getDataTerminoIncricao().isBefore(concurso.getDataInicioInscricao())) {
            throw new NegocioException("A data de termíno das inscrição deve ser maior que a data de início das inscrições");
        }
        try {
            return concursoFacade.save(concurso);
        } catch (Exception e) {
            throw new NegocioException(e.getLocalizedMessage());
        }
    }

    public Concurso buscarConcursoComCargos(Long id) {
        Concurso c = concursoFacade.findConcursoWithCargo(id);
        return c;
    }

}
