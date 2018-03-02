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

CREATE SEQUENCE seq_ciudad INCREMENT BY 1 START WITH 1;

create table ciudad
(
  id_ciudad BIGINT NOT NULL DEFAULT nextval('seq_ciudad'::regclass),
  cod_ciudad VARCHAR(5) NOT NULL,
  nombre_ciudad VARCHAR(50) NOT NULL,
  departamento_ciudad VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_ciudad),
  UNIQUE (cod_ciudad, nombre_ciudad)
);

/*==============================================================*/
/* Dimensión: Plan                                              */
/*==============================================================*/

create table plan
(
  id_plan BIGINT NOT NULL,
  nombre_plan VARCHAR(50) NOT NULL,
  tipo_plan VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_plan) 
);

/*==============================================================*/
/* Dimensión: Personal                                          */
/*==============================================================*/

create table personal 
(
  id_personal BIGINT NOT NULL,
  nombre_personal VARCHAR(50) NOT NULL,
  apellido_personal VARCHAR(50) NOT NULL,
  tipo_personal VARCHAR(2) NOT NULL,
  PRIMARY KEY (id_personal) 
);

/*==============================================================*/
/* Dimensión: Departamento                                      */
/*==============================================================*/

create table departamento 
(
  id_departamento BIGINT NOT NULL,
  nombre_departamento VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_departamento) 
);

/*==============================================================*/
/* Dimensión: Sede                                            	*/
/*==============================================================*/

create table sede 
(
  id_sede BIGINT NOT NULL DEFAULT 0,
  nombre_sede VARCHAR(50) NOT NULL,
  tipo_sede VARCHAR(5) NOT NULL,
  PRIMARY KEY (id_sede) 
);

/*==============================================================*/
/* Dimensión: Demografía                                        */
/*==============================================================*/


create table demografia (

  id_demografia INTEGER NOT NULL,
  estrato_demografia INTEGER NOT NULL,
  genero_demografia VARCHAR(10) NOT NULL,
  PRIMARY KEY (id_demografia)

);

/*==============================================================*/
/* Dimensión: Especialidad                                      */
/*==============================================================*/

CREATE TABLE especialidad (

  id_especialidad INTEGER NOT NULL,
  descripcion_especialidad VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_especialidad)

);

/*==============================================================*/
/* Dimensión: Especialista                                      */
/*==============================================================*/

CREATE TABLE especialista (

  id_especialista INTEGER NOT NULL,
  nombre_especialista VARCHAR(100) NOT NULL,
  sede_especialista INTEGER REFERENCES sede (id_sede),
  zona_especialista VARCHAR(10) NOT NULL,
  especialidad_especialista INTEGER REFERENCES especialidad (id_especialidad),
  costo_a_particular BIGINT NOT NULL,
  costo_a_cliente BIGINT NOT NULL,
  PRIMARY KEY (id_especialista)

);

/*==============================================================*/
/* Dimensión: Ventas                                            */
/*==============================================================*/

CREATE SEQUENCE seq_dim_venta INCREMENT BY 1 START WITH 1;

create table dim_venta
(
  id_venta BIGINT NOT NULL DEFAULT nextval('seq_dim_venta'::regclass),
  fecha_venta BIGINT NOT NULL,
  plan_venta BIGINT NOT NULL,
  sede_venta BIGINT NOT NULL,
  ciudad_venta BIGINT NOT NULL,
  vendedor BIGINT NOT NULL,
  total_ventas INTEGER NOT NULL,
  PRIMARY KEY (id_venta),
  FOREIGN KEY (plan_venta) REFERENCES plan (id_plan),
  FOREIGN KEY (sede_venta) REFERENCES sede (id_sede),
  FOREIGN KEY (ciudad_venta) REFERENCES ciudad (cod_ciudad),
  FOREIGN KEY (vendedor) REFERENCES personal (id_personal)
);

/*==============================================================*/
/* Dimensión: Ventas_2                                          */
/*==============================================================*/

CREATE SEQUENCE seq_dim_venta_2 INCREMENT BY 1 START WITH 1;

create table dim_venta_2
(
  id_venta BIGINT NOT NULL DEFAULT nextval('seq_dim_venta'::regclass),
  fecha_venta BIGINT NOT NULL,
  plan_venta BIGINT NOT NULL,
  sede_venta BIGINT NOT NULL,
  ciudad_venta BIGINT NOT NULL,
  vendedor BIGINT NOT NULL,
  total_ventas INTEGER NOT NULL,
  PRIMARY KEY (id_venta),
  FOREIGN KEY (plan_venta) REFERENCES plan (id_plan),
  FOREIGN KEY (sede_venta) REFERENCES sede (id_sede),
  FOREIGN KEY (ciudad_venta) REFERENCES ciudad (cod_ciudad),
  FOREIGN KEY (vendedor) REFERENCES personal (id_personal)
);

/*==============================================================*/
/* Dimensión: Citas Especialidad                                */
/*==============================================================*/

CREATE SEQUENCE seq_dim_cita_especialidad INCREMENT BY 1 START WITH 1;

create table dim_cita_especialidad
(
  id_cita_especialidad BIGINT NOT NULL DEFAULT nextval('seq_cita_especialidad'::regclass),
  ciudad_cita BIGINT REFERENCES ciudad (id_ciudad),
  fecha_actividad BIGINT references dim_fecha (id_dim_fecha),
  demografia_cita BIGINT references demografia (id_demografia),
    especialista_cita BIGINT references especialista (id_especialista),
  cantidad INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY (id_cita_especialidad)
);