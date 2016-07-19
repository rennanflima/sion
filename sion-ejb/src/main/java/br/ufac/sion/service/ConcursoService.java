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
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

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
        } else if (concurso.isInscricoesAberta()) {
            concurso.setStatus(StatusConcurso.INSCRICOES_ABERTAS);
        } else if (concurso.isInscricoesFechadas()) {
            concurso.setStatus(StatusConcurso.INSCRICOES_ENCERRADAS);
        } else if (concurso.isAutorizado()) {
            concurso.setStatus(StatusConcurso.AUTORIZADO);
        }

        if (concurso.getDataTerminoIncricao().isBefore(concurso.getDataInicioInscricao())) {
            throw new NegocioException("A data de termíno das inscrição deve ser maior que a data de início das inscrições");
        }

        try {
            concurso = em.merge(concurso);
            if (now.isBefore(concurso.getDataInicioInscricao())) {
                criarAgendamento(concurso.getDataInicioInscricao(), "abreInscricao" + concurso.getId());
            }

            if (now.isBefore(concurso.getDataTerminoIncricao())) {
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

    private void criarAgendamento(LocalDateTime fim, String nomeAgendamento) {
        LocalDateTime inicio = LocalDateTime.now();
        Duration duracao = Duration.between(inicio, fim);
        this.timerService.createTimer(duracao.toMillis(), nomeAgendamento);
    }

    @Timeout
    private void verificaInscricoes(Timer timer) {
        System.out.println("Time Service : " + timer.getInfo());
        System.out.println("Data da Execução : " + new Date());
        List<Concurso> concursos = this.concursoFacade.findAll();
        for (Concurso concurso : concursos) {
            if (timer.getInfo().equals("abreInscricao" + concurso.getId())) {
                concurso.setStatus(StatusConcurso.INSCRICOES_ABERTAS);
                em.merge(concurso);
                System.out.println("Abriu as incrições do Concurso : " + concurso.getTitulo());
            } else if (timer.getInfo().equals("fechaInscricao" + concurso.getId())) {
                concurso.setStatus(StatusConcurso.INSCRICOES_ENCERRADAS);
                em.merge(concurso);
                System.out.println("Encerrou as incrições do Concurso : " + concurso.getTitulo());
            }
        }
        System.out.println("____________________________________________");
    }
    
    
    public byte[] geraPDFEstatisticaIncritosConfirmados(Concurso concurso) throws JRException{
        Map<String, Object> parameters = new HashMap<>();
        InputStream logo = getClass().getResourceAsStream("/relatorios/topo.jpg");
        parameters.put("id_concurso", concurso.getId());
        parameters.put("logo",logo);
        
        JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/relatorios/estatistica_inscritos_confirmados.jrxml"));
        JasperPrint jp = JasperFillManager.fillReport(jr, parameters);
        byte[] paraRetorno = JasperExportManager.exportReportToPdf(jp);
        System.out.println("PDF: "+paraRetorno);
        return paraRetorno;
    }
}
