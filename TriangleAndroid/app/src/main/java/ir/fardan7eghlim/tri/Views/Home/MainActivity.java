package ir.fardan7eghlim.tri.Views.Home;

import android.Manifest;
import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.appolica.flubber.Flubber;
import com.jpeng.jpspringmenu.ListBean;
import com.jpeng.jpspringmenu.MyAdapter;
import com.jpeng.jpspringmenu.SpringMenu;
import com.jpeng.jpspringmenu.MenuListener;
import com.facebook.rebound.SpringConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ir.fardan7eghlim.tri.Controllers.HomeController;
import ir.fardan7eghlim.tri.Controllers.RssController;
import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.TaskModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseActivity;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.Models.widgets.customTextView;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Utils.DialogPopUp;
import ir.fardan7eghlim.tri.Utils.ProgressDialog;
import ir.fardan7eghlim.tri.Utils.RequestRespond;
import ir.fardan7eghlim.tri.Views.Home.Fragments.CalndrFragment;
import ir.fardan7eghlim.tri.Views.Home.Fragments.DialerFragment;
import ir.fardan7eghlim.tri.Views.Home.Fragments.ExchangeFragment;
import ir.fardan7eghlim.tri.Views.Home.Fragments.HomeFragment;
import ir.fardan7eghlim.tri.Views.Home.Fragments.NewsFragment;
import ir.fardan7eghlim.tri.Views.Home.Fragments.WeatherFragment;
import ir.fardan7eghlim.tri.Views.help.FirstPageActivity;

import static android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission;

public class MainActivity extends BaseActivity implements MenuListener, HomeFragment.OnFragmentInteractionListener
        , DialerFragment.OnFragmentInteractionListener
        , WeatherFragment.OnFragmentInteractionListener
        , CalndrFragment.OnFragmentInteractionListener
        , NewsFragment.OnFragmentInteractionListener
        , Observer {

    private Intent intent;
    private HomeController hc;

    private Animator anm_btnHome;
    private Animator anm_btnCalender;
    private Animator anm_btnWeather;
    private Animator anm_btnContacts;
    private Animator anm_btnNews;


    private ImageView iv_btnHome;
    private ImageView iv_btnCalender;
    private ImageView iv_btnWeather;
    private ImageView iv_btnContacts;
    private ImageView iv_btnNews;
    private ImageView iv_btnWorld;

    private LinearLayout tris_menu_master;
    private LinearLayout tris_menu_world;
    private LinearLayout tris_menu_tools;

    private SpringMenu menu;

    public static ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //service
        intent = new Intent(this, OverlayButton.class);

        PD = new ProgressDialog(this);



        //force to be english layouts
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.height = menu.getHeight();
//                MainActivity.this.getWindow().setAttributes(params);

                //spring menu init
                menu = new SpringMenu(MainActivity.this, R.layout.view_menu);
                menu.setDirection(SpringMenu.DIRECTION_RIGHT);
                ListBean[] listBeen = {new ListBean(R.drawable.btn_icon_notepad, "یادداشت ها"),new ListBean(R.drawable.btn_icon_setting, "تنظیمات"), new ListBean(R.drawable.btn_icon_star, "امتیاز به مثلث"), new ListBean(R.drawable.btn_icon_share, "اشتراک گذاری مثلث"),new ListBean(R.drawable.btn_icon_people, "درباره ما"), new ListBean(R.drawable.btn_icon_exit, "بستن کامل برنامه")};
                MyAdapter adapter = new MyAdapter(MainActivity.this, listBeen);
                ListView listView = (ListView) menu.findViewById(R.id.test_listView);
                listView.setAdapter(adapter);

                //version
                //meysam: set version of app
                PackageInfo pInfo = null;
                try {
                    pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                customTextView tv_version = findViewById(R.id.tv_version);
                tv_version.setText("نسخه: "+  pInfo.versionName);

            }
        }, 1000);
//

//        new RssController(getApplicationContext()).get(null,null);



        iv_btnHome = findViewById(R.id.img_home_main_activity);
        iv_btnCalender = findViewById(R.id.img_Calndr_main_activity);
        iv_btnWeather = findViewById(R.id.img_weather_main_activity);
        iv_btnContacts = findViewById(R.id.img_contacts_main_activity);
        iv_btnNews = findViewById(R.id.img_news_main_activity);
        iv_btnWorld = findViewById(R.id.img_world_main_activity);

        //tris_menus
        tris_menu_master = findViewById(R.id.tris_menu_master);
        tris_menu_world = findViewById(R.id.tris_menu_world);
        tris_menu_tools =findViewById(R.id.tris_menu_tools);

        //fragment
        HomeFragment HomeFragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frg_holder_main_activity, HomeFragment);
        ft.commit();
