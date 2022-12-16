<!DOCTYPE html>
<html>
<head>
  <title>Admin</title>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
       <link rel="stylesheet" href="./assets/css/style.css"></link>
  </head>
</head>
<body >
    
        <?php
            include "./header.php";
            include "./sidebar.php";
            if( $_SESSION['isAdmin'] != 1)
            {
                session_destroy();
                header("location: ./login.php");
            }
           

            include_once "./config/dbconnect.php";
        ?>

    <div id="main-content" class="container-fluid allContent-section px-5 py-3" >
        <div class="row text-center px-5">
            <div class="col-sm-4">
                <div class="card" onclick="showCustomers()">
                    <i class="fa fa-users  mb-2" style="font-size: 70px;"></i>
                    <h4 style="color:white;">Total Users</h4>
                    <h5 style="color:white;">
                    <?php
                        $sql="SELECT * from users where isAdmin=0";
                        $result=$conn-> query($sql);
                       
                        echo $result-> num_rows;
                    ?></h5>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="card" onclick="showBuses()">
                    <i class="fa fa-bus mb-2" style="font-size: 70px;"></i>
                    <h4 style="color:white;">Total Buses</h4>
                    <h5 style="color:white;">
                    <?php
                       
                       $sql="SELECT * from bus";
                       $result=$conn-> query($sql);
                       
                       echo $result-> num_rows;
                   ?>
                   </h5>
                </div>
            </div>
            <div class="col-sm-4">
            <div class="card" onclick="showSchedules()">
                    <i class="fa fa-road mb-2" style="font-size: 70px;"></i>
                    <h4 style="color:white;">Total Schedules</h4>
                    <h5 style="color:white;">
                    <?php
                       
                       $sql="SELECT * from travelschedule";
                       $result=$conn-> query($sql);
                      
                       echo $result-> num_rows;
                   ?>
                   </h5>
                </div>
            </div>
        
        </div>
        
        <div class="row text-center px-5">
            
            <div class="col-sm-4">
                <div class="card" onclick="showBusForReservations()">
                    <i class="fa fa-bus mb-2" style="font-size: 70px;"></i>
                    <h4 style="color:white;">Buses for Reservation</h4>
                    <h5 style="color:white;">
                    <?php
                       
                       $sql="SELECT * from busfor_reservation";
                       $result=$conn-> query($sql);
                       
                       echo $result-> num_rows;
                   ?>
                   </h5>
                </div>
            </div>
            <div class="col-sm-4">
            <div class="card" onclick="showReserved()">
                    <i class="fa fa-lock mb-2" style="font-size: 70px;"></i>
                    <h4 style="color:white;">Total Reserved Buses</h4>
                    <h5 style="color:white;">
                    <?php
                       
                       $sql="SELECT * from reservedBuses";
                       $result=$conn-> query($sql);
                      
                       echo $result-> num_rows;
                   ?>
                   </h5>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="card" onclick="showBookings()">
                    <i class="fa fa-list mb-2" style="font-size: 70px;"></i>
                    <h4 style="color:white;">Total Bookings</h4>
                    <h5 style="color:white;">
                    <?php
                       
                       $sql="SELECT * from bookings";
                       $result=$conn-> query($sql);
                   
                       echo $result-> num_rows;
                   ?>
                   </h5>
                </div>
            </div>
        </div>
        
    </div>
       
            
        <?php
            if (isset($_GET['category']) && $_GET['category'] == "success") {
                echo '<script> alert("Category Successfully Added")</script>';
            }else if (isset($_GET['category']) && $_GET['category'] == "error") {
                echo '<script> alert("Adding Unsuccess")</script>';
            }
            if (isset($_GET['size']) && $_GET['size'] == "success") {
                echo '<script> alert("Size Successfully Added")</script>';
            }else if (isset($_GET['size']) && $_GET['size'] == "error") {
                echo '<script> alert("Adding Unsuccess")</script>';
            }
            if (isset($_GET['variation']) && $_GET['variation'] == "success") {
                echo '<script> alert("Variation Successfully Added")</script>';
            }else if (isset($_GET['variation']) && $_GET['variation'] == "error") {
                echo '<script> alert("Adding Unsuccess")</script>';
            }
        ?>


    <script type="text/javascript" src="./assets/js/ajaxWork.js"></script>    
    <script type="text/javascript" src="./assets/js/script.js"></script>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js" ></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" ></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>
</body>
 
</html>