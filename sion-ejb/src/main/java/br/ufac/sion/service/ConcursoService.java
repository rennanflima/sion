/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.StatusConcurso;
import br.ufac.sion.exception.NegocioException;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Rennan
 */
@Stateless
public class ConcursoService {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Resource
    private TimerService timerService;

    @EJB
    private ConcursoFacadeLocal concursoFacade;

    public Concurso salvar(Concurso concurso) throws NegocioException {
        LocalDateTime now = LocalDateTime.now();

        if (concurso.isNovo()) {
            concurso.setStatus(StatusConcurso.AUTORIZADO);
        }
        if (concurso.isInscricoesAberta()) {
            concurso.setStatus(StatusConcurso.INSCRICOES_ABERTAS);
        } else {
            if (concurso.isInscricoesFechadas()) {
                concurso.setStatus(StatusConcurso.INSCRICOES_ENCERRADAS);
            } else if (concurso.isAutorizado()) {
                concurso.setStatus(StatusConcurso.AUTORIZADO);
            }
        }
        if (concurso.getDataTerminoIncricao().isBefore(concurso.getDataInicioInscricao())) {
            throw new NegocioException("A data de termíno das inscrição deve ser maior que a data de início das inscrições");
        }

        try {
            concurso = em.merge(concurso);
            if (concurso.isAutorizado() || concurso.isInscricoesAberta()) {
                cancelarAgendamentoIncricaoAberta(concurso);
                criarAgendamento(concurso.getDataInicioInscricao(), "abreInscricao" + concurso.getId());
            } else if (concurso.isAutorizado() || concurso.isInscricoesAberta() || concurso.isInscricoesFechadas()) {
                cancelarAgendamentoIncricaoFechada(concurso);
                criarAgendamento(concurso.getDataTerminoIncricao(), "fechaInscricao" + concurso.getId());
            }

            return concurso;
        } catch (Exception e) {
            throw new NegocioException(e.getMessage());
        }
    }

    public Concurso buscarConcursoComCargos(Long id) {
        Concurso c = concursoFacade.findConcursoWithCargo(id);
        return c;
    }

    private void cancelarAgendamentoIncricaoAberta(Concurso concurso) {
        for (Timer timer : timerService.getAllTimers()) {
            if (timer.getInfo().equals("abreInscricao" + concurso.getId())) {
                timer.cancel();
            }
        }
    }

    private void cancelarAgendamentoIncricaoFechada(Concurso concurso) {
        for (Timer timer : timerService.getAllTimers()) {
            if (timer.getInfo().equals("fechaInscricao" + concurso.getId())) {
                timer.cancel();
            }
        }
    }

    private void criarAgendamento(LocalDateTime dataHora, String nomeAgendamento) {
        ScheduleExpression expression = new ScheduleExpression();
        expression.dayOfMonth(dataHora.getDayOfMonth());
        expression.month(dataHora.getMonth().getValue());
        expression.year(dataHora.getYear());
        expression.hour(dataHora.getHour());
        expression.minute(dataHora.getMinute());
        this.timerService.createCalendarTimer(expression, new TimerConfig(nomeAgendamento, true));
    }

    @Timeout
    private void timeOut(Timer timer) {
        List<Concurso> concursos = this.concursoFacade.findAll();
        for (Concurso concurso : concursos) {
            if (timer.getInfo().equals("abreInscricao" + concurso.getId())) {
                abreInscricao(concurso);
            } else if (timer.getInfo().equals("fechaInscricao" + concurso.getId())) {
                fechaInscricao(concurso);
            }
        }
    }

    private void abreInscricao(Concurso concurso) {
        LocalDateTime now = LocalDateTime.now();
        if (concurso.getStatus().equals(StatusConcurso.AUTORIZADO)) {
            if (concurso.isInscricoesAberta()) {
                concurso.setStatus(StatusConcurso.INSCRICOES_ABERTAS);
                em.merge(concurso);
            }
        }
    }

    private void fechaInscricao(Concurso concurso) {
        LocalDateTime now = LocalDateTime.now();
        if (concurso.isInscricoesFechadas()) {
            concurso.setStatus(StatusConcurso.INSCRICOES_ENCERRADAS);
            em.merge(concurso);
        }
    }
}
