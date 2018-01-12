/*==============================================================*/
/*=======================BASE DE DATOS==========================*/
/*==============================================================*/

DROP TABLE IF EXISTS ciudad;
DROP TABLE IF EXISTS plan;
DROP TABLE IF EXISTS personal;
DROP TABLE IF EXISTS sede;

/*==============================================================*/
/* Dimensión: Ciudad                                            */
/*==============================================================*/

create table ciudad
(
  cod_ciudad VARCHAR(5) NOT NULL,
  nombre_ciudad VARCHAR(50) NOT NULL,
  region_ciudad VARCHAR(50) NOT NULL,
  PRIMARY KEY (cod_ciudad, nombre_ciudad)
);

/*==============================================================*/
/* Dimensión: Plan                                              */
/*==============================================================*/

create table plan
(
  id_plan VARCHAR(5) NOT NULL,
  nombre_plan VARCHAR(50) NOT NULL,
  tipo_plan VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_plan) 
);

/*==============================================================*/
/* Dimensión: Personal                                          */
/*==============================================================*/

create table personal 
(
  id_personal VARCHAR(5) NOT NULL,
  nombre_personal VARCHAR(50) NOT NULL,
  apellido_personal VARCHAR(50) NOT NULL,
  rol_personal VARCHAR(2) NOT NULL,
  PRIMARY KEY (id_personal) 
);

/*==============================================================*/
/* Dimensión: Sede                                            	*/
/*==============================================================*/

create table sede 
(
  id_sede VARCHAR(2) NOT NULL DEFAULT 0,
  nombre_sede VARCHAR(50) NOT NULL,
  tipo_sede VARCHAR(5) NOT NULL,
  PRIMARY KEY (id_sede) 
);

/*==============================================================*/
/* Dimensión: Ventas                                            */
/*==============================================================*/

CREATE SEQUENCE seq_venta INCREMENT BY 1 START WITH 1;

create table ventas
(
  id_venta BIGINT NOT NULL DEFAULT nextval('seq_venta'::regclass),
  anio_venta VARCHAR(50) NOT NULL,
  mes_venta VARCHAR(50) NOT NULL,
  plan_venta VARCHAR(50) NOT NULL,
  sede_venta VARCHAR(50) NOT NULL,
  ciudad_venta VARCHAR(50) NOT NULL,
  vendedor VARCHAR(50) NOT NULL,
  total_ventas INTEGER NOT NULL,
  PRIMARY KEY (id_venta),
  FOREIGN KEY (plan_venta) REFERENCES plan (id_plan),
  FOREIGN KEY (sede_venta) REFERENCES sede (id_sede),
  FOREIGN KEY (ciudad_venta) REFERENCES ciudad (cod_ciudad),
  FOREIGN KEY (vendedor) REFERENCES personal (id_personal)
);