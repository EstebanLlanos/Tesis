/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao.Solicitud_Servicios;

import ConectorBD.ConexionBD;
import Logico.Solicitud_Servicios.SolicitudTipoServicio;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * @author Esteban
 */
public class DaoSolicitudTipoServicio {
    
    ConexionBD BaseDeDatos;
    Connection conn;
    Statement stmt;
    public static ResultSet rsCandidato;
    public String criterioConsultaSolicitudes;
    
    int anioInicio;
    int anioFin;
    int mesInicio;
    int mesFin;

    public DaoSolicitudTipoServicio() {
        BaseDeDatos = new ConexionBD();
    }

    public String prepararRestriccionesClausulaWhereSolicitudes(SolicitudTipoServicio solicitudTipoServicio) {

        String where = "";

        int codigoCiudad = obtenerCodigoCiudad(solicitudTipoServicio.getCiudad());
        
        anioInicio = 0;
        anioFin = 0;
        mesInicio = 0;
        mesFin = 0;
        
        /*
        * En esta parte de determina el formato que debe tener el año de inicio y de fin de la consulta
        * de acueerdo a la selección que el usuario haya hecho
        */
        if (!solicitudTipoServicio.getAnioInicio().equals("Escoger una Opción...") && !solicitudTipoServicio.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(solicitudTipoServicio.getAnioInicio());
            anioFin = Integer.parseInt(solicitudTipoServicio.getAnioFin());            
        } else if (!solicitudTipoServicio.getAnioInicio().equals("Escoger una Opción...") && solicitudTipoServicio.getAnioFin().equals("Escoger una Opción...")){
            anioInicio = Integer.parseInt(solicitudTipoServicio.getAnioInicio());
        } else if (solicitudTipoServicio.getAnioInicio().equals("Escoger una Opción...") && !solicitudTipoServicio.getAnioFin().equals("Escoger una Opción...")){
            anioFin = Integer.parseInt(solicitudTipoServicio.getAnioFin());
        }
        
        // INICIO IF + CIUDAD
        if (!solicitudTipoServicio.getCiudad().equals("Escoger una Opción...")) {
            
            if (!solicitudTipoServicio.getAnioInicio().equals("Escoger una Opción...") && !solicitudTipoServicio.getAnioFin().equals("Escoger una Opción...")) {
                System.out.println("CIUDAD - AÑO INICIO - AÑO FIN");
                if (!solicitudTipoServicio.getMesInicio().equals("Escoger una Opción...") && !solicitudTipoServicio.getMesFin().equals("Escoger una Opción...")) {
                    System.out.println("CIUDAD - AÑO INICIO - AÑO FIN - MES INICIO - MES FIN");
                    mesInicio = obtenerCodigoMes(solicitudTipoServicio.getMesInicio());
                    mesFin = obtenerCodigoMes(solicitudTipoServicio.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE ciudad_solicitud = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE ciudad_solicitud = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE ciudad_solicitud = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE ciudad_solicitud = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioFin + mesFin + "01') ";
                        
                    }

                } else if(solicitudTipoServicio.getMesInicio().equals("Escoger una Opción...") && solicitudTipoServicio.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ciudad_solicitud = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioFin+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                
            } // FIN IF + AÑO INICIO + AÑO FIN 
            else if (!solicitudTipoServicio.getAnioInicio().equals("Escoger una Opción...") && solicitudTipoServicio.getAnioFin().equals("Escoger una Opción...")) {
               
                System.out.println("CIUDAD - AÑO INICIO");
                if (!solicitudTipoServicio.getMesInicio().equals("Escoger una Opción...") && !solicitudTipoServicio.getMesFin().equals("Escoger una Opción...")) {
                    System.out.println("CIUDAD - AÑO INICIO - MES INICIO - MES FIN");
                    mesInicio = obtenerCodigoMes(solicitudTipoServicio.getMesInicio());
                    mesFin = obtenerCodigoMes(solicitudTipoServicio.getMesFin());

                    if (mesInicio < 10 && mesFin < 10) {
                        
                        where = " WHERE ciudad_solicitud = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + "0" + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin < 10){
                        
                        where = " WHERE ciudad_solicitud = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + "0" +mesFin + "01') ";
                        
                    } else if (mesInicio < 10 && mesFin >= 10){
                        
                        where = " WHERE ciudad_solicitud = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + "0" + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    } else if (mesInicio >= 10 && mesFin >= 10){
                        
                        where = " WHERE ciudad_solicitud = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + anioInicio + mesInicio + "01' AND '" + anioInicio + mesFin + "01') ";
                        
                    }

                } else if(solicitudTipoServicio.getMesInicio().equals("Escoger una Opción...") && solicitudTipoServicio.getMesFin().equals("Escoger una Opción...")){
                        where = " WHERE ciudad_solicitud = '" + codigoCiudad
                        + "' AND (fecha_actividad BETWEEN '" + (anioInicio+"").substring(0, 4) + "0101' AND '" + (anioInicio+"").substring(0, 4) + "1201') ";
                } else {
                    return "Error Fecha Mes";
                }
                 
            }  else if (solicitudTipoServicio.getAnioInicio().equals("Escoger una Opción...") && !solicitudTipoServicio.getAnioFin().equals("Escoger una Opción...")) {
                return "Error Fecha";
            } else {
                where = " WHERE ciudad_solicitud = '" + codigoCiudad+ "' ";
            }

        } //FIN IF + CIUDAD
        else {
            
            return "Error";
        }
        
        return where;
    }

    public ArrayList<String[]> conteoSolicitudes(String where) {

        ArrayList<String[]> conteoCitas = new ArrayList<String[]>();
        String sql_select;
        
        sql_select = "SELECT nombre_tipo_servicio, SUM(se.cantidad) AS total_solicitudes FROM datamart_solicitud_servicios se "
                   + "INNER JOIN dim_especialidad esp ON (se.especialidad_solicitud = esp.id_especialidad) "
                   + "INNER JOIN dim_tipo_servicio ts ON (se.tipo_servicio_solicitud = ts.id_tipo_servicio) " + where
                   + "GROUP BY nombre_tipo_servicio "
                   + "ORDER BY total_solicitudes;"; 
        
        System.out.println("Consulta: " + sql_select);

        try {
            conn = BaseDeDatos.conectar();
            Statement sentencia = conn.createStatement();
            ResultSet tabla = sentencia.executeQuery(sql_select);
            
            while (tabla.next()) {
                
                String[] registro = new String[2];
                registro[0] = tabla.getString("nombre_tipo_servicio");
                registro[1] = tabla.getString("total_solicitudes");
                
                conteoCitas.add(registro);
                
            }
            
            return conteoCitas;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return null;
    }
    
    public int obtenerCodigoMes(String nombreMes){
        
        int mesSeleccionado = 0;
        
        if (nombreMes.equals("Enero")) {
            mesSeleccionado = 1;
        } else if (nombreMes.equals("Febrero")) {
            mesSeleccionado = 2;
        } else if (nombreMes.equals("Marzo")) {
            mesSeleccionado = 3;
        } else if (nombreMes.equals("Abril")) {
            mesSeleccionado = 4;
        } else if (nombreMes.equals("Mayo")) {
            mesSeleccionado = 5;
        } else if (nombreMes.equals("Junio")) {
            mesSeleccionado = 6;
        } else if (nombreMes.equals("Julio")) {
            mesSeleccionado = 7;
        } else if (nombreMes.equals("Agosto")) {
            mesSeleccionado = 8;
        } else if (nombreMes.equals("Septiembre")) {
            mesSeleccionado = 9;
        } else if (nombreMes.equals("Octubre")) {
            mesSeleccionado = 10;
        } else if (nombreMes.equals("Noviembre")) {
            mesSeleccionado = 11;
        } else if (nombreMes.equals("Diciembre")) {
            mesSeleccionado = 12;
        }
        
        return mesSeleccionado;
    }
    
    public int obtenerCodigoCiudad(String nombreCiudad){
        
        int ciudadSeleccionada = 0;
        
        if (nombreCiudad.equals("KM 30")) {
            ciudadSeleccionada = 1;
        } else if (nombreCiudad.equals("ALCALA")) {
            ciudadSeleccionada = 2;
        } else if (nombreCiudad.equals("AMAIME")) {
            ciudadSeleccionada = 3;
        } else if (nombreCiudad.equals("ANDALUCIA")) {
            ciudadSeleccionada = 4;
        } else if (nombreCiudad.equals("ANSERMA NUEVO")) {
            ciudadSeleccionada = 5;
        } else if (nombreCiudad.equals("BOLIVAR")) {
            ciudadSeleccionada = 6;
        } else if (nombreCiudad.equals("BOLO")) {
            ciudadSeleccionada = 7;
        } else if (nombreCiudad.equals("BUCHITOLO")) {
            ciudadSeleccionada = 8;
        } else if (nombreCiudad.equals("BUENAVENTURA")) {
            ciudadSeleccionada = 9;
        } else if (nombreCiudad.equals("BUGA")) {
            ciudadSeleccionada = 10;
        } else if (nombreCiudad.equals("BUGALAGRANDE")) {
            ciudadSeleccionada = 11;
        } else if (nombreCiudad.equals("CAICEDONIA")) {
            ciudadSeleccionada = 12;
        } else if (nombreCiudad.equals("CALI")) {
            ciudadSeleccionada = 13;
        } else if (nombreCiudad.equals("CANDELARIA")) {
            ciudadSeleccionada = 14;
        } else if (nombreCiudad.equals("CARTAGO")) {
            ciudadSeleccionada = 15;
        } else if (nombreCiudad.equals("COSTA RICA")) {
            ciudadSeleccionada = 16;
        } else if (nombreCiudad.equals("DAGUA")) {
            ciudadSeleccionada = 17;
        } else if (nombreCiudad.equals("DAPA")) {
            ciudadSeleccionada = 18;
        } else if (nombreCiudad.equals("DARIEN")) {
            ciudadSeleccionada = 19;
        } else if (nombreCiudad.equals("EL AGUILA")) {
            ciudadSeleccionada = 20;
        } else if (nombreCiudad.equals("EL CABUYAL")) {
            ciudadSeleccionada = 21;
        } else if (nombreCiudad.equals("EL CARMELO")) {
            ciudadSeleccionada = 22;
        } else if (nombreCiudad.equals("EL CARMEN")) {
            ciudadSeleccionada = 23;
        } else if (nombreCiudad.equals("EL CERRITO")) {
            ciudadSeleccionada = 24;
        } else if (nombreCiudad.equals("EL DOVIO")) {
            ciudadSeleccionada = 25;
        } else if (nombreCiudad.equals("EL PALMAR")) {
            ciudadSeleccionada = 26;
        } else if (nombreCiudad.equals("EL TREINTA")) {
            ciudadSeleccionada = 27;
        } else if (nombreCiudad.equals("EL VINCULO")) {
            ciudadSeleccionada = 28;
        } else if (nombreCiudad.equals("FLORIDA")) {
            ciudadSeleccionada = 29;
        } else if (nombreCiudad.equals("GINEBRA")) {
            ciudadSeleccionada = 30;
        } else if (nombreCiudad.equals("GUABITAS")) {
            ciudadSeleccionada = 31;
        } else if (nombreCiudad.equals("GUACARI")) {
            ciudadSeleccionada = 32;
        } else if (nombreCiudad.equals("JAMUNDI")) {
            ciudadSeleccionada = 33;
        } else if (nombreCiudad.equals("LA BUITRERA")) {
            ciudadSeleccionada = 34;
        } else if (nombreCiudad.equals("LA CUMBRE")) {
            ciudadSeleccionada = 35;
        } else if (nombreCiudad.equals("LA HERRADURA")) {
            ciudadSeleccionada = 36;
        } else if (nombreCiudad.equals("LA MARINA")) {
            ciudadSeleccionada = 37;
        } else if (nombreCiudad.equals("LA PAILA")) {
            ciudadSeleccionada = 38;
        } else if (nombreCiudad.equals("LA QUISQUINA")) {
            ciudadSeleccionada = 39;
        } else if (nombreCiudad.equals("LA UNION")) {
            ciudadSeleccionada = 40;
        } else if (nombreCiudad.equals("LA URIBE")) {
            ciudadSeleccionada = 41;
        } else if (nombreCiudad.equals("LA VICTORIA")) {
            ciudadSeleccionada = 42;
        } else if (nombreCiudad.equals("LOS CHANCOS")) {
            ciudadSeleccionada = 43;
        } else if (nombreCiudad.equals("MEDIA CANOA")) {
            ciudadSeleccionada = 44;
        } else if (nombreCiudad.equals("OBANDO")) {
            ciudadSeleccionada = 45;
        } else if (nombreCiudad.equals("ORTIGAL")) {
            ciudadSeleccionada = 46;
        } else if (nombreCiudad.equals("PALMASECA")) {
            ciudadSeleccionada = 47;
        } else if (nombreCiudad.equals("PALMIRA")) {
            ciudadSeleccionada = 48;
        } else if (nombreCiudad.equals("POTRERILLO")) {
            ciudadSeleccionada = 49;
        } else if (nombreCiudad.equals("PRADERA")) {
            ciudadSeleccionada = 50;
        } else if (nombreCiudad.equals("PRESIDENTE")) {
            ciudadSeleccionada = 51;
        } else if (nombreCiudad.equals("RESTREPO")) {
            ciudadSeleccionada = 52;
        } else if (nombreCiudad.equals("RIO FRIO")) {
            ciudadSeleccionada = 53;
        } else if (nombreCiudad.equals("ROLDANILLO")) {
            ciudadSeleccionada = 54;
        } else if (nombreCiudad.equals("ROZO")) {
            ciudadSeleccionada = 55;
        } else if (nombreCiudad.equals("SALONICA")) {
            ciudadSeleccionada = 56;
        } else if (nombreCiudad.equals("SAN ANTONIO DE LOS C")) {
            ciudadSeleccionada = 57;
        } else if (nombreCiudad.equals("SAN JUAQUIN")) {
            ciudadSeleccionada = 58;
        } else if (nombreCiudad.equals("SAN PEDRO")) {
            ciudadSeleccionada = 59;
        } else if (nombreCiudad.equals("SANTA ELENA")) {
            ciudadSeleccionada = 60;
        } else if (nombreCiudad.equals("SEVILLA")) {
            ciudadSeleccionada = 61;
        } else if (nombreCiudad.equals("SONSO")) {
            ciudadSeleccionada = 62;
        } else if (nombreCiudad.equals("TABLONES")) {
            ciudadSeleccionada = 63;
        } else if (nombreCiudad.equals("TENERIFE")) {
            ciudadSeleccionada = 64;
        } else if (nombreCiudad.equals("TIENDA NUEVA")) {
            ciudadSeleccionada = 65;
        } else if (nombreCiudad.equals("TODOS LOS SANTOS")) {
            ciudadSeleccionada = 66;
        } else if (nombreCiudad.equals("TORO")) {
            ciudadSeleccionada = 67;
        } else if (nombreCiudad.equals("TRUJILLO")) {
            ciudadSeleccionada = 68;
        } else if (nombreCiudad.equals("TULUA")) {
            ciudadSeleccionada = 69;
        } else if (nombreCiudad.equals("TUMACO NARINO")) {
            ciudadSeleccionada = 70;
        } else if (nombreCiudad.equals("ULLOA")) {
            ciudadSeleccionada = 71;
        } else if (nombreCiudad.equals("VERSALLES")) {
            ciudadSeleccionada = 72;
        } else if (nombreCiudad.equals("VIJES")) {
            ciudadSeleccionada = 73;
        } else if (nombreCiudad.equals("VILLAGORGONA")) {
            ciudadSeleccionada = 74;
        } else if (nombreCiudad.equals("YOTOCO")) {
            ciudadSeleccionada = 75;
        } else if (nombreCiudad.equals("YUMBO")) {
            ciudadSeleccionada = 76;
        } else if (nombreCiudad.equals("ZARAGOZA")) {
            ciudadSeleccionada = 77;
        } else if (nombreCiudad.equals("ZARZAL")) {
            ciudadSeleccionada = 78;
        } else if (nombreCiudad.equals("BUENOS AIRES")) {
            ciudadSeleccionada = 79;
        } else if (nombreCiudad.equals("CAJIBIO")) {
            ciudadSeleccionada = 80;
        } else if (nombreCiudad.equals("CALDONO")) {
            ciudadSeleccionada = 81;
        } else if (nombreCiudad.equals("CALOTO")) {
            ciudadSeleccionada = 82;
        } else if (nombreCiudad.equals("CORINTO")) {
            ciudadSeleccionada = 83;
        } else if (nombreCiudad.equals("GUACHENE")) {
            ciudadSeleccionada = 84;
        } else if (nombreCiudad.equals("GUAPI")) {
            ciudadSeleccionada = 85;
        } else if (nombreCiudad.equals("MIRANDA")) {
            ciudadSeleccionada = 86;
        } else if (nombreCiudad.equals("MONDOMO")) {
            ciudadSeleccionada = 87;
        } else if (nombreCiudad.equals("MORALES")) {
            ciudadSeleccionada = 88;
        } else if (nombreCiudad.equals("PADILLA")) {
            ciudadSeleccionada = 89;
        } else if (nombreCiudad.equals("PESCADOR")) {
            ciudadSeleccionada = 90;
        } else if (nombreCiudad.equals("PIENDAMO")) {
            ciudadSeleccionada = 91;
        } else if (nombreCiudad.equals("POPAYAN")) {
            ciudadSeleccionada = 92;
        } else if (nombreCiudad.equals("PUERTO TEJADA")) {
            ciudadSeleccionada = 93;
        } else if (nombreCiudad.equals("SANTANDER Q")) {
            ciudadSeleccionada = 94;
        } else if (nombreCiudad.equals("SILVIA")) {
            ciudadSeleccionada = 95;
        } else if (nombreCiudad.equals("SUAREZ")) {
            ciudadSeleccionada = 96;
        } else if (nombreCiudad.equals("TIMBIQUI")) {
            ciudadSeleccionada = 97;
        } else if (nombreCiudad.equals("TORIBIO")) {
            ciudadSeleccionada = 98;
        } else if (nombreCiudad.equals("TOTORO")) {
            ciudadSeleccionada = 99;
        } else if (nombreCiudad.equals("TUNIA")) {
            ciudadSeleccionada = 100;
        } else if (nombreCiudad.equals("VILLA RICA")) {
            ciudadSeleccionada = 101;
        } else if (nombreCiudad.equals("ALTAGRACIA")) {
            ciudadSeleccionada = 102;
        } else if (nombreCiudad.equals("ARMENIA")) {
            ciudadSeleccionada = 103;
        } else if (nombreCiudad.equals("CHINCHINA")) {
            ciudadSeleccionada = 104;
        } else if (nombreCiudad.equals("DOS QUEBRADAS")) {
            ciudadSeleccionada = 105;
        } else if (nombreCiudad.equals("LA FLORIDA")) {
            ciudadSeleccionada = 106;
        } else if (nombreCiudad.equals("LA VIRGINIA")) {
            ciudadSeleccionada = 107;
        } else if (nombreCiudad.equals("MARSELLA")) {
            ciudadSeleccionada = 108;
        } else if (nombreCiudad.equals("PEREIRA")) {
            ciudadSeleccionada = 109;
        } else if (nombreCiudad.equals("SANTA ROSA")) {
            ciudadSeleccionada = 110;
        } else if (nombreCiudad.equals("SANTUARIO")) {
            ciudadSeleccionada = 111;
        } else if (nombreCiudad.equals("ARMENIA")) {
            ciudadSeleccionada = 112;
        } else if (nombreCiudad.equals("BARCELONA")) {
            ciudadSeleccionada = 113;
        } else if (nombreCiudad.equals("BUENAVISTA")) {
            ciudadSeleccionada = 114;
        } else if (nombreCiudad.equals("CALARCA")) {
            ciudadSeleccionada = 115;
        } else if (nombreCiudad.equals("CIRCASIA")) {
            ciudadSeleccionada = 116;
        } else if (nombreCiudad.equals("CORDOBA")) {
            ciudadSeleccionada = 117;
        } else if (nombreCiudad.equals("FILANDIA")) {
            ciudadSeleccionada = 118; 
        } else if (nombreCiudad.equals("GENOVA")) {
            ciudadSeleccionada = 119;
        } else if (nombreCiudad.equals("MONTENEGRO")) {
            ciudadSeleccionada = 120;
        } else if (nombreCiudad.equals("PIJAO")) {
            ciudadSeleccionada = 121;
        } else if (nombreCiudad.equals("QUIMBAYA")) {
            ciudadSeleccionada = 122;
        } else if (nombreCiudad.equals("SALENTO")) {
            ciudadSeleccionada = 123;
        } else if (nombreCiudad.equals("LA TEBAIDA")) {
            ciudadSeleccionada = 124;
        } else if (nombreCiudad.equals("EL CHARCO")) {
            ciudadSeleccionada = 125;
        } else if (nombreCiudad.equals("IPIALES")) {
            ciudadSeleccionada = 126;
        } else if (nombreCiudad.equals("PASTO")) {
            ciudadSeleccionada = 127;
        } else if (nombreCiudad.equals("TUMACO")) {
            ciudadSeleccionada = 128;
        } else if (nombreCiudad.equals("MANIZALES")) {
            ciudadSeleccionada = 129;
        } else if (nombreCiudad.equals("CHINCHINA")) {
            ciudadSeleccionada = 130;
        } else if (nombreCiudad.equals("VITERBO")) {
            ciudadSeleccionada = 131;
        } else if (nombreCiudad.equals("RIOSUCIO")) {
            ciudadSeleccionada = 132;
        } else if (nombreCiudad.equals("ANSERMA")) {
            ciudadSeleccionada = 133;
        } else if (nombreCiudad.equals("SALAMINA")) {
            ciudadSeleccionada = 134;
        } else if (nombreCiudad.equals("ARGELIA")) {
            ciudadSeleccionada = 135;
        } else if (nombreCiudad.equals("JURADO")) {
            ciudadSeleccionada = 136;
        } else if (nombreCiudad.equals("BOGOTA")) {
            ciudadSeleccionada = 137;
        } else if (nombreCiudad.equals("VILLAVICENCIO")) {
            ciudadSeleccionada = 138;
        } else if (nombreCiudad.equals("NEIVA")) {
            ciudadSeleccionada = 139;
        } else if (nombreCiudad.equals("BUCARAMANGA")) {
            ciudadSeleccionada = 140;
        } else if (nombreCiudad.equals("BARRANQUILLA")) {
            ciudadSeleccionada = 141;
        } else if (nombreCiudad.equals("IBAGUE")) {
            ciudadSeleccionada = 142;
        } else if (nombreCiudad.equals("MEDELLIN")) {
            ciudadSeleccionada = 143;
        }
        
        return ciudadSeleccionada;
    }
    
    public void desconectar() {
        try {
            rsCandidato.close();
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
