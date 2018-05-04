/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.audit.CustomRevisionEntity;
import br.ufac.sion.dto.AuditoriaDTO;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.service.util.FiltroAuditoria;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.criteria.MatchMode;

/**
 *
 * @author Rennan
 */
@Stateless
public class AuditoriaService {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    private static final ZoneId ZONE = ZoneId.systemDefault();

    public List<AuditoriaDTO> findAll(FiltroAuditoria filtro) throws NegocioException {
//    public List<AuditoriaDTO> findAll(Class<?> classe, String nomeEntidade, String login, LocalDateTime dataInicio, LocalDateTime dataFim) throws NegocioException {
        Set<AuditoriaDTO> revisions = new HashSet<>();

        revisions.addAll(findAllRevisions(filtro));

        ArrayList<AuditoriaDTO> list = new ArrayList<>(revisions);

        Collections.sort(list);

        return list;
    }

//    public Set<AuditoriaDTO> findAllRevisions(Class<?> classe, String entidade, String login, LocalDateTime startDate, LocalDateTime endDate) throws NegocioException {
    public Set<AuditoriaDTO> findAllRevisions(FiltroAuditoria filtro) throws NegocioException {
        if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
            if (!filtro.getDataFim().isAfter(filtro.getDataInicio())) {
                throw new NegocioException("A data de termino deve ser maior que a data de início!");
            }
        }
        Set<AuditoriaDTO> dtos = new HashSet<>();

        AuditReader reader = AuditReaderFactory.get(em);

        AuditQuery query = reader.createQuery()
                .forRevisionsOfEntity(filtro.getClasse(), false, true);

        if (filtro.getDataInicio() != null) {
            query.add(AuditEntity.revisionProperty("timestamp")
                    .gt(Timestamp.from(filtro.getDataInicio().toInstant(ZoneOffset.UTC)).getTime()));
        }
        if (filtro.getDataFim() != null) {
            query.add(AuditEntity.revisionProperty("timestamp")
                    .lt(Timestamp.from(filtro.getDataFim().toInstant(ZoneOffset.UTC)).getTime()));
        }

        if (StringUtils.isNotEmpty(filtro.getLogin())) {
            query.add(AuditEntity.revisionProperty("username").ilike(filtro.getLogin(), MatchMode.EXACT));
        }

        if (filtro.getTiposRevisao().length > 0) {
            System.out.println("entra id tipo revisao");
            query.add(AuditEntity.property("REVTYPE").in(filtro.getTiposRevisao()));
        }

        List<Object[]> result = query.getResultList();

        for (Object[] o : result) {
            try {
//                Object instancia = filtro.getClass().cast(o[0]);
                Object instancia = Class.forName(filtro.getClasse().getName()).cast(o[0]);
//                Method metodo;
//                metodo = instancia.getClass().getMethod("getId");
//                Long id = (Long) metodo.invoke(instancia);

                CustomRevisionEntity revision = (CustomRevisionEntity) o[1];
                RevisionType revisionType = (RevisionType) o[2];

                Instant instant = Instant.ofEpochMilli(revision.getTimestamp());

                AuditoriaDTO dto = new AuditoriaDTO(instancia, revisionType, filtro.getEntidade(), revision);

                dtos.add(dto);
            } catch (Exception ex) {
                throw new NegocioException(ex.getMessage());
            }
        }
        return dtos;
    }

    public <T> List<T> findRevisionsById(Class<?> classe, Long id) {
        AuditReader reader = AuditReaderFactory.get(em);

        AuditQuery q = reader.createQuery().forRevisionsOfEntity(classe, true, true);
        q.add(AuditEntity.id().eq(id));
        List<T> audit = q.getResultList();

        return audit;
    }

}
