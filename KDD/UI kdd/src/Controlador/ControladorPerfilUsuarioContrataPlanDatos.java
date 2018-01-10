package Controlador;

import Dao.DaoPerfilUsuarioContrataPlanDatos;
import Logico.UsuarioContrataPlanDatos;
import java.util.ArrayList;

/**
 *
 * @author john
 */
public class ControladorPerfilUsuarioContrataPlanDatos {

    DaoPerfilUsuarioContrataPlanDatos daoPerfilUsuarioContrataPlanDatos;

    public ControladorPerfilUsuarioContrataPlanDatos() {

        daoPerfilUsuarioContrataPlanDatos = new DaoPerfilUsuarioContrataPlanDatos();
    }

    public ArrayList getPerfiles(String sexoFemenino, String sexoMasculino, String estadoCivil, String inicioEstrato, String finEstrato) {
        int enero = 0, febrero = 0, marzo = 0, abril = 0, mayo = 0, junio = 0,
                julio = 0, agosto = 0, septiembre = 0, octubre = 0, noviembre = 0, diciembre = 0;
        
        ArrayList contratosMes = new ArrayList();
        
        UsuarioContrataPlanDatos usuarioPlanDatos = new UsuarioContrataPlanDatos();
        usuarioPlanDatos.setSexoFemenino(sexoFemenino);
        usuarioPlanDatos.setSexoMasculino(sexoMasculino);
        usuarioPlanDatos.setEstadoCivil(estadoCivil);
        usuarioPlanDatos.setInicioEstrato(inicioEstrato);
        usuarioPlanDatos.setFinEstrato(finEstrato);

        String restriccionesClausulaWhere = daoPerfilUsuarioContrataPlanDatos.prepararRestriccionesClausulaWherePerfiles(usuarioPlanDatos);
        ArrayList<String[]> abandonos = daoPerfilUsuarioContrataPlanDatos.listaPerfiles(restriccionesClausulaWhere);
        if (!abandonos.isEmpty()) {
            for (int i = 0; i < abandonos.size(); i++) {
                int mes = (int) Double.parseDouble(abandonos.get(i)[1]);
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
