/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao.Afiliaciones;

import ConectorBD.ConexionBD;
import Logico.Afiliaciones.VentasCiudades;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban
 */
public class DaoVentasCiudades {
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public DaoVentasCiudades() {
        BaseDeDatos = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWhereVentas(VentasCiudades ventasCiudades) {

        String where = "";

        //TODOS
        if (!ventasCiudades.getSede().equals("Escoger una opción") && ventasCiudades.getDepartamento().equals("Escoger una opción")) {

            where = " WHERE dv.sede_venta = " + ventasCiudades.getSede();

        } //TODOS
        else if (!ventasCiudades.getDepartamento().equals("Escoger una opción") && ventasCiudades.getSede().equals("Escoger una opción")) {

            where = "WHERE cd.departamento_ciudad = '" + ventasCiudades.getDepartamento() + "'";

        }
        else if (!ventasCiudades.getDepartamento().equals("Escoger una opción") && !ventasCiudades.getSede().equals("Escoger una opción")) {
            
            where = "WHERE dv.sede_venta = " + ventasCiudades.getSede() + "AND cd.departamento_ciudad = '" + ventasCiudades.getDepartamento() + "'";
            
        }
                
        return where;
    }

    public ArrayList<String[]> conteoVentasSede(String where) {

        ArrayList<String[]> conteoVentas = new ArrayList<String[]>();
        String sql_select;
        
        sql_select = "SELECT cd.nombre_ciudad, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv INNER JOIN ciudad cd "
                + " ON (dv.ciudad_venta = CAST ( cd.cod_ciudad AS BIGINT )) "+ where +" GROUP BY cd.nombre_ciudad"
                + " ORDER BY SUM(dv.total_ventas) DESC LIMIT 5;";
        
        System.out.println("Consulta: " + sql_select);

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);

            System.out.println("Conexión establecida");
            
            while (tabla.next()) {
                
                String[] registro = new String[2];
                registro[0] = tabla.getString("nombre_ciudad");
                registro[1] = tabla.getString("total_ventas");
                
                conteoVentas.add(registro);
                
            }
            
            return conteoVentas;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
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
