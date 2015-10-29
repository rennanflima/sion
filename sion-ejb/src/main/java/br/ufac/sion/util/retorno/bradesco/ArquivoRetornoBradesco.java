package br.ufac.sion.util.retorno.bradesco;

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

public class ArquivoRetornoBradesco {

	private final String nomeArquivoLayout = "BradescoCNAB400Retorno.txg.xml";
	
	private Cabecalho cabecalho;
	private List<TransacaoTitulo> transacoes;
	private Sumario sumario;
	
	private FlatFile<Record> arquivoTexto;
	
	public ArquivoRetornoBradesco(File arquivo) {
		carregarLayout();
		carregarLinhas(arquivo);
		carregarInformacoes();
	}

	private void carregarInformacoes() {
		this.cabecalho = new Cabecalho(this.arquivoTexto.getRecord("Header"));
		this.sumario = new Sumario(this.arquivoTexto.getRecord("Trailler"));
		
		Collection<Record> registroDeTransacoes = this.arquivoTexto.getRecords("TransacaoTitulo");
		this.transacoes = new ArrayList<>(registroDeTransacoes.size());
		for (Record registro : registroDeTransacoes) {
			transacoes.add(new TransacaoTitulo(registro));
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
	
	public Cabecalho getCabecalho() {
		return cabecalho;
	}

	public List<TransacaoTitulo> getTransacoes() {
		return transacoes;
	}

	public Sumario getSumario() {
		return sumario;
	}

	public Map<Integer, Collection<TransacaoTitulo>> getTransacoesPorCodigoDeOcorrencia() {
		Map<Integer, Collection<TransacaoTitulo>> transacoesPorOcorrencias = new TreeMap<Integer, Collection<TransacaoTitulo>>();
		
		for (TransacaoTitulo transacao : getTransacoes()) {
			if (!transacoesPorOcorrencias.containsKey(transacao.getCodigoDeOcorrencia())) {
				ArrayList<TransacaoTitulo> transacoes = new ArrayList<TransacaoTitulo>();
				transacoes.add(transacao);
				transacoesPorOcorrencias.put(transacao.getCodigoDeOcorrencia(), transacoes);
			} else {
				transacoesPorOcorrencias.get(transacao.getCodigoDeOcorrencia()).add(transacao);
			}
		}
		
		return transacoesPorOcorrencias;
	}
	
}
