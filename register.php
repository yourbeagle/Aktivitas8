<?php
require "conn.php";
require "validate.php";
if(isset($_POST['username']) && isset($_POST['email']) && isset($_POST['password']) && isset($_POST['mobile']) && isset($_POST['gender'])){
    $username = validate($_POST['username']);
    $email = validate($_POST['email']);
    $password = validate($_POST['password']);
    $mobile = validate($_POST['mobile']);
    $gender = validate($_POST['gender']);
    $sql = "INSERT INTO users_table values('','$username','$email','$password','$mobile','$gender')";
    if(!$conn->query($sql)){
        echo "failure";
    }else{
        echo "success";
    }
}



?>