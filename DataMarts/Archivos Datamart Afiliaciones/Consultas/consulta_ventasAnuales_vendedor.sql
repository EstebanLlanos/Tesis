SELECT per.id_personal, fch.anio_actual, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv 
INNER JOIN personal per ON (dv.vendedor = per.id_personal) 
INNER JOIN dim_fecha fch ON (dv.fecha_venta = fch.id_dim_fecha) WHERE id_personal = '550'
GROUP BY per.id_personal, fch.anio_actual
ORDER BY fch.anio_actual