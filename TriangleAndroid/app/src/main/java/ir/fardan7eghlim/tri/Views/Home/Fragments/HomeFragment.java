package ir.fardan7eghlim.tri.Views.Home.Fragments;

import android.Manifest;
import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.facebook.rebound.ui.Util;

import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.TaskModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseFragment;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.Models.widgets.WrapContentListView;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Views.Home.CustomAdapterList.CustomAdapterList_task;
import ir.fardan7eghlim.tri.Views.Note.InsertNoteActivity;
import ir.fardan7eghlim.tri.Views.Task.InsertTaskActivity;
import ir.fardan7eghlim.tri.Views.Home.MainActivity;
import ir.fardan7eghlim.tri.Views.Home.utility.DateTools;
import ir.fardan7eghlim.tri.Views.Home.utility.Utilities;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private TextView txt_shamsi_date;
    private TextView txt_shamsi_date_name__week;
    private TextView txt_miladi_date;
    private TextView txt_ghamri_date;
    private String solarCalendarc;
    private WrapContentListView lv_list_today_tasks;
    private WrapContentListView lv_list_tomorrow_tasks;
    private CustomAdapterList_task CALM01;
    private CustomAdapterList_task CALM02;
    private ViewGroup header1;
    private ViewGroup header2;
    private ViewGroup footer;
    private LinearLayout ll_new_task_home;
    private LinearLayout ll_new_note_home;

    private Animator anm_btnAddTask;

    private ImageView iv_btnAddTask;

    private static final int INITIAL_REQUEST_SYSTEM_ALERT_WINDOW = 1340;


    //meysam - added in 13970318
    private Handler customHandler = new Handler();
    private int timeWaitBeforeRefresh = 60000;//meysam - must be 60000
    private Runnable refreshTimerThread = new Runnable() {

        public void run() {



            //meysam - on minute tick
            CALM02.notifyDataSetChanged();
            CALM01.notifyDataSetChanged();
            //////////////////////////////////////////////////////////

            //meysam - for continuing timer
            customHandler.postDelayed(refreshTimerThread, timeWaitBeforeRefresh);
            ////////////////////////////////

        }

    };
    ////////////////////////////////////////////////////////

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    //  Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //meysam - added in 13970318
        if (customHandler == null)
            customHandler = new Handler();
        customHandler.postDelayed(refreshTimerThread, timeWaitBeforeRefresh);
        /////////////////////////////////

        //shamsi date
        solarCalendarc = Utilities.getCurrentShamsidate();

