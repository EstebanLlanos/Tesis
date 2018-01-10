package Logico;

/**
 *
 * @author john
 */
public class Perfil {

    String sexoFemenino, sexoMasculino, estadoCivil, inicioEstrato, finEstrato;

    public Perfil() {

        sexoFemenino = "";
        sexoMasculino = "";
        estadoCivil = "";
        inicioEstrato = "";
        finEstrato = "";
    }

    public String getSexoFemenino() {
        return sexoFemenino;
    }

    public void setSexoFemenino(String sexoFemenino) {
        this.sexoFemenino = sexoFemenino;
    }

    public String getSexoMasculino() {
        return sexoMasculino;
    }

    public void setSexoMasculino(String sexoMasculino) {
        this.sexoMasculino = sexoMasculino;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getInicioEstrato() {
        return inicioEstrato;
    }

    public void setInicioEstrato(String inicioEstrato) {
        this.inicioEstrato = inicioEstrato;
    }

    public String getFinEstrato() {
        return finEstrato;
    }

    public void setFinEstrato(String finEstrato) {
        this.finEstrato = finEstrato;
    }
}
