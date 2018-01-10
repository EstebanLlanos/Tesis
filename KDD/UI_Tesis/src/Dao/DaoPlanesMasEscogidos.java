/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import ConectorBD.ConexionBD;
import Logico.PlanesMasEscogidos;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DaoPlanesMasEscogidos {

    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public DaoPlanesMasEscogidos() {
        BaseDeDatos = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWherePlanes(PlanesMasEscogidos planes) {

        String where = "";

        //TODOS
        if (planes.getPlanDeDatos().equals("true") && planes.getPlanPostpagoVoz().equals("true")
                && planes.getPlanPrepagoVoz().equals("true") && planes.getPlanCorporativo().equals("true")) {

            where = "";

        } //TODOS
        else if (planes.getPlanDeDatos().equals("false") && planes.getPlanPostpagoVoz().equals("false")
                && planes.getPlanPrepagoVoz().equals("false") && planes.getPlanCorporativo().equals("false")) {

            where = "";

        } //Sólo Datos
        else if (planes.getPlanDeDatos().equals("true") && planes.getPlanPostpagoVoz().equals("false")
                && planes.getPlanPrepagoVoz().equals("false") && planes.getPlanCorporativo().equals("false")) {

            where = " AND venta.plan_datos != 2";
        } //Sólo Postpago
        else if (planes.getPlanDeDatos().equals("false") && planes.getPlanPostpagoVoz().equals("true")
                && planes.getPlanPrepagoVoz().equals("false") && planes.getPlanCorporativo().equals("false")) {

            where = " AND venta.plan_datos = 2 AND venta.plan_voz != 1";
        } //Sólo Corporativo
        else if (planes.getPlanDeDatos().equals("false") && planes.getPlanPostpagoVoz().equals("false")
                && planes.getPlanPrepagoVoz().equals("false") && planes.getPlanCorporativo().equals("true")) {

            where = " AND bodega.plan_voz.es_corporativo = 'SI'";
        } //Sólo Prepago y Postpago sin corporativos
        else if (planes.getPlanPostpagoVoz().equals("true") && planes.getPlanPrepagoVoz().equals("true")
                && planes.getPlanCorporativo().equals("false")) {

            where = " AND venta.plan_datos = 2 AND bodega.plan_voz.es_corporativo = 'NO'";
        } //Todos los de Voz
        else if (planes.getPlanPostpagoVoz().equals("true") && planes.getPlanPrepagoVoz().equals("true")
                && planes.getPlanCorporativo().equals("true")) {

            where = " AND venta.plan_datos = 2 AND bodega.plan_voz.es_corporativo = 'SI'";
        } //Sólo Postpago y Corporativo
        else if (planes.getPlanPostpagoVoz().equals("true") && planes.getPlanPrepagoVoz().equals("false")
                && planes.getPlanCorporativo().equals("true")) {

            where = " AND bodega.plan_voz.es_corporativo = 'SI'";
        }
        return where;
    }

    public ArrayList<Integer> listaPlanes(String where) {

        int sinVoz = 0;
        int vozCasual = 0;
        int vozEstandar = 0;
        int vozPremium = 0;
        int vozFamilia = 0;
        int vozGold = 0;
        int vozTotal_1600 = 0;
        int vozTotal_600 = 0;
        int vozCasualAbierto = 0;
        int vozEstandarAbierto = 0;
        int vozPremiumAbierto = 0;
        int vozFamiliaAbierto = 0;
        int vozGoldAbierto = 0;
        int vozTotal_1600Abierto = 0;
        int vozTotal_600Abierto = 0;
        int vozEstandarCorporativo = 0;
        int vozPremiumCorporativo = 0;
        int vozGoldCorporativo = 0;

        int sinDatos = 0;
        int datos_5Megas = 0;
        int datos_50Megas = 0;
        int datos_100Megas = 0;
        int datos_1Giga = 0;
        int datosIlimitado = 0;
        int datosEstandarXKb = 0;
        int datosPremiumXKb = 0;
        int datosGoldXKb = 0;

        int prepago = 0;
        int postpago = 0;

        int corporativos = 0;
        int datos = 0;
        int voz = 0;

        ArrayList<Integer> conteoPlanes = new ArrayList<Integer>();
        String sql_select;
        sql_select = "SELECT bodega.venta.plan_datos, bodega.venta.plan_voz, "
                + "bodega.plan_voz.tipo_plan_voz, bodega.plan_voz.es_corporativo FROM bodega.venta, "
                + "bodega.plan_datos, bodega.plan_voz WHERE venta.plan_datos = plan_datos.sk_plan_datos "
                + "AND venta.plan_voz = plan_voz.sk_plan_voz " + where + ";";
        try {
            conn = BaseDeDatos.conectar();
            System.out.println("Conexión establecida");
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);

            while (tabla.next()) {

                if (tabla.getObject(1).toString().equalsIgnoreCase("2")) {
                    sinDatos++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("3")) {
                    datos_5Megas++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("4")) {
                    datos_50Megas++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("5")) {
                    datos_100Megas++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("6")) {
                    datos_1Giga++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("7")) {
                    datosIlimitado++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("8")) {
                    datosEstandarXKb++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("9")) {
                    datosPremiumXKb++;
                } else if (tabla.getObject(1).toString().equalsIgnoreCase("10")) {
                    datosGoldXKb++;
                }

                if (tabla.getObject(2).toString().equalsIgnoreCase("1")) {
                    sinVoz++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("2")) {
                    vozCasual++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("3")) {
                    vozEstandar++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("4")) {
                    vozPremium++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("5")) {
                    vozFamilia++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("6")) {
                    vozGold++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("7")) {
                    vozTotal_1600++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("8")) {
                    vozTotal_600++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("9")) {
                    vozCasualAbierto++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("10")) {
                    vozEstandarAbierto++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("11")) {
                    vozPremiumAbierto++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("12")) {
                    vozFamiliaAbierto++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("13")) {
                    vozGoldAbierto++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("14")) {
                    vozTotal_1600Abierto++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("15")) {
                    vozTotal_600Abierto++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("16")) {
                    vozEstandarCorporativo++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("17")) {
                    vozPremiumCorporativo++;
                } else if (tabla.getObject(2).toString().equalsIgnoreCase("18")) {
                    vozGoldCorporativo++;
                }

                if (tabla.getObject(3).toString().equalsIgnoreCase("prepago")) {
                    prepago++;
                } else {
                    postpago++;
                }

                if (tabla.getObject(4).toString().equalsIgnoreCase("SI")) {
                    corporativos++;
                }

                if (!tabla.getObject(1).toString().equalsIgnoreCase("2")) {
                    datos++;
                }

                if (!tabla.getObject(2).toString().equalsIgnoreCase("1")) {
                    voz++;
                }
                
            }

            if (where.equalsIgnoreCase("")) {

                conteoPlanes.add(datos);
                conteoPlanes.add(voz);
                conteoPlanes.add(1);
                return conteoPlanes;

            } else if (where.equalsIgnoreCase(" AND venta.plan_datos != 2")) {

                conteoPlanes.add(datos_5Megas);
                conteoPlanes.add(datos_50Megas);
                conteoPlanes.add(datos_100Megas);
                conteoPlanes.add(datos_1Giga);
                conteoPlanes.add(datosIlimitado);
                conteoPlanes.add(datosEstandarXKb);
                conteoPlanes.add(datosPremiumXKb);
                conteoPlanes.add(datosGoldXKb);
                conteoPlanes.add(2);
                return conteoPlanes;

            } else if (where.equalsIgnoreCase(" AND venta.plan_datos = 2 AND venta.plan_voz != 1")) {
                conteoPlanes.add(vozCasual);
                conteoPlanes.add(vozEstandar);
                conteoPlanes.add(vozPremium);
                conteoPlanes.add(vozFamilia);
                conteoPlanes.add(vozGold);
                conteoPlanes.add(vozTotal_1600);
                conteoPlanes.add(vozTotal_600);
                conteoPlanes.add(vozCasualAbierto);
                conteoPlanes.add(vozEstandarAbierto);
                conteoPlanes.add(vozPremiumAbierto);
                conteoPlanes.add(vozFamiliaAbierto);
                conteoPlanes.add(vozGoldAbierto);
                conteoPlanes.add(vozTotal_1600Abierto);
                conteoPlanes.add(vozTotal_600Abierto);
                conteoPlanes.add(3);
                return conteoPlanes;
            } else if (where.equalsIgnoreCase(" AND bodega.plan_voz.es_corporativo = 'SI'")) {
                conteoPlanes.add(vozEstandarCorporativo);
                conteoPlanes.add(vozPremiumCorporativo);
                conteoPlanes.add(vozGoldCorporativo);
                conteoPlanes.add(4);
                return conteoPlanes;
            } else if (where.equalsIgnoreCase(" AND venta.plan_datos = 2 AND bodega.plan_voz.es_corporativo = 'NO'")) {
                conteoPlanes.add(prepago);
                conteoPlanes.add(postpago);
                conteoPlanes.add(5);
                return conteoPlanes;
            } else if (where.equalsIgnoreCase(" AND venta.plan_datos = 2 AND bodega.plan_voz.es_corporativo = 'SI'")) {
                conteoPlanes.add(sinVoz);
                conteoPlanes.add(vozCasual);
                conteoPlanes.add(vozEstandar);
                conteoPlanes.add(vozPremium);
                conteoPlanes.add(vozFamilia);
                conteoPlanes.add(vozGold);
                conteoPlanes.add(vozTotal_1600);
                conteoPlanes.add(vozTotal_600);
                conteoPlanes.add(vozCasualAbierto);
                conteoPlanes.add(vozEstandarAbierto);
                conteoPlanes.add(vozPremiumAbierto);
                conteoPlanes.add(vozFamiliaAbierto);
                conteoPlanes.add(vozGoldAbierto);
                conteoPlanes.add(vozTotal_1600Abierto);
                conteoPlanes.add(vozTotal_600Abierto);
                conteoPlanes.add(vozEstandarCorporativo);
                conteoPlanes.add(vozPremiumCorporativo);
                conteoPlanes.add(vozGoldCorporativo);
                conteoPlanes.add(6);
                return conteoPlanes;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }

    public void desconectar() {
        try {
            rsCandidato.close();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
