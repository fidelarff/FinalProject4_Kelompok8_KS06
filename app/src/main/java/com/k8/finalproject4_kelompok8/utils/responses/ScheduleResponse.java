package com.k8.finalproject4_kelompok8.utils.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleResponse {
    @SerializedName("datas")
    @Expose
    private List<ScheduleResponseData> datas = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<ScheduleResponseData> getDatas() {
        return datas;
    }

    public void setDatas(List<ScheduleResponseData> datas) {
        this.datas = datas;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
