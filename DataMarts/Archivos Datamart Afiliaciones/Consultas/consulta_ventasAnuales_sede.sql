SELECT * FROM (SELECT sd.nombre_sede, fch.anio_actual, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv 
INNER JOIN sede sd ON (dv.sede_venta = sd.id_sede) 
INNER JOIN dim_fecha fch ON (dv.fecha_venta = fch.id_dim_fecha)
GROUP BY fch.anio_actual, sd.nombre_sede 
ORDER BY fch.anio_actual) AS consulta WHERE nombre_sede = 'SEDE CALI' ORDER BY anio_actual ASC;