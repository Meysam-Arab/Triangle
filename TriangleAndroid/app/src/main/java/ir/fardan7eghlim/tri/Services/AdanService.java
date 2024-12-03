package ir.fardan7eghlim.tri.Services;

/**
 * Created by Meysam on 5/2/2017.
 */


import android.animation.Animator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.Time;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.appolica.flubber.Flubber;

import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import io.saeid.oghat.PrayerTime;
import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.SoundModel;
import ir.fardan7eghlim.tri.Models.TaskModel;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Views.Home.MainActivity;
import ir.fardan7eghlim.tri.Views.Home.utility.DateTools;
import ir.fardan7eghlim.tri.Views.Home.utility.Utilities;
import ir.fardan7eghlim.tri.Views.Task.AlarmActivity;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static ir.fardan7eghlim.tri.Models.Utility.Utility.enToFa;

public class AdanService extends Service {

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    public static Boolean isRunning;

    private LinearLayout oView;
    private Context context = this;
    private double unit_screen, unit_screen_h;
    private WindowManager window;
    private FrameLayout btn;
    private ImageView adan_image;

    private SessionModel session;

    private Handler adanHandler;
    private Runnable adanThread;
    private Handler adanAnimationHandler;
    private Runnable adanAnimationThread;
    Animator anm_adan_fade_in;
    Animator anm_adan_fade_out;
    Animator anm_adan_transmit;
    Animator anm_adan_close_button;

    private Integer llId = 112555;//meysam - random number


    WindowManager wm;
//    private Boolean isAdanPlaying;
    private ImageView btnClose;

    private void playAzan(WindowManager wm, WindowManager.LayoutParams params) {
        showCloseBtn(true);
        String current_voice = session.getStringItem(SessionModel.KEY_AZAN_SOUND, SoundModel.MOAZEN_ZADEH);
        if (current_voice.equals(SoundModel.KAZEM_ZADEH_TRM)) {
            AzanKindKazemiTrim(wm, params);
        } else if (current_voice.equals(SoundModel.KAZEM_ZADEH)) {
            AzanKindKazemi(wm, params);
        } else if (current_voice.equals(SoundModel.AGHATI)) {
            AzanKindAghati(wm, params);
        } else {
            AzanKindMoazenZade(wm, params);
        }
    }

    private void AzanKindAghati(final WindowManager wm, final WindowManager.LayoutParams params) {

        AudioManager audio = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        switch (audio.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:

                new SoundModel(getApplicationContext()).playSound(session.getStringItem(SessionModel.KEY_AZAN_SOUND, SoundModel.MOAZEN_ZADEH));

                break;
            case AudioManager.RINGER_MODE_SILENT:
                //meysam - make music mute
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                //meysam - make music mute
                break;
            default:
                break;
        }


        final int[] azans_images = {R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_ashad_allah, R.drawable.adan_ashad_allah, R.drawable.adan_ashad_mohamad, R.drawable.adan_ashad_mohamad, R.drawable.adan_ashad_ali, R.drawable.adan_ashad_ali, R.drawable.adan_salat, R.drawable.adan_salat, R.drawable.adan_falah, R.drawable.adan_falah, R.drawable.adan_khairamal, R.drawable.adan_khairamal, R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_laelaha, R.drawable.adan_laelaha};
        final int[] azans_first_delays = {15, 27, 42, 63, 78, 98, 114, 131, 149, 165, 171, 183, 190, 205, 222, 237, 243, 254, 270};
        final int[] azans_times_staies = {7, 11, 17, 13, 15, 12, 10, 13, 12, 13, 4, 15, 8, 13, 10, 13, 7, 10, 10};

        for (int i = 0; i < azans_first_delays.length; i++) {
            adanHandler = new Handler();
            final int finalI = i;
            adanThread = new Runnable() {
                @Override
                public void run() {
                    if (finalI > 0) {
                        wm.removeView(oView);
                        oView.removeView(adan_image);
                        if (finalI == (azans_first_delays.length - 1)) {
                            btnClose.setVisibility(View.GONE);
                            showCloseBtn(false);
                            btn.removeView(btnClose);
                        }
                    }
                    adanAnimationKind_1(azans_images[finalI], params, azans_times_staies[finalI] * 1000);
                }
            };
            adanHandler.postDelayed(adanThread, azans_first_delays[i] * 1000);
        }
    }

