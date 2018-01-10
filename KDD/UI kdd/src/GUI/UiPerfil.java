package GUI;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author john En esta clase se van a poner todos los componentes de la
 * interfaz gráfica que estén relacionados con las preguntas sobre "Perfiles"
 */
public abstract class UiPerfil {

    protected JCheckBox checkBoxSexoFemenino, checkBoxSexoMasculino, checkBoxDatos, checkBoxPrepagoVoz, checkBoxPostpagoVoz, checkBoxCorporativo;
    protected JComboBox comboBoxEstadoCivil, comboBoxInicioEstrato, comboBoxFinEstrato, comboBoxOperador, comboBoxMeses, comboBoxInicioAnios, comboBoxFinAnios;
    protected JLabel labelSexo, labelEstadoCivil, labelInicioEstrato, labelFinEstrato, labelVacia, labelOperador, labelPlanDeDatos, labelPlanDeVoz, labelMeses, labelAnioInicio, labelAnioFin;
    protected JButton botonConsultar;

    public UiPerfil() {

        labelSexo = new JLabel();
        inicializarJLabel(labelSexo, "Sexo:          ");

        checkBoxSexoFemenino = new JCheckBox("Femenino");
        checkBoxSexoFemenino.setSelected(true);
        checkBoxSexoMasculino = new JCheckBox("Masculino");
        checkBoxSexoMasculino.setSelected(true);

        comboBoxEstadoCivil = new JComboBox();
        comboBoxInicioEstrato = new JComboBox();
        comboBoxFinEstrato = new JComboBox();
        comboBoxOperador = new JComboBox();
        comboBoxMeses = new JComboBox();
        
        labelMeses = new JLabel();
        inicializarJLabel(labelMeses, "Meses:");
        comboBoxInicioAnios = new JComboBox();
        comboBoxFinAnios = new JComboBox();

        labelEstadoCivil = new JLabel();
        inicializarJLabel(labelEstadoCivil, "Estado Civil:");

        labelInicioEstrato = new JLabel();
        inicializarJLabel(labelInicioEstrato, "Estrato:");

        labelFinEstrato = new JLabel();
        inicializarJLabel(labelFinEstrato, " Hasta:");

        labelVacia = new JLabel();
        inicializarJLabel(labelVacia, "        ");

        labelOperador = new JLabel();
        inicializarJLabel(labelOperador, " Operador:");

        labelPlanDeDatos = new JLabel();
        inicializarJLabel(labelPlanDeDatos, " Plan de Datos:");

        labelAnioInicio = new JLabel();
        inicializarJLabel(labelAnioInicio, " Año Inicio:");

        labelAnioFin = new JLabel();
        inicializarJLabel(labelAnioFin, " Año Fin:");

        checkBoxDatos = new JCheckBox("Sí");
        checkBoxDatos.setSelected(true);

        labelPlanDeVoz = new JLabel();
        inicializarJLabel(labelPlanDeVoz, " Plan de Voz:");

        checkBoxPrepagoVoz = new JCheckBox("Planes Prepago");
        checkBoxPrepagoVoz.setSelected(true);
        checkBoxPostpagoVoz = new JCheckBox("Planes Postpago");
        checkBoxPostpagoVoz.setSelected(true);
        checkBoxCorporativo = new JCheckBox("Planes Corporativos");
        checkBoxCorporativo.setSelected(true);

        botonConsultar = new JButton("Consultar");

        botonConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hacerConsulta(evt);
            }
        });

        inicializarCheckBox(checkBoxSexoFemenino);
        inicializarCheckBox(checkBoxSexoMasculino);
        inicializarCheckBox(checkBoxDatos);
        inicializarCheckBox(checkBoxPrepagoVoz);
        inicializarCheckBox(checkBoxPostpagoVoz);
        inicializarCheckBox(checkBoxCorporativo);
        inicializarEstadoCivil(comboBoxEstadoCivil);
        inicializarEstrato(comboBoxInicioEstrato);
        inicializarEstrato(comboBoxFinEstrato);
        inicializarOperador(comboBoxOperador);
        inicializarMeses(comboBoxMeses);
        inicializarAnios(comboBoxInicioAnios);
        inicializarAnios(comboBoxFinAnios);
    }

    protected void inicializarJLabel(JLabel label, String texto) {

        label.setText(texto);
        label.setSize(170, 30);
        label.setMinimumSize(new Dimension(170, 30));
        label.setMaximumSize(new Dimension(170, 30));
        label.setFont(new java.awt.Font("DejaVu Sans", 1, 13));
    }

    protected void inicializarCheckBox(JCheckBox checkBox) {

        checkBox.setVisible(true);
        checkBox.setSize(170, 40);
        checkBox.setMinimumSize(new Dimension(170, 40));
        checkBox.setMaximumSize(new Dimension(170, 40));
        checkBox.setMargin(new java.awt.Insets(0, 0, 5, 0));
    }

    protected void inicializarEstadoCivil(JComboBox estadoCivil) {

        estadoCivil.setVisible(true);
        estadoCivil.setMaximumSize(new Dimension(170, 30));

        String estados[][] = new String[6][1];
        estados[0][0] = "Escoger una Opción";
        estados[1][0] = "Casado";
        estados[2][0] = "Divorciado";
        estados[3][0] = "Soltero";
        estados[4][0] = "Unión Libre";
        estados[5][0] = "Viudo";

        for (int i = 0; i < estados.length; i++) {
            estadoCivil.addItem(estados[i][0]);
        }
    }

    protected void inicializarEstrato(JComboBox estrato) {

        estrato.setVisible(true);
        estrato.setMaximumSize(new Dimension(170, 30));

        String estratos[][] = new String[7][1];
        estratos[0][0] = "Escoger una Opción";
        estratos[1][0] = "1";
        estratos[2][0] = "2";
        estratos[3][0] = "3";
        estratos[4][0] = "4";
        estratos[5][0] = "5";
        estratos[6][0] = "6";

        for (int i = 0; i < estratos.length; i++) {
            estrato.addItem(estratos[i][0]);
        }
    }

    protected void inicializarOperador(JComboBox operador) {

        operador.setVisible(true);
        operador.setMaximumSize(new Dimension(200, 30));

        String estratos[][] = new String[18][1];
        estratos[0][0] = "Escoger una Opción";
        estratos[1][0] = "AT&T";
        estratos[2][0] = "Avantel";
        estratos[3][0] = "Colmovil";
        estratos[4][0] = "Comcel";
        estratos[5][0] = "EMCALI";
        estratos[6][0] = "EPM";
        estratos[7][0] = "ETB";
        estratos[8][0] = "ETP";
        estratos[9][0] = "Metrotel";
        estratos[10][0] = "Movistar";
        estratos[11][0] = "Orange";
        estratos[12][0] = "Telebucaramanga";
        estratos[13][0] = "telefonica";
        estratos[14][0] = "telmex";
        estratos[15][0] = "Tigo";
        estratos[16][0] = "UNE";
        estratos[17][0] = "Vodafone";

        for (int i = 0; i < estratos.length; i++) {
            operador.addItem(estratos[i][0]);
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

    
    protected void inicializarAnios(JComboBox anios) {

        anios.setVisible(true);
        anios.setMaximumSize(new Dimension(170, 30));

        String anos[][] = new String[16][1];
        anos[0][0] = "Escoger una Opción";
        anos[1][0] = "2000";
        anos[2][0] = "2001";
        anos[3][0] = "2002";
        anos[4][0] = "2003";
        anos[5][0] = "2004";
        anos[6][0] = "2005";
        anos[7][0] = "2006";
        anos[8][0] = "2007";
        anos[9][0] = "2008";
        anos[10][0] = "2009";
        anos[11][0] = "2010";
        anos[12][0] = "2011";
        anos[13][0] = "2012";
        anos[14][0] = "2013";
        anos[15][0] = "2014";

        for (int i = 0; i < anos.length; i++) {
            anios.addItem(anos[i][0]);
        }
    }

    abstract void hacerConsulta(java.awt.event.ActionEvent evt);

    protected boolean evaluarRangoEstrato() {

        if (!comboBoxInicioEstrato.getSelectedItem().equals("Escoger una Opción")) {

            if (comboBoxFinEstrato.getSelectedItem().equals("Escoger una Opción")) {

                JOptionPane.showMessageDialog(null, "Debe escoger un valor para el rango de Estrato");
                return false;

            } else {

                int valorInicioEstrato = Integer.parseInt("" + comboBoxInicioEstrato.getSelectedItem());
                int valorFinEstrato = Integer.parseInt("" + comboBoxFinEstrato.getSelectedItem());

                if (valorFinEstrato < valorInicioEstrato) {
                    JOptionPane.showMessageDialog(null, "Debe escoger un valor MAYOR o IGUAL para el rango del Estrato");
                    return false;
                } else {
                    return true;
                }
            }//fin else
        }

        return true;
    }

    protected boolean evaluarRangoAnios() {

        if (!comboBoxInicioAnios.getSelectedItem().equals("Escoger una Opción")) {

            if (comboBoxFinAnios.getSelectedItem().equals("Escoger una Opción")) {

                JOptionPane.showMessageDialog(null, "Debe escoger un valor para el rango el Año");
                return false;

            } else {

                int valorInicioAnio = Integer.parseInt("" + comboBoxInicioAnios.getSelectedItem());
                int valorFinAnio = Integer.parseInt("" + comboBoxFinAnios.getSelectedItem());

                if (valorFinAnio < valorInicioAnio) {
                    JOptionPane.showMessageDialog(null, "Debe escoger un valor MAYOR o IGUAL para el rango del Año");
                    return false;
                } else {
                    return true;
                }
            }//fin else
        }

        return true;
    }

    protected boolean evaluarPlanesEscogidos() {

        if (checkBoxDatos.isSelected() && checkBoxCorporativo.isSelected()
                && checkBoxPrepagoVoz.isSelected() && checkBoxPostpagoVoz.isSelected()) {

            return true;

        } else if (checkBoxDatos.isSelected() && (checkBoxCorporativo.isSelected()
                || checkBoxPrepagoVoz.isSelected() || checkBoxPostpagoVoz.isSelected())) {

            JOptionPane.showMessageDialog(null, "Sólo puede elegir un plan de datos solo,\n o con todas las demás opciones.");
            return false;

        } else if (!checkBoxDatos.isSelected() && checkBoxCorporativo.isSelected()
                && checkBoxPrepagoVoz.isSelected() && !checkBoxPostpagoVoz.isSelected()) {

            JOptionPane.showMessageDialog(null, "No existen planes prepago que sean corporativos.");
            return false;

        } else if (!checkBoxDatos.isSelected() && !checkBoxCorporativo.isSelected()
                && checkBoxPrepagoVoz.isSelected() && !checkBoxPostpagoVoz.isSelected()) {

            JOptionPane.showMessageDialog(null, "No es posible realizar esta consulta. "
                    + "Para ver el número de planer prepago vendidos, "
                    + "porfavor marque la opcion prepago y postpago..");
            return false;

        }

        return true;

    }

    public JLabel getLabelSexo() {
        return labelSexo;
    }

    public JCheckBox getCheckBoxSexoFemenino() {
        return checkBoxSexoFemenino;
    }

    public JCheckBox getCheckBoxSexoMasculino() {
        return checkBoxSexoMasculino;
    }

    public JComboBox getEstadoCivil() {
        return comboBoxEstadoCivil;
    }

    public void setEstadoCivil(JComboBox estadoCivil) {
        this.comboBoxEstadoCivil = estadoCivil;
    }

    public JComboBox getInicioEstrato() {
        return comboBoxInicioEstrato;
    }

    public void setInicioEstrato(JComboBox inicioEstrato) {
        this.comboBoxInicioEstrato = inicioEstrato;
    }

    public JComboBox getFinEstrato() {
        return comboBoxFinEstrato;
    }

    public void setFinEstrato(JComboBox finEstrato) {
        this.comboBoxFinEstrato = finEstrato;
    }

    public JLabel getLabelEstadoCivil() {
        return labelEstadoCivil;
    }

    public JLabel getLabelInicioEstrato() {
        return labelInicioEstrato;
    }

    public JLabel getLabelFinEstrato() {
        return labelFinEstrato;
    }

    public JLabel getLabelVacia() {
        return labelVacia;
    }

    public JComboBox getComboBoxOperador() {
        return comboBoxOperador;
    }

    public void setComboBoxOperador(JComboBox comboBoxOperador) {
        this.comboBoxOperador = comboBoxOperador;
    }

    public JComboBox getComboBoxInicioAnios() {
        return comboBoxInicioAnios;
    }

    public void setComboBoxInicioAnios(JComboBox comboBoxInicioAnios) {
        this.comboBoxInicioAnios = comboBoxInicioAnios;
    }

    public JComboBox getComboBoxFinAnios() {
        return comboBoxFinAnios;
    }

    public void setComboBoxFinAnios(JComboBox comboBoxFinAnios) {
        this.comboBoxFinAnios = comboBoxFinAnios;
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

    public JLabel getLabelOperador() {
        return labelOperador;
    }

    public JCheckBox getCheckBoxDatos() {
        return checkBoxDatos;
    }

    public JCheckBox getCheckBoxPrepagoVoz() {
        return checkBoxPrepagoVoz;
    }

    public JCheckBox getCheckBoxPostpagoVoz() {
        return checkBoxPostpagoVoz;
    }

    public JCheckBox getCheckBoxCorporativo() {
        return checkBoxCorporativo;
    }

    public JLabel getLabelPlanDeDatos() {
        return labelPlanDeDatos;
    }

    public JLabel getLabelPlanDeVoz() {
        return labelPlanDeVoz;
    }

    public JButton getConsultar() {
        return botonConsultar;
    }

    public JLabel getLabelMeses() {
        return labelMeses;
    }

    public JComboBox getComboBoxMeses() {
        return comboBoxMeses;
    }

    public void setComboBoxMeses(JComboBox comboBoxMeses) {
        this.comboBoxMeses = comboBoxMeses;
    }
}
