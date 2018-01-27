/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Afiliaciones;

import ConectorBD.ConexionBD;
import Controlador.Afiliaciones.ControladorVentasCiudades;
import Gráficos.FXBarChart;
import Gráficos.FXLineChart;
import Gráficos.FXPieChart;
import com.sun.javafx.image.impl.IntArgb;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban
 */

public class UiVentasCiudades {
    
    JComboBox comboBoxDepartamentos, comboBoxSedes, comboBoxAnioInicio, comboBoxAnioFin;
    JLabel labelDepartamento, labelSede, labelAnioInicio, labelAnioFin;
    JButton botonConsultar;
    
    ControladorVentasCiudades controladorVentasCiudad;
    
    // Elementos de conexion de la BD para el llenado de los comboBox
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public UiVentasCiudades() {

        controladorVentasCiudad = new ControladorVentasCiudades();
        
        labelDepartamento = new JLabel();
        inicializarJLabel(labelDepartamento, "Departamento:          ");

        comboBoxDepartamentos = new JComboBox();
        
        labelSede = new JLabel();
        inicializarJLabel(labelSede, "Sede:          ");
        
        labelAnioInicio = new JLabel();
        inicializarJLabel(labelAnioInicio, "Desde:          ");
        
        comboBoxAnioInicio = new JComboBox();        
        
        labelAnioFin = new JLabel();
        inicializarJLabel(labelAnioFin, "Hasta:          ");
        
        comboBoxAnioFin = new JComboBox();
        
        comboBoxSedes = new JComboBox();

        botonConsultar = new JButton("Consultar");

        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hacerConsulta(evt);
            }
        });
        
        inicializarDepartamentos(comboBoxDepartamentos);
        inicializarSedes(comboBoxSedes);
        inicializarAnios(comboBoxAnioInicio);
        inicializarAnios(comboBoxAnioFin);
    }

    void hacerConsulta(ActionEvent evt) {

        // Clases para el despligue Gráfico de resultados
    
        FXPieChart PieChart;
        FXBarChart BarChart;
        FXLineChart LineChart;
        
        //verificamos que el rango de estrato sea correcto
        

        String departamento = "" + comboBoxDepartamentos.getSelectedItem();
        String sede = "" + comboBoxSedes.getSelectedItem();
        String anioInicio = "" + comboBoxAnioInicio.getSelectedItem();
        String anioFin = "" + comboBoxAnioFin.getSelectedItem();

        ArrayList <String[]> ventasPorCiudad = controladorVentasCiudad.getVentas(departamento, sede, anioInicio, anioFin);

        if (ventasPorCiudad.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Esta consulta no entregó resultados. "
                    + "No existen registros que coincidan con los filtros solicitados");
            
        } else if(ventasPorCiudad.get(0)[0].equals("Error")){
            JOptionPane.showMessageDialog(null, "Debe seleccionar un departamento o una sede (o ambos) para realizar la consulta");
        } else if(ventasPorCiudad.get(0)[0].equals("Error Fecha")){
            JOptionPane.showMessageDialog(null, "La consulta no puede ser realizada solo con Fecha Final. "
                    + "Seleccione fecha de Inicio unicamente o un rango válido a consultar");
        }else {
            try{ ArrayList<String> ciudades = new ArrayList();
                ArrayList<Integer> ventas = new ArrayList();
                for (int i = 0; i <= ventasPorCiudad.size() - 1; i++) {
                    ciudades.add(ventasPorCiudad.get(i)[0]);
                    ventas.add(Integer.parseInt(ventasPorCiudad.get(i)[1]));
                }

                if (!ventasPorCiudad.isEmpty()) {
                    PieChart = new FXPieChart("Ventas por Ciudad", ciudades, ventas);
                    BarChart = new FXBarChart("Ventas por Ciudad", "Ciudades", ciudades, "Ventas", ventas, "Ventas Realizadas por Ciudad");
                    LineChart = new FXLineChart("Ventas por Ciudad", "Ciudades", ciudades, "Ventas", ventas, "Ventas Realizadas por Ciudad");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha extraido la información");
                }
            } catch(ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null, "La consulta no puede ser realizada solo con Fecha Final. "
                    + "Seleccione fecha de Inicio unicamente o un rango válido a consultar");
            }
        }
    }
    
    protected void inicializarJLabel(JLabel label, String texto) {

        label.setText(texto);
        label.setSize(170, 30);
        label.setMinimumSize(new Dimension(170, 30));
        label.setMaximumSize(new Dimension(170, 30));
        label.setFont(new java.awt.Font("DejaVu Sans", 1, 13));
    }

    protected void inicializarDepartamentos(JComboBox departamentos) {

        BaseDeDatos = new ConexionBD();
        
        departamentos.setVisible(true);
        departamentos.setMaximumSize(new Dimension(200, 30));
        
        ArrayList<String> listaDepartamentos = new ArrayList();
        
        try {
         
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery("SELECT nombre_departamento "
                    + "FROM departamento;");
            
            System.out.println("Conexión establecida");

            listaDepartamentos.add("Escoger una Opción...");
            
            while (tabla.next()) {
                listaDepartamentos.add(tabla.getObject(1) + "");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        for (int i = 0; i < listaDepartamentos.size(); i++) {
            departamentos.addItem(listaDepartamentos.get(i));
        }
    }

    protected void inicializarSedes(JComboBox sedes) {

        BaseDeDatos = new ConexionBD();
        
        sedes.setVisible(true);
        sedes.setMaximumSize(new Dimension(200, 30));
        
        ArrayList<String> listaSedes = new ArrayList();
        
        try {
         
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery("SELECT nombre_sede FROM sede;");
            
            System.out.println("Conexión establecida");

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
        entradaMeses.setMaximumSize(new Dimension(200, 30));

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
        anioVentas.setMaximumSize(new Dimension(170, 30));

        String[][] anios = new String[20][1];
        anios[0][0] = "Escoger una Opción...";
        anios[1][0] = "2000";
        anios[2][0] = "2001";
        anios[3][0] = "2002";
        anios[4][0] = "2003";
        anios[5][0] = "2004";
        anios[6][0] = "2005";
        anios[7][0] = "2006";
        anios[8][0] = "2007";
        anios[9][0] = "2008";
        anios[10][0] = "2009";
        anios[11][0] = "2010";
        anios[12][0] = "2011";
        anios[13][0] = "2012";
        anios[14][0] = "2013";
        anios[15][0] = "2014";
        anios[16][0] = "2015";
        anios[17][0] = "2016";
        anios[18][0] = "2017";
        anios[19][0] = "2018";

        for (int i = 0; i < anios.length; i++) {
            anioVentas.addItem(anios[i][0]);
        }
    }

    public JComboBox getComboBoxDepartamentos() {
        return comboBoxDepartamentos;
    }

    public void setComboBoxDepartamentos(JComboBox comboBoxDepartamentos) {
        this.comboBoxDepartamentos = comboBoxDepartamentos;
    }

    public JLabel getLabelDepartamento() {
        return labelDepartamento;
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
    
    
}
