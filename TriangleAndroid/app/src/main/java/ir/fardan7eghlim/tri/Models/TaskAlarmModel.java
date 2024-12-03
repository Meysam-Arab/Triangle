package ir.fardan7eghlim.tri.Models;

import java.math.BigInteger;

/**
 * Created by Meysam on 5/1/2018.
 */

public class TaskAlarmModel
{

    public BigInteger getId() {
        return Id;
    }

    public void setId(BigInteger id) {
        Id = id;
    }


    public String getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        DayOfWeek = dayOfWeek;
    }

    public String getDayOfMonth() {
        return DayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        DayOfMonth = dayOfMonth;
    }


    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public BigInteger getTaskId() {
        return TaskId;
    }

    public void setTaskId(BigInteger taskId) {
        TaskId = taskId;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    private BigInteger Id;
    private BigInteger TaskId;
    private String DayOfWeek;//meysam: 1, 2, 3, 4, 5, 6, 7.
    private String DayOfMonth;//meysam: 1 - 31.
    private String Month;//meysam: 1 - 12.
    private String Year;//meysam: 2018, 2019, ...
    private String Time;//meysam - like 13:30



    public TaskAlarmModel()
    {
        Id = null;
        TaskId = null;
        DayOfWeek = null;
        DayOfMonth = null;
        Month = null;
        Year = null;
        Time = null;
    }

    public TaskAlarmModel(TaskAlarmModel taskAlarm)
    {
        Id = taskAlarm.getId();
        TaskId = taskAlarm.getTaskId();
        DayOfWeek = taskAlarm.getDayOfWeek();
        DayOfMonth = taskAlarm.getDayOfMonth();
        Month = taskAlarm.getMonth();
        Year = taskAlarm.getYear();
        Time = taskAlarm.getTime();
    }
}
