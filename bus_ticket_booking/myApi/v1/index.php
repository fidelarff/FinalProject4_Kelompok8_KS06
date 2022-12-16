<?php

require_once '../include/DbHandler.php';
require_once '../include/PassHash.php';
require '.././libs/Slim/Slim.php';

\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();

// User id from db - Global Variable
$UserId = NULL;

/**
 * Adding Middle Layer to authenticate every request
 * Checking if the request has valid api key in the 'Authorization' header
 */
function authenticate(\Slim\Route $route) {
    // Getting request headers
    $headers = apache_request_headers();
    $response = array();
    $app = \Slim\Slim::getInstance();

    // Verifying Authorization Header
    if (isset($headers['api_key'])) {
        $db = new DbHandler();

        // get the api key
        $apikey = $headers['api_key'];
        // validating api key
        if (!$db->isValidApiKeyUser($apikey)) {
            // api key is not present in users table
            $response["error"] = true;
            $response["message"] = "Access Denied. Invalid Api key";
            echoRespnse(401, $response);
            $app->stop();
        } else {
            global $UserId;
            // get user primary key id
            $UserId = $db->getUserId($apikey);
        }
    } else {
        // api key is missing in header
        $response["error"] = true;
        $response["message"] = "Api key is misssing";
        echoRespnse(400, $response);
        $app->stop();
    }
}



/**
 * ----------- METHODS WITHOUT AUTHENTICATION ---------------------------------
 */
/**
 * User SignUp
 
 * method - POST
 * params - Name, Email, Password, Phone_No
 */
$app->post('/register', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('full_name', 'email', 'password' ,'contact_no'));

            $response = array();

            // reading post params
            $full_name = $app->request->post('full_name');
            $email = $app->request->post('email');
            $password = $app->request->post('password');
            $contact_no = $app->request->post('contact_no');
           
            // validating email address
            validateEmail($email);

            $db = new DbHandler();
            $res = $db->createUser($full_name, $email, $password, $contact_no);

            if ($res == USER_CREATED_SUCCESSFULLY) {
                $response["error"] = false;
                $response["message"] = "You are successfully registered";
            } else if ($res == USER_CREATE_FAILED) {
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while registereing";
            } else if ($res == USER_ALREADY_EXISTED) {
                $response["error"] = true;
                $response["message"] = "Sorry, this email already existed";
            }
            // echo json response
            echoRespnse(201, $response);
        });
        

/**
 * User Login
 * url - /login
 * method - POST
 * params - Email, Password
 */
$app->post('/login', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('email', 'password'));

            // reading post params
            $email = $app->request()->post('email');
            $password = $app->request()->post('password');
            $response = array();

            $db = new DbHandler();
            // check for correct Email and Password
            if ($db->checkUserLogin($email, $password)) {
                // get the user by email
                $User = $db->getUserByEmail($email);

                if ($User != NULL) {
                    $response["error"] = false;
                    $response['user_id']= $User['user_id'];
                    $response['full_name'] = $User['full_name'];
                    $response['email'] = $User['email'];
                    $response['contact_no']=$User['contact_no'];
                    $response['api_key'] = $User['api_key'];
                    $response['created_at'] = $User['created_at'];
                    $response['isAdmin'] =$User['isAdmin']; $response['profile_pic'] = $User['profile_pic'];
                } else {
                    // unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "An error occurred. Please try again";
                }
            } else {
                // user credentials are wrong
                $response['error'] = true;
                $response['message'] = 'Login failed. Incorrect credentials';
            }

            echoRespnse(200, $response);
        });
        
   

  
