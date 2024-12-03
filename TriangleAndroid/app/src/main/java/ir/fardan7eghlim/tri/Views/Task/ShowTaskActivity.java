package ir.fardan7eghlim.tri.Views.Task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.TaskModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseActivity;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Reciever.TriiiBroadcastReceiver;
import ir.fardan7eghlim.tri.Utils.DialogPopUp;
import ir.fardan7eghlim.tri.Views.Home.utility.DateTools;

public class ShowTaskActivity extends BaseActivity {

    private BigInteger task_id;

    private TextView txt_type_taskDlg;
    private TextView txt_description_taskDlg;
    private TextView txt_timeFrom_taskDlg;
    private TextView txt_timeTotaskDlg;
    private TextView txt_dateFrom_taskDlg;
    private TextView txt_dateTotaskDlg;
    private TextView txt_reminder_taskDlg;

    private CheckBox chBx_alarm_taskDlg;

    private Button btn_edit_taskDlg;
    private Button btn_delete_taskDlg;
    private Button btn_cancel_taskDlg;
    private LinearLayout ll_bg_taskDlg;

    private TextView txt_t_timeFrom_taskDlg;
    private TextView txt_t_timeTotaskDlg;
    private TextView txt_t_dateFrom_taskDlg;
    private TextView txt_t_dateTotaskDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_task);

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("show_task_activity_broadcast"));

        //sets
        txt_type_taskDlg = findViewById(R.id.txt_type_taskDlg);
        txt_description_taskDlg = findViewById(R.id.txt_description_taskDlg);
        txt_timeFrom_taskDlg = findViewById(R.id.txt_timeFrom_taskDlg);
        txt_timeTotaskDlg = findViewById(R.id.txt_timeTotaskDlg);
        txt_dateFrom_taskDlg = findViewById(R.id.txt_dateFrom_taskDlg);
        txt_dateTotaskDlg = findViewById(R.id.txt_dateTotaskDlg);
        txt_t_timeFrom_taskDlg = findViewById(R.id.txt_t_timeFrom_taskDlg);
        txt_t_timeTotaskDlg = findViewById(R.id.txt_t_timeTotaskDlg);
        txt_t_dateFrom_taskDlg = findViewById(R.id.txt_t_dateFrom_taskDlg);
        txt_t_dateTotaskDlg = findViewById(R.id.txt_t_dateTotaskDlg);
        txt_reminder_taskDlg = findViewById(R.id.txt_reminder_taskDlg);
        chBx_alarm_taskDlg = findViewById(R.id.chBx_alarm_taskDlg);
        btn_edit_taskDlg = findViewById(R.id.btn_edit_taskDlg);
        btn_delete_taskDlg = findViewById(R.id.btn_delete_taskDlg);
        btn_cancel_taskDlg = findViewById(R.id.btn_cancel_taskDlg);
        ll_bg_taskDlg=findViewById(R.id.ll_bg_taskDlg);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            task_id = new BigInteger(extras.getString("task_id"));
        } else {
            //error
            task_id = null;
        }

        //meysam - get task base on its ID
        if (task_id != null) {
            TaskModel task = db.getTaskById(task_id);
            String type_temp="فقط یکبار";
            if(task.getType()==1){
                type_temp="روزانه";
            }else if(task.getType()==2){
                String temp = "";
                for(int i=0;i<task.getDayOfWeek().size();i++)
                {
                    temp +=Utility.getDayNameOfWeek(task.getDayOfWeek().get(i))+" ها";
                    if(i != task.getDayOfWeek().size()-1)
                        temp +=",";
                }
                type_temp= temp;
            }else if(task.getType()==3)
            {
                type_temp=" روز "+task.getDayOfMonth()+" هر ماه";
            }
            //
            if(task.getStartDate().equals(task.getEndDate())){
                txt_t_dateFrom_taskDlg.setText("در تاریخ:");
                txt_t_dateTotaskDlg.setVisibility(View.INVISIBLE);
                txt_dateTotaskDlg.setVisibility(View.INVISIBLE);
            }
            if(task.getStartTime().equals(task.getEndTime())){
                txt_t_timeFrom_taskDlg.setText("در ساعت:");
                txt_t_timeTotaskDlg.setVisibility(View.INVISIBLE);
                txt_timeTotaskDlg.setVisibility(View.INVISIBLE);
            }
            //
            txt_type_taskDlg.setText(type_temp);
            txt_description_taskDlg.setText(task.getDescription());
            txt_timeFrom_taskDlg.setText(task.getStartTime());
            txt_timeTotaskDlg.setText(task.getEndTime());
            txt_dateFrom_taskDlg.setText(Utility.addDateSeparator(task.getStartDate(),"/"));
            txt_dateTotaskDlg.setText(Utility.addDateSeparator(task.getEndDate(),"/"));
            String alarm_temp="";
            if (task.getTaskAlarms().size() > 0) {
                chBx_alarm_taskDlg.setChecked(task.getHasAlarmSound());


//                for(TaskAlarmModel alrm:task.getTaskAlarms()){
//                    alarm_temp+=alrm.getTime()+" ,";
//                }
//                txt_reminder_taskDlg.setText(Utility.fixTimeFormat(alarm_temp.substring(0,alarm_temp.length()-2),":",":"));
//
                txt_reminder_taskDlg.setText(getSpinnerTextByMinutes(Integer.valueOf(task.getTaskAlarmsMinutes().get(0))));

            } else {
                txt_reminder_taskDlg.setText("بدون یادآوری");
                chBx_alarm_taskDlg.setVisibility(View.GONE);
            }

        }
        //buttons
        btn_edit_taskDlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go for edit
                Intent intent = new Intent(ShowTaskActivity.this, InsertTaskActivity.class);
                intent.putExtra("task_id", task_id+"");
                ShowTaskActivity.this.startActivity(intent);
                ShowTaskActivity.this.finish();
            }
        });
        btn_delete_taskDlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go for delete
                DialogPopUp.show(ShowTaskActivity.this,getString(R.string.msg_AreYouSure),getString(R.string.btn_Yes),getString(R.string.btn_No),true,false);
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
                                    //meysam - added in 13970318
                                    TriiiBroadcastReceiver.cancelTaskBroadcast(getApplicationContext(),task_id);
                                    ///////////////////////////////
                                    //first button clicked
                                    db.deleteTaskById(task_id);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Utility.displayToast(ShowTaskActivity.this,getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);
                                        }
                                    });
                                    //meysam - added in 13970318
                                    TriiiBroadcastReceiver.initializeTasksBroadcasts(getApplicationContext());
                                    ///////////////////////////////
                                    ShowTaskActivity.this.finish();

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
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        btn_cancel_taskDlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ll_bg_taskDlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chBx_alarm_taskDlg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                TaskModel task = new TaskModel();
                task.setId(task_id);
                if(chBx_alarm_taskDlg.isChecked())
                    task.setHasAlarmSound(true);
                else
                    task.setHasAlarmSound(false);
                db.editTask(task);
            }
        });
    }

    private String fixDate(String date) {
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
    }

    private String fixPerDate(String date) {
        String[] temp = date.split("/");
        return temp[2] + DateTools.convertNum2StrShamsiMonth(Integer.parseInt(temp[1])) + temp[0];
    }

    private String getSpinnerTextByMinutes(Integer minutes)
    {
        if(minutes == null)
            return "بدون یادآور";
        if(minutes.equals(0))
            return "همان موقع";
        if(minutes.equals(-5))
            return "5 دقیقه زودتر";
        if(minutes.equals(-10))
            return "10 دقیقه زودتر";
        if(minutes.equals(-15))
            return "15 دقیقه زودتر";
        if(minutes.equals(-20))
            return "20 دقیقه زودتر";
        if(minutes.equals(-25))
            return "25 دقیقه زودتر";
        if(minutes.equals(-30))
            return "30 دقیقه زودتر";
        if(minutes.equals(-45))
            return "45 دقیقه زودتر";
        if(minutes.equals(-60))
            return "یک ساعت زودتر";
        if(minutes.equals(-120))
            return "دو ساعت زودتر";
        if(minutes.equals(-180))
            return "سه ساعت زودتر";
        if(minutes.equals(-720))
            return "12 ساعت زودتر";
        if(minutes.equals(-1440))
            return "24 ساعت زودتر";
        if(minutes.equals(-2880))
            return "دو روز زودتر";
        if(minutes.equals(-10080))
            return "یک هفته زودتر";
        return null;
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

//            if (intent.getStringExtra("fromDialog") != null)
//            {
//            }
        }

    };

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mMeysamBroadcastReceiver);
        session.removeItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY);
        super.onDestroy();
    }
}
