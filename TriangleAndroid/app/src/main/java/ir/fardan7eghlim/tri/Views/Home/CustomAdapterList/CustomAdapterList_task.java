package ir.fardan7eghlim.tri.Views.Home.CustomAdapterList;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.TaskModel;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Views.Home.MainActivity;
import ir.fardan7eghlim.tri.Views.Home.SettingActivity;
import ir.fardan7eghlim.tri.Views.Home.utility.DateTools;
import ir.fardan7eghlim.tri.Views.Task.ShowTaskActivity;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission;

public class CustomAdapterList_task extends BaseAdapter implements Observer {

    private Context context;
    private boolean isToday;
    private java.util.List<Object> List;
    private static LayoutInflater inflater = null;
    private CustomAdapterList_task CAL = this;
    private Object foregn_key_obj;

    public CustomAdapterList_task(Context c, java.util.List<Object> list, boolean tag) {
        // TODO Auto-generated constructor stub
        List = list;
        isToday = tag;
        context = c;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public CustomAdapterList_task(Context c, java.util.List<Object> list, boolean tag, Object obj) {
        // TODO Auto-generated constructor stub
        List = list;
        isToday = tag;
        context = c;
        foregn_key_obj = obj;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void clear() {
        // TODO Auto-generated method stub
        List.clear();

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    //    public void updateAdapter(java.util.List<Object> list) {
//        this.List = list;
//        //and call notifyDataSetChanged
//        notifyDataSetChanged();
//    }
    public void updateAdapter(java.util.List<Object> newlist) {
        List.clear();
        List.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.notifyDataSetChanged();
    }

    public class Holder {
        LinearLayout ll_ca_box_task;
        TextView txt_ca_remndTime_task;
        TextView txt_ca_title_task;
        TextView txt_ca_time_task;
        ImageView img_ca_logo_task;

        public Holder(LinearLayout ll_ca_box_task, TextView txt_ca_remndTime_task, TextView txt_ca_title_task, TextView txt_ca_time_task, ImageView img_ca_logo_task) {
            this.ll_ca_box_task = ll_ca_box_task;
            this.txt_ca_remndTime_task = txt_ca_remndTime_task;
            this.txt_ca_title_task = txt_ca_title_task;
            this.txt_ca_time_task = txt_ca_time_task;
            this.img_ca_logo_task = img_ca_logo_task;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        final Holder holder = new Holder(new LinearLayout(context.getApplicationContext()), new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()), new TextView(context.getApplicationContext()), new ImageView(context.getApplicationContext()));
        final View rowView = inflater.inflate(R.layout.custom_adapter_task, null);
        holder.txt_ca_remndTime_task = rowView.findViewById(R.id.txt_ca_remndTime_task);
        holder.txt_ca_title_task = rowView.findViewById(R.id.txt_ca_title_task);
        holder.txt_ca_time_task = rowView.findViewById(R.id.txt_ca_time_task);
        holder.img_ca_logo_task = rowView.findViewById(R.id.img_ca_logo_task);
        holder.ll_ca_box_task = rowView.findViewById(R.id.ll_ca_box_task);
        //sets
        final TaskModel task = (TaskModel) List.get(position);
        holder.img_ca_logo_task.setImageResource(context.getResources().getIdentifier("cr_" + task.getType(), "drawable", context.getPackageName()));
        holder.txt_ca_title_task.setText(task.getDescription());
        if (task.getType() == 0) {
            if (task.getEndTime() != null) {
                if (task.getStartTime().equals(task.getEndTime())) {
                    holder.txt_ca_time_task.setText(task.getStartTime());
                } else {
                    holder.txt_ca_time_task.setText(task.getStartTime() + " ~ " + (task.getEndTime() == null ? "" : task.getEndTime()));
                }
            } else {
                holder.txt_ca_time_task.setText(task.getStartTime());
            }
        } else if (task.getType() == 1) {
            holder.txt_ca_time_task.setText(task.getStartTime() + " (روزانه)");
        } else if (task.getType() == 2) {
            String temp = task.getStartTime() + " (";
            for(int i=0;i<task.getDayOfWeek().size();i++)
            {
                temp +=convertDayNum2Name(Integer.parseInt(task.getDayOfWeek().get(i)));
                if(i != task.getDayOfWeek().size()-1)
                    temp +=",";
            }
            temp +=  ")";
            holder.txt_ca_time_task.setText(temp);
        } else if (task.getType() == 3) {
            holder.txt_ca_time_task.setText(task.getStartTime() + " ( روز " + task.getDayOfMonth() + " هرماه)");
        }

        //if is today
        if (isToday) {
            String rem_from_now = DateTools.minuesTimeWithNow(task.getStartTime());
            String rem_to_finish = DateTools.minuesTimeWithNow(task.getEndTime());
            if (!rem_from_now.equals("00:00")) {
                holder.txt_ca_remndTime_task.setText(getCorrectRemindTime(rem_from_now));
            } else if (!rem_to_finish.equals("00:00")) {
                holder.txt_ca_remndTime_task.setText("در حال انجام");
            } else {
                holder.txt_ca_remndTime_task.setText("گذشته");
                holder.img_ca_logo_task.setImageResource(R.drawable.cr_4);
                holder.ll_ca_box_task.setBackgroundResource(R.drawable.rounded_gray3);
                holder.txt_ca_remndTime_task.setTextColor(Color.parseColor("#7AECECEC"));
                holder.txt_ca_time_task.setTextColor(Color.parseColor("#7AECECEC"));
                holder.txt_ca_title_task.setTextColor(Color.parseColor("#7AECECEC"));
            }
        } else {
            holder.txt_ca_remndTime_task.setVisibility(View.GONE);
        }
        holder.ll_ca_box_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detail_task_dialog(task.getId());
            }
        });
        //return
        return rowView;
    }
    private void detail_task_dialog(BigInteger task_id){
        Intent intent = new Intent(context, ShowTaskActivity.class);
        intent.putExtra("task_id", task_id+"");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        new SessionModel(context.getApplicationContext()).saveItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, true);
        context.startActivity(intent);
    }


    private String getCorrectRemindTime(String rem) {
        String h = rem.substring(0, rem.indexOf(":"));
        if (Integer.parseInt(h) == 0)
            return rem.substring(rem.indexOf(":") + 1) + "دقیقه دیگر";
        return rem.substring(0, rem.indexOf(":")) + "ساعت و" + "\n" + rem.substring(rem.indexOf(":") + 1) + "دقیقه دیگر";
    }

    private String convertDayNum2Name(int day_num) {
        String out = "";
        switch (day_num) {
            case 0:
                return "شنبه\u200Cها";
            case 1:
                return "یکشنبه\u200Cها";
            case 2:
                return "دوشنبه\u200Cها";
            case 3:
                return "سه\u200Cشنبه\u200Cها";
            case 4:
                return "چهارشنبه\u200Cها";
            case 5:
                return "پنجشنبه\u200Cها";
            case 6:
                return "جمعه\u200Cها";
            case 7:
                return "شنبه\u200Cها";
        }
        return out;
    }

    public static String getDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static LayoutInflater getInflater() {
        return inflater;
    }

    public static void setInflater(LayoutInflater inflater) {
        CustomAdapterList_task.inflater = inflater;
    }

    private void dialContactPhone(final String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    private boolean canAccessCallPhone() {
        return (hasPermission(Manifest.permission.CALL_PHONE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == checkCallingOrSelfPermission(context, perm));
    }

}