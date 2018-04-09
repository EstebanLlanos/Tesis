CREATE TABLE "public"."dim_tiempo" (
    id int4 NOT NULL,
    tiempo time,
    hora int2,
    hora_militar int2,
    minuto int4,
    seggundo int4,
    minuto_del_dia int4,
    segundo_del_dia int4,
    cuarto_hora varchar,
    am_pm varchar,
    dia_noche varchar,
    dia_noche_abrev varchar,
    periodo_tiempo varchar,
    periodo_tiempo_abrev varchar
)
WITH (OIDS=FALSE);