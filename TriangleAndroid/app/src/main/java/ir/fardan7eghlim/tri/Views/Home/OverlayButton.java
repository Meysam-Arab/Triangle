package ir.fardan7eghlim.tri.Views.Home;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

import com.appolica.flubber.Flubber;

import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.R;

public class OverlayButton extends Service implements View.OnTouchListener {

    private int w_screen,h_screen;
    private double unit_screen;
    WindowManager wm,semi_wm;
    private float x1,x2;
    private float y1,y2;
    static final int MIN_DISTANCE = 150;
    private LinearLayout ll_triangels,semi_ll_tri;
    private Space semi_margin_top;
    private boolean semi_isMoving=false;

    private boolean tri_isLeft;
    private int tri_margin_top;
    private int tri_size;
    private int tri_within_margin;

    private ImageView tri_1;
    private ImageView tri_2;
    private ImageView tri_3;
    private Intent intent_service;

    private SessionModel session;
    private Integer llId = 112548;//meysam - random number

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "در سرویس آورلی", Toast.LENGTH_SHORT).show();


        //meysam - added in 13970318
        new SessionModel(getApplicationContext()).saveItem(SessionModel.KEY_STARTED,false);
        ///////////////////////////////////

        //session
        session=new SessionModel(getApplicationContext());

        //screen
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        w_screen= display.getWidth();
        h_screen= display.getHeight();
        unit_screen= w_screen*0.01;

        ///defult value for tri-s   Amir:set it with sp
        tri_isLeft=session.getBoolianItem(SessionModel.KEY_TRIANGLE_LEFT_RIGHT,true);
        tri_margin_top=session.getIntegerItem(SessionModel.KEY_TRIANGLE_TOP_MARGIN,(int) (20*unit_screen));
        tri_size=session.getIntegerItem(SessionModel.KEY_TRIANGLE_DIAMETER,(int) (5*unit_screen));
        tri_within_margin=session.getIntegerItem(SessionModel.KEY_TRIANGLE_BETWEEN,(int) (1*unit_screen));

        //make layouts for triangels
        ll_triangels=new LinearLayout(this);
        ll_triangels.setId(llId);
        ll_triangels.setOrientation(LinearLayout.VERTICAL);
        ll_triangels.setOnTouchListener(this);

        Space tri_space_1=new Space(this);
        tri_space_1.setLayoutParams(new LinearLayout.LayoutParams(tri_size, (int) (1*unit_screen)));
        ll_triangels.addView(tri_space_1);

        tri_1=new ImageView(this);
        tri_1.setImageResource(R.drawable.triangle_a);
        tri_1.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_size));
        ll_triangels.addView(tri_1);

        Space tri_space_2=new Space(this);
        tri_space_2.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_within_margin));
        ll_triangels.addView(tri_space_2);

        tri_2=new ImageView(this);
        tri_2.setImageResource(R.drawable.triangle_a);
        tri_2.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_size));
        ll_triangels.addView(tri_2);

        Space tri_space_3=new Space(this);
        tri_space_3.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_within_margin));
        ll_triangels.addView(tri_space_3);

        tri_3=new ImageView(this);
        tri_3.setImageResource(R.drawable.triangle_a);
        tri_3.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_size));
        ll_triangels.addView(tri_3);

        Space tri_space_4=new Space(this);
        tri_space_4.setLayoutParams(new LinearLayout.LayoutParams(tri_size, (int) (1*unit_screen)));
        ll_triangels.addView(tri_space_4);
        anim_tri_show();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(tri_size,
                (3*tri_size+4*tri_within_margin), WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE , PixelFormat.TRANSLUCENT);


        if(tri_isLeft)
            params.gravity = Gravity.LEFT | Gravity.TOP;
        else
            params.gravity = Gravity.RIGHT | Gravity.TOP;
        params.y=tri_margin_top;
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(ll_triangels, params);

        //animation for trangles
