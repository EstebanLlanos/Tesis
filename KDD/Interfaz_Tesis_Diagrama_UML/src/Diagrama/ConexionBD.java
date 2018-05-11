package Diagrama;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionBD {

    private final String driver;
    private final String name_dir;
    private final String user;
    private final String password;
    private Connection conexion;

    public ConexionBD() {
        this.password = "giossalpha";
        this.user = "postgres";
        this.driver = "org.postgresql.Driver";
        this.name_dir = "jdbc:postgresql://localhost:5432/bodega_previser";
    }
    
    /*localhost -> es el servidor local, en la universidad es pgsql, el puerto es el mismo
     * pruebaBD -> es el nombre de la base de datos, en la universidad es su usuario
     * user y password son también el usuario con el que inician sesion en la universidad
     */

    public Connection conectar() {

        boolean errorCargandoDriver = false;
        
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "No se encontró el controlador", JOptionPane.ERROR_MESSAGE);
            errorCargandoDriver = true;
        }

        if (!errorCargandoDriver) {

            try {
                conexion = DriverManager.getConnection(name_dir, user, password);
            } catch (SQLException se) {
                JOptionPane.showMessageDialog(null, se.getMessage(), "Error en base de datos!", JOptionPane.ERROR_MESSAGE);
            }
        }

        return conexion;
    }// fin metodo conectar

    public void desconectarBaseDeDatos(Connection conexion) {

        try {
            conexion.close();
        } catch (Exception e) {
            System.out.println("No se pudo cerrar.");
        }
    }// fin del metodo desconectarDeBaseDatos
}
