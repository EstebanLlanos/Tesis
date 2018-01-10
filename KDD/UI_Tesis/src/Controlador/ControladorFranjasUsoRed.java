package Controlador;

import Dao.DaoFranjasUsoRed;
import java.util.ArrayList;

public class ControladorFranjasUsoRed {

    DaoFranjasUsoRed daoFranjasUsoRed;

    public ControladorFranjasUsoRed() {
        daoFranjasUsoRed = new DaoFranjasUsoRed();
    }

    public ArrayList <Integer> obtenerFranjas(String operador) {
        ArrayList <Integer> conteoLlamadas = new ArrayList<>();
        
        String where = "";
        
        where = daoFranjasUsoRed.prepararRestriccionesClausulaWhereFranjas(operador);
        System.out.println("where: " + where);

        conteoLlamadas = daoFranjasUsoRed.listaFranjas(where);

        return conteoLlamadas;

    }

    public void desconectar() {
        daoFranjasUsoRed.desconectar();
    }
}
