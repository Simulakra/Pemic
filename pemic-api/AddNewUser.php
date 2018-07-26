<?php
 
$response = array();

define('DB_USER', $_POST['DB_User']); // db user
define('DB_PASSWORD', $_POST['DB_Pass']); // db password (mention your db password here)
define('DB_DATABASE', $_POST['DB_Name']); // database name
define('DB_SERVER', "localhost"); // db server // $_POST['DB_IP']

if (isset($_POST['MAC']) && isset($_POST['Nickname'])){
    
    $db = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());

    $mac=$_POST['MAC'];
    $nickname=$_POST['Nickname'];

    $result = mysqli_query($db, "INSERT INTO users(MAC, Nickname) VALUES('$mac','$nickname')") or die(mysqli_error());

    if ($result) {
        $response["success"] = 1;
        $response["message"] = "User successfully created.";
 
        echo json_encode($response);
    } else {
        $response["success"] = -1;
        $response["message"] = "Belirtilen MAC adresine tanımlı bir kullanıcı daha var.";
 
        echo json_encode($response);
    }

}else {
    $response["success"] = 0;
    $response["message"] = "Gerekli Alanlar Doldurulmalıdır.";
 
    echo json_encode($response);
}
?>