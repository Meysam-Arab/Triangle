package ir.fardan7eghlim.tri.Views.help;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.appolica.flubber.Flubber;

import java.util.ArrayList;

import ir.fardan7eghlim.tri.Models.CityModel;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseActivity;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Reciever.TriiiBroadcastReceiver;

public class FirstPageActivity extends BaseActivity {

    private int state = 0;
    private boolean lock_next_btn = true;
    private ImageView img_next_btn_helper;
    private TextView txt_first_helper;
    private TextView txt_second_helper;
    private TextView txt_third_helper;
    private ImageView img_first_helper;
    private LinearLayout ll_events_helper;
    private LinearLayout ll_oghat_helper;
    private CheckBox chBx_namayesh_oghat_helper;
    private CheckBox chBx_azan_sobh_helper;
    private CheckBox chBx_azan_zohr_helper;
    private CheckBox chBx_azan_maghreb_helper;
    private CheckBox chBx_namayesh_rectangels_helper;
    private LinearLayout ll_cities_helper;
    private Spinner spr_province_helper;
    private Spinner spr_city_helper;
    private FrameLayout fr_rectangles_helper;
    private ImageView img_rectangles_hand_helper;
    private LinearLayout ll_per_helper;
    private ImageView img_per_helper;
    private Button btn_per_helper;

    private ArrayList<CityModel> cities;
    private ArrayList<String> cities_name=new ArrayList<>();
    private ArrayList<String> provinces;
    private String current_city;
    private String current_province;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        //sets
        img_next_btn_helper = findViewById(R.id.img_next_btn_helper);
        txt_first_helper = findViewById(R.id.txt_first_helper);
        txt_second_helper = findViewById(R.id.txt_second_helper);
        txt_third_helper = findViewById(R.id.txt_third_helper);
        img_first_helper = findViewById(R.id.img_first_helper);
        ll_events_helper = findViewById(R.id.ll_events_helper);
        ll_oghat_helper = findViewById(R.id.ll_oghat_helper);
        chBx_namayesh_oghat_helper = findViewById(R.id.chBx_namayesh_oghat_helper);
        chBx_azan_sobh_helper = findViewById(R.id.chBx_azan_sobh_helper);
        chBx_azan_zohr_helper = findViewById(R.id.chBx_azan_zohr_helper);
        chBx_azan_maghreb_helper = findViewById(R.id.chBx_azan_maghreb_helper);
        chBx_namayesh_rectangels_helper = findViewById(R.id.chBx_namayesh_rectangels_helper);
        ll_cities_helper = findViewById(R.id.ll_cities_helper);
        spr_province_helper = findViewById(R.id.spr_province_helper);
        spr_city_helper = findViewById(R.id.spr_city_helper);
        fr_rectangles_helper = findViewById(R.id.fr_rectangles_helper);
        img_rectangles_hand_helper = findViewById(R.id.img_rectangles_hand_helper);
        ll_per_helper = findViewById(R.id.ll_per_helper);
        img_per_helper = findViewById(R.id.img_per_helper);
        btn_per_helper = findViewById(R.id.btn_per_helper);