//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMeysamBroadcastReceiver,
//                new IntentFilter("home_fragment_broadcast"));

        if (MainActivity.PD != null)
            MainActivity.PD.hide();


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        txt_shamsi_date = getView().findViewById(R.id.txt_shamsi_date);
        txt_shamsi_date_name__week = getActivity().findViewById(R.id.txt_shamsi_date_name__week);
        txt_miladi_date = getView().findViewById(R.id.txt_miladi_date);
        txt_ghamri_date = getView().findViewById(R.id.txt_ghamri_date);
        //headers
        LayoutInflater inflater = getLayoutInflater();
        footer = (ViewGroup) inflater.inflate(R.layout.empty_space_x1, lv_list_tomorrow_tasks, false);
        //date
        String name_of_week = new SimpleDateFormat("EEE").format(Calendar.getInstance().getTime());
        int year_miladi = (Integer.parseInt(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime())));
        int month_miladi = (Integer.parseInt(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime())));
        int day_miladi = (Integer.parseInt(new SimpleDateFormat("d").format(Calendar.getInstance().getTime())));
        //shamsi calendar
        String temp[] = solarCalendarc.split("/");
        String shamsiDate = DateTools.fixZeroBeforeTime(temp[2]) + " " + DateTools.convertNum2StrShamsiMonth(Integer.parseInt(temp[1])) + " " + temp[0];
        txt_shamsi_date.setText(shamsiDate);
        txt_shamsi_date_name__week.setText(DateTools.convertWeeksNameMiladi2Shamsi(name_of_week));
        //miladi calendar
        String miladiDate = DateTools.fixZeroBeforeTime(day_miladi + "") + " " + DateTools.convertNum2StrMiladiMonth(month_miladi) + " " + " " + year_miladi;
        txt_miladi_date.setText(miladiDate);
        //ghamari calendar
        Chronology iso = ISOChronology.getInstanceUTC();
        Chronology hijri = IslamicChronology.getInstanceUTC();
        LocalDate todayIso = new LocalDate(year_miladi, month_miladi, day_miladi, iso);
        LocalDate todayHijri = new LocalDate(todayIso.toDateTimeAtStartOfDay(), hijri);
        todayHijri=todayHijri.plusDays(session.getIntegerItem(SessionModel.KEY_CALENDER_GHAMARI_CORRECTION,0));
        todayHijri=DateTools.fixGhamariDate(todayHijri,year_miladi+"/"+(month_miladi<10?"0"+month_miladi:month_miladi)+"/"+(day_miladi<10?"0"+day_miladi:day_miladi));
        temp = todayHijri.toString().split("-");
        String ghmariDate = temp[2] + " " + DateTools.convertNum2StrGhamariMonth(Integer.parseInt(temp[1])) + " " + DateTools.fixZeroBeforeTime(temp[0]);
        txt_ghamri_date.setText(ghmariDate);

        //tasks
        LinearLayout ll_main_homefragment=getActivity().findViewById(R.id.ll_main_homefragment);
        ll_main_homefragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_new_note_home.setVisibility(View.GONE);
                ll_new_task_home.setVisibility(View.GONE);
            }
        });
        ll_new_note_home = getActivity().findViewById(R.id.ll_new_note_home);
        ll_new_note_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.saveItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, true);
                Intent intent = new Intent(getContext(), InsertNoteActivity.class);
                startActivity(intent);
            }
        });
        ll_new_task_home = getActivity().findViewById(R.id.ll_new_task_home);
        ll_new_task_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.saveItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, true);
                Intent intent = new Intent(getContext(), InsertTaskActivity.class);
                startActivity(intent);
            }
        });
        FrameLayout btn_add_instask = getActivity().findViewById(R.id.btn_add_instask);
        btn_add_instask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ll_new_note_home.getVisibility() == View.GONE) {
                    ll_new_note_home.setVisibility(View.VISIBLE);
                    Flubber.with()
                            .animation(Flubber.AnimationPreset.FADE_IN_UP)
                            .repeatCount(1)
                            .duration(1500)
                            .createFor(ll_new_note_home)
                            .start();
                    ll_new_task_home.setVisibility(View.VISIBLE);
                    Flubber.with()
                            .animation(Flubber.AnimationPreset.FADE_IN_RIGHT)
                            .repeatCount(1)
                            .duration(1500)
                            .createFor(ll_new_task_home)
                            .start();
                }else{
                    ll_new_note_home.setVisibility(View.GONE);
                    ll_new_task_home.setVisibility(View.GONE);
                }
            }
        });

        iv_btnAddTask = getActivity().findViewById(R.id.img_add_instask);

        anm_btnAddTask = Flubber.with().listener(flubberAddTaskButtonListener)
                .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                .delay(10000)
                .repeatCount(9999999)                              // Repeat once
                .duration(30000)  // Last for 1000 milliseconds(1 second)
                .force(5)
                .createFor(iv_btnAddTask);
        anm_btnAddTask.start();// Apply it to the view
        //////////////////////////////////////////

        //task lists
        lv_list_today_tasks = getActivity().findViewById(R.id.lv_list_today_tasks);
        lv_list_tomorrow_tasks = getActivity().findViewById(R.id.lv_list_tomorrow_tasks);
        lv_list_tomorrow_tasks.addFooterView(footer, null, false);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
