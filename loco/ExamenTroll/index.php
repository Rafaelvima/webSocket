<?php
require 'vendor/autoload.php';

use GuzzleHttp\Client;
use GuzzleHttp\Exception\ClientException;

$client = new Client();
$usuario = new \stdClass;
$caja = new \stdClass;
$cosica = new \stdClass;
$uric = 'http://localhost:8083/Examen2EVA/rest/restcajas';
$uris = 'http://localhost:8083/Examen2EVA/rest/restusuarios';
    if (isset($_REQUEST["op2"])) {
    if (isset($_REQUEST['op2'])){ $op2 = $_REQUEST['op2'];} else { $op2=null;}
    if (isset($_REQUEST['pass'])){ $nombreP = $_REQUEST['pass'];} else { $nombreP=null;}
    if (isset($_REQUEST['nombreU'])){ $nombreU = $_REQUEST['nombreU'];} else { $nombreU=null;}
    if (isset($_REQUEST['nombreC'])){$cajaN = $_REQUEST['nombreC'];} else {$cajaN=null;}
    if (isset($_REQUEST['cosaN'])){$cosaN = $_REQUEST['cosaN'];} else {$cosa=null;}
    if (isset($_REQUEST['cosaC'])){$cosaC = $_REQUEST['cosaC'];} else {$cantidad=null;}
   
    $usuario->name = $nombreU;
    $usuario->password = $nombreP;
        switch ($op2) {
            case"all":
            $response = $client->get($uric, ['query' => ['usuario' => json_encode($usuario)]]);
            $decode = json_decode($response->getBody());
            if ($decode != null) {
                echo "<h1>USUARIO: ".$usuario->name."</h1>";
                foreach ($decode as $deco) {
                    echo "<b>" . $deco->nombre . "</b></br>";
                    foreach ($deco->cosas as $cosa) {
                        echo $cosa->nombre . "-" . $cosa->cantidad."</br>" ;
                    }
                }

                echo "Ole que buena";
            } else {
                echo "error de algo";
            }
            break;
            case "add_caja":

                $caja->nombre = $cajaN;
                $usuario->name = $nombreU;
                $usuario->password = $nombreP;

                $request = $client->put($uric, ['query' => ['caja' => json_encode($caja), 'usuario' => json_encode($usuario), 'op2' => $op2]]);
                $deco = json_decode($request->getBody());

                if ($deco ==0) {
                    echo "Caja existe y no se ha añadido";
                }  if ($deco ==1) {
                    echo "caja existente añadida";
                }
                 if ($deco ==2) {
                    echo" caja creada y añdida";
                 }
                 if ($deco ==-1) {
                    echo" caja creada y no añadida a naide";
                 }
                break;
            case "add_cosa":
                
          $caja->nombre = $cajaN;
          $cosica->nombre = $cosaN;
          $cosica->cantidad = $cosaC;

          $request = $client->put( $uric, ['query' => ['caja' => json_encode($caja), 'cosa' => json_encode($cosica), 'op2' => $op2]]);
          $deco = json_decode($request->getBody());

          if ($deco >0)
          {
          echo "Cosa introducida perfeckli";
          }
          else
          {
          echo "error al introducir la cosa";
          }
          break;
            case "add_cantidad":
          
            $caja->nombre = $cajaN;
          $cosica->nombre = $cosaN;
          $cosica->cantidad = $cosaC;

          $request = $client->put($uric, ['query' => ['caja' => json_encode($caja), 'cosa' => json_encode($cosica), 'op2' => $op2]]);
          $deco = json_decode($request->getBody());

          if ($deco >0)
          {
          echo "has metido mas cosas muy bien";
          }
          else
          {
          echo "error al querer meterle mas cosas";
          }
          break;
          
        }
    }

?>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

        <title>Operaciones Cliente API REST</title>
    </head>
    <body>
        <div class="container"><br>
            <h1>Operaciones Cliente API REST</h1><br><hr><br>
            <div class="row">           
                <form action="index.php" method="post" id="formu" class="col-12">
                    <table>
                        <tr>
                            <td><label><b>user</b></label></td>
                            <td><input type="text" id="nombreU" name="nombreU"></td>
                        </tr>
                        <tr>
                            <td><label><b>password</b></label></td>
                            <td><input type="text" id="pass" name="pass"></td>
                        </tr>
                        <tr>
                            <td><label><b>nombre caja</b></label></td>
                            <td><input type="text" id="nombreC" name="nombreC"></td>
                        </tr>
                         <tr>
                            <td><label><b>nombre cosa</b></label></td>
                            <td><input type="text" id="cosaN" name="cosaN"></td>
                        </tr>
                         <tr>
                            <td><label><b>cantidad cosa</b></label></td>
                            <td><input type="text" id="cosaC" name="cosaC"></td>
                        </tr>

                    </table><br>        
                    <button name="op2" value="add_caja" class="btn btn-success">Add caja</button>
                    <button name="op2" value="add_cosa" class="btn btn-success">Add cosa</button>
                    <button name="op2" value="add_cantidad" class="btn btn-success">Add cantidad</button>
                    <button name="op2" value="all" class="btn btn-success">All cajas</button>
                </form>
<?php
if (isset($mensaje)) {
    echo $mensaje;
}
?>
            </div>  
        </div>
    </body>
</html>