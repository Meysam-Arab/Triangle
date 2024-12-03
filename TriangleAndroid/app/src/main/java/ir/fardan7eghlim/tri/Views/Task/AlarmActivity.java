package ir.fardan7eghlim.tri.Views.Task;

import android.animation.Animator;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appolica.flubber.Flubber;
import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.Utility.BaseActivity;
import ir.fardan7eghlim.tri.R;

public class AlarmActivity extends BaseActivity {

    private Ringtone currentRingtone;
    private TextView tv_description;
    private ImageView img_alarm_bg;
    private Boolean playSound;
    private ir.fardan7eghlim.tri.Models.widgets.customTextView digitalClock;

    private Animator anm_btnAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        digitalClock=findViewById(R.id.digitalClock);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        digitalClock.setText(sdf.format(new Date()));

        tv_description = findViewById(R.id.tv_description);
        img_alarm_bg=findViewById(R.id.img_alarm_bg);


        anm_btnAlarm = Flubber.with()
                .animation(Flubber.AnimationPreset.ZOOM_OUT)
                .interpolator(Flubber.Curve.BZR_EASE_OUT)
                .repeatCount(9999999)
                .duration(3000)
                .force(3)
                .createFor(img_alarm_bg);
        anm_btnAlarm.start();// Apply it to the view
        //////////////////////////////////////////



        playSound = getIntent().getBooleanExtra("play_sound",false);

        String[] descriptions = getIntent().getStringExtra("description").split("####");
        String description = "";
        for(int i=0;i<descriptions.length;i++)
        {
            if(i != 0)
                description += "\n";
            description += descriptions[i];
        }

        AudioManager audio = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        switch( audio.getRingerMode() ){
            case AudioManager.RINGER_MODE_NORMAL:

                if(playSound)
                {
                    Uri currentRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(this
                            .getApplicationContext(), RingtoneManager.TYPE_ALARM);
                    currentRingtone = RingtoneManager.getRingtone(getApplicationContext(), currentRintoneUri);

                    currentRingtone.play();
                }

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

        tv_description.setText(Html.fromHtml(description));
        SwipeButton enableButton =findViewById(R.id.swipe_btn);
        enableButton.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                if(playSound) {
                    currentRingtone.stop();
                }
                Flubber.with()
                        .animation(Flubber.AnimationPreset.WOBBLE)
                        .repeatCount(0)
                        .duration(2000)
                        .force(5)
                        .createFor(img_alarm_bg)
                        .start();
                Flubber.with()
                        .animation(Flubber.AnimationPreset.FADE_OUT)
                        .repeatCount(0)
                        .duration(2000)
                        .force(5)
                        .createFor(img_alarm_bg)
                        .start();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 2000);
            }
        });

//        SlideButton slideButton = new SlideButton(AlarmActivity.this);
//        slideButton.setOnSlideChangeListener(new SlideButton.OnSlideChangeListener() {
//            @Override
//            public void onSlideChange(float position) {
//                currentRingtone.stop();
//                Flubber.with()
//                        .animation(Flubber.AnimationPreset.SCALE_X)
//                        .repeatCount(9999999)
//                        .duration(3000)
//                        .force(5)
//                        .createFor(img_alarm_bg)
//                        .start();
//                Flubber.with()
//                        .animation(Flubber.AnimationPreset.FADE_OUT)
//                        .repeatCount(9999999)
//                        .duration(3000)
//                        .force(5)
//                        .createFor(img_alarm_bg)
//                        .start();
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        finish();
//                    }
//                }, 3000);
//            }
//        });


    }

    @Override
    public void onDestroy() {
        session.removeItem(SessionModel.KEY_IS_GOING_TO_ANOTHER_ACTIVITY);

        if (anm_btnAlarm != null) {
            anm_btnAlarm.end();
            anm_btnAlarm.cancel();
            anm_btnAlarm.removeAllListeners();
        }

        super.onDestroy();
    }
}
