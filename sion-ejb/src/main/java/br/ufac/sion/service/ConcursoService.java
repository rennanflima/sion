/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.dao.ConcursoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.dao.util.ConexaoJDBC;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.enuns.StatusConcurso;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.util.report.ExecutorRelatorio;
import java.io.InputStream;
import java.sql.ResultSet;
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
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.hibernate.Session;

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

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    private ConexaoJDBC conexaoJDBC;

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
    
    public void geraRelatorioEstatisticaIncritos(Concurso concurso, String status, HttpServletResponse response) throws NegocioException {
        Map<String, Object> parameters = new HashMap<>();
        InputStream logo = getClass().getResourceAsStream("/relatorios/topo.jpg");
        parameters.put("id_concurso", concurso.getId());
        parameters.put("logo", logo);
        
        ExecutorRelatorio executor;
        
        if (status.equals("CONFIRMADA")) {
            executor = new ExecutorRelatorio("/relatorios/estatistica_inscritos.jasper", response, parameters, "estatistica_inscritos_confirmados_" + concurso.getId() + ".pdf");
        } else {
            executor = new ExecutorRelatorio("/relatorios/estatistica_inscritos_confirmados.jasper", response, parameters, "estatistica_inscritos_" + concurso.getId() + ".pdf");
        }
        
        Session session = em.unwrap(Session.class);
        session.doWork(executor);
        if (!executor.isRelatorioGerado()) {
            throw new NegocioException("A execução do relatório não retornou dados.");
        }
    }
    
    public void geraRelatorioInscritos(Concurso concurso, HttpServletResponse response) throws NegocioException {
        Map<String, Object> parameters = new HashMap<>();
        InputStream logo = getClass().getResourceAsStream("/relatorios/topo.jpg");
        parameters.put("id_concurso", concurso.getId());
        parameters.put("logo", logo);
        
        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritos_grupo.jasper", response, parameters, "relacao_inscritos_" + concurso.getId() + ".pdf");
        
        Session session = em.unwrap(Session.class);
        session.doWork(executor);
        
        if (!executor.isRelatorioGerado()) {
            throw new NegocioException("A execução do relatório não retornou dados.");
        }
    }

    public void geraRelatorioInscritosDeferidos(Concurso concurso, HttpServletResponse response) throws NegocioException {
        Map<String, Object> parameters = new HashMap<>();
        InputStream logo = getClass().getResourceAsStream("/relatorios/topo.jpg");
        parameters.put("id_concurso", concurso.getId());
        parameters.put("logo", logo);
        
        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritos_grupo_deferidos.jasper", response, parameters, "relacao_inscritos_deferidos_" + concurso.getId() + ".pdf");
        
        Session session = em.unwrap(Session.class);
        session.doWork(executor);
        
        if (!executor.isRelatorioGerado()) {
            throw new NegocioException("A execução do relatório não retornou dados.");
        }
    }

    public void geraRelatorioInscritosDeferidosPNE(Concurso concurso, HttpServletResponse response) throws NegocioException {
        Map<String, Object> parameters = new HashMap<>();
        InputStream logo = getClass().getResourceAsStream("/relatorios/topo.jpg");
        parameters.put("id_concurso", concurso.getId());
        parameters.put("logo", logo);
        
        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritos_grupo_confirmada_deficiente.jasper", response, parameters, "relacao_inscritos_deferidos_pne_" + concurso.getId() + ".pdf");
        
        Session session = em.unwrap(Session.class);
        session.doWork(executor);
        
        if (!executor.isRelatorioGerado()) {
            throw new NegocioException("A execução do relatório não retornou dados.");
        }
    }

    public void geraRelatorioListaPresenca(Concurso concurso, HttpServletResponse response) throws NegocioException {
        Map<String, Object> parameters = new HashMap<>();
        InputStream logo = getClass().getResourceAsStream("/relatorios/topo.jpg");
        parameters.put("id_concurso", concurso.getId());
        parameters.put("logo", logo);
        
        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritos_presenca.jasper", response, parameters, "inscritos_presenca_" + concurso.getId() + ".pdf");
        
        Session session = em.unwrap(Session.class);
        session.doWork(executor);
        
        if (!executor.isRelatorioGerado()) {
            throw new NegocioException("A execução do relatório não retornou dados.");
        }
    }

    public void geraRelatorioInscritosIndeferidos(Concurso concurso, HttpServletResponse response) throws NegocioException {
        Map<String, Object> parameters = new HashMap<>();
        InputStream logo = getClass().getResourceAsStream("/relatorios/topo.jpg");
        parameters.put("id_concurso", concurso.getId());
        parameters.put("logo", logo);
        
        ExecutorRelatorio executor = new ExecutorRelatorio("/relatorios/inscritos_grupo_indeferidos.jasper", response, parameters, "relacao_inscritos_indeferidos_" + concurso.getId() + ".pdf");
        
        Session session = em.unwrap(Session.class);
        session.doWork(executor);
        
        if (!executor.isRelatorioGerado()) {
            throw new NegocioException("A execução do relatório não retornou dados.");
        }
    }


}
