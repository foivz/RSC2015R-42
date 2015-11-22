<?php 

$servername = "localhost";
$username = "root";
$password = "toor";
$dbname = "RSC";
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
  	 die("Connection failed: " . mysqli_connect_error());
}
$randnum = rand(1, 1000);
$q = "INSERT INTO games SET gameCode=$randnum";
$query = mysqli_query($conn, $q);
$row = mysqli_fetch_assoc($query);
echo json_encode($row);		
mysqli_close($conn);



?>