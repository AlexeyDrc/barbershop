<? php
$customer_id = $_POST['idUser']
$master_id = $_POST['master']
$date = $_POST['date']
$service_type_id = $_POST['service']

if(!empty($customer_id) || !empty($master_id) || !empty($date) || !empty($service_type_id)){
    $host = "localhost";
    $dbUsername = "root";
    $dbPassword = "";
    $dbname = "orders_database";

    $conn = new mysqli($host, $dbUsername, $dbPassword, $dbname);

    if(mysql_connect_error()){
    die('Connect Error('. mysql_connect_error(). ')'.mysql_connect_error());
    }
    else{
    $SELECT = "SELECT * from orders";
    $INSERT = "INSERT into orders(idUser, master, date, service) values(?,?,?,?)"

  /*   $stmt = $conn->prepart($SELECT)
    $stmt -> execute();
    $stmt -> store_results(); */

    $stmt = $conn->prepart($INSERT);
    $stmt -> bind_param("ssssii", $customer_id, $master_id ,$date, $service_type_id);
    $stmt -> execute();

    }
}

else{
echo "All field are required";
die();
}
?>