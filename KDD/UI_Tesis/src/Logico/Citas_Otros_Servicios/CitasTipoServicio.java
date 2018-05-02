
package Logico.Citas_Otros_Servicios;

import Logico.Citas_Examenes.*;

/**
 *
 * @author Esteban - Casa
 */
public class CitasTipoServicio {
    
    String ciudad, genero, estrato, edad, ingresos, anioInicio, anioFin, mesInicio, mesFin;
    
    public void CitaExamen(){
        ciudad = "";
        genero = "";
        estrato = "";
        edad = "";
        ingresos = "";
        anioInicio = "";
        anioFin = "";
        mesInicio = "";
        mesFin = "";
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getIngresos() {
        return ingresos;
    }

    public void setIngresos(String ingresos) {
        this.ingresos = ingresos;
    }

    public String getAnioInicio() {
        return anioInicio;
    }

    public void setAnioInicio(String anioInicio) {
        this.anioInicio = anioInicio;
    }

    public String getAnioFin() {
        return anioFin;
    }

    public void setAnioFin(String anioFin) {
        this.anioFin = anioFin;
    }

    public String getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(String mesInicio) {
        this.mesInicio = mesInicio;
    }

    public String getMesFin() {
        return mesFin;
    }

    public void setMesFin(String mesFin) {
        this.mesFin = mesFin;
    }
    
    
    
}
