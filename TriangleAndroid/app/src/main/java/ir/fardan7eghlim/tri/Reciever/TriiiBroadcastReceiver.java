package ir.fardan7eghlim.tri.Reciever;

/**
 * Created by Meysam on 6/8/2018.
 */

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.text.format.Time;
import android.widget.RemoteViews;
import android.widget.Toast;
import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;

import io.saeid.oghat.PrayerTime;
import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.TaskAlarmModel;
import ir.fardan7eghlim.tri.Models.TaskModel;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Services.AdanService;
import ir.fardan7eghlim.tri.Views.Home.MainActivity;
import ir.fardan7eghlim.tri.Views.Home.utility.DateTools;
import ir.fardan7eghlim.tri.Views.Home.utility.Utilities;
import ir.fardan7eghlim.tri.Views.Task.AlarmActivity;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static ir.fardan7eghlim.tri.Models.Utility.Utility.enToFa;

public class TriiiBroadcastReceiver extends BroadcastReceiver {


    public static int NOTIFICATION_ID = 1250;

    public static int REQUEST_CODE_NORMAL = 0;
    public static int REQUEST_CODE_NEW_DAY = 1;
    public static int REQUEST_CODE_TASK = 2;
    public static int REQUEST_CODE_AZAN_SOBH = 3;
    public static int REQUEST_CODE_AZAN_ZOHR = 4;
    public static int REQUEST_CODE_AZAN_MAGHREB = 5;

    public static String REQUEST_TYPE_NORMAL = "normal";
    public static String REQUEST_TYPE_NEW_DAY = "newDay";
    public static String REQUEST_TYPE_TASK = "task";
    public static String REQUEST_TYPE_AZAN_SOBH = "azan_sobh";
    public static String REQUEST_TYPE_AZAN_ZOHR = "azan_zohr";
    public static String REQUEST_TYPE_AZAN_MAGHREB = "azan_maghreb";

//    private static HashMap<String,Boolean> myMap =new HashMap<String,Boolean>();


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, " 1در برودکست", Toast.LENGTH_SHORT).show();
//        Log.e("meysam", "1در برودکست");
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "triii_tag");
        //Acquire the lock
        wl.acquire();
