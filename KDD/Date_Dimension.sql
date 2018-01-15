DROP TABLE if exists dim_fecha;

CREATE TABLE dim_fecha
(
  id_dim_fecha              INT NOT NULL,
  fecha_actual              DATE NOT NULL,
  epoca                    BIGINT NOT NULL,
  sufijo_dia               VARCHAR(4) NOT NULL,
  nombre_dia                 VARCHAR(9) NOT NULL,
  dia_de_semana              INT NOT NULL,
  dia_de_mes             INT NOT NULL,
  dia_de_cuarto           INT NOT NULL,
  dia_de_anio              INT NOT NULL,
  semana_de_mes            INT NOT NULL,
  semana_de_anio             INT NOT NULL,
  semana_de_anio_iso         CHAR(10) NOT NULL,
  mes_actual             INT NOT NULL,
  nombre_mes               VARCHAR(9) NOT NULL,
  nombre_mes_abreviado   CHAR(3) NOT NULL,
  cuarto_actual           INT NOT NULL,
  nombre_cuarto             VARCHAR(9) NOT NULL,
  anio_actual              INT NOT NULL,
  primer_dia_semana        DATE NOT NULL,
  ultimo_dia_semana         DATE NOT NULL,
  primer_dia_mes       DATE NOT NULL,
  ultimo_dia_mes        DATE NOT NULL,
  primer_dia_cuarto     DATE NOT NULL,
  ultimo_dia_cuarto      DATE NOT NULL,
  primer_dia_anio        DATE NOT NULL,
  ultimo_dia_anio         DATE NOT NULL,
  mmyyyy                   CHAR(6) NOT NULL,
  mmddyyyy                 CHAR(10) NOT NULL,
  fin_de_semana_indr             BOOLEAN NOT NULL
);

ALTER TABLE public.dim_fecha ADD CONSTRAINT dim_fecha_id_pk PRIMARY KEY (id_dim_fecha);

CREATE INDEX dim_fecha_actual_idx
  ON dim_fecha(fecha_actual);

COMMIT;

INSERT INTO dim_fecha
SELECT TO_CHAR(datum,'yyyymmdd')::INT AS id_dim_fecha,
       datum AS fecha_actual,
       EXTRACT(epoch FROM datum) AS epoca,
       TO_CHAR(datum,'Dth') AS sufijo_dia,
       TO_CHAR(datum,'Day') AS nombre_dia,
       EXTRACT(isodow FROM datum) AS dia_de_semana,
       EXTRACT(DAY FROM datum) AS dia_de_mes,
       datum - DATE_TRUNC('quarter',datum)::DATE +1 AS dia_de_cuarto,
       EXTRACT(doy FROM datum) AS dia_de_anio,
       TO_CHAR(datum,'W')::INT AS semana_de_mes,
       EXTRACT(week FROM datum) AS semana_de_anio,
       TO_CHAR(datum,'YYYY"-W"IW-D') AS semana_de_anio_iso,
       EXTRACT(MONTH FROM datum) AS mes_actual,
       TO_CHAR(datum,'Month') AS nombre_mes,
       TO_CHAR(datum,'Mon') AS nombre_mes_abreviado,
       EXTRACT(quarter FROM datum) AS cuarto_actual,
       CASE
         WHEN EXTRACT(quarter FROM datum) = 1 THEN 'Primero'
         WHEN EXTRACT(quarter FROM datum) = 2 THEN 'Segundo'
         WHEN EXTRACT(quarter FROM datum) = 3 THEN 'Tercero'
         WHEN EXTRACT(quarter FROM datum) = 4 THEN 'Cuarto'
       END AS nombre_cuarto,
       EXTRACT(isoyear FROM datum) AS anio_actual,
       datum +(1 -EXTRACT(isodow FROM datum))::INT AS primer_dia_semana,
       datum +(7 -EXTRACT(isodow FROM datum))::INT AS ultimo_dia_semana,
       datum +(1 -EXTRACT(DAY FROM datum))::INT AS primer_dia_mes,
       (DATE_TRUNC('MONTH',datum) +INTERVAL '1 MONTH - 1 day')::DATE AS ultimo_dia_mes,
       DATE_TRUNC('quarter',datum)::DATE AS primer_dia_cuarto,
       (DATE_TRUNC('quarter',datum) +INTERVAL '3 MONTH - 1 day')::DATE AS ultimo_dia_cuarto,
       TO_DATE(EXTRACT(isoyear FROM datum) || '-01-01','YYYY-MM-DD') AS primer_dia_anio,
       TO_DATE(EXTRACT(isoyear FROM datum) || '-12-31','YYYY-MM-DD') AS ultimo_dia_anio,
       TO_CHAR(datum,'mmyyyy') AS mmyyyy,
       TO_CHAR(datum,'mmddyyyy') AS mmddyyyy,
       CASE
         WHEN EXTRACT(isodow FROM datum) IN (6,7) THEN TRUE
         ELSE FALSE
       END AS fin_de_semana_indr
FROM (SELECT '1970-01-01'::DATE+ SEQUENCE.DAY AS datum
      FROM GENERATE_SERIES (0,29219) AS SEQUENCE (DAY)
      GROUP BY SEQUENCE.DAY) DQ
ORDER BY 1;

COMMIT;