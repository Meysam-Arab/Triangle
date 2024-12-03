package ir.fardan7eghlim.tri.Models.SQLiteHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

import ir.fardan7eghlim.tri.Models.CalenderEventModel;
import ir.fardan7eghlim.tri.Models.CityModel;
import ir.fardan7eghlim.tri.Models.CoinModel;
import ir.fardan7eghlim.tri.Models.CurrencyModel;
import ir.fardan7eghlim.tri.Models.DayModel;
import ir.fardan7eghlim.tri.Models.NewsModel;
import ir.fardan7eghlim.tri.Models.NoteModel;
import ir.fardan7eghlim.tri.Models.PaymentModel;
import ir.fardan7eghlim.tri.Models.PriceModel;
import ir.fardan7eghlim.tri.Models.SessionModel;
import ir.fardan7eghlim.tri.Models.TaskAlarmModel;
import ir.fardan7eghlim.tri.Models.TaskModel;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;


/**
 * Created by Meysam on 21/11/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context cntx;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;//meysam - last count added for app version 1.0.0

    // Database Name
    private static final String DATABASE_NAME = "triDB";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        cntx=context;
    }



    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
            createTables(db);
    }

    public void createTables(SQLiteDatabase db)
    {
        String CREATE_CALENDER_EVENTS_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_CalenderEvents + "("
                + KEY_CALENDER_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CALENDER_EVENT_DATE + " TEXT,"
                + KEY_CALENDER_EVENT_TITLE + " TEXT,"
                + KEY_CALENDER_EVENT_TYPE+ " INTEGER,"
                + KEY_CALENDER_EVENT_DESCRIPTION + " TEXT,"
                + KEY_CALENDER_EVENT_IS_HOLIDAY+ " INTEGER"+ ")";
        db.execSQL(CREATE_CALENDER_EVENTS_TABLE);
        String CREATE_PAYMENTS_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_Payments + "("
                + KEY_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PAYMENT_MOBILE_NUMBER + " TEXT,"
                + KEY_PAYMENT_AMOUNT + " TEXT,"
                + KEY_PAYMENT_SERVICE+ " INTEGER,"
                + KEY_PAYMENT_PARAMS + " INTEGER,"
                + KEY_PAYMENT_PAYMENT_STATUS + " INTEGER,"
                + KEY_PAYMENT_CREDIT_STATUS + " INTEGER,"
                + KEY_PAYMENT_DESCRIPTION + " TEXT,"
                + KEY_PAYMENT_AUTHORITY + " TEXT,"
                + KEY_PAYMENT_FOLLOWUP + " TEXT"+ ")";
        db.execSQL(CREATE_PAYMENTS_TABLE);
        String CREATE_DAYS_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_Days + "("
                + KEY_DAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_DAY_CITY_NAME + " TEXT,"
                + KEY_DAY_NAME + " TEXT,"
                + KEY_DAY_STATUS + " TEXT,"
                + KEY_DAY_TEMP + " INTEGER,"
                + KEY_DAY_MAX_TEMP+ " INTEGER,"
                + KEY_DAY_MIN_TEMP + " INTEGER,"
                + KEY_DAY_SYMBOL + " INTEGER" + ")";
        db.execSQL(CREATE_DAYS_TABLE);
        String CREATE_CURRENCIES_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_Currencies + "("
                + KEY_CURRENCY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CURRENCY_NAME + " TEXT,"
                + KEY_CURRENCY_PRICE + " TEXT,"
                + KEY_CURRENCY_CHANGE + " TEXT,"
                + KEY_CURRENCY_PERCENT + " TEXT" +")";
        db.execSQL(CREATE_CURRENCIES_TABLE);
        String CREATE_PRICE_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_Prices + "("
                + KEY_PRICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_PRICE_STATUS + " TEXT,"
                + KEY_PRICE_TIME + " TEXT,"
                + KEY_PRICE_TAG + " TEXT" +")";
        db.execSQL(CREATE_PRICE_TABLE);
        String CREATE_COIN_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_Coins + "("
                + KEY_COIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_COIN_NAME + " TEXT,"
                + KEY_COIN_PRICE + " TEXT,"
                + KEY_COIN_CHANGE + " TEXT,"
                + KEY_COIN_PERCENT + " TEXT" +")";
        db.execSQL(CREATE_COIN_TABLE);
        String CREATE_CITY_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_Cities + "("
                + KEY_CITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_CITY_PROVINCE + " TEXT,"
                + KEY_CITY_NAME + " TEXT,"
                + KEY_CITY_LATITUDE + " TEXT,"
                + KEY_CITY_LONGITUDE + " TEXT" +")";
        db.execSQL(CREATE_CITY_TABLE);
        String CREATE_TASK_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_Tasks + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TASK_DESCRIPTION + " TEXT,"
                + KEY_TASK_TYPE + " INTEGER,"
                + KEY_TASK_DAY_OF_WEEK + " TEXT,"
                + KEY_TASK_DAY_OF_MONTH + " TEXT,"
                + KEY_TASK_MONTH + " TEXT,"
                + KEY_TASK_YEAR + " TEXT,"
                + KEY_TASK_START_TIME + " TEXT,"
                + KEY_TASK_END_TIME + " TEXT,"
                + KEY_TASK_START_DATE + " INTEGER,"
                + KEY_TASK_END_DATE + " INTEGER,"
                + KEY_TASK_HAS_ALARM_SOUND + " INTEGER,"
                + KEY_TASK_ALARMS_MINUTES + " TEXT" +")";
        db.execSQL(CREATE_TASK_TABLE);

        String CREATE_TASK_ALARM_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_TaskAlarms + "("
                + KEY_TASK_ALARM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TASK_ALARM_TASK_ID + " INTEGER,"
                + KEY_TASK_ALARM_DAY_OF_WEEK + " TEXT,"
                + KEY_TASK_ALARM_DAY_OF_MONTH + " TEXT,"
                + KEY_TASK_ALARM_MONTH + " TEXT,"
                + KEY_TASK_ALARM_YEAR + " TEXT,"
                + KEY_TASK_ALARM_TIME + " TEXT"+")";
        db.execSQL(CREATE_TASK_ALARM_TABLE);

        String CREATE_NOTES_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_Notes + "("
                + KEY_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NOTE_CATEGORY + " INTEGER,"
                + KEY_NOTE_TITLE + " TEXT,"
                + KEY_NOTE_DESCRIPTION + " TEXT,"
                + KEY_NOTE_DATE_TIME + " TEXT"+")";
        db.execSQL(CREATE_NOTES_TABLE);

    }

    public void dropTables(SQLiteDatabase db)
    {

            // Simplest implementation is to drop all old tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CalenderEvents);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Payments);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Days);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Currencies);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Prices);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Coins);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Cities);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Tasks);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TaskAlarms);


        onCreate(db);
    }

    public void deleteAllTableRecords()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_CalenderEvents);
        db.execSQL("delete from "+ TABLE_Payments);
        db.execSQL("delete from "+ TABLE_Days);
        db.execSQL("delete from "+ TABLE_Currencies);
        db.execSQL("delete from "+ TABLE_Prices);
        db.execSQL("delete from "+ TABLE_Coins);
        db.execSQL("delete from "+ TABLE_Cities);
        db.execSQL("delete from "+ TABLE_Tasks);
        db.execSQL("delete from "+ TABLE_TaskAlarms);

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
           dropTables(db);
//            Intent intent = new Intent("main_activity_broadcast");
//            // You can also include some extra data.
//            intent.putExtra("logout", "true");
//            LocalBroadcastManager.getInstance(cntx).sendBroadcast(intent);
        }



//        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG_QUESTIONS);
//
//        // Create tables again
//        onCreate(db);

//        switch(oldVersion) {
//            case 1:
//                db.execSQL(DATABASE_CREATE_color);
//                // we want both updates, so no break statement here...
//            case 2:
//                db.execSQL(DATABASE_CREATE_someothertable);
//        }

    }

    public void dropAll(Context cntx) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CalenderEvents);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Payments);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Days);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Currencies);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Prices);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Coins);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Cities);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Tasks);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TaskAlarms);

        onCreate(db);
    }

    /////////////////////meysam - Calender Events table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_CalenderEvents = "calender_events";

    //  Table Columns names
    private static final String KEY_CALENDER_EVENT_ID = "calender_event_id";
    private static final String KEY_CALENDER_EVENT_DATE = "calender_event_date";
    private static final String KEY_CALENDER_EVENT_TYPE = "calender_event_type";
    private static final String KEY_CALENDER_EVENT_TITLE = "calender_event_title";
    private static final String KEY_CALENDER_EVENT_DESCRIPTION = "calender_event_description";
    private static final String KEY_CALENDER_EVENT_IS_HOLIDAY = "calender_event_is_holiday";



    /**
     * Storing CALENDER EVENT in database
     * */
    public void addCalenderEvent(CalenderEventModel calenderEvent, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();
        values.put(KEY_CALENDER_EVENT_DATE,calenderEvent.getDate()); // date
        values.put(KEY_CALENDER_EVENT_TYPE,calenderEvent.getType()); // type
        values.put(KEY_CALENDER_EVENT_TITLE,calenderEvent.getTitle()); // title
        values.put(KEY_CALENDER_EVENT_DESCRIPTION,calenderEvent.getDescription()); // description
        values.put(KEY_CALENDER_EVENT_IS_HOLIDAY,calenderEvent.getHoliday()); // is holiday


        // Inserting Row
        tdb.insert(TABLE_CalenderEvents, null, values);
    }

    /**
     * Storing CALENDER EVENT in database
     * */
    public void addCalenderEvent(CalenderEventModel calenderEvent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CALENDER_EVENT_DATE,calenderEvent.getDate()); // date
        values.put(KEY_CALENDER_EVENT_TYPE,calenderEvent.getType()); // type
        values.put(KEY_CALENDER_EVENT_TITLE,calenderEvent.getTitle()); // title
        values.put(KEY_CALENDER_EVENT_DESCRIPTION,calenderEvent.getDescription()); // description
        values.put(KEY_CALENDER_EVENT_IS_HOLIDAY,calenderEvent.getHoliday()); // is holiday

        // Inserting Row
        long id = db.insert(TABLE_CalenderEvents, null, values);
        db.close(); // Closing database connection

