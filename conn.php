<?php
$db_name = "users_database";
$username = "root";
$password = "";
$servername = "localhost";
$conn = mysqli_connect($servername ,$username ,$password ,$db_name );
mysqli_set_charset($conn ,"utf-8");
?>