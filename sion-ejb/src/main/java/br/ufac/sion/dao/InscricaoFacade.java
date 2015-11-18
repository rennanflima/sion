/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.SituacaoInscricao;
import br.ufac.sion.model.vo.DataQuantidade;
import br.ufac.sion.util.conversor.DateConversor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
    public List<Inscricao> findByConcurso(Concurso concurso) {
        return em.createQuery("SELECT i FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso", Inscricao.class)
                .setParameter("concurso", concurso)
                .getResultList();
    }

    @Override
    public List<Inscricao> findByConcursoAndConfirmadas(Concurso concurso, int first, int pageSize) {
        return em.createQuery("SELECT i FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND status = :status", Inscricao.class)
                .setParameter("concurso", concurso)
                .setParameter("status", SituacaoInscricao.CONFIRMADA)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList();
    }

    @Override
    public List<Inscricao> findByConcursoAndPNE(Concurso concurso) {
        return em.createQuery("SELECT i FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND i.NecessidadeEspecial.portador = :portador OR i.NecessidadeEspecial.necessitaAtendimento = :necessitaAtendimento", Inscricao.class)
                .setParameter("concurso", concurso)
                .setParameter("portador", true)
                .setParameter("necessitaAtendimento", true)
                .getResultList();
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
    public Long encontrarQuantidadeDeInscricoesConfirmadas(Concurso concurso) {
        return em.createQuery("SELECT count(i) FROM Inscricao i WHERE i.cargoConcurso.concurso = :concurso AND status = :status", Long.class)
                .setParameter("concurso", concurso)
                .setParameter("status", SituacaoInscricao.CONFIRMADA)
                .getSingleResult();
    }
}
