/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.dao.util.ConexaoJDBC;
import br.ufac.sion.model.Candidato;
import br.ufac.sion.model.Concurso;
import br.ufac.sion.model.Inscricao;
import br.ufac.sion.model.enuns.SituacaoBoleto;
import br.ufac.sion.model.enuns.SituacaoInscricao;
import br.ufac.sion.exception.NegocioException;
import br.ufac.sion.service.util.InfoEmail;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class InscricaoService {

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    @EJB
    private EnviaEmailService enviaEmailService;

    private ConexaoJDBC conexaoJDBC;

    private InfoEmail infoEmail;

    public Inscricao salvar(Inscricao inscricao) throws NegocioException {
        LocalDateTime now = LocalDateTime.now();
        infoEmail = new InfoEmail();
        String assunto = "";
        try {
            if (inscricao.getId() != null) {
                if (inscricao.getBoleto() != null) {
                    inscricao.getBoleto().setSituacao(SituacaoBoleto.PENDENTE);
                }
                inscricao.setDataInscricao(now);
                inscricao.setStatus(SituacaoInscricao.AGUARDANDO_PAGAMENTO);
                inscricao = em.merge(inscricao);

                assunto = "Alteração da solicitação de inscrição no " + inscricao.getCargoConcurso().getConcurso().getTitulo();
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

                assunto = "Solicitação de inscrição no " + inscricao.getCargoConcurso().getConcurso().getTitulo();
            }

            infoEmail.setPara(inscricao.getCandidato().getEmail());
            infoEmail.setAssunto(assunto);
            infoEmail.setCorpo(geraCorpoEmailSolicitacaoInscricao(inscricao));
            enviaEmailService.processaEnvioDeEmail(infoEmail);

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

    public void confirmaInscricao(Inscricao inscricao) throws NegocioException {
        if (StringUtils.isNotBlank(inscricao.getJustificativaStatus())) {
            inscricao.setStatus(SituacaoInscricao.CONFIRMADA);
            inscricao.setDataJustificativaStatus(LocalDateTime.now());
            em.merge(inscricao);
        } else {
            throw new NegocioException("A justificativa é obrigatória");
        }
    }

    private String geraCorpoEmailSolicitacaoInscricao(Inscricao inscricao) throws IOException {
        InputStream stream = getClass().getResourceAsStream("/comprovante.html");
        byte[] acessoBytes = new byte[stream.available()];
        stream.read(acessoBytes);
        stream.close();
        String body = new String(acessoBytes);
        body = body.replaceAll("@@@NOME_USUARIO@@@", inscricao.getCandidato().getNome());
        body = body.replaceAll("@@@CONCURSO@@@", inscricao.getCargoConcurso().getConcurso().getTitulo());
        body = body.replaceAll("@@@CARGO@@@", inscricao.getCargoConcurso().getCodigo() + " - " + inscricao.getCargoConcurso().getCargo().getDescricao());
        body = body.replaceAll("@@@CIDADE_PROVA@@@", inscricao.getCargoConcurso().getLocalidade().getNome());
        body = body.replaceAll("@@@BANCO@@@", inscricao.getCargoConcurso().getConcurso().getContaBancaria().getBanco().getDescricao());
        body = body.replaceAll("@@@NOME_FANTASIA@@@", inscricao.getCargoConcurso().getConcurso().getContaBancaria().getCedente().getNomeFantasia());
        body = body.replaceAll("@@@SIGLA@@@", inscricao.getCargoConcurso().getConcurso().getContaBancaria().getCedente().getSigla());
        return body;
    }

    public JasperPrint geraRelatorioFormularioInscricao(Inscricao inscricao) throws JRException {
        this.conexaoJDBC = new ConexaoJDBC();
        Map<String, Object> parameters = new HashMap<>();
        InputStream logo = getClass().getResourceAsStream("/relatorios/topo.jpg");
        parameters.put("inscricao_id", inscricao.getId());
        parameters.put("logo", logo);

        JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/relatorios/inscricao_detalhe.jrxml"));
        JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conexaoJDBC.abreConexao());
        conexaoJDBC.fechaConexao();
        return jp;
    }
}