//        if(!stopService(intent)){
//            stopService(intent);
//        }
//                String.valueOf(cal.get(Calendar.HOUR_OF_DAY))+":"+String.valueOf(cal.get(Calendar.MINUTE)));
//        startActivity(new Intent(MainActivity.this,FirstPageActivity.class));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(menu != null)
            return menu.dispatchTouchEvent(ev);
        else
            return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onDestroy() {

        if(PD != null)
        {
            PD.dismiss();
            PD = null;
        }

        flubberHomeButtonListener = null;
        flubberCalenderButtonListener = null;
        flubberWeatherButtonListener = null;
        flubberContactsButtonListener = null;
        flubberNewsButtonListener = null;

        if (anm_btnHome != null) {
            anm_btnHome.end();
            anm_btnHome.cancel();
            anm_btnHome.removeAllListeners();
        }

        if (anm_btnCalender != null) {
            anm_btnCalender.end();
            anm_btnCalender.cancel();
            anm_btnCalender.removeAllListeners();
        }

        if (anm_btnWeather != null) {
            anm_btnWeather.end();
            anm_btnWeather.cancel();
            anm_btnWeather.removeAllListeners();
        }

        if (anm_btnContacts != null) {
            anm_btnContacts.end();
            anm_btnContacts.cancel();
            anm_btnContacts.removeAllListeners();
        }
        if (anm_btnNews != null) {
            anm_btnNews.end();
            anm_btnNews.cancel();
            anm_btnNews.removeAllListeners();
        }

        super.onDestroy();
    }

    @Override
    public void onPause() {

//        if(stopService(intent)){
//        startService(intent);
//        Toast.makeText(getBaseContext(), "onPause", Toast.LENGTH_LONG).show();
//        }
//        if(TimerService.isRunning)
//        {
//            Intent msgIntent = new Intent(this, TimerService.class);
//            stopService(msgIntent);
//        }
//        if(stopService(intent)){
        Toast.makeText(getApplicationContext(), "در قبل از کال سرویس آورلی", Toast.LENGTH_SHORT).show();

        if (session.getBoolianItem(SessionModel.KEY_SHOW_SERVICE_TRIANGLES, true))
        {
            Toast.makeText(this, "در قبل 1 از کال سرویس آورلی", Toast.LENGTH_SHORT).show();

            if(!session.getBoolianItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, false))
            {
                Toast.makeText(this, "در قبل 0 از کال سرویس آورلی", Toast.LENGTH_SHORT).show();

                if (session.getBoolianItem(SessionModel.KEY_IS_ACCESS_ALERT_WINDOW_PERMISSION_GRANTED,false))
                {
                    Toast.makeText(this, "در کال سرویس آورلی", Toast.LENGTH_SHORT).show();

                    startService(intent);
                }
            }
            else
                session.removeItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY);

        }
        super.onPause();

//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        hc = new HomeController(getApplicationContext());
        hc.addObserver(this);
        new AsyncProcess01().execute("");
    }


    @Override
    public void onResume() {

//        if(TimerService.isRunning == null && !TimerService.isRunning)
//        {
//            Intent msgIntent = new Intent(this, TimerService.class);
//            startService(msgIntent);
//        }

//        if(session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 0 &&
//                session.getIntegerItem(SessionModel.KEY_WORD_DATABASE_TABLE_STATUS) != 1  )
//        {
//        }

        //buttons

        final ImageView btn_setting_main_activity = findViewById(R.id.btn_setting_main_activity);
        btn_setting_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//                startActivity(intent);
//
                hideSoftKey(view);
                menu.openMenu();
                Flubber.with()
                        .animation(Flubber.AnimationPreset.ROTATION)
                        .repeatCount(1)
                        .duration(400)
                        .force(5)
                        .createFor(btn_setting_main_activity)
                        .start();
            }
        });


        Random ran = new Random();
//        int rnd= 0;
        int randomDelay = 0;
        //meysam - home button animation
//        rnd= ran.nextInt(1) + 2;
        randomDelay = ran.nextInt(10) * 1000 + 5000;
        anm_btnHome = Flubber.with().listener(flubberHomeButtonListener)
                .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                .delay(randomDelay)
                .repeatCount(0)                              // Repeat once
                .duration(30000)  // Last for 1000 milliseconds(1 second)
