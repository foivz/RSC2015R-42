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

include($_SERVER['DOCUMENT_ROOT'] . '/backend/auth.php');

?>

<div class="activeusers">

    <div class="mrzimantona">


        <div class="hoverish"><label for="browse"><img class="profchange" style="width: 120px;cursor: pointer;"
                                                       src="src/user_img/<?php echo $id ?>.png"/>

                <p class="istitajaliveci profchange user" style="cursor: pointer;"><?php echo $username ?></p>

                <p class="istitajaliveci profchange change" style="display: none;cursor: pointer;">change picture</p>
            </label>
                <label for="dizese"><p class="istitajaliveci uploadit" style="display: none;cursor: pointer;">upload it</p></label>
        </div>



    </div>

    <form enctype="multipart/form-data" method="post" action="/backend/upload.php">

        <input onchange="changeFunkcija();" id="browse" name="uploaded_file" type="file"
               style="display:none"/><br/><br/>
        <input id="dizese" type="submit" value="Upload It" style="display:none"/>
    </form>

    <script>

        function changeFunkcija() {
            $(".user").hide();
            $(".change").hide();
            $(".uploadit").show();
            $('.profchange').unbind('mouseenter').unbind('mouseleave');
        }

        $(".profchange").hover(function () {
            $(".user").hide();
            $(".change").show();
        });

        $(".profchange").mouseleave(function () {
            $(".user").show();
            $(".change").hide();
        });
    </script>
</html>