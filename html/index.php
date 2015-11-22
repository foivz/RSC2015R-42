<html xmlns="http://www.w3.org/1999/html">
<head>

    <?php
    include 'head.php';
    include $_SERVER['DOCUMENT_ROOT'] . '/backend/session.php';
    ?>

</head>
<body>


<?php
include 'nav.php';
?>

<div class="mate"></div>
<div class="fullscreen-bg">

    <video loop muted autoplay class="fullscreen-bg__video">
        <source src="src/video.webm" type="video/webm">
    </video>
</div>


<div class="activeusers">
    <p class="big_ass_number"><?php echo $users ?></p>

    <p class="small_ass_txt">ARE CURRENTLY COLORBLIND</p>
    <?php if (isset($_SESSION['login_user'])) { ?>
        <a class="btnblack" href="myacc">MY ACCOUNT</a>
    <?php } else { ?>
        <a class="btnblack" href="login">JOIN THEM</a>
    <?php } ?>
    <a href="spectate" class="btnblack">SPECTATE</a>
</div>

<div class="about">
    <center>
        <div class="aboutinner">
            <p class="abouthead">ABOUT COLORBLIND</p>
            Colorblind is an open source solution designed to augment your standard game of paintball, bringing it to a
            whole new level of awesome! The application was built during the 24 hour-long 'Ready, Steady, Code'
            hackathon, and is currently available only for the Android platform. We hope you'll like it, and make good
            use of it's code.
            </br>Team 42

        </div>
    </center>
</div>


</body>
</html>