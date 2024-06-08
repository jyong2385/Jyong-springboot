package com.jyong.springboot.entity;

public class Launcher {

    public Long startTime;

    public Long endTime;

    public String appName;


    @Override
    public String toString() {
        return "Launcher{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", appName='" + appName + '\'' +
                '}';
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
