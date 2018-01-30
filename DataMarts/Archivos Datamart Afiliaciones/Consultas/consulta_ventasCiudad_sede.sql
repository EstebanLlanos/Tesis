SELECT per.nombre_personal, per.apellido_personal, SUM(dv.total_ventas) AS total_ventas FROM dim_venta dv 
INNER JOIN personal per ON (dv.vendedor = CAST ( per.id_personal AS BIGINT )) 
INNER JOIN ciudad cd ON (CAST ( cd.cod_ciudad AS BIGINT ) = dv.ciudad_venta)
WHERE dv.ciudad_venta = 22 GROUP BY per.nombre_personal, per.apellido_personal ORDER BY SUM(dv.total_ventas) DESC LIMIT 10;

/*SELECT per.nombre_personal, per.apellido_personal, SUM(dv.total_ventas) AS total_ventas
FROM dim_venta dv, ciudad cd INNER JOIN personal per ON (dv.vendedor = CAST ( per.id_personal AS BIGINT ))
INNER JOIN ciudad cd ON (dv.ciudad_venta = CAST ( cd.cod_ciudad AS BIGINT ));

select t1.name, t2.image_id, t3.path
from table1 t1 inner join table2 t2 on t1.person_id = t2.person_id
inner join table3 t3 on t2.image_id=t3.image_id*/