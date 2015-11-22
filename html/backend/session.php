<?php
error_reporting(E_ALL ^ E_DEPRECATED);
error_reporting( error_reporting() & ~E_NOTICE );

$connection = mysql_connect("localhost", "root", "toor");
$db = mysql_select_db("RSC", $connection);

session_start();

$user_check=$_SESSION['login_user'];

$ses_sql=mysql_query("select username from users where username='$user_check'", $connection);
$row = mysql_fetch_assoc($ses_sql);
$username =$row['username'];

$ses2_sql=mysql_query("select email from users where username='$user_check'", $connection);
$row2 = mysql_fetch_assoc($ses2_sql);
$email =$row2['email'];

$ses3_sql=mysql_query("select elo from users where username='$user_check'", $connection);
$row3 = mysql_fetch_assoc($ses3_sql);
$elo =$row3['elo'];

$ses4_sql=mysql_query("select ID from users where username='$user_check'", $connection);
$row4 = mysql_fetch_assoc($ses4_sql);
$id =$row4['ID'];

$ses5_sql=mysql_query("select * from users", $connection);
$users = mysql_num_rows($ses5_sql);


mysql_close($connection); 

if(!file_exists($_SERVER['DOCUMENT_ROOT'] . '/src/user_img/' . $id . '.png')){
	copy(($_SERVER['DOCUMENT_ROOT'] . '/src/user_img/def.png'), ($_SERVER['DOCUMENT_ROOT'] . '/src/user_img/' . $id . '.png'));
	}

?>