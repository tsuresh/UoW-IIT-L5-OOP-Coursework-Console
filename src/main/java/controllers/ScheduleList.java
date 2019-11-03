package controllers;

import models.Schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScheduleList {
    private List<Schedule> scheduleList;

    public ScheduleList() {
        scheduleList = new ArrayList<>();
    }

    public ScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public boolean addSchedule(Schedule schedule){
        return false;
    }

    public boolean deleteSchedule(Schedule schedule){
        return false;
    }

    public boolean updateSchedule(Schedule oldSchedule, Schedule updatedSchedule){
        return false;
    }

    public List<Schedule> getSchedules() {
        return scheduleList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleList that = (ScheduleList) o;
        return Objects.equals(scheduleList, that.scheduleList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scheduleList);
    }

    @Override
    public String toString() {
        return "controllers.ScheduleList{" +
                "scheduleList=" + scheduleList +
                '}';
    }
}
