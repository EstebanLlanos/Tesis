/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Citas_Especialidad;

import ConectorBD.ConexionBD;
import Controlador.Afiliaciones.ControladorVentasCiudades;
import Controlador.Citas_Especialidad.ControladorCitasEspecialista;
import Gráficos.FXBarChart;
import Gráficos.FXLineChart;
import Gráficos.FXPieChart;
import Recursos.AutoSuggestor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Esteban
 */
public class UiCitasEspecialista {

    JComboBox comboBoxCiudades, comboBoxDepartamentos, comboBoxAnioInicio, comboBoxAnioFin, comboBoxCriterioConsulta, comboBoxMesInicio, comboBoxMesFin;
    JLabel labelDepartamento, labelCiudad, labelAnioInicio, labelAnioFin, labelCriterioConsulta, labelMesInicio, labelMesFin, labelEspecialista;
    JTextField textFieldEspecialista;
    JCheckBox busquedaNombre, busquedaOtrosCriterios;
    JLabel separadorBoton;
    JButton botonConsultar;

    AutoSuggestor autoCompletar;

    ControladorCitasEspecialista controladorCitasEspecialista;

    // Elementos de conexion de la BD para el llenado de los comboBox
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public UiCitasEspecialista(JFrame ventanaPrincipal) {

        busquedaNombre = new JCheckBox("Buscar por nombre del Especialista");
        busquedaNombre.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        busquedaNombre.setForeground(new java.awt.Color(230, 230, 255));
        busquedaNombre.setSelected(false);

        labelEspecialista = new JLabel();
        inicializarJLabel(labelEspecialista, "Especialista:                       \n");
        
        textFieldEspecialista = new JTextField();
        textFieldEspecialista.setPreferredSize(new Dimension(170, 25));
        textFieldEspecialista.setEnabled(false);
        
        busquedaNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (busquedaNombre.isSelected()) {
                    busquedaOtrosCriterios.setSelected(false);
                    textFieldEspecialista.setEnabled(true);

                    comboBoxCiudades.setEnabled(false);
                    comboBoxCiudades.setSelectedItem("Escoger una Opción...");

                    comboBoxAnioInicio.setEnabled(false);
                    comboBoxAnioInicio.setSelectedItem("Escoger una Opción...");

                    comboBoxAnioFin.setEnabled(false);
                    comboBoxAnioFin.setSelectedItem("Escoger una Opción...");

                    comboBoxDepartamentos.setEnabled(false);
                    comboBoxDepartamentos.setSelectedItem("Escoger una Opción...");

                    comboBoxCriterioConsulta.setEnabled(false);
                    comboBoxCriterioConsulta.setSelectedItem("Escoger una Opción...");
                } else {
                    busquedaOtrosCriterios.setSelected(true);

                    textFieldEspecialista.setEnabled(false);
                    textFieldEspecialista.setText("");

                    comboBoxCiudades.setEnabled(true);
                    comboBoxAnioInicio.setEnabled(true);
                    comboBoxAnioFin.setEnabled(true);
                    comboBoxDepartamentos.setEnabled(true);
                    comboBoxCriterioConsulta.setEnabled(true);
                    comboBoxCriterioConsulta.setSelectedItem("Mayor Número de Citas");
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
                    busquedaNombre.setSelected(false);

                    textFieldEspecialista.setEnabled(false);
                    textFieldEspecialista.setText("");

                    comboBoxCiudades.setEnabled(true);
                    comboBoxAnioInicio.setEnabled(true);
                    comboBoxAnioFin.setEnabled(true);
                    comboBoxDepartamentos.setEnabled(true);
                    comboBoxCriterioConsulta.setEnabled(true);
                    comboBoxCriterioConsulta.setSelectedItem("Mayor Número de Citas");
                } else {
                    busquedaNombre.setSelected(true);
                    textFieldEspecialista.setEnabled(true);

                    comboBoxCiudades.setEnabled(false);
                    comboBoxCiudades.setSelectedItem("Escoger una Opción...");

                    comboBoxAnioInicio.setEnabled(false);
                    comboBoxAnioInicio.setSelectedItem("Escoger una Opción...");

                    comboBoxAnioFin.setEnabled(false);
                    comboBoxAnioFin.setSelectedItem("Escoger una Opción...");

                    comboBoxDepartamentos.setEnabled(false);
                    comboBoxDepartamentos.setSelectedItem("Escoger una Opción...");

                    comboBoxCriterioConsulta.setEnabled(false);
                    comboBoxCriterioConsulta.setSelectedItem("Escoger una Opción...");
                }
            }
        });

        controladorCitasEspecialista = new ControladorCitasEspecialista();

        labelDepartamento = new JLabel();
        inicializarJLabel(labelDepartamento, "Departamento:                    ");

        comboBoxDepartamentos = new JComboBox();

        labelCiudad = new JLabel();
        inicializarJLabel(labelCiudad, "Ciudad:                          ");

        comboBoxCiudades = new JComboBox();

        labelAnioInicio = new JLabel();
        inicializarJLabel(labelAnioInicio, "Desde el Año:                      ");

        comboBoxAnioInicio = new JComboBox();

        comboBoxAnioInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (!comboBoxAnioInicio.getSelectedItem().equals("Escoger una Opción...")) {
                    comboBoxMesInicio.setEnabled(true);
                    comboBoxMesFin.setEnabled(true);
                    comboBoxAnioFin.setEnabled(true);
                } else {
                    comboBoxMesInicio.setEnabled(false);
                    comboBoxMesFin.setEnabled(false);
                    comboBoxAnioFin.setEnabled(false);
                }
            }
        });

        labelAnioFin = new JLabel();
        inicializarJLabel(labelAnioFin, "Hasta el Año:                           ");

        labelCriterioConsulta = new JLabel();
        inicializarJLabel(labelCriterioConsulta, "Criterio de Consulta:                      ");

        comboBoxAnioFin = new JComboBox();

        comboBoxCriterioConsulta = new JComboBox();

        botonConsultar = new JButton("Consultar");
        botonConsultar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        botonConsultar.setPreferredSize(new Dimension(120, 30));

        separadorBoton = new JLabel();
        inicializarJLabel(separadorBoton, "≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡"
                + "≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡");
        separadorBoton.setFont(new java.awt.Font("Century Gothic", 1, 6));

        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hacerConsulta(evt);
            }
        });

        labelMesInicio = new JLabel();
        inicializarJLabel(labelMesInicio, "Desde el Mes:                       ");

        comboBoxMesInicio = new JComboBox();

        labelMesFin = new JLabel();
        inicializarJLabel(labelMesFin, "Hasta el Mes:                         ");

        comboBoxMesFin = new JComboBox();

        autoCompletar = new AutoSuggestor(textFieldEspecialista, ventanaPrincipal, obtenerEspecialistas(), Color.WHITE.brighter(), Color.BLUE, Color.RED, 0.85f);
        inicializarCiudades(comboBoxCiudades);
        inicializarDepartamentos(comboBoxDepartamentos);
        inicializarAnios(comboBoxAnioInicio);
        inicializarAnios(comboBoxAnioFin);
        inicializarMeses(comboBoxMesInicio);
        inicializarMeses(comboBoxMesFin);
        inicializarCriteriosDeConsulta(comboBoxCriterioConsulta);
        comboBoxCriterioConsulta.setSelectedItem("Mayor Número de Citas");
    }

    void hacerConsulta(ActionEvent evt) {

        // Clases para el despligue Gráfico de resultados
        FXPieChart PieChart;
        FXBarChart BarChart;
        FXLineChart LineChart;

        //verificamos que el rango de estrato sea correcto
        String especialista = "" + textFieldEspecialista.getText();
        String departamento = "" + comboBoxDepartamentos.getSelectedItem();
        String ciudad = "" + comboBoxCiudades.getSelectedItem();
        String anioInicio = "" + comboBoxAnioInicio.getSelectedItem();
        String mesInicio = "" + comboBoxMesInicio.getSelectedItem();
        String mesFin = "" + comboBoxMesFin.getSelectedItem();
        String anioFin = "" + comboBoxAnioFin.getSelectedItem();
        String criterioConsulta = "" + comboBoxCriterioConsulta.getSelectedItem();

        ArrayList<String[]> citasPorEspecialista = controladorCitasEspecialista.getCitas(especialista, departamento, ciudad, anioInicio, mesInicio, mesFin, anioFin, criterioConsulta);

        if (citasPorEspecialista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Esta consulta no entregó resultados. "
                    + "No existen registros que coincidan con los filtros solicitados");

        } else if (citasPorEspecialista.get(0)[0].equals("Error")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un departamento o una sede (o ambos) para realizar la consulta");
        } else if (citasPorEspecialista.get(0)[0].equals("Error Fecha Año")) {
            JOptionPane.showMessageDialog(null, "La consulta no pudo ser realizada. Seleccione fecha de Inicio unicamente o un rango válido a consultar");
        } else if (citasPorEspecialista.get(0)[0].equals("Error Fecha Mes")) {
            JOptionPane.showMessageDialog(null, "La consulta no pudo ser realizada. Seleccione un rango de meses válido a consultar");
        } else {
            try {
                ArrayList<String> especialistas = new ArrayList();
                ArrayList<Integer> citas = new ArrayList();
                for (int i = 0; i <= citasPorEspecialista.size() - 1; i++) {
                    especialistas.add(citasPorEspecialista.get(i)[0]);
                    citas.add(Integer.parseInt(citasPorEspecialista.get(i)[1]));
                }

                if (!citasPorEspecialista.isEmpty()) {
                    PieChart = new FXPieChart("Top Especialistas", especialistas, citas);
                    BarChart = new FXBarChart("Top Especialistas", "Especialistas", especialistas, "Citas", citas, "Citas Realizadas");
                    LineChart = new FXLineChart("Top Especialistas", "Especialistas", especialistas, "Citas", citas, "Citas Realizadas");
                } else {
                    JOptionPane.showMessageDialog(null, "No se ha extraido la información");
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                JOptionPane.showMessageDialog(null, "La consulta no puede ser realizada. "
                        + "Seleccione un rango válido a consultar");
            }
        }
    }

    public ArrayList<String> obtenerEspecialistas(){
    
        BaseDeDatos = new ConexionBD();
        
        ArrayList<String> especialistas = new ArrayList<>();
        
        try {
         
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery("SELECT id_especialista, nombre_especialista "
                    + "FROM especialista;");
            
            while (tabla.next()) {
                especialistas.add(tabla.getObject(1) + ", " + tabla.getObject(2));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        return especialistas;
    }
    
    protected void inicializarCiudades(JComboBox ciudades) {
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

    protected void inicializarJLabel(JLabel label, String texto) {

        label.setText(texto);
        label.setSize(170, 30);
        label.setMinimumSize(new Dimension(170, 30));
        label.setMaximumSize(new Dimension(170, 30));
        label.setFont(new java.awt.Font("Century Gothic", 1, 14));
        label.setForeground(Color.WHITE);
    }

    protected void inicializarCriteriosDeConsulta(JComboBox criteriosDeConsulta) {

        criteriosDeConsulta.setVisible(true);
        criteriosDeConsulta.setMaximumSize(new Dimension(200, 30));

        String meses[][] = new String[3][1];
        meses[0][0] = "Escoger una Opción...";
        meses[1][0] = "Mayor Número de Citas";
        meses[2][0] = "Menor Número de Citas";

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
                    + "FROM departamento;");

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

    public JLabel getLabelEspecialista() {
        return labelEspecialista;
    }

    public void setLabelEspecialista(JLabel labelEspecialista) {
        this.labelEspecialista = labelEspecialista;
    }

    public JTextField getTextFieldEspecialista() {
        return textFieldEspecialista;
    }

    public void setTextFieldEspecialista(JTextField textFieldEspecialista) {
        this.textFieldEspecialista = textFieldEspecialista;
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
