/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Afiliaciones;

import Dao.Afiliaciones.DaoVentasCiudades;
import Logico.Afiliaciones.VentasCiudades;
import Dao.Afiliaciones.DaoVentasVendedores;
import Logico.Afiliaciones.VentasVendedores;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban
 */
public class ControladorVentasVendedores {
    
    DaoVentasVendedores daoVentasVendedores;
    ArrayList<String[]> conteoVentas = new ArrayList();

    public ControladorVentasVendedores() {
        daoVentasVendedores = new DaoVentasVendedores();
    }

    public ArrayList<String[]> getVentas(String ciudad, String sede, String anioInicio, String anioFin, String criterioConsulta) {

        String ciudadVentas = ciudad;
        String sedeVentas = sede;
        
        String anioInicioVentas = anioInicio;
        String anioFinVentas = anioFin;
        
        String criterioConsultaVentas = criterioConsulta;

        VentasVendedores ventasVendedores = new VentasVendedores();
        ventasVendedores.setCiudad(ciudadVentas);
        ventasVendedores.setSede(sedeVentas);
        ventasVendedores.setAnioInicio(anioInicioVentas);
        ventasVendedores.setAnioFin(anioFinVentas);

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
        
        String restriccionesClausulaWhere = daoVentasVendedores.prepararRestriccionesClausulaWhereVentas(ventasVendedores, criterioConsulta);
        
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
        
        conteoVentas = daoVentasVendedores.conteoVentasSede(restriccionesClausulaWhere);
        
        return conteoVentas;
    }
    
}
