
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="author" content="Bigyan Bista, Sushant Rahapal, Susant Basnet">
        <meta name="keywords" content="YC Kicks, Shoes">
        <meta name="description" content="Shoe Shop">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./assets/css/style.css"></link>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <title>Admin Profile</title>
        <style>
            .profile-btn{
                margin:5px;
                border-style: inset;
                background-color:#eeeeee;
            }
            .profile-btn a i{
                padding: 10px;
                font-size:30px; 
                color:grey;              
             }
            .profile-btn a i:hover{  
                color: #4269B4;
            }
        </style>
    </head>
    <body>
        <?php 
            include "./header.php";
        ?>
    
    <div class="container allContent-section">
        <div class="card-account card-container text-center">
            <h3>Your Profile</h3>
            <hr>
            <img id="profile-img" class="profile-img-card" src="./assets/images/profile.png" />
           
            
            <div class="profile-btn">
                 <p id="profile-name" class="profile-name-card">Welcome, <?= $_SESSION['full_name']?></p>
                <p id="profile-name" class="profile-name-card">Email: <?= $_SESSION['email']?></p>
                <?php 
                    if($_SESSION['isAdmin'] == 1){
                        ?>
                    <a href="./index.php" title="Admin Panel"><i class="fa fa-home"></i></a>
                <?php 
                    }
                ?>
                <a href="./logout.php" title="Logout"><i class="fa fa-sign-out " aria-hidden="true"></i></a>
                
            </div>
            
        </div><!-- /card-container -->
    </div><!-- /container -->
   
        <script type="text/javascript" src="./assets/js/ajaxWork.js"></script> 
        <script src="https://code.jquery.com/jquery-3.1.1.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" ></script>
    </body>
</html>