//                .force(rnd)
                .createFor(iv_btnHome);
        anm_btnHome.start();// Apply it to the view
        //////////////////////////////////////////

        //meysam - calender button animation
//        rnd= ran.nextInt(1) + 2;
        randomDelay = ran.nextInt(10) * 1000 + 5000;
        anm_btnCalender = Flubber.with().listener(flubberCalenderButtonListener)
                .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                .delay(randomDelay)
                .repeatCount(0)                              // Repeat once
                .duration(30000)  // Last for 1000 milliseconds(1 second)
//                .force(rnd)
                .createFor(iv_btnCalender);
        anm_btnCalender.start();// Apply it to the view
        //////////////////////////////////////////

        //meysam - weather button animation
//        rnd= ran.nextInt(1) + 2;
        randomDelay = ran.nextInt(10) * 1000 + 5000;
        anm_btnWeather = Flubber.with().listener(flubberWeatherButtonListener)
                .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                .delay(randomDelay)
                .repeatCount(0)                              // Repeat once
                .duration(30000)  // Last for 1000 milliseconds(1 second)
//                .force(rnd)
                .createFor(iv_btnWeather);
        anm_btnWeather.start();// Apply it to the view
        //////////////////////////////////////////

        //meysam - contacts button animation
//        rnd= ran.nextInt(1) + 2;
        randomDelay = ran.nextInt(10) * 1000 + 5000;
        anm_btnContacts = Flubber.with().listener(flubberContactsButtonListener)
                .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                .delay(randomDelay)
                .repeatCount(0)                              // Repeat once
                .duration(30000)  // Last for 1000 milliseconds(1 second)
//                .force(rnd)
                .createFor(iv_btnContacts);
        anm_btnContacts.start();// Apply it to the view
        //////////////////////////////////////////

        //meysam - news button animation
//        rnd= ran.nextInt(1) + 2;
        randomDelay = ran.nextInt(10) * 1000 + 5000;
        anm_btnNews = Flubber.with().listener(flubberNewsButtonListener)
                .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                .delay(randomDelay)
                .repeatCount(0)    // Repeat
                .duration(30000)  // Last for milliseconds
//                .force(rnd)
                .createFor(iv_btnNews);
        anm_btnNews.start();// Apply it to the view
        //////////////////////////////////////////


        FrameLayout fl_home_main_activity = findViewById(R.id.fl_home_main_activity);
        fl_home_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PD.show();
                    }
                });
                HomeFragment HomeFragment = new HomeFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder_main_activity, HomeFragment);
                ft.commit();
                hideSoftKey(view);
            }
        });

        FrameLayout fl_contacts_main_activity = findViewById(R.id.fl_contacts_main_activity);
        fl_contacts_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PD.show();
                    }
                });
                DialerFragment DialerFragment = new DialerFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder_main_activity, DialerFragment);
//                addToBackstack();
                ft.commit();
                hideSoftKey(view);
            }
        });

        FrameLayout fl_weather_main_activity = findViewById(R.id.fl_weather_main_activity);
        fl_weather_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PD.show();
                    }
                });
                WeatherFragment WeatherFragment = new WeatherFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder_main_activity, WeatherFragment);
                ft.commit();
                hideSoftKey(view);
            }
        });

        FrameLayout fl_Calndr_main_activity = findViewById(R.id.fl_Calndr_main_activity);
        fl_Calndr_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PD.show();
                    }
                });
                CalndrFragment CalndrFragment = new CalndrFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder_main_activity, CalndrFragment);
                ft.commit();
                hideSoftKey(view);
            }
        });

        FrameLayout fl_news_main_activity = findViewById(R.id.fl_news_main_activity);
        fl_news_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PD.show();
                    }
                });
                NewsFragment NewsFragment = new NewsFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder_main_activity, NewsFragment);
                ft.commit();
                hideSoftKey(view);
            }
        });
        FrameLayout fl_exchange_main_activity = findViewById(R.id.fl_exchange_main_activity);
        fl_exchange_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PD.show();
                    }
                });
                ExchangeFragment ExchangeFragment = new ExchangeFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frg_holder_main_activity, ExchangeFragment);
                ft.commit();
                hideSoftKey(view);
            }
        });
        FrameLayout fl_world_main_activity = findViewById(R.id.fl_world_main_activity);
        fl_world_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Flubber.with()
