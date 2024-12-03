package ir.fardan7eghlim.tri.Views.Home.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appolica.flubber.Flubber;

import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import io.saeid.oghat.PrayerTime;
import ir.fardan7eghlim.tri.Models.CalenderEventModel;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.TaskModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseFragment;
import ir.fardan7eghlim.tri.Models.Utility.CalendarTool;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.Models.widgets.WrapContentListView;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Views.Home.CustomAdapterList.CustomAdapterList_task;
import ir.fardan7eghlim.tri.Views.Task.InsertTaskActivity;
import ir.fardan7eghlim.tri.Views.Home.MainActivity;
import ir.fardan7eghlim.tri.Views.Home.utility.DateTools;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.PersianCalendarHandler;
import ir.mirrajabi.persiancalendar.core.interfaces.OnDayClickedListener;
import ir.mirrajabi.persiancalendar.core.interfaces.OnMonthChangedListener;
import ir.mirrajabi.persiancalendar.core.models.CalendarEvent;
import ir.mirrajabi.persiancalendar.core.models.Day;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalndrFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalndrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalndrFragment extends BaseFragment {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //  Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageView btn_prev_month;
    private ImageView btn_next_month;

    private PersianCalendarView persianCalendarView;
    private PersianCalendarHandler calendarHandler;
    private PersianDate today;
    private PersianDate selected_day;
    private boolean isToday;
    private PrayerTime prayerTime;

    private int month_change;

    private TextView txt_shamsi_date;
    private TextView txt_miladi_date;
    private TextView txt_ghamari_date;
    private TextView txt_pray_nimeshab;
    private TextView txt_pray_maghreb;
    private TextView txt_pray_ghoroob;
    private TextView txt_pray_zohr;
    private TextView txt_pray_toloo;
    private TextView txt_pray_sobh;
    private TextView txt_events_date;
    private TextView txt_oghat_title;
    private ImageView img_add_instask;
    private FrameLayout fr_add_instask;
    private LinearLayout ll_oghat_title;

    private ViewGroup header;

    private WrapContentListView lv_list_today_tasks;
    private CustomAdapterList_task CALM01;

    private String all_events;

    public CalndrFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalndrFragment.
     */
    //  Rename and change types and number of parameters
    public static CalndrFragment newInstance(String param1, String param2) {
        CalndrFragment fragment = new CalndrFragment();
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

        if (MainActivity.PD != null)
            MainActivity.PD.hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (session.getBoolianItem(SessionModel.KEY_SHOW_PRAYER_TIMES, true)) {
            //if city has been changed
            String lastTilte4city = txt_oghat_title.getText().toString();
            txt_oghat_title.setText("اوقات شرعی به افق " + getNameOfCity());
            if (!txt_oghat_title.getText().toString().equals(lastTilte4city) && !lastTilte4city.equals("..."))
                setGhamariAndMiladiText(selected_day);
            ll_oghat_title.setVisibility(View.VISIBLE);
        } else {
            ll_oghat_title.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //sets
        btn_prev_month = getActivity().findViewById(R.id.btn_prev_month);
        btn_next_month = getActivity().findViewById(R.id.btn_next_month);
        persianCalendarView = getActivity().findViewById(R.id.persian_calendar);
        calendarHandler = persianCalendarView.getCalendar();
        today = calendarHandler.getToday();
        selected_day = calendarHandler.getToday();
        isToday = true;
        all_events = "";
        month_change=0;
        txt_shamsi_date = getActivity().findViewById(R.id.txt_shamsi_date);
        txt_miladi_date = getActivity().findViewById(R.id.txt_miladi_date);
        txt_ghamari_date = getActivity().findViewById(R.id.txt_ghamari_date);
        txt_pray_nimeshab = getActivity().findViewById(R.id.txt_pray_nimeshab);
        txt_pray_maghreb = getActivity().findViewById(R.id.txt_pray_maghreb);
        txt_pray_ghoroob = getActivity().findViewById(R.id.txt_pray_ghoroob);
        txt_pray_zohr = getActivity().findViewById(R.id.txt_pray_zohr);
        txt_pray_toloo = getActivity().findViewById(R.id.txt_pray_toloo);
        txt_pray_sobh = getActivity().findViewById(R.id.txt_pray_sobh);
        txt_events_date = getActivity().findViewById(R.id.txt_events_date);
        txt_oghat_title = getActivity().findViewById(R.id.txt_oghat_title);
        lv_list_today_tasks = getActivity().findViewById(R.id.lv_list_today_tasks);
        img_add_instask = getActivity().findViewById(R.id.img_add_instask);
        fr_add_instask = getActivity().findViewById(R.id.fr_add_instask);
        LayoutInflater inflater = getLayoutInflater();
        header = (ViewGroup) inflater.inflate(R.layout.tasks_header, lv_list_today_tasks, false);
        ll_oghat_title = getActivity().findViewById(R.id.ll_oghat_title);
        //set contents
        initMonthTable();
        setEvents(today);
        setShamsiText(today);
        setGhamariAndMiladiText(today);
//        setTasks(today);

        //event for clicking calendar
        persianCalendarView.setOnDayClickedListener(new OnDayClickedListener() {
            @Override
            public void onClick(PersianDate date) {
                selected_day = date;
                if (date.equals(today)) isToday = true;
                else isToday = false;
                setRedOrBack_forHoliday(false);

                setEvents(date);
                setShamsiText(date);
                setGhamariAndMiladiText(date);
            }
        });
        //event for next and prev month
        btn_prev_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month_change++;
                persianCalendarView.goToPreviousMonth();
            }
        });
        btn_next_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                month_change--;
                persianCalendarView.goToNextMonth();
            }
        });

        persianCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onChanged(PersianDate date) {
                txt_events_date.setText("");
                txt_shamsi_date.setText(calendarHandler.getMonthName(date) + " " + date.getYear());
                calendarHandler.clearLocalEvents();
                initMonthTable();
//                setEvents(date);
//                setShamsiText(date);
//                setGhamariAndMiladiText(date);
            }
        });
        //button add task for a day
        Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION)
                .repeatCount(9999999)
                .duration(20000)
                .force(5)
                .createFor(img_add_instask)
                .start();
    }

    private void fillList_today(ArrayList<TaskModel> tasks) {
        if (CALM01 != null) CALM01.clear();
        CALM01 = new CustomAdapterList_task(getActivity().getApplicationContext(),
                new ArrayList<Object>(tasks)
                , isToday);
        if (lv_list_today_tasks.getHeaderViewsCount() > 0)
            lv_list_today_tasks.removeHeaderView(header);
        if (tasks.size() > 0) {
            lv_list_today_tasks.setAdapter(null);
            lv_list_today_tasks.setAdapter(CALM01);
            lv_list_today_tasks.invalidateViews();
        } else {
            lv_list_today_tasks.addHeaderView(header, null, false);
        }
    }

    private void showTaskOfDay(String year, String month, String day_of_month) {
        //task
        PersianCalendar cal = new PersianCalendar();
        CalendarTool ct = new CalendarTool();
        ct.setGregorianDate(new Integer(year), new Integer(month), new Integer(day_of_month));
        cal.setPersianDate(ct.getIranianYear(), ct.getIranianMonth(), ct.getIranianDay());
        String st_date = String.valueOf(ct.getIranianYear())+Utility.addZeroFirst(String.valueOf(ct.getIranianMonth()),2)+Utility.addZeroFirst(String.valueOf(ct.getIranianDay()),2);
//        long date = Integer.parseInt(cal.getPersianShortDate().replace("/", ""));
        long date = Integer.parseInt(st_date);

        ArrayList<TaskModel> tasks = db.getTasksByTime(String.valueOf(cal.getPersianYear()), String.valueOf(cal.getPersianMonth()),
                String.valueOf(Utility.getDayNumberOfWeek(cal.getPersianWeekDayName())),
                String.valueOf(cal.getPersianDay()), null, date);
        fillList_today(tasks);
    }


