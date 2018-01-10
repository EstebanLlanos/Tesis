package Controlador;

import Dao.DAOParticipante;
import Logico.Participante;

public class ControladorParticipante {

    DAOParticipante daoParticipante;

    public ControladorParticipante() {
        daoParticipante = new DAOParticipante();
    }

    public boolean registrarParticipante(Participante participante) {

        if (daoParticipante.AgregarParticipante(participante) == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String[][] obtenerParticipantes() {

        String datos[][] = daoParticipante.obtenerParticipantes();
        return datos;
    }

    public Participante consultarParticipante(String idParticipante) {
        
        Participante participante = daoParticipante.consultarParticipante(idParticipante);
        return participante;
    }

    public boolean actualizarParticipante(Participante participante) {
        
        boolean actualizado = daoParticipante.actualizarParticipante(participante);
        return actualizado;
    }

    public boolean eliminarParticipante(String idParticipante) {
        
        boolean eliminado = daoParticipante.eliminarParticipante(idParticipante);
        return eliminado;
    }
}
