/*==============================================================*/
/*========================BODEGA DE DATOS=======================*/
/*==============================================================*/

DROP TABLE IF EXISTS datamart_afiiliacion;
DROP TABLE IF EXISTS datamart_cita_especialidad;
DROP TABLE IF EXISTS datamart_cita_examen;
DROP TABLE IF EXISTS datamart_cita_otros_servicios;

DROP TABLE IF EXISTS dim_ciudad;
DROP TABLE IF EXISTS dim_departamento;
DROP TABLE IF EXISTS dim_plan;
DROP TABLE IF EXISTS dim_vendedor;
DROP TABLE IF EXISTS dim_examen;
DROP TABLE IF EXISTS dim_institucion;
DROP TABLE IF EXISTS dim_demografia;
DROP TABLE IF EXISTS dim_especialista;
DROP TABLE IF EXISTS dim_especialidad;
DROP TABLE IF EXISTS dim_sede;

DROP SEQUENCE IF EXISTS seq_ciudad;
DROP SEQUENCE IF EXISTS seq_datamart_afiliacion;
DROP SEQUENCE IF EXISTS seq_datamart_cita_especialidad;
DROP SEQUENCE IF EXISTS seq_datamart_cita_examen;
DROP SEQUENCE IF EXISTS seq_datamart_cita_otros_servicios;

/*==============================================================*/
/* Dimensión: Ciudad                                            */
/*==============================================================*/

CREATE SEQUENCE seq_ciudad INCREMENT BY 1 START WITH 1;

create table dim_ciudad
(
  id_ciudad BIGINT NOT NULL DEFAULT nextval('seq_ciudad'::regclass),
  cod_ciudad VARCHAR(5) NOT NULL,
  nombre_ciudad VARCHAR(50) NOT NULL,
  departamento_ciudad VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_ciudad),
  UNIQUE (cod_ciudad, departamento_ciudad)
);

/*==============================================================*/
/* Dimensión: Plan                                              */
/*==============================================================*/

create table dim_plan
(
  id_plan BIGINT NOT NULL,
  nombre_plan VARCHAR(50) NOT NULL,
  tipo_plan VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_plan) 
);

/*==============================================================*/
/* Dimensión: Vendedor                                          */
/*==============================================================*/

create table dim_vendedor 
(
  id_vendedor BIGINT NOT NULL,
  nombre_vendedor VARCHAR(50) NOT NULL,
  apellido_vendedor VARCHAR(50) NOT NULL,
  tipo_vendedor VARCHAR(2) NOT NULL,
  PRIMARY KEY (id_vendedor) 
);

/*==============================================================*/
/* Dimensión: Departamento                                      */
/*==============================================================*/

create table dim_departamento 
(
  id_departamento BIGINT NOT NULL,
  nombre_departamento VARCHAR(50) NOT NULL,
  PRIMARY KEY (id_departamento) 
);

/*==============================================================*/
/* Dimensión: Sede                                            	*/
/*==============================================================*/

create table dim_sede 
(
  id_sede BIGINT NOT NULL DEFAULT 0,
  nombre_sede VARCHAR(50) NOT NULL,
  tipo_sede VARCHAR(5) NOT NULL,
  PRIMARY KEY (id_sede) 
);

/*==============================================================*/
/* Dimensión: Demografía                                        */
/*==============================================================*/


create table dim_demografia 
(
  id_demografia INTEGER NOT NULL,
  genero_demografia VARCHAR(10) NOT NULL,
  estrato_demografia INTEGER NOT NULL,
  edad_demografia INTEGER NOT NULL,
  ingresos_demografia INTEGER NOT NULL,
  PRIMARY KEY (id_demografia)
);

/*==============================================================*/
/* Dimensión: Especialidad                                      */
/*==============================================================*/

CREATE TABLE dim_especialidad 
(
  id_especialidad INTEGER NOT NULL,
  descripcion_especialidad VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_especialidad)
);

/*==============================================================*/
/* Dimensión: Especialista                                      */
/*==============================================================*/

CREATE TABLE dim_especialista 
(
  id_especialista INTEGER NOT NULL,
  nombre_especialista VARCHAR(100) NOT NULL,
  sede_especialista INTEGER REFERENCES dim_sede (id_sede),
  zona_especialista VARCHAR(10) NOT NULL,
  especialidad_especialista INTEGER REFERENCES dim_especialidad (id_especialidad),
  costo_a_particular BIGINT NOT NULL,
  costo_a_cliente BIGINT NOT NULL,
  PRIMARY KEY (id_especialista)
);

/*==============================================================*/
/* Dimensión: Institución                                      */
/*==============================================================*/

CREATE TABLE dim_institucion 
(
  id_institucion INTEGER NOT NULL,
  nombre_institucion VARCHAR(100) NOT NULL,
  sede_institucion INTEGER REFERENCES dim_sede (id_sede),
  zona_institucion VARCHAR(10) NOT NULL,
  PRIMARY KEY (id_institucion)
);

