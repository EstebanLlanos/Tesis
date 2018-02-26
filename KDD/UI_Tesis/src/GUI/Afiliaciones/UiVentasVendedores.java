/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Afiliaciones;

import ConectorBD.ConexionBD;
import Controlador.Afiliaciones.ControladorVentasVendedores;
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
import javax.swing.JPopupMenu.Separator;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import Recursos.AutoSuggestor;
import javax.swing.JFrame;

/**
 *
 * @author Esteban
 */

public class UiVentasVendedores {
    
    JComboBox comboBoxCiudades, comboBoxSedes, comboBoxAnioInicio, comboBoxAnioFin, comboBoxCriterioConsulta;
    JLabel labelCiudad, labelSede, labelAnioInicio, labelAnioFin, labelCriterioConsulta, labelVendedor;
    JTextField textFieldVendedor;
    JSeparator separadorVendedor;
    JButton botonConsultar;
    
    AutoSuggestor autoCompletar;
    
    ControladorVentasVendedores controladorVentasVendedor;
    
    // Elementos de conexion de la BD para el llenado de los comboBox
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public UiVentasVendedores(JFrame ventanaPrincipal) {

        controladorVentasVendedor = new ControladorVentasVendedores();
        
        labelVendedor = new JLabel();
        inicializarJLabel(labelVendedor, "Vendedor:                              ");
        
        textFieldVendedor = new JTextField();
        textFieldVendedor.setPreferredSize(new Dimension(170, 30));
        
        separadorVendedor = new Separator();
        
        labelCiudad = new JLabel();
        inicializarJLabel(labelCiudad, "Ciudad:          ");
        comboBoxCiudades = new JComboBox();
        
        labelSede = new JLabel();
        inicializarJLabel(labelSede, "Sede:          ");
        
        labelAnioInicio = new JLabel();
        inicializarJLabel(labelAnioInicio, "Desde:          ");
        
        comboBoxAnioInicio = new JComboBox();        
        
        labelAnioFin = new JLabel();
        inicializarJLabel(labelAnioFin, "Hasta:          ");
        
        labelCriterioConsulta = new JLabel();
        inicializarJLabel(labelCriterioConsulta, "Criterio de Consulta:          ");
        
        comboBoxAnioFin = new JComboBox();
        
        comboBoxSedes = new JComboBox();
        
        comboBoxCriterioConsulta = new JComboBox();

        botonConsultar = new JButton("Consultar");
        botonConsultar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

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
    }

    void hacerConsulta(ActionEvent evt) {

        // Clases para el despligue Gráfico de resultados
    
        FXPieChart PieChart;
        FXBarChart BarChart;
        FXLineChart LineChart;
        
        //verificamos que el rango de estrato sea correcto
        String ciudad = "" + comboBoxCiudades.getSelectedItem();
        String sede = "" + comboBoxSedes.getSelectedItem();
        String anioInicio = "" + comboBoxAnioInicio.getSelectedItem();
        String anioFin = "" + comboBoxAnioFin.getSelectedItem();
        String criterioConsulta = "" + comboBoxCriterioConsulta.getSelectedItem();

        ArrayList <String[]> ventasPorVendedor = controladorVentasVendedor.getVentas(ciudad, sede, anioInicio, anioFin, criterioConsulta);

        if (ventasPorVendedor.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Esta consulta no entregó resultados. "
                    + "No existen registros que coincidan con los filtros solicitados");
            
        } else if(ventasPorVendedor.get(0)[0].equals("Error")){
            JOptionPane.showMessageDialog(null, "Debe seleccionar un departamento, ciudad o sede para realizar la consulta");
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
                    PieChart = new FXPieChart("Top 10 Vendedores", vendedores, ventas);
                    BarChart = new FXBarChart("Top 10 Vendedores", "Vendedores", vendedores, "Ventas", ventas, "Ventas Realizadas");
                    LineChart = new FXLineChart("Top 10 Vendedores", "Vendedores", vendedores, "Ventas", ventas, "Ventas Realizadas");
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
            ResultSet tabla = sentencia.executeQuery("SELECT nombre_personal, apellido_personal, id_personal "
                    + "FROM personal;");
            
            while (tabla.next()) {
                vendedores.add(tabla.getObject(2) + " " + tabla.getObject(1) + ", " + tabla.getObject(3));
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

        String meses[][] = new String[2][1];
        meses[0][0] = "Mayor Número de Ventas";
        meses[1][0] = "Menor Número de Ventas";

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
                    + "FROM ciudad;");

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
            ResultSet tabla = sentencia.executeQuery("SELECT nombre_sede FROM sede;");


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

    public JSeparator getSeparadorVendedor() {
        return separadorVendedor;
    }

    public void setSeparadorVendedor(JSeparator separadorVendedor) {
        this.separadorVendedor = separadorVendedor;
    }
    
}
