-- Schema: colmovil

-- DROP SCHEMA colmovil;

SET search_path TO colmovil;

COPY cliente FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/clientes.csv' DELIMITER ',' CSV;

COPY localizacion FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/localizacion.csv' DELIMITER ',' CSV;

COPY oficina FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/oficinas.csv' DELIMITER ',' CSV;

COPY operador_roaming FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/operador_roaming.csv' DELIMITER ',' CSV;

COPY equipo_celular FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/equipo_celular.csv' DELIMITER ',' CSV;

COPY plan_voz FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/plan_voz.csv' DELIMITER ',' CSV;

COPY plan_datos FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/plan_datos.csv' DELIMITER ',' CSV;

COPY sim_card FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/sim_card.csv' DELIMITER ',' CSV;

COPY contrato FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/contratos_sim_carddef.csv' DELIMITER ',' CSV;

COPY operador FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/operador.csv' DELIMITER ',' CSV;

COPY llamada FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/llamadas_sim_card.csv' DELIMITER ',' CSV;

COPY retiro FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/retiros_sim_card.csv' DELIMITER ',' CSV;

COPY recarga FROM 'C:/Users/Miguel Torrres/Documents/Tesis/KDD/DatosColmovil/recargas_sim_card.csv' DELIMITER ',' CSV;