//        Log.e("meysam", "2در برودکست");
        //You can do the processing here.
        SessionModel session = new SessionModel(context.getApplicationContext());
        if(!session.getBoolianItem(SessionModel.KEY_CLOSED,false))
        {

            if(intent.hasExtra("type"))
            {
                String type = intent.getStringExtra("type");
                Toast.makeText(context, " 3در برودکست", Toast.LENGTH_SHORT).show();
//                Log.e("meysam", "3در برودکست");
                //meysam - play specific azan...
                if((type.equals(TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_SOBH) &&
                        session.getBoolianItem(SessionModel.KEY_PLAY_AZAN_SOBH,true)) ||
                        (type.equals(TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_ZOHR) &&
                                session.getBoolianItem(SessionModel.KEY_PLAY_AZAN_ZOHR,true)) ||
                        (type.equals(TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_MAGHREB) &&
                                session.getBoolianItem(SessionModel.KEY_PLAY_AZAN_MAGHREB,true)))
                {

                        //meysam - added in 13970318
                        Intent i= new Intent(context.getApplicationContext(), AdanService.class);
                        context.getApplicationContext().startService(i);

                    Toast.makeText(context, "برودکست اذان دریافت شد", Toast.LENGTH_SHORT).show();

                }
                //meysam - show task alarm...
                if (intent.getStringExtra("type").equals(TriiiBroadcastReceiver.REQUEST_TYPE_TASK)) {
//                    Toast.makeText(context, "برودکست تسک اولیه دریافت شد", Toast.LENGTH_SHORT).show();

                    if(intent.hasExtra("taskId"))
                    {

                        BigInteger taskId = BigInteger.valueOf(Integer.valueOf(intent.getStringExtra("taskId")));
//                taskId = taskId.subtract(BigInteger.valueOf((long)100));
                        //meysam - for alarms of tasks in specific time
                        TaskModel task = new DatabaseHandler(context.getApplicationContext()).getTaskById(taskId);
                        String description = task.getDescription();
                        Boolean playAlarmSound = false;
                        if (task.getHasAlarmSound())
                            playAlarmSound = true;

                        session.saveItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, true);
                        Intent alarmIntent = new Intent(context, AlarmActivity.class);
                        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        alarmIntent.putExtra("description", description);
                        alarmIntent.putExtra("play_sound", playAlarmSound);
                        context.startActivity(alarmIntent);

                        Toast.makeText(context, "برودکست تسک دریافت شد", Toast.LENGTH_SHORT).show();
                    }

                }
                //meysam - reload notification and register new alarm...
                if (intent.getStringExtra("type").equals(TriiiBroadcastReceiver.REQUEST_TYPE_NEW_DAY)) {
                    showCalenderNotification(context);
                    initializeNewDayBroadcasts(context);
                    Toast.makeText(context, "برودکست روز جدید دریافت شد", Toast.LENGTH_SHORT).show();
                }
                //meysam - for normal...
                if (intent.getStringExtra("type").equals(TriiiBroadcastReceiver.REQUEST_TYPE_NORMAL)) {
                    Toast.makeText(context, "برودکست معمولی دریافت شد", Toast.LENGTH_SHORT).show();

                }

            }
        }

        Toast.makeText(context, " 4در برودکست", Toast.LENGTH_SHORT).show();

        //Release the lock
        wl.release();
    }

    public static void cancelBroadcast(Context context, String type )
    {
        Intent intent = new Intent(context, TriiiBroadcastReceiver.class);
        intent.putExtra("type", type);

        PendingIntent sender = PendingIntent.getBroadcast(context, getRequestCodeWRTType(type), intent,  0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public static void cancelTaskBroadcast(Context context, BigInteger task_id )
    {
        Intent intent = new Intent(context, TriiiBroadcastReceiver.class);
        intent.putExtra("type", TriiiBroadcastReceiver.REQUEST_TYPE_TASK);
        intent.putExtra("taskId", task_id.toString());
        PendingIntent sender = PendingIntent.getBroadcast(context, Integer.valueOf(task_id.add(BigInteger.valueOf((long)100)).toString()), intent,  0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
    //meysam - time is like 13970325 09:07 - types are like top of this page
    public static void setTaskBroadcast(Context context, String datetime, BigInteger task_id){
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, TriiiBroadcastReceiver.class);
        intent.putExtra("type", TriiiBroadcastReceiver.REQUEST_TYPE_TASK);
        intent.putExtra("taskId", task_id.toString());
        PendingIntent pi = PendingIntent.getBroadcast(context, Integer.valueOf(task_id.add(BigInteger.valueOf((long)100)).toString()), intent,  0);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        try {
            cal.setTime(sdf.parse(datetime));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }

        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
    }


    //meysam - time is like 13970325 09:07 - types are like top of this page
    public static void setBroadcast(Context context, String datetime, String type){
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, TriiiBroadcastReceiver.class);
        intent.putExtra("type", type);
        PendingIntent pi = PendingIntent.getBroadcast(context, getRequestCodeWRTType(type), intent,  0);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
        try {
            cal.setTime(sdf.parse(datetime));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }

        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
    }

    private static int getRequestCodeWRTType(String type)
    {
        if(type.equals(REQUEST_TYPE_NEW_DAY))
            return REQUEST_CODE_NEW_DAY;
        else if(type.equals(REQUEST_TYPE_AZAN_SOBH))
            return REQUEST_CODE_AZAN_SOBH;
        else if(type.equals(REQUEST_TYPE_AZAN_ZOHR))
            return REQUEST_CODE_AZAN_ZOHR;
        else if(type.equals(REQUEST_TYPE_AZAN_MAGHREB))
            return REQUEST_CODE_AZAN_MAGHREB;
        else if(type.equals(REQUEST_TYPE_TASK))
            return REQUEST_CODE_TASK;
        else
            return REQUEST_CODE_NORMAL;
    }

    public static void initializeBroadcasts(Context context){
        cancelBroadcast(context,TriiiBroadcastReceiver.REQUEST_TYPE_NORMAL);
//        myMap.remove(TriiiBroadcastReceiver.REQUEST_TYPE_NORMAL);

        initializeAzanBroadcasts(context);

        initializeNewDayBroadcasts(context);

        initializeTasksBroadcasts(context);

    }

    public static void initializeNewDayBroadcasts(Context context){

        //meysam - set for new day
        cancelBroadcast(context,TriiiBroadcastReceiver.REQUEST_TYPE_NEW_DAY);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(0));
        cal.set(Calendar.MINUTE,Integer.valueOf(0));
        String dateTime = new SimpleDateFormat("yyyyMMdd HH:mm").format(cal.getTime());

        setBroadcast(context,dateTime,TriiiBroadcastReceiver.REQUEST_TYPE_NEW_DAY);
        ////////////////////////////////////////
    }

    public static void initializeAzanBroadcasts(Context context){


        //meysam - set azan broadcasts
        Calendar cal;
        String dateTime;
        SessionModel session = new SessionModel(context);
        double latitude = 35.6891975;
        double longitude = 51.388973599999986;
        String temp_latitude = session.getStringItem(SessionModel.KEY_CITY_LATITUDE);
        if (temp_latitude != null) latitude = Double.parseDouble(temp_latitude);
        temp_latitude = session.getStringItem(SessionModel.KEY_CITY_LONGITUDE);
        if (temp_latitude != null) longitude = Double.parseDouble(temp_latitude);

        Double timezone = (TimeZone.getTimeZone(Time.getCurrentTimezone()).getOffset(System.currentTimeMillis())) / (1000.0 * 60.0 * 60.0);
        int year_miladi = (Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime())));
        int month_miladi = (Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime())));
        int day_miladi = (Integer.parseInt(new SimpleDateFormat("d").format(Calendar.getInstance().getTime())));

        PrayerTime pt = PrayerTime.getInstance()
                .setDate(year_miladi, month_miladi, day_miladi) // in gregorian
                .setLatLong(latitude, longitude)
                .setTimeZone(timezone)
                .calculate();

        cancelBroadcast(context,TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_SOBH);
        cal = Calendar.getInstance();
        String[] temp = pt.getSobhAzan().split(":");
        cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(temp[0]));
        cal.set(Calendar.MINUTE,Integer.valueOf(temp[1]));
        if(cal.getTime().getTime() >= Calendar.getInstance().getTime().getTime())
        {
            dateTime = new SimpleDateFormat("yyyyMMdd HH:mm").format(cal.getTime());
            setBroadcast(context,dateTime,TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_SOBH);
        }


        cancelBroadcast(context,TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_ZOHR);
        cal = Calendar.getInstance();
        temp = pt.getZohrAzan().split(":");
        cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(temp[0]));
        cal.set(Calendar.MINUTE,Integer.valueOf(temp[1]));
        if(cal.getTime().getTime() >= Calendar.getInstance().getTime().getTime())
        {
            dateTime = new SimpleDateFormat("yyyyMMdd HH:mm").format(cal.getTime());
            setBroadcast(context,dateTime,TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_ZOHR);
        }


        cancelBroadcast(context,TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_MAGHREB);
        cal = Calendar.getInstance();
        temp = pt.getMaghrebAzan().split(":");
        cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(temp[0]));
        cal.set(Calendar.MINUTE,Integer.valueOf(temp[1]));
        if(cal.getTime().getTime() >= Calendar.getInstance().getTime().getTime())
        {
            dateTime = new SimpleDateFormat("yyyyMMdd HH:mm").format(cal.getTime());
            setBroadcast(context,dateTime,TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_MAGHREB);
        }

        //////////////////////////////////////////////////////
    }

    public static void initializeTasksBroadcasts(Context context){

        //meysam - set tasks broadcasts
        PersianCalendar pCal = new PersianCalendar();
        String year = String.valueOf(pCal.getPersianYear());
        String month = String.valueOf(pCal.getPersianMonth());
        String day_of_month = String.valueOf(pCal.getPersianDay());
        String day_of_week = String.valueOf(Utility.getDayNumberOfWeek( pCal.getPersianWeekDayName()));
        ArrayList<TaskAlarmModel> taskAlarms = new DatabaseHandler(context.getApplicationContext()).getTaskAlarmsByDate(year, month, day_of_week, day_of_month);

        if (taskAlarms.size() > 0)
        {
            for(int i = 0; i < taskAlarms.size(); i++)
            {
                TriiiBroadcastReceiver.cancelTaskBroadcast(context,taskAlarms.get(i).getTaskId());


                Calendar cal = Calendar.getInstance();
                String[] temp = taskAlarms.get(i).getTime().split(":");
                cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(temp[0]));
                cal.set(Calendar.MINUTE,Integer.valueOf(temp[1]));
                cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(temp[0]));
                cal.set(Calendar.MINUTE,Integer.valueOf(temp[1]));
                if(cal.getTime().getTime() >= Calendar.getInstance().getTime().getTime())
                {
                    String dateTime = new SimpleDateFormat("yyyyMMdd HH:mm").format(cal.getTime());
                    setTaskBroadcast(context,dateTime,taskAlarms.get(i).getTaskId());
                }

            }
        }

        //////////////////////////////////////////////////////
    }

    public static void hideNotification(Context context) {
        NotificationManager mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNM.cancel(TriiiBroadcastReceiver.NOTIFICATION_ID);
        mNM.cancelAll();
        mNM = null;
    }

    public static void showCalenderNotification(Context context) {

        SessionModel session = new SessionModel(context.getApplicationContext());
        if (session.getBoolianItem(SessionModel.KEY_SHOW_NOTIFICATION, true)) {
            Integer currentDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));
            session.saveItem(SessionModel.KEY_NOTIFICATION_LAST_CHECKED_DATE_TIME, currentDate);
            //meysam - regresh notification calender for new day...
            NotificationManager mNM;
            //date
            String solarCalendarc = Utilities.getCurrentShamsidate();
            String name_of_week = new SimpleDateFormat("EEE").format(Calendar.getInstance().getTime());
            int year_miladi = (Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime())));
            int month_miladi = (Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime())));
            int day_miladi = (Integer.parseInt(new SimpleDateFormat("d").format(Calendar.getInstance().getTime())));
            //shamsi calendar
            String temp[] = solarCalendarc.split("/");
            String day_shamsi = temp[2];
            String shamsiDate = DateTools.convertWeeksNameMiladi2Shamsi(name_of_week) + " " + enToFa(DateTools.fixZeroBeforeTime(day_shamsi)) + " " + DateTools.convertNum2StrShamsiMonth(Integer.parseInt(temp[1])) + " " + enToFa(temp[0]);

            //miladi calendar
            String miladiDate =  year_miladi+ "." + month_miladi + "." +day_miladi+ " (" + DateTools.convertNum2StrMiladiMonthEng(month_miladi)+")";

            //ghamari calendar
            Chronology iso = ISOChronology.getInstanceUTC();
            Chronology hijri = IslamicChronology.getInstanceUTC();
            LocalDate todayIso = new LocalDate(year_miladi, month_miladi, day_miladi, iso);
            LocalDate todayHijri = new LocalDate(todayIso.toDateTimeAtStartOfDay(), hijri);
            todayHijri=todayHijri.plusDays(session.getIntegerItem(SessionModel.KEY_CALENDER_GHAMARI_CORRECTION,0));
            todayHijri=DateTools.fixGhamariDate(todayHijri,temp[0]+"/"+temp[1]+"/"+temp[2]);
            temp = todayHijri.toString().split("-");
            String ghmariDate = enToFa(temp[0]) + "/" + enToFa(DateTools.fixZeroBeforeTime((temp[1])) + "/" + enToFa(DateTools.fixZeroBeforeTime(temp[2]))+ " (" + DateTools.convertNum2StrGhamariMonth(Integer.parseInt(temp[1])))+")";

            //oghat
            double latitude = 35.6891975;
            double longitude = 51.388973599999986;
            String temp_latitude = session.getStringItem(SessionModel.KEY_CITY_LATITUDE);
            if (temp_latitude != null) latitude = Double.parseDouble(temp_latitude);
            temp_latitude = session.getStringItem(SessionModel.KEY_CITY_LONGITUDE);
            if (temp_latitude != null) longitude = Double.parseDouble(temp_latitude);

            Double timezone = (TimeZone.getTimeZone(Time.getCurrentTimezone()).getOffset(System.currentTimeMillis())) / (1000.0 * 60.0 * 60.0);
            PrayerTime prayerTime = PrayerTime.getInstance()
                    .setDate(year_miladi, month_miladi, day_miladi) // in gregorian
                    .setLatLong(latitude, longitude)
                    .setTimeZone(timezone)
                    .calculate();

            //////////////////////////////////
            RemoteViews expandedView = new RemoteViews(context.getPackageName(), R.layout.custom_notification_expanded);
            expandedView.setTextViewText(R.id.content_title, shamsiDate);
            expandedView.setTextViewText(R.id.content_text_left, miladiDate);
            expandedView.setTextViewText(R.id.content_text_right, ghmariDate);
            expandedView.setTextViewText(R.id.tv_date, enToFa(DateTools.fixZeroBeforeTime(day_shamsi)));

            expandedView.setTextViewText(R.id.txt_pray_sobh, enToFa(DateTools.fixZeroBeforeTime(prayerTime.getSobhAzan())));
            expandedView.setTextViewText(R.id.txt_pray_toloo, enToFa(DateTools.fixZeroBeforeTime(prayerTime.getToloo())));
            expandedView.setTextViewText(R.id.txt_pray_zohr, enToFa(DateTools.fixZeroBeforeTime(prayerTime.getZohrAzan())));
            expandedView.setTextViewText(R.id.txt_pray_ghoroob, enToFa(DateTools.fixZeroBeforeTime(prayerTime.getGhoroob())));
            expandedView.setTextViewText(R.id.txt_pray_maghreb, enToFa(DateTools.fixZeroBeforeTime(prayerTime.getMaghrebAzan())));
            expandedView.setTextViewText(R.id.txt_pray_nimeshab, enToFa(DateTools.fixZeroBeforeTime(prayerTime.getMidnight())));


            RemoteViews collapsedView = new RemoteViews(context.getPackageName(), R.layout.custom_notification_collapsed);
            collapsedView.setTextViewText(R.id.content_title, shamsiDate);
            collapsedView.setTextViewText(R.id.content_text_left, miladiDate);
            collapsedView.setTextViewText(R.id.content_text_right, ghmariDate);
            collapsedView.setTextViewText(R.id.tv_date, enToFa(DateTools.fixZeroBeforeTime(day_shamsi)));


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Utility.NOTIFICATION_CHANNEL_ID)
                    // these are the three things a NotificationCompat.Builder object requires at a minimum
                    .setSmallIcon(smallDateIcon(Integer.parseInt(day_shamsi),context))
                    // notification will be dismissed when tapped
                    .setAutoCancel(false)
                    .setOngoing(true)
                    // tapping notification will open MainActivity
                    .setContentIntent(PendingIntent.getActivity(context, TriiiBroadcastReceiver.NOTIFICATION_ID, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                    // setting the custom collapsed and expanded views
                    .setCustomContentView(collapsedView);

            if (session.getBoolianItem(SessionModel.KEY_SHOW_EXTENDED_NOTIFICATION, true)) {

                builder.setCustomBigContentView(expandedView);

            }

            Notification notification = builder.build();
            mNM = (android.app.NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            mNM.notify(TriiiBroadcastReceiver.NOTIFICATION_ID, notification);

            session.saveItem(SessionModel.KEY_NOTIFICATION_LAST_CHECKED_DATE_TIME, currentDate);
        }



    }

    private static int smallDateIcon(int i, Context context) {
        return context.getResources().getIdentifier("ic_date_" + i, "drawable", context.getPackageName());
    }

    public static void cancelAllBroadcasts(Context context) {

//        myMap.clear();

        cancelBroadcast(context,TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_SOBH);
        cancelBroadcast(context,TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_ZOHR);
        cancelBroadcast(context,TriiiBroadcastReceiver.REQUEST_TYPE_AZAN_MAGHREB);
        cancelBroadcast(context,TriiiBroadcastReceiver.REQUEST_TYPE_NEW_DAY);
        cancelBroadcast(context,TriiiBroadcastReceiver.REQUEST_TYPE_NORMAL);


        //meysam - set tasks broadcasts
        PersianCalendar pCal = new PersianCalendar();
        String year = String.valueOf(pCal.getPersianYear());
        String month = String.valueOf(pCal.getPersianMonth());
        String day_of_month = String.valueOf(pCal.getPersianDay());
        String day_of_week = String.valueOf(Utility.getDayNumberOfWeek( pCal.getPersianWeekDayName()));
        ArrayList<TaskAlarmModel> taskAlarms = new DatabaseHandler(context.getApplicationContext()).getTaskAlarmsByDate(year, month, day_of_week, day_of_month);

        if (taskAlarms.size() > 0)
        {
            for(int i = 0; i < taskAlarms.size(); i++)
            {
                TriiiBroadcastReceiver.cancelTaskBroadcast(context,taskAlarms.get(i).getTaskId());
            }
        }

    }

//
//    /** code to post/handler request for permission */
//    public final static int REQUEST_CODE = -1010101; /*(see edit II)*/
//
//    public void checkDrawOverlayPermission() {
//        /** check if we already  have permission to draw over other apps */
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.canDrawOverlays(Context)) {
//                /** if not construct intent to request permission */
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                        Uri.parse("package:" + getPackageName()));
//                /** request permission via start activity for result */
//                startActivityForResult(intent, REQUEST_CODE);
//            }
//        }
//    }

}