/*==============================================================*/
/* Dimensión: Examen                                            */
/*==============================================================*/

CREATE TABLE dim_examen 
(
  id_examen INTEGER NOT NULL,
  nombre_examen VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_examen)
);

/*==============================================================*/
/* Dimensión: Tipo Servicio                                     */
/*==============================================================*/

CREATE TABLE dim_tipo_servicio
(
  id_tipo_servicio INTEGER NOT NULL,
  nombre_tipo_servicio VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_tipo_servicio)
);

/*==============================================================*/
/* Dimensión: Afiliaciones                                      */
/*==============================================================*/

CREATE SEQUENCE seq_datamart_afiliacion INCREMENT BY 1 START WITH 1;

create table datamart_afiiliacion
(
  id_afiliacion BIGINT NOT NULL DEFAULT nextval('seq_datamart_afiliacion'::regclass),
  fecha_afiliacion BIGINT NOT NULL,
  plan_afiliacion BIGINT NOT NULL,
  sede_afiliacion BIGINT NOT NULL,
  ciudad_afiliacion BIGINT NOT NULL,
  vendedor_afiliacion BIGINT NOT NULL,
  demografia_afiliacion BIGINT NOT NULL,
  cantidad_afiliaciones INTEGER NOT NULL,
  PRIMARY KEY (id_afiliacion),
  FOREIGN KEY (plan_afiliacion) REFERENCES dim_plan (id_plan),
  FOREIGN KEY (sede_afiliacion) REFERENCES dim_sede (id_sede),
  FOREIGN KEY (ciudad_afiliacion) REFERENCES dim_ciudad (id_ciudad),
  FOREIGN KEY (vendedor_afiliacion) REFERENCES dim_vendedor (id_vendedor)
);

/*==============================================================*/
/* Dimensión: Citas Especialidad                                */
/*==============================================================*/

CREATE SEQUENCE seq_datamart_cita_especialidad INCREMENT BY 1 START WITH 1;

create table datamart_cita_especialidad
(
  id_cita_especialidad BIGINT NOT NULL DEFAULT nextval('seq_datamart_cita_especialidad'::regclass),
  ciudad_cita BIGINT REFERENCES dim_ciudad (id_ciudad),
  fecha_actividad BIGINT references dim_fecha (id_dim_fecha),
  demografia_cita BIGINT references dim_demografia (id_demografia),
  especialista_cita BIGINT references dim_especialista (id_especialista),
  cantidad INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY (id_cita_especialidad)
);

/*==============================================================*/
/* Dimensión: Citas Exámenes                                    */
/*==============================================================*/

CREATE SEQUENCE seq_datamart_cita_examen INCREMENT BY 1 START WITH 1;

create table datamart_cita_examen
(
  id_cita_examen BIGINT NOT NULL DEFAULT nextval('seq_datamart_cita_examen'::regclass),
  fecha_actividad BIGINT references dim_fecha (id_dim_fecha),
  ciudad_cita BIGINT REFERENCES dim_ciudad (id_ciudad),
  examen_cita BIGINT references dim_examen (id_examen),
  demografia_cita BIGINT references dim_demografia (id_demografia),
  institucion_cita BIGINT references dim_institucion (id_institucion),
  cantidad INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY (id_cita_examen)
);

/*==============================================================*/
/* Dimensión: Citas Otros Servicios                             */
/*==============================================================*/

CREATE SEQUENCE seq_datamart_cita_otros_servicios INCREMENT BY 1 START WITH 1;

create table datamart_cita_otros_servicios
(
  id_cita_otros_servicios BIGINT NOT NULL DEFAULT nextval('seq_datamart_cita_otros_servicios'::regclass),
  fecha_actividad BIGINT references dim_fecha (id_dim_fecha),
  institucion_cita BIGINT references dim_institucion (id_institucion),
  demografia_cita BIGINT references dim_demografia (id_demografia),
  especialidad_cita BIGINT references dim_especialidad (id_especialidad),
  ciudad_cita BIGINT REFERENCES dim_ciudad (id_ciudad),
  cantidad INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY (id_cita_otros_servicios)
);

/*==============================================================*/
/* Dimensión: Solicitud Servicios                               */
/*==============================================================*/

CREATE SEQUENCE seq_datamart_solicitud_servicios INCREMENT BY 1 START WITH 1;

create table datamart_solicitud_servicios
(
  id_solicitud_servicios BIGINT NOT NULL DEFAULT nextval('seq_datamart_solicitud_servicios'::regclass),
  fecha_actividad BIGINT REFERENCES dim_fecha (id_dim_fecha),
  especialidad_solicitud BIGINT REFERENCES dim_especialidad (id_especialidad),
  ciudad_solicitud BIGINT REFERENCES dim_ciudad (id_ciudad),
  tipo_servicio_solcitud BIGINT REFERENCES dim_tipo_servicio (id_tipo_servicio),
  PRIMARY KEY (id_solicitud_servicios)
);