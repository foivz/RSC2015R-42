<html xmlns="http://www.w3.org/1999/html">
<head>

    <?php
    include 'head.php';
    ?>

</head>
<body>


<?php
include 'nav.php';

include($_SERVER['DOCUMENT_ROOT'] . '/backend/auth.php');

if (isset($_SESSION['login_user'])) {
    header("location: /myacc");
}
?>

<div class="activeusers">

    <div class="mrzimantona">

        <a href="https://github.com/foivz/RSC2015R-42">
            <div><img style="width: 120px;" src="src/img/app.svg"/>

                <p class="istitajaliveci">DOWNLOAD OUR APP</p></div>
        </a>

    </div>
    <center>
        <div class="signandreg">
            <div class="signindiv">
                <p class="problems2 problems small_ass_txt" id="logtxt">LOG IN</p>

                <form method="post" id="signin">
                    <div><input type="text" name="username" placeholder="username" required class="formsiginin"></div>
                    </br>
                    <div><input type="password" name="password" placeholder="password" required class="formsiginin">
                    </div>
                </form>
                <div class="vuco1"></div>
                <a class="btnpink" href="#" onClick="document.getElementById('signin').submit();">LOG IN</a>
            </div>

            <div class="line1"></div>

            <div class="registerdiv">
                <p class="problems small_ass_txt" id="regtxt">REGISTER</p>

                <form method="post" id="register">
                    <div><input type="text" name="usernameReg" placeholder="username" required class="formsiginin">
                    </div>
                    </br>
                    <div><input type="email" name="emailReg" placeholder="email" required class="formsiginin"></div>
                    </br>
                    <div><input type="password" name="passwordReg1" placeholder="password" required class="formsiginin">
                    </div>
                    </br>
                    <div><input type="password" name="passwordReg2" placeholder="password, one more time" required
                                class="formsiginin"></div>
                </form>
                <div class="vuco"></div>
                <a class="btnpink" href="#" onClick="document.getElementById('register').submit();">REGISTER</a>
                <div class="vuco"></div><div class="vuco"></div><div class="vuco"></div>
            </div>
        </div>
    </center>

</body>

<?php
if($regNotice=="1") echo "
<script>
    $( document ).ready(function() {
        $(\"#regtxt\").notify(\"Welcome! You may now log in.\", \"success\",
         { position:\"right\" }
         );
    });

</script>
";

if($regNotice=="2") echo "
<script>
    $( document ).ready(function() {
        $(\"#regtxt\").notify(\"This username is already taken!\" );
    });

</script>
";

if($regNotice=="3") echo "
<script>
    $( document ).ready(function() {
        $(\"#regtxt\").notify(\"Passwords are not matching. Could you try again?\");
    });

</script>
";

if($error=="4") echo "
<script>
    $( document ).ready(function() {
        $(\"#logtxt\").notify(\"Wrong username or password. Could you try again?\" );
    });

</script>
";

?>

</html>