//        anim_tri_regular();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {


        super.onDestroy();
        if (ll_triangels != null) {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(ll_triangels);
            ll_triangels = null;
        }
    }
    final Handler handler = new Handler();
    Runnable mLongPressed = new Runnable() {
        public void run() {
            semi_isMoving=true;
            //semi-triangles
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(tri_size,
                    ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
            if(tri_isLeft)
                params.gravity = Gravity.LEFT | Gravity.TOP;
            else
                params.gravity = Gravity.RIGHT | Gravity.TOP;
            params.y=tri_margin_top;
            params.x= (int) (10*unit_screen);
            semi_wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            semi_ll_tri=semi_trangle(tri_margin_top);
            semi_wm.addView(semi_ll_tri, params);
        }
    };
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                handler.postDelayed(mLongPressed, 200);
                break;
            case MotionEvent.ACTION_UP:
                handler.removeCallbacks(mLongPressed);
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE && !semi_isMoving)
                {
                    anim_tri_hide();
                    intent_service=new Intent(this, OverlayButton.class);
                    final Intent dialogIntent = new Intent(this, MainActivity.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
                            stopService(intent_service);

                    //meysam - added in 13970318
                    new SessionModel(getApplicationContext()).saveItem(SessionModel.KEY_STARTED,true);
                    ////////////////////////////////

                    startActivity(dialogIntent);
//                        }
//                    }, 400);
                }
                else
                {
                    // consider as something else - a screen tap for example
                    anim_tri_small_touch();
                }
                ////////////
                if(semi_isMoving) {
                    semi_isMoving=false;
                    semi_wm.removeViewImmediate(semi_ll_tri);
                    y2 = event.getY();
                    wm.removeViewImmediate(ll_triangels);
                    WindowManager.LayoutParams params2 = new WindowManager.LayoutParams(tri_size,
                            (3 * tri_size + 4 * tri_within_margin), WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
                    if(tri_isLeft)
                        params2.gravity = Gravity.LEFT | Gravity.TOP;
                    else
                        params2.gravity = Gravity.RIGHT | Gravity.TOP;
                    tri_margin_top += y2 - y1;
                    session.saveItem(SessionModel.KEY_TRIANGLE_TOP_MARGIN,tri_margin_top);
                    params2.y = tri_margin_top;
                    wm = (WindowManager) getSystemService(WINDOW_SERVICE);
                    wm.addView(ll_triangels, params2);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                x2 = event.getX();
                float deltaX2 = x2 - x1;
                if (Math.abs(deltaX2) > 50 && Math.abs(deltaX2) < MIN_DISTANCE && !semi_isMoving)
                {
                    handler.removeCallbacks(mLongPressed);
                }
                if(semi_isMoving) {
                    y2 = event.getY();
                    float deltaY = y2 - y1;
                    ViewGroup.LayoutParams params_temp = semi_margin_top.getLayoutParams();
                    params_temp.height = (int) (tri_margin_top + deltaY);
                    semi_margin_top.setLayoutParams(params_temp);
                }
                break;
        }
        return true;
    }

    private LinearLayout semi_trangle(int margin_top){
        LinearLayout semi_ll_triangels=new LinearLayout(this);
        semi_ll_triangels.setOrientation(LinearLayout.VERTICAL);
        semi_ll_triangels.setOnTouchListener(this);

        semi_margin_top=new Space(this);
        semi_margin_top.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_within_margin+margin_top));
        semi_ll_triangels.addView(semi_margin_top);

        ImageView tri_1=new ImageView(this);
        tri_1.setImageResource(R.drawable.triangle_a_alpha);
        tri_1.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_size));
        semi_ll_triangels.addView(tri_1);

        Space tri_space_2=new Space(this);
        tri_space_2.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_within_margin));
        semi_ll_triangels.addView(tri_space_2);

        ImageView tri_2=new ImageView(this);
        tri_2.setImageResource(R.drawable.triangle_a_alpha);
        tri_2.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_size));
        semi_ll_triangels.addView(tri_2);

        Space tri_space_3=new Space(this);
        tri_space_3.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_within_margin));
        semi_ll_triangels.addView(tri_space_3);

        ImageView tri_3=new ImageView(this);
        tri_3.setImageResource(R.drawable.triangle_a_alpha);
        tri_3.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_size));
        semi_ll_triangels.addView(tri_3);

        Space tri_space_4=new Space(this);
        tri_space_4.setLayoutParams(new LinearLayout.LayoutParams(tri_size, tri_within_margin));
        semi_ll_triangels.addView(tri_space_4);

        return semi_ll_triangels;
    }

    private void anim_tri_regular(){
        Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION)
                .repeatCount(9999999)
                .duration(20000)
                .force(5)
                .createFor(tri_1)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION)
                .repeatCount(9999999)
                .duration(20000)
                .force(5)
                .createFor(tri_2)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION)
                .repeatCount(9999999)
                .duration(20000)
                .force(5)
                .createFor(tri_3)
                .start();
    }
    private void anim_tri_small_touch() {
        Flubber.with()
                .animation(Flubber.AnimationPreset.MORPH)
                .repeatCount(0)
                .duration(1000)
                .force(1)
                .createFor(tri_1)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.MORPH)
                .repeatCount(0)
                .duration(1000)
                .force(1)
                .createFor(tri_2)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.MORPH)
                .repeatCount(0)
                .duration(1000)
                .force(1)
                .createFor(tri_3)
                .start();
    }
    private void anim_tri_hide() {
        if(tri_isLeft) {
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_OUT)
                    .repeatCount(0)
                    .duration(100)
                    .force(1)
                    .createFor(tri_1)
                    .start();
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_OUT)
                    .repeatCount(0)
                    .duration(200)
                    .force(1)
                    .createFor(tri_2)
                    .start();
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_OUT)
                    .repeatCount(0)
                    .duration(300)
                    .force(1)
                    .createFor(tri_3)
                    .start();
        }else{
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_OUT)
                    .repeatCount(0)
                    .duration(100)
                    .force(1)
                    .createFor(tri_1)
                    .start();
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_OUT)
                    .repeatCount(0)
                    .duration(200)
                    .force(1)
                    .createFor(tri_2)
                    .start();
            Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_OUT)
                    .repeatCount(0)
                    .duration(300)
                    .force(1)
                    .createFor(tri_3)
                    .start();
        }
    }
    private void anim_tri_show() {
        Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION)
                .repeatCount(1)
                .duration(600)
                .force(5)
                .createFor(tri_1)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION)
                .repeatCount(1)
                .duration(600)
                .force(5)
                .createFor(tri_2)
                .start();
        Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION)
                .repeatCount(1)
                .duration(600)
                .force(5)
                .createFor(tri_3)
                .start();
        if(tri_isLeft) {
            Flubber.with()
                    .animation(Flubber.AnimationPreset.SQUEEZE_LEFT)
                    .repeatCount(0)
                    .duration(900)
                    .force(1)
                    .createFor(tri_1)
                    .start();
            Flubber.with()
                    .animation(Flubber.AnimationPreset.SQUEEZE_LEFT)
                    .repeatCount(0)
                    .duration(900)
                    .force(1)
                    .createFor(tri_2)
                    .start();
            Flubber.with()
                    .animation(Flubber.AnimationPreset.SQUEEZE_LEFT)
                    .repeatCount(0)
                    .duration(900)
                    .force(1)
                    .createFor(tri_3)
                    .start();
        }else{
            Flubber.with()
                    .animation(Flubber.AnimationPreset.SQUEEZE_RIGHT)
                    .repeatCount(0)
                    .duration(900)
                    .force(1)
                    .createFor(tri_1)
                    .start();
            Flubber.with()
                    .animation(Flubber.AnimationPreset.SQUEEZE_RIGHT)
                    .repeatCount(0)
                    .duration(900)
                    .force(1)
                    .createFor(tri_2)
                    .start();
            Flubber.with()
                    .animation(Flubber.AnimationPreset.SQUEEZE_RIGHT)
                    .repeatCount(0)
                    .duration(900)
                    .force(1)
                    .createFor(tri_3)
                    .start();
        }
    }
}