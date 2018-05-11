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
    public String criterioConsultaVentas;
    
    int anioInicio;
    int anioFin;
    int mesInicio;
    int mesFin;

    public DaoVentasCiudades() {
        BaseDeDatos = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWhereVentas(VentasCiudades ventasCiudades, String criterioConsulta) {

        criterioConsultaVentas = criterioConsulta;
        String where = "";

        int codigoDepartamento = obtenerCodigoDepartamento(ventasCiudades.getDepartamento());
        int codigoSede = obtenerCodigoSede(ventasCiudades.getSede());
        
        anioInicio = 0;
        anioFin = 0;
        mesInicio = 0;
        mesFin = 0;
        
        /*
        * En esta parte de determina el formato que debe tener el año de inicio y dde fin de la consulta
        * de acueerdo a la selección que el usuario haya hecho
        */
        if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(ventasCiudades.getAnioInicio());
            anioFin = Integer.parseInt(ventasCiudades.getAnioFin());            
        } else if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && ventasCiudades.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(ventasCiudades.getAnioInicio());
        } else if (ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")){
            anioFin = Integer.parseInt(ventasCiudades.getAnioFin());
        }
        
        //TODOS
        if (!ventasCiudades.getSede().equals("Escoger una Opción...") && ventasCiudades.getDepartamento().equals("Escoger una Opción...")) {

            if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasCiudades.getMesInicio().equals("Escoger una Opción...") && !ventasCiudades.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasCiudades.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasCiudades.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dv.sede_afiliacion = '" + codigoSede 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dv.sede_afiliacion = '" + codigoSede 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dv.sede_afiliacion = '" + codigoSede 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dv.sede_afiliacion = '" + codigoSede 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasCiudades.getMesInicio().equals("Escoger una Opción...") && ventasCiudades.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dv.sede_afiliacion = '" + codigoSede 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + ANIO INICIO + ANIO FIN 
            else if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasCiudades.getMesInicio().equals("Escoger una Opción...") && !ventasCiudades.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasCiudades.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasCiudades.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dv.sede_afiliacion = '" + codigoSede 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dv.sede_afiliacion = '" + codigoSede 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dv.sede_afiliacion = '" + codigoSede 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dv.sede_afiliacion = '" + codigoSede 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasCiudades.getMesInicio().equals("Escoger una Opción...") && ventasCiudades.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dv.sede_afiliacion = '" + codigoSede 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dv.sede_afiliacion = '" + codigoSede + "'";
            }

        } //FIN IF + CODIGO SEDE - CODIGO DEPARTAMENTO
        else if (!ventasCiudades.getDepartamento().equals("Escoger una Opción...") && ventasCiudades.getSede().equals("Escoger una Opción...")) {

            if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasCiudades.getMesInicio().equals("Escoger una Opción...") && !ventasCiudades.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasCiudades.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasCiudades.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = "WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = "WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = "WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = "WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasCiudades.getMesInicio().equals("Escoger una Opción...") && ventasCiudades.getMesFin().equals("Escoger una Opción...")){
                    where = "WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasCiudades.getMesInicio().equals("Escoger una Opción...") && !ventasCiudades.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasCiudades.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasCiudades.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasCiudades.getMesInicio().equals("Escoger una Opción...") && ventasCiudades.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = "WHERE cd.departamento_ciudad = '" + codigoDepartamento + "'";
            }

        }
        else if (!ventasCiudades.getDepartamento().equals("Escoger una Opción...") && !ventasCiudades.getSede().equals("Escoger una Opción...")) {
            
            if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasCiudades.getMesInicio().equals("Escoger una Opción...") && !ventasCiudades.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasCiudades.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasCiudades.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasCiudades.getMesInicio().equals("Escoger una Opción...") && ventasCiudades.getMesFin().equals("Escoger una Opción...")){
                    where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasCiudades.getMesInicio().equals("Escoger una Opción...") && !ventasCiudades.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasCiudades.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasCiudades.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasCiudades.getMesInicio().equals("Escoger una Opción...") && ventasCiudades.getMesFin().equals("Escoger una Opción...")){
                    where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (ventasCiudades.getAnioInicio().equals("Escoger una Opción...") && !ventasCiudades.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = "WHERE dv.sede_afiliacion = '" + codigoSede + "' AND cd.departamento_ciudad = '" + codigoDepartamento + "'";
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
            sql_select = "SELECT cd.nombre_ciudad, SUM(dv.cantidad_afiliaciones) AS cantidad_afiliaciones FROM datamart_afiliacion dv INNER JOIN dim_ciudad cd "
                + " ON (dv.ciudad_afiliacion = CAST ( cd.cod_ciudad AS BIGINT )) "+ where +" GROUP BY cd.nombre_ciudad"
                + " ORDER BY SUM(dv.cantidad_afiliaciones) DESC LIMIT 5;";   
        } else {
            sql_select = "SELECT cd.nombre_ciudad, SUM(dv.cantidad_afiliaciones) AS cantidad_afiliaciones FROM datamart_afiliacion dv INNER JOIN dim_ciudad cd "
                + " ON (dv.ciudad_afiliacion = CAST ( cd.cod_ciudad AS BIGINT )) "+ where +" GROUP BY cd.nombre_ciudad"
                + " ORDER BY SUM(dv.cantidad_afiliaciones) ASC LIMIT 5;";
        }
        
        System.out.println("Consulta: " + sql_select);

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while (tabla.next()) {
                
                String[] registro = new String[2];
                registro[0] = tabla.getString("nombre_ciudad");
                registro[1] = tabla.getString("cantidad_afiliaciones");
                
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
    
    public int obtenerCodigoDepartamento(String nombreDepartamento){
        
        int departamentoSeleccionado = 0;
        
        if (nombreDepartamento.equals("VALLE")) {
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
