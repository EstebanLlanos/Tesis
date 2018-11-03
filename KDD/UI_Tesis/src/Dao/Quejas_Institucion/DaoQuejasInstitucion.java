/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao.Quejas_Institucion;

import ConectorBD.ConexionBD;
import Logico.Quejas_Institucion.QuejasInstitucion;
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
public class DaoQuejasInstitucion {
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;
    public String criterioConsultaQuejas;

    int anioInicio;
    int anioFin;
    int mesInicio;
    int mesFin;
    
    public DaoQuejasInstitucion() {
        BaseDeDatos = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWhereQuejas(QuejasInstitucion quejasInstitucion, String criterioConsulta) {

        criterioConsultaQuejas = criterioConsulta;
        String where = "";

        int codigoCiudad = obtenerCodigoCiudad(quejasInstitucion.getCiudad());
        int codigoDepartamento = obtenerCodigoDepartamento(quejasInstitucion.getDepartamento());
        int codigoCalificacion = obtenerCodigoCalificacion(quejasInstitucion.getCalificacion());
        
        anioInicio = 0;
        anioFin = 0;
        
        /*
        * En esta parte de determina el formato que debe tener el año de inicio y dde fin de la consulta
        * de acuerdo a la selección que el usuario haya hecho
        */
        if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(quejasInstitucion.getAnioInicio());
            anioFin = Integer.parseInt(quejasInstitucion.getAnioFin());            
        } else if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && quejasInstitucion.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(quejasInstitucion.getAnioInicio());
        } else if (quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")){
            anioFin = Integer.parseInt(quejasInstitucion.getAnioFin());
        }
        
        if (!quejasInstitucion.getCiudad().equals("Escoger una Opción...") && 
             !quejasInstitucion.getDepartamento().equals("Escoger una Opción...")) {
            
            return "Error";
            
        }
            
