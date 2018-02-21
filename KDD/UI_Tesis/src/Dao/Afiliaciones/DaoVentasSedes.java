/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao.Afiliaciones;

import ConectorBD.ConexionBD;
import Logico.Afiliaciones.VentasCiudades;
import Logico.Afiliaciones.VentasSedes;
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
public class DaoVentasSedes {
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;
    public String criterioConsultaVentas;

    public DaoVentasSedes() {
        BaseDeDatos = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWhereVentas(VentasSedes ventasSedes, String criterioConsulta) {

        criterioConsultaVentas = criterioConsulta;
        String where = "";

        int anioInicio = 0;
        int anioFin = 0;
        int mesInicio = 0;
        int mesFin = 0;
        
        /*
        * En esta parte de determina el formato que debe tener el año de inicio y dde fin de la consulta
        * de acueerdo a la selección que el usuario haya hecho
        */
        if (!ventasSedes.getAnioInicio().equals("Escoger una Opción...") && !ventasSedes.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(ventasSedes.getAnioInicio()+"0101");
            anioFin = Integer.parseInt(ventasSedes.getAnioFin()+"1201");
        } else if (!ventasSedes.getAnioInicio().equals("Escoger una Opción...") && ventasSedes.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(ventasSedes.getAnioInicio()+"0101");
        } else if (ventasSedes.getAnioInicio().equals("Escoger una Opción...") && !ventasSedes.getAnioFin().equals("Escoger una Opción...")){
            anioFin = Integer.parseInt(ventasSedes.getAnioFin()+"1201");
        }
        
        //TODOS
        if (!ventasSedes.getSede().equals("Escoger una Opción...") && ventasSedes.getDepartamento().equals("Escoger una Opción...")) {

            if (!ventasSedes.getAnioInicio().equals("Escoger una Opción...") && !ventasSedes.getAnioFin().equals("Escoger una Opción...")) {
                where = " WHERE dv.sede_venta = '" + codigoSede 
                        + "' AND (dv.fecha_venta BETWEEN " + anioInicio + " AND " + anioFin + ") ";
            } else if (!ventasSedes.getAnioInicio().equals("Escoger una Opción...") && ventasSedes.getAnioFin().equals("Escoger una Opción...")) {
                where = " WHERE dv.sede_venta = '" + codigoSede 
                        + "' AND (dv.fecha_venta BETWEEN " + anioInicio + " AND " + ((anioInicio+"").substring(0, 4)+"1201) ");
            }  else if (ventasSedes.getAnioInicio().equals("Escoger una Opción...") && !ventasSedes.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dv.sede_venta = '" + codigoSede + "'";
            }

        } else {
            
            return "Error";
        }
        
        return where;
    }

    public ArrayList<String[]> conteoVentasSede(String where) {

        ArrayList<String[]> conteoVentas = new ArrayList<String[]>();
        String sql_select;
        
        if (criterioConsultaVentas.equals("Mayor Número de Ventas")) {
            sql_select = "SELECT cd.nombre_ciudad, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv INNER JOIN ciudad cd "
                + " ON (dv.ciudad_venta = CAST ( cd.cod_ciudad AS BIGINT )) "+ where +" GROUP BY cd.nombre_ciudad"
                + " ORDER BY SUM(dv.total_ventas) DESC LIMIT 5;";   
        } else {
            sql_select = "SELECT cd.nombre_ciudad, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv INNER JOIN ciudad cd "
                + " ON (dv.ciudad_venta = CAST ( cd.cod_ciudad AS BIGINT )) "+ where +" GROUP BY cd.nombre_ciudad"
                + " ORDER BY SUM(dv.total_ventas) ASC LIMIT 5;";
        }
        
        System.out.println("Consulta: " + sql_select);

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
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
    
    public int obtenerCodigoSede(String nombreSede){
        
        int sedeSeleccionada = 0;
        
        if (nombreSede.equals("SEDE PALMIRA")) {
            sedeSeleccionada = 1;
        } else if (nombreSede.equals("SEDE CALI")) {
            sedeSeleccionada = 2;
        } else if (nombreSede.equals("SEDE TULUA")) {
            sedeSeleccionada = 3;
        } else if (nombreSede.equals("SEDE PEREIRA")) {
            sedeSeleccionada = 4;
        } else if (nombreSede.equals("SEDE ARMENIA")) {
            sedeSeleccionada = 5;
        }
        
        return sedeSeleccionada;
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