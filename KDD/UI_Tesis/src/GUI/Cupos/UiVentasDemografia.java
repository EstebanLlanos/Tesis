/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Cupos;

import ConectorBD.ConexionBD;
import Controlador.Cupos.ControladorVentasDemografia;
import GUI.Visualizador;
import Gráficos.FXBarChart;
import Gráficos.FXLineChart;
import Gráficos.FXPieChart;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Esteban
 */

public class UiVentasDemografia {
    
    JComboBox comboBoxGenero, comboBoxEstrato, comboBoxEdad, comboBoxIngreso, comboBoxAnioInicio, comboBoxAnioFin, comboBoxCriterioConsulta, comboBoxMesInicio, comboBoxMesFin;
    JLabel labelGenero, labelEstrato, labelEdad, labelIngreso, labelAnioInicio, labelAnioFin, labelCriterioConsulta, labelMesInicio, labelMesFin;
    JLabel separadorBoton;
    JButton botonConsultar;
    
    // Clases para el despligue Gráfico de resultados

    FXPieChart PieChart;
    FXBarChart BarChart;
    FXLineChart LineChart;
    
    ControladorVentasDemografia controladorVentasDemografia;
    
    // Elementos de conexion de la BD para el llenado de los comboBox
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public UiVentasDemografia() {

        controladorVentasDemografia = new ControladorVentasDemografia();
        
        labelGenero = new JLabel();
        inicializarJLabel(labelGenero, "Genero:");

        comboBoxGenero = new JComboBox();
        comboBoxGenero.setToolTipText("Seleccione un genero para ver\n"
                + " las ciudades con mayor o menor número de ventas, según el genero\n"
                + " seleccionado.");
        
        labelEstrato = new JLabel();
        inicializarJLabel(labelEstrato, "Estrato:");
        
        comboBoxEstrato = new JComboBox();
        comboBoxEstrato.setToolTipText("Seleccione un estrato para conocer las\n"
                + " ciudades con mayor o menor número de ventas, según el\n"
                + " criterio de consulta que haya definido.");
        
        labelEdad = new JLabel();
        inicializarJLabel(labelEdad, "Edad:");
        
        comboBoxEdad = new JComboBox();
        comboBoxEdad.setToolTipText("Seleccione un rango de edad para conocer las\n"
                + " ciudades con mayor o menor número de ventas, según el\n"
                + " criterio de consulta que haya definido.");
        
        labelIngreso = new JLabel();
        inicializarJLabel(labelIngreso, "Ingresos:");
        
        comboBoxIngreso = new JComboBox();
        comboBoxIngreso.setToolTipText("Seleccione un rango de ingress para conocer las\n"
                + " ciudades con mayor o menor número de ventas, según el\n"
                + " criterio de consulta que haya definido.");
        
        labelAnioInicio = new JLabel();
        inicializarJLabel(labelAnioInicio, "Desde el Año:");
        
        comboBoxAnioInicio = new JComboBox();
        comboBoxAnioInicio.setToolTipText("Seleccione un año inicial para conocer las\n"
                + " ciudades con mayor o menor número de ventas a partir de este, según\n"
                + " el criterio de consulta que haya definido.");
        
        comboBoxAnioInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (!comboBoxAnioInicio.getSelectedItem().equals("Escoger una Opción...")) {
                    comboBoxMesInicio.setEnabled(true);
                    comboBoxMesFin.setEnabled(true);
                } else {
                    comboBoxMesInicio.setEnabled(false);
                    comboBoxMesFin.setEnabled(false);
                }
            }
        });
        
        labelAnioFin = new JLabel();
        inicializarJLabel(labelAnioFin, "Hasta el Año:");
        
        labelCriterioConsulta = new JLabel();
        inicializarJLabel(labelCriterioConsulta, "Criterio de Consulta:");
        
        comboBoxAnioFin = new JComboBox();
        comboBoxAnioFin.setToolTipText("Seleccione un año final para completar \n"
                + " el rango dentro del cual desea conocer las ciudades con mayor\n"
                + " o menor número de ventas, según el criterio que haya definido.");
        
        comboBoxCriterioConsulta = new JComboBox();
        comboBoxCriterioConsulta.setToolTipText("Seleccione en este menú el criterio bajo\n"
                + " el cual desea consultar la información disponible.");

        botonConsultar = new JButton("Consultar");
        botonConsultar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        botonConsultar.setPreferredSize(new Dimension(120, 30));
        
        separadorBoton = new JLabel();
        inicializarJLabel(separadorBoton, "≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡"
                                            + "≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡");
        separadorBoton.setFont(new java.awt.Font("Century Gothic", 1, 6));
        
        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("Se realiza la consulta");
                hacerConsulta(evt);
            }
        });
        
        labelMesInicio = new JLabel();
        inicializarJLabel(labelMesInicio, "Desde el Mes:");
        
        comboBoxMesInicio = new JComboBox();
        comboBoxMesInicio.setToolTipText("Seleccione un mes inicial dentro del\n"
                + " año seleccionado para conocer las ciudades con mayor o menor\n"
                + " número de ventas a partir de este, según el criterio de consulta\n"
                + " que haya definido.");
        
        labelMesFin = new JLabel();
        inicializarJLabel(labelMesFin, "Hasta el Mes:");
        
        comboBoxMesFin = new JComboBox();
        comboBoxMesFin.setToolTipText("Seleccione un mes final para completar \n"
                + " el rango dentro del cual desea conocer las ciudades con mayor\n"
                + " o menor número de ventas, según el criterio que haya definido.");
        
        inicializarGeneros(comboBoxGenero);
        inicializarEstratos(comboBoxEstrato);
        inicializarEdades(comboBoxEdad);
        inicializarIngresos(comboBoxIngreso);
        inicializarAnios(comboBoxAnioInicio);
        inicializarAnios(comboBoxAnioFin);
        inicializarMeses(comboBoxMesInicio);
        inicializarMeses(comboBoxMesFin);
        inicializarCriteriosDeConsulta(comboBoxCriterioConsulta);
    }

    void hacerConsulta(ActionEvent evt) {
        
        //verificamos que el rango de estrato sea correcto

        String genero = "" + comboBoxGenero.getSelectedItem();
        String estrato = "" + comboBoxEstrato.getSelectedItem();
        String edad = "" + comboBoxEdad.getSelectedItem();
        String ingresos = "" + comboBoxIngreso.getSelectedItem();
        String anioInicio = "" + comboBoxAnioInicio.getSelectedItem();
        String mesInicio = "" + comboBoxMesInicio.getSelectedItem();
        String mesFin = "" + comboBoxMesFin.getSelectedItem();
        String anioFin = "" + comboBoxAnioFin.getSelectedItem();
        String criterioConsulta = "" + comboBoxCriterioConsulta.getSelectedItem();

        String tituloGraficos = "";
        
        if (!genero.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem();
        }
        
        if (!estrato.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem();
        }
        
        if (!estrato.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem();
        }
        
        if (!edad.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Edad " + comboBoxEdad.getSelectedItem();
        }
        
        if (!edad.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Edad " + comboBoxEdad.getSelectedItem();
        }
        
        if (!ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Ingresos \n                                     " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Ingresos \n                                     " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " y el Estrato " + comboBoxEstrato.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " y el Estrato " + comboBoxEstrato.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " y la Edad " + comboBoxEdad.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " y la Edad " + comboBoxEdad.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " y los Ingresos \n                                     " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " y los Ingresos \n                                     " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + " y la Edad " + comboBoxEdad.getSelectedItem();
        }
        
        if (!estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + " y la Edad " + comboBoxEdad.getSelectedItem();
        }
        
        if (!estrato.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + " y los Ingresos \n                                      " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!estrato.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + " y los Ingresos \n                                    " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!estrato.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!estrato.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para la Edad " + comboBoxEdad.getSelectedItem() + " y los Ingresos \n                                       " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para la Edad " + comboBoxEdad.getSelectedItem() + " y los Ingresos \n                                       " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!edad.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para la Edad " + comboBoxEdad.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!edad.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para la Edad " + comboBoxEdad.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para los Ingresos \n                                    " + comboBoxIngreso.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para los Ingresos \n                                    " + comboBoxIngreso.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", el Estrato " + comboBoxEstrato.getSelectedItem() + "\n                                    y la Edad " + comboBoxEdad.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", el Estrato " + comboBoxEstrato.getSelectedItem() + "\n                                      y la Edad " + comboBoxEdad.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", el Estrato " + comboBoxEstrato.getSelectedItem() + "\n                                    y los Ingresos " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", el Estrato " + comboBoxEstrato.getSelectedItem() + "\n                                   y los Ingresos " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " y el Estrato " + comboBoxEstrato.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " y el Estrato " + comboBoxEstrato.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", la Edad " + comboBoxEdad.getSelectedItem() + "\n                                          y los Ingresos " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", la Edad " + comboBoxEdad.getSelectedItem() + "\n                                          y los Ingresos " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " y la Edad " + comboBoxEdad.getSelectedItem() + "\n                               en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + " y la Edad " + comboBoxEdad.getSelectedItem() + "\n                            en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + "\n                                    y los Ingresos " + comboBoxIngreso.getSelectedItem() + "\n                               en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + "\n                                  y los Ingresos " + comboBoxIngreso.getSelectedItem() + "\n                                      en el periodo seleccionado";
        }
        
        if (!estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + ", la Edad " + comboBoxEdad.getSelectedItem() + "\n                                     y los Ingresos " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + ", la Edad " + comboBoxEdad.getSelectedItem() + "\n                                    y los Ingresos " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + " y la Edad " + comboBoxEdad.getSelectedItem() + "\n                                  en el periodo seleccionado";
        }
        
        if (!estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + " y la Edad " + comboBoxEdad.getSelectedItem() + "\n                                   en el periodo seleccionado";
        }
        
        if (!edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para la Edad " + comboBoxEstrato.getSelectedItem() + "\n                                 y los Ingresos " + comboBoxIngreso.getSelectedItem() + "\n                                          en el periodo seleccionado";
        }
        
        if (!edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para la Edad " + comboBoxEstrato.getSelectedItem() + "\n                             y los Ingresos " + comboBoxIngreso.getSelectedItem() + "\n                                      en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", el Estrato " + comboBoxEstrato.getSelectedItem() + ", la Edad \n                                   " + comboBoxEdad.getSelectedItem() + " y los Ingresos " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", el Estrato " + comboBoxEstrato.getSelectedItem() + ", la Edad \n                                    " + comboBoxEdad.getSelectedItem() + " y los Ingresos " + comboBoxIngreso.getSelectedItem();
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", el Estrato " + comboBoxEstrato.getSelectedItem() + " y la Edad \n                                   " + comboBoxEdad.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", el Estrato " + comboBoxEstrato.getSelectedItem() + " y la Edad \n                                  " + comboBoxEdad.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", la Edad " + comboBoxEdad.getSelectedItem() + " y los Ingresos \n                                      " + comboBoxIngreso.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...")  && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", la Edad " + comboBoxEdad.getSelectedItem() + " y los Ingresos \n                                    " + comboBoxIngreso.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + ", la Edad " + comboBoxEdad.getSelectedItem() + " y los Ingresos \n                                 " + comboBoxIngreso.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...")  && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Estrato " + comboBoxEstrato.getSelectedItem() + ", la Edad " + comboBoxEdad.getSelectedItem() + " y los Ingresos \n                                    " + comboBoxIngreso.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", el Estrato " + comboBoxEstrato.getSelectedItem() + ", la Edad \n                                    " + comboBoxEdad.getSelectedItem() + " y los Ingresos " + comboBoxIngreso.getSelectedItem() + "\n                                                                       en el periodo seleccionado";
        }
        
        if (!genero.equals("Escoger una Opción...") && !estrato.equals("Escoger una Opción...") && !edad.equals("Escoger una Opción...") && !ingresos.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...")  && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Género " + comboBoxGenero.getSelectedItem() + ", el Estrato " + comboBoxEstrato.getSelectedItem() + ", la Edad \n                                 " + comboBoxEdad.getSelectedItem() + " y los Ingresos " + comboBoxIngreso.getSelectedItem() + "\n                                                                          en el periodo seleccionado";
        }
        
        ArrayList <String[]> ventasPorDemografia = controladorVentasDemografia.getVentas(genero, estrato, edad, ingresos, anioInicio, mesInicio, mesFin, anioFin, criterioConsulta);

        if (ventasPorDemografia.isEmpty()) {            
            JOptionPane.showMessageDialog(null, "Esta consulta no entregó resultados. "
                    + "No existen registros que coincidan con los filtros solicitados");
            
        } else if(ventasPorDemografia.get(0)[0].equals("Error")){            
            JOptionPane.showMessageDialog(null, "Debe seleccionar uno de los filtros disponibles para realizar la consulta");
        } else if(ventasPorDemografia.get(0)[0].equals("Error Fecha Año")){            
            JOptionPane.showMessageDialog(null, "La consulta no pudo ser realizada. Seleccione fecha de Inicio unicamente o un rango válido a consultar");
        } else if(ventasPorDemografia.get(0)[0].equals("Error Fecha Mes")){            
            JOptionPane.showMessageDialog(null, "La consulta no pudo ser realizada. Seleccione un rango de meses válido a consultar");
        }else {
            try{ ArrayList<String> ciudades = new ArrayList();
                ArrayList<Integer> ventas = new ArrayList();
                for (int i = 0; i <= ventasPorDemografia.size() - 1; i++) {
                    ciudades.add(ventasPorDemografia.get(i)[0]);
                    ventas.add(Integer.parseInt(ventasPorDemografia.get(i)[1]));
                }

                if (!ventasPorDemografia.isEmpty()) {                    
                    PieChart = new FXPieChart(tituloGraficos, ciudades, ventas);
                    BarChart = new FXBarChart(tituloGraficos, "Ciudades", ciudades, "Ventas", ventas, "Ventas Realizadas");
                    LineChart = new FXLineChart(tituloGraficos, "Ciudades", ciudades, "Ventas", ventas, "Ventas Realizadas");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha extraido la información");
                }
            } catch(ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null, "La consulta no puede ser realizada. "
                    + "Seleccione un rango válido a consultar");
            }
        }
    }
    
    protected void inicializarJLabel(JLabel label, String texto) {

        label.setText(texto);
        label.setSize(170, 30);
        label.setMinimumSize(new Dimension(170, 30));
        label.setMaximumSize(new Dimension(170, 30));
        label.setFont(new java.awt.Font("Century Gothic", 1, 14));
        label.setForeground(Color.WHITE);
    }
    
    protected void inicializarCriteriosDeConsulta(JComboBox criteriosDeConsulta){
        
        criteriosDeConsulta.setVisible(true);
        criteriosDeConsulta.setMaximumSize(new Dimension(200, 30));

        String meses[][] = new String[2][1];
        meses[0][0] = "Mayor Número de Ventas";
        meses[1][0] = "Menor Número de Ventas";

        for (int i = 0; i < meses.length; i++) {
            criteriosDeConsulta.addItem(meses[i][0]);
        }
    
    }
    
    protected void inicializarMeses(JComboBox entradaMeses) {

        entradaMeses.setVisible(true);
        entradaMeses.setMaximumSize(new Dimension(230, 30));

        String meses[][] = new String[13][1];
        meses[0][0] = "Escoger una Opción...";
        meses[1][0] = "Enero";
        meses[2][0] = "Febrero";
        meses[3][0] = "Marzo";
        meses[4][0] = "Abril";
        meses[5][0] = "Mayo";
        meses[6][0] = "Junio";
        meses[7][0] = "Julio";
        meses[8][0] = "Agosto";
        meses[9][0] = "Septiembre";
        meses[10][0] = "Octubre";
        meses[11][0] = "Noviembre";
        meses[12][0] = "Diciembre";

        for (int i = 0; i < meses.length; i++) {
            entradaMeses.addItem(meses[i][0]);
        }
    }

    protected void inicializarGeneros(JComboBox generosVentas){
    
        generosVentas.setVisible(true);
        generosVentas.setMaximumSize(new Dimension(250, 30));
    
        String[] generos = new String[3];
        
        generos[0] = "Escoger una Opción...";
        generos[1] = "Femenino";
        generos[2] = "Masculino";
        
        for (int i = 0; i < generos.length; i++) {
            generosVentas.addItem(generos[i]);
        }
        
    }
    
    protected void inicializarEstratos(JComboBox estratosVentas){
    
        estratosVentas.setVisible(true);
        estratosVentas.setMaximumSize(new Dimension(250, 30));
    
        String[] estratos = new String[7];
        
        estratos[0] = "Escoger una Opción...";
        estratos[1] = "1";
        estratos[2] = "2";
        estratos[3] = "3";
        estratos[4] = "4";
        estratos[5] = "5";
        estratos[6] = "6";
        
        for (int i = 0; i < estratos.length; i++) {
            estratosVentas.addItem(estratos[i]);
        }
        
    }
    
    protected void inicializarEdades(JComboBox edadesVentas){
    
        edadesVentas.setVisible(true);
        edadesVentas.setMaximumSize(new Dimension(250, 30));
    
        String[] edades = new String[7];
        
        edades[0] = "Escoger una Opción...";
        edades[1] = "Menor a 18 años";
        edades[2] = "De 18 a 25 años";
        edades[3] = "De 26 a 40 años";
        edades[4] = "De 41 a 50 años";
        edades[5] = "De 51 a 60 años";
        edades[6] = "Mayor a 60 años";
        
        for (int i = 0; i < edades.length; i++) {
            edadesVentas.addItem(edades[i]);
        }
        
    }
    
    protected void inicializarIngresos(JComboBox ingresosVentas){
    
        ingresosVentas.setVisible(true);
        ingresosVentas.setMaximumSize(new Dimension(250, 30));
    
        String[] ingresos = new String[6];
        
        ingresos[0] = "Escoger una Opción...";
        ingresos[1] = "Menor a 1.000.000 de pesos";
        ingresos[2] = "De 1.000.001 a 2.000.000 de pesos";
        ingresos[3] = "De 2.000.001 a 4.000.000 de pesos";
        ingresos[4] = "De 4.000.001 a 5.000.000 de pesos";
        ingresos[5] = "Mayor a 5.000.000 de pesos";
        
        for (int i = 0; i < ingresos.length; i++) {
            ingresosVentas.addItem(ingresos[i]);
        }
        
    }
    
    protected void inicializarAnios(JComboBox anioVentas) {

        anioVentas.setVisible(true);
        anioVentas.setMaximumSize(new Dimension(250, 30));

        String[][] anios = new String[8][1];
        anios[0][0] = "Escoger una Opción...";
        anios[1][0] = "2012";
        anios[2][0] = "2013";
        anios[3][0] = "2014";
        anios[4][0] = "2015";
        anios[5][0] = "2016";
        anios[6][0] = "2017";
        anios[7][0] = "2018";

        for (int i = 0; i < anios.length; i++) {
            anioVentas.addItem(anios[i][0]);
        }
    }
    
    public int obtenerCodigoEdad(String edad){
        
        int codigoEdad = 0;
        
        switch (edad) {
            case "Menor a 18 años":
                codigoEdad = 1;
                break;
            case "De 18 a 25 años":
                codigoEdad = 2;
                break;
            case "De 26 a 40 años":
                codigoEdad = 3;
                break;
            case "De 41 a 50 años":
                codigoEdad = 4;
                break;
            case "De 51 a 60 años":
                codigoEdad = 5;
                break;
            case "Mayor a 60 años":
                codigoEdad = 6;
                break;
        }
        
        return codigoEdad;
    }
    
    public int obtenerCodigoIngreso(String ingreso){
        
        int codigoIngresos = 0;
        
        switch (ingreso) {
            case "Menor a 1.000.000 de pesos":
                codigoIngresos = 1;
                break;
            case "De 1.000.001 a 2.000.000 de pesos":
                codigoIngresos = 2;
                break;
            case "De 2.000.001 a 4.000.000 de pesos":
                codigoIngresos = 3;
                break;
            case "De 4.000.001 a 5.000.000 de pesos":
                codigoIngresos = 4;
                break;
            case "Mayor a 5.000.000 de pesos":
                codigoIngresos = 5;
                break;
        }
        
        return codigoIngresos;
    }
    
    public String obtenerGenero(String genero){
        
        String codigoGenero = "";
        
        switch (genero) {
            case "Femenino":
                codigoGenero = "F";
                break;
            case "Masculino":
                codigoGenero = "M";
                break;
        }
        
        return codigoGenero;
    }
    
    public void generarReporte(){
    
        JasperReport report;
        JasperPrint jasperPrint;
        
        ConexionBD bd = new ConexionBD();
        Connection conn = bd.conectar();

        try {

            Map<String, Object> parametros = new HashMap();
            
            System.out.println("Genero: " + new String(obtenerGenero(comboBoxGenero.getSelectedItem().toString() + "")).getClass());
            parametros.put("genero_demografia", new String(obtenerGenero(comboBoxGenero.getSelectedItem().toString())));
            
            System.out.println("Estrato: " + new Long(comboBoxEstrato.getSelectedItem().toString() + "").getClass());
            parametros.put("estrato_demografia", new Long(comboBoxEstrato.getSelectedItem().toString() + ""));
            
            System.out.println("Edad: " +  new Long(obtenerCodigoEdad(comboBoxEdad.getSelectedItem().toString() + "")).getClass());
            parametros.put("edad_demografia", new Long(obtenerCodigoEdad(comboBoxEdad.getSelectedItem().toString()) + ""));
            
            System.out.println("Ingresos: " + new Long(obtenerCodigoIngreso(comboBoxIngreso.getSelectedItem().toString() + "")).getClass());
            parametros.put("ingresos_demografia", new Long(obtenerCodigoIngreso(comboBoxIngreso.getSelectedItem().toString()) + ""));

            if (comboBoxAnioInicio.getSelectedItem().toString().equals("Escoger una Opción...")) {
                parametros.put("id_fecha_inicio", new Long("20120101"));
            } else {
                System.out.println("id_fecha_inicio: " + comboBoxAnioInicio.getSelectedItem().toString() + "0101");
                parametros.put("id_fecha_inicio", new Long(comboBoxAnioInicio.getSelectedItem().toString() + "0101"));
            }

            if (comboBoxAnioFin.getSelectedItem().toString().equals("Escoger una Opción...")) {
                if (comboBoxAnioInicio.getSelectedItem().toString().equals("Escoger una Opción...")) {
                    parametros.put("id_fecha_fin", new Long("20121201"));   
                } else {
                    System.out.println("id_fecha_fin: " + comboBoxAnioInicio.getSelectedItem().toString() + "1201");
                    parametros.put("id_fecha_fin", new Long(comboBoxAnioInicio.getSelectedItem().toString() + "1201"));   
                }
                
            } else {
                System.out.println("id_fecha_fin: " + comboBoxAnioFin.getSelectedItem().toString() + "1201");
                parametros.put("id_fecha_fin", new Long(comboBoxAnioFin.getSelectedItem().toString() + "1201"));
            }

            report = (JasperReport) JRLoader.loadObjectFromFile(System.getProperty("user.dir") + "\\src\\Reportes\\Cupos\\ReporteVentasDemografia.jasper");
            jasperPrint = JasperFillManager.fillReport(report, parametros, conn);

            JFrame frame = new JFrame("Reporte Venta de Cupos por Perfil Demográfico");
            frame.setPreferredSize(new Dimension(1000, 600));
            frame.getContentPane().add(new JRViewer(jasperPrint));
            frame.pack();
            frame.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Visualizador.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    public JComboBox getComboBoxGenero() {
        return comboBoxGenero;
    }

    public void setComboBoxGenero(JComboBox comboBoxGenero) {
        this.comboBoxGenero = comboBoxGenero;
    }

    public JComboBox getComboBoxEstrato() {
        return comboBoxEstrato;
    }

    public void setComboBoxEstrato(JComboBox comboBoxEstrato) {
        this.comboBoxEstrato = comboBoxEstrato;
    }

    public JComboBox getComboBoxEdad() {
        return comboBoxEdad;
    }

    public void setComboBoxEdad(JComboBox comboBoxEdad) {
        this.comboBoxEdad = comboBoxEdad;
    }

    public JComboBox getComboBoxIngreso() {
        return comboBoxIngreso;
    }

    public void setComboBoxIngreso(JComboBox comboBoxIngreso) {
        this.comboBoxIngreso = comboBoxIngreso;
    }

    public JLabel getLabelGenero() {
        return labelGenero;
    }

    public void setLabelGenero(JLabel labelGenero) {
        this.labelGenero = labelGenero;
    }

    public JLabel getLabelEstrato() {
        return labelEstrato;
    }

    public void setLabelEstrato(JLabel labelEstrato) {
        this.labelEstrato = labelEstrato;
    }

    public JLabel getLabelEdad() {
        return labelEdad;
    }

    public void setLabelEdad(JLabel labelEdad) {
        this.labelEdad = labelEdad;
    }

    public JLabel getLabelIngreso() {
        return labelIngreso;
    }

    public void setLabelIngreso(JLabel labelIngreso) {
        this.labelIngreso = labelIngreso;
    }

    public JButton getBotonConsultar() {
        return botonConsultar;
    }

    public void setBotonConsultar(JButton botonConsultar) {
        this.botonConsultar = botonConsultar;
    }

    public JComboBox getComboBoxAnioInicio() {
        return comboBoxAnioInicio;
    }

    public void setComboBoxAnioInicio(JComboBox comboBoxAnioInicio) {
        this.comboBoxAnioInicio = comboBoxAnioInicio;
    }

    public JComboBox getComboBoxAnioFin() {
        return comboBoxAnioFin;
    }

    public void setComboBoxAnioFin(JComboBox comboBoxAnioFin) {
        this.comboBoxAnioFin = comboBoxAnioFin;
    }

    public JLabel getLabelAnioInicio() {
        return labelAnioInicio;
    }

    public void setLabelAnioInicio(JLabel labelAnioInicio) {
        this.labelAnioInicio = labelAnioInicio;
    }

    public JLabel getLabelAnioFin() {
        return labelAnioFin;
    }

    public void setLabelAnioFin(JLabel labelAnioFin) {
        this.labelAnioFin = labelAnioFin;
    }

    public JComboBox getComboBoxCriterioConsulta() {
        return comboBoxCriterioConsulta;
    }

    public void setComboBoxCriterioConsulta(JComboBox comboBoxCriterioConsulta) {
        this.comboBoxCriterioConsulta = comboBoxCriterioConsulta;
    }

    public JLabel getLabelCriterioConsulta() {
        return labelCriterioConsulta;
    }

    public void setLabelCriterioConsulta(JLabel labelCriterioConsulta) {
        this.labelCriterioConsulta = labelCriterioConsulta;
    }

    public JComboBox getComboBoxMesInicio() {
        return comboBoxMesInicio;
    }

    public void setComboBoxMesInicio(JComboBox comboBoxMesInicio) {
        this.comboBoxMesInicio = comboBoxMesInicio;
    }

    public JComboBox getComboBoxMesFin() {
        return comboBoxMesFin;
    }

    public void setComboBoxMesFin(JComboBox comboBoxMesFin) {
        this.comboBoxMesFin = comboBoxMesFin;
    }

    public JLabel getLabelMesFin() {
        return labelMesFin;
    }

    public void setLabelMesFin(JLabel labelMesFin) {
        this.labelMesFin = labelMesFin;
    }

    public JLabel getLabelMesInicio() {
        return labelMesInicio;
    }

    public void setLabelMesInicio(JLabel labelMesInicio) {
        this.labelMesInicio = labelMesInicio;
    }

    public JLabel getSeparadorBoton() {
        return separadorBoton;
    }

    public void setSeparadorBoton(JLabel separadorBoton) {
        this.separadorBoton = separadorBoton;
    }
    
}
