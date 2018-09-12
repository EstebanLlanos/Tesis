/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Cupos;

import ConectorBD.ConexionBD;
import Controlador.Cupos.ControladorVentasCiudades;
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

/*
 * @author Esteban
 */

public class UiVentasCiudades {
    
    JComboBox comboBoxDepartamentos, comboBoxSedes, comboBoxAnioInicio, comboBoxAnioFin, comboBoxCriterioConsulta, comboBoxMesInicio, comboBoxMesFin;
    JLabel labelDepartamento, labelSede, labelAnioInicio, labelAnioFin, labelCriterioConsulta, labelMesInicio, labelMesFin;
    JLabel separadorBoton;
    JButton botonConsultar;
    
    // Clases para el despligue Gráfico de resultados

    FXPieChart PieChart;
    FXBarChart BarChart;
    FXLineChart LineChart;
    
    ControladorVentasCiudades controladorVentasCiudad;
    
    // Elementos de conexion de la BD para el llenado de los comboBox
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public UiVentasCiudades() {

        controladorVentasCiudad = new ControladorVentasCiudades();
        
        labelDepartamento = new JLabel();
        inicializarJLabel(labelDepartamento, "Departamento:                    ");

        comboBoxDepartamentos = new JComboBox();
        comboBoxDepartamentos.setToolTipText("Seleccione un departamento para conocer\n"
                + " las ciudades con mayor o menor número de ventas, según el criterio\n"
                + " de consulta que haya definido.");
        
        labelSede = new JLabel();
        inicializarJLabel(labelSede, "Sede:                          ");
        
        labelAnioInicio = new JLabel();
        inicializarJLabel(labelAnioInicio, "Desde el Año:                      ");
        
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
        inicializarJLabel(labelAnioFin, "Hasta el Año:                           ");
        
        labelCriterioConsulta = new JLabel();
        inicializarJLabel(labelCriterioConsulta, "Criterio de Consulta:                      ");
        
        comboBoxAnioFin = new JComboBox();
        comboBoxAnioFin.setToolTipText("Seleccione un año final para completar \n"
                + " el rango dentro del cual desea conocer las ciudades con mayor\n"
                + " o menor número de ventas, según el criterio que haya definido.");
        
        comboBoxSedes = new JComboBox();
        comboBoxSedes.setToolTipText("Seleccione una sede para conocer las\n"
                + " ciudades con mayor o menor número de ventas, según el\n"
                + " criterio de consulta que haya definido.");
        
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
        inicializarJLabel(labelMesInicio, "Desde el Mes:                       ");
        
        comboBoxMesInicio = new JComboBox();
        comboBoxMesInicio.setToolTipText("Seleccione un mes inicial dentro del\n"
                + " año seleccionado para conocer las ciudades con mayor o menor\n"
                + " número de ventas a partir de este, según el criterio de consulta\n"
                + " que haya definido.");
        
        labelMesFin = new JLabel();
        inicializarJLabel(labelMesFin, "Hasta el Mes:                         ");
        
        comboBoxMesFin = new JComboBox();
        comboBoxMesFin.setToolTipText("Seleccione un mes final para completar \n"
                + " el rango dentro del cual desea conocer las ciudades con mayor\n"
                + " o menor número de ventas, según el criterio que haya definido.");
        
        inicializarDepartamentos(comboBoxDepartamentos);
        inicializarSedes(comboBoxSedes);
        inicializarAnios(comboBoxAnioInicio);
        inicializarAnios(comboBoxAnioFin);
        inicializarMeses(comboBoxMesInicio);
        inicializarMeses(comboBoxMesFin);
        inicializarCriteriosDeConsulta(comboBoxCriterioConsulta);
    }