//    //set tasks
//    private void setTasks(PersianDate today) {
//        // meysam
////        ArrayList<TaskModel> tasks=db.getTasksByTime(today.getYear(),today.getMonth(),today.getDayOfWeek(),today.getDayOfMonth(),);
//    }

    //set days
    private void setShamsiText(PersianDate today) {
        String shamsiDate = today.getDayOfMonth() + " " + DateTools.convertNum2StrShamsiMonth(today.getMonth()) + " " + today.getYear();
        txt_shamsi_date.setText(shamsiDate);
    }
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

    private void setGhamariAndMiladiText(PersianDate today) {
        //convert shamsi to miladi
        String miladi = Utility.convertDatePersian2Gorgeian(today.getYear() + "/" + today.getMonth() + "/" + today.getDayOfMonth());
        String[] temp = miladi.split("/");
        txt_miladi_date.setText(temp[0] + "." + temp[1] + "." + temp[2] + "\n (" + DateTools.convertNum2StrMiladiMonthEng(Integer.parseInt(temp[1])) + ")");
        //ghamari
        Chronology iso = ISOChronology.getInstanceUTC();
        Chronology hijri = IslamicChronology.getInstanceUTC();
//        DateTimeZone tz=DateTimeZone.forID("Asia/Kabul");
//        Chronology hijri = IslamicChronology.getInstance(tz, IslamicChronology.LEAP_YEAR_15_BASED).getInstanceUTC();
        LocalDate todayIso = new LocalDate(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), iso);
        LocalDate todayHijri = new LocalDate(todayIso.toDateTimeAtStartOfDay(), hijri);
        todayHijri = todayHijri.plusDays(session.getIntegerItem(SessionModel.KEY_CALENDER_GHAMARI_CORRECTION, 0));
        todayHijri=DateTools.fixGhamariDate(todayHijri,temp[0]+"/"+(temp[1].length()==1?"0"+temp[1]:temp[1])+"/"+(temp[2].length()==1?"0"+temp[2]:temp[2]));
        String temp_arab[] = todayHijri.toString().split("-");
        temp_arab[1] = Utility.removeZeroFirst(temp_arab[1]);
        String ghmariDate = temp_arab[0] + "/" + temp_arab[1] + "/" + temp_arab[2] + "\n (" + DateTools.convertNum2StrGhamariMonth(Integer.parseInt(temp_arab[1])) + ")";
        txt_ghamari_date.setText(ghmariDate);


        //get events from ghamari date
