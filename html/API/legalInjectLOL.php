<?php

$get_q = $_POST['query'];

$servername = "localhost";
$username = "root";
$password = "toor";
$dbname = "RSC";
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
  	 die("Connection failed: " . mysqli_connect_error());
}
$query = mysqli_query($conn, $get_q);
$row = mysql_fetch_assoc($query);
echo json_encode($row);		
mysqli_close($conn);



?>