        //functions
        img_next_btn_helper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!lock_next_btn)
                    nextButton();
            }
        });
        init();
        init_Settingds();
    }

    private void init_Settingds(){
        //oghta
        chBx_namayesh_oghat_helper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    session.saveItem(SessionModel.KEY_SHOW_PRAYER_TIMES,true);
                    session.saveItem(SessionModel.KEY_SHOW_EXTENDED_NOTIFICATION,true);
                }else{
                    session.saveItem(SessionModel.KEY_SHOW_PRAYER_TIMES,false);
                    session.saveItem(SessionModel.KEY_SHOW_EXTENDED_NOTIFICATION,false);
                    //false azan sobh
                    session.saveItem(SessionModel.KEY_PLAY_AZAN_SOBH,false);
                    chBx_azan_sobh_helper.setChecked(false);
                    //false azan zohr
                    session.saveItem(SessionModel.KEY_PLAY_AZAN_ZOHR,false);
                    chBx_azan_zohr_helper.setChecked(false);
                    //false azan maghreb
                    session.saveItem(SessionModel.KEY_PLAY_AZAN_MAGHREB,false);
                    chBx_azan_maghreb_helper.setChecked(false);
                }
            }
        });
        chBx_azan_sobh_helper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    session.saveItem(SessionModel.KEY_PLAY_AZAN_SOBH,true);
                }else{
                    session.saveItem(SessionModel.KEY_PLAY_AZAN_SOBH,false);
                }
            }
        });
        chBx_azan_zohr_helper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    session.saveItem(SessionModel.KEY_PLAY_AZAN_ZOHR,true);
                }else{
                    session.saveItem(SessionModel.KEY_PLAY_AZAN_ZOHR,false);
                }
            }
        });
        chBx_azan_maghreb_helper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    session.saveItem(SessionModel.KEY_PLAY_AZAN_MAGHREB,true);
                }else{
                    session.saveItem(SessionModel.KEY_PLAY_AZAN_MAGHREB,false);
                }
            }
        });
        //city
        provinces = db.getProvinces();
        addItemsOnSpinner_Province();
        spr_province_helper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                cities =db.getCitiesByProvince(spr_province_helper.getSelectedItem().toString());
                addItemsOnSpinner_City();
                spr_city_helper.setSelection(cities_name.indexOf(current_city));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        spr_city_helper.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                CityModel city= getCityFromName(spr_city_helper.getSelectedItem().toString());
                if(city!=null){
                    session.saveItem(SessionModel.KEY_CITY_NAME,city.getCityName());
                    session.saveItem(SessionModel.KEY_CITY_LATITUDE,city.getCityLatitude());
                    session.saveItem(SessionModel.KEY_CITY_LONGITUDE,city.getCityLongitude());
                    session.saveItem(SessionModel.KEY_CITY_PROVINCE,city.getCityProvince());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        //mosalas

        chBx_namayesh_rectangels_helper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    session.saveItem(SessionModel.KEY_SHOW_SERVICE_TRIANGLES,true);
                }else{
                    session.saveItem(SessionModel.KEY_SHOW_SERVICE_TRIANGLES,false);
                }
            }
        });
    }
    // add items into spinner dynamically
    public void addItemsOnSpinner_Province() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,provinces);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spr_province_helper.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner_City() {
        cities_name.clear();
        for(CityModel city:cities){
            cities_name.add(city.getCityName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,cities_name);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spr_city_helper.setAdapter(dataAdapter);
    }
    private CityModel getCityFromName(String city_name){
        for(CityModel city:cities){
            if(city_name.equals(city.getCityName()))
                return city;
        }
        return null;
    }

    private void init() {
        img_first_helper.setVisibility(View.VISIBLE);
        appearContent(img_first_helper,1500);
        Flubber.with()
                .animation(Flubber.AnimationPreset.SLIDE_UP)
                .repeatCount(0)
                .duration(1500)
                .createFor(img_next_btn_helper)
                .start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lock_next_btn = false;
            }
        }, 1500);
        setTexts();
    }

    private void appearContent(View v, int d){
        v.setVisibility(View.VISIBLE);
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN_UP)
                .repeatCount(0)
                .duration(d)
                .createFor(v)
                .start();
    }
    private void disappearContent(View v, int d){
        Flubber.with()
                .animation(Flubber.AnimationPreset.TRANSLATION_X)
                .translateX(-10000)
                .repeatCount(0)
                .duration(d)
                .createFor(v)
                .start();
    }

    private void setTexts() {
        txt_first_helper.setVisibility(View.VISIBLE);
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN_UP)
                .repeatCount(0)
                .duration(900)
                .createFor(txt_first_helper)
                .start();
        txt_second_helper.setVisibility(View.VISIBLE);
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN_UP)
                .repeatCount(0)
                .duration(1200)
                .createFor(txt_second_helper)
                .start();
        txt_third_helper.setVisibility(View.VISIBLE);
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN_UP)
                .repeatCount(0)
                .duration(1200)
                .createFor(txt_third_helper)
                .start();
        lock_next_btn = false;
    }

    private void reset_texts() {
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_OUT)
                .repeatCount(0)
                .duration(1000)
                .createFor(txt_first_helper)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_OUT)
                .repeatCount(0)
                .duration(1000)
                .createFor(txt_second_helper)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_OUT)
                .repeatCount(0)
                .duration(1000)
                .createFor(txt_third_helper)
                .start();
