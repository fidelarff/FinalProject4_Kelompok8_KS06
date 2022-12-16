package com.k8.finalproject4_kelompok8.utils.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReserveHistoryData implements Serializable {
    @SerializedName("reserved_date")
    @Expose
    private String reservedDate;
    @SerializedName("reserved_status")
    @Expose
    private Integer reservedStatus;
    @SerializedName("pay_method")
    @Expose
    private String payMethod;
    @SerializedName("pay_status")
    @Expose
    private Integer payStatus;
    @SerializedName("no_of_days")
    @Expose
    private Integer noOfDays;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("d.r_id")
    @Expose
    private Integer dRId;
    @SerializedName("starting_point")
    @Expose
    private String startingPoint;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("bus_name")
    @Expose
    private String busName;
    @SerializedName("bus_number")
    @Expose
    private Integer busNumber;

    public String getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(String reservedDate) {
        this.reservedDate = reservedDate;
    }

    public Integer getReservedStatus() {
        return reservedStatus;
    }

    public void setReservedStatus(Integer reservedStatus) {
        this.reservedStatus = reservedStatus;
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

    public Integer getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Integer noOfDays) {
        this.noOfDays = noOfDays;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getdRId() {
        return dRId;
    }

    public void setdRId(Integer dRId) {
        this.dRId = dRId;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
