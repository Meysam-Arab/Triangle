package ir.fardan7eghlim.tri.Views.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ir.fardan7eghlim.tri.Models.CityModel;
import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.SoundModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseActivity;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Reciever.TriiiBroadcastReceiver;

public class SettingActivity extends BaseActivity {

    private ArrayList<CityModel> cities;
    private ArrayList<String> cities_name=new ArrayList<>();
    private ArrayList<String> provinces;
    private String current_city;
    private String current_province;

    private Spinner spr_province_setting;
    private Spinner spr_city_setting;
    private Spinner spr_mosalas_rtl_setting;
    private Spinner spr_ghamari_setting;

    private CheckBox chBx_namayesh_oghat_setting;
    private CheckBox chBx_azan_sobh_setting;
    private CheckBox chBx_azan_zohr_setting;
    private CheckBox chBx_azan_maghreb_setting;
    private CheckBox chBx_namayesh_triangles_setting;
    private CheckBox chBx_notifi_show_setting;
    private CheckBox chBx_notifi_ext_show_setting;
    private CheckBox chBx_startup_setting;

    private RadioGroup rdbtn_azan_voices_setting;
    private RadioButton rdbtn_azan_voice1_setting;
    private RadioButton rdbtn_azan_voice2_setting;
    private RadioButton rdbtn_azan_voice3_setting;
    private RadioButton rdbtn_azan_voice4_setting;

    private ImageView img_tri1_setting;
    private ImageView img_tri2_setting;
    private ImageView img_tri3_setting;
    private Space img_sp1_setting;
    private Space img_sp2_setting;
    private SeekBar skB_size_tris_setting;
    private SeekBar skB_spaces_tris_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportActionBar().hide();

        //view
        spr_province_setting=findViewById(R.id.spr_province_setting);
        spr_city_setting=findViewById(R.id.spr_city_setting);
        spr_mosalas_rtl_setting=findViewById(R.id.spr_mosalas_rtl_setting);
        spr_ghamari_setting=findViewById(R.id.spr_ghamari_setting);
        chBx_namayesh_oghat_setting=findViewById(R.id.chBx_namayesh_oghat_setting);
        chBx_azan_sobh_setting=findViewById(R.id.chBx_azan_sobh_setting);
        chBx_azan_zohr_setting=findViewById(R.id.chBx_azan_zohr_setting);
        chBx_azan_maghreb_setting=findViewById(R.id.chBx_azan_maghreb_setting);
        chBx_namayesh_triangles_setting=findViewById(R.id.chBx_namayesh_triangles_setting);
        chBx_notifi_show_setting=findViewById(R.id.chBx_notifi_show_setting);
        chBx_notifi_ext_show_setting=findViewById(R.id.chBx_notifi_ext_show_setting);
        chBx_startup_setting=findViewById(R.id.chBx_startup_setting);
        rdbtn_azan_voices_setting=findViewById(R.id.rdbtn_azan_voices_setting);
        rdbtn_azan_voice1_setting=findViewById(R.id.rdbtn_azan_voice1_setting);
        rdbtn_azan_voice2_setting=findViewById(R.id.rdbtn_azan_voice2_setting);
        rdbtn_azan_voice3_setting=findViewById(R.id.rdbtn_azan_voice3_setting);
        rdbtn_azan_voice4_setting=findViewById(R.id.rdbtn_azan_voice4_setting);
        img_tri1_setting=findViewById(R.id.img_tri1_setting);
        img_tri2_setting=findViewById(R.id.img_tri2_setting);
        img_tri3_setting=findViewById(R.id.img_tri3_setting);
        img_sp1_setting=findViewById(R.id.img_sp1_setting);
        img_sp2_setting=findViewById(R.id.img_sp2_setting);
        skB_size_tris_setting=findViewById(R.id.skB_size_tris_setting);
        skB_spaces_tris_setting=findViewById(R.id.skB_spaces_tris_setting);

