<?php
//filtra un reporte de inconsistencias de una norma seleccionada
set_time_limit(2592000);
ini_set('max_execution_time', 2592000);
ini_set('memory_limit', '2000M');

error_reporting(E_ALL);
ini_set('display_errors', '0');

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
$parte_from.="  especialista ";

$parte_where="";


$query_a_extraer_resultados_contar="";
/*
$query_a_extraer_resultados_contar.="
	select count(DISTINCT pa.c12_nitusuar) as contador_filas from poblacion_para_analizar_2016 pa inner join gioss_afiliados_eapb_mp gamp on pa.c12_nitusuar=gamp.id_afiliado::numeric where c32_anoper='2016' and c31_mesper='12'       ;
";
*/


$query_comun="";
/*
$query_comun.="
	select DISTINCT ON (pa.c12_nitusuar) c12_nitusuar, gamp.* , pa.c38_regional, pa.c39_regionaleps, pa.c0_sucursal from poblacion_para_analizar_2016 pa inner join gioss_afiliados_eapb_mp gamp on pa.c12_nitusuar=gamp.id_afiliado::numeric where pa.c32_anoper='2016' and pa.c31_mesper='12'  order by pa.c12_nitusuar
";
*/

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

	$pathArchivoCorrecto="destino/".$carpetaPropia."/"."Lineas_Correctas".$fecha_archivo.".csv";
	$archivoEncontradosBD=fopen($pathArchivoCorrecto, "w");
	fclose($archivoEncontradosBD);

	$pathArchivoCaracteresEspeciales="destino/".$carpetaPropia."/"."Lineas_Caracteres_Especiales".$fecha_archivo.".csv";
	$archivoCaracteresBD=fopen($pathArchivoCaracteresEspeciales, "w");
	fclose($archivoCaracteresBD);

	$pathArchivoNulos="destino/".$carpetaPropia."/"."Lineas_Nulos".$fecha_archivo.".csv";
	$archivoNulosBD=fopen($pathArchivoNulos, "w");
	fclose($archivoNulosBD);

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
			//$array_linea_a1[0]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[0]) );
			$id_especialista=trim($array_linea_a1[0]);

		}//fin if

		$nombre_especialista="";
		if(isset($array_linea_a1[1])==true)
		{
			//$array_linea_a1[1]=preg_replace("/[^A-Za-z\s]+/", "", trim($array_linea_a1[1]) );
			$nombre_especialista=trim($array_linea_a1[1]);

		}//fin if

		$sede_especialista="";
		if(isset($array_linea_a1[2])==true)
		{
			//$array_linea_a1[2]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[2]) );
			$sede_especialista=trim($array_linea_a1[2]);
		}//fin if

		$zona_especialista="";
		if(isset($array_linea_a1[3])==true)
		{
			//$array_linea_a1[3]=preg_replace("/[^A-Za-z]+/", "", trim($array_linea_a1[3]) );
			$zona_especialista=trim($array_linea_a1[3]);

		}//fin if

		$especialidad_especialista="";
		if(isset($array_linea_a1[4])==true)
		{
			//$array_linea_a1[4]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[4]) );
			$especialidad_especialista=trim($array_linea_a1[4]);

		}//fin if

		$costo_particular = "";
		if(isset($array_linea_a1[5])==true)
		{
			//$array_linea_a1[5]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[5]) );
			$costo_particular=trim($array_linea_a1[5]);

		}//fin if

		$costo_cliente = "";
		if(isset($array_linea_a1[6])==true)
		{
			//$array_linea_a1[6]=preg_replace("/[^0-9]+/", "", trim($array_linea_a1[6]) );
			$costo_cliente = trim($array_linea_a1[6]);

		}//fin if
		
		$linea_a_escribir="";

		if ($zona_especialista  == "") {
			$zona_especialista = "C";
		}

		if ($costo_particular  == "") {
			$costo_particular = "0";
		}

		if ($costo_cliente == "") {
			$costo_cliente = "0";
		}

		$linea_a_escribir = "$id_especialista,$nombre_especialista,$sede_especialista,$zona_especialista,$especialidad_especialista,$costo_particular,$costo_cliente";

		if ($id_especialista == "" || $nombre_especialista  == "" || $sede_especialista  == "" || $zona_especialista  == "" || $especialidad_especialista  == "" || 
			$costo_particular  == "" || $costo_cliente  == "") {

			$archivoNulosBD=fopen($pathArchivoNulos, "a");
			fwrite($archivoNulosBD, $linea_a_escribir."\n");
			fclose($archivoNulosBD);

		} else if(preg_match('/[^0-9A-Za-z\s-*.\'"\/]+/', $nombre_especialista, $coincidencias, PREG_OFFSET_CAPTURE)) {

			$archivoCaracteresBD=fopen($pathArchivoCaracteresEspeciales, "a");
			fwrite($archivoCaracteresBD, $linea_a_escribir."\n");
			fclose($archivoCaracteresBD);

		} else {

			$archivoEncontradosBD=fopen($pathArchivoCorrecto, "a");
			fwrite($archivoEncontradosBD, $linea_a_escribir."\n");
			fclose($archivoEncontradosBD);
		}

		$cont_linea_actual_archivo++;
	}//fin while
	
	$mensajes.="<a href=\"$pathArchivoEncontrados\" target=\"blank_\">Encontrados en BD.</a><br>";
	$mensajes.="<a href=\"$pathArchivoNoEncontrados\" target=\"blank_\">No Encontrados en BD.</a><br>";

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