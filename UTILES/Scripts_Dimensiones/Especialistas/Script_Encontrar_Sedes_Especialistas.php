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
$port="5433";
//$dbname="giossprepagadacoo";
$dbname="bodega_previser";
//$user="giossuser";
$user="postgres";
$pass="postgres";
$coneccionBD->crearConexionCustom($host,$port,$dbname,$user,$pass);

date_default_timezone_set('America/Bogota');

$nombre_tabla="";
if( isset($_REQUEST['nombre_tabla'])
	&& $_REQUEST['nombre_tabla']!=""
)
{
	$nombre_tabla=$_REQUEST['nombre_tabla'];
}
$columnas_a_imprimir="";
if( isset($_REQUEST['columnas_a_imprimir'])
	&& $_REQUEST['columnas_a_imprimir']!=""
)
{
	$columnas_a_imprimir=$_REQUEST['columnas_a_imprimir'];
}

$parte_from="";
$parte_from.="  dim_sede ";

$parte_where="";


$query_a_extraer_resultados_contar="";

$query_comun="";

$ruta_archivo_leer="origen/archivoLeer.txt";

if(isset($_REQUEST['ruta_leer'])==true
	&& trim($_REQUEST['ruta_leer'])!="")
{
	$ruta_archivo_leer=str_replace("\\", "/", trim($_REQUEST['ruta_leer'])) ;
}//fin if


$html="
<div id='div_pp' align='center'>
<h1>Consulta BD a host $host dbname $dbname</h1><br>
<h2>Query $query_comun</h2>
<form id='formulario' name='formulario'>
<table>

<tr>
<td><label>Iniciar Extraccion</label></td>
<td><select id='iniciar' name='iniciar'>
<option value=''>NO</option>
<option value='SI'>SI</option>
</select></td>
</tr>

<tr>
<td><label>Con WHERE</label></td>
<td><select id='con_where' name='con_where'>
<option value=''>NO</option>
<option value='SI' selected>SI</option>
</select></td>
</tr>

