/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logico.Afiliaciones;

/**
 *
 * @author Miguel Torrres
 */
public class VentasVendedores {
    
    String ciudad, sede, region;
    
    public VentasVendedores(){
    
        ciudad = "";
        sede = "";
        region = "";
        
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }   
    
}
