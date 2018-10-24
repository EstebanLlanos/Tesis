/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Cupos;

import ConectorBD.ConexionBD;
import Controlador.Cupos.ControladorVentasSedes;
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

public class UiVentasSedes {
    
    JComboBox comboBoxAnioInicio, comboBoxAnioFin, comboBoxMesInicio, comboBoxMesFin;
    JLabel labelAnioInicio, labelAnioFin, labelMesInicio, labelMesFin, separadorBoton;
    JButton botonConsultar;
    
    // Clases para el despligue Gráfico de resultados

    FXPieChart PieChart;
    FXBarChart BarChart;
    FXLineChart LineChart;
    
    ControladorVentasSedes controladorVentasSedes;
    
    // Elementos de conexion de la BD para el llenado de los comboBox
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public UiVentasSedes() {

        controladorVentasSedes = new ControladorVentasSedes();
        
        labelAnioInicio = new JLabel();
        inicializarJLabel(labelAnioInicio, "Desde el Año:                         ");
        comboBoxAnioInicio = new JComboBox();
        
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
        inicializarJLabel(labelAnioFin, "Hasta el Año:                            ");
        comboBoxAnioFin = new JComboBox();
        
        labelMesInicio = new JLabel();
        inicializarJLabel(labelMesInicio, "Desde el Mes:                          ");
        comboBoxMesInicio = new JComboBox();        
        comboBoxMesInicio.setEnabled(false);
        
        labelMesFin = new JLabel();
        inicializarJLabel(labelMesFin, "Hasta el Mes:                             ");
        comboBoxMesFin = new JComboBox();
        comboBoxMesFin.setEnabled(false);
        
        separadorBoton = new JLabel();
        inicializarJLabel(separadorBoton, "≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡"
                                            + "≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡");
        separadorBoton.setFont(new java.awt.Font("Century Gothic", 1, 6));
        
        botonConsultar = new JButton("Consultar");
        botonConsultar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        botonConsultar.setPreferredSize(new Dimension(120, 30));

        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hacerConsulta(evt);
            }
        });
        
        inicializarAnios(comboBoxAnioInicio);
        inicializarAnios(comboBoxAnioFin);
        inicializarMeses(comboBoxMesInicio);
        inicializarMeses(comboBoxMesFin);
    }

    void hacerConsulta(ActionEvent evt) {
        
        //verificamos que el rango de estrato sea correcto
        
        String anioInicio = "" + comboBoxAnioInicio.getSelectedItem();
        String anioFin = "" + comboBoxAnioFin.getSelectedItem();
        String mesInicio = "" + comboBoxMesInicio.getSelectedItem();
        String mesFin = "" + comboBoxMesFin.getSelectedItem();

        String tituloGrafico = "";
        
        if (!anioInicio.equals("Escoger una Opción...") && mesInicio.equals("Escoger una Opción...") && anioFin.equals("Escoger una Opción...") && mesFin.equals("Escoger una Opción...")) {
            tituloGrafico = "Número de Ventas de Afiliaciones entre el 01 de Enero de " + comboBoxAnioInicio.getSelectedItem() + " y el 01 de Diciembre de " + comboBoxAnioInicio.getSelectedItem();
        }
        
        if (!anioInicio.equals("Escoger una Opción...") && !mesInicio.equals("Escoger una Opción...") && anioFin.equals("Escoger una Opción...") && !mesFin.equals("Escoger una Opción...")) {
            tituloGrafico = "Número de Ventas de Afiliaciones entre el 01 de " + comboBoxMesInicio.getSelectedItem() + " de " + comboBoxAnioInicio.getSelectedItem() + " y el 01 de " + comboBoxMesFin.getSelectedItem() + " de " + comboBoxAnioInicio.getSelectedItem();
        }
        
        if (!anioInicio.equals("Escoger una Opción...") && !mesInicio.equals("Escoger una Opción...") && !anioFin.equals("Escoger una Opción...") && !mesFin.equals("Escoger una Opción...")) {
            tituloGrafico = "Número de Ventas de Afiliaciones entre el 01 de " + comboBoxMesInicio.getSelectedItem() + " de " + comboBoxAnioInicio.getSelectedItem() + " y el 01 de " + comboBoxMesFin.getSelectedItem() + " de " + comboBoxAnioFin.getSelectedItem();
        }
        
        if (!anioInicio.equals("Escoger una Opción...") && mesInicio.equals("Escoger una Opción...") && !anioFin.equals("Escoger una Opción...") && mesFin.equals("Escoger una Opción...")) {
            tituloGrafico = "Número de Ventas de Afiliaciones entre el 01 de Enero de " + comboBoxAnioInicio.getSelectedItem() + " y el 01 de Enero de " + comboBoxAnioFin.getSelectedItem();
        }
        
        ArrayList <String[]> ventasPorSede = controladorVentasSedes.getVentas(anioInicio, anioFin, mesInicio, mesFin);

        if (ventasPorSede.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Esta consulta no entregó resultados. "
                    + "No existen registros que coincidan con los filtros solicitados");
            
        } else if(ventasPorSede.get(0)[0].equals("Error Fecha Año")){
            JOptionPane.showMessageDialog(null, "Seleccione un año de inicio o un rango de años válido a consultar");
        } else if(ventasPorSede.get(0)[0].equals("Error Fecha Mes")){
            JOptionPane.showMessageDialog(null, "Debe seleccionar un rango de meses válido.");
        }else {
            try{
                ArrayList<String> sedes = new ArrayList();
                ArrayList<Integer> ventas = new ArrayList();
                for (int i = 0; i <= ventasPorSede.size() - 1; i++) {
                    sedes.add(ventasPorSede.get(i)[0]);
                    ventas.add(Integer.parseInt(ventasPorSede.get(i)[1]));
                }

                if (!ventasPorSede.isEmpty()) {
                    PieChart = new FXPieChart(tituloGrafico, sedes, ventas);
                    BarChart = new FXBarChart(tituloGrafico, "Sedes", sedes, "Ventas", ventas, "Ventas Realizadas");
                    LineChart = new FXLineChart(tituloGrafico, "Sedes", sedes, "Ventas", ventas, "Ventas Realizadas");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha extraido la información");
                }
            } catch(ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(null, "La consulta no puede ser realizada. "
                    + "Seleccione en el menú de opciones un periodo de consulta válido.");
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
    
    public void generarReporte(){
    
        JasperReport report;
        JasperPrint jasperPrint;
        
        ConexionBD bd = new ConexionBD();
        Connection conn = bd.conectar();

        try {

            Map<String, Object> parametros = new HashMap();         

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

            report = (JasperReport) JRLoader.loadObjectFromFile(System.getProperty("user.dir") + "\\src\\Reportes\\Cupos\\ReporteVentasSedes.jasper");
            jasperPrint = JasperFillManager.fillReport(report, parametros, conn);

            JFrame frame = new JFrame("Reporte Venta de Cupos por Sede");
            frame.setPreferredSize(new Dimension(1000, 600));
            frame.getContentPane().add(new JRViewer(jasperPrint));
            frame.pack();
            frame.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Visualizador.class.getName()).log(Level.SEVERE, null, ex);
        }
    
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

    public JLabel getLabelMesInicio() {
        return labelMesInicio;
    }

    public void setLabelMesInicio(JLabel labelMesInicio) {
        this.labelMesInicio = labelMesInicio;
    }

    public JLabel getLabelMesFin() {
        return labelMesFin;
    }

    public void setLabelMesFin(JLabel labelMesFin) {
        this.labelMesFin = labelMesFin;
    }

    public JLabel getSeparadorBoton() {
        return separadorBoton;
    }

    public void setSeparadorBoton(JLabel separadorBoton) {
        this.separadorBoton = separadorBoton;
    }
    
}