//        ArrayList<CalenderEventModel> calenderEvents = db.getCalenderEventsByDateAndType(Utility.addZeroFirst(temp_arab[1], 2) + Utility.addZeroFirst(temp_arab[2], 2), CalenderEventModel.TYPE_GHAMARI);

//        String temp_ghamari_events="";
//        for(CalenderEventModel calenderEvent:calenderEvents){
//            temp_ghamari_events+=" "+calenderEvent.getDescription();
//            if(calenderEvent.getHoliday())
//                setRedOrBack_forHoliday(true);
//        }
//        txt_events_date.setText(txt_events_date.getText().toString()+" "+temp_ghamari_events);

        //set Oghats
        if (session.getBoolianItem(SessionModel.KEY_SHOW_PRAYER_TIMES, true)) {
            double latitude = 35.6891975;
            double longitude = 51.388973599999986;
            String temp_latitude = session.getStringItem(SessionModel.KEY_CITY_LATITUDE);
            if (temp_latitude != null) latitude = Double.parseDouble(temp_latitude);
            temp_latitude = session.getStringItem(SessionModel.KEY_CITY_LONGITUDE);
            if (temp_latitude != null) longitude = Double.parseDouble(temp_latitude);

            Double timezone = (TimeZone.getTimeZone(Time.getCurrentTimezone()).getOffset(System.currentTimeMillis())) / (1000.0 * 60.0 * 60.0);
            prayerTime = PrayerTime.getInstance()
                    .setDate(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2])) // in gregorian
                    .setLatLong(latitude, longitude)
                    .setTimeZone(timezone)
                    .calculate();
            txt_pray_sobh.setText(DateTools.fixZeroBeforeTime(prayerTime.getSobhAzan()));
            txt_pray_toloo.setText(DateTools.fixZeroBeforeTime(prayerTime.getToloo()));
            txt_pray_zohr.setText(DateTools.fixZeroBeforeTime(prayerTime.getZohrAzan()));
            txt_pray_ghoroob.setText(DateTools.fixZeroBeforeTime(prayerTime.getGhoroob()));
            txt_pray_maghreb.setText(DateTools.fixZeroBeforeTime(prayerTime.getMaghrebAzan()));
            txt_pray_nimeshab.setText(DateTools.fixZeroBeforeTime(prayerTime.getMidnight()));
            ll_oghat_title.setVisibility(View.VISIBLE);
        } else {
            ll_oghat_title.setVisibility(View.GONE);
        }
        //tasks
        final int persian_day_of_week = getDayOfWeek(temp[0] + "-" + temp[1] + "-" + temp[2]);
        showTaskOfDay(temp[0], temp[1], temp[2]);
        //get name of week
        final String persian_date = today.getYear() + "/" + today.getMonth() + "/" + today.getDayOfMonth();
        fr_add_instask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), InsertTaskActivity.class);
                intent.putExtra("persian_date", persian_date);
                intent.putExtra("persian_day_of_week", persian_day_of_week+"");
                session.saveItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, true);
                startActivity(intent);
            }
        });
    }

    //intialize month table
    private void initMonthTable() {
        if(calendarHandler.getLocalEvents().size()>0) return;
        for (Day day : calendarHandler.getDays(month_change)) {
//            day.setHoliday(false);
            day.setEvent(false);
            //convert shamsi to miladi
            String miladi = Utility.convertDatePersian2Gorgeian(day.getPersianDate().getYear() + "/" + day.getPersianDate().getMonth() + "/" + day.getPersianDate().getDayOfMonth());
            String[] temp = miladi.split("/");
            txt_miladi_date.setText(temp[0] + "." + temp[1] + "." + temp[2] + "\n (" + DateTools.convertNum2StrMiladiMonthEng(Integer.parseInt(temp[1])) + ")");
            //ghamari
            Chronology iso = ISOChronology.getInstanceUTC();
            Chronology hijri = IslamicChronology.getInstanceUTC();
            LocalDate todayIso = new LocalDate(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), iso);
            LocalDate todayHijri = new LocalDate(todayIso.toDateTimeAtStartOfDay(), hijri);
            todayHijri = todayHijri.plusDays(session.getIntegerItem(SessionModel.KEY_CALENDER_GHAMARI_CORRECTION, 0));
            todayHijri=DateTools.fixGhamariDate(todayHijri,temp[0]+"/"+(temp[1].length()==1?"0"+temp[1]:temp[1])+"/"+(temp[2].length()==1?"0"+temp[2]:temp[2]));
            String temp_arab[] = todayHijri.toString().split("-");
            temp_arab[1] = Utility.removeZeroFirst(temp_arab[1]);
            ArrayList<CalenderEventModel> calenderEvents = db.getCalenderEventsByDateAndType(Utility.addZeroFirst(temp_arab[1], 2) + Utility.addZeroFirst(temp_arab[2], 2), CalenderEventModel.TYPE_GHAMARI);
//            ArrayList<CalenderEventModel> calenderEvents = db.getCalenderEventsByMonthAndType(Utility.addZeroFirst(temp_arab[1], 2) , CalenderEventModel.TYPE_GHAMARI);

//            String x;
//            if(calenderEvents.size() > 0)
//                x = "0";
            String temp_ghamari_events = "";
            boolean is_holiday=false;
            for (CalenderEventModel calenderEvent : calenderEvents) {
                temp_ghamari_events += " " + calenderEvent.getDescription();
                if (calenderEvent.getHoliday()) is_holiday=true;

            }
            if(!temp_ghamari_events.equals(""))
                calendarHandler.addLocalEvent(new CalendarEvent(day.getPersianDate(), temp_ghamari_events, is_holiday));
        }
    }

    //get day of week
    private int getDayOfWeek(String day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = dateFormat.parse(day);
            Calendar c = Calendar.getInstance();
            c.setFirstDayOfWeek(Calendar.SATURDAY);
            c.setTime(d);
            return c.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void setEvents(PersianDate today) {
        String temp = "";
        for (CalendarEvent e : calendarHandler.getAllEventsForDay(today)) {
            temp += " " + e.getTitle();
            if (e.isHoliday()) {
                setRedOrBack_forHoliday(true);
            }
        }
        txt_events_date.setText(temp);
    }

    private void setRedOrBack_forHoliday(boolean is_holday) {
        if (is_holday) {
            txt_events_date.setTextColor(getResources().getColor(R.color.drp_orange2));
        } else {
            txt_events_date.setTextColor(Color.BLACK);
        }
    }

    private String getNameOfCity() {
        String temp = session.getStringItem(SessionModel.KEY_CITY_NAME);
        if (temp == null) temp = "تهران";
        return temp;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calndr, container, false);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
