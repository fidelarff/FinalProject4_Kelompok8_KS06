<div class="container">
<table class="table table-striped">
    <thead>
        <tr>
            <th>Bus Image</th>
            <th>Bus Name</th>
            <th>Starting Point</th>
            <th>Destination</th>
            <th>Schedule Date</th>
            <th>Booked Seats</th>
            <th>Total Price</th>
        </tr>
    </thead>
    <?php
        include_once "../config/dbconnect.php";
        $ID= $_GET['ID'];
        //echo $ID;
        $sql="SELECT * FROM `booking_details`,`travelschedule`,`bus` WHERE booking_details.schedule_id=travelschedule.schedule_id AND travelschedule.bus_id=bus.bus_id AND booking_id=$ID";
        $result=$conn-> query($sql);

        if ($result-> num_rows > 0){
            while ($row=$result-> fetch_assoc()) {
    ?>
            <tr>
                <td><img width="100" src='../<?=$row["bus_image"]?>'/></td>
                <td><?=$row["bus_name"]?></td>
                <td><?=$row["starting_point"]?></td>
                <td><?=$row["destination"]?></td>
                <td><?=$row["schedule_date"]?></td>
                <td><?=$row["no_of_seats"]?></td>
                <td><?=$row["price"]?></td>
            </tr>
    <?php
            }
        }else{
            echo "error";
        }
    ?>
</table>
</div>
