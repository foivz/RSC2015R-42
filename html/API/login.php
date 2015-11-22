<?php
error_reporting(E_ALL ^ E_DEPRECATED);

	
	$username=$_POST['username'];
	$password=$_POST['password'];
	$connection = mysql_connect("localhost", "root", "toor");
	$username = stripslashes($username);
	$password = stripslashes($password);
	$username = mysql_real_escape_string($username);
	$password = mysql_real_escape_string($password);
	$db = mysql_select_db("RSC", $connection);
	$query = mysql_query("select * from users where password='$password' AND username='$username'", $connection);
	$rows = mysql_num_rows($query);
		if ($rows == 1) {
			$row = mysql_fetch_assoc($query);
			echo json_encode($row);			
		} else {
			echo "FALSE";
		}
	mysql_close($connection); 

?>

