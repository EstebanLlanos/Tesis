package Controlador.Afiliaciones;

import Dao.Afiliaciones.DaoVentasSedes;
import Logico.Afiliaciones.VentasSedes;
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
        
        if (Integer.parseInt(mesFinVentas) >= Integer.parseInt(mesInicioVentas)) {
            String[] error = new String[1];
            error[0] = "Error Fecha Mes";
            conteoVentas.add(error);

            return conteoVentas; 
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
    
}