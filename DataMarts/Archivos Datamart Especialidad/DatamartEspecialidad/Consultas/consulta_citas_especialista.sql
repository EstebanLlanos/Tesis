/*SELECT nombre_especialista, SUM(desp.cantidad) AS total_citas FROM dim_cita_especialidad desp
INNER JOIN especialista esp ON (desp.especialista_cita = esp.id_especialista)
INNER JOIN ciudad cd ON (desp.ciudad_cita = cd.id_ciudad)
INNER JOIN demografia dm ON (desp.demografia_cita = dm.id_demografia) WHERE estrato_demografia = '6' AND genero_demografia = 'M' 
GROUP BY nombre_especialista 
ORDER BY total_citas DESC;
*/

SELECT fch.anio_actual, SUM(desp.cantidad) AS total_citas FROM dim_cita_especialidad desp
INNER JOIN especialista esp ON (desp.especialista_cita = esp.id_especialista)
INNER JOIN dim_fecha fch ON (desp.fecha_actividad = fch.id_dim_fecha) WHERE especialista_cita = '2000' 
GROUP BY anio_actual
ORDER BY anio_actual ASC;