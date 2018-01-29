/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Afiliaciones;

import Dao.Afiliaciones.DaoVentasCiudades;
import Logico.Afiliaciones.VentasCiudades;
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

    public ArrayList<String[]> getVentas(String departamento, String sede, String anioInicio, String anioFin, String criterioConsulta) {

        String departamentoVentas = departamento;
        String sedeVentas = sede;
        
        String anioInicioVentas = anioInicio;
        String anioFinVentas = anioFin;
        
        String criterioConsultaVentas = criterioConsulta;

        VentasCiudades ventasCiudades = new VentasCiudades();
        ventasCiudades.setDepartamento(departamentoVentas);
        ventasCiudades.setSede(sedeVentas);
        ventasCiudades.setAnioInicio(anioInicioVentas);
        ventasCiudades.setAnioFin(anioFinVentas);

        if (!anioInicioVentas.equals("Escoger una Opción...") && !anioFinVentas.equals("Escoger una Opción...") ) {
            int anioInicial = Integer.parseInt(anioInicioVentas);
            int anioFinal = Integer.parseInt(anioFinVentas);
            
            if (anioInicial > anioFinal) {
                String[] error = new String[1];
                error[0] = "Error Fecha";
                conteoVentas.add(error);

                return conteoVentas;   
            }
        }
        
        String restriccionesClausulaWhere = daoVentasCiudades.prepararRestriccionesClausulaWhereVentas(ventasCiudades, criterioConsulta);
        
        if (restriccionesClausulaWhere.equals("Error")) {
            String[] error = new String[1];
            error[0] = "Error";
            conteoVentas.add(error);
            
            return conteoVentas;
        } else if (restriccionesClausulaWhere.equals("Error Fecha")) {
            String[] error = new String[1];
            error[0] = "Error Fecha";
            conteoVentas.add(error);
            
            return conteoVentas;
        }
        
        conteoVentas = daoVentasCiudades.conteoVentasSede(restriccionesClausulaWhere);
        
        return conteoVentas;
    }
    
}