//                        .animation(Flubber.AnimationPreset.SLIDE_RIGHT) // Slide up animation
//                        .repeatCount(0)                              // Repeat once
//                        .duration(700)                              // Last for 1000 milliseconds(1 second)
//                        .createFor(tris_menu_master)                             // Apply it to the view
//                        .start();
                tris_menu_world.setVisibility(View.VISIBLE);
                tris_menu_master.setVisibility(View.GONE);
                Flubber.with()
                        .animation(Flubber.AnimationPreset.SLIDE_LEFT) // Slide up animation
                        .repeatCount(0)                              // Repeat once
                        .duration(700)                              // Last for 1000 milliseconds(1 second)
                        .createFor(tris_menu_world)                             // Apply it to the view
                        .start();
                hideSoftKey(view);
            }
        });
        FrameLayout fl_bck_world_main_activity = findViewById(R.id.fl_bck_world_main_activity);
        fl_bck_world_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tris_menu_master.setVisibility(View.VISIBLE);
                tris_menu_world.setVisibility(View.GONE);
                Flubber.with()
                        .animation(Flubber.AnimationPreset.SLIDE_LEFT) // Slide up animation
                        .repeatCount(0)                              // Repeat once
                        .duration(700)                              // Last for 1000 milliseconds(1 second)
                        .createFor(tris_menu_master)                             // Apply it to the view
                        .start();
                hideSoftKey(view);
            }
        });
        FrameLayout fl_account_main_activity = findViewById(R.id.fl_account_main_activity);
        fl_account_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tris_menu_tools.setVisibility(View.VISIBLE);
                tris_menu_master.setVisibility(View.GONE);
                Flubber.with()
                        .animation(Flubber.AnimationPreset.SLIDE_LEFT) // Slide up animation
                        .repeatCount(0)                              // Repeat once
                        .duration(700)                              // Last for 1000 milliseconds(1 second)
                        .createFor(tris_menu_tools)                             // Apply it to the view
                        .start();
                hideSoftKey(view);
            }
        });
        FrameLayout fl_bck_tools_main_activity = findViewById(R.id.fl_bck_tools_main_activity);
        fl_bck_tools_main_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tris_menu_master.setVisibility(View.VISIBLE);
                tris_menu_tools.setVisibility(View.GONE);
                Flubber.with()
                        .animation(Flubber.AnimationPreset.SLIDE_LEFT) // Slide up animation
                        .repeatCount(0)                              // Repeat once
                        .duration(700)                              // Last for 1000 milliseconds(1 second)
                        .createFor(tris_menu_master)                             // Apply it to the view
                        .start();
                hideSoftKey(view);
            }
        });

        if (!stopService(intent)) {
            stopService(intent);
        }

        super.onResume();
    }

    private void hideSoftKey(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onMenuOpen() {

    }

    @Override
    public void onMenuClose() {

    }

    @Override
    public void onProgressUpdate(float value, boolean bouncing) {

    }


    //process_01
    private class AsyncProcess01 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // meysam - once per day...

            if (Utility.isTimeSpent(session.getStringItem(SessionModel.KEY_VERSION_LAST_CHECKED_DATE_TIME), Utility.CHECK_VERSION_INTERVAL) && Utility.isServerAvailable(getApplicationContext(), "http://www.triii.ir")) {
                hc.getVersion();
            }




            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    //update
    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            if (arg instanceof Boolean) {
                if (Boolean.parseBoolean(arg.toString()) == false) {
                    Utility.displayToast(getApplicationContext().getApplicationContext(), getApplicationContext().getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                }
            } else if (arg instanceof ArrayList) {
                if (((ArrayList) arg).get(0) == RequestRespond.TAG_HOME_GET_VERSION) {

                    PackageInfo pInfo = null;
                    Float current_server_version = null;
                    Float current_mobile_ver = null;
                    Float min_server_version = null;
                    try {
                        pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (pInfo != null) {
                        String[] tmpVersion = pInfo.versionName.split("[.]");
                        current_mobile_ver = new Float(tmpVersion[0] + "." + tmpVersion[1] + tmpVersion[2]);
                    }

                    current_server_version = new Float(((ArrayList) arg).get(1).toString());
                    min_server_version = new Float(((ArrayList) arg).get(4).toString());

                    if (current_server_version > current_mobile_ver) {
                        if (min_server_version > current_mobile_ver) {
                            String link = ((ArrayList) arg).get(2).toString();
                            if (link.equals("")) {
                                Intent i = new Intent(MainActivity.this, DownloadActivity.class);
                                i.putExtra("link", ((ArrayList) arg).get(2).toString());
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                session.saveItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY, true);
                                startActivity(i);
                                finish();
                            } else {
                                //meysam
                                Utility.displayToast(getApplicationContext(), getResources().getString(R.string.msg_OldVersion), Toast.LENGTH_SHORT);
                                Utility.finishActivity(MainActivity.this);
                            }
                        } else {
                            Utility.displayToast(getApplicationContext(), getResources().getString(R.string.msg_OldVersion), Toast.LENGTH_SHORT);
                        }
                    }

                    final String message = ((ArrayList) arg).get(3).toString();
                    if (!message.equals("null")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // meysam - show dialog...
                                DialogPopUp.show(MainActivity.this, message, getString(R.string.btn_OK), null, false, true);
                            }
                        });
                    }
                    session.saveItem(SessionModel.KEY_VERSION_LAST_CHECKED_DATE_TIME, new Date().getTime() + "");

                }
            } else if (arg instanceof Integer) {
                Utility.displayToast(getApplicationContext().getApplicationContext(), new RequestRespond(getApplicationContext()).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);
            } else {
                Utility.displayToast(getApplicationContext().getApplicationContext(), getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            }
        } else {
            Utility.displayToast(getApplicationContext().getApplicationContext(), getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }
    }

    public Animator.AnimatorListener flubberHomeButtonListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            Random ran = new Random();