//        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting Calender Event from database
     * */
    public ArrayList<CalenderEventModel> getCalenderEventsByDateAndType(String date, Integer type) {
        ArrayList<CalenderEventModel> calenderEvents = new ArrayList<>();
        CalenderEventModel out = null;
        String selectQuery = "SELECT  * FROM " + TABLE_CalenderEvents + " WHERE 1=1 ";
        if (date != null)
        {
            selectQuery += "AND "+KEY_CALENDER_EVENT_DATE+" LIKE '"+date+"' ";;
        }
        if (type != null)
        {
            selectQuery += "AND "+KEY_CALENDER_EVENT_TYPE+ " = "+type;
        }


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        if (cursor.moveToFirst()) {
            do {
                out = new CalenderEventModel();
                out.setId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    out.setDate(cursor.getString(1));
                if(cursor.getString(2) != null)
                    out.setType(Integer.parseInt(cursor.getString(2)));
                if(cursor.getString(3) != null)
                    out.setTitle(cursor.getString(3));
                if(cursor.getString(4) != null)
                    out.setDescription(cursor.getString(4));
                if(cursor.getString(5) != null)
                    out.setHoliday(Integer.parseInt(cursor.getString(5)) == 1? true:false);

                // Adding to list
                calenderEvents.add(out);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return user
//        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return calenderEvents;
    }

    /**
     * Getting Calender Event from database
     * */
    public ArrayList<CalenderEventModel> getCalenderEventsByMonthAndType(String ghamariMonth, Integer type) {

//        String startDate =  Utility.addZeroFirst(month,2)+"01";
//        String endDate =  Utility.addZeroFirst(month,2)+"31";


        ArrayList<CalenderEventModel> calenderEvents = new ArrayList<>();
        CalenderEventModel out = null;
        String selectQuery = "SELECT  * FROM " + TABLE_CalenderEvents + " WHERE 1=1 ";
        if (ghamariMonth != null)
        {
            selectQuery += "AND "+KEY_CALENDER_EVENT_DATE+" LIKE '"+ghamariMonth+"%'";
        }
        if (type != null)
        {
            selectQuery += "AND "+KEY_CALENDER_EVENT_TYPE+ " = "+type;
        }


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        if (cursor.moveToFirst()) {
            do {
                out = new CalenderEventModel();
                out.setId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    out.setDate(cursor.getString(1));
                if(cursor.getString(2) != null)
                    out.setType(Integer.parseInt(cursor.getString(2)));
                if(cursor.getString(3) != null)
                    out.setTitle(cursor.getString(3));
                if(cursor.getString(4) != null)
                    out.setDescription(cursor.getString(4));
                if(cursor.getString(5) != null)
                    out.setHoliday(Integer.parseInt(cursor.getString(5)) == 1? true:false);

                // Adding to list
                calenderEvents.add(out);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        // return user
//        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return calenderEvents;
    }

    /**
     * Getting Calender Event from database
     * */
    public CalenderEventModel getCalenderEvent(BigInteger id) {
        CalenderEventModel out = null;

        String selectQuery = "SELECT  * FROM " + TABLE_CalenderEvents + " WHERE 1=1 ";
        if (id != null)
        {
            selectQuery += "AND "+KEY_CALENDER_EVENT_ID+" = "+id;
        }


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            out = new CalenderEventModel();
            out.setId(new BigInteger(cursor.getString(0)));
            if(cursor.getString(1) != null)
                out.setDate(cursor.getString(1));
            if(cursor.getString(2) != null)
                out.setType(Integer.parseInt(cursor.getString(2)));
            if(cursor.getString(3) != null)
                out.setTitle(cursor.getString(3));
            if(cursor.getString(4) != null)
                out.setDescription(cursor.getString(4));
            if(cursor.getString(5) != null)
                out.setHoliday(Integer.parseInt(cursor.getString(5)) == 1? true:false);
        }
        cursor.close();
        db.close();
        // return user
//        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());
        return out;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteCalenderEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CalenderEvents, null, null);
        db.close();

//        Log.d(TAG, "Deleted all user info from sqlite");
    }

    /////////////////////meysam - Calender Events table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////meysam - Payments Events table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_Payments = "payments";

    //  Table Columns names
    private static final String KEY_PAYMENT_ID = "payment_id";
    private static final String KEY_PAYMENT_MOBILE_NUMBER = "payment_mobile_number";
    private static final String KEY_PAYMENT_AMOUNT = "payment_amount";
    private static final String KEY_PAYMENT_SERVICE = "payment_service";
    private static final String KEY_PAYMENT_PARAMS = "payment_params";
    private static final String KEY_PAYMENT_PAYMENT_STATUS = "payment_payment_status";
    private static final String KEY_PAYMENT_CREDIT_STATUS = "payment_credit_status";
    private static final String KEY_PAYMENT_DESCRIPTION = "payment_description";
    private static final String KEY_PAYMENT_AUTHORITY = "payment_authority";
    private static final String KEY_PAYMENT_FOLLOWUP = "payment_follow_up";


    /**
     * Storing in database
     * */
    public void addPayment(PaymentModel payment, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();
        values.put(KEY_PAYMENT_MOBILE_NUMBER,payment.getMobileNumber());
        values.put(KEY_PAYMENT_AMOUNT,payment.getAmount());
        values.put(KEY_PAYMENT_SERVICE,payment.getService());
        values.put(KEY_PAYMENT_PARAMS,payment.getParams());
        values.put(KEY_PAYMENT_PAYMENT_STATUS,payment.getPaymentStatus());
        values.put(KEY_PAYMENT_CREDIT_STATUS,payment.getCreditStatus());
        values.put(KEY_PAYMENT_DESCRIPTION,payment.getDescription());
        values.put(KEY_PAYMENT_AUTHORITY,payment.getAuthority());
        values.put(KEY_PAYMENT_FOLLOWUP,payment.getFollowup());

        // Inserting Row
        tdb.insert(TABLE_Payments, null, values);
    }

    /**
     * Storing in database
     * */
    public void addPayment(PaymentModel payment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PAYMENT_MOBILE_NUMBER,payment.getMobileNumber());
        values.put(KEY_PAYMENT_AMOUNT,payment.getAmount());
        values.put(KEY_PAYMENT_SERVICE,payment.getService());
        values.put(KEY_PAYMENT_PARAMS,payment.getParams());
        values.put(KEY_PAYMENT_PAYMENT_STATUS,payment.getPaymentStatus());
        values.put(KEY_PAYMENT_CREDIT_STATUS,payment.getCreditStatus());
        values.put(KEY_PAYMENT_DESCRIPTION,payment.getDescription());
        values.put(KEY_PAYMENT_AUTHORITY,payment.getAuthority());
        values.put(KEY_PAYMENT_FOLLOWUP,payment.getFollowup());

        // Inserting Row
        long id = db.insert(TABLE_Payments, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Getting from database
     * */
    public PaymentModel getPayment(BigInteger id) {
        PaymentModel out = null;

        String selectQuery = "SELECT  * FROM " + TABLE_Payments + " WHERE 1=1 ";
        if (id != null)
        {
            selectQuery += "AND "+KEY_PAYMENT_ID+" = "+id;
        }


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            out = new PaymentModel();
            out.setPaymentId(new BigInteger(cursor.getString(0)));
            if(cursor.getString(1) != null)
                out.setMobileNumber(cursor.getString(1));
            if(cursor.getString(2) != null)
                out.setAmount(cursor.getString(2));
            if(cursor.getString(3) != null)
                out.setService(Integer.parseInt(cursor.getString(3)));
            if(cursor.getString(4) != null)
                out.setParams(Integer.parseInt(cursor.getString(4)));
            if(cursor.getString(5) != null)
                out.setPaymentStatus(Integer.parseInt(cursor.getString(5)));
            if(cursor.getString(6) != null)
                out.setCreditStatus(Integer.parseInt(cursor.getString(6)));
            if(cursor.getString(7) != null)
                out.setDescription(cursor.getString(7));
            if(cursor.getString(8) != null)
                out.setAuthority(cursor.getString(8));
            if(cursor.getString(9) != null)
                out.setFollowup(cursor.getString(9));
        }
        cursor.close();
        db.close();
        // return
        return out;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deletePayments() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_Payments, null, null);
        db.close();
    }

    /////////////////////meysam - payments table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////meysam - Days table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_Days= "days";

    //  Table Columns names
    private static final String KEY_DAY_ID = "day_id";
    private static final String KEY_DAY_NAME = "day_name";
    private static final String KEY_DAY_STATUS = "day_status";
    private static final String KEY_DAY_CITY_NAME = "day_city_name";
    private static final String KEY_DAY_SYMBOL = "day_symbol";
    private static final String KEY_DAY_TEMP = "day_temp";
    private static final String KEY_DAY_MAX_TEMP = "day_max_temp";
    private static final String KEY_DAY_MIN_TEMP = "day_min_temp";


    /**
     * Storing DAY in database
     * */
    public void addDay(DayModel day, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();
        values.put(KEY_DAY_CITY_NAME,day.getCityName()); // city name
        values.put(KEY_DAY_NAME,day.getDayName()); // day name
        values.put(KEY_DAY_STATUS,day.getStatus()); // status
        values.put(KEY_DAY_TEMP,day.getTemp()); // temp
        values.put(KEY_DAY_MAX_TEMP,day.getMaxTemp()); // max temp
        values.put(KEY_DAY_MIN_TEMP,day.getMinTemp()); // min temp
        values.put(KEY_DAY_SYMBOL,day.getSymbol()); // symbol

        // Inserting Row
        tdb.insert(TABLE_Days, null, values);
    }

    /**
     * Storing CALENDER EVENT in database
     * */
    public void addDay(DayModel day) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DAY_CITY_NAME,day.getCityName()); // city name
        values.put(KEY_DAY_NAME,day.getDayName()); // day name
        values.put(KEY_DAY_STATUS,day.getStatus()); // status
        values.put(KEY_DAY_TEMP,day.getTemp()); // temp
        values.put(KEY_DAY_MAX_TEMP,day.getMaxTemp()); // max temp
        values.put(KEY_DAY_MIN_TEMP,day.getMinTemp()); // min temp
        values.put(KEY_DAY_SYMBOL,day.getSymbol()); // symbol

        // Inserting Row
        long id = db.insert(TABLE_Days, null, values);
        db.close(); // Closing database connection
    }

    // Getting All days
    public ArrayList<DayModel> getDays() {
        ArrayList<DayModel> days = new ArrayList<DayModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Days ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DayModel day = new DayModel();

                if(cursor.getString(0) != null)
                    day.setDayId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    day.setCityName(cursor.getString(1));
                if(cursor.getString(2) != null)
                    day.setDayName(cursor.getString(2));
                if(cursor.getString(3) != null)
                    day.setStatus(cursor.getString(3));
                if(cursor.getString(4) != null)
                    day.setTemp(new Integer(cursor.getString(4)));
                if(cursor.getString(5) != null)
                    day.setMaxTemp(new Integer(cursor.getString(5)));
                if(cursor.getString(6) != null)
                    day.setMinTemp(new Integer(cursor.getString(6)));
                if(cursor.getString(7) != null)
                    day.setSymbol(new Integer(cursor.getString(7)));

                // Adding contact to list
                days.add(day);
            } while (cursor.moveToNext());
        }

        // return day list
        return days;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteDays() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_Days, null, null);
        db.close();

