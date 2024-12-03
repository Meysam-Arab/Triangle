package ir.fardan7eghlim.tri.Models.Utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;

import ir.fardan7eghlim.tri.Models.LogModel;
import ir.fardan7eghlim.tri.Models.SessionModel;

/**
 * Created by Meysam on 5/3/2018.
 */

public class TriThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Context mContext;

    public TriThreadUncaughtExceptionHandler(Context cntx)
    {
        this.mContext = cntx;
    }

    @Override
    public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
        //Catch your exception
        // Without System.exit() this will not work.

        try {
            LogModel log = new LogModel(mContext.getApplicationContext());
            String message = "message: خطای کلی رخ داد :" + (paramThrowable == null?"ندارد":paramThrowable.getMessage());
            message += "#LocalizedMessage:#"+(paramThrowable == null?"ندارد":paramThrowable.getLocalizedMessage());
            message += "#Cause:#"+(paramThrowable == null?"ندارد":(paramThrowable.getCause() == null?"ندارد":paramThrowable.getCause().getMessage()));

            String stackTrace = "";
            if(paramThrowable == null)
                message += " #CallStack:# " + "ندارد";
            else
            {
                for (int i = 0; i < paramThrowable.getStackTrace().length; i++) {
                    stackTrace += paramThrowable.getStackTrace()[i].toString() + "\n";
                }
                Throwable tmp = paramThrowable;
                int j = 0;
                while ((tmp = tmp.getCause()) != null && j < 5) {
                    j++;
                    stackTrace += "Coused by:\n";
                    for (int i = 0; i < tmp.getStackTrace().length; i++) {
                        stackTrace += tmp.getStackTrace()[i].toString() + "\n";
                    }
                }


                message += " #CallStack:# " + stackTrace;

            }
            String deviceInfo = "";
            deviceInfo += "OS version: " + System.getProperty("os.version") + "\n";
            deviceInfo += "API level: " + Build.VERSION.SDK_INT + "\n";
            deviceInfo += "Manufacturer: " + Build.MANUFACTURER + "\n";
            deviceInfo += "Device: " + Build.DEVICE + "\n";
            deviceInfo += "Model: " + Build.MODEL + "\n";
            deviceInfo += "Product: " + Build.PRODUCT + "\n";

            PackageInfo pInfo = null;
            try {
                pInfo = mContext.getApplicationContext().getPackageManager().getPackageInfo(mContext.getApplicationContext().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if(pInfo != null)
                deviceInfo += "App Version: " + pInfo.versionName + "\n";
            else
                deviceInfo += "App Version: " + "not Specified(pInfo is null)" + "\n";

            message += " #Device Info:# " + deviceInfo;


            log.setErrorMessage(message);
            if(paramThread != null)
                if(paramThread.getContextClassLoader() != null)
                    if(paramThread.getContextClassLoader().getClass() != null)
                        log.setContollerName(paramThread.getContextClassLoader().getClass().getName());
//            log.setUserId(new SessionModel(mContext.getApplicationContext()).getCurrentUser().getId());
            log.insert();

            mContext = null;
//                    DialogPopUpModel.show(getApplicationContext(),"خطا رخ داد!! دوباره وارد شو","اهه!",null);
//                    Utility.displayToast(getApplicationContext(),"خطا رخ داد!! دوباره وارد شو",Toast.LENGTH_SHORT);
        }
        catch (Exception ex)
        {
            System.exit(2);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // Actions to do after 2 seconds
                System.exit(2);
            }
        }, 2000);

    }

    public void clear()
    {
        this.mContext = null;
    }

}
