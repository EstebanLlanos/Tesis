/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Dao.DaoTendenciaPlanVoz;
import Logico.TendenciaPlanVoz;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Olaya O
 */
public class ControladorTendenciaPlanVoz {

    DaoTendenciaPlanVoz daoTendenciaPlanVoz;
    ArrayList<Integer> contratosAnios = new ArrayList();

    public ControladorTendenciaPlanVoz() {

        daoTendenciaPlanVoz = new DaoTendenciaPlanVoz();
    }

    public ArrayList<Integer> getPerfiles(String anioInicio, String anioFin) {

        int inicio = Integer.parseInt(anioInicio);
        int fin = Integer.parseInt(anioFin);

        int[] valores = new int[(fin - inicio) + 1];

        TendenciaPlanVoz tendenciaPlanVoz = new TendenciaPlanVoz();
        tendenciaPlanVoz.setAnioInicio(inicio);
        tendenciaPlanVoz.setAnioFin(fin);

        String restriccionesClausulaWhere = daoTendenciaPlanVoz.prepararRestriccionesClausulaWherePerfiles(tendenciaPlanVoz);
        ArrayList<String[]> contratos = daoTendenciaPlanVoz.listaPerfiles(restriccionesClausulaWhere);

        if (!contratos.isEmpty()) {
            for (int i = 0; i < contratos.size(); i++) {
                int anio = (int) Double.parseDouble(contratos.get(i)[1]) - inicio;
                valores[anio] += 1;
            }
            for (int j = 0; j < valores.length; j++) {
                contratosAnios.add(valores[j]);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró tendencia en los años seleccionados");
        }
        for (int j = 0; j < contratosAnios.size(); j++) {
        }
        return contratosAnios;
    }

}
