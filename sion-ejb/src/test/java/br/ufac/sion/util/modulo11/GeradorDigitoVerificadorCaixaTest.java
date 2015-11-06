/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.modulo11;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rennan.lima
 */
public class GeradorDigitoVerificadorCaixaTest {

    private GeradorDigitoVerificador gerador;

    @Before
    public void init() {
        this.gerador = new GeradorDigitoVerificadorCaixa();
    }

    @Test
    public void deve_completar_com_zeros_a_esquerda() {
        String numero = "19";
        String resultado = gerador.completarComZeros(numero);
        assertEquals("000000000000019", resultado);
    }
    
    
    @Test
    public void deve_gerar_digito_verificador_entrada_19_saida_7() {
        Integer carteira = 14;
        String nossoNumero = "000000000000019";

        String digitoVerificador = gerador.gerarDigito(carteira, nossoNumero);
        assertEquals("7", digitoVerificador);
    }
    
    
    @Test
    public void deve_gerar_digito_verificador_entrada_201510754_saida_0() {
        Integer carteira = 24;
        String nossoNumero = "000000201510754";

        String digitoVerificador = gerador.gerarDigito(carteira, nossoNumero);
        assertEquals("0", digitoVerificador);
    }
    
    @Test
    public void deve_gerar_digito_verificador_entrada_20149862_saida_0() {
        Integer carteira = 24;
        String nossoNumero = "000000020149862";

        String digitoVerificador = gerador.gerarDigito(carteira, nossoNumero);
        assertEquals("0", digitoVerificador);
    }
    
    
    @Test
    public void deve_gerar_digito_verificador_entrada_1234_saida_7() {
        Integer carteira = 24;
        String nossoNumero = "000000000001234";

        String digitoVerificador = gerador.gerarDigito(carteira, nossoNumero);
        assertEquals("7", digitoVerificador);
    }
}
