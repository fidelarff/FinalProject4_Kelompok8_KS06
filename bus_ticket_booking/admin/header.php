<?php
   session_start();
   include_once "./config/dbconnect.php";

?>
       
<nav class="navbar navbar-expand-lg navbar-light px-5" style="background-color: #00877D;" >
    <a class="navbar-brand ml-5 profile-img-card" href="./index.php">
        <img class="logo-img" src="./assets/images/logo.png" width="80px" height="80px" alt="My Destination">
    </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
      
    </ul>
    <form class="form-inline my-2 my-lg-0">
        <?php
        
        if(isset($_SESSION['user_id'])){
          ?>
          <a href="./profile.php" style="text-decoration:none;">
            <i class="fa fa-user mr-5" style="font-size:30px; color:#fff;" aria-hidden="true"></i>
         </a>
          <?php
        } else {
            ?>
                <a href=" ./login.php" style="text-decoration:none;">
                    <i class="fa fa-sign-in mr-5" style="font-size:30px; color:#fff;" aria-hidden="true"></i>
               </a>

            <?php
        } ?>
    </form>
  </div>
</nav>
