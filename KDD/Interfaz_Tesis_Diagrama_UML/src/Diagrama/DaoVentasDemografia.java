/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class DaoVentasDemografia {
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;
    public String criterioConsultaVentas;
    
    int anioInicio;
    int anioFin;
    int mesInicio;
    int mesFin;

    public DaoVentasDemografia() {
        BaseDeDatos = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWhereVentas(VentasDemografia ventasDemografia, String criterioConsulta) {

        criterioConsultaVentas = criterioConsulta;
        String where = "";

        String genero = obtenerGenero(ventasDemografia.getGenero());
        String estrato = ventasDemografia.getEstrato();
        
        int edad = obtenerCodigoEdad(ventasDemografia.getEdad());
        int ingreso = obtenerCodigoIngreso(ventasDemografia.getIngresos());
        
        anioInicio = 0;
        anioFin = 0;
        mesInicio = 0;
        mesFin = 0;
        
        /*
        * En esta parte de determina el formato que debe tener el año de inicio y dde fin de la consulta
        * de acueerdo a la selección que el usuario haya hecho
        */
        if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(ventasDemografia.getAnioInicio());
            anioFin = Integer.parseInt(ventasDemografia.getAnioFin());            
        } else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(ventasDemografia.getAnioInicio());
        } else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")){
            anioFin = Integer.parseInt(ventasDemografia.getAnioFin());
        }
        
        //INICIO IF + GENERO - ESTRATO - EDAD - INGRESOS
        if (!ventasDemografia.getGenero().equals("Escoger una Opción...") && 
                ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero 
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.genero_demografia = '" + genero + "'";
            }

        } //FIN IF + GENERO - ESTRATO - EDAD - INGRESO
        //INICIO IF + GENERO - ESTRATO - EDAD - INGRESOS
        else if (!ventasDemografia.getGenero().equals("Escoger una Opción...") && 
                !ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato+ "'";
            }

        } //FIN IF + GENERO + ESTRATO - EDAD - INGRESO
        //INICIO IF + GENERO - ESTRATO + EDAD - INGRESOS
        else if (!ventasDemografia.getGenero().equals("Escoger una Opción...") && 
                !ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                !ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad+ "'";
            }

        } //FIN IF + GENERO + ESTRATO + EDAD - INGRESO
        //INICIO IF + GENERO - ESTRATO + EDAD + INGRESOS
        else if (!ventasDemografia.getGenero().equals("Escoger una Opción...") && 
                !ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                !ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                !ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso+ "'";
            }

        } //FIN IF + GENERO + ESTRATO + EDAD + INGRESO
        //INICIO IF + GENERO - ESTRATO + EDAD - INGRESOS
        else if (!ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                !ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad+ "'";
            }

        } //FIN IF + GENERO - ESTRATO + EDAD - INGRESO
        //INICIO IF + GENERO - ESTRATO - EDAD + INGRESOS
        else if (!ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                !ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.ingresos_demografia = '" + ingreso+ "'";
            }

        } //FIN IF + GENERO - ESTRATO - EDAD + INGRESO
        //INICIO IF - GENERO + ESTRATO + EDAD - INGRESOS
        else if (ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                !ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                !ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad+ "'";
            }

        } //FIN IF - GENERO + ESTRATO + EDAD - INGRESO
        //INICIO IF - GENERO + ESTRATO - EDAD + INGRESOS
        else if (ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                !ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                !ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso+ "'";
            }

        } //FIN IF - GENERO + ESTRATO - EDAD + INGRESO
        //INICIO IF - GENERO + ESTRATO - EDAD + INGRESOS
        else if (ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                !ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                !ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso+ "'";
            }

        } //FIN IF - GENERO - ESTRATO + EDAD + INGRESO
        //INICIO IF + GENERO + ESTRATO - EDAD + INGRESOS
        else if (!ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                !ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                !ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.estrato_demografia = '" + estrato + "' AND dd.ingresos_demografia = '" + ingreso+ "'";
            }

        } //FIN IF + GENERO + ESTRATO - EDAD + INGRESO
        //INICIO IF + GENERO - ESTRATO + EDAD + INGRESOS
        else if (!ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                !ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                !ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.genero_demografia = '" + genero + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso+ "'";
            }

        } //FIN IF + GENERO - ESTRATO + EDAD + INGRESO
        //INICIO IF - GENERO - ESTRATO + EDAD + INGRESOS
        else if (ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                !ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                !ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                !ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.estrato_demografia = '" + estrato + "' AND dd.edad_demografia = '" + edad + "' AND dd.ingresos_demografia = '" + ingreso+ "'";
            }

        } //FIN IF - GENERO + ESTRATO + EDAD + INGRESO
        //INICIO IF - GENERO + ESTRATO - EDAD - INGRESOS
        else if (ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                !ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.estrato_demografia = '" + estrato
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.estrato_demografia = '" + estrato+ "'";
            }

        } //FIN IF - GENERO + ESTRATO - EDAD - INGRESO
        //INICIO IF - GENERO - ESTRATO + EDAD - INGRESOS
        else if (ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                !ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.edad_demografia = '" + edad
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.edad_demografia = '" + edad+ "'";
            }

        } //FIN IF - GENERO - ESTRATO + EDAD - INGRESO
        //INICIO IF - GENERO - ESTRATO - EDAD + INGRESOS
        else if (ventasDemografia.getGenero().equals("Escoger una Opción...") &&
                ventasDemografia.getEstrato().equals("Escoger una Opción...") &&
                ventasDemografia.getEdad().equals("Escoger una Opción...") &&
                !ventasDemografia.getIngresos().equals("Escoger una Opción...")) {

            if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!ventasDemografia.getMesInicio().equals("Escoger una Opción...") && !ventasDemografia.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(ventasDemografia.getMesInicio());
                    mesFin = obtenerCodigoMes(ventasDemografia.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(ventasDemografia.getMesInicio().equals("Escoger una Opción...") && ventasDemografia.getMesFin().equals("Escoger una Opción...")){
                    where = " WHERE dd.ingresos_demografia = '" + ingreso
                        + "' AND (dv.fecha_afiliacion BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (ventasDemografia.getAnioInicio().equals("Escoger una Opción...") && !ventasDemografia.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE dd.ingresos_demografia = '" + ingreso+ "'";
            }

        } //FIN IF - GENERO - ESTRATO - EDAD + INGRESO
        else {
            
            return "Error";
        }
        
        return where;
    }

    public ArrayList<String[]> conteoVentasSede(String where) {

        ArrayList<String[]> conteoVentas = new ArrayList<String[]>();
        String sql_select;
        
        if (criterioConsultaVentas.equals("Mayor Número de Ventas")) {
            sql_select = "SELECT cd.nombre_ciudad, SUM(dv.cantidad_afiliaciones) AS total_ventas FROM datamart_afiliacion dv " 
                       + "INNER JOIN dim_ciudad cd ON (dv.ciudad_afiliacion = CAST ( cd.cod_ciudad AS BIGINT )) " 
                       + "INNER JOIN dim_demografia dd ON (dv.demografia_afiliacion = CAST ( dd.id_demografia AS BIGINT )) "
                       + where + " GROUP BY cd.nombre_ciudad "
                       + " ORDER BY SUM(dv.cantidad_afiliaciones) DESC LIMIT 5;";   
        } else {
            sql_select = "SELECT cd.nombre_ciudad, SUM(dv.cantidad_afiliaciones) AS total_ventas FROM datamart_afiliacion dv " 
                       + "INNER JOIN dim_ciudad cd ON (dv.ciudad_afiliacion = CAST ( cd.cod_ciudad AS BIGINT )) " 
                       + "INNER JOIN dim_demografia dd ON (dv.demografia_afiliacion = CAST ( dd.id_demografia AS BIGINT )) "
                       + where + " GROUP BY cd.nombre_ciudad "
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
    
    public int obtenerCodigoEdad(String edad){
        
        int codigoEdad = 0;
        
        switch (edad) {
            case "Menor a 18 años":
                codigoEdad = 1;
                break;
            case "De 18 a 25 años":
                codigoEdad = 2;
                break;
            case "De 26 a 40 años":
                codigoEdad = 3;
                break;
            case "De 41 a 50 años":
                codigoEdad = 4;
                break;
            case "De 51 a 60 años":
                codigoEdad = 5;
                break;
            case "Mayor a 60 años":
                codigoEdad = 6;
                break;
        }
        
        return codigoEdad;
    }
    
    public int obtenerCodigoIngreso(String ingreso){
        
        int codigoIngresos = 0;
        
        switch (ingreso) {
            case "Menor a 1.000.000 de pesos":
                codigoIngresos = 1;
                break;
            case "De 1.000.001 a 2.000.000 de pesos":
                codigoIngresos = 2;
                break;
            case "De 2.000.001 a 4.000.000 de pesos":
                codigoIngresos = 3;
                break;
            case "De 4.000.001 a 5.000.000 de pesos":
                codigoIngresos = 4;
                break;
            case "Mayor a 5.000.000 de pesos":
                codigoIngresos = 5;
                break;
        }
        
        return codigoIngresos;
    }
    
    public String obtenerGenero(String genero){
        
        String codigoGenero = "";
        
        switch (genero) {
            case "Femenino":
                codigoGenero = "F";
                break;
            case "Masculino":
                codigoGenero = "M";
                break;
        }
        
        return codigoGenero;
    }
    
    public int obtenerCodigoMes(String nombreMes){
        
        int mesSeleccionado = 0;
        
        switch (nombreMes) {
            case "Enero":
                mesSeleccionado = 1;
                break;
            case "Febrero":
                mesSeleccionado = 2;
                break;
            case "Marzo":
                mesSeleccionado = 3;
                break;
            case "Abril":
                mesSeleccionado = 4;
                break;
            case "Mayo":
                mesSeleccionado = 5;
                break;
            case "Junio":
                mesSeleccionado = 6;
                break;
            case "Julio":
                mesSeleccionado = 7;
                break;
            case "Agosto":
                mesSeleccionado = 8;
                break;
            case "Septiembre":
                mesSeleccionado = 9;
                break;
            case "Octubre":
                mesSeleccionado = 10;
                break;
            case "Noviembre":
                mesSeleccionado = 11;
                break;
            case "Diciembre":
                mesSeleccionado = 12;
                break;
        }
        
        return mesSeleccionado;
    }
    
    public int obtenerCodigoDepartamento(String nombreDepartamento){
        
        int departamentoSeleccionado = 0;
        
        switch (nombreDepartamento) {
            case "VALLE DEL CAUCA":
                departamentoSeleccionado = 1;
                break;
            case "CAUCA":
                departamentoSeleccionado = 2;
                break;
            case "RISARALDA":
                departamentoSeleccionado = 3;
                break;
            case "QUINDIO":
                departamentoSeleccionado = 4;
                break;
            case "NARIÑO":
                departamentoSeleccionado = 5;
                break;
            case "CALDAS":
                departamentoSeleccionado = 6;
                break;
            case "CHOCO":
                departamentoSeleccionado = 7;
                break;
            case "BOGOTA":
                departamentoSeleccionado = 8;
                break;
            case "ANTIOQUIA":
                departamentoSeleccionado = 9;
                break;
            case "TOLIMA":
                departamentoSeleccionado = 10;
                break;
            case "ATLANTICO":
                departamentoSeleccionado = 11;
                break;
            case "SANTANDER":
                departamentoSeleccionado = 12;
                break;
            case "HUILA":
                departamentoSeleccionado = 13;
                break;
            case "META":
                departamentoSeleccionado = 14;
                break;
            case "BOYACA":
                departamentoSeleccionado = 15;
                break;
            case "BOLIVAR":
                departamentoSeleccionado = 16;
                break;
            case "CAQUETA":
                departamentoSeleccionado = 18;
                break;
            case "CESAR":
                departamentoSeleccionado = 20;
                break;
            case "CORDOBA":
                departamentoSeleccionado = 23;
                break;
            case "CUNDINAMARCA":
                departamentoSeleccionado = 25;
                break;
            case "LA GUAJIRA":
                departamentoSeleccionado = 44;
                break;
            case "MAGDALENA":
                departamentoSeleccionado = 47;
                break;  
            case "N. DE SANTANDER":
                departamentoSeleccionado = 54;
                break;
            case "SUCRE":
                departamentoSeleccionado = 70;
                break;
            case "ARAUCA":
                departamentoSeleccionado = 81;
                break;
            case "CASANARE":
                departamentoSeleccionado = 85;
                break;
            case "PUTUMAYO":
                departamentoSeleccionado = 86;
                break;
            case "SAN ANDRES":
                departamentoSeleccionado = 88;
                break;
            case "AMAZONAS":
                departamentoSeleccionado = 91;
                break;
            case "GUAINIA":
                departamentoSeleccionado = 94;
                break;
            case "GUAVIARE":
                departamentoSeleccionado = 95;
                break;
            case "VAUPES":
                departamentoSeleccionado = 97;
                break;
            case "VICHADA":
                departamentoSeleccionado = 99;
                break;
        }
        
        return departamentoSeleccionado;
    }
    
    public int obtenerCodigoSede(String nombreSede){
        
        int sedeSeleccionada = 0;
        
        switch (nombreSede) {
            case "SEDE PALMIRA":
                sedeSeleccionada = 1;
                break;
            case "SEDE CALI":
                sedeSeleccionada = 2;
                break;
            case "SEDE TULUA":
                sedeSeleccionada = 3;
                break;
            case "SEDE PEREIRA":
                sedeSeleccionada = 4;
                break;
            case "SEDE ARMENIA":
                sedeSeleccionada = 5;
                break;
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
