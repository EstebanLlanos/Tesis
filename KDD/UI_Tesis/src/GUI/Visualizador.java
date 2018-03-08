package GUI;

import GUI.Afiliaciones.UiVentasCiudades;
import GUI.Afiliaciones.UiVentasSedes;
import GUI.Afiliaciones.UiVentasVendedores;
import GUI.Citas_Especialidad.UiCitasEspecialidad;
import GUI.Citas_Especialidad.UiCitasEspecialista;
import GUI.Resumenes.VisualizadorResumenes;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class Visualizador extends javax.swing.JFrame {

    String arregloPreguntas[][];
    public JComboBox comboBoxPreguntas;
    public int elementoConsultaSeleccionada = 0;
    public String elementoSeleccionado;

    public Visualizador(String elementoDeConsulta) {
        super("Interfaz de Visualización de Consultas Personalizadas - PREVISER");
        initComponents();
        this.setLocationRelativeTo(null);
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        panelOpciones.setLayout(layout);
        
        //Inicializamos y asignamos las Preguntas
        comboBoxPreguntas = new javax.swing.JComboBox<>();
        
        comboBoxPreguntas.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N 
        comboBoxPreguntas.setForeground(new java.awt.Color(0, 153, 153));
        
        elementoSeleccionado = elementoDeConsulta;
        
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
        
        comboBoxPreguntas = this.setPreguntas();
        comboBoxPreguntas.addActionListener(this::preguntasActionPerformed);
        panelPreguntas.add(comboBoxPreguntas);
    }

    private JComboBox setPreguntas() {

        if (elementoConsultaSeleccionada != 0) {

            switch (elementoConsultaSeleccionada) {
                case 1:
                    comboBoxPreguntas = new JComboBox();
                    arregloPreguntas = new String[4][1];
                    arregloPreguntas[0][0] = "Seleccione la opción de consulta que desea visualizar...";
                    arregloPreguntas[1][0] = "Venta de Afiliaciones por Ciudades";
                    arregloPreguntas[2][0] = "Venta de Afiliaciones por Vendedor";
                    arregloPreguntas[3][0] = "Venta de Afiliaciones por Sede";

                    for (String[] arregloPregunta : arregloPreguntas) {
                        comboBoxPreguntas.addItem(arregloPregunta[0]);
                    }
                    
                    break;
                
                case 2:
                    comboBoxPreguntas = new JComboBox();
                    arregloPreguntas = new String[3][1];
                    arregloPreguntas[0][0] = "Seleccione la opción de consulta que desea visualizar...";
                    arregloPreguntas[1][0] = "Citas por Especialidad";
                    arregloPreguntas[2][0] = "Citas por Especialista";

                    for (String[] arregloPregunta : arregloPreguntas) {
                        comboBoxPreguntas.addItem(arregloPregunta[0]);
                    }
                    
                    break;
                case 3:
                    comboBoxPreguntas = new JComboBox();
                    arregloPreguntas = new String[1][1];
                    arregloPreguntas[0][0] = "Seleccione la opción de consulta que desea visualizar...";

                    for (String[] arregloPregunta : arregloPreguntas) {
                        comboBoxPreguntas.addItem(arregloPregunta[0]);
                    }
                    
                    break;
                case 4:
                    comboBoxPreguntas = new JComboBox();
                    arregloPreguntas = new String[1][1];
                    arregloPreguntas[0][0] = "Seleccione la opción de consulta que desea visualizar...";

                    for (String[] arregloPregunta : arregloPreguntas) {
                        comboBoxPreguntas.addItem(arregloPregunta[0]);
                    }
                    
                    break;
                case 5:
                    comboBoxPreguntas = new JComboBox();
                    arregloPreguntas = new String[1][1];
                    arregloPreguntas[0][0] = "Seleccione la opción de consulta que desea visualizar...";

                    for (String[] arregloPregunta : arregloPreguntas) {
                        comboBoxPreguntas.addItem(arregloPregunta[0]);
                    }
                    
                    break;
                default:
                    
                    break;
            }

        } else {
            //por ahora eliminamos los componentes de la interfaz
            panelOpciones.removeAll();
            panelOpciones.updateUI();
        }
        
        return comboBoxPreguntas;
    }

    private void asignarComponentes(int codigoDePregunta) {

        //según la pregunta que se elija asignamos unos componentes a la interfaz
        if (elementoConsultaSeleccionada == 1) {

            switch (codigoDePregunta) {
                case 1:
                    UiVentasCiudades ventasPorCiudad = new UiVentasCiudades();
                    asignaComponentesVentasCiudad(ventasPorCiudad);
                    break;
                case 2:
                    UiVentasVendedores ventasPorVendedor = new UiVentasVendedores(this);
                    asignaComponentesVentasVendedor(ventasPorVendedor);
                    break;
                case 3:
                    UiVentasSedes ventasPorSede = new UiVentasSedes();
                    asignaComponentesVentasSede(ventasPorSede);
                    break;
                default:
                    panelOpciones.removeAll();
                    panelOpciones.updateUI();
                    break;
            }

        } else if (elementoConsultaSeleccionada == 2) {

            switch (codigoDePregunta) {
                case 1:
                    UiCitasEspecialidad citasPorEspecialidad = new UiCitasEspecialidad();
                    asignaComponentesCitasEspecialidad(citasPorEspecialidad);
                    break;
                case 2:
                    UiCitasEspecialista citasPorEspecialista = new UiCitasEspecialista(this);
                    asignaComponentesCitasEspecialista(citasPorEspecialista);
                    break;
                default:
                    panelOpciones.removeAll();
                    panelOpciones.updateUI();
                    break;
            }

        } else {
            //por ahora eliminamos los componentes de la interfaz
            panelOpciones.removeAll();
            panelOpciones.updateUI();
        }
    }
    
    private void asignaComponentesCitasEspecialista(UiCitasEspecialista citasPorEspecialista) {

        panelOpciones.removeAll();
        panelOpciones.add(citasPorEspecialista.getBusquedaNombre());
        panelOpciones.add(citasPorEspecialista.getBusquedaOtrosCriterios());
        panelOpciones.add(citasPorEspecialista.getLabelEspecialista());
        panelOpciones.add(citasPorEspecialista.getTextFieldEspecialista());
        panelOpciones.add(citasPorEspecialista.getLabelDepartamento());
        panelOpciones.add(citasPorEspecialista.getComboBoxDepartamentos());
        panelOpciones.add(citasPorEspecialista.getLabelCiudad());
        panelOpciones.add(citasPorEspecialista.getComboBoxCiudades());
        panelOpciones.add(citasPorEspecialista.getLabelAnioInicio());
        panelOpciones.add(citasPorEspecialista.getComboBoxAnioInicio());
        panelOpciones.add(citasPorEspecialista.getLabelMesInicio());
        panelOpciones.add(citasPorEspecialista.getComboBoxMesInicio());
        panelOpciones.add(citasPorEspecialista.getLabelMesFin());
        panelOpciones.add(citasPorEspecialista.getComboBoxMesFin());
        panelOpciones.add(citasPorEspecialista.getLabelAnioFin());
        panelOpciones.add(citasPorEspecialista.getComboBoxAnioFin());
        panelOpciones.add(citasPorEspecialista.getLabelCriterioConsulta());
        panelOpciones.add(citasPorEspecialista.getComboBoxCriterioConsulta());
        panelOpciones.add(citasPorEspecialista.getSeparadorBoton());
        panelOpciones.add(citasPorEspecialista.getBotonConsultar());
        panelOpciones.updateUI();
    }
    
    private void asignaComponentesCitasEspecialidad(UiCitasEspecialidad citasPorEspecialidad) {

        panelOpciones.removeAll();
        panelOpciones.add(citasPorEspecialidad.getLabelCiudad());
        panelOpciones.add(citasPorEspecialidad.getComboBoxCiudades());
        panelOpciones.add(citasPorEspecialidad.getLabelGenero());
        panelOpciones.add(citasPorEspecialidad.getComboBoxGenero());
        panelOpciones.add(citasPorEspecialidad.getLabelEstrato());
        panelOpciones.add(citasPorEspecialidad.getComboBoxEstrato());
        panelOpciones.add(citasPorEspecialidad.getLabelAnioInicio());
        panelOpciones.add(citasPorEspecialidad.getComboBoxAnioInicio());
        panelOpciones.add(citasPorEspecialidad.getLabelMesInicio());
        panelOpciones.add(citasPorEspecialidad.getComboBoxMesInicio());
        panelOpciones.add(citasPorEspecialidad.getLabelMesFin());
        panelOpciones.add(citasPorEspecialidad.getComboBoxMesFin());
        panelOpciones.add(citasPorEspecialidad.getLabelAnioFin());
        panelOpciones.add(citasPorEspecialidad.getComboBoxAnioFin());
        panelOpciones.add(citasPorEspecialidad.getLabelCriterioConsulta());
        panelOpciones.add(citasPorEspecialidad.getComboBoxCriterioConsulta());
        panelOpciones.add(citasPorEspecialidad.getSeparadorBoton());
        panelOpciones.add(citasPorEspecialidad.getBotonConsultar());
        panelOpciones.updateUI();
    }
    
    private void asignaComponentesVentasCiudad(UiVentasCiudades ventasPorCiudad) {

        panelOpciones.removeAll();
        panelOpciones.add(ventasPorCiudad.getLabelDepartamento());
        panelOpciones.add(ventasPorCiudad.getComboBoxDepartamentos());
        panelOpciones.add(ventasPorCiudad.getLabelSede());
        panelOpciones.add(ventasPorCiudad.getComboBoxSedes());
        panelOpciones.add(ventasPorCiudad.getLabelAnioInicio());
        panelOpciones.add(ventasPorCiudad.getComboBoxAnioInicio());
        panelOpciones.add(ventasPorCiudad.getLabelMesInicio());
        panelOpciones.add(ventasPorCiudad.getComboBoxMesInicio());
        panelOpciones.add(ventasPorCiudad.getLabelMesFin());
        panelOpciones.add(ventasPorCiudad.getComboBoxMesFin());
        panelOpciones.add(ventasPorCiudad.getLabelAnioFin());
        panelOpciones.add(ventasPorCiudad.getComboBoxAnioFin());
        panelOpciones.add(ventasPorCiudad.getLabelCriterioConsulta());
        panelOpciones.add(ventasPorCiudad.getComboBoxCriterioConsulta());
        panelOpciones.add(ventasPorCiudad.getSeparadorBoton());
        panelOpciones.add(ventasPorCiudad.getBotonConsultar());
        panelOpciones.updateUI();
    }
    
    private void asignaComponentesVentasVendedor(UiVentasVendedores ventasPorVendedor) {

        panelOpciones.removeAll();
        panelOpciones.add(ventasPorVendedor.getLabelTipoConsulta());
        panelOpciones.add(ventasPorVendedor.getBusquedaNombre());
        panelOpciones.add(ventasPorVendedor.getBusquedaOtrosCriterios());
        panelOpciones.add(ventasPorVendedor.getLabelVendedor());
        panelOpciones.add(ventasPorVendedor.getTextFieldVendedor());
        panelOpciones.add(ventasPorVendedor.getLabelCiudad());
        panelOpciones.add(ventasPorVendedor.getComboBoxCiudades());
        panelOpciones.add(ventasPorVendedor.getLabelSede());
        panelOpciones.add(ventasPorVendedor.getComboBoxSedes());
        panelOpciones.add(ventasPorVendedor.getLabelAnioInicio());
        panelOpciones.add(ventasPorVendedor.getComboBoxAnioInicio());
        panelOpciones.add(ventasPorVendedor.getLabelAnioFin());
        panelOpciones.add(ventasPorVendedor.getComboBoxAnioFin());
        panelOpciones.add(ventasPorVendedor.getLabelCriterioConsulta());
        panelOpciones.add(ventasPorVendedor.getComboBoxCriterioConsulta());
        panelOpciones.add(ventasPorVendedor.getSeparadorBoton());
        panelOpciones.add(ventasPorVendedor.getBotonConsultar());
        panelOpciones.updateUI();
    }

    private void asignaComponentesVentasSede(UiVentasSedes ventasPorSede) {

        panelOpciones.removeAll();
        panelOpciones.add(ventasPorSede.getLabelAnioInicio());
        panelOpciones.add(ventasPorSede.getComboBoxAnioInicio());
        panelOpciones.add(ventasPorSede.getLabelMesInicio());
        panelOpciones.add(ventasPorSede.getComboBoxMesInicio());
        panelOpciones.add(ventasPorSede.getLabelMesFin());
        panelOpciones.add(ventasPorSede.getComboBoxMesFin());
        panelOpciones.add(ventasPorSede.getLabelAnioFin());
        panelOpciones.add(ventasPorSede.getComboBoxAnioFin());
        panelOpciones.add(ventasPorSede.getSeparadorBoton());
        panelOpciones.add(ventasPorSede.getBotonConsultar());
        panelOpciones.updateUI();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu3 = new javax.swing.JMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jDialog1 = new javax.swing.JDialog();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        panelPreguntas = new javax.swing.JPanel();
        panelOpciones = new javax.swing.JPanel();
        panelPestanas = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        salir = new javax.swing.JMenu();
        menuSeleccionarHecho = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuResumenes = new javax.swing.JMenuItem();

        jMenu3.setText("jMenu3");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 255, 153));
        setMinimumSize(new java.awt.Dimension(600, 400));
        setResizable(false);

        panelPreguntas.setBackground(new java.awt.Color(0, 102, 153));
        panelPreguntas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Consultas Disponibles", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 16), new java.awt.Color(255, 255, 255)));
        panelPreguntas.setForeground(new java.awt.Color(255, 255, 255));
        panelPreguntas.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        panelPreguntas.setMinimumSize(new java.awt.Dimension(150, 0));
        panelPreguntas.setOpaque(false);
        panelPreguntas.setLayout(new javax.swing.BoxLayout(panelPreguntas, javax.swing.BoxLayout.LINE_AXIS));

        panelOpciones.setBackground(new java.awt.Color(0, 102, 153));
        panelOpciones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Opciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 16), new java.awt.Color(255, 255, 255))); // NOI18N
        panelOpciones.setForeground(new java.awt.Color(255, 255, 255));
        panelOpciones.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        panelOpciones.setMinimumSize(new java.awt.Dimension(150, 0));
        panelOpciones.setOpaque(false);
        panelOpciones.setLayout(new java.awt.GridLayout(1, 0));

        panelPestanas.setBackground(new java.awt.Color(204, 255, 255));
        panelPestanas.setBorder(new javax.swing.border.MatteBorder(null));
        panelPestanas.setAutoscrolls(true);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addComponent(panelOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelPestanas, javax.swing.GroupLayout.PREFERRED_SIZE, 1022, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelPreguntas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPreguntas, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                    .addComponent(panelPestanas)))
        );
        jDesktopPane2.setLayer(panelPreguntas, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(panelOpciones, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(panelPestanas, javax.swing.JLayeredPane.DEFAULT_LAYER);

        salir.setText("Archivo");
        salir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        salir.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N

        menuSeleccionarHecho.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        Image img = new ImageIcon(getClass().getResource("/Recursos/seleccionar.png")).getImage();
        ImageIcon selectIcon = new ImageIcon(getScaledImage(img, 30, 30));
        menuSeleccionarHecho.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        menuSeleccionarHecho.setIcon(selectIcon);
        menuSeleccionarHecho.setText("Seleccionar Objeto de Consulta...");
        menuSeleccionarHecho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSeleccionarHechoActionPerformed(evt);
            }
        });
        salir.add(menuSeleccionarHecho);

        jSeparator1.setMaximumSize(new java.awt.Dimension(32000, 32000));
        jSeparator1.setPreferredSize(new java.awt.Dimension(0, 5));
        salir.add(jSeparator1);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        Image exitImg = new ImageIcon(getClass().getResource("/Recursos/salir.png")).getImage();
        ImageIcon exitIcon = new ImageIcon(getScaledImage(exitImg, 30, 30));
        jMenuItem1.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jMenuItem1.setIcon(exitIcon);
        jMenuItem1.setText("Salir...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        salir.add(jMenuItem1);

        jMenuBar1.add(salir);

        jMenu1.setText("Tipo de Consulta");
        jMenu1.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N

        jMenuResumenes.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        Image resumeImg = new ImageIcon(getClass().getResource("/Recursos/resumenes.png")).getImage();
        ImageIcon resumeIcon = new ImageIcon(getScaledImage(resumeImg, 30, 30));
        jMenuResumenes.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jMenuResumenes.setIcon(resumeIcon);
        jMenuResumenes.setText("Resúmenes");
        jMenuResumenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuResumenesActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuResumenes);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menuSeleccionarHechoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSeleccionarHechoActionPerformed
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Visualizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Visualizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Visualizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Visualizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        this.panelPestanas.removeAll();
        System.out.println("Se cambia el objeto de consulta.");
        java.awt.EventQueue.invokeLater(() -> {
            SelectorElementoConsulta sec = new SelectorElementoConsulta();
            sec.setVisible(true);
            sec.setLocationRelativeTo(null);
        });
        
        try {
            this.dispose();
            this.finalize();
            this.removeAll();
        } catch (Throwable ex) {
            Logger.getLogger(Visualizador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_menuSeleccionarHechoActionPerformed

    private void jMenuResumenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuResumenesActionPerformed
        
        VisualizadorResumenes vis = new VisualizadorResumenes(elementoSeleccionado, this);
        vis.setVisible(true);
        vis.setLocationRelativeTo(null);
        this.setEnabled(false);
        
//        ResumenVentasAnuales_Ciudad p = new ResumenVentasAnuales_Ciudad();
//        p.setVisible(true);
//        p.setLocationRelativeTo(null);
    }//GEN-LAST:event_jMenuResumenesActionPerformed
    
    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
    private void preguntasActionPerformed(java.awt.event.ActionEvent evt) {

        String preguntaSeleccionada = "" + comboBoxPreguntas.getSelectedItem();
        int codigoDePregunta = 0;

        for (int i = 0; i <= arregloPreguntas.length; i++) {

            if (arregloPreguntas[i][0].equals(preguntaSeleccionada)) {
                codigoDePregunta = i;
                break;
            }
        }
        
        asignarComponentes(codigoDePregunta);
    }

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuResumenes;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JMenuItem menuSeleccionarHecho;
    private javax.swing.JPanel panelOpciones;
    public static javax.swing.JTabbedPane panelPestanas;
    private javax.swing.JPanel panelPreguntas;
    private javax.swing.JMenu salir;
    // End of variables declaration//GEN-END:variables
}
