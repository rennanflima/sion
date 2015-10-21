/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util;

import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.StatusConcurso;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author rennan.lima
 */
@Singleton
@Startup
public class AgendaInscricaoConcurso {

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    @Schedule(hour = "23", minute = "59")
    public void encerraInscricaoConcurso() {
        List<Concurso> concursos = this.concursoFacade.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Concurso concurso : concursos) {
            if (now.isAfter(concurso.getDataTerminoIncricao()) && concurso.getStatus().equals(StatusConcurso.INSCRICOES_ABERTAS)) {
                concurso.setStatus(StatusConcurso.INSCRICOES_ENCERRADAS);
                this.concursoFacade.save(concurso);
            }
        }
    }

    @Schedule(hour = "8")
    public void abreInscricaoConcurso() {
        List<Concurso> concursos = this.concursoFacade.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Concurso concurso : concursos) {
            if (concurso.getStatus().equals(StatusConcurso.ABERTO)) {
                if (now.isAfter(concurso.getDataInicioInscricao()) && now.isBefore(concurso.getDataTerminoIncricao())) {
                    concurso.setStatus(StatusConcurso.INSCRICOES_ABERTAS);
                    this.concursoFacade.save(concurso);
                }
            }
        }
    }
}
