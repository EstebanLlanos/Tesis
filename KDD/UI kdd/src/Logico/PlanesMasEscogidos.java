package Logico;

/**
 *
 * @author john
 */
public class PlanesMasEscogidos {

    String planDeDatos, PlanPrepagoVoz, PlanPostpagoVoz, PlanCorporativo;

    public PlanesMasEscogidos() {

        planDeDatos = "";
        PlanPrepagoVoz = "";
        PlanPostpagoVoz = "";
        PlanCorporativo = "";
        
    }

    public String getPlanDeDatos() {
        return planDeDatos;
    }

    public void setPlanDeDatos(String planDeDatos) {
        this.planDeDatos = planDeDatos;
    }

    public String getPlanPrepagoVoz() {
        return PlanPrepagoVoz;
    }

    public void setPlanPrepagoVoz(String PlanPrepagoVoz) {
        this.PlanPrepagoVoz = PlanPrepagoVoz;
    }

    public String getPlanPostpagoVoz() {
        return PlanPostpagoVoz;
    }

    public void setPlanPostpagoVoz(String PlanPostpagoVoz) {
        this.PlanPostpagoVoz = PlanPostpagoVoz;
    }

    public String getPlanCorporativo() {
        return PlanCorporativo;
    }

    public void setPlanCorporativo(String PlanCorporativo) {
        this.PlanCorporativo = PlanCorporativo;
    }
    
}
