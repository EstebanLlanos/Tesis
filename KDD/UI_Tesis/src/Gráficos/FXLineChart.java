/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gráficos;

import GUI.Visualizador;
import java.util.ArrayList;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/*
 * @author Esteban
 */
public class FXLineChart {
    
    String chartName, valuesName, tagName, legend;
    ArrayList<String> tags;
    ArrayList<Integer> values;

    public FXLineChart(String chartName, String tagName, ArrayList<String> tags, String valuesName, ArrayList<Integer> values, String legend) {

        this.tags = tags;
        this.values = values;
        this.chartName = chartName;
        this.valuesName = valuesName;
        this.tagName = tagName;
        this.legend = legend;
        JFXPanel panelVisualizador = new JFXPanel();
                
        Task task = new Task<Void>() { 
            
            @Override
            public Void call() {
                initFX(panelVisualizador, chartName, tags, tagName, values, valuesName, legend);
                return null;
            }
        };
        
        new Thread(task).start();
        
        if (Visualizador.panelPestanas.getTabCount() == 3) {
            if (Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico Lineal - Top Vendedores") || 
                    Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico Lineal - Top Ciudades") ||
                    Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico Lineal - Ventas por Sedes") ||
                    Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico Lineal - Top Especialidades") ||
                    Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico Lineal - Top Especialistas") ||
                    Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico Lineal - Top Examenes") ||
                    Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico Lineal - Top Instituciones") ||
                    Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico Lineal - Top Servicios Solicitados")) {
                Visualizador.panelPestanas.removeTabAt(0);
            } else if(Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico Lineal - Top Vendedores") || 
                    Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico Lineal - Top Ciudades") ||
                    Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico Lineal - Ventas por Sedes") ||
                    Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico Lineal - Top Especialidades") ||
                    Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico Lineal - Top Especialistas") ||
                    Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico Lineal - Top Examenes") ||
                    Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico Lineal - Top Instituciones") ||
                    Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico Lineal - Top Servicios Solicitados")){
                Visualizador.panelPestanas.removeTabAt(1);
            } else if(Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico Lineal - Top Vendedores") || 
                    Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico Lineal - Top Ciudades") ||
                    Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico Lineal - Ventas por Sedes") ||
                    Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico Lineal - Top Especialidades") ||
                    Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico Lineal - Top Especialistas") ||
                    Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico Lineal - Top Examenes") ||
                    Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico Lineal - Top Instituciones") ||
                    Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico Lineal - Top Servicios Solicitados")){
                Visualizador.panelPestanas.removeTabAt(2);
            }
        }
        
        Visualizador.panelPestanas.addTab("Gráfico Lineal - " + chartName, panelVisualizador);
        panelVisualizador.setVisible(true);
        
    }

    private void initFX(JFXPanel fxPanel, String chartName, ArrayList<String> tags, String tagName, ArrayList<Integer> values, String valuesName, String legend) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene(chartName, tags, tagName, values, valuesName, legend);
        fxPanel.setScene(scene);
    }

    private Scene createScene(String chartName, ArrayList<String> tags, String tagName, ArrayList<Integer> values, String valuesName, String legend) {
        Group root = new Group();
                
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> bc
                = new LineChart(xAxis, yAxis);
        bc.setTitle(chartName);
        xAxis.setLabel(tagName);
        yAxis.setLabel(valuesName);

        XYChart.Series series1 = new XYChart.Series();
        series1.setName(legend);
        for (int i = 0; i < tags.size(); i++) {
            series1.getData().add(new XYChart.Data(tags.get(i), values.get(i)));
        }
        Scene scene = new Scene(bc);
        bc.getData().addAll(series1);

        return (scene);
    }
    
}
