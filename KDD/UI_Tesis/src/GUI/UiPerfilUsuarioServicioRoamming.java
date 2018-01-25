package GUI;

import Controlador.ControladorPerfilUsuarioServicioRoamming;
import Gráficos.FXBarChart;
import Gráficos.FXLineChart;
import Gráficos.FXPieChart;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/*
 * @author Esteban
 */
public class UiPerfilUsuarioServicioRoamming extends UiPerfil {

    ControladorPerfilUsuarioServicioRoamming controladorUsuarioServicioRoamming;
    ArrayList dataContratos;

    public UiPerfilUsuarioServicioRoamming() {

        controladorUsuarioServicioRoamming = new ControladorPerfilUsuarioServicioRoamming();
    }

    @Override
    void hacerConsulta(ActionEvent evt) {

        //verificamos que el rango de estrato sea correcto
        if (evaluarRangoEstrato()) {

            String sexoFemenino = "" + checkBoxSexoFemenino.isSelected();
            String sexoMasculino = "" + checkBoxSexoMasculino.isSelected();
            String estadoCivil = "" + comboBoxEstadoCivil.getSelectedItem();
            String inicioEstrato = "" + comboBoxInicioEstrato.getSelectedItem();
            String finEstrato = "" + comboBoxFinEstrato.getSelectedItem();

            dataContratos = controladorUsuarioServicioRoamming.getPerfiles(sexoFemenino, sexoMasculino, estadoCivil, inicioEstrato, finEstrato);

            ArrayList<String> meses = new ArrayList();
            meses.add("Enero");
            meses.add("Febrero");
            meses.add("Marzo");
            meses.add("Abril");
            meses.add("Mayo");
            meses.add("Junio");
            meses.add("Julio");
            meses.add("Agosto");
            meses.add("Septiembre");
            meses.add("Octubre");
            meses.add("Noviembre");
            meses.add("Diciembre");

            if (!dataContratos.isEmpty()) {
                FXPieChart PieChart = new FXPieChart("Uso Roamming Prepago Mes-a-Mes", meses, dataContratos);
                FXBarChart BarChart = new FXBarChart("Uso Roamming Mes-a-Mes", "meses", meses, "Contratos", dataContratos, "Roamming");
                FXLineChart LineChart = new FXLineChart("Uso Roamming Mes-a-Mes", "meses", meses, "Contratos", dataContratos, "Roamming");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha extraido la información");
            }
        }

    }
}
