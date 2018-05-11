/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diagrama;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban - Casa
 */
public class VisualizadorResumenes extends JFrame {

    /**
     * Creates new form VisualizadorResumenes
     */
    String arregloPreguntas[][];
    public int elementoConsultaSeleccionada = 0;
    public String elementoSeleccionado;
    JFrame ventana;
    
    public VisualizadorResumenes(String elementoConsultar, JFrame parent) {
        
        this.setAlwaysOnTop (true);
        
        elementoSeleccionado = elementoConsultar;
        
        ventana = parent;
        
        initComponents();
        
        elementoSeleccionado = elementoConsultar;
        
        if (elementoSeleccionado.equals("Afiliaciones")) {
            elementoConsultaSeleccionada = 1;
        } else if (elementoSeleccionado.equals("Citas por Especialidad")) {
            elementoConsultaSeleccionada = 2;
        } else if (elementoSeleccionado.equals("Citas por Exámenes")) {
            elementoConsultaSeleccionada = 3;
        } else if (elementoSeleccionado.equals("Citas para Otros Servicios")) {
            elementoConsultaSeleccionada = 4;
        } else if (elementoSeleccionado.equals("Quejas y Comunicaciones Directas con Clientes")) {
            elementoConsultaSeleccionada = 5;
        }
        
        comboBoxResumen = this.setPreguntas();
    }
    
