/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Dao.DaoPlanesMasEscogidos;
import Gráficos.FXBarChart;
import Gráficos.FXPieChart;
import Logico.PlanesMasEscogidos;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ControladorPlanesMasEscogidos {
    
    DaoPlanesMasEscogidos daoPlanesMasEscogidos;

    public ControladorPlanesMasEscogidos() {
        daoPlanesMasEscogidos = new DaoPlanesMasEscogidos();
    }
    
    public ArrayList <Integer> obtenerPlanes(String datos, String prepago, String postpago, String corporativo/*Paramatros dinamicos*/) {

        ArrayList <Integer> planesMasEscogidos = new ArrayList<>();

        PlanesMasEscogidos planes = new PlanesMasEscogidos();
        planes.setPlanDeDatos(datos);
        planes.setPlanPrepagoVoz(prepago);
        planes.setPlanPostpagoVoz(postpago);
        planes.setPlanCorporativo(corporativo);
        
        String restriccionesClausulaWhere = daoPlanesMasEscogidos.prepararRestriccionesClausulaWherePlanes(planes);
        ArrayList<Integer> conteoPlanes = daoPlanesMasEscogidos.listaPlanes(restriccionesClausulaWhere);
        
        return conteoPlanes;
        
    }

    public void desconectar() {
        daoPlanesMasEscogidos.desconectar();
    }
}
