<?php 
include 'session.php';

$fileName = $_FILES["uploaded_file"]["name"]; 
$fileTmpLoc = $_FILES["uploaded_file"]["tmp_name"]; 
$fileType = $_FILES["uploaded_file"]["type"];
$fileSize = $_FILES["uploaded_file"]["size"];
$fileErrorMsg = $_FILES["uploaded_file"]["error"];
$fileName = preg_replace('#[^a-z.0-9]#i', '', $fileName);
$kaboom = explode(".", $fileName);
$fileExt = end($kaboom);

if (!$fileTmpLoc) { 
    echo "ERROR: Please browse for a file before clicking the upload button.";
    exit();
} else if($fileSize > 5242880) { 
    echo "ERROR: Your file was larger than 5 Megabytes in size.";
    unlink($fileTmpLoc); 
    exit();
} else if (!preg_match("/.(gif|jpg|png)$/i", $fileName) ) {  
     echo "ERROR: Your image was not .gif, .jpg, or .png.";
     unlink($fileTmpLoc); 
     exit();
} else if ($fileErrorMsg == 1) { 
    echo "ERROR: An error occured while processing the file. Try again.";
    exit();
}

$moveResult = move_uploaded_file($fileTmpLoc, $_SERVER['DOCUMENT_ROOT'] . '/src/user_img/' . $id . '.png');
if ($moveResult != true) {
    echo "ERROR: File not uploaded. Try again.";
    exit();
}

include_once($_SERVER['DOCUMENT_ROOT'] . '/backend/convert.php');
$target_file = ($_SERVER['DOCUMENT_ROOT'] . '/src/user_img/$fileName');
$resized_file = ($_SERVER['DOCUMENT_ROOT'] . '/src/user_img/resized_$fileName');
$wmax = 500;
$hmax = 500;
ak_img_resize($target_file, $resized_file, $wmax, $hmax, $fileExt);
if (strtolower($fileExt) != "jpg") {
    $target_file = "uploads/resized_$fileName";
    $new_jpg = "uploads/resized_".$kaboom[0].".jpg";
    ak_img_convert_to_jpg($target_file, $new_jpg, $fileExt);
}

header("Location: http://192.168.10.10/myacc");
/*
echo "The file named <strong>$fileName</strong> uploaded successfuly.<br /><br />";
echo "It is <strong>$fileSize</strong> bytes in size.<br /><br />";
echo "It is an <strong>$fileType</strong> type of file.<br /><br />";
echo "The file extension is <strong>$fileExt</strong><br /><br />";
echo "The Error Message output for this upload is: $fileErrorMsg";
*/
?>