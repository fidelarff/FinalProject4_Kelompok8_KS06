//show contents pages JS
function showSchedules(){  
    $.ajax({
        url:"./adminView/viewSchedules.php",
        method:"post",
        data:{record:1},
        success:function(data){
            $('.allContent-section').html(data);
        }
    });
}

function showBusForReservations(){  
    $.ajax({
        url:"./adminView/viewBusForReservation.php",
        method:"post",
        data:{record:1},
        success:function(data){
            $('.allContent-section').html(data);
        }
    });
}


function showCustomers(){
    $.ajax({
        url:"./adminView/viewCustomers.php",
        method:"post",
        data:{record:1},
        success:function(data){
            $('.allContent-section').html(data);
        }
    });
}

function showBuses(){
    $.ajax({
        url:"./adminView/viewBuses.php",
        method:"post",
        data:{record:1},
        success:function(data){
            $('.allContent-section').html(data);
        }
    });
}
function showBookings(){
    $.ajax({
        url:"./adminView/viewBookings.php",
        method:"post",
        data:{record:1},
        success:function(data){
            $('.allContent-section').html(data);
        }
    });
}


//add schedule data
function addItems(){
    var bus=$('#bus').val();
    var starting=$('#starting').val();
    var destination=$('#destination').val();
    var upload=$('#upload').val();
    var schedule=$('#schedule').val();
    var departure=$('#departure').val();
    var fare=$('#fare').val();
    var available=$('#available').val();

    var fd = new FormData();
    fd.append('bus', bus);
    fd.append('starting', starting);
    fd.append('destination', destination);
    fd.append('schedule', schedule);
    fd.append('departure', departure);
    fd.append('fare', fare);
    fd.append('available', available);
    fd.append('upload', upload);
    $.ajax({
        url:"./controller/addItemController.php",
        method:"post",
        data:fd,
        processData: false,
        contentType: false,
        success: function(data){
            alert('Schedule Added successfully.');
            $('form').trigger('reset');
            showSchedules();
        }
    });
}

//edit schedule data
function itemEditForm(id){
    $.ajax({
        url:"./adminView/editItemForm.php",
        method:"post",
        data:{record:id},
        success:function(data){
            $('.allContent-section').html(data);
        }
    });
}

//update schedule after submit
function updateItems(){
    var schedule_id=$('#schedule_id').val();
    var bus=$('#bus').val();
    var starting=$('#starting').val();
    var destination=$('#destination').val();
    var upload=$('#upload').val();
    var schedule=$('#schedule').val();
    var departure=$('#departure').val();
    var fare=$('#fare').val();
    var available=$('#available').val();

    var fd = new FormData();
    fd.append('schedule_id', schedule_id);
    fd.append('bus', bus);
    fd.append('starting', starting);
    fd.append('destination', destination);
    fd.append('schedule', schedule);
    fd.append('departure', departure);
    fd.append('fare', fare);
    fd.append('available', available);
    fd.append('upload', upload);
   
    $.ajax({
      url:'./controller/updateItemController.php',
      method:'post',
      data:fd,
      processData: false,
      contentType: false,
      success: function(data){
        alert('Schedule Details Updated Success.');
        $('form').trigger('reset');
        showSchedules();
      }
    });
}

//delete schedule data
function itemDelete(id){
    $.ajax({
        url:"./controller/deleteItemController.php",
        method:"post",
        data:{record:id},
        success:function(data){
            alert('Schedule Successfully deleted');
            $('form').trigger('reset');
            showSchedules();
        }
    });
}




//add bus data
function addBuses(){
    var p_name=$('#p_name').val();
    var b_number=$('#b_number').val();
    var capacity=$('#capacity').val();
    var upload=$('#upload').val();
    var file=$('#file')[0].files[0];

    var fd = new FormData();
    fd.append('p_name', p_name);
    fd.append('b_number', b_number);
    fd.append('capacity', capacity);
    fd.append('file', file);
    fd.append('upload', upload);
    $.ajax({
        url:"./controller/addBusesController.php",
        method:"post",
        data:fd,
        processData: false,
        contentType: false,
        success: function(data){
            alert('Bus successfully added.');
            $('form').trigger('reset');
            showBuses();
        }
    });
}

//edit  bus data
function busEditForm(id){
    $.ajax({
        url:"./adminView/editBusesForm.php",
        method:"post",
        data:{record:id},
        success:function(data){
            $('.allContent-section').html(data);
        }
    });
}

//update bus details
function updateBus(){
    var bus_id = $('#bus_id').val();
    var p_name = $('#p_name').val();
    var b_number = $('#b_number').val();
    var capacity = $('#capacity').val();
    var existingImage = $('#existingImage').val();
    var newImage = $('#newImage')[0].files[0];
    var fd = new FormData();
    fd.append('bus_id', bus_id);
    fd.append('p_name', p_name);
    fd.append('b_number', b_number);
    fd.append('capacity', capacity);
    fd.append('existingImage', existingImage);
    fd.append('newImage', newImage);
   
    $.ajax({
      url:'./controller/updateBusesController.php',
      method:'post',
      data:fd,
      processData: false,
      contentType: false,
      success: function(data){
        alert('Bus Data Update Success.');
        $('form').trigger('reset');
        showBuses();
      }
    });
}

//delete bus data
function busDelete(id){
    $.ajax({
        url:"./controller/deleteBusesController.php",
        method:"post",
        data:{record:id},
        success:function(data){
            alert('Bus deleted Successfully');
            $('form').trigger('reset');
            showBuses();
        }
    });
}



//add bus to reservation data
function addBustoReserve(){
    var bus=$('#bus').val();
    var starting=$('#starting').val();
    var destination=$('#destination').val();
    var upload=$('#upload').val();
    var fare=$('#fare').val();

    var fd = new FormData();
    fd.append('bus', bus);
    fd.append('starting', starting);
    fd.append('destination', destination);
    fd.append('fare', fare);
    fd.append('upload', upload);
    $.ajax({
        url:"./controller/addBusToReservationController.php",
        method:"post",
        data:fd,
        processData: false,
        contentType: false,
        success: function(data){
            alert('Bus Added for reservation successfully.');
            $('form').trigger('reset');
            showBusForReservations();
        }
    });
}

//edit reservation data
function reserveEditForm(id){
    $.ajax({
        url:"./adminView/editReserveForm.php",
        method:"post",
        data:{record:id},
        success:function(data){
            $('.allContent-section').html(data);
        }
    });
}

//update reservation after submit
function updateReserve(){
    var r_id=$('#r_id').val();
    var bus=$('#bus').val();
    var starting=$('#starting').val();
    var destination=$('#destination').val();
    var upload=$('#upload').val();
    var fare=$('#fare').val();

    var fd = new FormData();
    fd.append('r_id', r_id);
    fd.append('bus', bus);
    fd.append('starting', starting);
    fd.append('destination', destination);
    fd.append('fare', fare);
    fd.append('upload', upload);
   
    $.ajax({
      url:'./controller/updateReserveController.php',
      method:'post',
      data:fd,
      processData: false,
      contentType: false,
      success: function(data){
        alert('Schedule Details Updated Success.');
        $('form').trigger('reset');
        showBusForReservations();
      }
    });
}

//delete reservation data
function reserveDelete(id){
    $.ajax({
        url:"./controller/deleteReserveController.php",
        method:"post",
        data:{record:id},
        success:function(data){
            alert('Bus for reservation Successfully deleted');
            $('form').trigger('reset');
            showBusForReservations();
        }
    });
}


