CREATE SEQUENCE seq_plan INCREMENT BY 1 START WITH 1;

create table plan
(
	SK_plan BIGINT NOT NULL DEFAULT nextval('seq_plan'::regclass),
  	nombre_plan VARCHAR(50) NOT NULL,
  	tipo_plan VARCHAR(50) NOT NULL,
  	PRIMARY KEY (SK_plan) 
);

/*==============================================================*/
/* Dimensión: Demografía                                        */
/*==============================================================*/

CREATE SEQUENCE seq_demografia INCREMENT BY 1 START WITH 1;

create table demografia 
(
	SK_demografia BIGINT NOT NULL DEFAULT nextval('seq_demografia'::regclass),
  	estado_civil dominio_estado_civil NOT NULL,
  	estrato integer NOT NULL CHECK (estrato BETWEEN 1 AND 6),
  	genero dominio_genero NOT NULL,
  	PRIMARY KEY (SK_demografia) 
);

/*==============================================================*/
/* Dimensión: Especialista                                      */
/*==============================================================*/

CREATE SEQUENCE seq_especialista INCREMENT BY 1 START WITH 1;

create table especialista
(
	SK_especialista BIGINT NOT NULL DEFAULT nextval('seq_especialista'::regclass),
  	nombre_especialista VARCHAR(100) NOT NULL,
  	sede_especialista VARCHAR(100) NOT NULL,
  	zona_especialista VARCHAR(100) NOT NULL,
  	especialidad_especialista VARCHAR(100) NOT NULL,
  	PRIMARY KEY (SK_especialista) 
);

/*==============================================================*/
/* Dimensión: Sede                                            	*/
/*==============================================================*/

CREATE SEQUENCE seq_sede INCREMENT BY 1 START WITH 1;

create table sede 
(
	SK_sede BIGINT NOT NULL DEFAULT nextval('seq_sede'::regclass),
  	nombre_sede VARCHAR(100) NOT NULL,
  	PRIMARY KEY (SK_sede) 
);


/*==============================================================*/
/* Dimensión: Vendedor											*/
/*==============================================================*/

CREATE SEQUENCE seq_vendedor INCREMENT BY 1 START WITH 1;

create table vendedor 
(
	SK_vendedor BIGINT NOT NULL DEFAULT nextval('seq_vendedor'::regclass),
	nombre_vendedor BIGINT NOT NULL,
	tipo_vendedor VARCHAR(50) NOT NULL,
  	PRIMARY KEY (SK_vendedor) 
);

/*==============================================================*/
/* Dimensión: Tiempo											*/
/*==============================================================*/

CREATE SEQUENCE seq_tiempo INCREMENT BY 1 START WITH 1;

create table tiempo
(
	SK_tiempo BIGINT NOT NULL DEFAULT nextval('seq_tiempo'::regclass),
  	tiempo TIME WITHOUT TIME ZONE NOT NULL,
  	hora TIME WITHOUT TIME ZONE NOT NULL,
	cuarto_de_hora INTEGER NOT NULL,
	minuto INTEGER NOT NULL,
	periodo_del_dia dominio_periodo_dia NOT NULL,
	dia_noche_flag dominio_dia_noche NOT NULL,
  	PRIMARY KEY (SK_tiempo)
);

/*==============================================================*/
/* Dimensión: Fecha                                             */
/*==============================================================*/

CREATE SEQUENCE seq_fecha INCREMENT BY 1 START WITH 1;

create table fecha 
(
	SK_fecha BIGINT NOT NULL DEFAULT nextval('seq_fecha'::regclass),
	fecha DATE NOT NULL,
	anio INTEGER NOT NULL,
	mes INTEGER NOT NULL,
	nombre_mes VARCHAR (20) NOT NULL,
	dia INTEGER NOT NULL,
	dia_del_anio INTEGER NOT NULL,
	nombre_dia VARCHAR (10),
	numero_semana INTEGER NOT NULL,
	fecha_formateada DATE NOT NULL,
	trimestre INTEGER NOT NULL,
	mes_del_anio INTEGER NOT NULL,
	fin_de_semestre dominio_flag,
	dia_festivo dominio_flag NOT NULL,
	periodo dominio_periodo_anio NOT NULL,
	fecha_comienzo_semana dominio_flag NOT NULL,
	fecha_fin_semana dominio_flag NOT NULL,
	fecha_comienzo_mes dominio_flag NOT NULL,
  	PRIMARY KEY (SK_fecha)
);

/*==============================================================*/
/* Dimensión: Localización                                      */
/*==============================================================*/

CREATE SEQUENCE seq_localizacion INCREMENT BY 1 START WITH 1;

create table localizacion 
(
	SK_localizacion BIGINT NOT NULL DEFAULT nextval('seq_localizacion'::regclass),
  	departamento VARCHAR (30) NOT NULL,
  	localizacion VARCHAR (30) NOT NULL,
  	pais VARCHAR (30) NOT NULL,
  	PRIMARY KEY (SK_localizacion) 
);

/*==============================================================*/
/* Dimensión: Examen                                       		*/
/*==============================================================*/

CREATE SEQUENCE seq_examen INCREMENT BY 1 START WITH 1;

create table examen
(
	SK_examen BIGINT NOT NULL DEFAULT nextval('seq_examen'::regclass),
  	nombre_examen VARCHAR (30) NOT NULL,
  	tipo_examen VARCHAR (30) NOT NULL,
  	PRIMARY KEY (SK_examen) 
);

