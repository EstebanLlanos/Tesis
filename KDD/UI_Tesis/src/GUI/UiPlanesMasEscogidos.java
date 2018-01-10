package GUI;

import Controlador.ControladorPlanesMasEscogidos;
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
public class UiPlanesMasEscogidos extends UiPerfil {

    ControladorPlanesMasEscogidos controladorPlanesMasEscogidos;
    ArrayList<Integer> datosPlanes;

    public UiPlanesMasEscogidos() {

        controladorPlanesMasEscogidos = new ControladorPlanesMasEscogidos();
    }

    @Override
    void hacerConsulta(ActionEvent evt) {

        if (evaluarPlanesEscogidos()) {

            String datos = "" + checkBoxDatos.isSelected();
            String prepago = "" + checkBoxPrepagoVoz.isSelected();
            String postpago = "" + checkBoxPostpagoVoz.isSelected();
            String corporativo = "" + checkBoxCorporativo.isSelected();

            datosPlanes = controladorPlanesMasEscogidos.obtenerPlanes(datos, prepago, postpago, corporativo);

            ArrayList<String> parametros = new ArrayList();
            
            if(datosPlanes.get(datosPlanes.size()-1) == 1){
                parametros.add("Planes Datos");
                parametros.add("Planes Voz");
            }else if(datosPlanes.get(datosPlanes.size()-1) == 2){
                parametros.add("Datos 5Megas");
                parametros.add("Datos 50Megas");
                parametros.add("Datos 100Megas");
                parametros.add("Datos 1Giga");
                parametros.add("Datos Ilimitados");
                parametros.add("Datos Estandar X Kb");
                parametros.add("Datos Premium X Kb");
                parametros.add("Datos Gold X Kb");
            }else if(datosPlanes.get(datosPlanes.size()-1) == 3){
                parametros.add("Plan Casual");
                parametros.add("Plan Estandar");
                parametros.add("Plan Premium");
                parametros.add("Plan Familia");
                parametros.add("Plan Gold");
                parametros.add("Plan Total_1600");               
                parametros.add("Plan Total_600");
                parametros.add("Plan Casual Abierto");
                parametros.add("Plan Estandar Abierto");
                parametros.add("Plan Premium Abierto");       
                parametros.add("Plan Familia Abierto");
                parametros.add("Plan Gold Abierto");
                parametros.add("Plan Total_1600 Abierto");
                parametros.add("Plan Total_600 Abierto");                
            }else if(datosPlanes.get(datosPlanes.size()-1) == 4){
                parametros.add("Plan Estandar Corporativo");
                parametros.add("Plan Premium Corporativo");
                parametros.add("Plan Gold Corporativo");
            }else if(datosPlanes.get(datosPlanes.size()-1) == 5){
                parametros.add("Prepago");
                parametros.add("Postpago");
            }else if(datosPlanes.get(datosPlanes.size()-1) == 6){
                parametros.add("Prepago");
                parametros.add("Plan Casual");
                parametros.add("Plan Estandar");
                parametros.add("Plan Premium");
                parametros.add("Plan Familia");
                parametros.add("Plan Gold");
                parametros.add("Plan Total_1600");               
                parametros.add("Plan Total_600");
                parametros.add("Plan Casual Abierto");
                parametros.add("Plan Estandar Abierto");
                parametros.add("Plan Premium Abierto");       
                parametros.add("Plan Familia Abierto");
                parametros.add("Plan Gold Abierto");
                parametros.add("Plan Total_1600 Abierto");
                parametros.add("Plan Total_600 Abierto");
                parametros.add("Plan Estandar Corporativo");
                parametros.add("Plan Premium Corporativo");
                parametros.add("Plan Gold Corporativo");
            }
            
            FXPieChart PieChart;
            FXBarChart BarChart;

            if (!datosPlanes.isEmpty()) {
                PieChart = new FXPieChart("Planes Más Escogidos Hasta la Fecha", parametros, datosPlanes);
                BarChart = new FXBarChart("Planes Más Escogidos Hasta la Fecha", "", parametros, "Cantidad de Ventas", datosPlanes, "Planes más Escogidos");
                FXLineChart LineChart = new FXLineChart("Planes Más Escogidos Hasta la Fecha", "", parametros, "Cantidad de Ventas", datosPlanes, "Planes más Escogidos");
            } else {
                JOptionPane.showMessageDialog(null, "No se ha extraido la información");
            }
        }

    }
}
