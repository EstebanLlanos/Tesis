package Gui;

import Controlador.ControladorParticipante;
import javax.swing.JOptionPane;
import Logico.Participante;

public class InterfazGrafica {

    ControladorParticipante controlador;

    public InterfazGrafica() {

        controlador = new ControladorParticipante();
    }

    public void listarParticipantes() {

        //Consultar todos los participantes 
        String participante[][] = controlador.obtenerParticipantes();

        for (int fila = 0; fila < participante.length; fila++) {

            System.out.println("Id: " + participante[fila][0] + " Nombre: " + participante[fila][1] + " Edad: " + participante[fila][2]
                    + " Nro Cel: " + participante[fila][3] + " Correo: " + participante[fila][4] + " Nivel Formacion: " + participante[fila][5]
                    + " Genero: " + participante[fila][6]);
        }
    }

    public void consultarParticipantePorId() {

        Participante participante = controlador.consultarParticipante("0001");

        System.out.println("Id: " + participante.getIDPart() + " Nombre: " + participante.getNombre() + " Edad: " + participante.getEdad()
                + " Nro Cel: " + participante.getNroCel() + " Correo: " + participante.getCorreo() + " Nivel Formacion: " + participante.getNivelFormacion()
                + " Genero: " + participante.getGenero());
    }

    public void insertarParticipante() {

        Participante participante = new Participante();
        participante.setIDPart("1000");
        participante.setNombre("pablo perez");
        participante.setGenero("M");
        participante.setCorreo("correo@correounivalle.edu.co");
        participante.setEdad(20);
        participante.setNivelFormacion("Pregrado");
        participante.setNroCel("324 556 7778");

        boolean registro = controlador.registrarParticipante(participante);

        if (registro) {
            JOptionPane.showMessageDialog(null, "Participante registrado con éxito");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo registrar el partipante");
        }

    }

    public void actualizarParticipante() {

        //se actualizarán algunos datos del participante, id participante no puede ser actualizado porque es la llave primaria
        Participante participante = new Participante();
        participante.setIDPart("1000");
        participante.setNombre("PABLITO PEREZ");
        participante.setGenero("M");
        participante.setCorreo("correo@correounivalle.edu.co");
        participante.setEdad(21);
        participante.setNivelFormacion("Pregrado");
        participante.setNroCel("312 555 8676");

        boolean actualizacion = controlador.actualizarParticipante(participante);

        if (actualizacion) {
            JOptionPane.showMessageDialog(null, "Participante actualizado con éxito");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar el partipante");
        }
    }

    public void eliminarParticipante() {

        boolean eliminado = controlador.eliminarParticipante("1000");

        if (eliminado) {
            JOptionPane.showMessageDialog(null, "Participante eliminado con éxito");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el partipante");
        }

    }
}
