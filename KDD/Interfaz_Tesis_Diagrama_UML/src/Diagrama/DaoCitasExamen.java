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

/*
 *
 * @author Esteban
 */
public class DaoCitasExamen {
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;
    public String criterioConsultaVentas;
    
    int anioInicio;
    int anioFin;
    int mesInicio;
    int mesFin;

    public DaoCitasExamen() {
        BaseDeDatos = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWhereVentas(CitasExamen citasExamen, String criterioConsulta) {

        criterioConsultaVentas = criterioConsulta;
        String where = "";

        int codigoCiudad = obtenerCodigoCiudad(citasExamen.getCiudad());
        String codigoGenero = obtenerGenero(citasExamen.getGenero());
        String codigoEstrato = citasExamen.getEstrato();
        int codigoEdad = obtenerCodigoEdad(citasExamen.getEdad());
        int codigoIngresos = obtenerCodigoIngreso(citasExamen.getIngresos());
        
        anioInicio = 0;
        anioFin = 0;
        mesInicio = 0;
        mesFin = 0;
        
        /*
        * En esta parte de determina el formato que debe tener el año de inicio y de fin de la consulta
        * de acueerdo a la selección que el usuario haya hecho
        */
        if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(citasExamen.getAnioInicio());
            anioFin = Integer.parseInt(citasExamen.getAnioFin());            
        } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(citasExamen.getAnioInicio());
        } else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")){
            anioFin = Integer.parseInt(citasExamen.getAnioFin());
        }
        
        //INICIO IF + GENERO -ESTRATO - EDAD - INGRESOS - CIUDAD
        if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero 
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' ";
            }

        } //FIN IF + GENERO - ESTRATO - EDAD - INGRESOS - CIUDAD
        // INICIO IF - GENERO + ESTRATO - EDAD - INGRESOS - CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE estrato_demografia = '" + codigoEstrato+ "' ";
            }

        } //FIN IF - GENERO + ESTRATO - EDAD - INGRESOS - CIUDAD
        // INICIO IF - GENERO - ESTRATO + EDAD - INGRESOS - CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE edad_demografia = '" + codigoEdad+ "' ";
            }

        } //FIN IF - GENERO - ESTRATO + EDAD - INGRESOS - CIUDAD
        // INICIO IF - GENERO - ESTRATO - EDAD + INGRESOS - CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE ingresos_demografia = '" + codigoIngresos+ "' ";
            }

        } //FIN IF - GENERO - ESTRATO - EDAD + INGRESOS - CIUDAD
        // INICIO IF - GENERO - ESTRATO - EDAD - INGRESOS + CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
               
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE ciudad_cita = '" + codigoCiudad+ "' ";
            }

        } //FIN IF - GENERO - ESTRATO - EDAD - INGRESOS + CIUDAD
        // INICIO IF + GENERO + ESTRATO - EDAD - INGRESOS - CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' ";
            }
        } // FIN IF + GENERO + ESTRATO - EDAD - INGRESOS - CIUDAD
        // INICIO IF + GENERO - ESTRATO + EDAD - INGRESOS - CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' ";
            }
        } // FIN IF + GENERO - ESTRATO + EDAD - INGRESOS - CIUDAD
        // INICIO IF + GENERO - ESTRATO - EDAD + INGRESOS - CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' ";
            }
        } // FIN IF + GENERO - ESTRATO - EDAD + INGRESOS - CIUDAD
        // INICIO IF + GENERO - ESTRATO - EDAD - INGRESOS + CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
        } // FIN IF + GENERO - ESTRATO - EDAD - INGRESOS + CIUDAD
        // INICIO IF - GENERO + ESTRATO + EDAD - INGRESOS - CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' ";
            }
        } // FIN IF - GENERO + ESTRATO + EDAD - INGRESOS - CIUDAD
        // INICIO IF - GENERO + ESTRATO - EDAD + INGRESOS - CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' ";
            }
        } // FIN IF - GENERO + ESTRATO - EDAD + INGRESOS - CIUDAD
        // INICIO IF - GENERO + ESTRATO - EDAD - INGRESOS + CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
        } // FIN IF - GENERO + ESTRATO - EDAD - INGRESOS + CIUDAD
        // INICIO IF - GENERO - ESTRATO + EDAD + INGRESOS - CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' ";
            }
        } // FIN IF - GENERO - ESTRATO + EDAD + INGRESOS - CIUDAD
        // INICIO IF - GENERO - ESTRATO + EDAD - INGRESOS + CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
        } // FIN IF - GENERO - ESTRATO + EDAD - INGRESOS + CIUDAD
        // INICIO IF - GENERO - ESTRATO - EDAD + INGRESOS + CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {

            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
        } // FIN IF - GENERO - ESTRATO - EDAD + INGRESOS + CIUDAD
        // INICIO IF + GENERO + ESTRATO + EDAD - INGRESOS - CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' ";
            }
            
        }// FIN IF + GENERO + ESTRATO + EDAD - INGRESOS - CIUDAD
        // INICIO IF + GENERO + ESTRATO - EDAD + INGRESOS - CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' ";
            }
            
        }// FIN IF + GENERO + ESTRATO - EDAD + INGRESOS - CIUDAD
        // INICIO IF + GENERO + ESTRATO - EDAD - INGRESOS + CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
            
        }// FIN IF + GENERO + ESTRATO - EDAD - INGRESOS + CIUDAD 
        // INICIO IF - GENERO + ESTRATO + EDAD + INGRESOS - CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' ";
            }
            
        }// FIN IF - GENERO + ESTRATO + EDAD + INGRESOS - CIUDAD 
        // INICIO IF - GENERO + ESTRATO + EDAD - INGRESOS + CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
            
        }// FIN IF - GENERO + ESTRATO + EDAD - INGRESOS + CIUDAD
        // INICIO IF - GENERO - ESTRATO + EDAD + INGRESOS + CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
            
        }// FIN IF - GENERO - ESTRATO + EDAD + INGRESOS + CIUDAD
        // INICIO IF + GENERO + ESTRATO + EDAD + INGRESOS - CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' ";
            }
            
        }// FIN IF + GENERO + ESTRATO + EDAD + INGRESOS - CIUDAD
        // INICIO IF - GENERO + ESTRATO + EDAD + INGRESOS + CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
            
        }// FIN IF - GENERO + ESTRATO + EDAD + INGRESOS + CIUDAD
        // INICIO IF + GENERO - ESTRATO + EDAD - INGRESOS + CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
            
        }// FIN IF + GENERO - ESTRATO + EDAD - INGRESOS + CIUDAD
        // INICIO IF + GENERO - ESTRATO - EDAD + INGRESOS + CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
            
        }// FIN IF + GENERO - ESTRATO - EDAD + INGRESOS + CIUDAD
        // INICIO IF - GENERO + ESTRATO - EDAD + INGRESOS + CIUDAD
        else if (citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
            
        }// FIN IF - GENERO + ESTRATO - EDAD + INGRESOS + CIUDAD
        // INICIO IF + GENERO + ESTRATO - EDAD + INGRESOS + CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
            
        }// FIN IF + GENERO + ESTRATO - EDAD + INGRESOS + CIUDAD
        // INICIO IF + GENERO - ESTRATO + EDAD + INGRESOS + CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
            
        }// FIN IF + GENERO - ESTRATO + EDAD + INGRESOS + CIUDAD
        // INICIO IF + GENERO + ESTRATO + EDAD + INGRESOS + CIUDAD
        else if (!citasExamen.getGenero().equals("Escoger una Opción...") && 
                !citasExamen.getEstrato().equals("Escoger una Opción...") &&
                !citasExamen.getEdad().equals("Escoger una Opción...") &&
                !citasExamen.getIngresos().equals("Escoger una Opción...") &&
                !citasExamen.getCiudad().equals("Escoger una Opción...")) {
            
            if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } else if (!citasExamen.getAnioInicio().equals("Escoger una Opción...") && citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                
                if (!citasExamen.getMesInicio().equals("Escoger una Opción...") && !citasExamen.getMesFin().equals("Escoger una Opción...")) {
                
                    mesInicio = obtenerCodigoMes(citasExamen.getMesInicio());
                    mesFin = obtenerCodigoMes(citasExamen.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(citasExamen.getMesInicio().equals("Escoger una Opción...") && citasExamen.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            }  else if (citasExamen.getAnioInicio().equals("Escoger una Opción...") && !citasExamen.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE genero_demografia = '" + codigoGenero + "' AND estrato_demografia = '" + codigoEstrato + "' AND edad_demografia = '" + codigoEdad + "' AND ingresos_demografia = '" + codigoIngresos + "' AND ciudad_cita = '" + codigoCiudad + "' ";
            }
            
        }// FIN IF + GENERO + ESTRATO + EDAD + INGRESOS + CIUDAD
         else {
            
            return "Error";
        }
        
        return where;
    }

    public ArrayList<String[]> conteoCitasEspecialidad(String where) {

        ArrayList<String[]> conteoCitas = new ArrayList<String[]>();
        String sql_select;
        
        if (criterioConsultaVentas.equals("Examenes Más Solicitados")) {
            sql_select = "SELECT nombre_examen, SUM(de.cantidad) AS total_citas FROM datamart_cita_examen de "
                       + "INNER JOIN dim_examen examen ON (de.examen_cita = examen.id_examen) "
                       + "INNER JOIN dim_demografia dm ON (de.demografia_cita = dm.id_demografia) " + where 
                       + "GROUP BY nombre_examen "
                       + "ORDER BY total_citas DESC LIMIT 5;";
        } else {
            sql_select = "SELECT nombre_examen, SUM(de.cantidad) AS total_citas FROM datamart_cita_examen de "
                       + "INNER JOIN dim_examen examen ON (de.examen_cita = examen.id_examen) "
                       + "INNER JOIN dim_demografia dm ON (de.demografia_cita = dm.id_demografia) " + where 
                       + "GROUP BY nombre_examen "
                       + "ORDER BY total_citas ASC LIMIT 5;";
        }
        
        System.out.println("Consulta: " + sql_select);

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while (tabla.next()) {
                
                String[] registro = new String[2];
                registro[0] = tabla.getString("nombre_examen");
                registro[1] = tabla.getString("total_citas");
                
                conteoCitas.add(registro);
                
            }
            
            return conteoCitas;
            
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
    
    public int obtenerCodigoCiudad(String nombreCiudad){
        
        int ciudadSeleccionada = 0;
        
        if (nombreCiudad.equals("KM 30")) {
            ciudadSeleccionada = 22;
        } else if (nombreCiudad.equals("ALCALA")) {
            ciudadSeleccionada = 91;
        } else if (nombreCiudad.equals("AMAIME")) {
            ciudadSeleccionada = 42;
        } else if (nombreCiudad.equals("ANDALUCIA")) {
            ciudadSeleccionada = 71;
        } else if (nombreCiudad.equals("ANSERMA NUEVO")) {
            ciudadSeleccionada = 61;
        } else if (nombreCiudad.equals("BOLIVAR")) {
            ciudadSeleccionada = 85;
        } else if (nombreCiudad.equals("BOLO")) {
            ciudadSeleccionada = 30;
        } else if (nombreCiudad.equals("BUCHITOLO")) {
            ciudadSeleccionada = 95;
        } else if (nombreCiudad.equals("BUENAVENTURA")) {
            ciudadSeleccionada = 26;
        } else if (nombreCiudad.equals("BUGA")) {
            ciudadSeleccionada = 41;
        } else if (nombreCiudad.equals("BUGALAGRANDE")) {
            ciudadSeleccionada = 70;
        } else if (nombreCiudad.equals("CAICEDONIA")) {
            ciudadSeleccionada = 78;
        } else if (nombreCiudad.equals("CALI")) {
            ciudadSeleccionada = 53;
        } else if (nombreCiudad.equals("CANDELARIA")) {
            ciudadSeleccionada = 46;
        } else if (nombreCiudad.equals("CARTAGO")) {
            ciudadSeleccionada = 74;
        } else if (nombreCiudad.equals("COSTA RICA")) {
            ciudadSeleccionada = 44;
        } else if (nombreCiudad.equals("DAGUA")) {
            ciudadSeleccionada = 47;
        } else if (nombreCiudad.equals("DAPA")) {
            ciudadSeleccionada = 24;
        } else if (nombreCiudad.equals("DARIEN")) {
            ciudadSeleccionada = 55;
        } else if (nombreCiudad.equals("EL AGUILA")) {
            ciudadSeleccionada = 99;
        } else if (nombreCiudad.equals("EL CABUYAL")) {
            ciudadSeleccionada = 57;
        } else if (nombreCiudad.equals("EL CARMELO")) {
            ciudadSeleccionada = 56;
        } else if (nombreCiudad.equals("EL CARMEN")) {
            ciudadSeleccionada = 98;
        } else if (nombreCiudad.equals("EL CERRITO")) {
            ciudadSeleccionada = 52;
        } else if (nombreCiudad.equals("EL DOVIO")) {
            ciudadSeleccionada = 88;
        } else if (nombreCiudad.equals("EL PALMAR")) {
            ciudadSeleccionada = 97;
        } else if (nombreCiudad.equals("EL TREINTA")) {
            ciudadSeleccionada = 93;
        } else if (nombreCiudad.equals("EL VINCULO")) {
            ciudadSeleccionada = 96;
        } else if (nombreCiudad.equals("FLORIDA")) {
            ciudadSeleccionada = 45;
        } else if (nombreCiudad.equals("GINEBRA")) {
            ciudadSeleccionada = 51;
        } else if (nombreCiudad.equals("GUABITAS")) {
            ciudadSeleccionada = 62;
        } else if (nombreCiudad.equals("GUACARI")) {
            ciudadSeleccionada = 58;
        } else if (nombreCiudad.equals("JAMUNDI")) {
            ciudadSeleccionada = 63;
        } else if (nombreCiudad.equals("LA BUITRERA")) {
            ciudadSeleccionada = 32;
        } else if (nombreCiudad.equals("LA CUMBRE")) {
            ciudadSeleccionada = 84;
        } else if (nombreCiudad.equals("LA HERRADURA")) {
            ciudadSeleccionada = 35;
        } else if (nombreCiudad.equals("LA MARINA")) {
            ciudadSeleccionada = 66;
        } else if (nombreCiudad.equals("LA PAILA")) {
            ciudadSeleccionada = 79;
        } else if (nombreCiudad.equals("LA QUISQUINA")) {
            ciudadSeleccionada = 25;
        } else if (nombreCiudad.equals("LA UNION")) {
            ciudadSeleccionada = 76;
        } else if (nombreCiudad.equals("LA URIBE")) {
            ciudadSeleccionada = 90;
        } else if (nombreCiudad.equals("LA VICTORIA")) {
            ciudadSeleccionada = 82;
        } else if (nombreCiudad.equals("LOS CHANCOS")) {
            ciudadSeleccionada = 29;
        } else if (nombreCiudad.equals("MEDIA CANOA")) {
            ciudadSeleccionada = 94;
        } else if (nombreCiudad.equals("OBANDO")) {
            ciudadSeleccionada = 80;
        } else if (nombreCiudad.equals("ORTIGAL")) {
            ciudadSeleccionada = 38;
        } else if (nombreCiudad.equals("PALMASECA")) {
            ciudadSeleccionada = 37;
        } else if (nombreCiudad.equals("PALMIRA")) {
            ciudadSeleccionada = 40;
        } else if (nombreCiudad.equals("POTRERILLO")) {
            ciudadSeleccionada = 33;
        } else if (nombreCiudad.equals("PRADERA")) {
            ciudadSeleccionada = 43;
        } else if (nombreCiudad.equals("PRESIDENTE")) {
            ciudadSeleccionada = 92;
        } else if (nombreCiudad.equals("RESTREPO")) {
            ciudadSeleccionada = 49;
        } else if (nombreCiudad.equals("RIO FRIO")) {
            ciudadSeleccionada = 69;
        } else if (nombreCiudad.equals("ROLDANILLO")) {
            ciudadSeleccionada = 77;
        } else if (nombreCiudad.equals("ROZO")) {
            ciudadSeleccionada = 81;
        } else if (nombreCiudad.equals("SALONICA")) {
            ciudadSeleccionada = 83;
        } else if (nombreCiudad.equals("SAN ANTONIO DE LOS C")) {
            ciudadSeleccionada = 54;
        } else if (nombreCiudad.equals("SAN JUAQUIN")) {
            ciudadSeleccionada = 59;
        } else if (nombreCiudad.equals("SAN PEDRO")) {
            ciudadSeleccionada = 64;
        } else if (nombreCiudad.equals("SANTA ELENA")) {
            ciudadSeleccionada = 39;
        } else if (nombreCiudad.equals("SEVILLA")) {
            ciudadSeleccionada = 87;
        } else if (nombreCiudad.equals("SONSO")) {
            ciudadSeleccionada = 60;
        } else if (nombreCiudad.equals("TABLONES")) {
            ciudadSeleccionada = 31;
        } else if (nombreCiudad.equals("TENERIFE")) {
            ciudadSeleccionada = 36;
        } else if (nombreCiudad.equals("TIENDA NUEVA")) {
            ciudadSeleccionada = 34;
        } else if (nombreCiudad.equals("TODOS LOS SANTOS")) {
            ciudadSeleccionada = 28;
        } else if (nombreCiudad.equals("TORO")) {
            ciudadSeleccionada = 86;
        } else if (nombreCiudad.equals("TRUJILLO")) {
            ciudadSeleccionada = 73;
        } else if (nombreCiudad.equals("TULUA")) {
            ciudadSeleccionada = 67;
        } else if (nombreCiudad.equals("TUMACO NARINO")) {
            ciudadSeleccionada = 27;
        } else if (nombreCiudad.equals("ULLOA")) {
            ciudadSeleccionada = 65;
        } else if (nombreCiudad.equals("VERSALLES")) {
            ciudadSeleccionada = 89;
        } else if (nombreCiudad.equals("VIJES")) {
            ciudadSeleccionada = 72;
        } else if (nombreCiudad.equals("VILLAGORGONA")) {
            ciudadSeleccionada = 48;
        } else if (nombreCiudad.equals("YOTOCO")) {
            ciudadSeleccionada = 68;
        } else if (nombreCiudad.equals("YUMBO")) {
            ciudadSeleccionada = 50;
        } else if (nombreCiudad.equals("ZARAGOZA")) {
            ciudadSeleccionada = 23;
        } else if (nombreCiudad.equals("ZARZAL")) {
            ciudadSeleccionada = 75;
        } else if (nombreCiudad.equals("BUENOS AIRES")) {
            ciudadSeleccionada = 28;
        } else if (nombreCiudad.equals("CAJIBIO")) {
            ciudadSeleccionada = 17;
        } else if (nombreCiudad.equals("CALDONO")) {
            ciudadSeleccionada = 16;
        } else if (nombreCiudad.equals("CALOTO")) {
            ciudadSeleccionada = 33;
        } else if (nombreCiudad.equals("CORINTO")) {
            ciudadSeleccionada = 31;
        } else if (nombreCiudad.equals("GUACHENE")) {
            ciudadSeleccionada = 23;
        } else if (nombreCiudad.equals("GUAPI")) {
            ciudadSeleccionada = 24;
        } else if (nombreCiudad.equals("MIRANDA")) {
            ciudadSeleccionada = 30;
        } else if (nombreCiudad.equals("MONDOMO")) {
            ciudadSeleccionada = 15;
        } else if (nombreCiudad.equals("MORALES")) {
            ciudadSeleccionada = 14;
        } else if (nombreCiudad.equals("PADILLA")) {
            ciudadSeleccionada = 32;
        } else if (nombreCiudad.equals("PESCADOR")) {
            ciudadSeleccionada = 10;
        } else if (nombreCiudad.equals("PIENDAMO")) {
            ciudadSeleccionada = 12;
        } else if (nombreCiudad.equals("POPAYAN")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("PUERTO TEJADA")) {
            ciudadSeleccionada = 21;
        } else if (nombreCiudad.equals("SANTANDER Q")) {
            ciudadSeleccionada = 65;
        } else if (nombreCiudad.equals("SILVIA")) {
            ciudadSeleccionada = 13;
        } else if (nombreCiudad.equals("SUAREZ")) {
            ciudadSeleccionada = 29;
        } else if (nombreCiudad.equals("TIMBIQUI")) {
            ciudadSeleccionada = 20;
        } else if (nombreCiudad.equals("TORIBIO")) {
            ciudadSeleccionada = 19;
        } else if (nombreCiudad.equals("TOTORO")) {
            ciudadSeleccionada = 18;
        } else if (nombreCiudad.equals("TUNIA")) {
            ciudadSeleccionada = 11;
        } else if (nombreCiudad.equals("VILLA RICA")) {
            ciudadSeleccionada = 22;
        } else if (nombreCiudad.equals("ALTAGRACIA")) {
            ciudadSeleccionada = 7;
        } else if (nombreCiudad.equals("ARMENIA")) {
            ciudadSeleccionada = 6;
        } else if (nombreCiudad.equals("CHINCHINA")) {
            ciudadSeleccionada = 9;
        } else if (nombreCiudad.equals("DOS QUEBRADAS")) {
            ciudadSeleccionada = 2;
        } else if (nombreCiudad.equals("LA FLORIDA")) {
            ciudadSeleccionada = 8;
        } else if (nombreCiudad.equals("LA VIRGINIA")) {
            ciudadSeleccionada = 4;
        } else if (nombreCiudad.equals("MARSELLA")) {
            ciudadSeleccionada = 5;
        } else if (nombreCiudad.equals("PEREIRA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("SANTA ROSA")) {
            ciudadSeleccionada = 3;
        } else if (nombreCiudad.equals("SANTUARIO")) {
            ciudadSeleccionada = 10;
        } else if (nombreCiudad.equals("ARMENIA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("BARCELONA")) {
            ciudadSeleccionada = 9;
        } else if (nombreCiudad.equals("BUENAVISTA")) {
            ciudadSeleccionada = 13;
        } else if (nombreCiudad.equals("CALARCA")) {
            ciudadSeleccionada = 5;
        } else if (nombreCiudad.equals("CIRCASIA")) {
            ciudadSeleccionada = 6;
        } else if (nombreCiudad.equals("CORDOBA")) {
            ciudadSeleccionada = 10;
        } else if (nombreCiudad.equals("FILANDIA")) {
            ciudadSeleccionada = 8; 
        } else if (nombreCiudad.equals("GENOVA")) {
            ciudadSeleccionada = 11;
        } else if (nombreCiudad.equals("MONTENEGRO")) {
            ciudadSeleccionada = 4;
        } else if (nombreCiudad.equals("PIJAO")) {
            ciudadSeleccionada = 12;
        } else if (nombreCiudad.equals("QUIMBAYA")) {
            ciudadSeleccionada = 2;
        } else if (nombreCiudad.equals("SALENTO")) {
            ciudadSeleccionada = 7;
        } else if (nombreCiudad.equals("LA TEBAIDA")) {
            ciudadSeleccionada = 3;
        } else if (nombreCiudad.equals("EL CHARCO")) {
            ciudadSeleccionada = 3;
        } else if (nombreCiudad.equals("IPIALES")) {
            ciudadSeleccionada = 4;
        } else if (nombreCiudad.equals("PASTO")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("TUMACO")) {
            ciudadSeleccionada = 2;
        } else if (nombreCiudad.equals("MANIZALES")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("CHINCHINA")) {
            ciudadSeleccionada = 2;
        } else if (nombreCiudad.equals("VITERBO")) {
            ciudadSeleccionada = 3;
        } else if (nombreCiudad.equals("RIOSUCIO")) {
            ciudadSeleccionada = 4;
        } else if (nombreCiudad.equals("ANSERMA")) {
            ciudadSeleccionada = 5;
        } else if (nombreCiudad.equals("SALAMINA")) {
            ciudadSeleccionada = 6;
        } else if (nombreCiudad.equals("ARGELIA")) {
            ciudadSeleccionada = 20;
        } else if (nombreCiudad.equals("JURADO")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("BOGOTA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("VILLAVICENCIO")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("NEIVA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("BUCARAMANGA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("BARRANQUILLA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("IBAGUE")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("MEDELLIN")) {
            ciudadSeleccionada = 1;
        }
        
        return ciudadSeleccionada;
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
