/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.retorno.caixa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.io.FileUtils;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;
import org.jrimum.utilix.ClassLoaders;

/**
 *
 * @author rennan.lima
 */
public class ArquivoRetornoCaixa {

    private final String nomeArquivoLayout = "LayoutCefSIGCB240.txg.xml";

    private FlatFile<Record> arquivoTexto;

    private CabecalhoArquivo cabecalhoArquivo;
    private CabecalhoLote cabecalhoLote;
    private List<SegmentoT> transacoes;
    private SumarioLote sumarioLote;
    private SumarioArquivo sumarioArquivo;

    public ArquivoRetornoCaixa(File arquivo) {
        carregarLayout();
        carregarLinhas(arquivo);
        carregarInformacoes();
    }

    private void carregarInformacoes() {
        this.cabecalhoArquivo = new CabecalhoArquivo(this.arquivoTexto.getRecord("HeaderArquivo"));
        this.cabecalhoLote = new CabecalhoLote(this.arquivoTexto.getRecord("HeaderLote"));
        this.sumarioLote = new SumarioLote(this.arquivoTexto.getRecord("TraillerLote"));
        this.sumarioArquivo = new SumarioArquivo(this.arquivoTexto.getRecord("TraillerArquivo"));

        Collection<Record> registroDeTransacoes = this.arquivoTexto.getRecords("Arrecadacao-Segmento-T");
        this.transacoes = new ArrayList<>(registroDeTransacoes.size());
        for (Record registro : registroDeTransacoes) {
            transacoes.add(new SegmentoT(registro));
        }
    }

    private void carregarLinhas(File arquivo) {
        List<String> linhas;
        try {
            linhas = FileUtils.readLines(arquivo);
        } catch (IOException e) {
            throw new RuntimeException("Erro lendo linhas do arquivo de retorno", e);
        }
        this.arquivoTexto.read(linhas);
    }

    private void carregarLayout() {
        InputStream in = ClassLoaders.getResourceAsStream(nomeArquivoLayout, this.getClass());
        this.arquivoTexto = Texgit.createFlatFile(in);
    }

    public Map<Integer, Collection<SegmentoT>> getTransacoesPorCodigoDeOcorrencia() {
        Map<Integer, Collection<SegmentoT>> transacoesPorOcorrencias = new TreeMap<Integer, Collection<SegmentoT>>();

        for (SegmentoT transacao : getTransacoes()) {
            if (!transacoesPorOcorrencias.containsKey(transacao.getSegmentoU().getCodigoMovimento())) {
                ArrayList<SegmentoT> transacoes = new ArrayList<SegmentoT>();
                transacoes.add(transacao);
                transacoesPorOcorrencias.put(transacao.getSegmentoU().getCodigoMovimento(), transacoes);
            } else {
                transacoesPorOcorrencias.get(transacao.getSegmentoU().getCodigoMovimento()).add(transacao);
            }
        }

        return transacoesPorOcorrencias;
    }

    public String getNomeArquivoLayout() {
        return nomeArquivoLayout;
    }

    public CabecalhoArquivo getCabecalhoArquivo() {
        return cabecalhoArquivo;
    }

    public CabecalhoLote getCabecalhoLote() {
        return cabecalhoLote;
    }

    public List<SegmentoT> getTransacoes() {
        return transacoes;
    }

    public SumarioLote getSumarioLote() {
        return sumarioLote;
    }

    public SumarioArquivo getSumarioArquivo() {
        return sumarioArquivo;
    }

}
