package Diagrama;

import Diagrama.ConexionBD;
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
    
    int anioInicio;
    int anioFin;
    int mesInicio;
    int mesFin;

    public DaoVentasSedes() {
        BaseDeDatos = new ConexionBD();
        
        anioInicio = 0;
        anioFin = 0;
        mesInicio = 0;
        mesFin = 0;
    }

    public String prepararRestriccionesClausulaWhereVentas(VentasSedes ventasSedes) {

        String where = "";
        
        /*
        * En esta parte de determina el formato que debe tener el año de inicio y dde fin de la consulta
        * de acueerdo a la selección que el usuario haya hecho
        */
        if (!ventasSedes.getAnioInicio().equals("Escoger una Opción...") && !ventasSedes.getAnioFin().equals("Escoger una Opción...")){
            
            anioInicio = Integer.parseInt(ventasSedes.getAnioInicio());
            anioFin = Integer.parseInt(ventasSedes.getAnioFin());
            
            if (!ventasSedes.getMesInicio().equals("Escoger una Opción...") && !ventasSedes.getMesFin().equals("Escoger una Opción...")) {
                
                mesInicio = obtenerCodigoMes(ventasSedes.getMesInicio());
                mesFin = obtenerCodigoMes(ventasSedes.getMesFin());
                
                if (mesInicio < 10 && mesFin < 10) {
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01'";
                } else if (mesInicio >= 10 && mesFin < 10){
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01'";
                } else if (mesInicio < 10 && mesFin >= 10){
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01'";
                } else if (mesInicio >= 10 && mesFin >= 10){
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01'";
                }
                
            } else if(ventasSedes.getMesInicio().equals("Escoger una Opción...") && ventasSedes.getMesFin().equals("Escoger una Opción...")){
                where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioInicio + "0101' AND '" + anioFin + "1201'";
            } else {
                return "Error Fecha Mes";
            }
            
        } else if (!ventasSedes.getAnioInicio().equals("Escoger una Opción...") && ventasSedes.getAnioFin().equals("Escoger una Opción...")){
            
            anioInicio = Integer.parseInt(ventasSedes.getAnioInicio());
            
            if (!ventasSedes.getMesInicio().equals("Escoger una Opción...") && !ventasSedes.getMesFin().equals("Escoger una Opción...")) {
                
                mesInicio = obtenerCodigoMes(ventasSedes.getMesInicio());
                mesFin = obtenerCodigoMes(ventasSedes.getMesFin());
                
                if (mesInicio < 10 && mesFin < 10) {
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01'";
                } else if (mesInicio >= 10 && mesFin < 10){
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01'";
                } else if (mesInicio < 10 && mesFin >= 10){
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01'";
                } else if (mesInicio >= 10 && mesFin >= 10){
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01'";
                }
                
            } else if(ventasSedes.getMesInicio().equals("Escoger una Opción...") && ventasSedes.getMesFin().equals("Escoger una Opción...")){
                where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioInicio + "0101' AND '" + anioInicio + "1201'";
            } else {
                
                return "Error Fecha Mes";
                
            }
            
        } else if (ventasSedes.getAnioInicio().equals("Escoger una Opción...") && !ventasSedes.getAnioFin().equals("Escoger una Opción...")){
            
            anioFin = Integer.parseInt(ventasSedes.getAnioFin());
            
            if (!ventasSedes.getMesInicio().equals("Escoger una Opción...") && !ventasSedes.getMesFin().equals("Escoger una Opción...")) {
                
                mesInicio = obtenerCodigoMes(ventasSedes.getMesInicio());
                mesFin = obtenerCodigoMes(ventasSedes.getMesFin());
                
                if (mesInicio < 10 && mesFin < 10) {
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioFin + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01'";
                } else if (mesInicio >= 10 && mesFin < 10){
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioFin + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01'";
                } else if (mesInicio < 10 && mesFin >= 10){
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioFin + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01'";
                } else if (mesInicio >= 10 && mesFin >= 10){
                    where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioFin + mesInicio + "01' AND '" + anioFin + mesFin + "01'";
                }
                
            } else if(ventasSedes.getMesInicio().equals("Escoger una Opción...") && ventasSedes.getMesFin().equals("Escoger una Opción...")){
                where = " WHERE dv.fecha_afiliacion BETWEEN '" + anioFin + "0101' AND '" + anioFin + "1201'";
            } else {
                
                return "Error Fecha Mes";
                
            }
            
        } else if (ventasSedes.getAnioInicio().equals("Escoger una Opción...") && ventasSedes.getAnioFin().equals("Escoger una Opción...")){
            return "Error Fecha Año";
        }
        
        return where;
    }

    public ArrayList<String[]> conteoVentasSede(String where) {

        ArrayList<String[]> conteoVentas = new ArrayList<String[]>();
        String sql_select;
        
        sql_select = "SELECT sd.nombre_sede, SUM(dv.cantidad_afiliaciones) AS total_ventas FROM datamart_afiliacion dv INNER JOIN dim_sede sd "
                    + "ON (dv.sede_afiliacion = sd.id_sede) " + where + " GROUP BY sd.nombre_sede LIMIT 5;";
        
        System.out.println("Consulta: " + sql_select);

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while (tabla.next()) {
                
                String[] registro = new String[2];
                registro[0] = tabla.getString("nombre_sede");
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
    
    public int obtenerCodigoMes(String nombreMes){
        
        int mesSeleccionado = 0;
        
        if (nombreMes.equals("Enero")) {
            mesSeleccionado = 1;
        } else if (nombreMes.equals("Febrero")) {
            mesSeleccionado = 2;
        } else if (nombreMes.equals("Marzo")) {
            mesSeleccionado = 3;
        } else if (nombreMes.equals("Abril")) {
            mesSeleccionado = 4;
        } else if (nombreMes.equals("Mayo")) {
            mesSeleccionado = 5;
        } else if (nombreMes.equals("Junio")) {
            mesSeleccionado = 6;
        } else if (nombreMes.equals("Julio")) {
            mesSeleccionado = 7;
        } else if (nombreMes.equals("Agosto")) {
            mesSeleccionado = 8;
        } else if (nombreMes.equals("Septiembre")) {
            mesSeleccionado = 9;
        } else if (nombreMes.equals("Octubre")) {
            mesSeleccionado = 10;
        } else if (nombreMes.equals("Noviembre")) {
            mesSeleccionado = 11;
        } else if (nombreMes.equals("Diciembre")) {
            mesSeleccionado = 12;
        }
        
        return mesSeleccionado;
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