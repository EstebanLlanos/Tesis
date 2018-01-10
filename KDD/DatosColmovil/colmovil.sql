COPY cliente FROM '/home/esteban/Escritorio/DatosColmovil/clientes.csv' DELIMITER ',' CSV;

COPY localizacion FROM '/home/esteban/Escritorio/DatosColmovil/localizacion.csv' DELIMITER ',' CSV;

COPY oficina FROM '/home/esteban/Escritorio/DatosColmovil/oficinas.csv' DELIMITER ',' CSV;

COPY operador_roaming FROM '/home/esteban/Escritorio/DatosColmovil/operador_roaming.csv' DELIMITER ',' CSV;

COPY equipo_celular FROM '/home/esteban/Escritorio/DatosColmovil/equipo_celular.csv' DELIMITER ',' CSV;

COPY plan_voz FROM '/home/esteban/Escritorio/DatosColmovil/plan_voz.csv' DELIMITER ',' CSV;

COPY plan_datos FROM '/home/esteban/Escritorio/DatosColmovil/plan_datos.csv' DELIMITER ',' CSV;

COPY sim_card FROM '/home/esteban/Escritorio/DatosColmovil/sim_card.csv' DELIMITER ',' CSV;

COPY contrato FROM '/home/esteban/Escritorio/DatosColmovil/contratos_sim_carddef.csv' DELIMITER ',' CSV;

COPY operador FROM '/home/esteban/Escritorio/DatosColmovil/operador.csv' DELIMITER ',' CSV;

COPY llamada FROM '/home/esteban/Escritorio/DatosColmovil/llamadas_sim_card.csv' DELIMITER ',' CSV;

COPY retiro FROM '/home/esteban/Escritorio/DatosColmovil/retiros_sim_card.csv' DELIMITER ',' CSV;

COPY recarga FROM '/home/esteban/Escritorio/DatosColmovil/recargas_sim_card.csv' DELIMITER ',' CSV;