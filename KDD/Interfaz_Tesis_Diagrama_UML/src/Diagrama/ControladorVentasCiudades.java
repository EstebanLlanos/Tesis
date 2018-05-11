/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diagrama;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban
 */
public class ControladorVentasCiudades {
    
    DaoVentasCiudades daoVentasCiudades;
    ArrayList<String[]> conteoVentas = new ArrayList();

    public ControladorVentasCiudades() {

        daoVentasCiudades = new DaoVentasCiudades();
    }

    public ArrayList<String[]> getVentas(String departamento, String sede, String anioInicio, String mesInicio, String mesFin, String anioFin, String criterioConsulta) {

        String departamentoVentas = departamento;
        String sedeVentas = sede;
        
        String anioInicioVentas = anioInicio;
        String anioFinVentas = anioFin;
        
        String mesInicioVentas = mesInicio;
        String mesFinVentas = mesFin;
        
        String criterioConsultaVentas = criterioConsulta;

        VentasCiudades ventasCiudades = new VentasCiudades();
        ventasCiudades.setDepartamento(departamentoVentas);
        ventasCiudades.setSede(sedeVentas);
        ventasCiudades.setAnioInicio(anioInicioVentas);
        ventasCiudades.setAnioFin(anioFinVentas);
        ventasCiudades.setMesInicio(mesInicioVentas);
        ventasCiudades.setMesFin(mesFinVentas);

        if (!anioInicioVentas.equals("Escoger una Opción...") && !anioFinVentas.equals("Escoger una Opción...") ) {
            int anioInicial = Integer.parseInt(anioInicioVentas);
            int anioFinal = Integer.parseInt(anioFinVentas);
            
            if (anioInicial > anioFinal) {
                conteoVentas.clear();
                String[] error = new String[1];
                error[0] = "Error Fecha Año";
                conteoVentas.add(error);

                return conteoVentas;   
            }
        }
        
        int mesInicial = obtenerCodigoMes(mesInicioVentas);
        int mesFinal = obtenerCodigoMes(mesFinVentas);
        
        if (!mesInicioVentas.equals("Escoger una Opción...") && !mesFinVentas.equals("Escoger una Opción...") ) {
            if (mesInicial >= mesFinal) {
                conteoVentas.clear();
                String[] error = new String[1];
                error[0] = "Error Fecha Mes";
                conteoVentas.add(error);

                return conteoVentas; 
            }
        }
        
        String restriccionesClausulaWhere = daoVentasCiudades.prepararRestriccionesClausulaWhereVentas(ventasCiudades, criterioConsultaVentas);
        
        if (restriccionesClausulaWhere.equals("Error Fecha Año")) {
            conteoVentas.clear();
            String[] error = new String[1];
            error[0] = "Error Fecha Año";
            conteoVentas.add(error);
            
            return conteoVentas;
        } else if (restriccionesClausulaWhere.equals("Error Fecha Mes")) {
            conteoVentas.clear();
            String[] error = new String[1];
            error[0] = "Error Fecha Mes";
            conteoVentas.add(error);
            
            return conteoVentas;
        } else if (restriccionesClausulaWhere.equals("Error")) {
            conteoVentas.clear();
            String[] error = new String[1];
            error[0] = "Error";
            conteoVentas.add(error);
            
            return conteoVentas;
        }
        
        conteoVentas = daoVentasCiudades.conteoVentasSede(restriccionesClausulaWhere);
        
        return conteoVentas;
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
    
}
