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

    public ArrayList<String[]> getVentas(String departamento, String sede) {

        String departamentoVentas = departamento;
        String sedeVentas = sede;

        VentasCiudades ventasCiudades = new VentasCiudades();
        ventasCiudades.setDepartamento(departamentoVentas);
        ventasCiudades.setSede(sedeVentas);

        String restriccionesClausulaWhere = daoVentasCiudades.prepararRestriccionesClausulaWhereVentas(ventasCiudades);
        if (restriccionesClausulaWhere.equals("Error")) {
            return conteoVentas;
        }
        
        conteoVentas = daoVentasCiudades.conteoVentasSede(restriccionesClausulaWhere);
        
        return conteoVentas;
    }
    
}
