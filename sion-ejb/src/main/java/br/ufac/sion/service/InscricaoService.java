/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.SituacaoBoleto;
import br.ufac.sion.model.SituacaoInscricao;
import br.ufac.sion.exception.NegocioException;
import java.time.LocalDateTime;
import java.time.Year;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class InscricaoService {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    public Inscricao salvar(Inscricao inscricao) throws NegocioException {
        LocalDateTime now = LocalDateTime.now();
        try {
            if (inscricao.getId() != null) {
                if(inscricao.getBoleto() != null){
                    inscricao.getBoleto().setSituacao(SituacaoBoleto.PENDENTE);
                }
                inscricao.setDataInscricao(now);
                inscricao.setStatus(SituacaoInscricao.AGUARDANDO_PAGAMENTO);
                inscricao = em.merge(inscricao);
            } else {
                Inscricao insc = pesquisarPorCandidatoEConcurso(inscricao.getCandidato(), inscricao.getCargoConcurso().getConcurso());
                if (insc != null) {
                    throw new NegocioException("Já foi realizada uma inscrição com o CPF '" + inscricao.getCandidato().getCpf() + "' para o concurso.");
                }
                inscricao.setDataInscricao(now);
                inscricao.setStatus(SituacaoInscricao.AGUARDANDO_PAGAMENTO);
                inscricao = em.merge(inscricao);
                inscricao.setNumero(geraNumeroInscricao(inscricao));
                inscricao = em.merge(inscricao);
            }
            return inscricao;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NegocioException(e.getMessage());
        }
    }

    public Inscricao pesquisarPorCandidatoEConcurso(Candidato candidato, Concurso concurso) {
        Inscricao i = null;
        try {
            i = em.createQuery("select i from Inscricao i where i.candidato = :candidato AND i.cargoConcurso.concurso = :concurso", Inscricao.class)
                    .setParameter("candidato", candidato)
                    .setParameter("concurso", concurso)
                    .getSingleResult();
        } catch (NoResultException e) {

        }
        return i;
    }

    private String geraNumeroInscricao(Inscricao inscricao) {
        return Year.now() + "" + inscricao.getCargoConcurso().getConcurso().getId() + "" + inscricao.getId();
    }
    
    public void confirmaInscricao(Inscricao inscricao) throws NegocioException{
        if(StringUtils.isNotBlank(inscricao.getJustificativaStatus())){
            inscricao.setStatus(SituacaoInscricao.CONFIRMADA);
            inscricao.setDataJustificativaStatus(LocalDateTime.now());
            em.merge(inscricao);
        }else{
            throw new NegocioException("A justificativa é obrigatória");
        }
    }
}
