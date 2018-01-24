/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Afiliaciones;

import Controlador.Afiliaciones.ControladorVentasCiudades;
import Gráficos.FXBarChart;
import Gráficos.FXLineChart;
import Gráficos.FXPieChart;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban - Casa
 */
public class UiVentasCiudades {
    ControladorVentasCiudades controladorVentasCiudades;
    ArrayList<String[]> dataLlamadas;

    public UiVentasCiudades() {

        controladorVentasCiudades = new ControladorVentasCiudades();
    }

    void hacerConsulta(ActionEvent evt) {

        //verificamos que el rango de estrato sea correcto
        String sede = "" + comboBoxSede.getSelectedItem(); 
        String departamento = "" + comboBoxDepartamento.getSelectedItem();
        
        if (evaluarRangoAnios()) {

            String inicioAnio = "" + comboBoxInicioAnios.getSelectedItem();
            String finAnio = "" + comboBoxFinAnios.getSelectedItem();
            int inicio = Integer.parseInt(inicioAnio);
            int fin = Integer.parseInt(finAnio);

            dataLlamadas = controladorTendenciaPLanVoz.getPerfiles(inicioAnio, finAnio);

            ArrayList<String> anios = new ArrayList();
            for (int i = inicio; i <= fin; i++) {
                anios.add(i + "");
            }

            if (!dataLlamadas.isEmpty()) {
                FXPieChart PieChart = new FXPieChart("Tendencia Llamadas Mes-a-Mes", anios, dataLlamadas);
                FXBarChart BarChart = new FXBarChart("Tendencia Llamadas Mes-a-Mes", "Años", anios, "Llamadas", dataLlamadas, "Llamadas");
                FXLineChart LineChart = new FXLineChart("Uso Roamming Mes-a-Mes", "Años", anios, "Llamadas", dataLlamadas, "Llamadas");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha extraido la información");
            }
        }

    }
}
