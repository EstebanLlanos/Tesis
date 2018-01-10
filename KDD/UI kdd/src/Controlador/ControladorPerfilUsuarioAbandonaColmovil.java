package Controlador;

import Dao.DaoPerfilUsuarioAbandonaColmovil;
import Logico.UsuarioAbandonaColmovil;
import java.util.ArrayList;

public class ControladorPerfilUsuarioAbandonaColmovil {

    DaoPerfilUsuarioAbandonaColmovil daoPerfilAbandonanColmovil;

    public ControladorPerfilUsuarioAbandonaColmovil() {

        daoPerfilAbandonanColmovil = new DaoPerfilUsuarioAbandonaColmovil();
    }

    public ArrayList getPerfiles(String sexoFemenino, String sexoMasculino, String estadoCivil, String inicioEstrato, String finEstrato) {
        int enero = 0, febrero = 0, marzo = 0, abril = 0, mayo = 0, junio = 0,
                julio = 0, agosto = 0, septiembre = 0, octubre = 0, noviembre = 0, diciembre = 0;
        
        ArrayList retirosMes = new ArrayList();

        UsuarioAbandonaColmovil abandono = new UsuarioAbandonaColmovil();
        abandono.setSexoFemenino(sexoFemenino);
        abandono.setSexoMasculino(sexoMasculino);
        abandono.setEstadoCivil(estadoCivil);
        abandono.setInicioEstrato(inicioEstrato);
        abandono.setFinEstrato(finEstrato);

        String restriccionesClausulaWhere = daoPerfilAbandonanColmovil.prepararRestriccionesClausulaWherePerfiles(abandono);
        ArrayList<String[]> abandonos = daoPerfilAbandonanColmovil.listaPerfiles(restriccionesClausulaWhere);

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
            retirosMes.add(enero);
            retirosMes.add(febrero);
            retirosMes.add(marzo);
            retirosMes.add(abril);
            retirosMes.add(mayo);
            retirosMes.add(junio);
            retirosMes.add(julio);
            retirosMes.add(agosto);
            retirosMes.add(septiembre);
            retirosMes.add(octubre);
            retirosMes.add(noviembre);
            retirosMes.add(diciembre);
            
        }
        return retirosMes;
    }
}
