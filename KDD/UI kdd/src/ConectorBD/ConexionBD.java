package ConectorBD;

import GUI.Visualizador;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ConexionBD {

    private final String driver = "org.postgresql.Driver";
    private final String name_dir = "jdbc:postgresql://localhost:5432/bodega";
    private final String user = "giossuser";
    private final String password = "giossalpha";
    private Connection conexion;
    
    /*localhost -> es el servidor local, en la universidad es pgsql, el puerto es el mismo
     * pruebaBD -> es el nombre de la base de datos, en la universidad es su usuario
     * user y password son también el usuario con el que inician sesion en la universidad
     */

    public Connection conectar() {
        
//        System.out.println("------------------------------- Ingresa al método conectar ------------------------------------");
        
        boolean errorCargandoDriver = false;
        
        try {
            System.out.println("Carga el Driver");
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "No se encontró el controlador", JOptionPane.ERROR_MESSAGE);
            errorCargandoDriver = true;
        }

        if (!errorCargandoDriver) {

            try {
                conexion = DriverManager.getConnection(name_dir, user, password);
//                System.out.println("------------------------- Establece la conexión-----------------------------");
            } catch (SQLException se) {
                JOptionPane.showMessageDialog(null, se.getMessage(), "Error en base de datos!", JOptionPane.ERROR_MESSAGE);
            }
        }
//        System.out.println("------------------------------- Termina el método de Conexión ------------------------------------");
        return conexion;
    }// fin metodo conectar

    public void desconectarBaseDeDatos(Connection conexion) {

        try {
            conexion.close();
        } catch (SQLException e) {
            System.out.println("No se pudo cerrar. Excepcion:" + e);
        }
    }// fin del metodo desconectarDeBaseDatos
    
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Visualizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Visualizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Visualizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Visualizador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        ConexionBD conexionBd = new ConexionBD();
//        conexionBd.conectar();
//        
//        Statement sentencia;
//        Connection connection = conexionBd.conectar();
//        ResultSet resultSet;
//        System.out.println("----------Inicia Consulta");
//        
//        try {
//
//            sentencia = connection.createStatement();
//            String consulta = "SELECT bodega.retiro.fecha, bodega.fecha.mes FROM bodega.retiro, bodega.demografia, bodega.fecha\n"
//                    + "WHERE retiro.demografia = demografia.sk_demografia"
//                    + " AND retiro.fecha = fecha.sk_fecha AND (demografia.genero = 'Femenino' OR demografia.genero = 'Masculino');";
//
//            System.out.println("Consulta: " + consulta);
//
//            resultSet = sentencia.executeQuery(consulta);
//            
//            System.out.println("----------Termina Consulta\n");
//            resultSet.close();
//            connection.close();
//        } catch (SQLException sqle) {
//            JOptionPane.showMessageDialog(null, sqle.getMessage());
//        }
//    }
}