<tr>
<td><label>Nombre archivo a leer o Ruta Completa</label></td>
<td>
<input type='text' id='ruta_leer' name='ruta_leer' value='$ruta_archivo_leer' />
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
if( isset($_REQUEST['iniciar'])
	&& $_REQUEST['iniciar']!=""
	&& $_REQUEST['iniciar']=="SI"
	&& file_exists($ruta_archivo_leer)
)
{

	date_default_timezone_set('America/Bogota');
	$fecha_archivo=date('dmYHis');

	$carpetaPropia="resCoincidentes".$fecha_archivo;

	mkdir("destino/".$carpetaPropia,777,true);

	$pathArchivoEncontrados="destino/".$carpetaPropia."/"."Sedes_Encontradas".$fecha_archivo.".csv";
	$archivoEncontradosBD=fopen($pathArchivoEncontrados, "w");
	fclose($archivoEncontradosBD);

	$pathArchivoNoEncontrados="destino/".$carpetaPropia."/"."Sedes_No_Encontradas".$fecha_archivo.".csv";
	$archivoNoEncontradosBD=fopen($pathArchivoEncontrados, "w");
	fclose($archivoNoEncontradosBD);

	$cont_fila_actual=0;

	$cont_linea_actual_archivo=0;

	$handle1 = fopen($ruta_archivo_leer, "r");

	while(!feof($handle1))
	{
		$registro_encontrado = false;

		$line1 = trim(fgets($handle1) );

		$array_linea_a1=explode(",", $line1);

		$id_especialista="";
		if(isset($array_linea_a1[0])==true)
		{
			$array_linea_a1[0]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[0]) );
			$id_especialista=trim($array_linea_a1[0]);

		}//fin if

		$nombre_especialista="";
		if(isset($array_linea_a1[1])==true)
		{
			$array_linea_a1[1]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[1]) );
			$nombre_especialista=trim($array_linea_a1[1]);

		}//fin if
		
		$sede_especialista="";
		if(isset($array_linea_a1[2])==true)
		{
			$array_linea_a1[2]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[2]) );
			$sede_especialista=trim($array_linea_a1[2]);

		}//fin if

		$zona_especialista="";
		if(isset($array_linea_a1[3])==true)
		{
			$array_linea_a1[3]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[3]) );
			$zona_especialista=trim($array_linea_a1[3]);

		}//fin if

		$especialidad_especialista="";
		if(isset($array_linea_a1[4])==true)
		{
			$array_linea_a1[4]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[4]) );
			$especialidad_especialista=trim($array_linea_a1[4]);

		}//fin if

		$costo_particular="";
		if(isset($array_linea_a1[5])==true)
		{
			$array_linea_a1[5]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[5]) );
			$costo_particular=trim($array_linea_a1[5]);

		}//fin if

		$costo_cliente="";
		if(isset($array_linea_a1[6])==true)
		{
			$array_linea_a1[6]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[6]) );
			$costo_cliente=trim($array_linea_a1[6]);

		}//fin if

		$parte_where="";
		if( isset($_REQUEST['con_where'])
			&& $_REQUEST['con_where']!=""
			&& $_REQUEST['con_where']=="SI"
		)
		{
			//$parte_where.=" primer_nombre ilike '%A' AND sexo ilike 'M' and primer_apellido NOT ilike '%hijo%de%' ";
			$parte_where.=" id_sede='$sede_especialista'";
		}


		$query_a_extraer_resultados_contar="SELECT count(*) as contador_filas FROM $parte_from ";
		if(trim($parte_where)!="")
		{
			$query_a_extraer_resultados_contar.=" WHERE $parte_where ; ";
		}

		$query_comun=" SELECT * FROM $parte_from   ";
		if(trim($parte_where)!="")
		{
			$query_comun.=" WHERE $parte_where  ";//aca no lleva punto y coma debido a que despues va el limit y offset en el ciclo
		}//fin if		

		$numero_filas=0;

		$error_bd_seq="";
		$resultados_contador=$coneccionBD->consultar_no_warning_get_error_no_crea_cierra($query_a_extraer_resultados_contar, $error_bd_seq);		

		if($error_bd_seq!="")
		{
		    echo "Error al contar los resultados.<br>";
		}//fin if
		else
		{
			$numero_filas=$resultados_contador[0]['contador_filas'];
		}//fin else 

		$cantidad_registros_bloque=50000;

		$ultima_posicion=0;

		ob_flush();
	  	flush();

		if($numero_filas>0)
		{			
			while($ultima_posicion<$numero_filas)
			{

				$query_a_extraer_resultados="";

				$query_a_extraer_resultados.=$query_comun;

				$query_a_extraer_resultados.=" LIMIT $cantidad_registros_bloque OFFSET $ultima_posicion; ";

				$error_bd_seq="";
				$resultados=$coneccionBD->consultar_no_warning_get_error_no_crea_cierra($query_a_extraer_resultados, $error_bd_seq);
				if($error_bd_seq!="")
				{
				    echo "Error al consultar los resultados.<br>";
				}//fin if
				else if(is_array($resultados) && count($resultados)>0 )
				{
					foreach ($resultados as $key => $fila_actual) 
					{
						$linea_a_escribir="";

						if( ($sede_especialista == trim($fila_actual['id_sede'])) ){

							$registro_encontrado = true;

							//echo "especialista archivo: ".$especialista."   especialista BD ".trim($fila_actual['id_especialista']);

							//ob_flush();
							//flush();

							$linea_a_escribir = "$id_especialista,$nombre_tabla,$sede_especialista,$zona_especialista,$especialidad_especialista,$costo_particular,$costo_cliente";

							$archivoEncontradosBD=fopen($pathArchivoEncontrados, "a");
							fwrite($archivoEncontradosBD, $linea_a_escribir."\n");
							fclose($archivoEncontradosBD);
						}
					}//fin foreach resultado

				}//fin else
				else
				{
					echo "Problema query comun<br>";
				}//fin else

				$ultima_posicion=$ultima_posicion+$cantidad_registros_bloque;

				//echo "ultima_posicion $ultima_posicion , cantidad_registros_bloque $cantidad_registros_bloque<br>";
				ob_flush();
	  			flush();

			}//fin while

		}//fin if numero de filas contadas en bd es mayor de cero

		if ($registro_encontrado == false) {

			$linea_a_escribir="";

			$linea_a_escribir = "$id_especialista,$nombre_tabla,$sede_especialista,$zona_especialista,$especialidad_especialista,$costo_particular,$costo_cliente";

			$archivoNoEncontradosBD=fopen($pathArchivoNoEncontrados, "a");
			fwrite($archivoNoEncontradosBD, $linea_a_escribir."\n");
			fclose($archivoNoEncontradosBD);

		}

		$cont_linea_actual_archivo++;
	}//fin while
	
	$mensajes.="<a href=\"$pathArchivoEncontrados\" target=\"blank_\">Sedes Encontradas en BD.</a><br>";
	$mensajes.="<a href=\"$pathArchivoNoEncontrados\" target=\"blank_\">Sedes No Encontradas en BD.</a><br>";

}//fin if
else
{

	if(isset($_REQUEST['iniciar'])
	&& ($_REQUEST['iniciar']=="" || $_REQUEST['iniciar']=="NO")
	)
	{
		$mensajes.="Seleccione SI en iniciar para ejecutar la extraccion de datos.<br>";
	}//fin if

	if(file_exists($ruta_archivo_leer)==false)
	{
		$mensajes.="La ruta del archivo no existe.<br>";
	}//fin if
}//fin else


	echo "<script>document.getElementById('mensaje').innerHTML='".$mensajes."'</script>";
	ob_flush();
  	flush();

$coneccionBD->cerrar_conexion();

?>