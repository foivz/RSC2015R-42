<?php
error_reporting(E_ALL ^ E_DEPRECATED);
error_reporting( error_reporting() & ~E_NOTICE );

$servername = "localhost";
$username = "root";
$password = "toor";
$dbname = "RSC";
$location = $_POST['data'];
$id = $_POST['id'];
$conn = mysqli_connect($servername, $username, $password, $dbname);
if (!$conn) {
  	die("Connection failed: " . mysqli_connect_error());
}	
			$sql = "UPDATE users SET location='$location' where ID='$id'";

			mysqli_query($conn, $sql);
			mysqli_close($conn);

?>