
package GUI.Quejas_Institucion;

import ConectorBD.ConexionBD;
import Controlador.Quejas_Institucion.ControladorQuejasInstitucion;
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

public class UiQuejasInstitucion {
    
    JComboBox comboBoxCiudades, comboBoxDepartamento, comboBoxCalificacion, comboBoxAnioInicio, comboBoxAnioFin, comboBoxMesInicio, comboBoxMesFin, comboBoxCriterioConsulta;
    JLabel labelCiudad, labelDepartamento, labelCalificacion, labelAnioInicio, labelAnioFin, labelCriterioConsulta, labelMesInicio, labelMesFin;
    JLabel separadorBoton;
    JButton botonConsultar;
    
    // Clases para el despligue Gráfico de resultados
    
    FXPieChart PieChart;
    FXBarChart BarChart;
    FXLineChart LineChart;
    
    ControladorQuejasInstitucion controladorQuejasInstitucion;
    
    // Elementos de conexion de la BD para el llenado de los comboBox
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;

    public UiQuejasInstitucion() {

        controladorQuejasInstitucion = new ControladorQuejasInstitucion();

        labelCiudad = new JLabel();
        inicializarJLabel(labelCiudad, "Ciudad:                      ");

        comboBoxCiudades = new JComboBox();
        
        labelDepartamento = new JLabel();
        inicializarJLabel(labelDepartamento, "Departamento:                      ");

        comboBoxDepartamento = new JComboBox();
        
        labelCalificacion = new JLabel();
        inicializarJLabel(labelCalificacion, "Calificacion:                     ");
        
        comboBoxCalificacion = new JComboBox();
        
        labelAnioInicio = new JLabel();
        inicializarJLabel(labelAnioInicio, "Desde el Año:                   ");
        
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
        inicializarJLabel(labelAnioFin, "Hasta el Año:                    ");
        
        labelCriterioConsulta = new JLabel();
        inicializarJLabel(labelCriterioConsulta, "Criterio de Consulta:                   ");
        
        comboBoxAnioFin = new JComboBox();
        
        comboBoxCriterioConsulta = new JComboBox();

        botonConsultar = new JButton("Consultar");
        botonConsultar.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        botonConsultar.setPreferredSize(new Dimension(120, 30));

        separadorBoton = new JLabel();
        inicializarJLabel(separadorBoton, "≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡≡");
        separadorBoton.setFont(new java.awt.Font("Century Gothic", 1, 6));
        
        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hacerConsulta(evt);
            }
        });
        
        labelMesInicio = new JLabel();
        inicializarJLabel(labelMesInicio, "Desde el Mes:                         ");
        
        comboBoxMesInicio = new JComboBox();  
        
        labelMesFin = new JLabel();
        inicializarJLabel(labelMesFin, "Hasta el Mes:                            ");
        
        comboBoxMesFin = new JComboBox();  
        
        inicializarCiudades(comboBoxCiudades);
        inicializarDepartamentos(comboBoxDepartamento);
        inicializarCalificaciones(comboBoxCalificacion);
        inicializarAnios(comboBoxAnioInicio);
        inicializarAnios(comboBoxAnioFin);
        inicializarMeses(comboBoxMesInicio);
        inicializarMeses(comboBoxMesFin);
        inicializarCriteriosDeConsulta(comboBoxCriterioConsulta);
    }

    void hacerConsulta(ActionEvent evt) {
        
        //verificamos que el rango de estrato sea correcto

        String ciudad = "" + comboBoxCiudades.getSelectedItem();
        String departamento = "" + comboBoxDepartamento.getSelectedItem();
        String calificacion = "" + comboBoxCalificacion.getSelectedItem();
        String anioInicio = "" + comboBoxAnioInicio.getSelectedItem();
        String mesInicio = "" + comboBoxMesInicio.getSelectedItem();
        String mesFin = "" + comboBoxMesFin.getSelectedItem();
        String anioFin = "" + comboBoxAnioFin.getSelectedItem();
        String criterioConsulta = "" + comboBoxCriterioConsulta.getSelectedItem();

        String tituloGraficos = "";
        
        if (!ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                calificacion.equals("Escoger una Opción...") &&
                anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas en la Ciudad de " + comboBoxCiudades.getSelectedItem();
        } else if (!ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                calificacion.equals("Escoger una Opción...") &&
                anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas en la Ciudad de " + comboBoxCiudades.getSelectedItem();
        }
        
        if (!ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas en la Ciudad de " + comboBoxCiudades.getSelectedItem() + " con Calificacion " + comboBoxCalificacion.getSelectedItem();
        } else if (!ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas en la Ciudad de " + comboBoxCiudades.getSelectedItem() + " con Calificacion " + comboBoxCalificacion.getSelectedItem();
        }
        
        if (!ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas en la Ciudad de " + comboBoxCiudades.getSelectedItem() + " en el periodo seleccionado";
        } else if (!ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas en la Ciudad de " + comboBoxCiudades.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (!ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas en la Ciudad de " + comboBoxCiudades.getSelectedItem() + " con la Calificación " + comboBoxCalificacion.getSelectedItem() + " en el periodo seleccionado";
        } else if (!ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas en la Ciudad de " + comboBoxCiudades.getSelectedItem() + " con la Calificación " + comboBoxCalificacion.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (ciudad.equals("Escoger una Opción...") &&
                !departamento.equals("Escoger una Opción...") &&
                calificacion.equals("Escoger una Opción...") &&
                anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas en la Departamento de " + comboBoxDepartamento.getSelectedItem();
        } else if (ciudad.equals("Escoger una Opción...") &&
                !departamento.equals("Escoger una Opción...") &&
                calificacion.equals("Escoger una Opción...") &&
                anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas en la Departamento de " + comboBoxDepartamento.getSelectedItem();
        }
        
        if (ciudad.equals("Escoger una Opción...") &&
                !departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas en la Departamento de " + comboBoxDepartamento.getSelectedItem() + " con la Calificación " + comboBoxCalificacion.getSelectedItem();
        } else if (ciudad.equals("Escoger una Opción...") &&
                !departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas en la Departamento de " + comboBoxDepartamento.getSelectedItem() + " con la Calificación " + comboBoxCalificacion.getSelectedItem();
        }
        
        if (ciudad.equals("Escoger una Opción...") &&
                !departamento.equals("Escoger una Opción...") &&
                calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas en la Departamento de " + comboBoxDepartamento.getSelectedItem() + " en el periodo seleccionada";
        } else if (ciudad.equals("Escoger una Opción...") &&
                !departamento.equals("Escoger una Opción...") &&
                calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas en la Departamento de " + comboBoxDepartamento.getSelectedItem() + " en el periodo seleccionada";
        }
        
        if (ciudad.equals("Escoger una Opción...") &&
                !departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas en la Departamento de " + comboBoxDepartamento.getSelectedItem() + " con la Calificación " + comboBoxCalificacion.getSelectedItem() + " en el periodo seleccionado";
        } else if (ciudad.equals("Escoger una Opción...") &&
                !departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas en la Departamento de " + comboBoxDepartamento.getSelectedItem() + " con la Calificación " + comboBoxCalificacion.getSelectedItem() + " en el periodo seleccionado";
        }
        
        if (ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas con la Calificación " + comboBoxCalificacion.getSelectedItem();
        } else if (ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas con la Calificación " + comboBoxCalificacion.getSelectedItem();
        }
        
        if (ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas en el periodo seleccionado";
        } else if (ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas en el periodo seleccionado";
        }
        
        if (ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Más Solicitadas")) {
            tituloGraficos = "Instituciones con Mayor Número de Quejas con la Calificacion " + comboBoxCalificacion.getSelectedItem() + " en el periodo seleccionado";
        } else if (ciudad.equals("Escoger una Opción...") &&
                departamento.equals("Escoger una Opción...") &&
                !calificacion.equals("Escoger una Opción...") &&
                !anioInicio.equals("Escoger una Opción...") &&
                criterioConsulta.equals("Instituciones Menos Solicitadas")) {
            tituloGraficos = "Instituciones con Menor Número de Quejas con la Calificacion " + comboBoxCalificacion.getSelectedItem() + " en el periodo seleccionado";
        }
        
        ArrayList <String[]> solicitudesPorInstitucion = controladorQuejasInstitucion.getQuejas(calificacion, departamento, ciudad, anioInicio, mesInicio, mesFin, anioFin, criterioConsulta);

        if (solicitudesPorInstitucion.isEmpty()) {            
            JOptionPane.showMessageDialog(null, "Esta consulta no entregó resultados. "
                    + "No existen registros que coincidan con los filtros solicitados");
            
        } else if(solicitudesPorInstitucion.get(0)[0].equals("Error")){            
            JOptionPane.showMessageDialog(null, "Debe seleccionar uno o más de los filtros disponibles para realizar la consulta.");
        } else if(solicitudesPorInstitucion.get(0)[0].equals("Error Fecha Año")){            
            JOptionPane.showMessageDialog(null, "La consulta no pudo ser realizada. Seleccione Año de Inicio unicamente o un rango válido a consultar.");
        } else if(solicitudesPorInstitucion.get(0)[0].equals("Error Fecha Mes")){            
            JOptionPane.showMessageDialog(null, "La consulta no pudo ser realizada. Seleccione un rango de meses válido a consultar.");
        }else {
            try{ 
                ArrayList<String> instituciones = new ArrayList();
                ArrayList<Integer> quejas = new ArrayList();
                for (int i = 0; i <= solicitudesPorInstitucion.size() - 1; i++) {
                    instituciones.add(solicitudesPorInstitucion.get(i)[0]);
                    quejas.add(Integer.parseInt(solicitudesPorInstitucion.get(i)[1]));
                }

                if (!solicitudesPorInstitucion.isEmpty()) {                    
                    PieChart = new FXPieChart(tituloGraficos, instituciones, quejas);
                    BarChart = new FXBarChart(tituloGraficos, "Instituciones", instituciones, "Solicitudes", quejas, "Solicitudes Hechas");
                    LineChart = new FXLineChart(tituloGraficos, "Instituciones", instituciones, "Solicitudes", quejas, "Solicitudes Hechas");
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
        criteriosDeConsulta.setSize(new Dimension(250, 30));
        criteriosDeConsulta.setMaximumSize(new Dimension(250, 30));

        String criterios[][] = new String[2][1];
        criterios[0][0] = "Instituciones Más Solicitadas";
        criterios[1][0] = "Instituciones Menos Solicitadas";

        for (int i = 0; i < criterios.length; i++) {
            criteriosDeConsulta.addItem(criterios[i][0]);
        }
    
    }
    
    protected void inicializarMeses(JComboBox entradaMeses) {

        entradaMeses.setVisible(true);
        entradaMeses.setSize(new Dimension(300, 30));
        entradaMeses.setMaximumSize(new Dimension(300, 30));

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

    protected void inicializarCiudades(JComboBox ciudades){
        BaseDeDatos = new ConexionBD();
        
        ciudades.setVisible(true);
        ciudades.setSize(new Dimension(300, 30));
        ciudades.setMaximumSize(new Dimension(300, 30));
        
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

    protected void inicializarAnios(JComboBox anioVentas) {

        anioVentas.setVisible(true);
        anioVentas.setSize(new Dimension(300, 30));
        anioVentas.setMaximumSize(new Dimension(300, 30));

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
    
    protected void inicializarCalificaciones(JComboBox calificacionQueja) {

        calificacionQueja.setVisible(true);
        calificacionQueja.setSize(new Dimension(300, 30));
        calificacionQueja.setMaximumSize(new Dimension(300, 30));

        String[][] anios = new String[4][1];
        anios[0][0] = "Escoger una Opción...";
        anios[1][0] = "2012";
        anios[2][0] = "2013";
        anios[3][0] = "2014";

        for (int i = 0; i < anios.length; i++) {
            calificacionQueja.addItem(anios[i][0]);
        }
    }
    
    protected void inicializarTiposServicio(JComboBox tipoServicioSolicitudes) {

        tipoServicioSolicitudes.setVisible(true);
        tipoServicioSolicitudes.setSize(new Dimension(300, 30));
        tipoServicioSolicitudes.setMaximumSize(new Dimension(300, 30));

        String[][] tiposServicio = new String[5][1];
        tiposServicio[0][0] = "Escoger una Opción...";
        tiposServicio[1][0] = "SALUD";
        tiposServicio[2][0] = "RECREACION";
        tiposServicio[3][0] = "COMERCIAL";
        tiposServicio[4][0] = "EDUCACION";

        for (int i = 0; i < tiposServicio.length; i++) {
            tipoServicioSolicitudes.addItem(tiposServicio[i][0]);
        }
    }

    public JComboBox getComboBoxDepartamento() {
        return comboBoxDepartamento;
    }

    public void setComboBoxDepartamento(JComboBox comboBoxDepartamento) {
        this.comboBoxDepartamento = comboBoxDepartamento;
    }

    public JComboBox getComboBoxCalificacion() {
        return comboBoxCalificacion;
    }

    public void setComboBoxCalificacion(JComboBox comboBoxCalificacion) {
        this.comboBoxCalificacion = comboBoxCalificacion;
    }

    public JLabel getLabelDepartamento() {
        return labelDepartamento;
    }

    public void setLabelDepartamento(JLabel labelDepartamento) {
        this.labelDepartamento = labelDepartamento;
    }

    public JLabel getLabelCalificacion() {
        return labelCalificacion;
    }

    public void setLabelCalificacion(JLabel labelCalificacion) {
        this.labelCalificacion = labelCalificacion;
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
}