    private void AzanKindMoazenZade(final WindowManager wm, final WindowManager.LayoutParams params) {

        AudioManager audio = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        switch (audio.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
                new SoundModel(getApplicationContext()).playSound(session.getStringItem(SessionModel.KEY_AZAN_SOUND, SoundModel.MOAZEN_ZADEH));
                break;
            case AudioManager.RINGER_MODE_SILENT:
                //meysam - make music mute
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                //meysam - make music mute
                break;
            default:
                break;
        }


        final int[] azans_images = {R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_ashad_allah, R.drawable.adan_ashad_allah, R.drawable.adan_ashad_mohamad, R.drawable.adan_ashad_mohamad, R.drawable.adan_ashad_ali, R.drawable.adan_ashad_ali, R.drawable.adan_salat, R.drawable.adan_salat, R.drawable.adan_falah, R.drawable.adan_falah, R.drawable.adan_khairamal, R.drawable.adan_khairamal, R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_laelaha, R.drawable.adan_laelaha};
        final int[] azans_first_delays = {0, 16, 33, 36, 50, 68, 85, 103, 122, 149, 175, 180, 191, 196, 206, 220, 235, 240, 252, 266};
        final int[] azans_times_staies = {11, 10, 6, 9, 12, 12, 11, 11, 21, 21, 6, 5, 5, 5, 10, 9, 7, 6, 10, 14};

        for (int i = 0; i < azans_first_delays.length; i++) {
            adanHandler = new Handler();
            final int finalI = i;
            adanThread = new Runnable() {
                @Override
                public void run() {
                    if (finalI > 0) {
                        wm.removeView(oView);
                        oView.removeView(adan_image);
                        if (finalI == (azans_first_delays.length - 1)) {
                            btnClose.setVisibility(View.GONE);
                            showCloseBtn(false);
                            btn.removeView(btnClose);
                        }
                    }

                    adanAnimationKind_1(azans_images[finalI], params, azans_times_staies[finalI] * 1000);
                }
            };
            adanHandler.postDelayed(adanThread, azans_first_delays[finalI] * 1000);
        }
    }

    private void AzanKindKazemiTrim(final WindowManager wm, final WindowManager.LayoutParams params) {

        AudioManager audio = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        switch (audio.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:

                new SoundModel(getApplicationContext()).playSound(session.getStringItem(SessionModel.KEY_AZAN_SOUND, SoundModel.MOAZEN_ZADEH));

                break;
            case AudioManager.RINGER_MODE_SILENT:
                //meysam - make music mute
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                //meysam - make music mute
                break;
            default:
                break;
        }

        final int[] azans_images = {R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar};
        final int[] azans_first_delays = {1, 7};
        final int[] azans_times_staies = {13, 14};

        for (int i = 0; i < azans_first_delays.length; i++) {
            adanHandler = new Handler();
            final int finalI = i;
            adanThread = new Runnable() {
                @Override
                public void run() {
                    if (finalI > 0) {
                        wm.removeView(oView);
                        oView.removeView(adan_image);
                        if (finalI == (azans_first_delays.length - 1)) {
                            btnClose.setVisibility(View.GONE);
                            showCloseBtn(false);
                            btn.removeView(btnClose);
                        }
                    }
                    adanAnimationKind_1(azans_images[finalI], params, azans_times_staies[finalI] * 1000);
                }
            };
            adanHandler.postDelayed(adanThread, azans_first_delays[i] * 1000);
        }
    }

