package ir.fardan7eghlim.tri.Models;

import org.joda.time.Minutes;

import java.math.BigInteger;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

/**
 * Created by Meysam on 5/1/2018.
 */

public class TaskModel
{

    public static Integer TYPE_NORMAL = 0;
    public static Integer TYPE_DAILY = 1;
    public static Integer TYPE_WEEKLY = 2;
    public static Integer TYPE_MONTHLY = 3;


    public BigInteger getId() {
        return Id;
    }

    public void setId(BigInteger id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public ArrayList<String> getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(ArrayList<String> dayOfWeek) {
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

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public ArrayList<TaskAlarmModel> getTaskAlarms() {
        return TaskAlarms;
    }

    public void setTaskAlarms(ArrayList<TaskAlarmModel> taskAlarms) {
        TaskAlarms = taskAlarms;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public Boolean getHasAlarmSound() {
        return HasAlarmSound;
    }

    public void setHasAlarmSound(Boolean hasAlarmSound) {
        HasAlarmSound = hasAlarmSound;
    }


    public ArrayList<String> getTaskAlarmsMinutes() {
        return TaskAlarmsMinutes;
    }

    public void setTaskAlarmsMinutes(ArrayList<String> taskAlarmsMinutes) {
        TaskAlarmsMinutes = taskAlarmsMinutes;
    }


    private BigInteger Id;
    private String Description;
    private Integer Type;//meysam: 1, 2, 3, 4.
    private ArrayList<String> DayOfWeek;//meysam: 1, 2, 3, 4, 5, 6, 7.
    private String DayOfMonth;//meysam: 1 - 31.
    private String Month;//meysam: 1 - 12.
    private String Year;//meysam: 2018, 2019, ...
    private String StartTime;//meysam - like 13:30
    private String EndTime;//meysam - like 13:30
    private String StartDate;
    private String EndDate;
    private Boolean HasAlarmSound;
    private ArrayList<TaskAlarmModel> TaskAlarms;
    private ArrayList<String> TaskAlarmsMinutes;



    public TaskModel()
    {
        Id = null;
        Description = null;
        Type = null;
        DayOfWeek = null;
        DayOfMonth = null;
        Month = null;
        Year = null;
        StartTime = null;
        EndTime = null;
        StartDate = null;
        EndDate = null;
        TaskAlarms = new ArrayList<>();
        TaskAlarmsMinutes = new ArrayList<>();

    }
    
//    public static Integer getMinuteDifferenceWithAlarm(TaskModel task)
//    {
//        ArrayList<TaskAlarmModel> taskAlarms = task.getTaskAlarms();
//        Integer result = null;
//        if(taskAlarms.size() == 0)
//            return  result;
//        if(task.getType().equals(TaskModel.TYPE_NORMAL))
//        {
//            //meysam - for normal tasks
////            PersianCalendar pc1 = new PersianCalendar();
////            pc1.set(Integer.valueOf(task.getYear()),Integer.valueOf(task.getMonth()),Integer.valueOf(task.getDayOfMonth()));
////            PersianCalendar pc2 = new PersianCalendar();
////            pc2.set(Integer.valueOf(taskAlarms.get(0).getYear()),Integer.valueOf(taskAlarms.get(0).getMonth()),Integer.valueOf(taskAlarms.get(0).getDayOfMonth()));
//
//            String startTime = task.getYear()+Utility.addZeroFirst(task.getMonth(),2)+Utility.addZeroFirst(task.getDayOfMonth(),2)+" "+task.getStartTime();
//            String endTime = taskAlarms.get(0).getYear()+Utility.addZeroFirst(taskAlarms.get(0).getMonth(),2)+Utility.addZeroFirst(taskAlarms.get(0).getDayOfMonth(),2)+" "+taskAlarms.get(0).getTime();
//            result = Utility.getTimeDifferenceInMinutes(startTime, endTime);
//        }
//        if(task.getType().equals(TaskModel.TYPE_DAILY))
//        {
//            //meysam - for daily tasks
//            Integer startTotalTime = (Integer.valueOf(task.getStartTime().substring(0,2))*60 + Integer.valueOf(task.getStartTime().substring(3,5)));
//            Integer endTotalTime = (Integer.valueOf(taskAlarms.get(0).getTime().substring(0,2))*60 + Integer.valueOf(taskAlarms.get(0).getTime().substring(3,5)));
//            result = endTotalTime - startTotalTime;
//
//        }
//        if(task.getType().equals(TaskModel.TYPE_MONTHLY))
//        {
//            //meysam - for monthly tasks
//            if(task.getDayOfMonth().equals(taskAlarms.get(0).getDayOfMonth()))
//            {
//                Integer startTotalTime = (Integer.valueOf(task.getStartTime().substring(0,2))*60 + Integer.valueOf(task.getStartTime().substring(3,5)));
//                Integer endTotalTime = (Integer.valueOf(taskAlarms.get(0).getTime().substring(0,2))*60 + Integer.valueOf(taskAlarms.get(0).getTime().substring(3,5)));
//                result = endTotalTime - startTotalTime;
//            }
//            else
//            {
//                if(Integer.valueOf(task.getDayOfMonth()) - Integer.valueOf(taskAlarms.get(0).getDayOfMonth()) >= 2)
//                {
//                    result = -2880;
//                }
//                else
//                {
//                    if(task.getStartTime().equals(taskAlarms.get(0).getTime()))
//                        result = -1440;
//                    else
//                        result = -720;
//                }
//            }
//        }
//        if(task.getType().equals(TaskModel.TYPE_WEEKLY))
//        {
//            //meysam - for Weekly
//            if(task.getDayOfWeek().get(0).equals(taskAlarms.get(0).getDayOfWeek()))
//            {
//                Integer startTotalTime = (Integer.valueOf(task.getStartTime().substring(0,2))*60 + Integer.valueOf(task.getStartTime().substring(3,5)));
//                Integer endTotalTime = (Integer.valueOf(taskAlarms.get(0).getTime().substring(0,2))*60 + Integer.valueOf(taskAlarms.get(0).getTime().substring(3,5)));
//                result = endTotalTime - startTotalTime;
//            }
//            else
//            {
//                //meysam - almost like monthly...
//            }
//        }
//
//        return result;
//    }
}
