/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import ConectorBD.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DaoFranjasUsoRed {

    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public DaoFranjasUsoRed() {
        BaseDeDatos = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWhereFranjas(String operador) {

        String where = "";

        if (!operador.equalsIgnoreCase("Escoger una Opción")) {
            where += " AND llamada.nombre_operador = '" + operador + "'";
        }

        return where;
    }

    public ArrayList <Integer> listaFranjas(String where) {

        int night = 0;
        int morning = 0;
        int am = 0;
        int pm = 0;
        int evening = 0;

        ArrayList <Integer> conteoLlamadas = new ArrayList<Integer>();
        String sql_select;
        sql_select = "SELECT periodo_del_dia, cliente  FROM tiempo, "
                + "llamada WHERE tiempo.sk_tiempo = llamada.tiempo" + where + ";";
        try {
            conn = BaseDeDatos.conectar();
            System.out.println("Conexión establecida");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);

            while (tabla.next()) {
                if (tabla.getObject(1).toString().equalsIgnoreCase("night")) {
                    night++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("morning")) {
                    morning++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("am")) {
                    am++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("pm")) {
                    pm++;
                } else {
                    evening++;
                }
            }
            
            System.out.println("am: " + am);
            System.out.println("evening: " + evening);
            System.out.println("morning: " + morning);
            System.out.println("night: " + night);
            System.out.println("pm: " + pm);

            conteoLlamadas.add(am);
            conteoLlamadas.add(evening);
            conteoLlamadas.add(morning);
            conteoLlamadas.add(night);
            conteoLlamadas.add(pm);            

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return conteoLlamadas;
    }

    public void desconectar() {
        try {
            rsCandidato.close();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
