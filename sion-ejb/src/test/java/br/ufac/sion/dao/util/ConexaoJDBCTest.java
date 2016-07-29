/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao.util;

import java.sql.Connection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Rennan
 */
public class ConexaoJDBCTest {
    private ConexaoJDBC conJDBC;
    
    @Before
    public void init(){
        conJDBC = new ConexaoJDBC();
    }
    
    @Test
    public void testa_se_abre_a_conexao_jdbc_dos_relatorios(){
        Connection conn = conJDBC.abreConexao();
        assertNotNull(conn);
    } 
}