//        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void addDayes(ArrayList<DayModel> dayes, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteDays();
        for(int i = 0; i < dayes.size(); i++)
        {
            addDay(dayes.get(i));
        }
    }

    /////////////////////meysam - days table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////meysam - prices table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_Prices= "prices";

    //  Table Columns names
    private static final String KEY_PRICE_ID = "price_id";
    private static final String KEY_PRICE_STATUS = "price_status";
    private static final String KEY_PRICE_TIME = "price_time";
    private static final String KEY_PRICE_TAG = "price_tag";


    /**
     * Storing price in database
     * */
    public void addPrice(PriceModel price, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();
        values.put(KEY_PRICE_STATUS,price.getPriceStatus());
        values.put(KEY_PRICE_TIME,price.getPriceTime());
        values.put(KEY_PRICE_TAG,price.getPriceTag());

        // Inserting Row
        tdb.insert(TABLE_Prices, null, values);
    }

    /**
     * Storing price in database
     * */
    public void addPrice(PriceModel price) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PRICE_STATUS,price.getPriceStatus());
        values.put(KEY_PRICE_TIME,price.getPriceTime());
        values.put(KEY_PRICE_TAG,price.getPriceTag());

        // Inserting Row
        long id = db.insert(TABLE_Prices, null, values);
        db.close(); // Closing database connection
    }

    // Getting price by specific tag
    public PriceModel getPriceByTag(String priceTag) {
//        ArrayList<PriceModel> prices = new ArrayList<PriceModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Prices + " WHERE "+ KEY_PRICE_TAG +" = '"+priceTag+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        PriceModel price = new PriceModel();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
//            do {


                if(cursor.getString(0) != null)
                    price.setPriceId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    price.setPriceStatus(cursor.getString(1));
                if(cursor.getString(2) != null)
                    price.setPriceTime(cursor.getString(2));
                if(cursor.getString(3) != null)
                    price.setPriceTag(cursor.getString(3));

                // Adding to list
//                prices.add(price);
//            } while (cursor.moveToNext());
        }

        // return
        return price;
    }

    public Boolean deletePriceByTag(String priceTag) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_Prices, KEY_PRICE_TAG + " = '"+priceTag+"'", null) > 0;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deletePrices() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_Prices, null, null);
        db.close();

//        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void addPrices(ArrayList<PriceModel> prices, Boolean deleteOlds)
    {
        if(deleteOlds)
            deletePrices();
        for(int i = 0; i < prices.size(); i++)
        {
            addPrice(prices.get(i));
        }
    }

    /////////////////////meysam - prices table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////meysam - currencies table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_Currencies= "currencies";

    //  Table Columns names
    private static final String KEY_CURRENCY_ID = "currency_id";
    private static final String KEY_CURRENCY_NAME = "currency_name";
    private static final String KEY_CURRENCY_PRICE = "currency_price";
    private static final String KEY_CURRENCY_CHANGE = "currency_change";
    private static final String KEY_CURRENCY_PERCENT = "currency_percent";


    /**
     * Storing CURRENCY in database
     * */
    public void addCurrency(CurrencyModel currency, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();
        values.put(KEY_CURRENCY_NAME,currency.getCurrencyName()); //  name
        values.put(KEY_CURRENCY_CHANGE,currency.getCurrencyChange()); // change
        values.put(KEY_CURRENCY_PERCENT,currency.getCurrencyPercent()); // percent
        values.put(KEY_CURRENCY_PRICE,currency.getCurrencyPrice()); // price

        // Inserting Row
        tdb.insert(TABLE_Currencies, null, values);
    }

    /**
     * Storing CALENDER EVENT in database
     * */
    public void addCurrency(CurrencyModel currency) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_CURRENCY_NAME,currency.getCurrencyName()); //  name
        values.put(KEY_CURRENCY_CHANGE,currency.getCurrencyChange()); // change
        values.put(KEY_CURRENCY_PERCENT,currency.getCurrencyPercent()); // percent
        values.put(KEY_CURRENCY_PRICE,currency.getCurrencyPrice()); // price

        // Inserting Row
        long id = db.insert(TABLE_Currencies, null, values);
        db.close(); // Closing database connection
    }

    // Getting All currencies
    public ArrayList<CurrencyModel> getCurrencies() {
        ArrayList<CurrencyModel> currencies = new ArrayList<CurrencyModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Currencies ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CurrencyModel currency = new CurrencyModel();

                if(cursor.getString(0) != null)
                    currency.setCurrencyId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    currency.setCurrencyName(cursor.getString(1));
                if(cursor.getString(2) != null)
                    currency.setCurrencyChange(cursor.getString(2));
                if(cursor.getString(3) != null)
                    currency.setCurrencyPercent(cursor.getString(3));
                if(cursor.getString(4) != null)
                    currency.setCurrencyPrice(cursor.getString(4));

                // Adding contact to list
                currencies.add(currency);
            } while (cursor.moveToNext());
        }

        // return currency list
        return currencies;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteCurrencies() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_Currencies, null, null);
        db.close();

