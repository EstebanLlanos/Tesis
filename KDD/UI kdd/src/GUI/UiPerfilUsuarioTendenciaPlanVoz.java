/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controlador.ControladorPerfilUsuarioServicioRoamming;
import Controlador.ControladorTendenciaPlanVoz;
import Gráficos.FXBarChart;
import Gráficos.FXLineChart;
import Gráficos.FXPieChart;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Olaya O
 */
public class UiPerfilUsuarioTendenciaPlanVoz extends UiPerfil {

    ControladorTendenciaPlanVoz controladorTendenciaPLanVoz;
    ArrayList<Integer> dataLlamadas;

    public UiPerfilUsuarioTendenciaPlanVoz() {

        controladorTendenciaPLanVoz = new ControladorTendenciaPlanVoz();
    }

    @Override
    void hacerConsulta(ActionEvent evt) {

        //verificamos que el rango de estrato sea correcto
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