//            int rnd= 0;
//            rnd= ran.nextInt(1) + 2;
            int randomDelay = 0;
            randomDelay = ran.nextInt(60) * 1000;
            anm_btnHome.removeAllListeners();
            anm_btnHome = Flubber.with().listener(flubberHomeButtonListener)
                    .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                    .repeatCount(0)// Repeat once
                    .delay(randomDelay)
                    .duration(30000)  // Last for 1000 milliseconds(1 second)
//                    .force(rnd)
                    .createFor(iv_btnHome);
            anm_btnHome.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public Animator.AnimatorListener flubberCalenderButtonListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            Random ran = new Random();
//            int rnd= 0;
//            rnd= ran.nextInt(1) + 2;
            int randomDelay = 0;
            randomDelay = ran.nextInt(60) * 1000;
            anm_btnCalender.removeAllListeners();
            anm_btnCalender = Flubber.with().listener(flubberCalenderButtonListener)
                    .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                    .repeatCount(0)// Repeat once
                    .delay(randomDelay)
                    .duration(30000)  // Last for 1000 milliseconds(1 second)
//                    .force(rnd)
                    .createFor(iv_btnCalender);
            anm_btnCalender.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public Animator.AnimatorListener flubberWeatherButtonListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            Random ran = new Random();
//            int rnd= 0;
//            rnd= ran.nextInt(1) + 2;
            int randomDelay = 0;
            randomDelay = ran.nextInt(60) * 1000;
            anm_btnWeather.removeAllListeners();
            anm_btnWeather = Flubber.with().listener(flubberWeatherButtonListener)
                    .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                    .repeatCount(0)// Repeat once
                    .delay(randomDelay)
                    .duration(30000)  // Last for 1000 milliseconds(1 second)
//                    .force(rnd)
                    .createFor(iv_btnWeather);
            anm_btnWeather.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public Animator.AnimatorListener flubberContactsButtonListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            Random ran = new Random();
//            int rnd= 0;
//            rnd= ran.nextInt(1) + 2;
            int randomDelay = 0;
            randomDelay = ran.nextInt(60) * 1000;
            anm_btnContacts.removeAllListeners();
            anm_btnContacts = Flubber.with().listener(flubberContactsButtonListener)
                    .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                    .delay(randomDelay)
                    .repeatCount(0)// Repeat once
                    .duration(30000)  // Last for 1000 milliseconds(1 second)
//                    .force(rnd)
                    .createFor(iv_btnContacts);
            anm_btnContacts.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public Animator.AnimatorListener flubberNewsButtonListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            Random ran = new Random();
//            int rnd= 0;
//            rnd= ran.nextInt(1) + 2;
            int randomDelay = 0;
            randomDelay = ran.nextInt(60) * 1000;
            anm_btnNews.removeAllListeners();
            anm_btnNews = Flubber.with().listener(flubberNewsButtonListener)
                    .animation(Flubber.AnimationPreset.ROTATION) // Slide up animation
                    .delay(randomDelay)
                    .repeatCount(0)// Repeat once
                    .duration(30000)  // Last for 1000 milliseconds(1 second)
//                    .force(rnd)
                    .createFor(iv_btnNews);
            anm_btnNews.start();// Apply it to the view
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    @Override
    public void onBackPressed() {
        //meysam - if menu is open just close the menu
        if(menu.isOpened())
            menu.closeMenu();
        else
            super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Toast.makeText(this, "در جواب اجازه مین", Toast.LENGTH_SHORT).show();

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
