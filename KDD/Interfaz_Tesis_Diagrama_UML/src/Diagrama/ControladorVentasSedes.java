package Diagrama;

import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class ControladorVentasSedes {
    
    DaoVentasSedes daoVentasSedes;
    ArrayList<String[]> conteoVentas = new ArrayList();

    public ControladorVentasSedes() {
        daoVentasSedes = new DaoVentasSedes();
    }

    public ArrayList<String[]> getVentas(String anioInicio, String anioFin, String mesInicio, String mesFin) {
        
        String anioInicioVentas = anioInicio;
        String anioFinVentas = anioFin;
        String mesInicioVentas = mesInicio;
        String mesFinVentas = mesFin;

        VentasSedes ventasSedes = new VentasSedes();
        ventasSedes.setAnioInicio(anioInicioVentas);
        ventasSedes.setAnioFin(anioFinVentas);
        ventasSedes.setMesInicio(mesInicioVentas);
        ventasSedes.setMesFin(mesFinVentas);

        if (!anioInicioVentas.equals("Escoger una Opción...") && !anioFinVentas.equals("Escoger una Opción...") ) {
            int anioInicial = Integer.parseInt(anioInicioVentas);
            int anioFinal = Integer.parseInt(anioFinVentas);
            
            if (anioInicial > anioFinal) {
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
                String[] error = new String[1];
                error[0] = "Error Fecha Mes";
                conteoVentas.add(error);

                return conteoVentas; 
            }
        }
        
        String restriccionesClausulaWhere = daoVentasSedes.prepararRestriccionesClausulaWhereVentas(ventasSedes);
        
        if (restriccionesClausulaWhere.equals("Error Fecha Año")) {
            String[] error = new String[1];
            error[0] = "Error Fecha Año";
            conteoVentas.add(error);
            
            return conteoVentas;
            
        } else if(restriccionesClausulaWhere.equals("Error Fecha Mes")){
            String[] error = new String[1];
            error[0] = "Error Fecha Mes";
            conteoVentas.add(error);
            
            return conteoVentas;
        }
        
        conteoVentas = daoVentasSedes.conteoVentasSede(restriccionesClausulaWhere);
        
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