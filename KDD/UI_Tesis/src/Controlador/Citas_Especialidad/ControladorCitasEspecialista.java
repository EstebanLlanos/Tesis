/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Citas_Especialidad;

import Logico.Citas_Especialidad.CitasEspecialista;
import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class ControladorCitasEspecialista {
    
    //DaoCitasEspecialista daoCitasEspecialista;
    ArrayList<String[]> conteoCitas = new ArrayList();

    public ControladorCitasEspecialista() {
        //daoCitasEspecialista = new DaoCitasEspecialista();
    }

    public ArrayList<String[]> getCitas(String especialista, String departamento, String ciudad, String anioInicio, String mesInicio, String mesFin, String anioFin, String criterioConsulta) {

        String especialistaCitas = especialista;
        
        String ciudadCitas = ciudad;
        String departamentoCitas = departamento;
        
        String anioInicioCitas = anioInicio;
        String anioFinCitas = anioFin;
        
        String mesInicioCitas = mesInicio;
        String mesFinCitas = mesFin;
        
        String criterioConsultaVentas = criterioConsulta;

        CitasEspecialista citasEspecialista = new CitasEspecialista();
        citasEspecialista.setEspecialista(especialistaCitas);
        citasEspecialista.setCiudad(ciudadCitas);
        citasEspecialista.setDepartamento(departamentoCitas);
        citasEspecialista.setAnioInicio(anioInicioCitas);
        citasEspecialista.setAnioFin(anioFinCitas);
        citasEspecialista.setMesInicio(mesInicioCitas);
        citasEspecialista.setAnioFin(mesFinCitas);

        if (!anioInicioCitas.equals("Escoger una Opción...") && !anioFinCitas.equals("Escoger una Opción...") ) {
            int anioInicial = Integer.parseInt(anioInicioCitas);
            int anioFinal = Integer.parseInt(anioFinCitas);
            
            if (anioInicial > anioFinal) {
                String[] error = new String[1];
                error[0] = "Error Fecha";
                conteoCitas.add(error);

                return conteoCitas;   
            }
        } else if (especialistaCitas.equals("") && criterioConsultaVentas.equals("")) {
            String[] error = new String[1];
            error[0] = "Error Especialista";
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
        
        String restriccionesClausulaWhere = "";
        //String restriccionesClausulaWhere = daoCitasEspecialista.prepararRestriccionesClausulaWhereVentas(citasEspecialista, criterioConsultaVentas);
        
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
        } else if (restriccionesClausulaWhere.equals("Error Vendedor")) {
            String[] error = new String[1];
            error[0] = "Error Vendedor";
            conteoCitas.add(error);
            
            return conteoCitas;
        }
        
        //conteoCitas = daoCitasEspecialista.conteoCitasEspecialista(restriccionesClausulaWhere);
        
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