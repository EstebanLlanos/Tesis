<?php
//filtra un reporte de inconsistencias de una norma seleccionada
set_time_limit(2592000);
ini_set('max_execution_time', 2592000);
ini_set('memory_limit', '2000M');

error_reporting(E_ALL);
ini_set('display_errors', '0');
include_once ('../conexionbd/clase_coneccion_bd.php');
$coneccionBD = new conexion();
$host="localhost";
$port="5432";
//$dbname="giossprepagadacoo";
$dbname="bodega_previser";
//$user="giossuser";
$user="postgres";
$pass="postgres";
$coneccionBD->crearConexionCustom($host,$port,$dbname,$user,$pass);

date_default_timezone_set('America/Bogota');

$html="
<div id='div_pp' align='center'>
<h1>Consulta BD a host $host dbname $dbname</h1><br>
<form id='formulario' name='formulario'>
<table>

<tr>
	<td>
		<label>Cantidad de Registros</label>
	</td>
	<td>
		<input type='text' id='cantidad_registros' name='cantidad_registros' value='500'></input>
	</td>
</tr>

<tr>
<td colspan='2' align='center' ><input type='submit' id='enviar' name='enviar' value='INICIAR'/></td>
</tr>

<tr>
<td colspan='2' align='center' ><div id='mensaje'></div></td>
</tr>

</table>
</form>
</div>
";

echo $html;