        if (!quejasInstitucion.getCiudad().equals("Escoger una Opción...") && 
             quejasInstitucion.getDepartamento().equals("Escoger una Opción...") &&
             quejasInstitucion.getCalificacion().equals("Escoger una Opción...")) {

            if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {

                if (!quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && !quejasInstitucion.getMesFin().equals("Escoger una Opción...")) {

                    mesInicio = obtenerCodigoMes(quejasInstitucion.getMesInicio());
                    mesFin = obtenerCodigoMes(quejasInstitucion.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {

                        where = " WHERE ciudad_queja = '" + codigoCiudad 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin < 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";

                    } else if (mesInicio < 10 && mesFin >= 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin >= 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";

                    }

                } else if(quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && quejasInstitucion.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ciudad_queja = '" + codigoCiudad 
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }

            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {

                if (!quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && !quejasInstitucion.getMesFin().equals("Escoger una Opción...")) {

                    mesInicio = obtenerCodigoMes(quejasInstitucion.getMesInicio());
                    mesFin = obtenerCodigoMes(quejasInstitucion.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {

                        where = " WHERE ciudad_queja = '" + codigoCiudad 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin < 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";

                    } else if (mesInicio < 10 && mesFin >= 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin >= 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";

                    }

                } else if(quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && quejasInstitucion.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ciudad_queja = '" + codigoCiudad 
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }

            }  else if (quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE ciudad_queja = '" + codigoCiudad + "' ";
            }

        } else if (!quejasInstitucion.getCiudad().equals("Escoger una Opción...") && 
             quejasInstitucion.getDepartamento().equals("Escoger una Opción...") &&
             !quejasInstitucion.getCalificacion().equals("Escoger una Opción...")) {

            if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {

                if (!quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && !quejasInstitucion.getMesFin().equals("Escoger una Opción...")) {

                    mesInicio = obtenerCodigoMes(quejasInstitucion.getMesInicio());
                    mesFin = obtenerCodigoMes(quejasInstitucion.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {

                        where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin < 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";

                    } else if (mesInicio < 10 && mesFin >= 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin >= 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";

                    }

                } else if(quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && quejasInstitucion.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }

            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {

                if (!quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && !quejasInstitucion.getMesFin().equals("Escoger una Opción...")) {

                    mesInicio = obtenerCodigoMes(quejasInstitucion.getMesInicio());
                    mesFin = obtenerCodigoMes(quejasInstitucion.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {

                        where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin < 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";

                    } else if (mesInicio < 10 && mesFin >= 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin >= 10){

                        where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";

                    }

                } else if(quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && quejasInstitucion.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }

            }  else if (quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE ciudad_queja = '" + codigoCiudad + "' AND calificacion_queja = '" + codigoCalificacion + "' ";
            }

        } else if (quejasInstitucion.getCiudad().equals("Escoger una Opción...") && 
             !quejasInstitucion.getDepartamento().equals("Escoger una Opción...") &&
             quejasInstitucion.getCalificacion().equals("Escoger una Opción...")) {

            if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {

                if (!quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && !quejasInstitucion.getMesFin().equals("Escoger una Opción...")) {

                    mesInicio = obtenerCodigoMes(quejasInstitucion.getMesInicio());
                    mesFin = obtenerCodigoMes(quejasInstitucion.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin < 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";

                    } else if (mesInicio < 10 && mesFin >= 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin >= 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";

                    }

                } else if(quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && quejasInstitucion.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE departamento_ciudad = '" + codigoDepartamento
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }

            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {

                if (!quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && !quejasInstitucion.getMesFin().equals("Escoger una Opción...")) {

                    mesInicio = obtenerCodigoMes(quejasInstitucion.getMesInicio());
                    mesFin = obtenerCodigoMes(quejasInstitucion.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin < 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";

                    } else if (mesInicio < 10 && mesFin >= 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin >= 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";

                    }

                } else if(quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && quejasInstitucion.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE departamento_ciudad = '" + codigoDepartamento
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }

            }  else if (quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' ";
            }

        } else if (quejasInstitucion.getCiudad().equals("Escoger una Opción...") && 
             !quejasInstitucion.getDepartamento().equals("Escoger una Opción...") &&
             !quejasInstitucion.getCalificacion().equals("Escoger una Opción...")) {

            if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {

                if (!quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && !quejasInstitucion.getMesFin().equals("Escoger una Opción...")) {

                    mesInicio = obtenerCodigoMes(quejasInstitucion.getMesInicio());
                    mesFin = obtenerCodigoMes(quejasInstitucion.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin < 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";

                    } else if (mesInicio < 10 && mesFin >= 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin >= 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";

                    }

                } else if(quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && quejasInstitucion.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }

            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {

                if (!quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && !quejasInstitucion.getMesFin().equals("Escoger una Opción...")) {

                    mesInicio = obtenerCodigoMes(quejasInstitucion.getMesInicio());
                    mesFin = obtenerCodigoMes(quejasInstitucion.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin < 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";

                    } else if (mesInicio < 10 && mesFin >= 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin >= 10){

                        where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";

                    }

                } else if(quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && quejasInstitucion.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }

            }  else if (quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE departamento_ciudad = '" + codigoDepartamento + "' AND calificacion_queja = '" + codigoCalificacion + "' ";
            }

        } else if (quejasInstitucion.getCiudad().equals("Escoger una Opción...") && 
             quejasInstitucion.getDepartamento().equals("Escoger una Opción...") &&
             !quejasInstitucion.getCalificacion().equals("Escoger una Opción...")) {

            if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {

                if (!quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && !quejasInstitucion.getMesFin().equals("Escoger una Opción...")) {

                    mesInicio = obtenerCodigoMes(quejasInstitucion.getMesInicio());
                    mesFin = obtenerCodigoMes(quejasInstitucion.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {

                        where = " WHERE calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin < 10){

                        where = " WHERE calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";

                    } else if (mesInicio < 10 && mesFin >= 10){

                        where = " WHERE calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin >= 10){

                        where = " WHERE calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";

                    }

                } else if(quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && quejasInstitucion.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101)' AND " + (anioFin+"").substring(0, 4) + "1201) ";
                } else {
                    return "Error Fecha Mes";
                }

            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {

                if (!quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && !quejasInstitucion.getMesFin().equals("Escoger una Opción...")) {

                    mesInicio = obtenerCodigoMes(quejasInstitucion.getMesInicio());
                    mesFin = obtenerCodigoMes(quejasInstitucion.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {

                        where = " WHERE calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin < 10){

                        where = " WHERE calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";

                    } else if (mesInicio < 10 && mesFin >= 10){

                        where = " WHERE calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";

                    } else if (mesInicio >= 10 && mesFin >= 10){

                        where = " WHERE calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";

                    }

                } else if(quejasInstitucion.getMesInicio().equals("Escoger una Opción...") && quejasInstitucion.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE calificacion_queja = '" + codigoCalificacion 
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }

            }  else if (quejasInstitucion.getAnioInicio().equals("Escoger una Opción...") && !quejasInstitucion.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE calificacion_queja = '" + codigoCalificacion + "' ";
            }

        } else {

            return "Error";
        }
        
        return where;
    }

    public ArrayList<String[]> conteoQuejasInstitucion(String where) {

        ArrayList<String[]> conteoQuejas = new ArrayList<String[]>();
        String sql_select = "";
        
        if (criterioConsultaQuejas.equals("Instituciones Más Solicitadas")) {
            
            sql_select = "SELECT nombre_institucion, SUM(desp.cantidad) AS total_quejas FROM datamart_queja_instituciones desp " +
                         "INNER JOIN dim_institucion esp ON (desp.institucion_queja = esp.id_institucion) " +
                         "INNER JOIN dim_ciudad cd ON (desp.ciudad_queja = cd.id_ciudad) " + where +
                         " GROUP BY nombre_institucion " +
                         "ORDER BY total_quejas DESC LIMIT 5;";

        } else if (criterioConsultaQuejas.equals("Instituciones Más Solicitadas")) {

            sql_select = "SELECT nombre_institucion, SUM(desp.cantidad) AS total_quejas FROM datamart_queja_instituciones desp " +
                         "INNER JOIN dim_especialista esp ON (desp.institucion_queja = esp.id_institucion) " +
                         "INNER JOIN dim_ciudad cd ON (desp.ciudad_queja = cd.id_ciudad) " + where +
                         " GROUP BY nombre_institucion " +
                         "ORDER BY total_quejas ASC LIMIT 5;";
        }
        
        System.out.println("Consulta: " + sql_select);


        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);

            while (tabla.next()) {

                String[] registro = new String[2];
                String[] nombreEspecialistaAcortado = tabla.getString("nombre_institucion").split(" ");

                if (nombreEspecialistaAcortado.length == 3) {
                    registro[0] = nombreEspecialistaAcortado[0] + " " + nombreEspecialistaAcortado[2];
                    registro[1] = tabla.getString("total_quejas");   
                } else if (nombreEspecialistaAcortado.length == 4) {
                    registro[0] = nombreEspecialistaAcortado[0] + " " + nombreEspecialistaAcortado[3];
                    registro[1] = tabla.getString("total_quejas");  
                } else {
                    registro[0] = tabla.getString("nombre_institucion");
                    registro[1] = tabla.getString("total_quejas");  
                }

                conteoQuejas.add(registro);

            }

            return conteoQuejas;

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
    
    public int obtenerCodigoCalificacion(String calificacionQueja){
    
        int calificacionSeleccionada = 0;
        
        if (calificacionQueja.equals("POSITIVO")) {
            calificacionSeleccionada = 1;
        } else if (calificacionQueja.equals("NEUTRAL")) {
            calificacionSeleccionada = 2;
        } else if (calificacionQueja.equals("NEGATIVO")) {
            calificacionSeleccionada = 3;
        }
        
        return calificacionSeleccionada;
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
