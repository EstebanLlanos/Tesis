TRUNCATE TABLE dim_tiempo;

-- Unknown member
INSERT INTO dim_tiempo VALUES (
    -1, --id
    '0:0:0', -- tiempo
    0, -- hora
    0, -- hora_militar
    0, -- minuto
    0, -- segundo
    0, -- minuto_del_dia
    0, -- segundo_del_dia
    'Desconocido', -- cuarto_hora
    'Desconocido', -- am_pm
    'Desconocido', -- dia_noche
    'Des', -- dia_noche_abrev
    'Desconocido', -- periodo_de_tiempo
    'Des' -- periodo_de_tiempo_abrev
);

INSERT INTO dim_tiempo
SELECT
  to_char(datum, 'HH24MISS')::integer AS id,
  datum::time AS tiempo,

  to_char(datum, 'HH12')::integer AS hora,
  to_char(datum, 'HH24')::integer AS hora_militar,

  extract(minute FROM datum)::integer AS minuto,

  extract(second FROM datum) AS segundo,

  to_char(datum, 'SSSS')::integer / 60 AS minuto_del_dia,
  to_char(datum, 'SSSS')::integer AS segundo_del_dia,

  to_char(datum - (extract(minute FROM datum)::integer % 15 || 'minutes')::interval, 'hh24:mi') ||
  ' – ' ||
  to_char(datum - (extract(minute FROM datum)::integer % 15 || 'minutes')::interval + '14 minutes'::interval, 'hh24:mi')
    AS cuarto_de_hora,

  to_char(datum, 'AM') AS am_pm,

  CASE WHEN to_char(datum, 'hh24:mi') BETWEEN '08:00' AND '19:59' THEN 'Dia (8AM-8PM)' ELSE 'Noche (8PM-8AM)' END
  AS dia_noche,
  CASE WHEN to_char(datum, 'hh24:mi') BETWEEN '08:00' AND '19:59' THEN 'Dia' ELSE 'Noche' END
  AS dia_noche_abrev,

  CASE
  WHEN to_char(datum, 'hh24:mi') BETWEEN '00:00' AND '03:59' THEN 'Tarde en la Noche (Medianoche-4AM)'
  WHEN to_char(datum, 'hh24:mi') BETWEEN '04:00' AND '07:59' THEN 'Temprano en la Mañana (4AM-8AM)'
  WHEN to_char(datum, 'hh24:mi') BETWEEN '08:00' AND '11:59' THEN 'Mañana (8AM-Mediodia)'
  WHEN to_char(datum, 'hh24:mi') BETWEEN '12:00' AND '15:59' THEN 'Tarde (Mediodia-4PM)'
  WHEN to_char(datum, 'hh24:mi') BETWEEN '16:00' AND '19:59' THEN 'Anochecer (4PM-8PM)'
  WHEN to_char(datum, 'hh24:mi') BETWEEN '20:00' AND '23:59' THEN 'Noche (8PM-Medianoche)'
  END AS periodo_de_tiempo,

  CASE
  WHEN to_char(datum, 'hh24:mi') BETWEEN '00:00' AND '03:59' THEN 'Tarde en la Noche'
  WHEN to_char(datum, 'hh24:mi') BETWEEN '04:00' AND '07:59' THEN 'Temprano en la Mañana'
  WHEN to_char(datum, 'hh24:mi') BETWEEN '08:00' AND '11:59' THEN 'Mañana'
  WHEN to_char(datum, 'hh24:mi') BETWEEN '12:00' AND '15:59' THEN 'Tarde'
  WHEN to_char(datum, 'hh24:mi') BETWEEN '16:00' AND '19:59' THEN 'Anochecer'
  WHEN to_char(datum, 'hh24:mi') BETWEEN '20:00' AND '23:59' THEN 'Noche'
  END AS periodo_de_tiempo_abrev

FROM generate_series('2000-01-01 00:00:00'::timestamp, '2000-01-01 23:59:59'::timestamp, '1 second') datum;