/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Resumenes;

import ConectorBD.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javax.swing.JOptionPane;

/**
 *
 * @author Miguel Torrres
 */
public class ResumenVentasAnuales_Ciudad extends javax.swing.JFrame {

    /**
     * Creates new form ResumenVentasAnuales_Ciudad
     */
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;
    
    public ResumenVentasAnuales_Ciudad() {
        
        BaseDeDatos = new ConexionBD();
        conn = BaseDeDatos.conectar();
        ResultSet resultados;
        ArrayList<String> ventasAnuales = new ArrayList();
        
        ArrayList<String[]> conteoVentas = new ArrayList<String[]>();
        String sql_select;
        
        initComponents();
        
        JFXPanel jfx = new JFXPanel();
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
         xAxis.setLabel("Año");
        final LineChart<String,Number> lineChart = 
                new LineChart<String,Number>(xAxis,yAxis);
       
        lineChart.setTitle("Registro Anual de Ventas");
                          
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("SEDE CALI");
        
        sql_select = "SELECT * FROM (SELECT sd.nombre_sede, fch.anio_actual, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv "
                + "INNER JOIN sede sd ON (dv.sede_venta = sd.id_sede)"
                + "INNER JOIN dim_fecha fch ON (dv.fecha_venta = fch.id_dim_fecha)"
                + "GROUP BY fch.anio_actual, sd.nombre_sede "
                + "ORDER BY fch.anio_actual) AS consulta WHERE nombre_sede = 'SEDE CALI' ORDER BY anio_actual ASC LIMIT 7;";

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while (tabla.next()) {
                series1.getData().add(new XYChart.Data(tabla.getString("anio_actual"),Integer.parseInt(tabla.getString("total_ventas"))));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("SEDE PALMIRA");
        
        sql_select = "SELECT * FROM (SELECT sd.nombre_sede, fch.anio_actual, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv "
                + "INNER JOIN sede sd ON (dv.sede_venta = sd.id_sede)"
                + "INNER JOIN dim_fecha fch ON (dv.fecha_venta = fch.id_dim_fecha)"
                + "GROUP BY fch.anio_actual, sd.nombre_sede "
                + "ORDER BY fch.anio_actual) AS consulta WHERE nombre_sede = 'SEDE PALMIRA' ORDER BY anio_actual ASC LIMIT 7;";

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while (tabla.next()) {
                series2.getData().add(new XYChart.Data(tabla.getString("anio_actual"),Integer.parseInt(tabla.getString("total_ventas"))));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        XYChart.Series series3 = new XYChart.Series();
        series3.setName("SEDE TULUA");
        
        sql_select = "SELECT * FROM (SELECT sd.nombre_sede, fch.anio_actual, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv "
                + "INNER JOIN sede sd ON (dv.sede_venta = sd.id_sede)"
                + "INNER JOIN dim_fecha fch ON (dv.fecha_venta = fch.id_dim_fecha)"
                + "GROUP BY fch.anio_actual, sd.nombre_sede "
                + "ORDER BY fch.anio_actual) AS consulta WHERE nombre_sede = 'SEDE TULUA' ORDER BY anio_actual ASC LIMIT 7;";

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while (tabla.next()) {
                series3.getData().add(new XYChart.Data(tabla.getString("anio_actual"),Integer.parseInt(tabla.getString("total_ventas"))));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        XYChart.Series series4 = new XYChart.Series();
        series4.setName("SEDE ARMENIA");
        
        sql_select = "SELECT * FROM (SELECT sd.nombre_sede, fch.anio_actual, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv "
                + "INNER JOIN sede sd ON (dv.sede_venta = sd.id_sede)"
                + "INNER JOIN dim_fecha fch ON (dv.fecha_venta = fch.id_dim_fecha)"
                + "GROUP BY fch.anio_actual, sd.nombre_sede "
                + "ORDER BY fch.anio_actual) AS consulta WHERE nombre_sede = 'SEDE ARMENIA' ORDER BY anio_actual ASC LIMIT 7;";

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while (tabla.next()) {
                series4.getData().add(new XYChart.Data(tabla.getString("anio_actual"),Integer.parseInt(tabla.getString("total_ventas"))));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        XYChart.Series series5 = new XYChart.Series();
        series5.setName("SEDE PEREIRA");
        
        sql_select = "SELECT * FROM (SELECT sd.nombre_sede, fch.anio_actual, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv "
                + "INNER JOIN sede sd ON (dv.sede_venta = sd.id_sede)"
                + "INNER JOIN dim_fecha fch ON (dv.fecha_venta = fch.id_dim_fecha)"
                + "GROUP BY fch.anio_actual, sd.nombre_sede "
                + "ORDER BY fch.anio_actual) AS consulta WHERE nombre_sede = 'SEDE PEREIRA' ORDER BY anio_actual ASC LIMIT 7;";

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while (tabla.next()) {
                series5.getData().add(new XYChart.Data(tabla.getString("anio_actual"),Integer.parseInt(tabla.getString("total_ventas"))));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        Scene scene  = new Scene(lineChart,1200,1000);       
        lineChart.getData().addAll(series1, series2, series3, series4, series5);
        
        jfx.setScene(scene);
        jfx.setVisible(true);
        
        jPanelPrueba.addTab("Resumen Prueba", jfx);
        jPanelFondo.setVisible(true);
        jPanelPrueba.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelFondo = new javax.swing.JDesktopPane();
        jPanelPrueba = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelFondo.setLayer(jPanelPrueba, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanelFondoLayout = new javax.swing.GroupLayout(jPanelFondo);
        jPanelFondo.setLayout(jPanelFondoLayout);
        jPanelFondoLayout.setHorizontalGroup(
            jPanelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPrueba, javax.swing.GroupLayout.DEFAULT_SIZE, 1302, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelFondoLayout.setVerticalGroup(
            jPanelFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFondoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelPrueba, javax.swing.GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelFondo)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelFondo)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jPanelFondo;
    private javax.swing.JTabbedPane jPanelPrueba;
    // End of variables declaration//GEN-END:variables
}
