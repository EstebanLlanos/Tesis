/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Solicitud_Servicios;

import Dao.Solicitud_Servicios.DaoSolicitudTipoServicio;
import Logico.Solicitud_Servicios.SolicitudTipoServicio;
import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class ControladorSolicitudTipoServicio {
    
    DaoSolicitudTipoServicio daoSolicitudTipoServicio;
    ArrayList<String[]> conteoSolicitudes = new ArrayList();

    public ControladorSolicitudTipoServicio() {

        daoSolicitudTipoServicio = new DaoSolicitudTipoServicio();
    }

    public ArrayList<String[]> getSolicitudes(String ciudad, String anioInicio, String mesInicio, String mesFin, String anioFin) {

        String ciudadSolicitudes = ciudad;
        
        String anioInicioSolicitudes = anioInicio;
        String anioFinSolicitudes = anioFin;
        
        String mesInicioSolicitudes = mesInicio;
        String mesFinSolicitudes = mesFin;
        
        SolicitudTipoServicio solicitudTipoServicio = new SolicitudTipoServicio();
        solicitudTipoServicio.setCiudad(ciudadSolicitudes);
        solicitudTipoServicio.setAnioInicio(anioInicioSolicitudes);
        solicitudTipoServicio.setAnioFin(anioFinSolicitudes);
        solicitudTipoServicio.setMesInicio(mesInicioSolicitudes);
        solicitudTipoServicio.setMesFin(mesFinSolicitudes);

        if (!anioInicioSolicitudes.equals("Escoger una Opción...") && !anioFinSolicitudes.equals("Escoger una Opción...") ) {
            int anioInicial = Integer.parseInt(anioInicioSolicitudes);
            int anioFinal = Integer.parseInt(anioFinSolicitudes);
            
            if (anioInicial > anioFinal) {
                conteoSolicitudes.clear();
                String[] error = new String[1];
                error[0] = "Error Fecha Año";
                conteoSolicitudes.add(error);

                return conteoSolicitudes;   
            }
        }
        
        int mesInicial = obtenerCodigoMes(mesInicioSolicitudes);
        int mesFinal = obtenerCodigoMes(mesFinSolicitudes);
        
        if (!mesInicioSolicitudes.equals("Escoger una Opción...") && !mesFinSolicitudes.equals("Escoger una Opción...") ) {
            if (mesInicial >= mesFinal) {
                conteoSolicitudes.clear();
                String[] error = new String[1];
                error[0] = "Error Fecha Mes";
                conteoSolicitudes.add(error);

                return conteoSolicitudes; 
            }
        }
        
        String restriccionesClausulaWhere = daoSolicitudTipoServicio.prepararRestriccionesClausulaWhereSolicitudes(solicitudTipoServicio);
        
        if (restriccionesClausulaWhere.equals("Error Fecha Año")) {
            conteoSolicitudes.clear();
            String[] error = new String[1];
            error[0] = "Error Fecha Año";
            conteoSolicitudes.add(error);
            
            return conteoSolicitudes;
        } else if (restriccionesClausulaWhere.equals("Error Fecha Mes")) {
            conteoSolicitudes.clear();
            String[] error = new String[1];
            error[0] = "Error Fecha Mes";
            conteoSolicitudes.add(error);
            
            return conteoSolicitudes;
        } else if (restriccionesClausulaWhere.equals("Error")) {
            conteoSolicitudes.clear();
            String[] error = new String[1];
            error[0] = "Error";
            conteoSolicitudes.add(error);
            
            return conteoSolicitudes;
        }
        
        conteoSolicitudes = daoSolicitudTipoServicio.conteoSolicitudes(restriccionesClausulaWhere);
        
        return conteoSolicitudes;
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
