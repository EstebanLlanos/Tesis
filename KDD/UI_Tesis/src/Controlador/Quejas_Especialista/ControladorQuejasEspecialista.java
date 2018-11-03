/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Quejas_Especialista;

import Dao.Quejas_Especialista.DaoQuejasEspecialista;
import Logico.Quejas_Especialista.QuejasEspecialista;
import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class ControladorQuejasEspecialista {
    
    DaoQuejasEspecialista daoQuejasEspecialista;
    ArrayList<String[]> conteoQuejas = new ArrayList();

    public ControladorQuejasEspecialista() {
        daoQuejasEspecialista = new DaoQuejasEspecialista();
    }

    public ArrayList<String[]> getQuejas(String calificacion, String departamento, String ciudad, String anioInicio, String mesInicio, String mesFin, String anioFin, String criterioConsulta) {

        
        String calificacionQuejas = calificacion;
        
        String ciudadQuejas = ciudad;
        String departamentoQuejas = departamento;
        
        String anioInicioQuejas = anioInicio;
        String anioFinQuejas = anioFin;
        
        String mesInicioQuejas = mesInicio;
        String mesFinQuejas = mesFin;
        
        String criterioConsultaQuejas = criterioConsulta;

        QuejasEspecialista quejasEspecialista = new QuejasEspecialista();
        quejasEspecialista.setCalificacion(calificacionQuejas);
        quejasEspecialista.setCiudad(ciudadQuejas);
        quejasEspecialista.setDepartamento(departamentoQuejas);
        quejasEspecialista.setAnioInicio(anioInicioQuejas);
        quejasEspecialista.setAnioFin(anioFinQuejas);
        quejasEspecialista.setMesInicio(mesInicioQuejas);
        quejasEspecialista.setMesFin(mesFinQuejas);

        if (!anioInicioQuejas.equals("Escoger una Opción...") && !anioFinQuejas.equals("Escoger una Opción...") ) {
            int anioInicial = Integer.parseInt(anioInicioQuejas);
            int anioFinal = Integer.parseInt(anioFinQuejas);
            
            if (anioInicial > anioFinal) {
                String[] error = new String[1];
                error[0] = "Error Fecha";
                conteoQuejas.add(error);

                return conteoQuejas;   
            }
        }
        
        int mesInicial = obtenerCodigoMes(mesInicioQuejas);
        int mesFinal = obtenerCodigoMes(mesFinQuejas);
        
        if (!mesInicioQuejas.equals("Escoger una Opción...") && !mesFinQuejas.equals("Escoger una Opción...") ) {
            if (mesInicial >= mesFinal) {
                conteoQuejas.clear();
                String[] error = new String[1];
                error[0] = "Error Fecha Mes";
                conteoQuejas.add(error);

                return conteoQuejas; 
            }
        }
        
        String restriccionesClausulaWhere = daoQuejasEspecialista.prepararRestriccionesClausulaWhereQuejas(quejasEspecialista, criterioConsultaQuejas);
        
        if (restriccionesClausulaWhere.equals("Error")) {
            String[] error = new String[1];
            error[0] = "Error";
            conteoQuejas.add(error);
            
            return conteoQuejas;
        } else if (restriccionesClausulaWhere.equals("Error Fecha")) {
            String[] error = new String[1];
            error[0] = "Error Fecha";
            conteoQuejas.add(error);
            
            return conteoQuejas;
        } else if (restriccionesClausulaWhere.equals("Error Especialista")) {
            String[] error = new String[1];
            error[0] = "Error Especialista";
            conteoQuejas.add(error);
            
            return conteoQuejas;
        }
        
        conteoQuejas = daoQuejasEspecialista.conteoQuejasEspecialista(restriccionesClausulaWhere);
        
        return conteoQuejas;
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