/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufac.sion.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rennan
 */
public class ConexaoJDBC {

    Connection con = null;

    public Connection abreConexao() {
        String url = "jdbc:mysql://localhost:3306/siondb";//porta do mysql do wamp:3306 e do mamp:8889 
        String user = "user_sion";
        String pass = "siondb";
//        String user = "root";
//        String pass = "root";
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public void fechaConexao() {
        try {
            con.close();
        } catch (Exception e) {
            Logger.getLogger(ConexaoJDBC.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
