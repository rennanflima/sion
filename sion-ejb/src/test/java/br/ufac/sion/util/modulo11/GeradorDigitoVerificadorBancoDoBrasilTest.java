/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.modulo11;

import br.ufac.sion.model.BancosSuportados;
import br.ufac.sion.model.ContaBancaria;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rennan.lima
 */
public class GeradorDigitoVerificadorBancoDoBrasilTest {

    private GeradorDigitoVerificador gerador;

    @Before
    public void init() {
        this.gerador = new GeradorDigitoVerificadorBancoDoBrasil();
    }

    @Test
    public void deve_completar_com_zeros_a_esquerda() {
        String numero = "20152921";
        String resultado = gerador.completarComZeros(numero);
        assertEquals("0020152921", resultado);
    }

    @Test
    public void deve_gerar_nosso_numero_de_17_posicoes() {
        Integer convenio = 1379859;

        String numero = "20152921";
        String resultado = gerador.completarComZeros(numero);
        String nossoNumero = convenio + resultado;

        assertEquals("13798590020152921", nossoNumero);

    }

}
