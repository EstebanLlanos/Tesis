/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diagrama;

import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class ControladorVentasDemografia {
    
    DaoVentasDemografia daoVentasDemografia;
    ArrayList<String[]> conteoVentas = new ArrayList();

    public ControladorVentasDemografia() {

        daoVentasDemografia = new DaoVentasDemografia();
    }

    public ArrayList<String[]> getVentas(String genero, String estrato, String edad, String ingresos, String anioInicio, String mesInicio, String mesFin, String anioFin, String criterioConsulta) {

        String generoVentas = genero;
        String estratoVentas = estrato;
        String edadVentas = edad;
        String ingresoVentas = ingresos;
        
        String anioInicioVentas = anioInicio;
        String anioFinVentas = anioFin;
        
        String mesInicioVentas = mesInicio;
        String mesFinVentas = mesFin;
        
        String criterioConsultaVentas = criterioConsulta;

        VentasDemografia ventasDemografía = new VentasDemografia();
        ventasDemografía.setGenero(generoVentas);
        ventasDemografía.setEstrato(estratoVentas);
        ventasDemografía.setEdad(edadVentas);
        ventasDemografía.setIngresos(ingresoVentas);
        
        ventasDemografía.setAnioInicio(anioInicioVentas);
        ventasDemografía.setAnioFin(anioFinVentas);
        ventasDemografía.setMesInicio(mesInicioVentas);
        ventasDemografía.setMesFin(mesFinVentas);

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
        
        String restriccionesClausulaWhere = daoVentasDemografia.prepararRestriccionesClausulaWhereVentas(ventasDemografía, criterioConsultaVentas);
        
        switch (restriccionesClausulaWhere) {
            case "Error Fecha Año":
            {
                conteoVentas.clear();
                String[] error = new String[1];
                error[0] = "Error Fecha Año";
                conteoVentas.add(error);
                
                return conteoVentas;
            }
            case "Error Fecha Mes":
            {
                conteoVentas.clear();
                String[] error = new String[1];
                error[0] = "Error Fecha Mes";
                conteoVentas.add(error);
                
                return conteoVentas;
            }
            case "Error":
            {
                conteoVentas.clear();
                String[] error = new String[1];
                error[0] = "Error";
                conteoVentas.add(error);
                
            return conteoVentas;
        }
        }
        
        conteoVentas = daoVentasDemografia.conteoVentasSede(restriccionesClausulaWhere);
        
        return conteoVentas;
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
    
}
