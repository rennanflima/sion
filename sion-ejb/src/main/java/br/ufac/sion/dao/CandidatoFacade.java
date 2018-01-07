/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao;

import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.vo.FiltroCandidatos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class CandidatoFacade extends AbstractFacade<Candidato, Long> implements CandidatoFacadeLocal {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CandidatoFacade() {
        super(Candidato.class);
    }

    @Override
    public Candidato findByCPF(String cpf) {

        Candidato candidato = null;
        String cpf_formatado = "";

        if (cpf.length() == 11) {
            cpf_formatado = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
        } else {
            cpf_formatado = cpf;
        }

        try {
            candidato = em.createQuery("SELECT c FROM Candidato c JOIN FETCH c.usuario u WHERE c.cpf = :cpf", Candidato.class)
                    .setParameter("cpf", cpf_formatado)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum candidato econtrado com o CPF informado.");
        }
        return candidato;
    }

    @Override
    public Candidato save(Candidato entity) {
        throw new UnsupportedOperationException("Operação não suportada! Para salvar utilize CandidatoService.");
    }

    @Override
    public Candidato findByEmail(String email) {
        Candidato candidato = null;
        try {
            candidato = em.createQuery("SELECT c FROM Candidato JOIN FETCH c.usuario u WHERE u.email = :email", Candidato.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Nenhum candidato econtrado com o CPF informado.");
        }
        return candidato;
    }

    @Override
    public Candidato findByCPFWithInscricoes(String cpf) {

        Candidato candidato = null;
        String cpf_formatado = "";

        if (cpf.length() == 11) {
            cpf_formatado = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
        } else {
            cpf_formatado = cpf;
        }

        try {
            candidato = em.createQuery("SELECT c FROM Candidato c JOIN FETCH c.usuario u LEFT JOIN FETCH c.inscricoes i WHERE c.cpf = :cpf", Candidato.class)
                    .setParameter("cpf", cpf_formatado)
                    .getSingleResult();
            System.out.println("Candidato = " + candidato.getId());
        } catch (NoResultException e) {
            System.out.println("Nenhum candidato econtrado com o CPF informado.");
        }
        return candidato;
    }

    @Override
    public List<Candidato> buscaTodosComPaginacao(FiltroCandidatos filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);

        criteria.setFirstResult(filtro.getPrimeiroRegistro());
        criteria.setMaxResults(filtro.getQuantidadeRegistros());

        return criteria.list();
    }

    private Criteria criarCriteriaParaFiltro(FiltroCandidatos filtro) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(Candidato.class);

        criteria.createAlias("usuario", "u");

        if (StringUtils.isNotEmpty(filtro.getNome())) {
            criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotEmpty(filtro.getCpf())) {
            criteria.add(Restrictions.ilike("cpf", filtro.getCpf(), MatchMode.EXACT));
        }
        if (StringUtils.isNotEmpty(filtro.getEmail())) {
            criteria.add(Restrictions.ilike("u.email", filtro.getEmail(), MatchMode.START));
        }

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria;
    }

    @Override
    public int contaCandidatos(FiltroCandidatos filtro) {
        Criteria criteria = criarCriteriaParaFiltro(filtro);

        criteria.setProjection(Projections.rowCount());

        return ((Number) criteria.uniqueResult()).intValue();
    }

}
