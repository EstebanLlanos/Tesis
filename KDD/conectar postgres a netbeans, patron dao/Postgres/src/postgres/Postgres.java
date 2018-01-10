package postgres;

import Gui.InterfazGrafica;

public class Postgres {
    
    public static void main(String[] args) {        
        
        InterfazGrafica interfaz = new InterfazGrafica();
        interfaz.listarParticipantes();
        //interfaz.consultarParticipantePorId();
        //interfaz.eliminarParticipante();
        //interfaz.insertarParticipante();
        //interfaz.actualizarParticipante();
    }
}
