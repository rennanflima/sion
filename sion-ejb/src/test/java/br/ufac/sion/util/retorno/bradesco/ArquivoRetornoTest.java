package br.ufac.sion.util.retorno.bradesco;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import org.junit.Test;

public class ArquivoRetornoTest {

    @Test
    public void deve_retornar_valor_total_transacoes() {
        File arquivo = new File("src/test/resources/CB041000.RET");
        ArquivoRetornoBradesco arquivoRetorno = new ArquivoRetornoBradesco(arquivo);

        BigDecimal valorTotal = arquivoRetorno.getSumario().getValorTotalEmCobranca();
        assertEquals(new BigDecimal("10.00"), valorTotal);
    }

    @Test
    public void deve_retornar_nosso_numero_com_digito() {
        File arquivo = new File("src/test/resources/CB041000.RET");
        ArquivoRetornoBradesco arquivoRetorno = new ArquivoRetornoBradesco(arquivo);
        Map<Integer, Collection<TransacaoTitulo>> titulosPorOcorrencia = arquivoRetorno.getTransacoesPorCodigoDeOcorrencia();

        for (TransacaoTitulo t : titulosPorOcorrencia.get(TransacaoTitulo.LIQUIDACAO)) {
            assertEquals("000000000029", t.getNossoNumeroComDigito());
        }
    }

}