/*==============================================================*/
/* Dimensión: Institución                                       */
/*==============================================================*/

CREATE SEQUENCE seq_institucion INCREMENT BY 1 START WITH 1;

create table institucion
(
	SK_institucion BIGINT NOT NULL DEFAULT nextval('seq_institucion'::regclass),
  	nombre_institucion VARCHAR (30) NOT NULL,
  	sede_institucion VARCHAR (30) NOT NULL,
  	zona_institucion VARCHAR (30) NOT NULL,
  	PRIMARY KEY (SK_institucion) 
);

/*==============================================================*/
/* Dimensión: Tipo Servicio                                     */
/*==============================================================*/

CREATE SEQUENCE seq_tipo_servicio INCREMENT BY 1 START WITH 1;

create table tipo_servicio
(
	SK_tipo_servicio BIGINT NOT NULL DEFAULT nextval('seq_tipo_servicio'::regclass),
  	nombre_tipo_servicio VARCHAR (30) NOT NULL,
  	PRIMARY KEY (SK_tipo_servicio) 
);

/*============================================================================================================================*/
/*=========================================================== HECHOS  ========================================================*/
/*============================================================================================================================*/


/*==============================================================*/
/* Hecho: Afiliacion    			                            */
/*==============================================================*/

CREATE SEQUENCE seq_afiliacion INCREMENT BY 1 START WITH 1;

create table afiliacion
(
	SK_afiliacion BIGINT NOT NULL DEFAULT nextval('seq_afiliacion'::regclass),
	fecha BIGINT references fecha (SK_fecha),
  	plan BIGINT references plan (SK_plan),
  	sede BIGINT references sede (SK_sede),
	localizacion BIGINT references localizacion (SK_localizacion),
	vendedor BIGINT references vendedor (SK_vendedor),
	cantidad INTEGER NOT NULL DEFAULT 0,
	renovacion VARCHAR (30) NOT NULL,
  	PRIMARY KEY (SK_afiliacion)
);

/*==============================================================*/
/* Hecho: Cita_Especialidad    			                        */
/*==============================================================*/

CREATE SEQUENCE seq_cita_especialidad INCREMENT BY 1 START WITH 1;

create table cita_especialidad
(
	SK_cita_especialidad BIGINT NOT NULL DEFAULT nextval('seq_cita_especialidad'::regclass),
	fecha BIGINT references fecha (SK_fecha),
  	especialista BIGINT references especialista (SK_especialista),
  	demografia BIGINT references demografia (SK_demografia),
	localizacion BIGINT references localizacion (SK_localizacion),
	cantidad INTEGER NOT NULL DEFAULT 0,
	ahorro DOUBLE PRECISION NOT NULL DEFAULT 0.0,
  	PRIMARY KEY (SK_cita_especialidad)
);

/*==============================================================*/
/* Hecho: Cita_Otros_Servicios    			                    */
/*==============================================================*/

CREATE SEQUENCE seq_cita_otros_servicios INCREMENT BY 1 START WITH 1;

create table cita_otros_servicios
(
	SK_cita_otros_servicios BIGINT NOT NULL DEFAULT nextval('seq_cita_otros_servicios'::regclass),
	fecha BIGINT references fecha (SK_fecha),
  	institucion BIGINT references institucion (SK_institucion),
  	demografia BIGINT references demografia (SK_demografia),
  	tipo_servicio BIGINT references tipo_servicio (SK_tipo_servicio),
	localizacion BIGINT references localizacion (SK_localizacion),
	cantidad INTEGER NOT NULL DEFAULT 0,
	ahorro DOUBLE PRECISION NOT NULL DEFAULT 0.0,
  	PRIMARY KEY (SK_cita_otros_servicios)
);

/*==============================================================*/
/* Hecho: Cita_Examen    			                            */
/*==============================================================*/

CREATE SEQUENCE seq_cita_examen INCREMENT BY 1 START WITH 1;

create table cita_examen
(
	SK_cita_examen BIGINT NOT NULL DEFAULT nextval('seq_cita_examen'::regclass),
	fecha BIGINT references fecha (SK_fecha),
	localizacion BIGINT references localizacion (SK_localizacion),
	examen BIGINT references examen (SK_examen),
  	demografia BIGINT references demografia (SK_demografia),
  	institucion BIGINT references institucion (SK_institucion),
	cantidad INTEGER NOT NULL DEFAULT 0,
	ahorro DOUBLE PRECISION NOT NULL DEFAULT 0.0,
  	PRIMARY KEY (SK_cita_examen)
);

/*==============================================================*/
/* Hecho: Cita_Queja    			                            */
/*==============================================================*/

CREATE SEQUENCE seq_cita_queja INCREMENT BY 1 START WITH 1;

create table cita_queja
(
	SK_cita_queja BIGINT NOT NULL DEFAULT nextval('seq_cita_queja'::regclass),
	fecha BIGINT references fecha (SK_fecha),
	localizacion BIGINT references localizacion (SK_localizacion),
	tipo_servicio BIGINT references tipo_servicio (SK_tipo_servicio),
	institucion BIGINT references institucion (SK_institucion),
  	especialista BIGINT references especialista (SK_especialista),
	cantidad INTEGER NOT NULL DEFAULT 0,
	total_tipo INTEGER NOT NULL DEFAULT 0,
  	PRIMARY KEY (SK_cita_queja)
);