    private void AzanKindKazemi(final WindowManager wm, final WindowManager.LayoutParams params) {


        AudioManager audio = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        switch (audio.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:

                new SoundModel(getApplicationContext()).playSound(session.getStringItem(SessionModel.KEY_AZAN_SOUND, SoundModel.MOAZEN_ZADEH));

                break;
            case AudioManager.RINGER_MODE_SILENT:
                //meysam - make music mute
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                //meysam - make music mute
                break;
            default:
                break;
        }


        final int[] azans_images = {R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_ashad_allah, R.drawable.adan_ashad_allah, R.drawable.adan_ashad_mohamad, R.drawable.adan_ashad_mohamad, R.drawable.adan_ashad_ali, R.drawable.adan_ashad_ali, R.drawable.adan_salat, R.drawable.adan_salat, R.drawable.adan_falah, R.drawable.adan_falah, R.drawable.adan_khairamal, R.drawable.adan_khairamal, R.drawable.adan_allah_akbar, R.drawable.adan_allah_akbar, R.drawable.adan_laelaha, R.drawable.adan_laelaha};
        final int[] azans_first_delays = {1, 7, 30, 36, 53, 75, 96, 117, 138, 159, 179, 190, 201, 213, 224, 238, 257, 264, 280, 296};
        final int[] azans_times_staies = {13, 14, 13, 14, 15, 15, 18, 15, 18, 15, 8, 8, 8, 7, 10, 15, 7, 11, 12, 13};

        for (int i = 0; i < azans_first_delays.length; i++) {
            adanHandler = new Handler();
            final int finalI = i;
            adanThread = new Runnable() {
                @Override
                public void run() {
                    if (finalI > 0) {
                        wm.removeView(oView);
                        oView.removeView(adan_image);
                        if (finalI == (azans_first_delays.length - 1)) {
                            btnClose.setVisibility(View.GONE);
                            showCloseBtn(false);
                            btn.removeView(btnClose);


                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // meysam - stop service after 30s = 30000ms
                                    AdanService.this.stopSelf();
                                }
                            }, 30000);

                        }
                    }
                    adanAnimationKind_1(azans_images[finalI], params, azans_times_staies[finalI] * 1000);
                }
            };
            adanHandler.postDelayed(adanThread, azans_first_delays[i] * 1000);
        }
    }

    private void adanAnimationKind_1(int azans_image, WindowManager.LayoutParams params, int stay_time) {
        adan_image = new ImageView(context);
        adan_image.setImageResource(azans_image);
        adan_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (unit_screen * 20), Gravity.CENTER_HORIZONTAL);
        layoutParams.setMargins((int) (unit_screen * 20), 00, (int) (unit_screen * 20), 0);
        adan_image.setLayoutParams(layoutParams);
        oView.addView(adan_image);

        anm_adan_fade_in = Flubber.with()
                .animation(Flubber.AnimationPreset.FADE_IN)
                .repeatCount(0)
                .duration(5000)
                .createFor(adan_image);
        anm_adan_fade_in.start();

        anm_adan_transmit = Flubber.with()
                .animation(Flubber.AnimationPreset.TRANSLATION_Y)
                .translateY((int) (unit_screen * -10), (int) (unit_screen * 30))
                .repeatCount(0)
                .duration(stay_time)
                .createFor(adan_image);
        anm_adan_transmit.start();

        wm.addView(oView, params);

        adanAnimationHandler = new Handler();
        adanAnimationThread = new Runnable() {
            @Override
            public void run() {
                anm_adan_fade_out = Flubber.with()
                        .animation(Flubber.AnimationPreset.FADE_OUT)
                        .repeatCount(0)
                        .duration(5000)
                        .createFor(adan_image);
                anm_adan_fade_out.start();

            }
        };
        adanAnimationHandler.postDelayed(adanAnimationThread, stay_time);
    }

    private void showCloseBtn(boolean show) {
        if (show) {
            btn = new FrameLayout(context);
            btn.setLayoutParams(new FrameLayout.LayoutParams((int) (unit_screen * 20), ViewGroup.LayoutParams.MATCH_PARENT));
            btnClose.setBackgroundResource(R.drawable.triangle_a_azan_off);
            btnClose.setLayoutParams(new LinearLayout.LayoutParams((int) (unit_screen * 20), (int) (unit_screen * 20), Gravity.CENTER));
            btn.addView(btnClose);

            anm_adan_close_button = Flubber.with()
                    .animation(Flubber.AnimationPreset.FADE_OUT_IN)
                    .repeatCount(9999999)
                    .duration(4000)
                    .createFor(btnClose);
            anm_adan_close_button.start();// Apply it to the view


            btnClose.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // meysam - stoping azan
                            if (SoundModel.isPlaying())
                                SoundModel.stopSound();
                            showCloseBtn(false);
                            if (anm_adan_close_button != null) {
                                if (anm_adan_close_button.isRunning()) {
                                    anm_adan_close_button.end();
                                    anm_adan_close_button.cancel();
                                }
                                anm_adan_close_button.removeAllListeners();
                                anm_adan_close_button = null;
                            }
                            if (anm_adan_fade_in != null) {
                                if (anm_adan_fade_in.isRunning()) {
                                    anm_adan_fade_in.end();
                                    anm_adan_fade_in.cancel();

                                }
                                anm_adan_fade_in.removeAllListeners();
                                anm_adan_fade_in = null;
                            }
                            if (anm_adan_transmit != null) {
                                if (anm_adan_transmit.isRunning()) {
                                    anm_adan_transmit.end();
                                    anm_adan_transmit.cancel();
                                }
                                anm_adan_transmit.removeAllListeners();
                                anm_adan_transmit = null;
                            }
                            if (anm_adan_fade_out != null) {
                                if (anm_adan_fade_out.isRunning()) {
                                    anm_adan_fade_out.end();
                                    anm_adan_fade_out.cancel();
                                }
                                anm_adan_fade_out.removeAllListeners();
                                anm_adan_fade_out = null;
                            }
                            if (adanHandler != null) {
                                adanHandler.removeCallbacks(adanThread);
                                adanHandler = null;
                            }
                            if (adanAnimationHandler != null) {
                                adanAnimationHandler.removeCallbacks(adanAnimationThread);
                                adanAnimationHandler = null;
                            }

                            wm.removeView(oView);
                            oView.removeView(adan_image);
                            btn.removeView(btnClose);
                            AdanService.this.stopSelf();
                            break;
                    }
                    return true;
                }
            });
            WindowManager.LayoutParams params_btn = new WindowManager.LayoutParams((int) (unit_screen * 20),
                    (int) (unit_screen * 20), WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
            params_btn.gravity = Gravity.CENTER;
            window.addView(btn, params_btn);
        } else {
            if (btn != null)
                window.removeViewImmediate(btn);
        }
    }


    @Override
    public void onCreate() {
        // To avoid cpu-blocking, we create a background handler to run our service
        HandlerThread thread = new HandlerThread("AdanService",
                Process.THREAD_PRIORITY_BACKGROUND);
        // start the new handler thread
        thread.start();

        session = new SessionModel(context);

        mServiceLooper = thread.getLooper();
        // start the service using the background handler
        mServiceHandler = new ServiceHandler(mServiceLooper);

        isRunning = false;

        btnClose = new ImageView(context);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(this, "chat start command", Toast.LENGTH_SHORT).show();

        if (isRunning == null || !isRunning) {
            isRunning = true;
            // call a new service handler. The service ID can be used to identify the service
            Message message = mServiceHandler.obtainMessage();
            message.arg1 = startId;
            mServiceHandler.sendMessage(message);

            //screen
            window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            Display display = window.getDefaultDisplay();
            unit_screen = display.getWidth() * 0.01;
            unit_screen_h = display.getHeight() * 0.01;

            oView = new LinearLayout(context);
            oView.setId(llId);
            oView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


            WindowManager.LayoutParams params;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
            }else{
                params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);
            }



            wm = (WindowManager) getSystemService(WINDOW_SERVICE);

            //meysam - وقت اذان
            playAzan(wm, params);


        }

        return START_STICKY ;

    }

    // Object responsible for
    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // Well calling mServiceHandler.sendMessage(message); from onStartCommand,
            // this method will be called.

        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Nullable
    @Override
    public boolean stopService(Intent name) {

        if (adanHandler != null) {
            adanHandler.removeCallbacks(adanThread);
            adanHandler = null;
        }
        if (adanAnimationHandler != null) {
            adanAnimationHandler.removeCallbacks(adanAnimationThread);
            adanAnimationHandler = null;
        }
        if (anm_adan_close_button != null) {
            if (anm_adan_close_button.isRunning()) {
                anm_adan_close_button.end();
                anm_adan_close_button.cancel();
            }
            anm_adan_close_button.removeAllListeners();
            anm_adan_close_button = null;
        }
        if (anm_adan_fade_in != null) {
            if (anm_adan_fade_in.isRunning()) {
                anm_adan_fade_in.end();
                anm_adan_fade_in.cancel();

            }
            anm_adan_fade_in.removeAllListeners();
            anm_adan_fade_in = null;
        }
        if (anm_adan_transmit != null) {
            if (anm_adan_transmit.isRunning()) {
                anm_adan_transmit.end();
                anm_adan_transmit.cancel();
            }
            anm_adan_transmit.removeAllListeners();
            anm_adan_transmit = null;
        }
        if (anm_adan_fade_out != null) {
            if (anm_adan_fade_out.isRunning()) {
                anm_adan_fade_out.end();
                anm_adan_fade_out.cancel();
            }
            anm_adan_fade_out.removeAllListeners();
            anm_adan_fade_out = null;
        }

        return super.stopService(name);
    }

    @Override
    public void onDestroy() {

        if (adanHandler != null) {
            adanHandler.removeCallbacks(adanThread);
            adanHandler = null;
        }
        if (adanAnimationHandler != null) {
            adanAnimationHandler.removeCallbacks(adanAnimationThread);
            adanAnimationHandler = null;
        }
        if (anm_adan_close_button != null) {
            if (anm_adan_close_button.isRunning()) {
                anm_adan_close_button.end();
                anm_adan_close_button.cancel();
            }
            anm_adan_close_button.removeAllListeners();
            anm_adan_close_button = null;
        }
        if (anm_adan_fade_in != null) {
            if (anm_adan_fade_in.isRunning()) {
                anm_adan_fade_in.end();
                anm_adan_fade_in.cancel();

            }
            anm_adan_fade_in.removeAllListeners();
            anm_adan_fade_in = null;
        }
        if (anm_adan_transmit != null) {
            if (anm_adan_transmit.isRunning()) {
                anm_adan_transmit.end();
                anm_adan_transmit.cancel();
            }
            anm_adan_transmit.removeAllListeners();
            anm_adan_transmit = null;
        }
        if (anm_adan_fade_out != null) {
            if (anm_adan_fade_out.isRunning()) {
                anm_adan_fade_out.end();
                anm_adan_fade_out.cancel();
            }
            anm_adan_fade_out.removeAllListeners();
            anm_adan_fade_out = null;
        }

        isRunning = null;
        super.onDestroy();
    }
}