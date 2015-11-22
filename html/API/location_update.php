<?php

$servername = "localhost";
$username = "root";
$password = "toor";
$dbname = "RSC";

$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
  	 die("Connection failed: " . mysqli_connect_error());
}

$q = "SELECT location, username FROM users WHERE location!='NULL' ";
$query = mysqli_query($conn, $q);
$rows = array();
while($row = mysqli_fetch_assoc($query)) {
    $rows[] = $row;
}
echo json_encode($rows);
mysqli_close($conn);



?>