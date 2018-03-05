/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Citas_Especialidad;

import Dao.Citas_Especialidad.DaoCitasEspecialidad;
import Logico.Citas_Especialidad.CitasEspecialidad;
import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class ControladorCitasEspecialidad {
    
    DaoCitasEspecialidad daoCitasEspecialidad;
    ArrayList<String[]> conteoCitas = new ArrayList();

    public ControladorCitasEspecialidad() {

        daoCitasEspecialidad = new DaoCitasEspecialidad();
    }

    public ArrayList<String[]> getCitas(String departamento, String ciudad, String genero, String estrato, String anioInicio, String mesInicio, String mesFin, String anioFin, String criterioConsulta) {

        String departamentoCitas = departamento;
        String ciudadCitas = ciudad;
        String generoCitas = genero;
        String estratoCitas = estrato;
        
        String anioInicioCitas = anioInicio;
        String anioFinCitas = anioFin;
        
        String mesInicioCitas = mesInicio;
        String mesFinCitas = mesFin;
        
        String criterioConsultaVentas = criterioConsulta;

        CitasEspecialidad citasEspecialidad = new CitasEspecialidad();
        citasEspecialidad.setDepartamento(departamentoCitas);
        citasEspecialidad.setCiudad(ciudadCitas);
        citasEspecialidad.setGenero(generoCitas);
        citasEspecialidad.setEstrato(estratoCitas);
        citasEspecialidad.setAnioInicio(anioInicioCitas);
        citasEspecialidad.setAnioFin(anioFinCitas);
        citasEspecialidad.setMesInicio(mesInicioCitas);
        citasEspecialidad.setMesFin(mesFinCitas);

        if (!anioInicioCitas.equals("Escoger una Opción...") && !anioFinCitas.equals("Escoger una Opción...") ) {
            int anioInicial = Integer.parseInt(anioInicioCitas);
            int anioFinal = Integer.parseInt(anioFinCitas);
            
            if (anioInicial > anioFinal) {
                conteoCitas.clear();
                String[] error = new String[1];
                error[0] = "Error Fecha Año";
                conteoCitas.add(error);

                return conteoCitas;   
            }
        }
        
        int mesInicial = obtenerCodigoMes(mesInicioCitas);
        int mesFinal = obtenerCodigoMes(mesFinCitas);
        
        if (!mesInicioCitas.equals("Escoger una Opción...") && !mesFinCitas.equals("Escoger una Opción...") ) {
            if (mesInicial >= mesFinal) {
                conteoCitas.clear();
                String[] error = new String[1];
                error[0] = "Error Fecha Mes";
                conteoCitas.add(error);

                return conteoCitas; 
            }
        }
        
        String restriccionesClausulaWhere = daoCitasEspecialidad.prepararRestriccionesClausulaWhereVentas(citasEspecialidad, criterioConsultaVentas);
        
        if (restriccionesClausulaWhere.equals("Error Fecha Año")) {
            conteoCitas.clear();
            String[] error = new String[1];
            error[0] = "Error Fecha Año";
            conteoCitas.add(error);
            
            return conteoCitas;
        } else if (restriccionesClausulaWhere.equals("Error Fecha Mes")) {
            conteoCitas.clear();
            String[] error = new String[1];
            error[0] = "Error Fecha Mes";
            conteoCitas.add(error);
            
            return conteoCitas;
        } else if (restriccionesClausulaWhere.equals("Error")) {
            conteoCitas.clear();
            String[] error = new String[1];
            error[0] = "Error";
            conteoCitas.add(error);
            
            return conteoCitas;
        }
        
        conteoCitas = daoCitasEspecialidad.conteoVentasSede(restriccionesClausulaWhere);
        
        return conteoCitas;
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