///edit profile
$app->post('/edit_profile', 'authenticate', function() use ($app) {
    global $UserId;
        
    $uploadDirectoryLink = 'profilePicture/';
    $response=array();
    $has_pp = false;

        if(isset($_FILES['file'])){
            $has_pp = true;
        }
        if($has_pp || isset($_POST['email']) || isset($_POST['full_name']) || isset($_POST['contact_no']) || isset($_POST['oldProfileLink'])){
            $fileName ;
            $fileSize =null;
            $fileTmpName = null;
            $fileType = null;
            $fileExtension = null ;
            $filename = null ;
            $imageLink = null;
            $finalEmailId = null;
        
            $db = new DbHandler();
            if($has_pp){
                $fileName = $_FILES['file']['name'];
                $fileSize = $_FILES['file']['size'];
                $fileTmpName = $_FILES['file']['tmp_name'];
                $fileType = $_FILES['file']['type'];
            
                $tmpo = explode('.', $fileName);
                $fileExtension = end($tmpo);
                $path_parts = pathinfo($_FILES["file"]["name"]);
                $filename = $path_parts['filename'] . '_' . time() . '_'.$UserId. '.' . $path_parts['extension']  ;
            
                $year = date("Y");
                $month = date("m");
                $short_dir = $year . "/" . $month;
                $imageLink =$uploadDirectoryLink . $short_dir . "/" . $filename;
            
            }
        
            $full_name = $app->request->post('full_name');
            $phone_number = $app->request->post('contact_no');
            $email_id = $app->request->post('email');
            $oldProfileLink = $app->request->post('oldProfileLink');
        
        
            // validating email address
            validateEmail($email_id);
            $finalEmailId = $email_id;
            
            
            if(is_null($imageLink)){
                $finalImageLink=$oldProfileLink;
            }else{
                $finalImageLink=$imageLink;
            }

            
            if($db->editUser($UserId, $full_name,$finalEmailId, $phone_number, $fileSize, $fileTmpName, $fileType, $fileExtension , $filename ,$finalImageLink, $has_pp))
            {
            $customer=$db->getUserId($UserId);
                    if ($customer != NULL) {
                        $response["error"] = false;
                        $response["message"] = "User info changed successfully";
                        $response['full_name'] = $customer['full_name'];
                        $response['email'] = $customer['email'];
                        $response["profile_pic"] = $customer['profile_pic'];
                        $response["contact_no"] = $customer['contact_no'];
                        echoRespnse(200, $response);
                    }else{
                        $response['error'] = true;
                        $response['message'] = "An error occurred. Please try again";
                        echoRespnse(200, $response);    
                    }
                
            }else
            {
                $response["error"] = true;
                $response["message"] = "User info not changed";
                echoRespnse(300, $response);
            

            } 
        
        }
        else{
            $response["message"] = "Nothing to be changed";
            echoRespnse(300, $response);
        }

}); 


///edit password
$app->post('/edit_password', 'authenticate', function() use ($app) {
    global $UserId;
    // check for required params
    verifyRequiredParams(array('password'));  
   
    $response=array();
        
    $password = $app->request->post('password');
      
    
    $db = new DbHandler();
    $res = $db->editPass($UserId,$password);

    if ($res == TRUE) {
        $response["error"] = false;
        $response['message'] ="Password changed.";
    }
    else {
        // unknown error occurred
        $response['error'] = true;
        $response['message'] = "Error!";
    }
    echoRespnse(200, $response);
    
});
       




    
//view all schedules

$app -> get('/get-all-schedule','authenticate', function () use ($app){
 
    $response = array();
    $db = new DbHandler();
    if(isset($_REQUEST['id'])){
        $s_id = $app->request()->get('id');
        $response["data"] = $db->getScheduleById($s_id);
    }else
    $response["datas"] = $db->getAllSchedule();
    $response["error"] = false;
    $response["message"] = "Travel Schedule Details Successfully Fetched";
    echoRespnse(200, $response);

});
    
    

$app->post('/bookings','authenticate', function() use ($app) {
    
    global $UserId;
    verifyRequiredParams(array('schedule_id','no_of_seats','pay_method','pay_status','price'));

    $response = array();
    $pay_method = $app->request->post('pay_method');
    $pay_status= $app->request ->post('pay_status');
    $schedule_id= $app->request ->post('schedule_id');
    $no_of_seats= $app->request ->post('no_of_seats');
    $price= $app->request ->post('price');
       
    $db = new DbHandler();
    $res = $db->bookedBy($UserId,$pay_method,$pay_status,$schedule_id,$no_of_seats,$price);
    if ($res ==1) {
        $response["error"] = false;
        $response["message"] = "successfully booked";
    }
    else if($res == 10)  {
        $response["error"] = true;
        $response["message"] = "No schedule selected";
    } 
    else  {
        $response["error"] = true;
        $response["message"] = "Booking unsuccessful";
    } 
    
    
    echoRespnse(200, $response);

});




