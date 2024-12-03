package ir.fardan7eghlim.tri.Reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Views.Home.OverlayButton;

/**
 * Created by Meysam on 5/12/2018.
 */

public class AtBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            if(new SessionModel(context).getBoolianItem(SessionModel.KEY_RUN_ON_PHONE_STARTUP,true))
            {
                //meysam - added in 13970318
                TriiiBroadcastReceiver.showCalenderNotification(context.getApplicationContext());
                TriiiBroadcastReceiver.initializeBroadcasts(context.getApplicationContext());
                new SessionModel(context.getApplicationContext()).saveItem(SessionModel.KEY_STARTED,true);
                new SessionModel(context.getApplicationContext()).saveItem(SessionModel.KEY_CLOSED,false);

                ///////////////////////////////

                Intent serviceIntent = new Intent(context, OverlayButton.class);
                serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(serviceIntent);
            }

        }
    }
}