//        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMeysamBroadcastReceiver);

        //meysam - added in 13970318
        if (customHandler != null) {
            customHandler.removeCallbacks(refreshTimerThread);
            customHandler = null;
            timeWaitBeforeRefresh = 60000;
        }
        ///////////////////////////////

        CustomAdapterList_task.setInflater(null);
        if(CALM01 != null)
            CALM01.setInflater(null);
        if(CALM02 != null)
            CALM02.setInflater(null);

        flubberAddTaskButtonListener = null;
        if (anm_btnAddTask != null) {
            anm_btnAddTask.end();
            anm_btnAddTask.cancel();
            anm_btnAddTask.removeAllListeners();
        }

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //showing tasks
        ll_new_note_home.setVisibility(View.GONE);
        ll_new_task_home.setVisibility(View.GONE);
        showingTasks();

        if (Build.VERSION.SDK_INT >= 23)
        {
            // Marshmallow+
            Toast.makeText(getContext(), "در چک اجازه هوم", Toast.LENGTH_SHORT).show();
            checkDrawOverlayPermission();
        }
        else
        {
            // Pre-Marshmallow
            session.saveItem(SessionModel.KEY_IS_ACCESS_ALERT_WINDOW_PERMISSION_GRANTED,true);
        }
    }

    private void showingTasks() {
        //today
        PersianCalendar pc = new PersianCalendar();
        long date = Integer.parseInt(pc.getPersianShortDate().replace("/",""));
        ArrayList<TaskModel> tasks = db.getTasksByTime(String.valueOf(pc.getPersianYear()), Utility.addZeroFirst(String.valueOf(pc.getPersianMonth()),2),
                String.valueOf(Utility.getDayNumberOfWeek(pc.getPersianWeekDayName())),
               String.valueOf(pc.getPersianDay()), null, date);
        fillList_today(tasks);

        //tomorrow
        pc.addPersianDate(PersianCalendar.DATE,1);
        date = Integer.parseInt(pc.getPersianShortDate().replace("/",""));
        ArrayList<TaskModel> tasks_tommorow = db.getTasksByTime(String.valueOf(pc.getPersianYear()), String.valueOf(pc.getPersianMonth()),
                String.valueOf(Utility.getDayNumberOfWeek(pc.getPersianWeekDayName())),
                String.valueOf(pc.getPersianDay()), null, date);
        fillList_tommorw(tasks_tommorow);
    }


    private void fillList_today(ArrayList<TaskModel> tasks) {
        CALM01 = new CustomAdapterList_task(getActivity().getApplicationContext(),
                new ArrayList<Object>(tasks)
                , true);
        if (tasks.size() > 0) {
            lv_list_today_tasks.setAdapter(null);
            lv_list_today_tasks.setAdapter(CALM01);
            lv_list_today_tasks.invalidateViews();
        }
    }

    private void fillList_tommorw(ArrayList<TaskModel> tasks) {
        CALM02 = new CustomAdapterList_task(getActivity().getApplicationContext(),
                new ArrayList<Object>(tasks)
                , false);
        if (tasks.size() > 0) {
            lv_list_tomorrow_tasks.setAdapter(null);
            lv_list_tomorrow_tasks.setAdapter(CALM02);
            lv_list_tomorrow_tasks.invalidateViews();
        }
    }

//    // Our handler for received Intents. This will be called whenever an Intent
//// with an action named "custom-event-name" is broadcasted.
//    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // Get extra data included in the Intent
//
//            if (intent.getStringExtra("timerTick") != null) {
//
////                Utility.displayToast(getContext(), "یک دقیقه گذشت", Toast.LENGTH_SHORT);
//                CALM02.notifyDataSetChanged();
//                CALM01.notifyDataSetChanged();
//            }
//        }
//
//    };

    public Animator.AnimatorListener flubberAddTaskButtonListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            anm_btnAddTask.removeAllListeners();
            anm_btnAddTask = Flubber.with().listener(flubberAddTaskButtonListener)
                    .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                    .repeatCount(9999999)                              // Repeat once
                    .duration(30000)  // Last for 1000 milliseconds(1 second)
                    .force(5)
                    .createFor(iv_btnAddTask);
            anm_btnAddTask.start();// Apply it to the view
            //////////////////////////////////////////
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private boolean canAccessAlertWindow() {
        return (hasPermission(Manifest.permission.SYSTEM_ALERT_WINDOW));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == checkCallingOrSelfPermission(getContext(),perm));
    }

    public void checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getContext())) {
                session.saveItem(SessionModel.KEY_IS_ACCESS_ALERT_WINDOW_PERMISSION_GRANTED,false);
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getContext().getPackageName()));
                /** request permission via start activity for result */
                startActivityForResult(intent, INITIAL_REQUEST_SYSTEM_ALERT_WINDOW);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        /** check if received result code
         is equal our requested code for draw permission  */
        if(requestCode == INITIAL_REQUEST_SYSTEM_ALERT_WINDOW) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(getContext())) {
                    // continue here - permission was granted
                    Toast.makeText(getContext(), "در جواب اجازه هوم 2", Toast.LENGTH_SHORT).show();
                    session.saveItem(SessionModel.KEY_IS_ACCESS_ALERT_WINDOW_PERMISSION_GRANTED,true);

                }
                else
                {
                    Toast.makeText(getContext(), "در جواب اجازه هوم 3", Toast.LENGTH_SHORT).show();
                    Utility.displayToast(getContext().getApplicationContext(),getString(R.string.error_permission_access), Toast.LENGTH_SHORT);
                }
            }
        }
    }

}
