package com.k8.finalproject4_kelompok8.api;

import com.k8.finalproject4_kelompok8.utils.responses.BookingResponse;
import com.k8.finalproject4_kelompok8.utils.responses.LoginResponse;
import com.k8.finalproject4_kelompok8.utils.responses.RegisterResponse;
import com.k8.finalproject4_kelompok8.utils.responses.ReserveResponse;
import com.k8.finalproject4_kelompok8.utils.responses.ScheduleResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {
    @FormUrlEncoded
    @POST("/bus_ticket_booking/myApi/v1/login")
    Call<LoginResponse> login(@Field("email") String email,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("/bus_ticket_booking/myApi/v1/register")
    Call<RegisterResponse> register(@Field("full_name") String name,
                                            @Field("email") String email,
                                            @Field("password") String password,
                                            @Field("contact_no") String phoneNum);


    @GET("/bus_ticket_booking/myApi/v1/get-all-schedule")
    Call<ScheduleResponse> get_all_schedule(@Header("api_key") String apiKey);



    @FormUrlEncoded
    @POST("/bus_ticket_booking/myApi/v1/bookings")
    Call<BookingResponse> book(@Header("api_key") String apiKey,
                               @Field("schedule_id") Integer schedule_id,
                               @Field("no_of_seats") Integer no_of_seats,
                               @Field("pay_method") String pay_method,
                               @Field("pay_status") Integer pay_status,
                               @Field("price") Integer price);


    @GET("/bus_ticket_booking/myApi/v1/view_bookings")
    Call<BookingResponse> get_bookings(@Header("api_key") String apiKey);


    @GET("/bus_ticket_booking/myApi/v1/get-bus-forReservation")
    Call<ScheduleResponse> get_bus_forReservation(@Header("api_key") String apiKey);


    @FormUrlEncoded
    @POST("/bus_ticket_booking/myApi/v1/reserve")
    Call<ReserveResponse> reserve(@Header("api_key") String apiKey,
                                  @Field("r_id") Integer r_id,
                                  @Field("no_of_days") Integer no_of_days,
                                  @Field("start_date") String start_date,
                                  @Field("end_date") String end_date,
                                  @Field("pay_method") String pay_method,
                                  @Field("pay_status") Integer pay_status,
                                  @Field("price") Integer price);


    @GET("/bus_ticket_booking/myApi/v1/view_reservations")
    Call<ReserveResponse> view_reservations(@Header("api_key") String apiKey);



    @POST("/bus_ticket_booking/myApi/v1/edit_profile")
    @Multipart
    Call<LoginResponse> edit_profile(
            @Header("api_key") String apiKey,
            @Part("email") RequestBody email,
            @Part("full_name") RequestBody name,
            @Part("contact_no") RequestBody phoneNum,
            @Part("oldProfileLink") RequestBody oldProfileLink,
            @Part MultipartBody.Part file
    );

    @FormUrlEncoded
    @POST("/bus_ticket_booking/myApi/v1/edit_password")
    Call<LoginResponse> edit_password(@Header("api_key") String apiKey,
                                      @Field("password") String password);

}
