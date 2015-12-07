package br.ufac.sion.util.retorno.caixa;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;

public class ArquivoRetornoCaixaTest {

    @Test
    public void deve_retornar_nosso_numero_com_digito() {
        File arquivo = new File("src/test/resources/cob11030000.ret");
        ArquivoRetornoCaixa arquivoRetorno = new ArquivoRetornoCaixa(arquivo);
        Map<Integer, Collection<SegmentoT>> titulosPorOcorrencia = arquivoRetorno.getTransacoesPorCodigoDeOcorrencia();

        for (SegmentoT t : titulosPorOcorrencia.get(SegmentoU.LIQUIDACAO)) {
            assertEquals("240000000201498174", t.getNossoNumero());
        }
    }

    @Test
    public void deve_retornar_numero_do_retorno() {
        File arquivo = new File("src/test/resources/cob11030000.ret");
        ArquivoRetornoCaixa arquivoRetorno = new ArquivoRetornoCaixa(arquivo);
        assertEquals(new Integer("46"), arquivoRetorno.getCabecalhoLote().getNumeroRetorno());
    }
}
