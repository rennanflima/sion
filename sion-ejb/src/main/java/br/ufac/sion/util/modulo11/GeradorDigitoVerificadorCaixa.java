/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.modulo11;

import java.io.Serializable;

/**
 *
 * @author rennan.lima
 */
public class GeradorDigitoVerificadorCaixa implements GeradorDigitoVerificador, Serializable {

    @Override
    public String gerarDigito(Integer carteira, String nossoNumero) {
        String digitosParaCalculo = String.valueOf(carteira);
        digitosParaCalculo += nossoNumero;

        int soma = obterSomaParaModulo11(digitosParaCalculo);

        return obterDigitoVerificador(soma);
    }

    private int obterSomaParaModulo11(String digitosParaCalculo) {
        int fator = 2;
        int somatorio = 0;
        for (int i = digitosParaCalculo.length() - 1; i >= 0; i--) {
            Integer digito = Integer.parseInt(String.valueOf(digitosParaCalculo.charAt(i)));
            somatorio += fator * digito;
            fator++;
            if (fator == 10) {
                fator = 2;
            }
        }

        return somatorio;
    }

    private String obterDigitoVerificador(int soma) {
        int modulo = 11;
        int restoDivisao = soma % modulo;

        int preDigito = modulo - restoDivisao;

        String digitoVerificador;
        
        if (preDigito > 9) {
            digitoVerificador = "0";
        } else {
            digitoVerificador = String.valueOf(preDigito);
        }

        return digitoVerificador;
    }

    @Override
    public String completarComZeros(String numero) {
        String novoNumero = "";
        for (int i = 0; i < (15 - numero.length()); i++) {
            novoNumero += "0";
        }

        return novoNumero + numero;
    }

}
