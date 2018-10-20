package com.rahil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityDetail {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("location_type")
    @Expose
    private String locationType;
    @SerializedName("woeid")
    @Expose
    private Long woeid;
    @SerializedName("latt_long")
    @Expose
    private String lattLong;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Long getWoeid() {
        return woeid;
    }

    public void setWoeid(Long woeid) {
        this.woeid = woeid;
    }

    public String getLattLong() {
        return lattLong;
    }

    public void setLattLong(String lattLong) {
        this.lattLong = lattLong;
    }

    @Override
    public String toString() {
        return "CityDetail{" +
                "title='" + title + '\'' +
                ", locationType='" + locationType + '\'' +
                ", woeid=" + woeid +
                ", lattLong='" + lattLong + '\'' +
                '}';
    }
}