//        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void addCurrencies(ArrayList<CurrencyModel> currencies, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteCurrencies();
        for(int i = 0; i < currencies.size(); i++)
        {
            addCurrency(currencies.get(i));
        }
    }

    /////////////////////meysam - currencies table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////



    /////////////////////meysam - coins table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_Coins= "coins";

    //  Table Columns names
    private static final String KEY_COIN_ID = "coin_id";
    private static final String KEY_COIN_NAME = "coin_name";
    private static final String KEY_COIN_PRICE = "coin_price";
    private static final String KEY_COIN_CHANGE = "coin_change";
    private static final String KEY_COIN_PERCENT = "coin_percent";

    /**
     * Storing in database
     * */
    public void addCoin(CoinModel coin, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();
        values.put(KEY_COIN_NAME,coin.getCoinName());
        values.put(KEY_COIN_PRICE,coin.getCoinPrice());
        values.put(KEY_COIN_CHANGE,coin.getCoinChange());
        values.put(KEY_COIN_PERCENT,coin.getCoinPercent());

        // Inserting Row
        tdb.insert(TABLE_Coins, null, values);
    }

    /**
     * Storing in database
     * */
    public void addCoin(CoinModel coin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_COIN_NAME,coin.getCoinName());
        values.put(KEY_COIN_PRICE,coin.getCoinPrice());
        values.put(KEY_COIN_CHANGE,coin.getCoinChange());
        values.put(KEY_COIN_PERCENT,coin.getCoinPercent());

        // Inserting Row
        long id = db.insert(TABLE_Coins, null, values);
        db.close(); // Closing database connection
    }

    // Getting All records
    public ArrayList<CoinModel> getCoins() {
        ArrayList<CoinModel> coins = new ArrayList<CoinModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Coins ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CoinModel coin = new CoinModel();

                if(cursor.getString(0) != null)
                    coin.setCoinId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    coin.setCoinName(cursor.getString(1));
                if(cursor.getString(2) != null)
                    coin.setCoinPrice(cursor.getString(2));
                if(cursor.getString(3) != null)
                    coin.setCoinChange(cursor.getString(3));
                if(cursor.getString(4) != null)
                    coin.setCoinPercent(cursor.getString(4));

                // Adding to list
                coins.add(coin);
            } while (cursor.moveToNext());
        }

        // return list
        return coins;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteCoins() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_Coins, null, null);
        db.close();
    }

    public void addCoins(ArrayList<CoinModel> coins, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteCoins();
        for(int i = 0; i < coins.size(); i++)
        {
            addCoin(coins.get(i));
        }
    }

    /////////////////////meysam - coins table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////meysam - cities table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_Cities= "cities";

    //  Table Columns names
    private static final String KEY_CITY_ID = "city_id";
    private static final String KEY_CITY_NAME = "city_name";
    private static final String KEY_CITY_PROVINCE = "city_province";
    private static final String KEY_CITY_LATITUDE = "city_latitude";
    private static final String KEY_CITY_LONGITUDE = "city_longitude";


    /**
     * Storing in database
     * */
    public void addCity(CityModel city, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();
        values.put(KEY_CITY_NAME,city.getCityName());
        values.put(KEY_CITY_PROVINCE,city.getCityProvince());
        values.put(KEY_CITY_LATITUDE,city.getCityLatitude());
        values.put(KEY_CITY_LONGITUDE,city.getCityLongitude());

        // Inserting Row
        tdb.insert(TABLE_Cities, null, values);
    }

    /**
     * Storing in database
     * */
    public void addCity(CityModel city) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_CITY_NAME,city.getCityName());
        values.put(KEY_CITY_PROVINCE,city.getCityProvince());
        values.put(KEY_CITY_LATITUDE,city.getCityLatitude());
        values.put(KEY_CITY_LONGITUDE,city.getCityLongitude());

        // Inserting Row
        long id = db.insert(TABLE_Cities, null, values);
        db.close(); // Closing database connection
    }

    // Get All
    public ArrayList<CityModel> getCities() {
        ArrayList<CityModel> cities = new ArrayList<CityModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Cities ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CityModel city = new CityModel();

                if(cursor.getString(0) != null)
                    city.setCityId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    city.setCityName(cursor.getString(1));
                if(cursor.getString(2) != null)
                    city.setCityProvince(cursor.getString(2));
                if(cursor.getString(3) != null)
                    city.setCityLatitude(cursor.getString(3));
                if(cursor.getString(4) != null)
                    city.setCityLongitude(cursor.getString(4));


                // Adding to list
                cities.add(city);
            } while (cursor.moveToNext());
        }

        // return list
        return cities;
    }

    // Get All by specific fields
    public ArrayList<CityModel> getCitiesByProvince(String province) {
        ArrayList<CityModel> cities = new ArrayList<CityModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Cities +" WHERE " + KEY_CITY_PROVINCE + " LIKE '" + province +"'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CityModel city = new CityModel();

                if(cursor.getString(0) != null)
                    city.setCityId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    city.setCityProvince(cursor.getString(1));
                if(cursor.getString(2) != null)
                    city.setCityName(cursor.getString(2));
                if(cursor.getString(3) != null)
                    city.setCityLatitude(cursor.getString(3));
                if(cursor.getString(4) != null)
                    city.setCityLongitude(cursor.getString(4));


                // Adding to list
                cities.add(city);
            } while (cursor.moveToNext());
        }

        // return list
        return cities;
    }

    // Get All
    public ArrayList<String> getProvinces() {
        ArrayList<String> provinces = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT "+KEY_CITY_PROVINCE+" FROM " + TABLE_Cities;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do
            {
                if(cursor.getString(0) != null)
                    provinces.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }

        // return list
        return provinces;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteCities() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_Cities, null, null);
        db.close();

//        Log.d(TAG, "Deleted all info from sqlite");
    }

    public void addCities(ArrayList<CityModel> cities, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteCities();
        for(int i = 0; i < cities.size(); i++)
        {
            addCity(cities.get(i));
        }
    }

    /////////////////////meysam - cities table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////meysam - news table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_News= "news";

    //  Table Columns names
    private static final String KEY_NEWS_ID = "news_id";
    private static final String KEY_NEWS_TITLE = "news_title";
    private static final String KEY_NEWS_DESCRIPTION = "news_description";
    private static final String KEY_NEWS_LINK = "news_link";
    private static final String KEY_NEWS_PUBLICATION_DATE = "news_publication_date";
    private static final String KEY_NEWS_TYPE = "news_type";

    /**
     * Storing DAY in database
     * */
    public void addNews(NewsModel news, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();


        values.put(KEY_NEWS_TITLE,news.getNewsTitle());
        values.put(KEY_NEWS_DESCRIPTION,news.getNewsDescription());
        values.put(KEY_NEWS_LINK,news.getNewsLink());
        values.put(KEY_NEWS_PUBLICATION_DATE,news.getNewsPublicationDate());
        values.put(KEY_NEWS_TYPE,news.getNewsType());

        // Inserting Row
        tdb.insert(TABLE_News, null, values);
    }

    /**
     * Storing CALENDER EVENT in database
     * */
    public void addNews(NewsModel news) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NEWS_TITLE,news.getNewsTitle());
        values.put(KEY_NEWS_DESCRIPTION,news.getNewsDescription());
        values.put(KEY_NEWS_LINK,news.getNewsLink());
        values.put(KEY_NEWS_PUBLICATION_DATE,news.getNewsPublicationDate());
        values.put(KEY_NEWS_TYPE,news.getNewsType());

        // Inserting Row
        long id = db.insert(TABLE_News, null, values);
        db.close(); // Closing database connection
    }

    // Getting All
    public ArrayList<NewsModel> getNewsByType(String type) {
        ArrayList<NewsModel> newses = new ArrayList<NewsModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_News +" WHERE " + KEY_NEWS_TYPE + " LIKE '" + type +"'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NewsModel news = new NewsModel();

                if(cursor.getString(0) != null)
                    news.setNewsId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    news.setNewsTitle(cursor.getString(1));
                if(cursor.getString(2) != null)
                    news.setNewsDescription(cursor.getString(2));
                if(cursor.getString(3) != null)
                    news.setNewsLink(cursor.getString(3));
                if(cursor.getString(4) != null)
                    news.setNewsPublicationDate(cursor.getString(4));
                if(cursor.getString(5) != null)
                    news.setNewsType(cursor.getString(5));

                // Adding to list
                newses.add(news);
            } while (cursor.moveToNext());
        }

        // return day list
        return newses;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteNews() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_News, null, null);
        db.close();

