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

        int codigoDepartamento = obtenerCodigoDepartamento(ventasCiudades.getDepartamento());
        int codigoSede = obtenerCodigoSede(ventasCiudades.getSede());
        int anioInicio = 0;
        int anioFin = 0;
        
        if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(ventasCiudades.getAnioInicio()+"0101");
            anioFin = Integer.parseInt(ventasCiudades.getAnioFin()+"1201");
        } else if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && ventasCiudades.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(ventasCiudades.getAnioInicio()+"0101");
        } else if (ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")){
            anioFin = Integer.parseInt(ventasCiudades.getAnioFin()+"1201");
        }
        
        //TODOS
        if (!ventasCiudades.getSede().equals("Escoger una Opción...") && ventasCiudades.getDepartamento().equals("Escoger una Opción...")) {

            if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                System.out.println("---------------------- ENTRA A: SEDE + INICIO + FIN ----------------------------");
                where = " WHERE dv.sede_venta = '" + codigoSede 
                        + "' AND dv.fecha_venta >= " + anioInicio + " AND dv.fecha_venta <= " + anioFin;
            } else if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                System.out.println("---------------------- ENTRA A: SEDE + INICIO ----------------------------");
                where = " WHERE dv.sede_venta = '" + codigoSede 
                        + "' AND dv.fecha_venta >= " + anioInicio + " AND dv.fecha_venta >= " + ((anioInicio+"").substring(0, 4)+"1201");
            }  else if (ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                System.out.println("---------------------- ENTRA A: SEDE + FIN ----------------------------");
                return "Error Fecha";
            } else {
                System.out.println("---------------------- ENTRA A: SEDE ----------------------------");
                where = " WHERE dv.sede_venta = '" + codigoSede + "'";
            }

        } //TODOS
        else if (!ventasCiudades.getDepartamento().equals("Escoger una Opción...") && ventasCiudades.getSede().equals("Escoger una Opción...")) {

            if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                System.out.println("---------------------- ENTRA A: DEPARTAMENTO + INICIO + FIN ----------------------------");
                where = "WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND dv.fecha_venta >= " + anioInicio + " AND dv.fecha_venta <= " + anioFin;
            } else if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                System.out.println("---------------------- ENTRA A: DEPARTAMENTO + INICIO ----------------------------");
                where = " WHERE dv.sede_venta = '" + codigoSede 
                        + "' AND dv.fecha_venta >= " + anioInicio + " AND dv.fecha_venta >= " + ((anioInicio+"").substring(0, 4)+"1201");
            }  else if (ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                System.out.println("---------------------- ENTRA A: DEPARTAMENTO + FIN ----------------------------");
                return "Error Fecha";
            } else {
                System.out.println("---------------------- ENTRA A: DEPARTAMENTO ----------------------------");
                where = "WHERE cd.departamento_ciudad = '" + codigoDepartamento + "'";
            }

        }
        else if (!ventasCiudades.getDepartamento().equals("Escoger una Opción...") && !ventasCiudades.getSede().equals("Escoger una Opción...")) {
            
            if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                System.out.println("---------------------- ENTRA A: DEPARTAMENTO + SEDE + INICIO + FIN ----------------------------");
                where = "WHERE dv.sede_venta = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND dv.fecha_venta >= " + anioInicio + " AND dv.fecha_venta <= " + anioFin;
            } else if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                System.out.println("---------------------- ENTRA A: DEPARTAMENTO + SEDE + INICIO ----------------------------");
                where = "WHERE dv.sede_venta = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND dv.fecha_venta >= " + anioInicio + " AND dv.fecha_venta >= " + ((anioInicio+"").substring(0, 4)+"1201");
            }  else if (ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                System.out.println("---------------------- ENTRA A: DEPARTAMENTO + SEDE + FIN ----------------------------");
                return "Error Fecha";
            } else {
                System.out.println("---------------------- ENTRA A: DEPARTAMENTO + SEDE ----------------------------");
                where = "WHERE dv.sede_venta = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento + "'";
            }
            
        } else {
            
            return "Error";
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
    
    public int obtenerCodigoDepartamento(String nombreDepartamento){
        
        int departamentoSeleccionado = 0;
        
        if (nombreDepartamento.equals("VALLE DEL CAUCA")) {
            departamentoSeleccionado = 1;
        } else if (nombreDepartamento.equals("CAUCA")) {
            departamentoSeleccionado = 2;
        } else if (nombreDepartamento.equals("RISARALDA")) {
            departamentoSeleccionado = 3;
        } else if (nombreDepartamento.equals("QUINDIO")) {
            departamentoSeleccionado = 4;
        } else if (nombreDepartamento.equals("NARIÑO")) {
            departamentoSeleccionado = 5;
        } else if (nombreDepartamento.equals("CALDAS")) {
            departamentoSeleccionado = 6;
        } else if (nombreDepartamento.equals("CHOCO")) {
            departamentoSeleccionado = 7;
        } else if (nombreDepartamento.equals("BOGOTA")) {
            departamentoSeleccionado = 8;
        } else if (nombreDepartamento.equals("ANTIOQUIA")) {
            departamentoSeleccionado = 9;
        } else if (nombreDepartamento.equals("TOLIMA")) {
            departamentoSeleccionado = 10;
        } else if (nombreDepartamento.equals("ATLANTICO")) {
            departamentoSeleccionado = 11;
        } else if (nombreDepartamento.equals("SANTANDER")) {
            departamentoSeleccionado = 12;
        } else if (nombreDepartamento.equals("HUILA")) {
            departamentoSeleccionado = 13;
        } else if (nombreDepartamento.equals("META")) {
            departamentoSeleccionado = 14;
        } else if (nombreDepartamento.equals("BOYACA")) {
            departamentoSeleccionado = 15;
        } else if (nombreDepartamento.equals("BOLIVAR")) {
            departamentoSeleccionado = 16;
        } else if (nombreDepartamento.equals("CAQUETA")) {
            departamentoSeleccionado = 18;
        } else if (nombreDepartamento.equals("CESAR")) {
            departamentoSeleccionado = 20;
        } else if (nombreDepartamento.equals("CORDOBA")) {
            departamentoSeleccionado = 23;
        } else if (nombreDepartamento.equals("CUNDINAMARCA")) {
            departamentoSeleccionado = 25;
        } else if (nombreDepartamento.equals("LA GUAJIRA")) {
            departamentoSeleccionado = 44;
        } else if (nombreDepartamento.equals("MAGDALENA")) {
            departamentoSeleccionado = 47;
        } else if (nombreDepartamento.equals("N. DE SANTANDER")) {
            departamentoSeleccionado = 54;
        } else if (nombreDepartamento.equals("SUCRE")) {
            departamentoSeleccionado = 70;
        } else if (nombreDepartamento.equals("ARAUCA")) {
            departamentoSeleccionado = 81;
        } else if (nombreDepartamento.equals("CASANARE")) {
            departamentoSeleccionado = 85;
        } else if (nombreDepartamento.equals("PUTUMAYO")) {
            departamentoSeleccionado = 86;
        } else if (nombreDepartamento.equals("SAN ANDRES")) {
            departamentoSeleccionado = 88;
        } else if (nombreDepartamento.equals("AMAZONAS")) {
            departamentoSeleccionado = 91;
        } else if (nombreDepartamento.equals("GUAINIA")) {
            departamentoSeleccionado = 94;
        } else if (nombreDepartamento.equals("GUAVIARE")) {
            departamentoSeleccionado = 95;
        } else if (nombreDepartamento.equals("VAUPES")) {
            departamentoSeleccionado = 97;
        } else if (nombreDepartamento.equals("VICHADA")) {
            departamentoSeleccionado = 99;
        }  
        
        return departamentoSeleccionado;
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
