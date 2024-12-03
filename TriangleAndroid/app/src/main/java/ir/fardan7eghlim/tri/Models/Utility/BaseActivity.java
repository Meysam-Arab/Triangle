package ir.fardan7eghlim.tri.Models.Utility;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Models.LogModel;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Views.Home.MainActivity;


/**
 * Created by Meysam on 3/8/2017.
 */

public class BaseActivity extends AppCompatActivity  {

    protected SessionModel session;
    private Thread llErrorThread;
    private TriThreadUncaughtExceptionHandler mThread;
    protected DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionModel(getApplicationContext());
        db = new DatabaseHandler(getApplicationContext());

        mThread = new TriThreadUncaughtExceptionHandler(this);
        llErrorThread = Thread.currentThread();
        llErrorThread.setDefaultUncaughtExceptionHandler(mThread);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public void onResume() {
        super.onResume();

        mThread = new TriThreadUncaughtExceptionHandler(this);
        llErrorThread = Thread.currentThread();
        llErrorThread.setDefaultUncaughtExceptionHandler(mThread);
    }

    @Override
    public void onBackPressed() {

        System.gc();
        super.onBackPressed();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        mThread.clear();
        mThread = null;
        llErrorThread.interrupt();
        super.onPause();
    }

    @Override
    protected void onUserLeaveHint()
    {
        super.onUserLeaveHint();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Utility.runningActivities == 0) {

        }
        Utility.runningActivities++;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Utility.runningActivities--;
        if (Utility.runningActivities == 0) {
            // app goes to background
        }
    }

}
