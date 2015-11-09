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
public class GeradorDigitoVerificadorBancoDoBrasil implements GeradorDigitoVerificador, Serializable {

    @Override
    public String completarComZeros(String numero) {
        String novoNumero = "";
        for (int i = 0; i < (10 - numero.length()); i++) {
            novoNumero += "0";
        }

        return novoNumero + numero;
    }

    @Override
    public String gerarDigito(Integer carteira, String nossoNumero) {
        throw new UnsupportedOperationException("Método não suportado."); //To change body of generated methods, choose Tools | Templates.
    }
}
