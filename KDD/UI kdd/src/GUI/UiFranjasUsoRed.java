package GUI;

import Controlador.ControladorFranjasUsoRed;
import Gráficos.FXBarChart;
import Gráficos.FXLineChart;
import Gráficos.FXPieChart;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author luisfemm
 */
public class UiFranjasUsoRed extends UiPerfil {

    ControladorFranjasUsoRed controladorFranjasUsoRed;
    ArrayList<Integer> datosFranjas;

    public UiFranjasUsoRed() {

        controladorFranjasUsoRed = new ControladorFranjasUsoRed();
    }

    @Override
    void hacerConsulta(ActionEvent evt) {

        String operador = "" + comboBoxOperador.getSelectedItem();

        datosFranjas = controladorFranjasUsoRed.obtenerFranjas(operador);

        ArrayList<String> franjas = new ArrayList();
        franjas.add("AM");
        franjas.add("Evening");
        franjas.add("Morning");
        franjas.add("Night");
        franjas.add("PM");

        FXPieChart PieChart;
        FXBarChart BarChart;

        if (!datosFranjas.isEmpty()) {
            PieChart = new FXPieChart("Franjas Más Usadas Hasta la Fecha", franjas, datosFranjas);
            BarChart = new FXBarChart("Franjas Más Usadas Hasta la Fecha", "Franjas", franjas, "Uso", datosFranjas, "Franjas");
            FXLineChart LineChart = new FXLineChart("Franjas Más Usadas Hasta la Fecha", "Franjas", franjas, "Uso", datosFranjas, "Franjas");

        } else {
            JOptionPane.showMessageDialog(null, "No se ha extraido la información");
        }

    }
}
