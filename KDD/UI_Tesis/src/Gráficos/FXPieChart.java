/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gráficos;

import GUI.Visualizador;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Esteban
 */
public class FXPieChart {

    final String chartName;
    final ArrayList<String> tags;
    final ArrayList<Integer> values;

    public FXPieChart(final String chartName, final ArrayList<String> tags, final ArrayList<Integer> values) {

        this.tags = tags;
        this.values = values;
        this.chartName = chartName;
        final JFXPanel panelVisualizador = new JFXPanel();
        final ScrollBar barra = new ScrollBar();
        
        //Visualizador.panelPestanas.removeAll();
        panelVisualizador.removeAll();
        panelVisualizador.setAutoscrolls(true);
                
        Task task = new Task<Void>() { 
            
            @Override
            public Void call() {
                initFX(panelVisualizador, chartName, tags, values);
                return null;
            }
        };
        
        new Thread(task).start();
        
        if (Visualizador.panelPestanas.getTabCount() == 3) {
            if (Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico De Torta - Top 10 Vendedores") || 
                    Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico De Torta - Top 5 Ciudades") ||
                    Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico De Torta - Ventas por Sedes") ||
                    Visualizador.panelPestanas.getTitleAt(0).equals("Gráfico De Torta - Top Especialidades")) {
                Visualizador.panelPestanas.removeTabAt(0);
            } else if(Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico De Torta - Top 10 Vendedores") || 
                    Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico De Torta - Top 5 Ciudades") ||
                    Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico De Torta - Ventas por Sedes") ||
                    Visualizador.panelPestanas.getTitleAt(1).equals("Gráfico De Torta - Top Especialidades")){
                Visualizador.panelPestanas.removeTabAt(1);
            } else if(Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico De Torta - Top 10 Vendedores") || 
                    Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico De Torta - Top 5 Ciudades") ||
                    Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico De Torta - Ventas por Sedes") ||
                    Visualizador.panelPestanas.getTitleAt(2).equals("Gráfico De Torta - Top Especialidades")){
                Visualizador.panelPestanas.removeTabAt(2);
            }
        }
        
        Visualizador.panelPestanas.addTab("Gráfico De Torta - " + chartName, panelVisualizador);
        panelVisualizador.setVisible(true);
        
    }

    private static void initFX(JFXPanel fxPanel, String chartName, ArrayList<String> tags, ArrayList<Integer> values) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene(chartName, tags, values);
        fxPanel.setScene(scene);
    }

    private static Scene createScene(String chartName, ArrayList<String> tags, ArrayList<Integer> values) {
        Group root = new Group();
        Scene scene = new Scene(root);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < tags.size(); i++) {
            pieChartData.add(new PieChart.Data(tags.get(i), values.get(i)));
        }

        final PieChart chart = new PieChart(pieChartData);
        //chart.setTitle(chartName);
        //chart.setTitleSide(Side.BOTTOM);
        
        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setOpacity(0.9);
        caption.setStyle("-fx-font: 20 arial;-fx-text-fill: midnightblue;");

        chart.getData().forEach((data) -> {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf((int)data.getPieValue()));
                        }
                    });
        });
        
        chart.setLegendSide(Side.LEFT);
        
        chart.setPrefSize(Visualizador.panelPestanas.getWidth() - 50, Visualizador.panelPestanas.getHeight() - 50);
        /*chart.setMinSize(400, 400);
        chart.setStartAngle(250);
        chart.setTranslateY(-200);*/
        
        ((Group) scene.getRoot()).getChildren().add(chart);
        ((Group) scene.getRoot()).getChildren().add(caption);

        return (scene);
    }

}
