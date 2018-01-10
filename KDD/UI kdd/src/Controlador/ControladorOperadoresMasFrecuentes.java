package Controlador;

import Dao.DaoFranjasUsoRed;
import Dao.DaoOperadoresMasFrecuentes;
import java.util.ArrayList;

public class ControladorOperadoresMasFrecuentes {

    DaoOperadoresMasFrecuentes daoOperadores;

    public ControladorOperadoresMasFrecuentes() {
        daoOperadores = new DaoOperadoresMasFrecuentes();
    }

    public ArrayList <Integer> obtenerOperadores(String mes) {
        ArrayList <Integer> conteoOperadores = new ArrayList<>();
        
        String where = "";
        
        where = daoOperadores.prepararRestriccionesClausulaWhereOperadores(mes);
        System.out.println("where: " + where);

        conteoOperadores = daoOperadores.listaOperadores(where);

        return conteoOperadores;

    }

    public void desconectar() {
        daoOperadores.desconectar();
    }
}