////view booking history 
 $app->get('/view_bookings','authenticate', function() use ($app) {
    // check for required params
    global $UserId;

    $response = array();

    // reading post params

    $db = new DbHandler();
    $res = $db->showBookings($UserId);
    if ($res != NULL) {
        $response["error"] = false;
        $response['data'] = $res;
        $response['message'] ="successfully fetched";
    }
    else if($res == 0){
        // unknown error occurred
        $response['error'] = false;
        $response['message'] = "No data found";
        $response['data'] = NULL; 
    }
    else {
        // unknown error occurred
        $response['error'] = true;
        $response['message'] = "Error!";
        $response['data'] = NULL; 
    }
    echoRespnse(200, $response);
    });
    
    
    
    
    
    
       
//view all bus for reservation

$app -> get('/get-bus-forReservation','authenticate', function () use ($app){
 
    $response = array();
    $db = new DbHandler();
    if(isset($_REQUEST['id'])){
        $s_id = $app->request()->get('id');
        $response["data"] = $db->getBusForReserveById($s_id);
    }else
    $response["datas"] = $db->getAllBusForReserve();
    $response["error"] = false;
    $response["message"] = "Bus for reservation Successfully Fetched";
    echoRespnse(200, $response);

});







$app->post('/reserve','authenticate', function() use ($app) {
    
    global $UserId;
    verifyRequiredParams(array('r_id','no_of_days','start_date','end_date','pay_method','pay_status','price'));

    $response = array();
    $pay_method = $app->request->post('pay_method');
    $pay_status= $app->request ->post('pay_status');
    $r_id= $app->request ->post('r_id');
    $no_of_days= $app->request ->post('no_of_days');
    $start_date= $app->request ->post('start_date');
    $end_date= $app->request ->post('end_date');
    $price= $app->request ->post('price');
       
    $db = new DbHandler();
    $res = $db->reservedBy($UserId,$pay_method,$pay_status,$r_id,$no_of_days,$start_date,$end_date,$price);
    if ($res ==1) {
        $response["error"] = false;
        $response["message"] = "successfully reserved";
    }
    else if($res == 10)  {
        $response["error"] = true;
        $response["message"] = "No bus selected";
    } 
    else  {
        $response["error"] = true;
        $response["message"] = "Reservation unsuccessful";
    } 
    
    
    echoRespnse(200, $response);

});




////view reservation history 
 $app->get('/view_reservations','authenticate', function() use ($app) {
    // check for required params
    global $UserId;

    $response = array();

    // reading post params

    $db = new DbHandler();
    $res = $db->showReservations($UserId);
    if ($res != NULL) {
        $response["error"] = false;
        $response['data'] = $res;
        $response['message'] ="successfully fetched";
    }
    else if($res == 0){
        // unknown error occurred
        $response['error'] = false;
        $response['message'] = "No data found";
        $response['data'] = NULL; 
    }
    else {
        // unknown error occurred
        $response['error'] = true;
        $response['message'] = "Error!";
        $response['data'] = NULL; 
    }
    echoRespnse(200, $response);
    });
    



    
/**
 * Verifying required params posted or not
 */
function verifyRequiredParams($required_fields) {
    $error = false;
    $error_fields = "";
    $request_params = array();
    $request_params = $_REQUEST;
    // Handling PUT request params
    if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
        $app = \Slim\Slim::getInstance();
        parse_str($app->request()->getBody(), $request_params);
    }
    foreach ($required_fields as $field) {
        if (!isset($request_params[$field]) || strlen(trim($request_params[$field])) <= 0) {
            $error = true;
            $error_fields .= $field . ', ';
        }
    }

    if ($error) {
        // Required field(s) are missing or empty
        // echo error json and stop the app
        $response = array();
        $app = \Slim\Slim::getInstance();
        $response["error"] = true;
        $response["message"] = 'Required field(s) ' . substr($error_fields, 0, -2) . ' is missing or empty';
        echoRespnse(400, $response);
        $app->stop();
    }
}

/**
 * Validating email address
 */
function validateEmail($Email) {
    $app = \Slim\Slim::getInstance();
    if (!filter_var($Email, FILTER_VALIDATE_EMAIL)) {
        $response["error"] = true;
        $response["message"] = 'Email address is not valid';
        echoRespnse(400, $response);
        $app->stop();
    }
}


/**
 * Echoing json response to client
 * @param String $status_code Http response code
 * @param Int $response Json response
 */
function echoRespnse($status_code, $response) {
    $app = \Slim\Slim::getInstance();
    // Http response code
    $app->status($status_code);

    // setting response content type to json
    $app->contentType('application/json');

    echo json_encode($response);
}

$app->run();
?>