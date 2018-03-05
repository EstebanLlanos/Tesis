SELECT nombre_especialidad, id_especialista, cd.departamento_ciudad, de.ciudad_cita, de.fecha_actividad, de.demografia_cita, dm.genero_demografia, dm.estrato_demografia, SUM(de.cantidad) AS total_citas FROM dim_cita_especialidad de
INNER JOIN (SELECT especialidad.nombre_especialidad, especialista.id_especialista FROM especialidad especialidad
INNER JOIN especialista especialista ON (especialidad.id_especialidad = especialista.especialidad_especialista)) AS consulta 
ON (de.especialista_cita = consulta.id_especialista)
INNER JOIN ciudad cd ON (de.ciudad_cita = cd.id_ciudad)
INNER JOIN demografia dm ON (de.demografia_cita = dm.id_demografia)
GROUP BY nombre_especialidad, id_especialista, ciudad_cita, fecha_actividad, demografia_cita, cd.departamento_ciudad, genero_demografia, estrato_demografia
ORDER BY total_citas DESC;

/*
SELECT esp.nombre_especialidad, SUM(de.total_ventas) AS total_ventas FROM dim_cita_especialidad de 
INNER JOIN especialidad esp ON (de.vendedor = per.id_personal) 
INNER JOIN dim_fecha fch ON (dv.fecha_venta = fch.id_dim_fecha) WHERE id_personal = '550'
GROUP BY per.id_personal, fch.anio_actual
ORDER BY fch.anio_actual
*/