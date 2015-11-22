<?php
error_reporting(E_ALL ^ E_DEPRECATED);

session_start(); 

$error=''; 
	if (empty($_POST['username']) && empty($_POST['password']) && isset($_POST['usernameReg']) && isset($_POST['passwordReg2'])) {
		$usernameReg = $_POST['usernameReg'];		
		$email = $_POST['emailReg'];
		$pass1 = $_POST['passwordReg1'];
		$pass2 = $_POST['passwordReg2'];

		if(strcmp($pass1, $pass2) == 0){

			$servername = "localhost";
			$username = "root";
			$password = "toor";
			$dbname = "RSC";
			$conn = mysqli_connect($servername, $username, $password, $dbname);
			if (!$conn) {
  			  die("Connection failed: " . mysqli_connect_error());
			}
			
			$sel_user = "select * from users where username='$usernameReg' OR email='$email'";

			$run_user = mysqli_query($conn, $sel_user);

			$check_user = mysqli_num_rows($run_user);

			if($check_user==0){
	
			$sql = "INSERT INTO users (username, password, email)
			VALUES ('$usernameReg', '$pass1', '$email')";

			if (mysqli_query($conn, $sql)) {
  			$regNotice = "1";
			} else {
  			  $regNotice = "Error: " . $sql . "<br>" . mysqli_error($conn);
			}
		mysqli_close($conn);
			}
			}	
			if($check_user != 0){
				
			$regNotice = '2';
			
				}
		elseif(strcmp($pass1, $pass2) != 0) {
		$regNotice = "3";
		}
		
	}
	elseif (isset($_POST['username']) && isset($_POST['password']))
	{ 
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
				$_SESSION['login_user']=$username; 	
			} else {
				$error = "4";
			}
		mysql_close($connection); 
	}

?>