//        Log.d(TAG, "Deleted all info from sqlite");
    }

    public void addNews(ArrayList<NewsModel> newses, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteNews();
        for(int i = 0; i < newses.size(); i++)
        {
            addNews(newses.get(i));
        }
    }

    /////////////////////meysam - news table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////meysam - tasks table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_Tasks= "tasks";

    //  Table Columns names
    private static final String KEY_TASK_ID = "task_id";
    private static final String KEY_TASK_DESCRIPTION = "task_description";
    private static final String KEY_TASK_TYPE = "task_type";
    private static final String KEY_TASK_DAY_OF_WEEK = "task_day_of_week";
    private static final String KEY_TASK_DAY_OF_MONTH = "task_day_of_month";
    private static final String KEY_TASK_MONTH = "task_month";
    private static final String KEY_TASK_YEAR = "task_year";
    private static final String KEY_TASK_START_TIME = "task_start_time";
    private static final String KEY_TASK_END_TIME = "task_end_time";
    private static final String KEY_TASK_START_DATE = "task_start_date";
    private static final String KEY_TASK_END_DATE = "task_end_date";
    private static final String KEY_TASK_HAS_ALARM_SOUND = "task_has_alarm_sound";
    private static final String KEY_TASK_ALARMS_MINUTES = "task_alarms_minutes";


    /**
     * Storing in database
     * */
    public void addTasks(TaskModel task, SQLiteDatabase tdb) {

        StringBuilder sb;
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_DESCRIPTION,task.getDescription());
        values.put(KEY_TASK_TYPE,task.getType());
        if(task.getDayOfWeek()!=null)
        {
            sb = new StringBuilder();
            for(String s: task.getDayOfWeek()) {
                sb.append(s).append(',');
            }

            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TASK_DAY_OF_WEEK, sb.toString());
        }
        values.put(KEY_TASK_DAY_OF_MONTH,task.getDayOfMonth());
        values.put(KEY_TASK_MONTH,task.getMonth());
        values.put(KEY_TASK_YEAR,task.getYear());
        values.put(KEY_TASK_START_TIME,task.getStartTime());
        values.put(KEY_TASK_END_TIME,task.getEndTime());
        values.put(KEY_TASK_START_DATE,Integer.valueOf(task.getStartDate()));
        values.put(KEY_TASK_END_DATE,Integer.valueOf(task.getEndDate()));
        values.put(KEY_TASK_HAS_ALARM_SOUND,task.getHasAlarmSound());
        if(task.getTaskAlarmsMinutes()!=null)
        {
            if(task.getTaskAlarmsMinutes().size() > 0)
            {
                sb = new StringBuilder();
                for(String s: task.getTaskAlarmsMinutes()) {
                    sb.append(s.toString()).append(',');
                }

                sb.deleteCharAt(sb.length()-1); //delete last comma
                values.put(KEY_TASK_ALARMS_MINUTES, sb.toString());
            }
        }

        // Inserting Row
        tdb.insert(TABLE_Tasks, null, values);
    }

    /**
     * Storing in database
     * */
    public void addTask(TaskModel task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        StringBuilder sb;

        values.put(KEY_TASK_DESCRIPTION,task.getDescription());
        values.put(KEY_TASK_TYPE,task.getType());
        if(task.getDayOfWeek()!=null)
        {
            sb = new StringBuilder();
            for(String s: task.getDayOfWeek()) {
                sb.append(s).append(',');
            }

            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TASK_DAY_OF_WEEK, sb.toString());
        }
        values.put(KEY_TASK_DAY_OF_MONTH,task.getDayOfMonth());
        values.put(KEY_TASK_MONTH,task.getMonth());
        values.put(KEY_TASK_YEAR,task.getYear());
        values.put(KEY_TASK_START_TIME,task.getStartTime());
        values.put(KEY_TASK_END_TIME,task.getEndTime());
        values.put(KEY_TASK_START_DATE,Integer.valueOf(task.getStartDate()));
        values.put(KEY_TASK_END_DATE,Integer.valueOf(task.getEndDate()));
        values.put(KEY_TASK_HAS_ALARM_SOUND,task.getHasAlarmSound());
        if(task.getTaskAlarmsMinutes()!=null)
        {
            if(task.getTaskAlarmsMinutes().size() > 0)
            {
                sb = new StringBuilder();
                for(String s: task.getTaskAlarmsMinutes()) {
                    sb.append(s.toString()).append(',');
                }

                sb.deleteCharAt(sb.length()-1); //delete last comma
                values.put(KEY_TASK_ALARMS_MINUTES, sb.toString());
            }
        }
        // Inserting Row
        long id = db.insert(TABLE_Tasks, null, values);
        for(int i = 0; i < task.getTaskAlarms().size(); i++)
        {
            TaskAlarmModel taskAlarm = task.getTaskAlarms().get(i);
            taskAlarm.setTaskId(BigInteger.valueOf(id));
            addTaskAlarm(taskAlarm);
        }

        db.close(); // Closing database connection
    }

    public void editTask(TaskModel task) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        StringBuilder sb;

        if(task.getDescription() != null)
            values.put(KEY_TASK_DESCRIPTION, task.getDescription());
        if(task.getType() != null)
            values.put(KEY_TASK_TYPE, task.getType());
        if(task.getDayOfWeek()!=null)
        {
            sb = new StringBuilder();
            for(String s: task.getDayOfWeek()) {
                sb.append(s).append(',');
            }

            sb.deleteCharAt(sb.length()-1); //delete last comma
            values.put(KEY_TASK_DAY_OF_WEEK, sb.toString());
        }
        if(task.getDayOfMonth() != null)
            values.put(KEY_TASK_DAY_OF_MONTH, task.getDayOfMonth());
        if(task.getMonth() != null)
            values.put(KEY_TASK_MONTH, task.getMonth());
        if(task.getYear() != null)
            values.put(KEY_TASK_YEAR, task.getYear());
        if(task.getStartTime() != null)
            values.put(KEY_TASK_START_TIME, task.getStartTime());
        if(task.getEndTime() != null)
            values.put(KEY_TASK_END_TIME, task.getEndTime());
        if(task.getStartDate() != null)
            values.put(KEY_TASK_START_DATE, task.getStartDate());
        if(task.getEndDate() != null)
            values.put(KEY_TASK_END_DATE, task.getEndDate());
        if(task.getHasAlarmSound() != null)
            values.put(KEY_TASK_HAS_ALARM_SOUND, task.getHasAlarmSound());
        if(task.getTaskAlarmsMinutes()!=null)
        {
            if(task.getTaskAlarmsMinutes().size() > 0)
            {
                sb = new StringBuilder();
                for(String s: task.getTaskAlarmsMinutes()) {
                    sb.append(s.toString()).append(',');
                }

                sb.deleteCharAt(sb.length()-1); //delete last comma
                values.put(KEY_TASK_ALARMS_MINUTES, sb.toString());
            }
        }

        // Edit Row
        long id = db.update(TABLE_Tasks, values,KEY_TASK_ID+"="+task.getId().toString(),null);
        db.close(); // Closing database connection
    }

    // Getting All
    public ArrayList<TaskModel> getTasksByType(String type) {
        ArrayList<TaskModel> tasks = new ArrayList<TaskModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Tasks +" WHERE " + KEY_TASK_TYPE + " = " + type;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskModel task = new TaskModel();

                if(cursor.getString(0) != null)
                    task.setId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    task.setDescription(cursor.getString(1));
                if(cursor.getString(2) != null)
                    task.setType(Integer.valueOf(cursor.getString(2)));
                if(cursor.getString(3) != null)
                    task.setDayOfWeek(new ArrayList<String>(Arrays.asList(cursor.getString(3).split("\\s*,\\s*"))));
                if(cursor.getString(4) != null)
                    task.setDayOfMonth(cursor.getString(4));
                if(cursor.getString(5) != null)
                    task.setMonth(cursor.getString(5));
                if(cursor.getString(6) != null)
                    task.setYear(cursor.getString(6));
                if(cursor.getString(7) != null)
                    task.setStartTime(cursor.getString(7));
                if(cursor.getString(8) != null)
                    task.setEndTime(cursor.getString(8));
                if(cursor.getString(9) != null)
                    task.setStartDate(String.valueOf(cursor.getInt(9)));
                if(cursor.getString(10) != null)
                    task.setEndDate(String.valueOf(cursor.getInt(10)));
                if(cursor.getString(11) != null)
                    task.setHasAlarmSound(Integer.parseInt(cursor.getString(11)) == 1? true:false);
                if(cursor.getString(12) != null)
                    task.setTaskAlarmsMinutes(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));


                task.setTaskAlarms(getTaskAlarmsByTaskId(task.getId()));

                // Adding to list
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        // return list
        return tasks;
    }

    // meysam - Getting All - all variables most be present...
    public ArrayList<TaskModel> getTasksByTime(String year, String month, String dayOfWeek, String dayOfMonth, String time, long date) {
        ArrayList<TaskModel> tasks = new ArrayList<TaskModel>();

//        long date = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));

//        if(month.equals("09"))
//        {
//            int x = 0;
//            Log.d("lde","ddd");
//        }
//        if(month != null && month.length()<2)
//        {
//            month = Utility.addZeroFirst(month,2);
//        }
//        if(dayOfMonth != null && dayOfMonth.length()<2)
//        {
//            dayOfMonth = Utility.addZeroFirst(dayOfMonth,2);
//        }


//        String selectQuery = "SELECT * FROM " + TABLE_Tasks+" WHERE " + KEY_TASK_ID+ " IN ( ";
//        String yearPortion = "";
//        if(year != null)
//        {
//            yearPortion = " AND " +KEY_TASK_YEAR + " LIKE '" + year + "' ";
//        }
//        String monthPortion = "";
//        if(month != null)
//        {
//            monthPortion = " AND " +KEY_TASK_MONTH + " LIKE '"+month+ "' ";
//        }
//        String dayOfMonthPortion = "";
//        if(dayOfMonth != null)
//        {
//            dayOfMonthPortion = " AND " +KEY_TASK_DAY_OF_MONTH + " LIKE '" + dayOfMonth + "' ";
//        }
//        String dayOfWeekPortion = "";
//        if(dayOfWeek != null)
//        {
//            dayOfWeekPortion = " AND " +KEY_TASK_DAY_OF_WEEK + " LIKE '%"+dayOfWeek + "%' ";
//        }
//        String timePortion = "";
//        if(time != null)
//        {
//            timePortion = " AND " + KEY_TASK_START_TIME + " LIKE '" + time + "' ";
//        }
//        String datePortion = "";
//        if(date != null)
//        {
//            datePortion = " AND " +KEY_TASK_START_DATE + " <= "+ date + " AND "+KEY_TASK_END_DATE + " >= "+ date;
//        }
//        selectQuery += "SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE "+ KEY_TASK_TYPE+" = "+ TaskModel.TYPE_NORMAL + yearPortion + monthPortion + dayOfMonthPortion

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Tasks;

        if(time != null)
        {
            selectQuery += " WHERE " + KEY_TASK_ID+ " IN ( " +
                    "SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE "+ KEY_TASK_TYPE+" = "+ TaskModel.TYPE_NORMAL+" AND " +KEY_TASK_YEAR + " LIKE '" + year + "' AND "+KEY_TASK_MONTH + " LIKE '"+month+ "' AND " + KEY_TASK_DAY_OF_MONTH + " LIKE '" + dayOfMonth + "' UNION "
                    +"SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE "+ KEY_TASK_TYPE+" = "+ TaskModel.TYPE_DAILY + " AND " + KEY_TASK_START_TIME + " LIKE '" + time + "' AND " +KEY_TASK_START_DATE + " <= "+ date + " AND "+KEY_TASK_END_DATE + " >= "+ date +  " UNION "
                    +"SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE "+ KEY_TASK_TYPE+" = "+ TaskModel.TYPE_WEEKLY + " AND "+ KEY_TASK_DAY_OF_WEEK + " LIKE '%"+dayOfWeek + "%' AND " + KEY_TASK_START_TIME + " LIKE '" + time + "' AND " +KEY_TASK_START_DATE + " <= "+ date + " AND "+KEY_TASK_END_DATE + " >= "+ date + " UNION "
                    +"SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE "+ KEY_TASK_TYPE+" = "+ TaskModel.TYPE_MONTHLY + " AND "+ KEY_TASK_DAY_OF_MONTH + " LIKE '"+dayOfMonth+ "' AND " + KEY_TASK_START_TIME + " LIKE '" + time + "' AND " +KEY_TASK_START_DATE + " <= "+ date + " AND "+KEY_TASK_END_DATE + " >= "+ date
                    + " ) ";
        }
        else
        {
            selectQuery += " WHERE " + KEY_TASK_ID+ " IN ( " +
                    "SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE "+ KEY_TASK_TYPE+" = "+ TaskModel.TYPE_NORMAL+" AND " +KEY_TASK_YEAR + " LIKE '" + year + "' AND "+KEY_TASK_MONTH + " = "+month+ " AND " + KEY_TASK_DAY_OF_MONTH + " LIKE '" + dayOfMonth + "' UNION "
                    +"SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE "+ KEY_TASK_TYPE+" = "+ TaskModel.TYPE_DAILY + " AND " +KEY_TASK_START_DATE + " <= "+ date + " AND "+KEY_TASK_END_DATE + " >= "+ date +  " UNION "
                    +"SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE "+ KEY_TASK_TYPE+" = "+ TaskModel.TYPE_WEEKLY + " AND "+ KEY_TASK_DAY_OF_WEEK + " LIKE "+dayOfWeek + " AND " +KEY_TASK_START_DATE + " <= "+ date + " AND "+KEY_TASK_END_DATE + " >= "+ date +  " UNION "
                    +"SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE "+ KEY_TASK_TYPE+" = "+ TaskModel.TYPE_MONTHLY + " AND "+ KEY_TASK_DAY_OF_MONTH + " LIKE '"+dayOfMonth+ "' AND " +KEY_TASK_START_DATE + " <= "+ date + " AND "+KEY_TASK_END_DATE + " >= "+ date
                    + " ) ";
        }

        selectQuery += "ORDER BY "+KEY_TASK_START_TIME +" ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskModel task = new TaskModel();

                if(cursor.getString(0) != null)
                    task.setId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    task.setDescription(cursor.getString(1));
                if(cursor.getString(2) != null)
                    task.setType(Integer.valueOf(cursor.getString(2)));
                if(cursor.getString(3) != null)
                    task.setDayOfWeek(new ArrayList<String>(Arrays.asList(cursor.getString(3).split("\\s*,\\s*"))));
                if(cursor.getString(4) != null)
                    task.setDayOfMonth(cursor.getString(4));
                if(cursor.getString(5) != null)
                    task.setMonth(cursor.getString(5));
                if(cursor.getString(6) != null)
                    task.setYear(cursor.getString(6));
                if(cursor.getString(7) != null)
                    task.setStartTime(cursor.getString(7));
                if(cursor.getString(8) != null)
                    task.setEndTime(cursor.getString(8));
                if(cursor.getString(9) != null)
                    task.setStartDate(String.valueOf(cursor.getInt(9)));
                if(cursor.getString(10) != null)
                    task.setEndDate(String.valueOf(cursor.getInt(10)));
                if(cursor.getString(11) != null)
                    task.setHasAlarmSound(Integer.parseInt(cursor.getString(11)) == 1? true:false);
                if(cursor.getString(12) != null)
                    task.setTaskAlarmsMinutes(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));


                task.setTaskAlarms(getTaskAlarmsByTaskId(task.getId()));

                // Adding to list
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        // return list
        return tasks;
    }

    // meysam - Getting All - all variables most be present...
    public ArrayList<TaskModel> getTasksByYearAndMonth(String year, String month) {
        ArrayList<TaskModel> tasks = new ArrayList<TaskModel>();

        Integer startDate = Integer.parseInt(year + Utility.addZeroFirst(month,2)+"01");
        Integer endDate = Integer.parseInt(year + Utility.addZeroFirst(month,2)+"31");


        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Tasks;


//        selectQuery += " WHERE " + KEY_TASK_ID+ " IN ( " +
//                    "SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE (" +KEY_TASK_START_DATE + " BETWEEN "+ startDate + " AND "+ endDate+ " )OR(  "+KEY_TASK_END_DATE + " BETWEEN "+ startDate + " AND "+ endDate+" )) ";
                selectQuery += " WHERE " + KEY_TASK_ID+ " IN ( " +
                    "SELECT " + KEY_TASK_ID +" FROM "+TABLE_Tasks+" WHERE "+KEY_TASK_END_DATE + " >= "+ endDate+" AND "+KEY_TASK_START_DATE + " <= "+ startDate+" ) ";


        selectQuery += "ORDER BY "+KEY_TASK_START_TIME +" ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskModel task = new TaskModel();

                if(cursor.getString(0) != null)
                    task.setId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    task.setDescription(cursor.getString(1));
                if(cursor.getString(2) != null)
                    task.setType(Integer.valueOf(cursor.getString(2)));
                if(cursor.getString(3) != null)
                    task.setDayOfWeek(new ArrayList<String>(Arrays.asList(cursor.getString(3).split("\\s*,\\s*"))));
                if(cursor.getString(4) != null)
                    task.setDayOfMonth(cursor.getString(4));
                if(cursor.getString(5) != null)
                    task.setMonth(cursor.getString(5));
                if(cursor.getString(6) != null)
                    task.setYear(cursor.getString(6));
                if(cursor.getString(7) != null)
                    task.setStartTime(cursor.getString(7));
                if(cursor.getString(8) != null)
                    task.setEndTime(cursor.getString(8));
                if(cursor.getString(9) != null)
                    task.setStartDate(String.valueOf(cursor.getInt(9)));
                if(cursor.getString(10) != null)
                    task.setEndDate(String.valueOf(cursor.getInt(10)));
                if(cursor.getString(11) != null)
                    task.setHasAlarmSound(Integer.parseInt(cursor.getString(11)) == 1? true:false);
                if(cursor.getString(12) != null)
                    task.setTaskAlarmsMinutes(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));


                task.setTaskAlarms(getTaskAlarmsByTaskId(task.getId()));

                // Adding to list
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        // return list
        return tasks;
    }

    // Getting All
    public TaskModel getTaskById(BigInteger taskId) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Tasks +" WHERE " + KEY_TASK_ID + " = " + taskId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        TaskModel task = null;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

                task = new TaskModel();

                if(cursor.getString(0) != null)
                    task.setId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    task.setDescription(cursor.getString(1));
                if(cursor.getString(2) != null)
                    task.setType(Integer.valueOf(cursor.getString(2)));
                if(cursor.getString(3) != null)
                    task.setDayOfWeek(new ArrayList<String>(Arrays.asList(cursor.getString(3).split("\\s*,\\s*"))));
                if(cursor.getString(4) != null)
                    task.setDayOfMonth(cursor.getString(4));
                if(cursor.getString(5) != null)
                    task.setMonth(cursor.getString(5));
                if(cursor.getString(6) != null)
                    task.setYear(cursor.getString(6));
                if(cursor.getString(7) != null)
                    task.setStartTime(cursor.getString(7));
                if(cursor.getString(8) != null)
                    task.setEndTime(cursor.getString(8));
                if(cursor.getString(9) != null)
                    task.setStartDate(String.valueOf(cursor.getInt(9)));
                if(cursor.getString(10) != null)
                    task.setEndDate(String.valueOf(cursor.getInt(10)));
                if(cursor.getString(11) != null)
                    task.setHasAlarmSound(Integer.parseInt(cursor.getString(11)) == 1? true:false);
                if(cursor.getString(12) != null)
                    task.setTaskAlarmsMinutes(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));


            task.setTaskAlarms(getTaskAlarmsByTaskId(task.getId()));

        }
        if(task.getHasAlarmSound())
            task.setTaskAlarms(getTaskAlarmsByTaskId(task.getId()));

        // return
        return task;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteTaskById(BigInteger taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows

        db.delete(TABLE_Tasks, KEY_TASK_ID + " = "+taskId, null);
        db.delete(TABLE_TaskAlarms, KEY_TASK_ALARM_TASK_ID + " = "+taskId, null);
        db.close();

//        Log.d(TAG, "Deleted all info from sqlite");
    }

    /**
     *  Delete all tasks
     * */
    public void deleteTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_Tasks, null, null);
        db.delete(TABLE_TaskAlarms, null, null);
        db.close();