        //session
        current_province=session.getStringItem(SessionModel.KEY_CITY_PROVINCE);
        if(current_province==null) current_province="تهران";
        current_city=session.getStringItem(SessionModel.KEY_CITY_NAME);
        if(current_city==null) current_city="تهران";

        //cites
        provinces = db.getProvinces();
        addItemsOnSpinner_Province();

        spr_province_setting.setSelection(provinces.indexOf(current_province));
        //spinner
        spr_province_setting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                cities =db.getCitiesByProvince(spr_province_setting.getSelectedItem().toString());
                addItemsOnSpinner_City();
                spr_city_setting.setSelection(cities_name.indexOf(current_city));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        spr_city_setting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                CityModel city= getCityFromName(spr_city_setting.getSelectedItem().toString());
                if(city!=null){
                    session.saveItem(SessionModel.KEY_CITY_NAME,city.getCityName());
                    session.saveItem(SessionModel.KEY_CITY_LATITUDE,city.getCityLatitude());
                    session.saveItem(SessionModel.KEY_CITY_LONGITUDE,city.getCityLongitude());
                    session.saveItem(SessionModel.KEY_CITY_PROVINCE,city.getCityProvince());

                    sendReloadNotificationBroadcast();

                    //meysam - added in 13970318
                    TriiiBroadcastReceiver.initializeAzanBroadcasts(getApplicationContext());
                    ///////////////////////////////

                    //meysam - notify weather fragment
                    Intent intent = new Intent("weather_fragment_broadcast");
                    // You can also include some extra data.
                    intent.putExtra("changeCity", "true");
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        //oghat
        chBx_namayesh_oghat_setting.setChecked(session.getBoolianItem(SessionModel.KEY_SHOW_PRAYER_TIMES,true));
        chBx_azan_sobh_setting.setChecked(session.getBoolianItem(SessionModel.KEY_PLAY_AZAN_SOBH,true));
        chBx_azan_zohr_setting.setChecked(session.getBoolianItem(SessionModel.KEY_PLAY_AZAN_ZOHR,true));
        chBx_azan_maghreb_setting.setChecked(session.getBoolianItem(SessionModel.KEY_PLAY_AZAN_MAGHREB,true));

        chBx_namayesh_oghat_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    session.saveItem(SessionModel.KEY_SHOW_PRAYER_TIMES,true);
                }else{
                    session.saveItem(SessionModel.KEY_SHOW_PRAYER_TIMES,false);
                }
            }
        });
        chBx_azan_sobh_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
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
        chBx_azan_zohr_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
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
        chBx_azan_maghreb_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
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

        //radios
        String current_voice=session.getStringItem(SessionModel.KEY_AZAN_SOUND, SoundModel.MOAZEN_ZADEH);
        if(current_voice.equals(SoundModel.KAZEM_ZADEH_TRM)){
            rdbtn_azan_voice1_setting.setChecked(true);
        }else if(current_voice.equals(SoundModel.KAZEM_ZADEH)){
            rdbtn_azan_voice4_setting.setChecked(true);
        }else if(current_voice.equals(SoundModel.AGHATI)){
            rdbtn_azan_voice3_setting.setChecked(true);
        }else{
            rdbtn_azan_voice2_setting.setChecked(true);
        }
        rdbtn_azan_voices_setting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                int selectedId=rdbtn_azan_voices_setting.getCheckedRadioButtonId();
                switch (selectedId){
                    case R.id.rdbtn_azan_voice1_setting:
                        session.saveItem(SessionModel.KEY_AZAN_SOUND,SoundModel.KAZEM_ZADEH_TRM);
                        break;
                    case R.id.rdbtn_azan_voice2_setting:
                        session.saveItem(SessionModel.KEY_AZAN_SOUND,SoundModel.MOAZEN_ZADEH);
                        break;
                    case R.id.rdbtn_azan_voice3_setting:
                        session.saveItem(SessionModel.KEY_AZAN_SOUND,SoundModel.AGHATI);
                        break;
                    case R.id.rdbtn_azan_voice4_setting:
                        session.saveItem(SessionModel.KEY_AZAN_SOUND,SoundModel.KAZEM_ZADEH);
                        break;
                }
            }
        });

        //ghamari calender repair
        addItemsOnSpinner_ghamari();
        switch (session.getIntegerItem(SessionModel.KEY_CALENDER_GHAMARI_CORRECTION,0)){
            case -2:
                spr_ghamari_setting.setSelection(0);
                break;
            case -1:
                spr_ghamari_setting.setSelection(1);
                break;
            case 1:
                spr_ghamari_setting.setSelection(3);
                break;
            case 2:
                spr_ghamari_setting.setSelection(4);
                break;
            default:
                spr_ghamari_setting.setSelection(2);
                break;
        }
        spr_ghamari_setting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               switch (position){
                   case 0:
                       session.saveItem(SessionModel.KEY_CALENDER_GHAMARI_CORRECTION,-2);
                       break;
                   case 1:
                       session.saveItem(SessionModel.KEY_CALENDER_GHAMARI_CORRECTION,-1);
                       break;
                   case 3:
                       session.saveItem(SessionModel.KEY_CALENDER_GHAMARI_CORRECTION,1);
                       break;
                   case 4:
                       session.saveItem(SessionModel.KEY_CALENDER_GHAMARI_CORRECTION,2);
                       break;
                   default:
                       session.saveItem(SessionModel.KEY_CALENDER_GHAMARI_CORRECTION,0);
                       break;
               }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        //mosalas
        chBx_namayesh_triangles_setting.setChecked(session.getBoolianItem(SessionModel.KEY_SHOW_SERVICE_TRIANGLES,true));
        chBx_namayesh_triangles_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
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
        addItemsOnSpinner_leftRightTri();
        if(session.getBoolianItem(SessionModel.KEY_TRIANGLE_LEFT_RIGHT,true)){
            spr_mosalas_rtl_setting.setSelection(0);
        }else{
            spr_mosalas_rtl_setting.setSelection(1);
        }
        spr_mosalas_rtl_setting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position==0){
                    session.saveItem(SessionModel.KEY_TRIANGLE_LEFT_RIGHT,true);
                }else{
                    session.saveItem(SessionModel.KEY_TRIANGLE_LEFT_RIGHT,false);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        //notifications
        chBx_notifi_show_setting.setChecked(session.getBoolianItem(SessionModel.KEY_SHOW_NOTIFICATION,true));
        chBx_notifi_show_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    session.saveItem(SessionModel.KEY_SHOW_NOTIFICATION,true);
                    sendReloadNotificationBroadcast();
                }else{
                    session.saveItem(SessionModel.KEY_SHOW_NOTIFICATION,false);
                    sendCloseNotificationBroadcast();
                }
            }
        });
        chBx_notifi_ext_show_setting.setChecked(session.getBoolianItem(SessionModel.KEY_SHOW_EXTENDED_NOTIFICATION,true));
        chBx_notifi_ext_show_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    session.saveItem(SessionModel.KEY_SHOW_EXTENDED_NOTIFICATION,true);
                }else{
                    session.saveItem(SessionModel.KEY_SHOW_EXTENDED_NOTIFICATION,false);
                }
                sendReloadNotificationBroadcast();
            }
        });

        //screen
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        final double unit_screen= display.getWidth()*0.01;
        //seekbars for triangles
        int tri_size=session.getIntegerItem(SessionModel.KEY_TRIANGLE_DIAMETER,(int) (5*unit_screen));
        img_tri1_setting.getLayoutParams().width = tri_size;
        img_tri1_setting.getLayoutParams().height = tri_size;
        img_tri1_setting.requestLayout();
        img_tri2_setting.getLayoutParams().width = tri_size;
        img_tri2_setting.getLayoutParams().height = tri_size;
        img_tri2_setting.requestLayout();
        img_tri3_setting.getLayoutParams().width = tri_size;
        img_tri3_setting.getLayoutParams().height = tri_size;
        img_tri3_setting.requestLayout();
        int tri_within_margin=session.getIntegerItem(SessionModel.KEY_TRIANGLE_BETWEEN,(int) (1*unit_screen));
        img_sp1_setting.getLayoutParams().width = tri_size;
        img_sp1_setting.getLayoutParams().height = tri_within_margin;
        img_sp1_setting.requestLayout();
        img_sp2_setting.getLayoutParams().width = tri_size;
        img_sp2_setting.getLayoutParams().height = tri_within_margin;
        img_sp2_setting.requestLayout();

        skB_size_tris_setting.setProgress((int) (tri_size/unit_screen)-2);
        skB_spaces_tris_setting.setProgress((int) (tri_within_margin/unit_screen));
        skB_size_tris_setting.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = (int) (3* unit_screen);
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = (int) (3* unit_screen) + (int) (progress*unit_screen);
//                Toast.makeText(SettingActivity.this, progress+"", Toast.LENGTH_SHORT).show();
                img_tri1_setting.getLayoutParams().width = progressChanged;
                img_tri1_setting.getLayoutParams().height = progressChanged;
                img_tri1_setting.requestLayout();
                img_tri2_setting.getLayoutParams().width = progressChanged;
                img_tri2_setting.getLayoutParams().height = progressChanged;
                img_tri2_setting.requestLayout();
                img_tri3_setting.getLayoutParams().width = progressChanged;
                img_tri3_setting.getLayoutParams().height = progressChanged;
                img_tri3_setting.requestLayout();
                session.saveItem(SessionModel.KEY_TRIANGLE_DIAMETER,progressChanged);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        skB_spaces_tris_setting.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = (int) (unit_screen);
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChanged = (int) (unit_screen) + (int) (progress*unit_screen);
                img_sp1_setting.getLayoutParams().height = progressChanged;
                img_sp1_setting.requestLayout();
                img_sp2_setting.getLayoutParams().height = progressChanged;
                img_sp2_setting.requestLayout();
                session.saveItem(SessionModel.KEY_TRIANGLE_BETWEEN,progressChanged);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //Exit
        chBx_startup_setting.setChecked(session.getBoolianItem(SessionModel.KEY_RUN_ON_PHONE_STARTUP,true));
        chBx_startup_setting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    session.saveItem(SessionModel.KEY_RUN_ON_PHONE_STARTUP,true);
                }else{
                    session.saveItem(SessionModel.KEY_RUN_ON_PHONE_STARTUP,false);
                }
            }
        });
    }

    private CityModel getCityFromName(String city_name){
        for(CityModel city:cities){
            if(city_name.equals(city.getCityName()))
                return city;
        }
        return null;
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner_Province() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,provinces);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spr_province_setting.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner_City() {
        cities_name.clear();
        for(CityModel city:cities){
            cities_name.add(city.getCityName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,cities_name);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spr_city_setting.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner_leftRightTri() {
        List<String> list = new ArrayList<String>();
        list.add("چپ");
        list.add("راست");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spr_mosalas_rtl_setting.setAdapter(dataAdapter);
    }
    public void addItemsOnSpinner_ghamari() {
        List<String> list = new ArrayList<String>();
        list.add("دو روز جلواست");
        list.add("یک روز جلواست");
        list.add("صحیح است");
        list.add("یک روز عقب است");
        list.add("دو روز عقب است");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spr_ghamari_setting.setAdapter(dataAdapter);
    }

    private void sendReloadNotificationBroadcast()
    {
//        //meysam - commeneted in 13970318
//        Intent intent = new Intent("timer_service_broadcast");
//        // You can also include some extra data.
//        intent.putExtra("reloadNotification", "true");
//        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        //meysam - added in 13970318
        TriiiBroadcastReceiver.showCalenderNotification(getApplicationContext());
        ////////////////////////////////////////
    }
    private void sendCloseNotificationBroadcast()
    {
        //meysam - commeneted in 13970318
//        Intent intent = new Intent("timer_service_broadcast");
//        // You can also include some extra data.
//        intent.putExtra("closeNotification", "true");
//        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        //meysam - added in 13970318
        TriiiBroadcastReceiver.hideNotification(getApplicationContext());
        ////////////////////////////////////////

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
