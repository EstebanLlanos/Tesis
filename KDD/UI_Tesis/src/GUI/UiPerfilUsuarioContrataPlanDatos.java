package GUI;

import Controlador.ControladorPerfilUsuarioContrataPlanDatos;
import Gráficos.FXBarChart;
import Gráficos.FXLineChart;
import Gráficos.FXPieChart;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban
 */
public class UiPerfilUsuarioContrataPlanDatos extends UiPerfil {

    ControladorPerfilUsuarioContrataPlanDatos controladorUsuarioContrataPlanDatos;
    ArrayList dataContratos;

    public UiPerfilUsuarioContrataPlanDatos() {

        controladorUsuarioContrataPlanDatos = new ControladorPerfilUsuarioContrataPlanDatos();
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

            dataContratos = controladorUsuarioContrataPlanDatos.getPerfiles(sexoFemenino, sexoMasculino, estadoCivil, inicioEstrato, finEstrato);

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
                FXPieChart PieChart = new FXPieChart("Contratos Mes-a-Mes", meses, dataContratos);
                FXBarChart BarChart = new FXBarChart("Contratos Mes-a-Mes", "meses", meses, "Contratos", dataContratos, "Contratos");
                FXLineChart LineChart = new FXLineChart("Contratos Mes-a-Mes", "meses", meses, "Contratos", dataContratos, "Contratos");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha extraido la información");
            }
        }

    }
}
