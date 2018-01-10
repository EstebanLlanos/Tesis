
package Controlador;

import Dao.DaoPerfilUsuarioPlanPospago;
import Logico.UsuarioPlanPospago;
import java.util.ArrayList;

/**
 *
 * @author john
 */
public class ControladorPerfilUsuarioPlanPospago {
    
     DaoPerfilUsuarioPlanPospago daoPerfilPlanesPospago;

    public ControladorPerfilUsuarioPlanPospago() {

        daoPerfilPlanesPospago = new DaoPerfilUsuarioPlanPospago();
    }

    public ArrayList<String[]>   getPerfiles(String sexoFemenino, String sexoMasculino, String estadoCivil, String inicioEstrato, String finEstrato) {
        int enero = 0, febrero = 0, marzo = 0, abril = 0, mayo = 0, junio = 0,
                julio = 0, agosto = 0, septiembre = 0, octubre = 0, noviembre = 0, diciembre = 0;
        
        ArrayList contratosMes = new ArrayList();
        
        UsuarioPlanPospago planesPospago = new UsuarioPlanPospago();
        planesPospago.setSexoFemenino(sexoFemenino);
        planesPospago.setSexoMasculino(sexoMasculino);
        planesPospago.setEstadoCivil(estadoCivil);
        planesPospago.setInicioEstrato(inicioEstrato);
        planesPospago.setFinEstrato(finEstrato);

        String restriccionesClausulaWhere = daoPerfilPlanesPospago.prepararRestriccionesClausulaWherePerfiles(planesPospago);
        ArrayList<String[]> contratos = daoPerfilPlanesPospago.listaPerfiles(restriccionesClausulaWhere);
        
        if (!contratos.isEmpty()) {
            for (int i = 0; i < contratos.size(); i++) {
                int mes = (int) Double.parseDouble(contratos.get(i)[1]);
                switch (mes) {
                    case 1:
                        enero++;
                        break;
                    case 2:
                        febrero++;
                        break;
                    case 3:
                        marzo++;
                        break;
                    case 4:
                        abril++;
                        break;
                    case 5:
                        mayo++;
                        break;
                    case 6:
                        junio++;
                        break;
                    case 7:
                        julio++;
                        break;
                    case 8:
                        agosto++;
                        break;
                    case 9:
                        septiembre++;
                        break;
                    case 10:
                        octubre++;
                        break;
                    case 11:
                        noviembre++;
                        break;
                    case 12:
                        diciembre++;
                        break;
                    default:
                        System.out.println("Mes desconocido!");
                }
            }
            contratosMes.add(enero);
            contratosMes.add(febrero);
            contratosMes.add(marzo);
            contratosMes.add(abril);
            contratosMes.add(mayo);
            contratosMes.add(junio);
            contratosMes.add(julio);
            contratosMes.add(agosto);
            contratosMes.add(septiembre);
            contratosMes.add(octubre);
            contratosMes.add(noviembre);
            contratosMes.add(diciembre);
            
        }
        return contratosMes;
    }
}