    private JComboBox setPreguntas() {

        if (elementoConsultaSeleccionada != 0) {

            switch (elementoConsultaSeleccionada) {
                case 1:
                    arregloPreguntas = new String[2][1];
                    arregloPreguntas[0][0] = "Seleccione el resumen de consulta que desea visualizar...";
                    arregloPreguntas[1][0] = "Total de Ventas Anuales por Sede";

                    for (String[] arregloPregunta : arregloPreguntas) {
                        comboBoxResumen.addItem(arregloPregunta[0]);
                    }
                    comboBoxResumen.addActionListener(this::preguntasActionPerformedAfiliaciones);
                    break;
                
                case 2:
                    arregloPreguntas = new String[1][1];
                    arregloPreguntas[0][0] = "Seleccione el resumen de consulta que desea visualizar...";

                    for (String[] arregloPregunta : arregloPreguntas) {
                        comboBoxResumen.addItem(arregloPregunta[0]);
                    }
                    comboBoxResumen.addActionListener(this::preguntasActionPerformedCitasEspecialidad);
                    break;
                case 3:
                    arregloPreguntas = new String[1][1];
                    arregloPreguntas[0][0] = "Seleccione la opción de consulta que desea visualizar...";

                    for (String[] arregloPregunta : arregloPreguntas) {
                        comboBoxResumen.addItem(arregloPregunta[0]);
                    }
                    
                    break;
                case 4:
                    arregloPreguntas = new String[1][1];
                    arregloPreguntas[0][0] = "Seleccione la opción de consulta que desea visualizar...";

                    for (String[] arregloPregunta : arregloPreguntas) {
                        comboBoxResumen.addItem(arregloPregunta[0]);
                    }
                    
                    break;
                case 5:
                    arregloPreguntas = new String[1][1];
                    arregloPreguntas[0][0] = "Seleccione el resumen de consulta que desea visualizar...";

                    for (String[] arregloPregunta : arregloPreguntas) {
                        comboBoxResumen.addItem(arregloPregunta[0]);
                    }
                    
                    break;
                default:
                    
                    break;
            }
        }
        
        return comboBoxResumen;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        panelOpcionesConsultaResumen = new javax.swing.JPanel();
        comboBoxResumen = new javax.swing.JComboBox();
        panelDescripcionResumen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textDescripcion = new javax.swing.JTextArea();
        botonConsultar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelOpcionesConsultaResumen.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, new java.awt.Color(0, 0, 0), null, new java.awt.Color(0, 0, 0)), "Opciones de Consulta de Resumen", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 16), java.awt.Color.white)); // NOI18N
        panelOpcionesConsultaResumen.setOpaque(false);

        comboBoxResumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxResumenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelOpcionesConsultaResumenLayout = new javax.swing.GroupLayout(panelOpcionesConsultaResumen);
        panelOpcionesConsultaResumen.setLayout(panelOpcionesConsultaResumenLayout);
        panelOpcionesConsultaResumenLayout.setHorizontalGroup(
            panelOpcionesConsultaResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesConsultaResumenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboBoxResumen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelOpcionesConsultaResumenLayout.setVerticalGroup(
            panelOpcionesConsultaResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesConsultaResumenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboBoxResumen, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelDescripcionResumen.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED, null, java.awt.Color.black, null, new java.awt.Color(0, 0, 0)), "Descripción de Resumen", javax.swing.border.TitledBorder.RIGHT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 16), java.awt.Color.white)); // NOI18N
        panelDescripcionResumen.setOpaque(false);

        textDescripcion.setEditable(false);
        textDescripcion.setBackground(new java.awt.Color(0, 97, 133));
        textDescripcion.setColumns(20);
        textDescripcion.setFont(new java.awt.Font("Footlight MT Light", 1, 16)); // NOI18N
        textDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        textDescripcion.setRows(5);
        textDescripcion.setText("Proceda a seleccionar el resumen que desea consultar en torno a las\nAfilaciones.");
        textDescripcion.setAutoscrolls(false);
        textDescripcion.setMaximumSize(new java.awt.Dimension(244, 79));
        textDescripcion.setMinimumSize(new java.awt.Dimension(244, 79));
        textDescripcion.setOpaque(false);
        jScrollPane1.setViewportView(textDescripcion);

        javax.swing.GroupLayout panelDescripcionResumenLayout = new javax.swing.GroupLayout(panelDescripcionResumen);
        panelDescripcionResumen.setLayout(panelDescripcionResumenLayout);
        panelDescripcionResumenLayout.setHorizontalGroup(
            panelDescripcionResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        panelDescripcionResumenLayout.setVerticalGroup(
            panelDescripcionResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        botonConsultar.setBackground(new java.awt.Color(0, 175, 106));
        botonConsultar.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        botonConsultar.setForeground(new java.awt.Color(255, 255, 255));
        botonConsultar.setText("Consultar");
        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarActionPerformed(evt);
            }
        });

        botonCancelar.setBackground(new java.awt.Color(204, 94, 82));
        botonCancelar.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        jDesktopPane1.setLayer(panelOpcionesConsultaResumen, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(panelDescripcionResumen, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(botonConsultar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(botonCancelar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelOpcionesConsultaResumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelDescripcionResumen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addComponent(botonConsultar)
                .addGap(111, 111, 111)
                .addComponent(botonCancelar)
                .addContainerGap(138, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelOpcionesConsultaResumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelDescripcionResumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonConsultar)
                    .addComponent(botonCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxResumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxResumenActionPerformed
        
    }//GEN-LAST:event_comboBoxResumenActionPerformed

    private void botonConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarActionPerformed
        String resumenSeleccionado = "" + comboBoxResumen.getSelectedItem();
        
        if (resumenSeleccionado.equals("Seleccione la opción de consulta que desea visualizar...")){
            JOptionPane.showMessageDialog(null, "La consulta no pudo ser realizada. "
                    + "Ningún resumen ha sido seleccionado. Seleccione uno de los resúmenes "
                    + "disponibles en el menú");
        }
    }//GEN-LAST:event_botonConsultarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        ventana.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void preguntasActionPerformedAfiliaciones(java.awt.event.ActionEvent evt) {

        String resumenSeleccionado = "" + comboBoxResumen.getSelectedItem();
        if (resumenSeleccionado.equals("Total de Ventas Anuales por Sede")) {
            textDescripcion.setText("En esta consulta encontrará el resumen consolidado"
                    + " del total de \nventas realizadas por cada sede hasta la fecha actual.");

            botonConsultar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    ResumenVentasAnuales_Ciudad resumenVentas_Ciudades = new ResumenVentasAnuales_Ciudad();
                    resumenVentas_Ciudades.setVisible(true);
                    resumenVentas_Ciudades.setLocationRelativeTo(null);
                }
            });
        }
        
    }
    
    private void preguntasActionPerformedCitasEspecialidad(java.awt.event.ActionEvent evt) {

        String resumenSeleccionado = "" + comboBoxResumen.getSelectedItem();
        
        if (resumenSeleccionado.equals("Seleccione el resumen de consulta que desea visualizar...")) {
            elementoConsultaSeleccionada = 1;
        }
        //escribirDescripcion(codigoDePregunta);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonConsultar;
    private javax.swing.JComboBox comboBoxResumen;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelDescripcionResumen;
    private javax.swing.JPanel panelOpcionesConsultaResumen;
    private javax.swing.JTextArea textDescripcion;
    // End of variables declaration//GEN-END:variables
}