//        Log.d(TAG, "Deleted all info from sqlite");
    }

    public void addTasks(ArrayList<TaskModel> tasks, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteTasks();
        for(int i = 0; i < tasks.size(); i++)
        {
            addTask(tasks.get(i));
        }
    }

    /////////////////////meysam - tasks table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////meysam - task alarms table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_TaskAlarms= "taskAlarms";

    //  Table Columns names
    private static final String KEY_TASK_ALARM_ID = "task_alarm_id";
    private static final String KEY_TASK_ALARM_TASK_ID = "task_alarm_task_id";
    private static final String KEY_TASK_ALARM_DAY_OF_WEEK = "task_alarm_day_of_week";
    private static final String KEY_TASK_ALARM_DAY_OF_MONTH = "task_alarm_day_of_month";
    private static final String KEY_TASK_ALARM_MONTH = "task_alarm_month";
    private static final String KEY_TASK_ALARM_YEAR = "task_alarm_year";
    private static final String KEY_TASK_ALARM_TIME = "task_alarm_time";


    /**
     * Storing in database
     * */
    public void addTaskAlarm(TaskAlarmModel taskAlarm, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_ALARM_TASK_ID,taskAlarm.getTaskId().longValue());
        values.put(KEY_TASK_ALARM_DAY_OF_WEEK,taskAlarm.getDayOfWeek());
        values.put(KEY_TASK_ALARM_DAY_OF_MONTH,taskAlarm.getDayOfMonth());
        values.put(KEY_TASK_ALARM_MONTH,taskAlarm.getMonth());
        values.put(KEY_TASK_ALARM_YEAR,taskAlarm.getYear());
        values.put(KEY_TASK_ALARM_TIME,taskAlarm.getTime());

        // Inserting Row
        tdb.insert(TABLE_TaskAlarms, null, values);
    }

    /**
     * Storing in database
     * */
    public void addTaskAlarm(TaskAlarmModel taskAlarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TASK_ALARM_TASK_ID,taskAlarm.getTaskId().longValue());
        values.put(KEY_TASK_ALARM_DAY_OF_WEEK,taskAlarm.getDayOfWeek());
        values.put(KEY_TASK_ALARM_DAY_OF_MONTH,taskAlarm.getDayOfMonth());
        values.put(KEY_TASK_ALARM_MONTH,taskAlarm.getMonth());
        values.put(KEY_TASK_ALARM_YEAR,taskAlarm.getYear());
        values.put(KEY_TASK_ALARM_TIME,taskAlarm.getTime());

        // Inserting Row
        long id = db.insert(TABLE_TaskAlarms, null, values);
        db.close(); // Closing database connection
    }

    // Getting All
    public ArrayList<TaskAlarmModel> getTaskAlarmsByTaskId(BigInteger taskId) {
        ArrayList<TaskAlarmModel> taskAlarms = new ArrayList<TaskAlarmModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TaskAlarms +" WHERE " + KEY_TASK_ALARM_TASK_ID+ " = " + taskId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskAlarmModel taskAlarm = new TaskAlarmModel();

                if(cursor.getString(0) != null)
                    taskAlarm.setId(new BigInteger(cursor.getString(1)));
                if(cursor.getString(1) != null)
                    taskAlarm.setTaskId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(2) != null)
                    taskAlarm.setDayOfWeek(cursor.getString(2));
                if(cursor.getString(3) != null)
                    taskAlarm.setDayOfMonth(cursor.getString(3));
                if(cursor.getString(4) != null)
                    taskAlarm.setMonth(cursor.getString(4));
                if(cursor.getString(5) != null)
                    taskAlarm.setYear(cursor.getString(5));
                if(cursor.getString(6) != null)
                    taskAlarm.setTime(cursor.getString(6));

                // Adding to list
                taskAlarms.add(taskAlarm);
            } while (cursor.moveToNext());
        }

        // return list
        return taskAlarms;
    }

    // meysam - Getting All - all variables most be present...
    public ArrayList<TaskModel> getTasksByTaskAlarmTime(String year, String month, String dayOfWeek, String dayOfMonth, String time) {
        ArrayList<TaskModel> tasks = new ArrayList<TaskModel>();
//        long date = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));
        long date = Integer.parseInt(new PersianCalendar().getPersianShortDate().replace("/",""));

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Tasks + " WHERE " + KEY_TASK_ID+ " IN ( " +
                //for daily alarms....
                "SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " IS NULL AND "
                + KEY_TASK_ALARM_MONTH + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " IS NULL AND "
                + KEY_TASK_ALARM_TIME + " LIKE '" + time +  "'" +  " UNION "
                //for weekly alarms
                +"SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " IS NULL AND "
                + KEY_TASK_ALARM_MONTH + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " LIKE '%"+dayOfWeek + "%' AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " IS NULL AND "
                + KEY_TASK_ALARM_TIME + " LIKE '" + time +  "' " +  " UNION "
                //for monthly alarms
                +"SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " IS NULL AND "
                + KEY_TASK_ALARM_MONTH +  " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " LIKE '" + dayOfMonth +  "' AND "
                + KEY_TASK_ALARM_TIME + " LIKE '" + time +  "'" +  " UNION "
                //for normal alarms
                +"SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " LIKE '" + year+ "' AND "
                + KEY_TASK_ALARM_MONTH +  " LIKE '" + month +  "' AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " LIKE '" + dayOfMonth +  "' AND "
                + KEY_TASK_ALARM_TIME + " LIKE '" + time +  "'" +  " ) "+ " AND " +KEY_TASK_START_DATE + " <= "+ date + " AND "+KEY_TASK_END_DATE + " >= "+ date;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskModel task = new TaskModel();

                if(cursor.getString(0) != null)
                    task.setId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    task.setDescription(cursor.getString(1));
                if(cursor.getString(2) != null)
                    task.setType(Integer.valueOf(cursor.getString(2)));
                if(cursor.getString(3) != null)
                    task.setDayOfWeek(new ArrayList<String>(Arrays.asList(cursor.getString(3).split("\\s*,\\s*"))));
                if(cursor.getString(4) != null)
                    task.setDayOfMonth(cursor.getString(4));
                if(cursor.getString(5) != null)
                    task.setMonth(cursor.getString(5));
                if(cursor.getString(6) != null)
                    task.setYear(cursor.getString(6));
                if(cursor.getString(7) != null)
                    task.setStartTime(cursor.getString(7));
                if(cursor.getString(8) != null)
                    task.setEndTime(cursor.getString(8));
                if(cursor.getString(9) != null)
                    task.setStartDate(String.valueOf(cursor.getInt(9)));
                if(cursor.getString(10) != null)
                    task.setEndDate(String.valueOf(cursor.getInt(10)));
                if(cursor.getString(11) != null)
                    task.setHasAlarmSound(Integer.parseInt(cursor.getString(11)) == 1? true:false);
                if(cursor.getString(12) != null)
                    task.setTaskAlarmsMinutes(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));


                // Adding to list
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        // return list
        return tasks;
    }

    // meysam - Getting All - all variables most be present...
    public ArrayList<TaskModel> getTasksByTaskAlarmDate(String year, String month, String dayOfWeek, String dayOfMonth) {
        ArrayList<TaskModel> tasks = new ArrayList<TaskModel>();
//        long date = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));
        long date = Integer.parseInt(new PersianCalendar().getPersianShortDate().replace("/",""));

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Tasks + " WHERE " + KEY_TASK_ID+ " IN ( " +
                //for daily alarms....
                "SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " IS NULL AND "
                + KEY_TASK_ALARM_MONTH + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " IS NULL "
                +  " UNION "
                //for weekly alarms
                +"SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " IS NULL AND "
                + KEY_TASK_ALARM_MONTH + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " LIKE '%"+dayOfWeek + "%' AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " IS NULL "
                +  " UNION "
                //for monthly alarms
                +"SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " IS NULL AND "
                + KEY_TASK_ALARM_MONTH +  " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " LIKE '" + dayOfMonth +  "' "
                +  " UNION "
                //for normal alarms
                +"SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " LIKE '" + year+ "' AND "
                + KEY_TASK_ALARM_MONTH +  " LIKE '" + month +  "' AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " LIKE '" + dayOfMonth +  "' "
                + " ) "+ " AND " +KEY_TASK_START_DATE + " <= "+ date + " AND "+KEY_TASK_END_DATE + " >= "+ date;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskModel task = new TaskModel();

                if(cursor.getString(0) != null)
                    task.setId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    task.setDescription(cursor.getString(1));
                if(cursor.getString(2) != null)
                    task.setType(Integer.valueOf(cursor.getString(2)));
                if(cursor.getString(3) != null)
                    task.setDayOfWeek(new ArrayList<String>(Arrays.asList(cursor.getString(3).split("\\s*,\\s*"))));
                if(cursor.getString(4) != null)
                    task.setDayOfMonth(cursor.getString(4));
                if(cursor.getString(5) != null)
                    task.setMonth(cursor.getString(5));
                if(cursor.getString(6) != null)
                    task.setYear(cursor.getString(6));
                if(cursor.getString(7) != null)
                    task.setStartTime(cursor.getString(7));
                if(cursor.getString(8) != null)
                    task.setEndTime(cursor.getString(8));
                if(cursor.getString(9) != null)
                    task.setStartDate(String.valueOf(cursor.getInt(9)));
                if(cursor.getString(10) != null)
                    task.setEndDate(String.valueOf(cursor.getInt(10)));
                if(cursor.getString(11) != null)
                    task.setHasAlarmSound(Integer.parseInt(cursor.getString(11)) == 1? true:false);
                if(cursor.getString(12) != null)
                    task.setTaskAlarmsMinutes(new ArrayList<String>(Arrays.asList(cursor.getString(12).split("\\s*,\\s*"))));


                // Adding to list
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        // return list
        return tasks;
    }

    // meysam - Getting All - all variables most be present...
    public ArrayList<TaskAlarmModel> getTaskAlarmsByDate(String year, String month, String dayOfWeek, String dayOfMonth) {
        ArrayList<TaskAlarmModel> taskAlarms = new ArrayList<TaskAlarmModel>();
        long date = Integer.parseInt(new PersianCalendar().getPersianShortDate().replace("/",""));

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TaskAlarms + " WHERE " + KEY_TASK_ALARM_TASK_ID+ " IN ( " + "SELECT "+KEY_TASK_ID+" FROM " + TABLE_Tasks + " WHERE " + KEY_TASK_ID+ " IN ( " +
                //for daily alarms....
                "SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " IS NULL AND "
                + KEY_TASK_ALARM_MONTH + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " IS NULL "
                +  " UNION "
                //for weekly alarms
                +"SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " IS NULL AND "
                + KEY_TASK_ALARM_MONTH + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " LIKE '%"+dayOfWeek + "%' AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " IS NULL "
                +  " UNION "
                //for monthly alarms
                +"SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " IS NULL AND "
                + KEY_TASK_ALARM_MONTH +  " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " LIKE '" + dayOfMonth +  "' "
                +  " UNION "
                //for normal alarms
                +"SELECT " + KEY_TASK_ALARM_TASK_ID +" FROM "+TABLE_TaskAlarms+" WHERE "
                + KEY_TASK_ALARM_YEAR + " LIKE '" + year+ "' AND "
                + KEY_TASK_ALARM_MONTH +  " LIKE '" + month +  "' AND "
                + KEY_TASK_ALARM_DAY_OF_WEEK + " IS NULL AND "
                + KEY_TASK_ALARM_DAY_OF_MONTH + " LIKE '" + dayOfMonth +  "' "
                + " ) "+ " AND " +KEY_TASK_START_DATE + " <= "+ date + " AND "+KEY_TASK_END_DATE + " >= "+ date+ " ) ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TaskAlarmModel taskAlarm = new TaskAlarmModel();

                if(cursor.getString(0) != null)
                    taskAlarm.setId(new BigInteger(cursor.getString(1)));
                if(cursor.getString(1) != null)
                    taskAlarm.setTaskId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(2) != null)
                    taskAlarm.setDayOfWeek(cursor.getString(2));
                if(cursor.getString(3) != null)
                    taskAlarm.setDayOfMonth(cursor.getString(3));
                if(cursor.getString(4) != null)
                    taskAlarm.setMonth(cursor.getString(4));
                if(cursor.getString(5) != null)
                    taskAlarm.setYear(cursor.getString(5));
                if(cursor.getString(6) != null)
                    taskAlarm.setTime(cursor.getString(6));

                // Adding to list
                taskAlarms.add(taskAlarm);
            } while (cursor.moveToNext());
        }

        // return list
        return taskAlarms;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteTaskAlarms() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_TaskAlarms, null, null);
        db.close();

