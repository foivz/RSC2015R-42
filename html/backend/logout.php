<?php
	session_start();
	if(session_destroy())
		{
			header("Location: http://192.168.10.10/login"); 
		}
?>