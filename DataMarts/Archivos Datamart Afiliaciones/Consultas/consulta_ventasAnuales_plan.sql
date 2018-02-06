SELECT * FROM (SELECT pl.nombre_plan, fch.anio_actual, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv 
INNER JOIN plan pl ON (dv.plan_venta = pl.id_plan) 
INNER JOIN dim_fecha fch ON (dv.fecha_venta = fch.id_dim_fecha)
GROUP BY fch.anio_actual, sd.nombre_sede 
ORDER BY fch.anio_actual) AS consulta WHERE nombre_plan = '"PREVISER ESPECIAL REF.3"' ORDER BY anio_actual ASC;