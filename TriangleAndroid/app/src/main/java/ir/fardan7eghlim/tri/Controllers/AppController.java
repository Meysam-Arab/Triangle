package ir.fardan7eghlim.tri.Controllers;

/**
 * Created by Meysam on 2/16/2017.
 */
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Reciever.TriiiBroadcastReceiver;
import ir.fardan7eghlim.tri.Services.AdanService;
import ir.fardan7eghlim.tri.Services.DbService;
//import ir.fardan7eghlim.tri.Services.TimerService;


public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;



    @Override
    public void onCreate() {
        super.onCreate();

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);


        if(Utility.isNetworkAvailable(getApplicationContext())){
            // OneSignal Initialization
            OneSignal.startInit(this)
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                    .unsubscribeWhenNotificationsAreDisabled(true)
                    .init();
        }

        SessionModel session = new SessionModel(getApplicationContext());
        if(session.getIntegerItem(SessionModel.KEY_DATABASE_TABLE_STATUS,0) == 0 ||
                session.getIntegerItem(SessionModel.KEY_DATABASE_TABLE_STATUS,0) == 1)
        {
            session.saveItem(SessionModel.KEY_DATABASE_TABLE_STATUS,0);
            Intent msgIntent = new Intent(this, DbService.class);
            startService(msgIntent);
        }


        createNotificationChannel();

        mInstance = this;

        //meysam - commented in 13970318
//        Intent i= new Intent(getApplicationContext(), TimerService.class);
//        getApplicationContext().startService(i);

        //meysam - added in 13970318
        if(!session.getBoolianItem(SessionModel.KEY_STARTED,false))
        {
            TriiiBroadcastReceiver.showCalenderNotification(getApplicationContext());
            TriiiBroadcastReceiver.initializeBroadcasts(getApplicationContext());
            session.saveItem(SessionModel.KEY_STARTED,true);
            new SessionModel(getApplicationContext()).saveItem(SessionModel.KEY_CLOSED,false);

        }
        ///////////////////////////////////////////

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    @Override
    public void onLowMemory() {
        // meysam - code for low memory detection
//        Utility.displayToast(getApplicationContext(), "کمبود مموری!!", Toast.LENGTH_SHORT);
        // This is called when the overall system is running low on memory, and would like
        // actively running processes to tighten their belts.
        super.onLowMemory();
    }

//    @Override
//    public void onTerminate() {
//        // meysam - code for terminating detection
//        // This method is for use in emulated process environments.
//        // It will never be called on a production Android device, where processes
//        // are removed by simply killing them; no user code (including this callback) is executed when doing so.
//        super.onTerminate();
//    }
private void createNotificationChannel() {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        CharSequence name = getString(R.string.str_notification_chanel_name);//میثم - کاربر در ستینگ خودش میبیند اندروید هشت به بالا
        String description = getString(R.string.str_notification_channel_description);//میثم - کاربر در ستینگ خودش می بیند در اندریود هشت به بالا
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(Utility.NOTIFICATION_CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}

}