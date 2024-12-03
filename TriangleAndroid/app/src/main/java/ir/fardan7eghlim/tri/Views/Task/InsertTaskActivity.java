package ir.fardan7eghlim.tri.Views.Task;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.rebound.ui.Util;

import net.steamcrafted.lineartimepicker.dialog.LinearTimePickerDialog;

import org.joda.time.DateTime;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.TaskAlarmModel;
import ir.fardan7eghlim.tri.Models.TaskModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseActivity;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Reciever.TriiiBroadcastReceiver;
import ir.fardan7eghlim.tri.Utils.DialogPopUp;
import ir.fardan7eghlim.tri.Views.Home.MainActivity;
import ir.fardan7eghlim.tri.Views.Home.utility.Utilities;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class InsertTaskActivity extends BaseActivity {
    private Spinner spr_type_instask;
//    private Spinner spr_weekname_instask;
    private Spinner spr_monthday_instask;
    private Spinner spr_alert_instask;
    private TextView txt_date_intask;
    private TextView txt_time_intask;
    private TextView txt_date2_intask;
    private TextView txt_time2_intask;
    private EditText txt_description_intask;
    private Button btn_add_instask;
    private Button btn_addAndQuit_instask;
    private LinearLayout ll_date_intask;
    private LinearLayout ll_date2_intask;
    private LinearLayout ll_weekname_instask;
    private LinearLayout ll_monthday_instask;
    private PersianDatePickerDialog PersianDatePickerDialog1;
    private PersianDatePickerDialog PersianDatePickerDialog2;
    private CheckBox chBx_alarm_instask;
    private TextView txt_date_title_intask;
    private CheckBox cbk_week_1_instask;
    private CheckBox cbk_week_2_instask;
    private CheckBox cbk_week_3_instask;
    private CheckBox cbk_week_4_instask;
    private CheckBox cbk_week_5_instask;
    private CheckBox cbk_week_6_instask;
    private CheckBox cbk_week_0_instask;
    private BigInteger taskIdForEdit;
    private TaskModel taskForEdit;

    private Boolean isInCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_task);

        isInCreate = true;

        //sets
        spr_type_instask=findViewById(R.id.spr_type_instask);
//        spr_weekname_instask=findViewById(R.id.spr_weekname_instask);
        spr_monthday_instask=findViewById(R.id.spr_monthday_instask);
        spr_alert_instask=findViewById(R.id.spr_alert_instask);
        txt_date_intask=findViewById(R.id.txt_date_intask);
        txt_time_intask=findViewById(R.id.txt_time_intask);
        txt_date2_intask=findViewById(R.id.txt_date2_intask);
        txt_time2_intask=findViewById(R.id.txt_time2_intask);
        txt_description_intask=findViewById(R.id.txt_description_intask);
        btn_add_instask=findViewById(R.id.btn_add_instask);
        btn_addAndQuit_instask=findViewById(R.id.btn_addAndQuit_instask);
        ll_date_intask=findViewById(R.id.ll_date_intask);
        ll_date2_intask=findViewById(R.id.ll_date2_intask);
        ll_weekname_instask=findViewById(R.id.ll_weekname_instask);
        ll_monthday_instask=findViewById(R.id.ll_monthday_instask);
        chBx_alarm_instask=findViewById(R.id.chBx_alarm_instask);
        txt_date_title_intask=findViewById(R.id.txt_date_title_intask);
        cbk_week_1_instask=findViewById(R.id.cbk_week_1_instask);
        cbk_week_2_instask=findViewById(R.id.cbk_week_2_instask);
        cbk_week_3_instask=findViewById(R.id.cbk_week_3_instask);
        cbk_week_4_instask=findViewById(R.id.cbk_week_4_instask);
        cbk_week_5_instask=findViewById(R.id.cbk_week_5_instask);
        cbk_week_6_instask=findViewById(R.id.cbk_week_6_instask);
        cbk_week_0_instask=findViewById(R.id.cbk_week_0_instask);
        //fill date and time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        txt_time_intask.setText(sdf.format(new Date()));
        txt_time2_intask.setText(sdf.format(new Date()));

        //fill spinners
        addItemsOnSpinner_types();
        addItemsOnSpinner_alert(TaskModel.TYPE_NORMAL);
        addItemsOnSpinner_monthday();