//        txt_first_helper.setVisibility(View.GONE);
//        txt_second_helper.setVisibility(View.GONE);
//        txt_third_helper.setVisibility(View.GONE);
    }

    private void nextButton() {
        lock_next_btn = true;
        reset_texts();
        state++;
        if (state == 5 && Build.VERSION.SDK_INT < 23) {
            //skip getting permission for android under 6
            state++;
        }
        switch (state) {
            case 1:
                disappearContent(img_first_helper,1000);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txt_first_helper.setText("رویدادها");
                        txt_second_helper.setText("رویدادهای خود را مدیریت کنید");
                        txt_third_helper.setText("رویدادهایی که فقط یکبار هستند یا روزانه،هفتگی و ماهانه");
                        setTexts();
                        appearContent(ll_events_helper,1000);
                    }
                }, 1000);
                break;
            case 2:
                disappearContent(ll_events_helper,1000);
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txt_first_helper.setText("اوقات شرعی و اذان");
                        txt_second_helper.setText("لطفا تنظیمات مورد نظر خود را اعمال کنید");
                        txt_third_helper.setText("");
                        setTexts();
                        appearContent(ll_oghat_helper,1000);
                    }
                }, 1000);
                break;
            case 3:
                disappearContent(ll_oghat_helper,1000);
                Handler handler3 = new Handler();
                handler3.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txt_first_helper.setText("تنظیمات شهر");
                        txt_second_helper.setText("لطفا شهرستان خود را انتخاب کنید");
                        txt_third_helper.setText("");
                        setTexts();
                        appearContent(ll_cities_helper,1000);
                    }
                }, 1000);
                break;
            case 4:
                disappearContent(ll_cities_helper,1000);
                Handler handler4 = new Handler();
                handler4.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txt_first_helper.setText("دسترسی سریع");
                        txt_second_helper.setText("این سه مثلث همیشه برروی صفحه ی گوشی شما هستند.تا با کشیدن آن ها به سمت راست بتوانید سریع برنامه را باز کنید.");
                        txt_third_helper.setText("");
                        setTexts();
                        appearContent(fr_rectangles_helper,1000);
                    }
                }, 1000);
                Handler handler42 = new Handler();
                handler42.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img_rectangles_hand_helper.setImageResource(R.drawable.help_tri_hand_b);
                        AnimationDrawable anim = (AnimationDrawable) img_rectangles_hand_helper.getDrawable();
                        img_rectangles_hand_helper.setVisibility(View.VISIBLE);
                        anim.start();
                    }
                }, 2000);
                break;
            case 5:
                img_next_btn_helper.setVisibility(View.GONE);
                disappearContent(fr_rectangles_helper,1000);
                Handler handler5 = new Handler();
                handler5.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txt_first_helper.setText("دسترسی");
                        txt_second_helper.setText("لطفا به مثلث ها اجازه دهید تا نمایش داده شوند.");
                        txt_third_helper.setText("");
                        setTexts();
                        appearContent(ll_per_helper,1000);
                    }
                }, 1000);
                Handler handler52 = new Handler();
                handler52.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        img_per_helper.setImageResource(R.drawable.help_per_c);
                        AnimationDrawable anim = (AnimationDrawable) img_per_helper.getDrawable();
                        img_per_helper.setVisibility(View.VISIBLE);
                        anim.start();
                    }
                }, 2000);
                break;
            default:
                finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
