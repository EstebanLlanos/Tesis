/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Cupos;

import Dao.Cupos.DaoVentasVendedores;
import Logico.Cupos.VentasVendedores;
import java.util.ArrayList;

/*
 * @author Esteban
 */
public class ControladorVentasVendedores {
    
    DaoVentasVendedores daoVentasVendedores;
    ArrayList<String[]> conteoVentas = new ArrayList();

    public ControladorVentasVendedores() {
        daoVentasVendedores = new DaoVentasVendedores();
    }

    public ArrayList<String[]> getVentas(String vendedor, String ciudad, String sede, String anioInicio, String anioFin, String criterioConsulta) {

        String vendedorVentas = vendedor;
        
        String ciudadVentas = ciudad;
        String sedeVentas = sede;
        
        String anioInicioVentas = anioInicio;
        String anioFinVentas = anioFin;
        
        String criterioConsultaVentas = criterioConsulta;

        VentasVendedores ventasVendedores = new VentasVendedores();
        ventasVendedores.setVendedor(vendedorVentas);
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
        } else if (vendedorVentas.equals("") && criterioConsultaVentas.equals("")) {
            String[] error = new String[1];
            error[0] = "Error Vendedor";
            conteoVentas.add(error);

            return conteoVentas;   
        }
        
        String restriccionesClausulaWhere = daoVentasVendedores.prepararRestriccionesClausulaWhereVentas(ventasVendedores, criterioConsultaVentas);
        
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
        } else if (restriccionesClausulaWhere.equals("Error Vendedor")) {
            String[] error = new String[1];
            error[0] = "Error Vendedor";
            conteoVentas.add(error);
            
            return conteoVentas;
        }
        
        conteoVentas = daoVentasVendedores.conteoVentasVendedor(restriccionesClausulaWhere);
        
        return conteoVentas;
    }
    
}