//        addItemsOnSpinner_weeknames();
        //type
        spr_type_instask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0)
                {
                    addItemsOnSpinner_alert(TaskModel.TYPE_NORMAL);
                    txt_date_title_intask.setText("در تاریخ");
                    ll_date2_intask.setVisibility(View.GONE);
                    ll_weekname_instask.setVisibility(View.GONE);
                    ll_monthday_instask.setVisibility(View.GONE);
                } else if (position == 1) {
                    addItemsOnSpinner_alert(TaskModel.TYPE_DAILY);
                    txt_date_title_intask.setText("از تاریخ");
                    ll_date2_intask.setVisibility(View.VISIBLE);
                    ll_weekname_instask.setVisibility(View.GONE);
                    ll_monthday_instask.setVisibility(View.GONE);

                } else if (position == 2) {
                    addItemsOnSpinner_alert(TaskModel.TYPE_WEEKLY);
                    txt_date_title_intask.setText("از تاریخ");
                    ll_date2_intask.setVisibility(View.VISIBLE);
                    ll_weekname_instask.setVisibility(View.VISIBLE);
                    ll_monthday_instask.setVisibility(View.GONE);

                } else if (position == 3) {
                    addItemsOnSpinner_alert(TaskModel.TYPE_MONTHLY);
                    txt_date_title_intask.setText("از تاریخ");
                    ll_date2_intask.setVisibility(View.VISIBLE);
                    ll_weekname_instask.setVisibility(View.GONE);
                    ll_monthday_instask.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        spr_alert_instask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {
                   chBx_alarm_instask.setVisibility(View.GONE);
                }else{
                    chBx_alarm_instask.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        //date picker
        txt_date_intask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianCalendar initDate = new PersianCalendar();
                String[] temp=txt_date_intask.getText().toString().split("/");
                initDate.setPersianDate(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
                PersianDatePickerDialog1 = new PersianDatePickerDialog(InsertTaskActivity.this)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMaxYear(1450)
                        .setMinYear(1350)
                        .setInitDate(initDate)
                        .setActionTextColor(Color.GRAY)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
//                                Toast.makeText(getApplicationContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                                txt_date_intask.setText(Utility.enToFa(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay()));
                                //compare

                                String endTime = Utility.fixTimeFormat(Utility.faToEn(txt_time2_intask.getText().toString()),":",":");
                                String startTime = Utility.fixTimeFormat(Utility.faToEn(txt_time_intask.getText().toString()),":",":");

                                String endDate = Utility.fixDateFormat_01(Utility.faToEn(txt_date2_intask.getText().toString()));
                                String startDate = Utility.fixDateFormat_01(Utility.faToEn(txt_date_intask.getText().toString()));

                                if(compareTwoDateOrTime(startDate,endDate,"/")>0){
                                    txt_date2_intask.setText(startDate);
                                }
                                if(compareTwoDateOrTime(startDate,endDate,"/")==0 && compareTwoDateOrTime(startTime,endTime,":")>0){
                                    txt_time2_intask.setText(startTime);
                                }
                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                PersianDatePickerDialog1.show();
            }
        });
        txt_date2_intask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersianCalendar initDate = new PersianCalendar();
                String[] temp=txt_date2_intask.getText().toString().split("/");
                initDate.setPersianDate(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
                PersianDatePickerDialog2 = new PersianDatePickerDialog(InsertTaskActivity.this)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMaxYear(1450)
                        .setMinYear(1350)
                        .setInitDate(initDate)
                        .setActionTextColor(Color.GRAY)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                txt_date2_intask.setText(Utility.enToFa(persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth()) + "/" + persianCalendar.getPersianDay());
                                //compare
                                String endTime = Utility.fixTimeFormat(Utility.faToEn(txt_time2_intask.getText().toString()),":",":");
                                String startTime = Utility.fixTimeFormat(Utility.faToEn(txt_time_intask.getText().toString()),":",":");

                                String endDate = Utility.fixDateFormat_01(Utility.faToEn(txt_date2_intask.getText().toString()));
                                String startDate = Utility.fixDateFormat_01(Utility.faToEn(txt_date_intask.getText().toString()));

                                if(compareTwoDateOrTime(startDate,endDate,"/")>0){
                                    txt_date_intask.setText(endDate);
                                }
                                if(compareTwoDateOrTime(startDate,endDate,"/")==0 && compareTwoDateOrTime(startTime,endTime,":")>0){
                                    txt_time2_intask.setText(startTime);
                                }
                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                PersianDatePickerDialog2.show();
            }
        });
        //time picker
        txt_time_intask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearTimePickerDialog dialog = LinearTimePickerDialog.Builder.with(InsertTaskActivity.this)
                        .setLineColor(Color.rgb(69, 101, 141))
                        .setTextColor(Color.parseColor("#abc0da"))
                        .setDialogBackgroundColor(Color.parseColor("#83c0f9"))
                        .setPickerBackgroundColor(Color.parseColor("#1a3b64"))
                        .setShowTutorial(session.getBoolianItem(SessionModel.KEY_IS_TIME_PICKER_TUTORIAL_SHOWED,true)?true:false)
                        .setButtonCallback(new LinearTimePickerDialog.ButtonCallback() {
                            @Override
                            public void onPositive(DialogInterface dialog, int hour, int minutes) {
                                txt_time_intask.setText(Utility.enToFa(Utility.fixTimeFormat(hour+":"+minutes,":",":")));

                                if(spr_type_instask.getSelectedItemId() == TaskModel.TYPE_DAILY)
                                    addItemsOnSpinner_alert(TaskModel.TYPE_DAILY);

                                //compare
                                String endTime = Utility.fixTimeFormat(Utility.faToEn(txt_time2_intask.getText().toString()),":",":");
                                String startTime = Utility.fixTimeFormat(Utility.faToEn(txt_time_intask.getText().toString()),":",":");

                                String endDate = Utility.fixDateFormat_01(Utility.faToEn(txt_date2_intask.getText().toString()));
                                String startDate = Utility.fixDateFormat_01(Utility.faToEn(txt_date_intask.getText().toString()));

                                if(ll_date2_intask.getVisibility()==View.VISIBLE){
                                    if(compareTwoDateOrTime(startDate,endDate,"/")==0){
                                        if(compareTwoDateOrTime(startTime,endTime,":")>0){
                                            txt_time2_intask.setText(startTime);
                                        }
                                    }
                                }else{
                                    if(compareTwoDateOrTime(startTime,endTime,":")>0){
                                        txt_time2_intask.setText(startTime);
                                    }
                                }
                            }

                            @Override
                            public void onNegative(DialogInterface dialog) {

                            }
                        })
                        .build();
                if(session.getBoolianItem(SessionModel.KEY_IS_TIME_PICKER_TUTORIAL_SHOWED,true)){
                    session.saveItem(SessionModel.KEY_IS_TIME_PICKER_TUTORIAL_SHOWED,false);
                }
                dialog.show();
            }
        });
        txt_time2_intask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearTimePickerDialog dialog = LinearTimePickerDialog.Builder.with(InsertTaskActivity.this)
                        .setLineColor(Color.rgb(69, 101, 141))
                        .setTextColor(Color.parseColor("#abc0da"))
                        .setDialogBackgroundColor(Color.parseColor("#83c0f9"))
                        .setPickerBackgroundColor(Color.parseColor("#1a3b64"))
                        .setShowTutorial(session.getBoolianItem(SessionModel.KEY_IS_TIME_PICKER_TUTORIAL_SHOWED,true)?true:false)
                        .setButtonCallback(new LinearTimePickerDialog.ButtonCallback() {
                            @Override
                            public void onPositive(DialogInterface dialog, int hour, int minutes) {
                                txt_time2_intask.setText(Utility.enToFa(Utility.fixTimeFormat(hour+":"+minutes,":",":")));
                                //compare
                                String endTime = Utility.fixTimeFormat(Utility.faToEn(txt_time2_intask.getText().toString()),":",":");
                                String startTime = Utility.fixTimeFormat(Utility.faToEn(txt_time_intask.getText().toString()),":",":");

                                String endDate = Utility.fixDateFormat_01(Utility.faToEn(txt_date2_intask.getText().toString()));
                                String startDate = Utility.fixDateFormat_01(Utility.faToEn(txt_date_intask.getText().toString()));

                                if(ll_date2_intask.getVisibility()==View.VISIBLE){
                                    if(compareTwoDateOrTime(startDate,endDate,"/")==0){
                                        if(compareTwoDateOrTime(startTime,endTime,":")>0){
                                            txt_time_intask.setText(endTime);
                                        }
                                    }
                                }else{
                                    if(compareTwoDateOrTime(startTime,endTime,":")>0){
                                        txt_time_intask.setText(endTime);
                                    }
                                }
                            }

                            @Override
                            public void onNegative(DialogInterface dialog) {

                            }
                        })
                        .build();
                if(session.getBoolianItem(SessionModel.KEY_IS_TIME_PICKER_TUTORIAL_SHOWED,true)){
                    session.saveItem(SessionModel.KEY_IS_TIME_PICKER_TUTORIAL_SHOWED,false);
                }
                dialog.show();
            }
        });



        btn_add_instask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        btn_addAndQuit_instask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPopUp.show(InsertTaskActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No),true,false);
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            while(DialogPopUp.isUp()){
                                Thread.sleep(500);
                            }
                            if(!DialogPopUp.isUp()){
                                Thread.currentThread().interrupt();//meysam
                                if(DialogPopUp.dialog_result==1){
                                    //first button clicked
                                    if(taskForEdit != null)
                                    {
                                        db.deleteTaskById(taskIdForEdit);
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(addTask())
                                            {

                                                //meysam - added in 13970318
                                                TriiiBroadcastReceiver.initializeTasksBroadcasts(getApplicationContext());
                                                ///////////////////////////////

                                                finish();
                                                Utility.displayToast(InsertTaskActivity.this,getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);
                                            }

                                        }
                                    });

                                }
                                else if (DialogPopUp.dialog_result==2)
                                {
                                    //second button clicked

                                }
                                else
                                {
                                    //do nothing
                                }
                                DialogPopUp.hide();
                            }
                        }
                        catch (InterruptedException e)
                        {
                            // Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }


    private Boolean addTask() {
//        Boolean hasError = false;
        TaskModel task = new TaskModel();
        ArrayList<TaskAlarmModel> taskAlarms = new ArrayList<>();
        TaskAlarmModel taskAlarm = new TaskAlarmModel();
        String startDate = Utility.fixDateFormat_01(Utility.faToEn(txt_date_intask.getText().toString())).replace("/","");
        String endDate = Utility.fixDateFormat_01(Utility.faToEn(txt_date2_intask.getText().toString())).replace("/","");
        PersianCalendar cal;
        PersianCalendar calAlert;
        task.setStartDate(startDate);
        task.setEndDate(endDate);

        ArrayList<String> alarmMinutes = new ArrayList<>();
        Integer minuteDifferences = getMinuteDifference(spr_alert_instask.getSelectedItem().toString());
        if(minuteDifferences != null)
        {
            alarmMinutes.add(minuteDifferences.toString());
            task.setTaskAlarmsMinutes(alarmMinutes);
        }

        if(!txt_time2_intask.getText().toString().equals(""))
        {
            String endTime = Utility.fixTimeFormat(txt_time2_intask.getText().toString(),":",":");
            task.setEndTime(Utility.faToEn(endTime));
        }

        if(txt_description_intask.getText().toString() == null || txt_description_intask.getText().toString().equals(""))
        {
//            hasError = true;
            Utility.displayToast(getApplicationContext(),getString(R.string.error_invalid_description),Toast.LENGTH_SHORT);
            return false;
        }
        if(txt_time_intask.getText().toString() == null || txt_time_intask.getText().toString().equals(""))
        {
//            hasError = true;
            Utility.displayToast(getApplicationContext(),getString(R.string.error_invalid_time),Toast.LENGTH_SHORT);
            return false;
        }

        String startTime = Utility.fixTimeFormat(txt_time_intask.getText().toString(),":",":");
        task.setStartTime(Utility.faToEn(startTime));

        task.setDescription(Utility.faToEn(txt_description_intask.getText().toString()));

        task.setHasAlarmSound(chBx_alarm_instask.isChecked());

        if(spr_type_instask.getSelectedItemId() == TaskModel.TYPE_NORMAL)
        {
            task.setType(TaskModel.TYPE_NORMAL);
            cal = new PersianCalendar();
            String date = Utility.faToEn(txt_date_intask.getText().toString());
            if(date == null)
            {
                Utility.displayToast(getApplicationContext(),getString(R.string.error_invalid_date),Toast.LENGTH_SHORT);
//                hasError = true;
                return false;
            }
            else
            {

//                String miladiDate = Utility.convertDatePersian2Gorgeian(date);
                cal.setPersianDate(Integer.valueOf(Utility.faToEn(date).split("/")[0]),Integer.valueOf(Utility.faToEn(date).split("/")[1]),Integer.valueOf(Utility.faToEn(date).split("/")[2]));

                task.setYear(String.valueOf(cal.getPersianYear()));
                task.setMonth(String.valueOf(cal.getPersianMonth()));
                task.setDayOfMonth(String.valueOf(cal.getPersianDay()));

                String time = Utility.fixTimeFormat(txt_time_intask.getText().toString(),":",":");
                cal.set(PersianCalendar.HOUR_OF_DAY,Integer.valueOf(Utility.faToEn(time).split(":")[0]));
                cal.set(PersianCalendar.MINUTE,Integer.valueOf(Utility.faToEn(time).split(":")[1]));

                Integer taskAlertMinuteDiffrence = getMinuteDifference(spr_alert_instask.getSelectedItem().toString());
                if(taskAlertMinuteDiffrence != null)
                {
                    calAlert = (PersianCalendar) cal.clone();
                    calAlert.add(PersianCalendar.MINUTE, taskAlertMinuteDiffrence);

                    taskAlarm.setTime(Utility.fixTimeFormat(Utility.faToEn(calAlert.get(PersianCalendar.HOUR_OF_DAY) + ":" +calAlert.get(PersianCalendar.MINUTE)),":",":"));

                    taskAlarm.setYear(String.valueOf(calAlert.getPersianYear()));
                    taskAlarm.setMonth(String.valueOf(calAlert.getPersianMonth()));
                    taskAlarm.setDayOfMonth(String.valueOf(calAlert.getPersianDay()));

                    if(taskAlarm.getTime() != null)
                    {
                        taskAlarms.add(taskAlarm);
                        task.setTaskAlarms(taskAlarms);
                    }
                }
            }
        }
        if(spr_type_instask.getSelectedItemId() == TaskModel.TYPE_DAILY)
        {
            task.setType(TaskModel.TYPE_DAILY);
            cal = new PersianCalendar();
            String time = Utility.fixTimeFormat(txt_time_intask.getText().toString(),":",":");

            cal.set(PersianCalendar.HOUR_OF_DAY,Integer.valueOf(Utility.faToEn(time).split(":")[0]));
            cal.set(PersianCalendar.MINUTE,Integer.valueOf(Utility.faToEn(time).split(":")[1]));

            Integer taskAlertMinuteDiffrence = getMinuteDifference(spr_alert_instask.getSelectedItem().toString());
            if(taskAlertMinuteDiffrence != null)
            {
                calAlert = (PersianCalendar) cal.clone();
                calAlert.add(PersianCalendar.MINUTE, taskAlertMinuteDiffrence);

                taskAlarm.setTime(Utility.fixTimeFormat(Utility.faToEn(calAlert.get(PersianCalendar.HOUR_OF_DAY) + ":" +calAlert.get(PersianCalendar.MINUTE)),":",":"));

                if(taskAlarm.getTime() != null)
                {
                    taskAlarms.add(taskAlarm);
                    task.setTaskAlarms(taskAlarms);
                }
            }
        }
        if(spr_type_instask.getSelectedItemId() == TaskModel.TYPE_WEEKLY)
        {
            task.setType(TaskModel.TYPE_WEEKLY);

            // meysam - check if day count between start and end dates are at least 7 days
            PersianCalendar pc1 = new PersianCalendar();
            pc1.set(Integer.valueOf(task.getStartDate().substring(0,4)),Integer.valueOf(task.getStartDate().substring(4,6)),Integer.valueOf(task.getStartDate().substring(6,8)));
            PersianCalendar pc2 = new PersianCalendar();
            pc2.set(Integer.valueOf(task.getEndDate().substring(0,4)),Integer.valueOf(task.getEndDate().substring(4,6)),Integer.valueOf(task.getEndDate().substring(6,8)));
            int dayCountBetween = Utility.daysBetween(pc1.getTime(),pc2.getTime());
            if(dayCountBetween < 7)
            {
//                hasError = true;
                Utility.displayToast(getApplicationContext(),getString(R.string.error_start_date_end_date_most_be_more_than_seven_days),Toast.LENGTH_SHORT);
                return false;
            }
            //////////////////////////////////////////////

            ArrayList<String> daysOfWeek = new ArrayList<String>();
            cal = new PersianCalendar();

            String time = Utility.fixTimeFormat(txt_time_intask.getText().toString(),":",":");

            cal.set(PersianCalendar.HOUR_OF_DAY,Integer.valueOf(Utility.faToEn(time).split(":")[0]));
            cal.set(PersianCalendar.MINUTE,Integer.valueOf(Utility.faToEn(time).split(":")[1]));

            Integer taskAlertMinuteDiffrence = getMinuteDifference(spr_alert_instask.getSelectedItem().toString());
            calAlert = (PersianCalendar) cal.clone();
            if(taskAlertMinuteDiffrence != null)
            {

                calAlert.add(PersianCalendar.MINUTE, taskAlertMinuteDiffrence);

                taskAlarm.setTime(Utility.fixTimeFormat(Utility.faToEn(calAlert.get(PersianCalendar.HOUR_OF_DAY) + ":" +calAlert.get(PersianCalendar.MINUTE)),":",":"));
            }

            Boolean hasDayOfWeekSelected = false;
            if(cbk_week_0_instask.isChecked())
            {
                hasDayOfWeekSelected = true;
//                cal.set(PersianCalendar.DAY_OF_WEEK,PersianCalendar.SATURDAY);
                daysOfWeek.add("0");

                if (taskAlertMinuteDiffrence != null)
                {
                    taskAlarm = new TaskAlarmModel(taskAlarm);
                    taskAlarm.setDayOfWeek("0");
                    if(taskAlarm.getTime() != null)
                    {
                        taskAlarms.add(taskAlarm);
                    }
                }
            }
            if(cbk_week_1_instask.isChecked())
            {
                hasDayOfWeekSelected = true;
//                cal.set(PersianCalendar.DAY_OF_WEEK,PersianCalendar.SUNDAY);
                daysOfWeek.add("1");

                if (taskAlertMinuteDiffrence != null)
                {
                    taskAlarm = new TaskAlarmModel(taskAlarm);
//                    taskAlarm.setDayOfWeek(String.valueOf(calAlert.get(PersianCalendar.DAY_OF_WEEK)));
                    taskAlarm.setDayOfWeek("1");
                    if(taskAlarm.getTime() != null)
                    {
                        taskAlarms.add(taskAlarm);
                    }
                }
            }

            if(cbk_week_2_instask.isChecked())
            {
                hasDayOfWeekSelected = true;
//                cal.set(PersianCalendar.DAY_OF_WEEK,PersianCalendar.MONDAY);
                daysOfWeek.add("2");
                if (taskAlertMinuteDiffrence != null)
                {
                    taskAlarm = new TaskAlarmModel(taskAlarm);
//                    taskAlarm.setDayOfWeek(String.valueOf(calAlert.get(PersianCalendar.DAY_OF_WEEK)));
                    taskAlarm.setDayOfWeek("2");
                    if(taskAlarm.getTime() != null)
                    {
                        taskAlarms.add(taskAlarm);
                    }
                }
            }

            if(cbk_week_3_instask.isChecked())
            {
                hasDayOfWeekSelected = true;
//                cal.set(PersianCalendar.DAY_OF_WEEK,PersianCalendar.TUESDAY);
                daysOfWeek.add("3");
                if (taskAlertMinuteDiffrence != null)
                {
                    taskAlarm = new TaskAlarmModel(taskAlarm);
//                    taskAlarm.setDayOfWeek(String.valueOf(calAlert.get(PersianCalendar.DAY_OF_WEEK)));
                    taskAlarm.setDayOfWeek("3");
                    if(taskAlarm.getTime() != null)
                    {
                        taskAlarms.add(taskAlarm);
                    }
                }
            }

            if(cbk_week_4_instask.isChecked())
            {
                hasDayOfWeekSelected = true;
//                cal.set(PersianCalendar.DAY_OF_WEEK,PersianCalendar.WEDNESDAY);
//                daysOfWeek.add(String.valueOf(cal.get(PersianCalendar.DAY_OF_WEEK)));
                daysOfWeek.add("4");

                if (taskAlertMinuteDiffrence != null)
                {
                    taskAlarm = new TaskAlarmModel(taskAlarm);
//                    taskAlarm.setDayOfWeek(String.valueOf(calAlert.get(PersianCalendar.DAY_OF_WEEK)));
                    taskAlarm.setDayOfWeek("4");
                    if(taskAlarm.getTime() != null)
                    {
                        taskAlarms.add(taskAlarm);
                    }
                }
            }

            if(cbk_week_5_instask.isChecked())
            {
                hasDayOfWeekSelected = true;
//                cal.set(PersianCalendar.DAY_OF_WEEK,PersianCalendar.THURSDAY);
//                daysOfWeek.add(String.valueOf(cal.get(PersianCalendar.DAY_OF_WEEK)));
                daysOfWeek.add("5");

                if (taskAlertMinuteDiffrence != null)
                {
                    taskAlarm = new TaskAlarmModel(taskAlarm);
//                    taskAlarm.setDayOfWeek(String.valueOf(calAlert.get(PersianCalendar.DAY_OF_WEEK)));
                    taskAlarm.setDayOfWeek("5");
                    if(taskAlarm.getTime() != null)
                    {
                        taskAlarms.add(taskAlarm);
                    }
                }
            }

            if(cbk_week_6_instask.isChecked())
            {
                hasDayOfWeekSelected = true;
//                cal.set(PersianCalendar.DAY_OF_WEEK,PersianCalendar.FRIDAY);
//                daysOfWeek.add(String.valueOf(cal.get(PersianCalendar.DAY_OF_WEEK)));
                daysOfWeek.add("6");

                if (taskAlertMinuteDiffrence != null)
                {
                    taskAlarm = new TaskAlarmModel(taskAlarm);
//                    taskAlarm.setDayOfWeek(String.valueOf(calAlert.get(PersianCalendar.DAY_OF_WEEK)));
                    taskAlarm.setDayOfWeek("6");
                    if(taskAlarm.getTime() != null)
                    {
                        taskAlarms.add(taskAlarm);
                    }
                }
            }

            if(!hasDayOfWeekSelected)
            {
//                hasError = true;
                Utility.displayToast(getApplicationContext(),getString(R.string.error_select_day_of_week),Toast.LENGTH_SHORT);
                return false;
            }
//            hasDayOfWeekSelected = false;
//            if(!hasError)
//            {
                task.setTaskAlarms(taskAlarms);
                task.setDayOfWeek(daysOfWeek);
//            }
        }
        if(spr_type_instask.getSelectedItemId() == TaskModel.TYPE_MONTHLY)
        {
            task.setType(TaskModel.TYPE_MONTHLY);

            // meysam - check if day is between start and end dates
            Integer startDay = Integer.valueOf(task.getStartDate().substring(6,8));
            Integer endDay = Integer.valueOf(task.getEndDate().substring(6,8));
            Integer targetDay = Integer.valueOf(String.valueOf(spr_monthday_instask.getSelectedItemId()+1));
            Boolean targetDayOk = false;
            if(targetDay >= startDay || targetDay <= endDay)
                targetDayOk = true;
            if(!targetDayOk)
            {
//                hasError = true;
                Utility.displayToast(getApplicationContext(),getString(R.string.error_day_most_be_between_start_date_end_date),Toast.LENGTH_SHORT);
                return false;
            }
            //////////////////////////////////////////////

            cal = new PersianCalendar();
            cal.set(PersianCalendar.DAY_OF_MONTH,(int)spr_monthday_instask.getSelectedItemId());
            task.setDayOfMonth(String.valueOf(spr_monthday_instask.getSelectedItemId()+1));
            String time = Utility.fixTimeFormat(txt_time_intask.getText().toString(),":",":");

            cal.set(PersianCalendar.HOUR_OF_DAY,Integer.valueOf(Utility.faToEn(time).split(":")[0]));
            cal.set(PersianCalendar.MINUTE,Integer.valueOf(Utility.faToEn(time).split(":")[1]));

            Integer taskAlertMinuteDiffrence = getMinuteDifference(spr_alert_instask.getSelectedItem().toString());
            if(taskAlertMinuteDiffrence != null)
            {
                calAlert = (PersianCalendar) cal.clone();
                calAlert.add(PersianCalendar.MINUTE, taskAlertMinuteDiffrence);

                taskAlarm.setTime(Utility.fixTimeFormat(Utility.faToEn(calAlert.get(PersianCalendar.HOUR_OF_DAY) + ":" +calAlert.get(PersianCalendar.MINUTE)),":",":"));
                taskAlarm.setDayOfMonth(String.valueOf(calAlert.get(PersianCalendar.DAY_OF_MONTH)+1));

                if(taskAlarm.getTime() != null)
                {
                    taskAlarms.add(taskAlarm);
                    task.setTaskAlarms(taskAlarms);
                }
            }

        }
            db.addTask(task);
            return true;
    }

    //add items to spinner
    public void addItemsOnSpinner_types() {
        List<String> list = new ArrayList<String>();
        list.add("فقط یکبار");
        list.add("روزانه");
        list.add("هفتگی");
        list.add("ماهانه");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spr_type_instask.setAdapter(dataAdapter);
        //set from prev task
//        if(taskForEdit!=null)
//            spr_type_instask.setSelection(taskForEdit.getType());
    }
//    public void addItemsOnSpinner_weeknames() {
//        List<String> list = new ArrayList<String>();
//        list.add("شنبه ها");
//        list.add("یک شنبه ها");
//        list.add("دو شنبه ها");
//        list.add("سه شنبه ها");
//        list.add("چهارشنبه ها");
//        list.add("پنج شنبه ها");
//        list.add("جمعه ها");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
//        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
////        spr_weekname_instask.setAdapter(dataAdapter);
//    }
    public void addItemsOnSpinner_monthday() {
        List<String> list = new ArrayList<String>();
        for(int i=1;i<32;i++){
            list.add(Utility.enToFa(i+" ام "));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spr_monthday_instask.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner_alert(Integer taskType) {

        List<String> list = new ArrayList<String>();
        list.add("بدون یادآور");
        list.add("همان موقع");


        String startTime = null;
        Integer dayMinutes = null;
        if(taskType.equals(TaskModel.TYPE_DAILY))
        {
            startTime = Utility.fixTimeFormat(Utility.faToEn(txt_time_intask.getText().toString()),":",":");
            dayMinutes = (Integer.valueOf(startTime.substring(0,2))*60 + Integer.valueOf(startTime.substring(3,5)));
            if(dayMinutes - 5 > 0)
                list.add(Utility.enToFa("5 دقیقه زودتر"));
            if(dayMinutes - 10 > 0)
                list.add(Utility.enToFa("10 دقیقه زودتر"));
            if(dayMinutes - 15 > 0)
                list.add(Utility.enToFa("15 دقیقه زودتر"));
            if(dayMinutes - 20 > 0)
                list.add(Utility.enToFa("20 دقیقه زودتر"));
            if(dayMinutes - 25 > 0)
                list.add(Utility.enToFa("25 دقیقه زودتر"));
            if(dayMinutes - 30 > 0)
                list.add(Utility.enToFa("30 دقیقه زودتر"));
            if(dayMinutes - 45 > 0)
                list.add(Utility.enToFa("45 دقیقه زودتر"));
            if(dayMinutes - 60 > 0)
                list.add("یک ساعت زودتر");
            if(dayMinutes - 120 > 0)
                list.add("دو ساعت زودتر");
            if(dayMinutes - 180 > 0)
                list.add("سه ساعت زودتر");
            if(dayMinutes - 720 > 0)
                list.add(Utility.enToFa("12 ساعت زودتر"));
            if(dayMinutes - 1440 > 0)
                list.add(Utility.enToFa("24 ساعت زودتر"));
            if(dayMinutes - 2880 > 0)
                list.add("دو روز زودتر");
            if(dayMinutes - 10080 > 0)
                list.add("یک هفته زودتر");
        }
        else
        {
            list.add(Utility.enToFa("5 دقیقه زودتر"));
            list.add(Utility.enToFa("10 دقیقه زودتر"));
            list.add(Utility.enToFa("15 دقیقه زودتر"));
            list.add(Utility.enToFa("20 دقیقه زودتر"));
            list.add(Utility.enToFa("25 دقیقه زودتر"));
            list.add(Utility.enToFa("30 دقیقه زودتر"));
            list.add(Utility.enToFa("45 دقیقه زودتر"));
            list.add("یک ساعت زودتر");
            list.add("دو ساعت زودتر");
            list.add("سه ساعت زودتر");
            list.add(Utility.enToFa("12 ساعت زودتر"));
            list.add(Utility.enToFa("24 ساعت زودتر"));
            list.add("دو روز زودتر");
            list.add("یک هفته زودتر");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spr_alert_instask.setAdapter(dataAdapter);
    }

    private Integer getMinuteDifference(String chosenAlert)
    {
        if(chosenAlert.equals("بدون یادآور"))
            return null;
        if(chosenAlert.equals("همان موقع"))
            return 0;
        if(chosenAlert.equals(Utility.enToFa("5 دقیقه زودتر")))
            return -5;
        if(chosenAlert.equals(Utility.enToFa("10 دقیقه زودتر")))
            return -10;
        if(chosenAlert.equals(Utility.enToFa("15 دقیقه زودتر")))
            return -15;
        if(chosenAlert.equals(Utility.enToFa("20 دقیقه زودتر")))
            return -20;
        if(chosenAlert.equals(Utility.enToFa("25 دقیقه زودتر")))
            return -25;
        if(chosenAlert.equals(Utility.enToFa("30 دقیقه زودتر")))
            return -30;
        if(chosenAlert.equals(Utility.enToFa("45 دقیقه زودتر")))
            return -45;
        if(chosenAlert.equals("یک ساعت زودتر"))
            return -60;
        if(chosenAlert.equals("دو ساعت زودتر"))
            return -120;
        if(chosenAlert.equals("سه ساعت زودتر"))
            return -180;
        if(chosenAlert.equals(Utility.enToFa("12 ساعت زودتر")))
            return -720;
        if(chosenAlert.equals(Utility.enToFa("24 ساعت زودتر")))
            return -1440;
        if(chosenAlert.equals("دو روز زودتر"))
            return -2880;
        if(chosenAlert.equals("یک هفته زودتر"))
            return -10080;
        return null;
    }

    private Integer getSpinnerPositionByMinutes(Integer minutes)
    {
        if(minutes == null)
            return 0;
        if(minutes.equals(0))
            return 1;
        if(minutes.equals(-5))
            return 2;
        if(minutes.equals(-10))
            return 3;
        if(minutes.equals(-15))
            return 4;
        if(minutes.equals(-20))
            return 5;
        if(minutes.equals(-25))
            return 6;
        if(minutes.equals(-30))
            return 7;
        if(minutes.equals(-45))
            return 8;
        if(minutes.equals(-60))
            return 9;
        if(minutes.equals(-120))
            return 10;
        if(minutes.equals(-180))
            return 11;
        if(minutes.equals(-720))
            return 12;
        if(minutes.equals(-1440))
            return 13;
        if(minutes.equals(-2880))
            return 14;
        if(minutes.equals(-10080))
            return 15;
        return null;
    }

//    private Integer getDayOfWeekCalendarCode(String dayOdWeek)
//    {
//        Calendar cal = Calendar.getInstance();
//        cal.setFirstDayOfWeek(Calendar.SATURDAY);
//        if(dayOdWeek.equals("شنبه ها"))
//        {
//            cal.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
//            return cal.get(Calendar.DAY_OF_WEEK);
//        }
//        if(dayOdWeek.equals("یک شنبه ها"))
//        {
//            cal.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
//            return cal.get(Calendar.DAY_OF_WEEK);
//        }
//        if(dayOdWeek.equals("دو شنبه ها"))
//        {
//            cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
//            return cal.get(Calendar.DAY_OF_WEEK);
//        }
//        if(dayOdWeek.equals("سه شنبه ها"))
//        {
//            cal.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
//            return cal.get(Calendar.DAY_OF_WEEK);
//        }
//        if(dayOdWeek.equals("چهارشنبه ها"))
//        {
//            cal.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
//            return cal.get(Calendar.DAY_OF_WEEK);
//        }
//        if(dayOdWeek.equals("پنج شنبه ها"))
//        {
//            cal.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
//            return cal.get(Calendar.DAY_OF_WEEK);
//        }
//        if(dayOdWeek.equals("جمعه ها"))
//        {
//            cal.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
//            return cal.get(Calendar.DAY_OF_WEEK);
//        }
//        return null;
//    }
    //fix date
    private String fixDate(int st){
        return st<10?"0"+st:""+st;
    }
    //compae two date or time
    private byte compareTwoDateOrTime(String value1,String value2,String delimeter){
        int v1= Integer.parseInt(value1.replaceAll(delimeter,""));
        int v2= Integer.parseInt(value2.replaceAll(delimeter,""));
        if(v1>v2){
            return 1;
        }else if(v1<v2){
            return -1;
        }
        return 0;
    }
    private String fixDate(String date) {
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
    }

    private String fixPerDate2(String date) {
        String[] temp = date.split("/");
        return temp[0] +"/"+ (temp[1].startsWith("0")?temp[1].substring(1):temp[1]) +"/"+ (temp[2].startsWith("0")?temp[2].substring(1):temp[2]);
    }

//    private void clearAllFields()
//    {
//        spr_type_instask.setSelection(0);
//        txt_description_intask.setText("");
//        String persian_date = fixPerDate2(Utilities.getCurrentShamsidate());
//        txt_date_intask.setText(persian_date);
//        txt_date2_intask.setText(persian_date);
//        txt_date_title_intask.setText("");
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        txt_time_intask.setText(sdf.format(new Date()));
//        txt_time2_intask.setText(sdf.format(new Date()));
//    }


    @Override
    public void onResume() {
        if(isInCreate)
        {

            Bundle extras = getIntent().getExtras();
            String persian_date="";
            taskIdForEdit=null;
            if (extras != null && extras.getString("task_id") != null)
            {
                taskIdForEdit = new BigInteger(extras.getString("task_id"));
                btn_addAndQuit_instask.setText("ویرایش");
                taskForEdit=null;
                if (taskIdForEdit != null) {
                    taskForEdit = db.getTaskById(taskIdForEdit);
                    if(taskForEdit.getType().equals(TaskModel.TYPE_NORMAL))
                    {
                        spr_type_instask.setSelection(TaskModel.TYPE_NORMAL);
                    }
                    if(taskForEdit.getType().equals(TaskModel.TYPE_DAILY))
                    {
                        spr_type_instask.setSelection(TaskModel.TYPE_DAILY);
                    }
                    if(taskForEdit.getType().equals(TaskModel.TYPE_WEEKLY))
                    {
                        spr_type_instask.setSelection(TaskModel.TYPE_WEEKLY);
                    }
                    if(taskForEdit.getType().equals(TaskModel.TYPE_MONTHLY))
                    {
                        spr_type_instask.setSelection(TaskModel.TYPE_MONTHLY);
                    }

                    txt_date_intask.setText((Utility.addDateSeparator(taskForEdit.getStartDate(),"/")));
                    txt_date2_intask.setText((Utility.addDateSeparator(taskForEdit.getEndDate(),"/")));
                    txt_time_intask.setText(taskForEdit.getStartTime());
                    txt_time2_intask.setText(taskForEdit.getEndTime());
                    txt_description_intask.setText(taskForEdit.getDescription());
                    if(taskForEdit.getType().equals(TaskModel.TYPE_WEEKLY))
                    {
                        //meysam - weekly task - set days of week wrt alarms
                        ArrayList<String> taskDays = taskForEdit.getDayOfWeek();
                        for(int i =0; i < taskDays.size();i++)
                        {
                            if(taskDays.get(i).equals("0"))
                                cbk_week_0_instask.setChecked(true);
                            if(taskDays.get(i).equals("1"))
                                cbk_week_1_instask.setChecked(true);
                            if(taskDays.get(i).equals("2"))
                                cbk_week_2_instask.setChecked(true);
                            if(taskDays.get(i).equals("3"))
                                cbk_week_3_instask.setChecked(true);
                            if(taskDays.get(i).equals("4"))
                                cbk_week_4_instask.setChecked(true);
                            if(taskDays.get(i).equals("5"))
                                cbk_week_5_instask.setChecked(true);
                            if(taskDays.get(i).equals("6"))
                                cbk_week_6_instask.setChecked(true);
                        }
                    }
                    if(taskForEdit.getType().equals(TaskModel.TYPE_MONTHLY))
                    {
                        spr_monthday_instask.setSelection(Integer.valueOf( taskForEdit.getDayOfMonth())-1);
                    }
                    chBx_alarm_instask.setChecked(taskForEdit.getHasAlarmSound());

                    if (taskForEdit.getTaskAlarms().size() > 0)
                    {
//                        spr_alert_instask.post(new Runnable() {
//                            public void run() {
//                                spr_alert_instask.setSelection(getSpinnerPositionByMinutes(Integer.valueOf(taskForEdit.getTaskAlarmsMinutes().get(0))));
//                            }
//                        });

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                spr_alert_instask.setSelection(getSpinnerPositionByMinutes(Integer.valueOf(taskForEdit.getTaskAlarmsMinutes().get(0))));
                            }
                        }, 100);

                    }
                    else
                    {
                        spr_alert_instask.setSelection(0);
                        chBx_alarm_instask.setVisibility(View.GONE);
                    }

                }
            }else if (extras != null && extras.getString("persian_date") != null) {
                persian_date = extras.getString("persian_date");
                txt_date_intask.setText(persian_date);
                txt_date2_intask.setText(persian_date);
                String persian_day_of_week = extras.getString("persian_day_of_week");
            }else{
                persian_date = fixPerDate2(Utilities.getCurrentShamsidate());
                txt_date_intask.setText(persian_date);
                txt_date2_intask.setText(persian_date);
            }


            isInCreate = false;
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
