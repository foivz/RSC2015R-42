<?php
$host = "192.168.10.2";
$port = 31337;
// No Timeout
set_time_limit(0);
$socket = socket_create(AF_INET, SOCK_STREAM, 0) or die("Could not create socket\n");
$result = socket_bind($socket, $host, $port);
socket_write($socket, "0", 1) or die("Could not send data to server\n");
$result = socket_read ($socket, 1024) or die("Could not read server response\n");
echo "Reply From Server  :".$result;
socket_close($socket);