    void hacerConsulta(ActionEvent evt) {
        
        //verificamos que el rango de estrato sea correcto

        String departamento = "" + comboBoxDepartamentos.getSelectedItem();
        String sede = "" + comboBoxSedes.getSelectedItem();
        String anioInicio = "" + comboBoxAnioInicio.getSelectedItem();
        String mesInicio = "" + comboBoxMesInicio.getSelectedItem();
        String mesFin = "" + comboBoxMesFin.getSelectedItem();
        String anioFin = "" + comboBoxAnioFin.getSelectedItem();
        String criterioConsulta = "" + comboBoxCriterioConsulta.getSelectedItem();
        
        String tituloGraficos = "";
        
        if (!departamento.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas en el Departamento del " + comboBoxDepartamentos.getSelectedItem();
        }
        
        if (!departamento.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas en el Departamento del " + comboBoxDepartamentos.getSelectedItem();
        }
        
        if (!sede.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para la " + comboBoxSedes.getSelectedItem();
        }
        
        if (!sede.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para la " + comboBoxSedes.getSelectedItem();
        }
        
        if (!departamento.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas en el Departamento " + comboBoxDepartamentos.getSelectedItem() + " para la " + comboBoxSedes.getSelectedItem();
        }
        
        if (!departamento.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas en el Departamento " + comboBoxDepartamentos.getSelectedItem() + " para la " + comboBoxSedes.getSelectedItem();
        }
        
        if (!departamento.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas en el Departamento " + comboBoxDepartamentos.getSelectedItem() + " para la " + comboBoxSedes.getSelectedItem() + "\n                                                 durante el periodo seleccionado";
        }
        
        if (!departamento.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas en el Departamento " + comboBoxDepartamentos.getSelectedItem() + " para la " + comboBoxSedes.getSelectedItem() + "\n                                                 durante el periodo seleccionado";
        }
        
        if (departamento.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para la " + comboBoxSedes.getSelectedItem() + "\n                                                durante el periodo seleccionado";
        }
        
        if (departamento.equals("Escoger una Opción...") && !sede.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para la " + comboBoxSedes.getSelectedItem() + "\n                                                durante el periodo seleccionado";
        }
        
        if (!departamento.equals("Escoger una Opción...") && sede.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Mayor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Mayor Número de Ventas para el Departamento " + comboBoxDepartamentos.getSelectedItem() + "\n                                                durante el periodo seleccionado";
        }
        
        if (!departamento.equals("Escoger una Opción...") && sede.equals("Escoger una Opción...") && !anioInicio.equals("Escoger una Opción...") && criterioConsulta.equals("Menor Número de Ventas")) {
            tituloGraficos = "Top 5 de Ciudades con Menor Número de Ventas para el Departamento " + comboBoxDepartamentos.getSelectedItem() + "\n                                                durante el periodo seleccionado";
        }

        ArrayList <String[]> ventasPorCiudad = controladorVentasCiudad.getVentas(departamento, sede, anioInicio, mesInicio, mesFin, anioFin, criterioConsulta);

        if (ventasPorCiudad.isEmpty()) {            
            JOptionPane.showMessageDialog(null, "Esta consulta no entregó resultados. "
                    + "No existen registros que coincidan con los filtros solicitados");
            
        } else if(ventasPorCiudad.get(0)[0].equals("Error")){            
            JOptionPane.showMessageDialog(null, "Debe seleccionar un departamento o una sede (o ambos) para realizar la consulta");
        } else if(ventasPorCiudad.get(0)[0].equals("Error Fecha Año")){            
            JOptionPane.showMessageDialog(null, "La consulta no pudo ser realizada. Seleccione fecha de Inicio unicamente o un rango válido a consultar");
        } else if(ventasPorCiudad.get(0)[0].equals("Error Fecha Mes")){            
            JOptionPane.showMessageDialog(null, "La consulta no pudo ser realizada. Seleccione un rango de meses válido a consultar");
        }else {
            try{ 
                ArrayList<String> ciudades = new ArrayList();
                ArrayList<Integer> ventas = new ArrayList();
                
                for (int i = 0; i <= ventasPorCiudad.size() - 1; i++) {
                    ciudades.add(ventasPorCiudad.get(i)[0]);
                    ventas.add(Integer.parseInt(ventasPorCiudad.get(i)[1]));
                }

                if (!ventasPorCiudad.isEmpty()) {                    
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

    protected void inicializarDepartamentos(JComboBox departamentos) {

        BaseDeDatos = new ConexionBD();
        
        departamentos.setVisible(true);
        departamentos.setMaximumSize(new Dimension(250, 30));
        
        ArrayList<String> listaDepartamentos = new ArrayList();
        
        try {
         
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery("SELECT nombre_departamento "
                    + "FROM dim_departamento;");

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
