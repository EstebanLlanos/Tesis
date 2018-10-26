SELECT descripcion_especialidad, SUM(se.cantidad) AS total_citas FROM datamart_solicitud_servicios se 
INNER JOIN dim_especialidad esp ON (se.especialidad_solicitud = esp.id_especialidad)
INNER JOIN dim_tipo_servicio ts ON (se.tipo_servicio_solicitud = ts.id_tipo_servicio)
GROUP BY descripcion_especialidad
ORDER BY total_citas DESC LIMIT 5;

SELECT nombre_tipo_servicio, SUM(se.cantidad) AS total_citas FROM datamart_solicitud_servicios se 
INNER JOIN dim_especialidad esp ON (se.especialidad_solicitud = esp.id_especialidad)
INNER JOIN dim_tipo_servicio ts ON (se.tipo_servicio_solicitud = ts.id_tipo_servicio)
GROUP BY nombre_tipo_servicio
ORDER BY total_citas ASC LIMIT 5;