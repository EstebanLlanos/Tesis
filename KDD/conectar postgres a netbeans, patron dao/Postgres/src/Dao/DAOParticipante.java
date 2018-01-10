package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import ConexionBD.ConexionBD;
import Logico.Participante;

public class DAOParticipante {

    ConexionBD conexion;
    Connection con;

    public DAOParticipante() {
        conexion = new ConexionBD();
        //en cada DAO se crea un objeto de tipo ConexionBD
    }

    public int AgregarParticipante(Participante participante) {

        String sql;
        int numFilas = 0;

        sql = "INSERT INTO participante VALUES ('" + participante.getIDPart() + "', '" + participante.getNombre() + "', '"
                + participante.getEdad() + "', '" + participante.getNroCel() + "', '" + participante.getCorreo() + "', '"
                + participante.getNivelFormacion() + "', '" + participante.getGenero() + "');";

        try {
            con = conexion.conectar();
            Statement sentencia = con.createStatement();
            numFilas = sentencia.executeUpdate(sql);
            conexion.desconectarBaseDeDatos(con);
            return numFilas;

        } catch (Exception e) {
            return -1;
        }

    }//fin metodo agregarParticipante

    public String[][] obtenerParticipantes() {

        String[][] resultado = null;
        final int columnas = 7;
        int filas = 0;

        try {

            con = conexion.conectar();
            Statement stmt = con.createStatement();
            String consulta = "SELECT * FROM participante;";
            ResultSet rs = stmt.executeQuery(consulta);

            while (rs.next()) {
                filas++;
            }

            int f = filas;

            resultado = new String[f][columnas];
            filas = 0;
            rs = stmt.executeQuery(consulta);

            while (rs.next()) {
                resultado[filas][0] = rs.getObject(1) + "";
                resultado[filas][1] = rs.getObject(2) + "";
                resultado[filas][2] = rs.getObject(3) + "";
                resultado[filas][3] = rs.getObject(4) + "";
                resultado[filas][4] = rs.getObject(5) + "";
                resultado[filas][5] = rs.getObject(6) + "";
                resultado[filas][6] = rs.getObject(7) + "";
                filas++;
            }

            rs.close();
            conexion.desconectarBaseDeDatos(con);

        } catch (SQLException e) {
        }

        return resultado;

    }//fin metodo obtenerParticipantes

    public Participante consultarParticipante(String idParticipante) {

        Participante participante = new Participante();
        String sql;
        sql = "SELECT* FROM Participante WHERE idPart='" + idParticipante + "';";

        try {

            con = conexion.conectar();
            Statement sentencia = con.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);

            while (rs.next()) {
                participante.setIDPart(rs.getString(1));
                participante.setNombre(rs.getString(2));
                participante.setEdad(Integer.parseInt(rs.getString(3)));
                participante.setNroCel(rs.getString(4));
                participante.setCorreo(rs.getString(5));
                participante.setNivelFormacion(rs.getString(6));
                participante.setGenero(rs.getString(7));
            }

            return participante;
        } catch (SQLException exp) {
        }
        return null;
    }

    public boolean actualizarParticipante(Participante participante) {

        boolean flag = false;
        String sql;
        sql = "UPDATE Participante SET nompart ='"
                + participante.getNombre() + "', edad ="
                + participante.getEdad() + ", nrocel ='"
                + participante.getNroCel() + "', correo ='"
                + participante.getCorreo() + "', nivelformacion ='"
                + participante.getNivelFormacion() + "', genero ='"
                + participante.getGenero() + "' "
                + "where idpart ='" + participante.getIDPart() + "';";

        try {

            con = conexion.conectar();
            Statement sentencia = con.createStatement();
            sentencia.executeUpdate(sql);
            flag = true;

        } catch (SQLException exp) {
        }
        return flag;
    }

    public boolean eliminarParticipante(String idParticipante) {

        boolean flag = false;
        String sql;
        sql = "DELETE FROM Participante where idpart='" + idParticipante + "';";

        try {
            con = conexion.conectar();
            Statement sentencia = con.createStatement();
            sentencia.executeUpdate(sql);
            flag = true;
        } catch (SQLException exp) {
        }

        return flag;
    }
}
