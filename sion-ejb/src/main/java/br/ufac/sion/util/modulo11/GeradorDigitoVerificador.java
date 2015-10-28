/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util.modulo11;

/**
 *
 * @author rennan.lima
 */
public interface GeradorDigitoVerificador {

    public String gerarDigito(Integer carteira, String nossoNumero);

    public String completarComZeros(String numero);
}