//        Log.d(TAG, "Deleted all info from sqlite");
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteTaskAlarmsByTaskId(BigInteger taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows

        db.delete(TABLE_TaskAlarms, KEY_TASK_ALARM_TASK_ID + " = "+taskId, null);
        db.close();

//        Log.d(TAG, "Deleted all info from sqlite");
    }

    public void addTaskAlarms(ArrayList<TaskAlarmModel> taskAlarms, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteTaskAlarms();
        for(int i = 0; i < taskAlarms.size(); i++)
        {
            addTaskAlarm(taskAlarms.get(i));
        }
    }

    /////////////////////meysam - task alarms table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////meysam - notes table specifications - start/////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////
    //  table name
    private static final String TABLE_Notes= "notes";

    //  Table Columns names
    private static final String KEY_NOTE_ID = "note_id";
    private static final String KEY_NOTE_CATEGORY = "note_category";
    private static final String KEY_NOTE_TITLE = "note_title";
    private static final String KEY_NOTE_DESCRIPTION = "note_description";
    private static final String KEY_NOTE_DATE_TIME = "note_date_time";



    /**
     * Storing in database
     * */
    public void addNote(NoteModel note, SQLiteDatabase tdb) {

        ContentValues values = new ContentValues();
        values.put(KEY_NOTE_CATEGORY,note.getNoteCategory());
        values.put(KEY_NOTE_TITLE,note.getNoteTitle());
        values.put(KEY_NOTE_DESCRIPTION,note.getNoteDescription());
        values.put(KEY_NOTE_DATE_TIME,note.getNoteDateTime());

        // Inserting Row
        tdb.insert(TABLE_Notes, null, values);
    }

    /**
     * Storing in database
     * */
    public void addNote(NoteModel note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NOTE_CATEGORY,note.getNoteCategory());
        values.put(KEY_NOTE_TITLE,note.getNoteTitle());
        values.put(KEY_NOTE_DESCRIPTION,note.getNoteDescription());
        values.put(KEY_NOTE_DATE_TIME,note.getNoteDateTime());

        // Inserting Row
        long id = db.insert(TABLE_Notes, null, values);
        db.close(); // Closing database connection
    }

    /**
     * editing in database
     * */
    public void editNote(NoteModel note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        StringBuilder sb;

        if(note.getNoteCategory() != null)
            values.put(KEY_NOTE_CATEGORY, note.getNoteCategory());
        if(note.getNoteTitle() != null)
            values.put(KEY_NOTE_TITLE, note.getNoteTitle());
        if(note.getNoteDescription() != null)
            values.put(KEY_NOTE_DESCRIPTION, note.getNoteDescription());
        if(note.getNoteDateTime() != null)
            values.put(KEY_NOTE_DATE_TIME, note.getNoteDateTime());

        // Edit Row
        long id = db.update(TABLE_Notes, values,KEY_NOTE_ID+"="+note.getNoteId().toString(),null);
        db.close(); // Closing database connection
    }
    // Get All
    public ArrayList<NoteModel> getNotes() {
        ArrayList<NoteModel> notes = new ArrayList<NoteModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Notes;
        selectQuery += " ORDER BY "+KEY_NOTE_ID +" DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NoteModel note = new NoteModel();

                if(cursor.getString(0) != null)
                    note.setNoteId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    note.setNoteCategory(new Integer(cursor.getString(1)));
                if(cursor.getString(2) != null)
                    note.setNoteTitle(cursor.getString(2));
                if(cursor.getString(3) != null)
                    note.setNoteDescription(cursor.getString(3));
                if(cursor.getString(4) != null)
                    note.setNoteDateTime(cursor.getString(4));


                // Adding to list
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // return list
        return notes;
    }

    // Get All by specific fields
    public ArrayList<NoteModel> getNotesByCategory(Integer category) {
        ArrayList<NoteModel> notes = new ArrayList<NoteModel>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Notes +" WHERE " + KEY_NOTE_CATEGORY + " = " + category  ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                NoteModel note = new NoteModel();

                if(cursor.getString(0) != null)
                    note.setNoteId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    note.setNoteCategory(new Integer(cursor.getString(1)));
                if(cursor.getString(2) != null)
                    note.setNoteTitle(cursor.getString(2));
                if(cursor.getString(3) != null)
                    note.setNoteDescription(cursor.getString(3));
                if(cursor.getString(4) != null)
                    note.setNoteDateTime(cursor.getString(4));


                // Adding to list
                notes.add(note);
            } while (cursor.moveToNext());
        }

        // return list
        return notes;
    }

    // Get All by specific fields
    public NoteModel getNoteById(BigInteger noteId) {
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_Notes +" WHERE " + KEY_NOTE_ID + " = " + noteId  ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        NoteModel note = new NoteModel();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
                if(cursor.getString(0) != null)
                    note.setNoteId(new BigInteger(cursor.getString(0)));
                if(cursor.getString(1) != null)
                    note.setNoteCategory(new Integer(cursor.getString(1)));
                if(cursor.getString(2) != null)
                    note.setNoteTitle(cursor.getString(2));
                if(cursor.getString(3) != null)
                    note.setNoteDescription(cursor.getString(3));
                if(cursor.getString(4) != null)
                    note.setNoteDateTime(cursor.getString(4));
        }

        // return
        return note;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_Notes, null, null);
        db.close();

