package com.k8.finalproject4_kelompok8.utils.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookingResponseData implements Serializable {

    @SerializedName("booking_date")
    @Expose
    private String bookingDate;
    @SerializedName("booking_status")
    @Expose
    private Integer bookingStatus;
    @SerializedName("pay_method")
    @Expose
    private String payMethod;
    @SerializedName("pay_status")
    @Expose
    private Integer payStatus;
    @SerializedName("no_of_seats")
    @Expose
    private Integer noOfSeats;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("d.schedule_id")
    @Expose
    private Integer dScheduleId;
    @SerializedName("starting_point")
    @Expose
    private String startingPoint;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("schedule_date")
    @Expose
    private String scheduleDate;
    @SerializedName("departure_time")
    @Expose
    private String departureTime;
    @SerializedName("bus_name")
    @Expose
    private String busName;
    @SerializedName("bus_number")
    @Expose
    private Integer busNumber;

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Integer bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getdScheduleId() {
        return dScheduleId;
    }

    public void setdScheduleId(Integer dScheduleId) {
        this.dScheduleId = dScheduleId;
    }


    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public Integer getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(Integer busNumber) {
        this.busNumber = busNumber;
    }
}
