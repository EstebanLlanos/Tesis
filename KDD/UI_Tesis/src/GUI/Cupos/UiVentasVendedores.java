/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Cupos;

import ConectorBD.ConexionBD;
import Controlador.Cupos.ControladorVentasVendedores;
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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import Recursos.AutoSuggestor;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
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

public class UiVentasVendedores {
    
    JComboBox comboBoxCiudades, comboBoxSedes, comboBoxAnioInicio, comboBoxAnioFin, comboBoxCriterioConsulta;
    JLabel labelCiudad, labelSede, labelAnioInicio, labelAnioFin, labelCriterioConsulta, labelVendedor, labelTipoConsulta, separadorBoton;
    JTextField textFieldVendedor;
    JCheckBox busquedaCodigo, busquedaNombre, busquedaOtrosCriterios;
    JButton botonConsultar;
    
    // Clases para el despligue Gráfico de resultados
    
    FXPieChart PieChart;
    FXBarChart BarChart;
    FXLineChart LineChart;
    
    AutoSuggestor autoCompletar;
    
    ControladorVentasVendedores controladorVentasVendedor;
    
    // Elementos de conexion de la BD para el llenado de los comboBox
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public UiVentasVendedores(JFrame ventanaPrincipal) {

        controladorVentasVendedor = new ControladorVentasVendedores();
        
        labelTipoConsulta = new JLabel();
        inicializarJLabel(labelTipoConsulta , "Tipo de Consulta a Realizar:                              ");
        
        busquedaCodigo = new JCheckBox("Buscar por código de Vendedor");
        busquedaCodigo.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        busquedaCodigo.setForeground(new java.awt.Color(230, 230, 255));
        busquedaCodigo.setSelected(false);
        
        busquedaCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (busquedaCodigo.isSelected()) {
                    
                    busquedaNombre.setSelected(false);
                    busquedaOtrosCriterios.setSelected(false);
                    textFieldVendedor.setEnabled(true);
                    
                    comboBoxCiudades.setEnabled(false);
                    comboBoxCiudades.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxAnioInicio.setEnabled(false);
                    comboBoxAnioInicio.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxAnioFin.setEnabled(false);
                    comboBoxAnioFin.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxSedes.setEnabled(false);
                    comboBoxSedes.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxCriterioConsulta.setEnabled(false);
                    comboBoxCriterioConsulta.setSelectedItem("Escoger una Opción...");
                } else {
                    busquedaOtrosCriterios.setSelected(true);
                    
                    textFieldVendedor.setEnabled(false);
                    textFieldVendedor.setText("");
                    
                    comboBoxCiudades.setEnabled(true);
                    comboBoxAnioInicio.setEnabled(true);
                    comboBoxAnioFin.setEnabled(true);
                    comboBoxSedes.setEnabled(true);
                    comboBoxCriterioConsulta.setEnabled(true);
                    comboBoxCriterioConsulta.setSelectedItem("Mayor Número de Ventas");
                }
            }
        });
        
        busquedaNombre = new JCheckBox("Buscar por nombre de Vendedor");
        busquedaNombre.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        busquedaNombre.setForeground(new java.awt.Color(230, 230, 255));
        busquedaNombre.setSelected(false);
        
        busquedaNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (busquedaNombre.isSelected()) {
                    
                    busquedaCodigo.setSelected(false);
                    busquedaOtrosCriterios.setSelected(false);
                    textFieldVendedor.setEnabled(true);
                    
                    comboBoxCiudades.setEnabled(false);
                    comboBoxCiudades.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxAnioInicio.setEnabled(false);
                    comboBoxAnioInicio.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxAnioFin.setEnabled(false);
                    comboBoxAnioFin.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxSedes.setEnabled(false);
                    comboBoxSedes.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxCriterioConsulta.setEnabled(false);
                    comboBoxCriterioConsulta.setSelectedItem("Escoger una Opción...");
                } else {
                    busquedaOtrosCriterios.setSelected(true);
                    
                    textFieldVendedor.setEnabled(false);
                    textFieldVendedor.setText("");
                    
                    comboBoxCiudades.setEnabled(true);
                    comboBoxAnioInicio.setEnabled(true);
                    comboBoxAnioFin.setEnabled(true);
                    comboBoxSedes.setEnabled(true);
                    comboBoxCriterioConsulta.setEnabled(true);
                    comboBoxCriterioConsulta.setSelectedItem("Mayor Número de Ventas");
                }
            }
        });
        
        busquedaOtrosCriterios = new JCheckBox("Buscar por otros criterios");
        busquedaOtrosCriterios.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        busquedaOtrosCriterios.setForeground(new java.awt.Color(230, 230, 255));
        busquedaOtrosCriterios.setSelected(true);
        
        busquedaOtrosCriterios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (busquedaOtrosCriterios.isSelected()) {
                    busquedaCodigo.setSelected(false);
                    busquedaNombre.setSelected(false);
                    
                    textFieldVendedor.setEnabled(false);
                    textFieldVendedor.setText("");
                    
                    comboBoxCiudades.setEnabled(true);
                    comboBoxAnioInicio.setEnabled(true);
                    comboBoxAnioFin.setEnabled(true);
                    comboBoxSedes.setEnabled(true);
                    comboBoxCriterioConsulta.setEnabled(true);
                    comboBoxCriterioConsulta.setSelectedItem("Mayor Número de Ventas");
                } else {
                    busquedaCodigo.setSelected(true);
                    textFieldVendedor.setEnabled(true);
                    
                    comboBoxCiudades.setEnabled(false);
                    comboBoxCiudades.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxAnioInicio.setEnabled(false);
                    comboBoxAnioInicio.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxAnioFin.setEnabled(false);
                    comboBoxAnioFin.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxSedes.setEnabled(false);
                    comboBoxSedes.setSelectedItem("Escoger una Opción...");
                    
                    comboBoxCriterioConsulta.setEnabled(false);
                    comboBoxCriterioConsulta.setSelectedItem("Escoger una Opción...");
                }
            }
        });
        
        labelVendedor = new JLabel();
        inicializarJLabel(labelVendedor, "Vendedor:                                   ");
        
        textFieldVendedor = new JTextField();
        textFieldVendedor.setPreferredSize(new Dimension(170, 30));
        textFieldVendedor.setEnabled(false);
        
        labelCiudad = new JLabel();
        inicializarJLabel(labelCiudad, "Ciudad:                        ");
        comboBoxCiudades = new JComboBox();
        
        labelSede = new JLabel();
        inicializarJLabel(labelSede, "Sede:                          ");
        
        labelAnioInicio = new JLabel();
        inicializarJLabel(labelAnioInicio, "Desde:                    ");
        
        comboBoxAnioInicio = new JComboBox();        
        
        labelAnioFin = new JLabel();
        inicializarJLabel(labelAnioFin, "Hasta:                         ");
        
        labelCriterioConsulta = new JLabel();
        inicializarJLabel(labelCriterioConsulta, "Criterio de Consulta:                  ");
        
        comboBoxAnioFin = new JComboBox();
        
        comboBoxSedes = new JComboBox();
        
        comboBoxCriterioConsulta = new JComboBox();

        separadorBoton = new JLabel();
        inicializarJLabel(separadorBoton, "≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡"
                                            + "≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡");
        separadorBoton.setFont(new java.awt.Font("Century Gothic", 1, 6));
        
        botonConsultar = new JButton("Consultar");
        botonConsultar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        botonConsultar.setPreferredSize(new Dimension(120, 30));

        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hacerConsulta(evt);
            }
        });
        
        autoCompletar = new AutoSuggestor(textFieldVendedor, ventanaPrincipal, obtenerVendedores(), Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.85f);
        
        inicializarCiudades(comboBoxCiudades);
        inicializarSedes(comboBoxSedes);
        inicializarAnios(comboBoxAnioInicio);
        inicializarAnios(comboBoxAnioFin);
        inicializarCriteriosDeConsulta(comboBoxCriterioConsulta);
        comboBoxCriterioConsulta.setSelectedItem("Mayor Número de Ventas");
    }

    void hacerConsulta(ActionEvent evt) {
        
        //verificamos que el rango de estrato sea correcto
        String vendedor = textFieldVendedor.getText();
        String ciudad = "" + comboBoxCiudades.getSelectedItem();
        String sede = "" + comboBoxSedes.getSelectedItem();
        String anioInicio = "" + comboBoxAnioInicio.getSelectedItem();
        String anioFin = "" + comboBoxAnioFin.getSelectedItem();
        String criterioConsulta = "" + comboBoxCriterioConsulta.getSelectedItem();

        String tituloGrafica = "";
        
        if (busquedaCodigo.isSelected()) {
            tituloGrafica = "Ventas de " + vendedor.split(",")[1] + " en los últimos años";
        } else if(busquedaOtrosCriterios.isSelected()){
            
            if (!ciudad.equals("Escoger una Opción...") && sede.equals("Escoger una Opción...") && anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
                tituloGrafica = "Top 5 de Mejores Vendedores en la Ciudad de " + comboBoxCiudades.getSelectedItem();
            }
            
            if (!ciudad.equals("Escoger una Opción...") && sede.equals("Escoger una Opción...") && anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
                tituloGrafica = "Top 5 de Peores Vendedores en la Ciudad de " + comboBoxCiudades.getSelectedItem();
            }
            
            if (ciudad.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
                tituloGrafica = "Top 5 de Mejores Vendedores en la Sede de " + comboBoxSedes.getSelectedItem();
            }
            
            if (ciudad.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
                tituloGrafica = "Top 5 de Peores Vendedores en la Sede de " + comboBoxSedes.getSelectedItem();
            }
            
            if (!ciudad.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
                tituloGrafica = "Top 5 de Mejores Vendedores en la Ciudad " + comboBoxCiudades.getSelectedItem() + " en la Sede de " + comboBoxSedes.getSelectedItem();
            }
            
            if (!ciudad.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
                tituloGrafica = "Top 5 de Peores Vendedores en la Ciudad " + comboBoxCiudades.getSelectedItem() + " en la Sede de " + comboBoxSedes.getSelectedItem();
            }
            
            if (!ciudad.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
                tituloGrafica = "Top 5 de Mejores Vendedores en la Ciudad " + comboBoxCiudades.getSelectedItem() + " en la Sede de " + comboBoxSedes.getSelectedItem() + " en el periodo seleccionado";
            }
            
            if (!ciudad.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
                tituloGrafica = "Top 5 de Peores Vendedores en la Ciudad " + comboBoxCiudades.getSelectedItem() + " en la Sede de " + comboBoxSedes.getSelectedItem() + " en el periodo seleccionado";
            }
            
        }
        
        ArrayList <String[]> ventasPorVendedor = controladorVentasVendedor.getVentas(vendedor, ciudad, sede, anioInicio, anioFin, criterioConsulta);

        if (ventasPorVendedor.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Esta consulta no entregó resultados. "
                    + "No existen registros que coincidan con los filtros solicitados");
            
        } else if(ventasPorVendedor.get(0)[0].equals("Error Vendedor")){
            JOptionPane.showMessageDialog(null, "El vendedor consultado no existe. Inténtelo nuevamente.");
        } else if(ventasPorVendedor.get(0)[0].equals("Error")){
            JOptionPane.showMessageDialog(null, "Debe seleccionar una ciudad o una sede para realizar la consulta");
        } else if(ventasPorVendedor.get(0)[0].equals("Error Fecha")){
            JOptionPane.showMessageDialog(null, "La consulta no puede ser realizada solo con Fecha Final. "
                    + "Seleccione fecha de Inicio unicamente o un rango válido a consultar");
        }else {
            try{
                ArrayList<String> vendedores = new ArrayList();
                ArrayList<Integer> ventas = new ArrayList();
                for (int i = 0; i <= ventasPorVendedor.size() - 1; i++) {
                    vendedores.add(ventasPorVendedor.get(i)[0]);
                    ventas.add(Integer.parseInt(ventasPorVendedor.get(i)[1]));
                }

                if (!ventasPorVendedor.isEmpty()) {
                    PieChart = new FXPieChart(tituloGrafica, vendedores, ventas);
                    BarChart = new FXBarChart(tituloGrafica, "Vendedores", vendedores, "Ventas", ventas, "Ventas Realizadas");
                    LineChart = new FXLineChart(tituloGrafica, "Vendedores", vendedores, "Ventas", ventas, "Ventas Realizadas");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha extraido la información");
                }
            } catch(ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null, "La consulta no puede ser realizada solo con Fecha Final. "
                    + "Seleccione fecha de Inicio unicamente o un rango válido a consultar");
            }
        }
    }
    
    public ArrayList<String> obtenerVendedores(){
    
        BaseDeDatos = new ConexionBD();
        
        ArrayList<String> vendedores = new ArrayList<>();
        
        try {
         
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery("SELECT nombre_vendedor, apellido_vendedor, id_vendedor "
                    + "FROM dim_vendedor WHERE tipo_vendedor = 'V';");
            
            while (tabla.next()) {
                vendedores.add(tabla.getObject(2) + " " + tabla.getObject(1) +  ", " + tabla.getObject(3));
                vendedores.add(tabla.getObject(3) +  ", " + tabla.getObject(2) + " " + tabla.getObject(1));
            }
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        return vendedores;
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

        String meses[][] = new String[3][1];
        meses[0][0] = "Escoger una Opción...";
        meses[1][0] = "Mayor Número de Ventas";
        meses[2][0] = "Menor Número de Ventas";

        for (int i = 0; i < meses.length; i++) {
            criteriosDeConsulta.addItem(meses[i][0]);
        }
    
    }

    protected void inicializarCiudades(JComboBox ciudades){
        BaseDeDatos = new ConexionBD();
        
        ciudades.setVisible(true);
        ciudades.setMaximumSize(new Dimension(250, 30));
        
        ArrayList<String> listaCiudades = new ArrayList();
        
        try {
         
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery("SELECT nombre_ciudad "
                    + "FROM dim_ciudad;");

            listaCiudades.add("Escoger una Opción...");
            
            while (tabla.next()) {
                listaCiudades.add(tabla.getObject(1) + "");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        for (int i = 0; i < listaCiudades.size(); i++) {
            ciudades.addItem(listaCiudades.get(i));
        }
    }
    
    protected void inicializarSedes(JComboBox sedes) {

        BaseDeDatos = new ConexionBD();
        
        sedes.setVisible(true);
        sedes.setMaximumSize(new Dimension(250, 30));
        
        ArrayList<String> listaSedes = new ArrayList();
        
        try {
         
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery("SELECT nombre_sede FROM dim_sede;");


            listaSedes.add("Escoger una Opción...");
            
            while (tabla.next()) {
                listaSedes.add(tabla.getObject(1) + "");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        for (int i = 0; i < listaSedes.size(); i++) {
            sedes.addItem(listaSedes.get(i));
        }
    }
    
    protected void inicializarMeses(JComboBox entradaMeses) {

        entradaMeses.setVisible(true);
        entradaMeses.setMaximumSize(new Dimension(230, 30));

        String meses[][] = new String[13][1];
        meses[0][0] = "General";
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
    
    public int obtenerCodigoCiudad(String nombreCiudad){
        
        int ciudadSeleccionada = 0;
        
        if (nombreCiudad.equals("KM 30")) {
            ciudadSeleccionada = 22;
        } else if (nombreCiudad.equals("ALCALA")) {
            ciudadSeleccionada = 91;
        } else if (nombreCiudad.equals("AMAIME")) {
            ciudadSeleccionada = 42;
        } else if (nombreCiudad.equals("ANDALUCIA")) {
            ciudadSeleccionada = 71;
        } else if (nombreCiudad.equals("ANSERMA NUEVO")) {
            ciudadSeleccionada = 61;
        } else if (nombreCiudad.equals("BOLIVAR")) {
            ciudadSeleccionada = 85;
        } else if (nombreCiudad.equals("BOLO")) {
            ciudadSeleccionada = 30;
        } else if (nombreCiudad.equals("BUCHITOLO")) {
            ciudadSeleccionada = 95;
        } else if (nombreCiudad.equals("BUENAVENTURA")) {
            ciudadSeleccionada = 26;
        } else if (nombreCiudad.equals("BUGA")) {
            ciudadSeleccionada = 41;
        } else if (nombreCiudad.equals("BUGALAGRANDE")) {
            ciudadSeleccionada = 70;
        } else if (nombreCiudad.equals("CAICEDONIA")) {
            ciudadSeleccionada = 78;
        } else if (nombreCiudad.equals("CALI")) {
            ciudadSeleccionada = 53;
        } else if (nombreCiudad.equals("CANDELARIA")) {
            ciudadSeleccionada = 46;
        } else if (nombreCiudad.equals("CARTAGO")) {
            ciudadSeleccionada = 74;
        } else if (nombreCiudad.equals("COSTA RICA")) {
            ciudadSeleccionada = 44;
        } else if (nombreCiudad.equals("DAGUA")) {
            ciudadSeleccionada = 47;
        } else if (nombreCiudad.equals("DAPA")) {
            ciudadSeleccionada = 24;
        } else if (nombreCiudad.equals("DARIEN")) {
            ciudadSeleccionada = 55;
        } else if (nombreCiudad.equals("EL AGUILA")) {
            ciudadSeleccionada = 99;
        } else if (nombreCiudad.equals("EL CABUYAL")) {
            ciudadSeleccionada = 57;
        } else if (nombreCiudad.equals("EL CARMELO")) {
            ciudadSeleccionada = 56;
        } else if (nombreCiudad.equals("EL CARMEN")) {
            ciudadSeleccionada = 98;
        } else if (nombreCiudad.equals("EL CERRITO")) {
            ciudadSeleccionada = 52;
        } else if (nombreCiudad.equals("EL DOVIO")) {
            ciudadSeleccionada = 88;
        } else if (nombreCiudad.equals("EL PALMAR")) {
            ciudadSeleccionada = 97;
        } else if (nombreCiudad.equals("EL TREINTA")) {
            ciudadSeleccionada = 93;
        } else if (nombreCiudad.equals("EL VINCULO")) {
            ciudadSeleccionada = 96;
        } else if (nombreCiudad.equals("FLORIDA")) {
            ciudadSeleccionada = 45;
        } else if (nombreCiudad.equals("GINEBRA")) {
            ciudadSeleccionada = 51;
        } else if (nombreCiudad.equals("GUABITAS")) {
            ciudadSeleccionada = 62;
        } else if (nombreCiudad.equals("GUACARI")) {
            ciudadSeleccionada = 58;
        } else if (nombreCiudad.equals("JAMUNDI")) {
            ciudadSeleccionada = 63;
        } else if (nombreCiudad.equals("LA BUITRERA")) {
            ciudadSeleccionada = 32;
        } else if (nombreCiudad.equals("LA CUMBRE")) {
            ciudadSeleccionada = 84;
        } else if (nombreCiudad.equals("LA HERRADURA")) {
            ciudadSeleccionada = 35;
        } else if (nombreCiudad.equals("LA MARINA")) {
            ciudadSeleccionada = 66;
        } else if (nombreCiudad.equals("LA PAILA")) {
            ciudadSeleccionada = 79;
        } else if (nombreCiudad.equals("LA QUISQUINA")) {
            ciudadSeleccionada = 25;
        } else if (nombreCiudad.equals("LA UNION")) {
            ciudadSeleccionada = 76;
        } else if (nombreCiudad.equals("LA URIBE")) {
            ciudadSeleccionada = 90;
        } else if (nombreCiudad.equals("LA VICTORIA")) {
            ciudadSeleccionada = 82;
        } else if (nombreCiudad.equals("LOS CHANCOS")) {
            ciudadSeleccionada = 29;
        } else if (nombreCiudad.equals("MEDIA CANOA")) {
            ciudadSeleccionada = 94;
        } else if (nombreCiudad.equals("OBANDO")) {
            ciudadSeleccionada = 80;
        } else if (nombreCiudad.equals("ORTIGAL")) {
            ciudadSeleccionada = 38;
        } else if (nombreCiudad.equals("PALMASECA")) {
            ciudadSeleccionada = 37;
        } else if (nombreCiudad.equals("PALMIRA")) {
            ciudadSeleccionada = 40;
        } else if (nombreCiudad.equals("POTRERILLO")) {
            ciudadSeleccionada = 33;
        } else if (nombreCiudad.equals("PRADERA")) {
            ciudadSeleccionada = 43;
        } else if (nombreCiudad.equals("PRESIDENTE")) {
            ciudadSeleccionada = 92;
        } else if (nombreCiudad.equals("RESTREPO")) {
            ciudadSeleccionada = 49;
        } else if (nombreCiudad.equals("RIO FRIO")) {
            ciudadSeleccionada = 69;
        } else if (nombreCiudad.equals("ROLDANILLO")) {
            ciudadSeleccionada = 77;
        } else if (nombreCiudad.equals("ROZO")) {
            ciudadSeleccionada = 81;
        } else if (nombreCiudad.equals("SALONICA")) {
            ciudadSeleccionada = 83;
        } else if (nombreCiudad.equals("SAN ANTONIO DE LOS C")) {
            ciudadSeleccionada = 54;
        } else if (nombreCiudad.equals("SAN JUAQUIN")) {
            ciudadSeleccionada = 59;
        } else if (nombreCiudad.equals("SAN PEDRO")) {
            ciudadSeleccionada = 64;
        } else if (nombreCiudad.equals("SANTA ELENA")) {
            ciudadSeleccionada = 39;
        } else if (nombreCiudad.equals("SEVILLA")) {
            ciudadSeleccionada = 87;
        } else if (nombreCiudad.equals("SONSO")) {
            ciudadSeleccionada = 60;
        } else if (nombreCiudad.equals("TABLONES")) {
            ciudadSeleccionada = 31;
        } else if (nombreCiudad.equals("TENERIFE")) {
            ciudadSeleccionada = 36;
        } else if (nombreCiudad.equals("TIENDA NUEVA")) {
            ciudadSeleccionada = 34;
        } else if (nombreCiudad.equals("TODOS LOS SANTOS")) {
            ciudadSeleccionada = 28;
        } else if (nombreCiudad.equals("TORO")) {
            ciudadSeleccionada = 86;
        } else if (nombreCiudad.equals("TRUJILLO")) {
            ciudadSeleccionada = 73;
        } else if (nombreCiudad.equals("TULUA")) {
            ciudadSeleccionada = 67;
        } else if (nombreCiudad.equals("TUMACO NARINO")) {
            ciudadSeleccionada = 27;
        } else if (nombreCiudad.equals("ULLOA")) {
            ciudadSeleccionada = 65;
        } else if (nombreCiudad.equals("VERSALLES")) {
            ciudadSeleccionada = 89;
        } else if (nombreCiudad.equals("VIJES")) {
            ciudadSeleccionada = 72;
        } else if (nombreCiudad.equals("VILLAGORGONA")) {
            ciudadSeleccionada = 48;
        } else if (nombreCiudad.equals("YOTOCO")) {
            ciudadSeleccionada = 68;
        } else if (nombreCiudad.equals("YUMBO")) {
            ciudadSeleccionada = 50;
        } else if (nombreCiudad.equals("ZARAGOZA")) {
            ciudadSeleccionada = 23;
        } else if (nombreCiudad.equals("ZARZAL")) {
            ciudadSeleccionada = 75;
        } else if (nombreCiudad.equals("BUENOS AIRES")) {
            ciudadSeleccionada = 28;
        } else if (nombreCiudad.equals("CAJIBIO")) {
            ciudadSeleccionada = 17;
        } else if (nombreCiudad.equals("CALDONO")) {
            ciudadSeleccionada = 16;
        } else if (nombreCiudad.equals("CALOTO")) {
            ciudadSeleccionada = 33;
        } else if (nombreCiudad.equals("CORINTO")) {
            ciudadSeleccionada = 31;
        } else if (nombreCiudad.equals("GUACHENE")) {
            ciudadSeleccionada = 23;
        } else if (nombreCiudad.equals("GUAPI")) {
            ciudadSeleccionada = 24;
        } else if (nombreCiudad.equals("MIRANDA")) {
            ciudadSeleccionada = 30;
        } else if (nombreCiudad.equals("MONDOMO")) {
            ciudadSeleccionada = 15;
        } else if (nombreCiudad.equals("MORALES")) {
            ciudadSeleccionada = 14;
        } else if (nombreCiudad.equals("PADILLA")) {
            ciudadSeleccionada = 32;
        } else if (nombreCiudad.equals("PESCADOR")) {
            ciudadSeleccionada = 10;
        } else if (nombreCiudad.equals("PIENDAMO")) {
            ciudadSeleccionada = 12;
        } else if (nombreCiudad.equals("POPAYAN")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("PUERTO TEJADA")) {
            ciudadSeleccionada = 21;
        } else if (nombreCiudad.equals("SANTANDER Q")) {
            ciudadSeleccionada = 65;
        } else if (nombreCiudad.equals("SILVIA")) {
            ciudadSeleccionada = 13;
        } else if (nombreCiudad.equals("SUAREZ")) {
            ciudadSeleccionada = 29;
        } else if (nombreCiudad.equals("TIMBIQUI")) {
            ciudadSeleccionada = 20;
        } else if (nombreCiudad.equals("TORIBIO")) {
            ciudadSeleccionada = 19;
        } else if (nombreCiudad.equals("TOTORO")) {
            ciudadSeleccionada = 18;
        } else if (nombreCiudad.equals("TUNIA")) {
            ciudadSeleccionada = 11;
        } else if (nombreCiudad.equals("VILLA RICA")) {
            ciudadSeleccionada = 22;
        } else if (nombreCiudad.equals("ALTAGRACIA")) {
            ciudadSeleccionada = 7;
        } else if (nombreCiudad.equals("ARMENIA")) {
            ciudadSeleccionada = 6;
        } else if (nombreCiudad.equals("CHINCHINA")) {
            ciudadSeleccionada = 9;
        } else if (nombreCiudad.equals("DOS QUEBRADAS")) {
            ciudadSeleccionada = 2;
        } else if (nombreCiudad.equals("LA FLORIDA")) {
            ciudadSeleccionada = 8;
        } else if (nombreCiudad.equals("LA VIRGINIA")) {
            ciudadSeleccionada = 4;
        } else if (nombreCiudad.equals("MARSELLA")) {
            ciudadSeleccionada = 5;
        } else if (nombreCiudad.equals("PEREIRA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("SANTA ROSA")) {
            ciudadSeleccionada = 3;
        } else if (nombreCiudad.equals("SANTUARIO")) {
            ciudadSeleccionada = 10;
        } else if (nombreCiudad.equals("ARMENIA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("BARCELONA")) {
            ciudadSeleccionada = 9;
        } else if (nombreCiudad.equals("BUENAVISTA")) {
            ciudadSeleccionada = 13;
        } else if (nombreCiudad.equals("CALARCA")) {
            ciudadSeleccionada = 5;
        } else if (nombreCiudad.equals("CIRCASIA")) {
            ciudadSeleccionada = 6;
        } else if (nombreCiudad.equals("CORDOBA")) {
            ciudadSeleccionada = 10;
        } else if (nombreCiudad.equals("FILANDIA")) {
            ciudadSeleccionada = 8; 
        } else if (nombreCiudad.equals("GENOVA")) {
            ciudadSeleccionada = 11;
        } else if (nombreCiudad.equals("MONTENEGRO")) {
            ciudadSeleccionada = 4;
        } else if (nombreCiudad.equals("PIJAO")) {
            ciudadSeleccionada = 12;
        } else if (nombreCiudad.equals("QUIMBAYA")) {
            ciudadSeleccionada = 2;
        } else if (nombreCiudad.equals("SALENTO")) {
            ciudadSeleccionada = 7;
        } else if (nombreCiudad.equals("LA TEBAIDA")) {
            ciudadSeleccionada = 3;
        } else if (nombreCiudad.equals("EL CHARCO")) {
            ciudadSeleccionada = 3;
        } else if (nombreCiudad.equals("IPIALES")) {
            ciudadSeleccionada = 4;
        } else if (nombreCiudad.equals("PASTO")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("TUMACO")) {
            ciudadSeleccionada = 2;
        } else if (nombreCiudad.equals("MANIZALES")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("CHINCHINA")) {
            ciudadSeleccionada = 2;
        } else if (nombreCiudad.equals("VITERBO")) {
            ciudadSeleccionada = 3;
        } else if (nombreCiudad.equals("RIOSUCIO")) {
            ciudadSeleccionada = 4;
        } else if (nombreCiudad.equals("ANSERMA")) {
            ciudadSeleccionada = 5;
        } else if (nombreCiudad.equals("SALAMINA")) {
            ciudadSeleccionada = 6;
        } else if (nombreCiudad.equals("ARGELIA")) {
            ciudadSeleccionada = 20;
        } else if (nombreCiudad.equals("JURADO")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("BOGOTA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("VILLAVICENCIO")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("NEIVA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("BUCARAMANGA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("BARRANQUILLA")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("IBAGUE")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("MEDELLIN")) {
            ciudadSeleccionada = 1;
        }
        
        return ciudadSeleccionada;
    }
    
    public int obtenerCodigoSede(String nombreSede){
        
        int sedeSeleccionada = 0;
        
        if (nombreSede.equals("SEDE PALMIRA")) {
            sedeSeleccionada = 1;
        } else if (nombreSede.equals("SEDE CALI")) {
            sedeSeleccionada = 2;
        } else if (nombreSede.equals("SEDE TULUA")) {
            sedeSeleccionada = 3;
        } else if (nombreSede.equals("SEDE PEREIRA")) {
            sedeSeleccionada = 4;
        } else if (nombreSede.equals("SEDE ARMENIA")) {
            sedeSeleccionada = 5;
        }
        
        return sedeSeleccionada;
    }
    
    public void generarReporte(){
    
        JasperReport report;
        JasperPrint jasperPrint;
        
        ConexionBD bd = new ConexionBD();
        Connection conn = bd.conectar();

        try {

            Map<String, Object> parametros = new HashMap();
            
            System.out.println("Sede: " + obtenerCodigoSede(comboBoxSedes.getSelectedItem().toString()));
            parametros.put("id_sede", new Long(obtenerCodigoSede(comboBoxSedes.getSelectedItem().toString()) + ""));
            
            System.out.println("Ciudad: " + obtenerCodigoCiudad(comboBoxCiudades.getSelectedItem().toString()));
            parametros.put("id_ciudad", new Long(obtenerCodigoCiudad(comboBoxCiudades.getSelectedItem().toString()) + ""));

            if (!textFieldVendedor.getText().equals("")) {
                System.out.println("Ciudad: " + obtenerCodigoCiudad(comboBoxCiudades.getSelectedItem().toString()));
                parametros.put("id_vendedor", new Long(obtenerCodigoCiudad(comboBoxCiudades.getSelectedItem().toString()) + ""));
            }
            
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

            report = (JasperReport) JRLoader.loadObjectFromFile("C:\\Users\\llani\\OneDrive\\Documentos\\Tesis\\Tesis\\KDD\\UI_Tesis\\src\\Reportes\\Cupos\\ReporteVentasVendedores.jasper");
            jasperPrint = JasperFillManager.fillReport(report, parametros, conn);

            JFrame frame = new JFrame("Reporte Venta de Cupos por Vendedor");
            frame.setPreferredSize(new Dimension(1000, 600));
            frame.getContentPane().add(new JRViewer(jasperPrint));
            frame.pack();
            frame.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Visualizador.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public JComboBox getComboBoxCiudades() {
        return comboBoxCiudades;
    }

    public void setComboBoxCiudades(JComboBox comboBoxCiudades) {
        this.comboBoxCiudades = comboBoxCiudades;
    }

    public JLabel getLabelCiudad() {
        return labelCiudad;
    }

    public void setLabelCiudad(JLabel labelCiudad) {
        this.labelCiudad = labelCiudad;
    }

    public JComboBox getComboBoxSedes() {
        return comboBoxSedes;
    }

    public void setComboBoxSedes(JComboBox comboBoxSedes) {
        this.comboBoxSedes = comboBoxSedes;
    }
    
    public JLabel getLabelSede() {
        return labelSede;
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

    public JLabel getLabelVendedor() {
        return labelVendedor;
    }

    public void setLabelVendedor(JLabel labelVendedor) {
        this.labelVendedor = labelVendedor;
    }

    public JTextField getTextFieldVendedor() {
        return textFieldVendedor;
    }

    public void setTextFieldVendedor(JTextField textFieldVendedor) {
        this.textFieldVendedor = textFieldVendedor;
    }

    public JLabel getLabelTipoConsulta() {
        return labelTipoConsulta;
    }

    public void setLabelTipoConsulta(JLabel labelTipoConsulta) {
        this.labelTipoConsulta = labelTipoConsulta;
    }

    public JCheckBox getBusquedaCodigo() {
        return busquedaCodigo;
    }

    public void setBusquedaCodigo(JCheckBox busquedaCodigo) {
        this.busquedaCodigo = busquedaCodigo;
    }
    
    public JCheckBox getBusquedaNombre() {
        return busquedaNombre;
    }

    public void setBusquedaNombre(JCheckBox busquedaNombre) {
        this.busquedaNombre = busquedaNombre;
    }

    public JCheckBox getBusquedaOtrosCriterios() {
        return busquedaOtrosCriterios;
    }

    public void setBusquedaOtrosCriterios(JCheckBox busquedaOtrosCriterios) {
        this.busquedaOtrosCriterios = busquedaOtrosCriterios;
    }

    public JLabel getSeparadorBoton() {
        return separadorBoton;
    }

    public void setSeparadorBoton(JLabel separadorBoton) {
        this.separadorBoton = separadorBoton;
    }
    
}
