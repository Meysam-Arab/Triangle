package ir.fardan7eghlim.tri.Services;

import android.app.IntentService;
import android.content.Intent;
import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;

public class DbService extends IntentService {
//    public static final String PARAM_IN_MSG = "imsg";
//    public static final String PARAM_OUT_MSG = "omsg";

    public DbService() {
        super("DbService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        DatabaseHandler db=new DatabaseHandler(getApplicationContext());
        db.fillTables();

    }

}
