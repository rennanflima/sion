/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.service.retorno;

import br.ufac.sion.dao.BoletoFacadeLocal;
import br.ufac.sion.dao.InscricaoFacadeLocal;
import br.ufac.sion.exception.ArquivoRetornoException;
import br.ufac.sion.model.ArquivoRetorno;
import br.ufac.sion.model.SituacaoBoleto;
import br.ufac.sion.model.SituacaoInscricao;
import br.ufac.sion.service.util.ArquivoRetornoDetalhe;
import br.ufac.sion.util.conversor.DateConversor;
import br.ufac.sion.util.retorno.caixa.ArquivoRetornoCaixa;
import br.ufac.sion.util.retorno.caixa.CabecalhoLote;
import br.ufac.sion.util.retorno.caixa.SegmentoT;
import br.ufac.sion.util.retorno.caixa.SegmentoU;
import br.ufac.sion.util.retorno.caixa.SumarioArquivo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
public class ArquivoRetornoCaixaService{

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
            ArquivoRetornoCaixa arquivoRetorno = criarArquivoRetorno(fileName, inputstream);
            this.ard = new ArquivoRetornoDetalhe();
            this.ar = new ArquivoRetorno();

            
            this.ar.setNome(fileName);
            this.ar.setDataUpload(LocalDateTime.now());
            this.ar.setNumero(arquivoRetorno.getCabecalhoLote().getNumeroRetorno());
            this.ar = em.merge(ar);

            carregarMensagens(arquivoRetorno);

            carregarTitulos(arquivoRetorno);

            return ard;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ArquivoRetornoException("Erro ao processar o arquivo de retorno: " + e.getMessage());
        }

    }

    private void carregarTitulos(ArquivoRetornoCaixa arquivoRetorno) {
        Map<Integer, Collection<SegmentoT>> titulosPorOcorrencia = arquivoRetorno.getTransacoesPorCodigoDeOcorrencia();
        BigDecimal valorTotal = BigDecimal.ZERO;
        int totalTitulosPagos = 0;
        for (SegmentoT t : titulosPorOcorrencia.get(SegmentoU.LIQUIDACAO)) {
            br.ufac.sion.model.Boleto cobranca = this.boletoFacade.findByNossoNumero(t.getNossoNumero());
            System.out.println("Nosso numero: " + t.getNossoNumero());
            System.out.println("Numero do documento: " + t.getNumeroDoDocumento());
            if (cobranca != null) {
                if (t.getSegmentoU().getValorPago().compareTo(cobranca.getValor()) >= 0) {
                    cobranca.getSacado().setStatus(SituacaoInscricao.CONFIRMADA);
                    cobranca.getSacado().setJustificativaStatus("Confirmação automática (via arquivo de retorno)");
                    cobranca.getSacado().setDataJustificativaStatus(LocalDateTime.now());
                    cobranca.setSituacao(SituacaoBoleto.PAGO);
                    cobranca.setDataPagamento(DateConversor.convertDateToLocalDate(t.getSegmentoU().getDataOcorrencia()));
                    cobranca.setValorPago(t.getSegmentoU().getValorPago());
                    cobranca.setArquivo(ar);
                    this.ard.getIncricoesConfirmadas().add(cobranca.getSacado());
                    totalTitulosPagos++;
                }
            }
            valorTotal.add(t.getValor());
        }
        this.ard.setTotalTitulosPagos(totalTitulosPagos);
        this.ard.setValorTotalEmCobrancas(valorTotal);
    }

    private void carregarMensagens(ArquivoRetornoCaixa arquivoRetorno) {
        CabecalhoLote cabecalho = arquivoRetorno.getCabecalhoLote();
        SumarioArquivo sumarioArquivo = arquivoRetorno.getSumarioArquivo();
        List<String> mensagens = new ArrayList<>();
        this.ard.setDataCredito(DateConversor.convertDateToLocalDate(cabecalho.getDataDoCredito()));
        this.ard.setQuantidadeDeTitulosEmCobranca(sumarioArquivo.getQuantidadeDeLotes());
    }

    private ArquivoRetornoCaixa criarArquivoRetorno(String fileName, InputStream inputstream) throws ArquivoRetornoException {
        ArquivoRetornoCaixa arquivoRetorno;
        try {
            File arquivo = File.createTempFile(fileName, "");
            FileUtils.copyInputStreamToFile(inputstream, arquivo);
            arquivoRetorno = new ArquivoRetornoCaixa(arquivo);
        } catch (IOException e) {
            throw new ArquivoRetornoException("Erro carregando arquivo de retorno");
        }
        return arquivoRetorno;
    }
}