$mensajes="";
if( isset($_REQUEST['cantidad_registros'])
	&& $_REQUEST['cantidad_registros']!=""
	&& $_REQUEST['cantidad_registros']>0)
{

	date_default_timezone_set('America/Bogota');
	$fecha_archivo=date('dmYHis');

	$carpetaPropia="archivosGenerados".$fecha_archivo;

	mkdir("destino/".$carpetaPropia,777,true);

	$pathArchivoSalida="destino/".$carpetaPropia."/"."Solicitudes_Servicios".$fecha_archivo.".csv";
	$archivoEncontradosBD=fopen($pathArchivoSalida, "w");
	fclose($archivoEncontradosBD);

	$cont_registros_totales = trim($_REQUEST['cantidad_registros']);

	$cont_linea_actual_archivo=0;

	while($cont_linea_actual_archivo < $cont_registros_totales)
	{

		// CONTEO DE ELEMENTOS EN DIMENSION FECHA

		$query_contar_fechas="";
		$query_contar_fechas.="SELECT COUNT(*) as numero_fechas FROM dim_fecha;";
		$resultado_query_contar_fechas=$coneccionBD->consultar2_no_crea_cierra($query_contar_fechas);

		$numero_fechas=0;
		if(isset($resultado_query_contar_fechas[0]['numero_fechas'])==true)
		{
			$numero_fechas=intval($resultado_query_contar_fechas[0]['numero_fechas']);
		}//fin if 

		// CONTEO DE ELEMENTOS EN DIMENSION CIUDAD

		$query_contar_ciudades="";
		$query_contar_ciudades.="SELECT COUNT(*) as numero_ciudades FROM dim_ciudad;";
		$resultado_query_contar_ciudades=$coneccionBD->consultar2_no_crea_cierra($query_contar_ciudades);

		$numero_ciudades=0;
		if(isset($resultado_query_contar_ciudades[0]['numero_ciudades'])==true)
		{
			$numero_ciudades=intval($resultado_query_contar_ciudades[0]['numero_ciudades']);
		}//fin if 

		// CONTEO DE ELEMENTOS EN DIMENSION ESPECIALIDAD

		$query_contar_especialidades="";
		$query_contar_especialidades.="SELECT COUNT(*) as numero_especialidades FROM dim_especialidad;";
		$resultado_query_contar_especialidades=$coneccionBD->consultar2_no_crea_cierra($query_contar_especialidades);

		$numero_especialidades=0;
		if(isset($resultado_query_contar_especialidades[0]['numero_especialidades'])==true)
		{
			$numero_especialidades=intval($resultado_query_contar_especialidades[0]['numero_especialidades']);
		}//fin if 

		// SELECCIÓN DE ELEMENTO DIMENSION FECHA

		$flag_fecha = false;

		while ($flag_fecha == false) {
			$posicion_fecha_aleatorio_bd=intval(rand(1, $numero_fechas));
			$query_info_fecha_aleatorio="";
			$query_info_fecha_aleatorio.="SELECT id_dim_fecha as id_dim_fecha FROM dim_fecha LIMIT 1 OFFSET ".$posicion_fecha_aleatorio_bd."; ";
			$resultado_query_info_fecha_aleatoria=$coneccionBD->consultar2_no_crea_cierra($query_info_fecha_aleatorio);

			$fecha_seleccionada=0;
			if(isset($resultado_query_info_fecha_aleatoria[0]['id_dim_fecha'])==true)
			{
				$fecha_seleccionada=intval($resultado_query_info_fecha_aleatoria[0]['id_dim_fecha']);
			}//fin if

			if ($fecha_seleccionada > 20080101 && $fecha_seleccionada < 20181001) {
			 	$flag_fecha = true;
			}
		}

		// SELECCIÓN DE ELEMENTO DIMENSION CIUDAD

		$posicion_ciudad_aleatoria_bd=intval(rand(1, $numero_ciudades));
		$query_info_ciudad_aleatoria="";
		$query_info_ciudad_aleatoria.="SELECT id_ciudad as id_ciudad FROM dim_ciudad LIMIT 1 OFFSET ".$posicion_ciudad_aleatoria_bd."; ";
		$resultado_query_info_ciudad_aleatoria=$coneccionBD->consultar2_no_crea_cierra($query_info_ciudad_aleatoria);

		$ciudad_seleccionada=0;
		if(isset($resultado_query_info_ciudad_aleatoria[0]['id_ciudad'])==true)
		{
			$ciudad_seleccionada=intval($resultado_query_info_ciudad_aleatoria[0]['id_ciudad']);
		}//fin if

		// SELECCIÓN DE ELEMENTO DIMENSION TIPO_SERVICIO

		$tipo_servicio_seleccionado=intval(rand(1, 4));

		// SELECCIÓN DE ELEMENTO DIMENSION ESPECIALIDAD

		$posicion_especialidad_aleatoria_bd=intval(rand(1, $numero_especialidades));
		$query_info_especialidad_aleatoria="";
		$query_info_especialidad_aleatoria.="SELECT id_especialidad as id_especialidad FROM dim_especialidad LIMIT 1 OFFSET ".$posicion_especialidad_aleatoria_bd."; ";
		$resultado_query_info_especialidad_aleatoria=$coneccionBD->consultar2_no_crea_cierra($query_info_especialidad_aleatoria);

		$especialidad_seleccionada=0;
		if(isset($resultado_query_info_especialidad_aleatoria[0]['id_especialidad'])==true)
		{
			$especialidad_seleccionada=intval($resultado_query_info_especialidad_aleatoria[0]['id_especialidad']);
		}//fin if

		$linea_a_escribir="$fecha_seleccionada,$especialidad_seleccionada,$ciudad_seleccionada,$tipo_servicio_seleccionado";

		$archivoEncontradosBD=fopen($pathArchivoSalida, "a");
		fwrite($archivoEncontradosBD, $linea_a_escribir."\n");
		fclose($archivoEncontradosBD);

		ob_flush();
	  	flush();

		$cont_linea_actual_archivo++;
	}//fin while
	
	$mensajes.="<a href=\"$pathArchivoSalida\" target=\"blank_\">Archivo de Solicitudes Generado.</a><br>";

}//fin if

	echo "<script>document.getElementById('mensaje').innerHTML='".$mensajes."'</script>";
	ob_flush();
  	flush();

$coneccionBD->cerrar_conexion();

?>