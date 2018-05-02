/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Citas_Otros_Servicios;

import Dao.Citas_Examenes.DaoCitasInstitucion;
import Logico.Citas_Examenes.CitasInstitucion;
import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class ControladorCitasInstitucion {
    
    DaoCitasInstitucion daoCitasInstitucion;
    ArrayList<String[]> conteoCitas = new ArrayList();

    public ControladorCitasInstitucion() {
        daoCitasInstitucion = new DaoCitasInstitucion();
    }

    public ArrayList<String[]> getCitas(String institucion, String departamento, String ciudad, String anioInicio, String mesInicio, String mesFin, String anioFin, String criterioConsulta) {

        String institucionCitas = institucion;
        
        String ciudadCitas = ciudad;
        String departamentoCitas = departamento;
        
        String anioInicioCitas = anioInicio;
        String anioFinCitas = anioFin;
        
        String mesInicioCitas = mesInicio;
        String mesFinCitas = mesFin;
        
        String criterioConsultaVentas = criterioConsulta;

        CitasInstitucion citasInstitucion = new CitasInstitucion();
        citasInstitucion.setInstitucion(institucionCitas);
        citasInstitucion.setCiudad(ciudadCitas);
        citasInstitucion.setDepartamento(departamentoCitas);
        citasInstitucion.setAnioInicio(anioInicioCitas);
        citasInstitucion.setAnioFin(anioFinCitas);
        citasInstitucion.setMesInicio(mesInicioCitas);
        citasInstitucion.setMesFin(mesFinCitas);

        if (!anioInicioCitas.equals("Escoger una Opción...") && !anioFinCitas.equals("Escoger una Opción...") ) {
            int anioInicial = Integer.parseInt(anioInicioCitas);
            int anioFinal = Integer.parseInt(anioFinCitas);
            
            if (anioInicial > anioFinal) {
                String[] error = new String[1];
                error[0] = "Error Fecha";
                conteoCitas.add(error);

                return conteoCitas;   
            }
        } else if (institucionCitas.equals("") && criterioConsultaVentas.equals("")) {
            String[] error = new String[1];
            error[0] = "Error Institucion";
            conteoCitas.add(error);

            return conteoCitas;   
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
        
        String restriccionesClausulaWhere = daoCitasInstitucion.prepararRestriccionesClausulaWhereCitas(citasInstitucion, criterioConsultaVentas);
        
        if (restriccionesClausulaWhere.equals("Error")) {
            String[] error = new String[1];
            error[0] = "Error";
            conteoCitas.add(error);
            
            return conteoCitas;
        } else if (restriccionesClausulaWhere.equals("Error Fecha")) {
            String[] error = new String[1];
            error[0] = "Error Fecha";
            conteoCitas.add(error);
            
            return conteoCitas;
        } else if (restriccionesClausulaWhere.equals("Error Institucion")) {
            String[] error = new String[1];
            error[0] = "Error Institucion";
            conteoCitas.add(error);
            
            return conteoCitas;
        }
        
        conteoCitas = daoCitasInstitucion.conteoCitasInstitucion(restriccionesClausulaWhere);
        
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