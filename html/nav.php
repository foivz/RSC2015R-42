<?php include( $_SERVER['DOCUMENT_ROOT'] . '/backend/session.php' ); ?>

<div class="nav" >


<a class="anav" href="/">HOME</a>&nbsp <a class="anav" href="/spectate">MATCH</a>
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
        <a href="/"><img style="width: 120px; margin-bottom: -20px" src="src/logo.svg"/></a>
    &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
        <a class="anav" href="support">SUPPORT</a> &nbsp
    <?php if(isset($_SESSION['login_user'])) { ?>
    <a class="anav" href="/backend/logout">LOG OUT</a></div>
    <?php } else { ?>
        <a class="anav" href="login">LOG IN</a>
    <?php } ?>
    

</div>