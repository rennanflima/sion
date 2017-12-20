/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.util;

import java.util.Random;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author rennan.lima
 */
public class GeraSenha {

    public String geraSenha() {
        char[] caracteres = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
            'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z'};
        char[] pass = new char[8];

        int caracteresLenght = caracteres.length;
        Random rdm = new Random();

        for (int i = 0; i < 8; i++) {
            pass[i] = caracteres[rdm.nextInt(caracteresLenght)];
        }
        return new String(pass);
    }

    public String ecripta(String pas) {
        return new BCryptPasswordEncoder().encode(pas);
    }

    public String geraSenhaEncriptada() {
        return ecripta(geraSenha());
    }
}
