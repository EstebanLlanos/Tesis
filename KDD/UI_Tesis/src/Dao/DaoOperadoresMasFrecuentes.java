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

public class DaoOperadoresMasFrecuentes {

    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public DaoOperadoresMasFrecuentes() {
        BaseDeDatos = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWhereOperadores(String mes) {

        String where = "";

        if (mes.equalsIgnoreCase("general")) {
            where = "";
        }else if (mes.equalsIgnoreCase("enero")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("febrero")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("marzo")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("abril")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("mayo")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("junio")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("julio")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("agosto")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("septiembre")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("octubre")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("noviembre")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        } else if (mes.equalsIgnoreCase("diciembre")) {
            where = " AND fecha.nombre_mes = '" + mes + "'";
        }

        return where;
    }

    public ArrayList<Integer> listaOperadores(String where) {

        int at = 0;
        int avantel = 0;
        int colmovil = 0;
        int comcel = 0;
        int emcali = 0;
        int epm = 0;
        int etb = 0;
        int etp = 0;
        int metrotel = 0;
        int movistar = 0;
        int orange = 0;
        int telebu = 0;
        int telefonica = 0;
        int telmex = 0;
        int tigo = 0;
        int une = 0;
        int vodafone = 0;

        ArrayList<Integer> conteoOperadores = new ArrayList<Integer>();
        String sql_select;
        sql_select = "SELECT nombre_operador, nombre_mes FROM bodega.llamada, "
                + "bodega.fecha WHERE llamada.fecha = fecha.sk_fecha " + where + ";";
        try {
            conn = BaseDeDatos.conectar();
            System.out.println("Conexión establecida");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);

            while (tabla.next()) {
                if (tabla.getObject(1).toString().equalsIgnoreCase("AT&T")) {
                    at++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Avantel")) {
                    avantel++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Colmovil")) {
                    colmovil++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Comcel")) {
                    comcel++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("EMCALI")) {
                    emcali++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("EPM")) {
                    epm++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("ETB")) {
                    etb++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("ETP")) {
                    etp++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Metrotel")) {
                    metrotel++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Movistar")) {
                    movistar++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Orange")) {
                    orange++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Telebucaramanga")) {
                    telebu++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Telefonica")) {
                    telefonica++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Telmex")) {
                    telmex++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Tigo")) {
                    tigo++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("UNE")) {
                    une++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("Vodafone")) {
                    vodafone++;
                }
            }
            
            conteoOperadores.add(at);
            conteoOperadores.add(avantel);
            conteoOperadores.add(colmovil);
            conteoOperadores.add(comcel);
            conteoOperadores.add(emcali);
            conteoOperadores.add(epm);
            conteoOperadores.add(etb);
            conteoOperadores.add(etp);
            conteoOperadores.add(metrotel);
            conteoOperadores.add(movistar);
            conteoOperadores.add(orange);
            conteoOperadores.add(telebu);
            conteoOperadores.add(telefonica);
            conteoOperadores.add(telmex);
            conteoOperadores.add(tigo);
            conteoOperadores.add(une);
            conteoOperadores.add(vodafone);
            

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return conteoOperadores;
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
