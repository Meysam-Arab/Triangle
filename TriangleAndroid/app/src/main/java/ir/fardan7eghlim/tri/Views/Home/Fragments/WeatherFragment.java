package ir.fardan7eghlim.tri.Views.Home.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.tri.Controllers.WeatherController;
import ir.fardan7eghlim.tri.Models.DayModel;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseFragment;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.Models.WeatherModel;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Utils.RequestRespond;
import ir.fardan7eghlim.tri.Views.Home.MainActivity;
import ir.fardan7eghlim.tri.Views.Home.SettingActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeatherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends BaseFragment implements Observer {
    //  Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String city;

    private WeatherController wc;

    private OnFragmentInteractionListener mListener;

    private TextView tv_lastUpdate;
//    private ProgressDialog PD;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    //  Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
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

//        PD = new ProgressDialog(getContext());

        wc = new WeatherController(getContext());
        wc.addObserver(this);

        //meysam - اینجا از سرور پارسی جو صدا می زنیم ولی روز قبلی را از دیتا بیس میگیریم و ست میکنیم چون جواب از سرور شاید نیاید یا طول بکشد
        city=session.getStringItem(SessionModel.KEY_CITY_NAME);
        if(city==null) city="تهران";
//        if(Utility.isServerAvailable(getActivity().getApplicationContext(),"http://www.parsijoo.ir"))
        if(Utility.isNetworkAvailable(getActivity().getApplicationContext()))
        {
//            PD.show();
            wc.get(city);
        }

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMeysamBroadcastReceiver,
                new IntentFilter("weather_fragment_broadcast"));


        if(MainActivity.PD != null)
            MainActivity.PD.hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
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
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView txt_city_weather = getActivity().findViewById(R.id.txt_city_weather);
        tv_lastUpdate = getActivity().findViewById(R.id.txt_last_update_weather);
        tv_lastUpdate.setText("نامشخص");


        txt_city_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SettingActivity.class);
                session.saveItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, true);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume(){
        ArrayList<DayModel> temp = new ArrayList<>();
        if (session.getIntegerItem(SessionModel.KEY_DATABASE_TABLE_STATUS, 0) != 0 &&
                session.getIntegerItem(SessionModel.KEY_DATABASE_TABLE_STATUS, 0) != 1) {
            Integer todayDate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));
            if(session.getIntegerItem(SessionModel.KEY_WEATHER_LAST_UPDATED_DATE,Integer.valueOf(0)) == todayDate)
            {
                temp = db.getDays();
                String tempDate = " آخرین بروزرسانی: " +Utility.convertDateTimeGorgeian2Persian(new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(Calendar.getInstance().getTime()));
                tv_lastUpdate.setText(tempDate);
            }
            else
            {
                String tempDate = " آخرین بروزرسانی: " +"نامشخص";
                tv_lastUpdate.setText(tempDate);
            }

        }
        if(temp.size() > 0)
        {
            final WeatherModel weather = new WeatherModel();
            weather.setDays(temp);
            setWeather(weather.getDays().get(0));
            //meysam - make dialog to show predict weather of week
            setPreWeather(weather);

        }
        setNameOfCity();
        super.onResume();
    }

    @Override
    public void update(Observable o, Object arg) {
//        PD.hide();
        if (arg != null) {
            if (arg instanceof Boolean) {
                if (Boolean.parseBoolean(arg.toString()) == false) {
                    Utility.displayToast(getContext().getApplicationContext(), getContext().getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                }
            } else if (arg instanceof ArrayList) {
                if (((ArrayList) arg).get(0).toString().equals(RequestRespond.TAG_WEATHER_GET)) {
                    ArrayList<DayModel> temp = (ArrayList<DayModel>) ((ArrayList) arg).get(1);
                    if(temp.size()>0) {
                        final WeatherModel weather = new WeatherModel();
                        weather.setDays(temp);
                        if (session.getIntegerItem(SessionModel.KEY_DATABASE_TABLE_STATUS, 0) != 0 &&
                                session.getIntegerItem(SessionModel.KEY_DATABASE_TABLE_STATUS, 0) != 1) {
                            db.addDayes(temp, true);
                        }
                        setWeather(weather.getDays().get(0));
                        // amir - make dialog to show predict weather of week
                        setPreWeather(weather);

                        Integer lastUpdate = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));
                        session.saveItem(SessionModel.KEY_WEATHER_LAST_UPDATED_DATE, lastUpdate);
                        String tempDate = " آخرین بروزرسانی: " +Utility.convertDateTimeGorgeian2Persian(new SimpleDateFormat("yyyy-MM-dd HH:mm a").format(Calendar.getInstance().getTime()));
                        tv_lastUpdate.setText(tempDate);
                    }else{
                        // meysam-when parsijoo weather has error
                        temp = new ArrayList<>();
                        if (session.getIntegerItem(SessionModel.KEY_DATABASE_TABLE_STATUS, 0) != 0 &&
                                session.getIntegerItem(SessionModel.KEY_DATABASE_TABLE_STATUS, 0) != 1) {
                            temp = db.getDays();
                        }
                        if(temp.size() > 0)
                        {
                            final WeatherModel weather = new WeatherModel();
                            weather.setDays(temp);
                            setWeather(weather.getDays().get(0));
                            //meysam - make dialog to show predict weather of week
                            setPreWeather(weather);
                            setNameOfCity();
                        }
                    }
                    ///////////////////////////////////////////////////////
                } else {
                    Utility.displayToast(getContext().getApplicationContext(), getString(R.string.msg_MessageNotSpecified), Toast.LENGTH_SHORT);
                }
            } else if (arg instanceof Integer) {
                Utility.displayToast(getContext().getApplicationContext(), new RequestRespond(getContext()).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
            } else {
                Utility.displayToast(getContext().getApplicationContext(), getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            }
        } else {
            Utility.displayToast(getContext().getApplicationContext(), getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }
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
    //weather
    private int getImageForWeather(int st){
        switch (st) {
            case 1:
                return R.drawable.w_1a;
            case 2:
                return R.drawable.w_2a;
            case 3:
                return R.drawable.w_3a;
            case 4:
                return R.drawable.w_4a;
            case 5:
                return R.drawable.w_5a;
            case 6:
                return R.drawable.w_6a;
            case 7:
                return R.drawable.w_7a;
            case 8:
                return R.drawable.w_8a;
            case 9:
                return R.drawable.w_9a;
            case 10:
                return R.drawable.w_10a;
            case 12:
                return R.drawable.w_11a;
            case 38:
                return R.drawable.w_10a;
            case 39:
                return R.drawable.w_10a;
            case 40:
                return R.drawable.w_7a;
            case 41:
                return R.drawable.w_7a;
            case 42:
                return R.drawable.w_12a;
            case 43:
                return R.drawable.w_12a;
            default:
                return R.drawable.w_11a;
        }
    }
    private void setPreWeather(WeatherModel weather) {
        //sets
        ImageView img_weather_pr_01=getActivity().findViewById(R.id.img_weather_pr_01);
        ImageView img_weather_pr_02=getActivity().findViewById(R.id.img_weather_pr_02);
        ImageView img_weather_pr_03=getActivity().findViewById(R.id.img_weather_pr_03);
        ImageView img_weather_pr_04=getActivity().findViewById(R.id.img_weather_pr_04);
        ImageView img_weather_pr_05=getActivity().findViewById(R.id.img_weather_pr_05);
        ImageView img_weather_pr_06=getActivity().findViewById(R.id.img_weather_pr_06);
        TextView txt_weather_max_pr_01=getActivity().findViewById(R.id.txt_weather_max_pr_01);
        TextView txt_weather_max_pr_02=getActivity().findViewById(R.id.txt_weather_max_pr_02);
        TextView txt_weather_max_pr_03=getActivity().findViewById(R.id.txt_weather_max_pr_03);
        TextView txt_weather_max_pr_04=getActivity().findViewById(R.id.txt_weather_max_pr_04);
        TextView txt_weather_max_pr_05=getActivity().findViewById(R.id.txt_weather_max_pr_05);
        TextView txt_weather_max_pr_06=getActivity().findViewById(R.id.txt_weather_max_pr_06);
        TextView txt_weather_min_pr_01=getActivity().findViewById(R.id.txt_weather_min_pr_01);
        TextView txt_weather_min_pr_02=getActivity().findViewById(R.id.txt_weather_min_pr_02);
        TextView txt_weather_min_pr_03=getActivity().findViewById(R.id.txt_weather_min_pr_03);
        TextView txt_weather_min_pr_04=getActivity().findViewById(R.id.txt_weather_min_pr_04);
        TextView txt_weather_min_pr_05=getActivity().findViewById(R.id.txt_weather_min_pr_05);
        TextView txt_weather_min_pr_06=getActivity().findViewById(R.id.txt_weather_min_pr_06);
        TextView txt_weather_title_pr_01=getActivity().findViewById(R.id.txt_weather_title_pr_01);
        TextView txt_weather_title_pr_02=getActivity().findViewById(R.id.txt_weather_title_pr_02);
        TextView txt_weather_title_pr_03=getActivity().findViewById(R.id.txt_weather_title_pr_03);
        TextView txt_weather_title_pr_04=getActivity().findViewById(R.id.txt_weather_title_pr_04);
        TextView txt_weather_title_pr_05=getActivity().findViewById(R.id.txt_weather_title_pr_05);
        TextView txt_weather_title_pr_06=getActivity().findViewById(R.id.txt_weather_title_pr_06);
        //set images
        img_weather_pr_01.setImageResource(getImageForWeather(weather.getDays().get(1).getSymbol()));
        img_weather_pr_02.setImageResource(getImageForWeather(weather.getDays().get(2).getSymbol()));
        img_weather_pr_03.setImageResource(getImageForWeather(weather.getDays().get(3).getSymbol()));
        img_weather_pr_04.setImageResource(getImageForWeather(weather.getDays().get(4).getSymbol()));
        img_weather_pr_05.setImageResource(getImageForWeather(weather.getDays().get(5).getSymbol()));
        img_weather_pr_06.setImageResource(getImageForWeather(weather.getDays().get(6).getSymbol()));
        //set max temp
        txt_weather_max_pr_01.setText(weather.getDays().get(1).getMaxTemp()+"°");
        txt_weather_max_pr_02.setText(weather.getDays().get(2).getMaxTemp()+"°");
        txt_weather_max_pr_03.setText(weather.getDays().get(3).getMaxTemp()+"°");
        txt_weather_max_pr_04.setText(weather.getDays().get(4).getMaxTemp()+"°");
        txt_weather_max_pr_05.setText(weather.getDays().get(5).getMaxTemp()+"°");
        txt_weather_max_pr_06.setText(weather.getDays().get(6).getMaxTemp()+"°");
        //set min temp
        txt_weather_min_pr_01.setText(weather.getDays().get(1).getMinTemp()+"°");
        txt_weather_min_pr_02.setText(weather.getDays().get(2).getMinTemp()+"°");
        txt_weather_min_pr_03.setText(weather.getDays().get(3).getMinTemp()+"°");
        txt_weather_min_pr_04.setText(weather.getDays().get(4).getMinTemp()+"°");
        txt_weather_min_pr_05.setText(weather.getDays().get(5).getMinTemp()+"°");
        txt_weather_min_pr_06.setText(weather.getDays().get(6).getMinTemp()+"°");
        //set name
        txt_weather_title_pr_01.setText(weather.getDays().get(1).getDayName());
        txt_weather_title_pr_02.setText(weather.getDays().get(2).getDayName());
        txt_weather_title_pr_03.setText(weather.getDays().get(3).getDayName());
        txt_weather_title_pr_04.setText(weather.getDays().get(4).getDayName());
        txt_weather_title_pr_05.setText(weather.getDays().get(5).getDayName());
        txt_weather_title_pr_06.setText(weather.getDays().get(6).getDayName());
    }
    private void setNameOfCity() {
        TextView txt_city_weather = getActivity().findViewById(R.id.txt_city_weather);
        String temp=session.getStringItem(SessionModel.KEY_CITY_NAME,"تهران");
        if(temp==null) temp="تهران";
        txt_city_weather.setText(temp);
    }
    private void setWeather(DayModel weather_day) {
        TextView txt_weather_max = getActivity().findViewById(R.id.txt_weather_max);
        txt_weather_max.setText(weather_day.getMaxTemp() + "°");
        TextView txt_weather_stutus = getActivity().findViewById(R.id.txt_weather_stutus);
        txt_weather_stutus.setText(weather_day.getStatus());
        TextView txt_weather_temp = getActivity().findViewById(R.id.txt_weather_temp);
        txt_weather_temp.setText(weather_day.getTemp() + "°");
        TextView txt_weather_min = getActivity().findViewById(R.id.txt_weather_min);
        txt_weather_min.setText(weather_day.getMinTemp() + "°");
        ImageView img_weather = getActivity().findViewById(R.id.img_weather);
        AnimationDrawable anim;
        switch (weather_day.getSymbol()) {
            case 1:
                img_weather.setImageResource(R.drawable.w_1);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 2:
                img_weather.setImageResource(R.drawable.w_2);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 3:
                img_weather.setImageResource(R.drawable.w_3);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 4:
                img_weather.setImageResource(R.drawable.w_4a);
                break;
            case 5:
                img_weather.setImageResource(R.drawable.w_5);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 6:
                img_weather.setImageResource(R.drawable.w_6);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 7:
                img_weather.setImageResource(R.drawable.w_7);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 8:
                img_weather.setImageResource(R.drawable.w_8a);
                break;
            case 9:
                img_weather.setImageResource(R.drawable.w_9);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 10:
                img_weather.setImageResource(R.drawable.w_10);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 12:
                img_weather.setImageResource(R.drawable.w_12);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 38:
                img_weather.setImageResource(R.drawable.w_10);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 39:
                img_weather.setImageResource(R.drawable.w_10);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 40:
                img_weather.setImageResource(R.drawable.w_7);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 41:
                img_weather.setImageResource(R.drawable.w_7);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 42:
                img_weather.setImageResource(R.drawable.w_12);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            case 43:
                img_weather.setImageResource(R.drawable.w_12);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
            default:
                img_weather.setImageResource(R.drawable.w_11);
                anim = (AnimationDrawable) img_weather.getDrawable();
                anim.start();
                break;
        }
        img_weather.setVisibility(View.VISIBLE);
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN)
                .repeatCount(0)
                .duration(800)
                .createFor(img_weather)
                .start();
    }

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMeysamBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            if (intent.getStringExtra("changeCity") != null) {

                db.deleteDays();
                city=session.getStringItem(SessionModel.KEY_CITY_NAME);
                if(city==null) city="تهران";
                wc.get(city);
            }
        }

    };

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onDestroy() {
        wc.deleteObservers();

        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMeysamBroadcastReceiver);

//        if(PD != null)
//        {
//            PD.dismiss();
//            PD = null;
//        }
        super.onDestroy();
    }
}
