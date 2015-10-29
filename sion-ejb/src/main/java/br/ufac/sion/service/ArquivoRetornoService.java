/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service;

import br.ufac.sion.dao.BoletoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.exception.ArquivoRetornoException;
import br.ufac.sion.model.ArquivoRetorno;
import br.ufac.sion.model.SituacaoBoleto;
import br.ufac.sion.model.SituacaoInscricao;
import br.ufac.sion.service.util.ArquivoRetornoDetalhe;
import br.ufac.sion.util.conversor.DateConversor;
import br.ufac.sion.util.retorno.bradesco.ArquivoRetornoBradesco;
import br.ufac.sion.util.retorno.bradesco.Cabecalho;
import br.ufac.sion.util.retorno.bradesco.Sumario;
import br.ufac.sion.util.retorno.bradesco.TransacaoTitulo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author rennan.lima
 */
@Stateless
public class ArquivoRetornoService {

    @EJB
    private BoletoFacadeLocal boletoFacade;

    @EJB
    private InscricaoFacadeLocal inscricaoFacade;

    @PersistenceContext(unitName = "sionPU")
    private EntityManager em;

    private ArquivoRetornoDetalhe ard;

    private ArquivoRetorno ar;

    public ArquivoRetornoDetalhe carregar(String fileName, InputStream inputstream) throws ArquivoRetornoException {
        try {
            ArquivoRetornoBradesco arquivoRetorno = criarArquivoRetorno(fileName, inputstream);
            this.ard = new ArquivoRetornoDetalhe();
            this.ar = new ArquivoRetorno();

            this.ar.setNome(fileName);
            this.ar.setDataUpload(LocalDateTime.now());
            this.ar = em.merge(ar);
            
            carregarMensagens(arquivoRetorno);

            carregarTitulos(arquivoRetorno);

            return ard;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ArquivoRetornoException("Erro ao processar o arquivo de retorno: " + e.getMessage());
        }

    }

    private void carregarTitulos(ArquivoRetornoBradesco arquivoRetorno) {
        Map<Integer, Collection<TransacaoTitulo>> titulosPorOcorrencia = arquivoRetorno.getTransacoesPorCodigoDeOcorrencia();

        int totalTitulosPagos = 0;
        for (TransacaoTitulo t : titulosPorOcorrencia.get(TransacaoTitulo.LIQUIDACAO)) {
            br.ufac.sion.model.Boleto cobranca = this.boletoFacade.findByNossoNumero(t.getNossoNumeroComDigito());
            System.out.println("Nosso numero: " + t.getNossoNumeroComDigito());
            if (cobranca != null) {
                if (t.getValorPago().compareTo(cobranca.getValor()) >= 0) {
                    cobranca.getSacado().setStatus(SituacaoInscricao.CONFIRMADA);
                    cobranca.setSituacao(SituacaoBoleto.PAGO);
                    cobranca.setDataPagamento(DateConversor.convertDateToLocalDate(t.getDataDoCredito()));
                    cobranca.setValorPago(t.getValorPago());
                    cobranca.setArquivo(ar);
                    this.ard.getIncricoesConfirmadas().add(cobranca.getSacado());
                    totalTitulosPagos++;
                }
            }
        }
        this.ard.setTotalTitulosPagos(totalTitulosPagos);
    }

    private void carregarMensagens(ArquivoRetornoBradesco arquivoRetorno) {
        Cabecalho cabecalho = arquivoRetorno.getCabecalho();
        Sumario sumario = arquivoRetorno.getSumario();
        List<String> mensagens = new ArrayList<>();
        this.ard.setDataCredito(DateConversor.convertDateToLocalDate(cabecalho.getDataDoCredito()));
        this.ard.setQuantidadeDeTitulosEmCobranca(sumario.getQuantidadeDeTitulosEmCobranca());
        this.ard.setValorTotalEmCobrancas(sumario.getValorTotalEmCobranca());
    }

    private ArquivoRetornoBradesco criarArquivoRetorno(String fileName, InputStream inputstream) throws ArquivoRetornoException {
        ArquivoRetornoBradesco arquivoRetorno;
        try {
            File arquivo = File.createTempFile(fileName, "");
            FileUtils.copyInputStreamToFile(inputstream, arquivo);
            arquivoRetorno = new ArquivoRetornoBradesco(arquivo);
        } catch (IOException e) {
            throw new ArquivoRetornoException("Erro carregando arquivo de retorno");
        }
        return arquivoRetorno;
    }
}
