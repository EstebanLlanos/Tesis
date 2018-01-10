/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import ConectorBD.ConexionBD;
import Logico.TendenciaPlanVoz;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Olaya O
 */
public class DaoTendenciaPlanVoz {

    ConexionBD conexionBd;

    public DaoTendenciaPlanVoz() {
        conexionBd = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWherePerfiles(TendenciaPlanVoz tendencia) {

        String where = "";

        int rango = tendencia.getAnioFin() - tendencia.getAnioInicio();
        for (int i = tendencia.getAnioInicio(); i <= tendencia.getAnioFin(); i++) {
            if (i == tendencia.getAnioFin()) {
                where += "bodega.fecha.anio = " + i;
            } else {
                where += "bodega.fecha.anio = " + i + " OR ";
            }
        }

        System.out.println("Este es el where: WHERE " + where);

        return where;
    }

    public ArrayList<String[]> listaPerfiles(String where) {

        ArrayList<String[]> resultado = new ArrayList<String[]>();

        Statement sentencia;
        Connection connection = conexionBd.conectar();
        ResultSet resultSet;
        System.out.println("----------Inicia Consulta");

        try {

            sentencia = connection.createStatement();
            String consulta = "SELECT bodega.llamada.fecha, bodega.fecha.anio FROM bodega.llamada,  bodega.demografia, bodega.fecha\n"
                    + "WHERE llamada.demografia = demografia.sk_demografia"
                    + " AND llamada.fecha = fecha.sk_fecha and(" + where + ");";

            System.out.println("Consulta: " + consulta);

            resultSet = sentencia.executeQuery(consulta);

            while (resultSet.next()) {

                String temp[] = new String[9];
                temp[0] = "" + resultSet.getObject(1);
                temp[1] = "" + resultSet.getObject(2);
                /*temp[2] = "" + resultSet.getObject(3);
                 temp[3] = "" + resultSet.getObject(4);
                 temp[4] = "" + resultSet.getObject(5);
                 temp[5] = "" + resultSet.getObject(6);
                 temp[6] = "" + resultSet.getObject(7);
                 temp[7] = "" + resultSet.getObject(8);
                 temp[8] = "" + resultSet.getObject(9);*/

                resultado.add(temp);
            }
            System.out.println("----------Termina Consulta\n");
            resultSet.close();
            connection.close();

        } catch (SQLException exp) {
            JOptionPane.showMessageDialog(null, exp.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al realizar la consulta");
        }

        return resultado;
    }

}
