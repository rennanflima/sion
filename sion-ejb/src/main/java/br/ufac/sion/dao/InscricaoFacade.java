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
import br.ufac.sion.model.vo.DataQuantidade;
import br.ufac.sion.model.vo.FiltroInscritos;
import br.ufac.sion.model.vo.FiltroInscritosRelatorio;
import br.ufac.sion.util.conversor.DateConversor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class InscricaoFacade extends AbstractFacade<Inscricao, Long> implements InscricaoFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InscricaoFacade() {
        super(Inscricao.class);
    }

    @Override
    public Inscricao save(Inscricao entity) {
        throw new UnsupportedOperationException("Operação não suportada! Para salvar utilize InscricaoService."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Inscricao entity) {
        throw new UnsupportedOperationException("Operação não suportada! Não é possível excluir uma inscrição."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Inscricao> findByCandidato(Candidato candidato) {
        return em.createQuery("SELECT i FROM Inscricao i WHERE i.candidato = :candidato", Inscricao.class)
                .setParameter("candidato", candidato)
                .getResultList();
    }

    @Override
    public List<Inscricao> findIncricoesAtivasByCandidato(Candidato candidato) {
        return em.createQuery("SELECT i FROM Inscricao i WHERE i.candidato = :candidato and i.status != :situcao", Inscricao.class)
                .setParameter("candidato", candidato)
                .setParameter("situcao", SituacaoInscricao.CANCELADA)
                .getResultList();
    }

    @Override
    public List<Inscricao> findByConcurso(FiltroInscritos filtro) {
//        return em.createQuery("SELECT i FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso", Inscricao.class)
//                .setParameter("concurso", concurso)
//                .getResultList();
        Criteria criteria = criarCriteriaParaFiltro(filtro);

        criteria.setFirstResult(filtro.getPrimeiroRegistro());
        criteria.setMaxResults(filtro.getQuantidadeRegistros());

        return criteria.list();
    }

    @Override
    public List<Inscricao> findByConcursoAndConfirmadasESubJudice(FiltroInscritos filtro) {
//        return em.createQuery("SELECT i FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND status = :confirmada OR status = :judice", Inscricao.class)
//                .setParameter("concurso", concurso)
//                .setParameter("confirmada", SituacaoInscricao.CONFIRMADA)
//                .setParameter("judice", SituacaoInscricao.SUB_JUDICE)
//                .setFirstResult(first)
//                .setMaxResults(pageSize)
//                .getResultList();

        Criteria criteria = criarCriteriaParaFiltro(filtro);

        //criteria.createAlias("candidato", "c");
        criteria.setFirstResult(filtro.getPrimeiroRegistro());
        criteria.setMaxResults(filtro.getQuantidadeRegistros());

        Criterion confirmada = Restrictions.eq("status", SituacaoInscricao.CONFIRMADA);
        Criterion judice = Restrictions.eq("status", SituacaoInscricao.SUB_JUDICE);
        criteria.add(Restrictions.or(confirmada, judice));

        //criteria.addOrder(Order.asc("c.nome"));
        return criteria.list();
    }

    @Override
    public List<Inscricao> findByConcursoAndPNE(FiltroInscritos filtro) {
//        return em.createQuery("SELECT i FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND i.NecessidadeEspecial.portador = :portador OR i.NecessidadeEspecial.necessitaAtendimento = :necessitaAtendimento", Inscricao.class)
//                .setParameter("concurso", concurso)
//                .setParameter("portador", true)
//                .setParameter("necessitaAtendimento", true)
//                .getResultList();

        Criteria criteria = criarCriteriaParaFiltro(filtro);

        //criteria.createAlias("necessidadeEspecial", "ne");
        criteria.setFirstResult(filtro.getPrimeiroRegistro());
        criteria.setMaxResults(filtro.getQuantidadeRegistros());

        Criterion portador = Restrictions.eq("necessidadeEspecial.portador", true);
        Criterion necessitaAtendimento = Restrictions.eq("necessidadeEspecial.necessitaAtendimento", true);
        criteria.add(Restrictions.or(portador, necessitaAtendimento));

        //criteria.addOrder(Order.asc("c.nome"));
        return criteria.list();
    }

    @Override
    public Map<Date, Long> inscricoesPorData(Concurso concurso, SituacaoInscricao situacao) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Inscricao.class);

        LocalDateTime dataInicial = concurso.getDataInicioInscricao();

        Integer dias = Period.between(concurso.getDataInicioInscricao().toLocalDate(), concurso.getDataTerminoIncricao().toLocalDate()).getDays();
        Map<Date, Long> resultado = criaMapaVazio(dias, dataInicial);

        criteria.createAlias("cargoConcurso", "cc")
                .setProjection(Projections.projectionList()
                        .add(Projections.sqlGroupProjection("date(data_inscricao) as data",
                                "date(data_inscricao)", new String[]{"data"},
                                new Type[]{StandardBasicTypes.DATE}))
                        .add(Projections.count("id").as("quantidade"))
                )
                .add(Restrictions.ge("dataInscricao", dataInicial))
                .add(Restrictions.eq("cc.concurso", concurso));

        if (situacao != null) {
            criteria.add(Restrictions.eq("status", situacao.CONFIRMADA));
        }

        List<DataQuantidade> quantidadesPorData
                = criteria.setResultTransformer(Transformers.aliasToBean(DataQuantidade.class)).list();

        for (DataQuantidade quantidadeData : quantidadesPorData) {
            resultado.put(quantidadeData.getData(), quantidadeData.getQuantidade());
        }

        return resultado;
    }

    private Map<Date, Long> criaMapaVazio(Integer numeroDeDias, LocalDateTime dataInicial) {
        Map<Date, Long> mapaInicial = new TreeMap<>();

        LocalDate dt = dataInicial.toLocalDate();

        for (int i = 0; i <= numeroDeDias; i++) {
            mapaInicial.put(DateConversor.convertLocalDateToDate(dt), 0L);
            dt = dt.plusDays(1);
        }

        return mapaInicial;
    }

    @Override
    public int contaInscricoesConfirmadasESubJudice(FiltroInscritos filtro) {
//        return em.createQuery("SELECT count(i) FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND status = :confirmada OR status = :judice", Long.class)
//                .setParameter("concurso", concurso)
//                .setParameter("confirmada", SituacaoInscricao.CONFIRMADA)
//                .setParameter("judice", SituacaoInscricao.SUB_JUDICE)
//                .getSingleResult();

        Criteria criteria = criarCriteriaParaFiltro(filtro);
        Criterion confirmada = Restrictions.eq("status", SituacaoInscricao.CONFIRMADA);
        Criterion judice = Restrictions.eq("status", SituacaoInscricao.SUB_JUDICE);
        criteria.add(Restrictions.or(confirmada, judice));

        criteria.setProjection(Projections.rowCount());

        return ((Number) criteria.uniqueResult()).intValue();
    }

    @Override
    public Long encontrarQuantidadeDeInscricoesNaoConfirmadas(Concurso concurso) {
        return em.createQuery("SELECT count(i) FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND status = :situacao", Long.class)
                .setParameter("concurso", concurso)
                .setParameter("situacao", SituacaoInscricao.AGUARDANDO_PAGAMENTO)
                .getSingleResult();
    }

    @Override
    public Long encontrarQuantidadeDeInscricoes(Concurso concurso) {
        return em.createQuery("SELECT count(i) FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso", Long.class)
                .setParameter("concurso", concurso)
                .getSingleResult();
    }

    @Override
    public Long encontrarQuantidadeDeInscricoesPNE(Concurso concurso) {
        return em.createQuery("SELECT count(i) FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND i.necessidadeEspecial.portador = :portador OR i.necessidadeEspecial.necessitaAtendimento = :necessitaAtendimento", Long.class)
                .setParameter("concurso", concurso)
                .setParameter("portador", true)
                .setParameter("necessitaAtendimento", true)
                .getSingleResult();
    }

    @Override
    public Long encontrarQuantidadeDeInscricoesSubJudice(Concurso concurso) {
        return em.createQuery("SELECT count(i) FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND status = :status", Long.class)
                .setParameter("concurso", concurso)
                .setParameter("status", SituacaoInscricao.SUB_JUDICE)
                .getSingleResult();
    }

    @Override
    public Long encontrarQuantidadeDeInscricoesConfirmadas(Concurso concurso) {
        return em.createQuery("SELECT count(i) FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND status = :status", Long.class)
                .setParameter("concurso", concurso)
                .setParameter("status", SituacaoInscricao.CONFIRMADA)
                .getSingleResult();
    }

    private Criteria criarCriteriaParaFiltro(FiltroInscritos filtro) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Inscricao.class);

        criteria.createAlias("cargoConcurso", "cc").add(Restrictions.eq("cc.concurso", filtro.getConcurso()));
        criteria.createAlias("candidato", "c");

        if (filtro.getCargo() != null && filtro.getCargo().getId() != null) {
            criteria.add(Restrictions.eq("cargoConcurso", filtro.getCargo()));
        }

        if (StringUtils.isNotEmpty(filtro.getNome())) {
            criteria.add(Restrictions.ilike("c.nome", filtro.getNome(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotEmpty(filtro.getCpf())) {
            criteria.add(Restrictions.ilike("c.cpf", filtro.getCpf(), MatchMode.EXACT));
        }
        if (StringUtils.isNotEmpty(filtro.getNumeroInscricao())) {
            criteria.add(Restrictions.ilike("numero", filtro.getNumeroInscricao(), MatchMode.START));
        }

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    @Override
    public Long encontrarQuantidadeDeInscricoesConfirmadasESubJudice(Concurso concurso) {
        return em.createQuery("SELECT count(i) FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND status = :confirmada OR status = :judice", Long.class)
                .setParameter("concurso", concurso)
                .setParameter("confirmada", SituacaoInscricao.CONFIRMADA)
                .setParameter("judice", SituacaoInscricao.SUB_JUDICE)
                .getSingleResult();
    }

    @Override
    public int contaInscricoes(FiltroInscritos filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);

        criteria.setProjection(Projections.rowCount());

        return ((Number) criteria.uniqueResult()).intValue();
    }

    @Override
    public int contaInscricoesPNE(FiltroInscritos filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);

        //criteria.createAlias("necessidadeEspecial", "ne");
        Criterion portador = Restrictions.eq("necessidadeEspecial.portador", true);
        Criterion necessitaAtendimento = Restrictions.eq("necessidadeEspecial.necessitaAtendimento", true);
        criteria.add(Restrictions.or(portador, necessitaAtendimento));

        criteria.setProjection(Projections.rowCount());

        return ((Number) criteria.uniqueResult()).intValue();
    }

    @Override
    public Long encontrarQuatidadeDeInscricoesPorCargo(CargoConcurso cargoConcurso, String status) {

        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Inscricao.class);

        criteria.add(Restrictions.eq("cargoConcurso", cargoConcurso));

        if (StringUtils.isNotEmpty(status) && status.equals("CONFIRMADA")) {
            Criterion confirmada = Restrictions.eq("status", SituacaoInscricao.CONFIRMADA);
            Criterion judice = Restrictions.eq("status", SituacaoInscricao.SUB_JUDICE);
            criteria.add(Restrictions.or(confirmada, judice));
        }

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.setProjection(Projections.rowCount());

        return ((Number) criteria.uniqueResult()).longValue();
//        return em.createQuery("SELECT count(i) FROM Inscricao i WHERE i.cargoConcurso = :cargo", Long.class)
//                .setParameter("cargo", cargoConcurso)
//                .getSingleResult();
    }

    @Override
    public ResultSet findInscritosByConcurso(Concurso concurso) {
        Session session = em.unwrap(Session.class);
        SessionFactoryImpl sfi = (SessionFactoryImpl) session.getSessionFactory();
        ResultSet rs = null;
        try {
            Connection con = sfi.getConnectionProvider().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT i.numero AS ficha_inscricao_numero_inscricao, "
                    + "cand.nome AS ficha_inscricao_nome, "
                    + "cand.cpf AS ficha_inscricao_cpf, "
                    + "i.data_inscricao AS ficha_inscricao_data_cadastro, "
                    + "CONCAT(cc.codigo,' - ',c.descricao)  AS ficha_inscricao_cargo, "
                    + "l.nome AS ficha_inscricao_local, "
                    + "i.portador  AS ficha_inscricao_possui_deficiencia, "
                    + "i.`status` AS ficha_inscricao_situacao, "
                    + "conc.titulo AS concurso_nome "
                    + "FROM "
                    + "inscricao i, "
                    + "candidato cand, "
                    + "cargo c, "
                    + "localidade l, "
                    + "concurso conc, "
                    + "cargo_concurso cc "
                    + "WHERE "
                    + "i.candidato_id = cand.id AND "
                    + "i.cargo_concurso_id = cc.id AND "
                    + "cc.cargo_id = c.id AND "
                    + "cc.localidade_id = l.id AND "
                    + "cc.concurso_id = conc.id AND "
                    + "conc.id = ? "
                    + "ORDER BY "
                    + "l.nome, "
                    + "c.descricao, "
                    + "cand.nome ");
            ps.setLong(1, concurso.getId());
            rs = ps.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(InscricaoFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Inscricao> findByCargoAndLocalidade(FiltroInscritosRelatorio filtroRelatorio) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Inscricao.class);

        criteria.createAlias("cargoConcurso", "cc").add(Restrictions.eq("cc.concurso", filtroRelatorio.getConcurso()));
        criteria.createAlias("candidato", "c");

        if (filtroRelatorio.getCargo() != null && filtroRelatorio.getCargo().getId() != null) {
            criteria.add(Restrictions.eq("cargoConcurso", filtroRelatorio.getCargo()));
        }

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        criteria.setFirstResult(filtroRelatorio.getPrimeiroRegistro());
        criteria.setMaxResults(filtroRelatorio.getQuantidadeRegistros());

        Criterion confirmada = Restrictions.eq("status", SituacaoInscricao.CONFIRMADA);
        Criterion judice = Restrictions.eq("status", SituacaoInscricao.SUB_JUDICE);
        criteria.add(Restrictions.or(confirmada, judice));

        //criteria.addOrder(Order.asc("c.nome"));
        return criteria.list();
    }

    @Override
    public int contaInscricoesByCargoAndLocalidade(FiltroInscritosRelatorio filtroRelatorio) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Inscricao.class);

        criteria.createAlias("cargoConcurso", "cc").add(Restrictions.eq("cc.concurso", filtroRelatorio.getConcurso()));
        criteria.createAlias("candidato", "c");

        if (filtroRelatorio.getCargo() != null && filtroRelatorio.getCargo().getId() != null) {
            criteria.add(Restrictions.eq("cargoConcurso", filtroRelatorio.getCargo()));
        }

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        Criterion confirmada = Restrictions.eq("status", SituacaoInscricao.CONFIRMADA);
        Criterion judice = Restrictions.eq("status", SituacaoInscricao.SUB_JUDICE);
        criteria.add(Restrictions.or(confirmada, judice));

        criteria.setProjection(Projections.rowCount());

        return ((Number) criteria.uniqueResult()).intValue();
    }
}
