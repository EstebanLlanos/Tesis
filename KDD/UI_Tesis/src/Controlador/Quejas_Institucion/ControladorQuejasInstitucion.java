/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Quejas_Institucion;

import Dao.Quejas_Institucion.DaoQuejasInstitucion;
import Logico.Quejas_Institucion.QuejasInstitucion;
import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class ControladorQuejasInstitucion {
    
    DaoQuejasInstitucion daoQuejasInstitucion;
    ArrayList<String[]> conteoQuejas = new ArrayList();

    public ControladorQuejasInstitucion() {
        daoQuejasInstitucion = new DaoQuejasInstitucion();
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

        QuejasInstitucion quejasInstitucion = new QuejasInstitucion();
        quejasInstitucion.setCalificacion(calificacionQuejas);
        quejasInstitucion.setCiudad(ciudadQuejas);
        quejasInstitucion.setDepartamento(departamentoQuejas);
        quejasInstitucion.setAnioInicio(anioInicioQuejas);
        quejasInstitucion.setAnioFin(anioFinQuejas);
        quejasInstitucion.setMesInicio(mesInicioQuejas);
        quejasInstitucion.setMesFin(mesFinQuejas);

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
        
        String restriccionesClausulaWhere = daoQuejasInstitucion.prepararRestriccionesClausulaWhereQuejas(quejasInstitucion, criterioConsultaQuejas);
        
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
        } else if (restriccionesClausulaWhere.equals("Error Institucion")) {
            String[] error = new String[1];
            error[0] = "Error Institucion";
            conteoQuejas.add(error);
            
            return conteoQuejas;
        }
        
        conteoQuejas = daoQuejasInstitucion.conteoQuejasInstitucion(restriccionesClausulaWhere);
        
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