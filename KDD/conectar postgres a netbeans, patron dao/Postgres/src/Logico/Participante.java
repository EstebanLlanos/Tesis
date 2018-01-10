package Logico;

public class Participante {
    
    String idPart, nombre, nroCel, correo, nivelFormacion, genero;
    int edad;
    
    public void setIDPart(String idPart){
        this.idPart = idPart;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setEdad(int edad){
        this.edad = edad;
    }
    
    public void setNroCel(String nroCel){
        this.nroCel = nroCel;
    }
    
    public void setCorreo(String correo){
        this.correo = correo;
    }
    
    public void setNivelFormacion(String nivelFormacion){
        this.nivelFormacion = nivelFormacion;
    }
    
    public void setGenero(String genero){
        this.genero = genero;
    }
    
    public String getIDPart(){
        return idPart;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public int getEdad(){
        return edad;
    }
    
    public String getNroCel(){
        return nroCel;
    }
    
    public String getCorreo(){
        return correo;
    }
    
    public String getNivelFormacion(){
        return nivelFormacion;
    }
    
    public String getGenero(){
        return genero;
    }
}