//        Log.d(TAG, "Deleted all info from sqlite");
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteNotesByCategory(Integer category) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_Notes, KEY_NOTE_CATEGORY + " = "+category, null);
        db.close();

//        Log.d(TAG, "Deleted all info from sqlite");
    }

    public void deleteNoteById(BigInteger noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows

        db.delete(TABLE_Notes, KEY_NOTE_ID + " = "+noteId, null);
        db.close();

//        Log.d(TAG, "Deleted all info from sqlite");
    }

    public void addNotes(ArrayList<NoteModel> notes, Boolean deleteOlds)
    {
        if(deleteOlds)
            deleteNotes();
        for(int i = 0; i < notes.size(); i++)
        {
            addNote(notes.get(i));
        }
    }

    /////////////////////meysam - notes table specifications - end///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////



    public void fillTables(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        //fill table
        SessionModel session = new SessionModel(cntx);
        session.saveItem(SessionModel.KEY_DATABASE_TABLE_STATUS,1);
        Scanner s = new Scanner(cntx.getResources().openRawResource(R.raw.cities));
        try
        {
            while (s.hasNext())
            {
                String temp=s.nextLine().toString();
                if(temp.equals("")) continue;
                String[] lineText = temp.split(",");
                if(lineText.length > 0)
                {
                    addCity(new CityModel(lineText[0], lineText[1], lineText[2], lineText[3]), db);
                }

            }

            s = new Scanner(cntx.getResources().openRawResource(R.raw.calender_events));
            while (s.hasNext())
            {
                String temp=s.nextLine().toString();
                if(temp.equals("")) continue;
                String[] lineText = temp.split("#");
                if(lineText.length > 0)
                {
                    addCalenderEvent(new CalenderEventModel(lineText[1],null, lineText[0], CalenderEventModel.TYPE_GHAMARI, lineText[2].equals("0")?false:true), db);
                }

            }

            db.setTransactionSuccessful();
            session.saveItem(SessionModel.KEY_DATABASE_TABLE_STATUS,2);
        }
        catch (Exception ex)
        {
            int x=2;
        }
        finally {
            s.close();
            db.endTransaction();
        }
    }


}
