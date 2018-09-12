/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logico.Cupos;

/**
 *
 * @author Esteban
 */
public class VentasVendedores {
    
    String ciudad, sede, anioInicio, anioFin, vendedor;
    
    public VentasVendedores(){
        vendedor = "";
        ciudad = "";
        sede = "";
        anioInicio = "";
        anioFin = "";
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

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

}
