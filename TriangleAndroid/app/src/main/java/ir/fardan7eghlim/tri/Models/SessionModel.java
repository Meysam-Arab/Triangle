package ir.fardan7eghlim.tri.Models;

import android.content.Context;
import android.content.SharedPreferences;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Meysam on 12/21/2016.
 */

public class SessionModel {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "TriPref";

    // All Shared Preferences Keys

    //meysam - triangle properties
    public static final String KEY_TRIANGLE_DIAMETER = "triangle_diameter";
    public static final String KEY_TRIANGLE_TOP_MARGIN = "triangle_top_margin";
    public static final String KEY_TRIANGLE_BETWEEN = "triangle_between";
    public static final String KEY_TRIANGLE_LEFT_RIGHT = "triangle_left_right";


    //meysam - buttons
    public static final String KEY_BUTTON_CONTACTS = "button_contact";

    //meysam - city
    public static final String KEY_CITY_PROVINCE = "city_province";
    public static final String KEY_CITY_NAME = "city_name";
    public static final String KEY_CITY_LATITUDE = "city_latitude";
    public static final String KEY_CITY_LONGITUDE = "city_longitude";

    //meysam - azan
    public static final String KEY_SHOW_PRAYER_TIMES = "show_prayer_times";
    public static final String KEY_PLAY_AZAN_SOBH = "play_azan_sobh";
    public static final String KEY_PLAY_AZAN_ZOHR = "play_azan_zohr";
    public static final String KEY_PLAY_AZAN_MAGHREB = "play_azan_maghreb";
    public static final String KEY_AZAN_SOUND = "azan_sound";

    //meysam - calender
    public static final String KEY_CALENDER_GHAMARI_CORRECTION = "calender_ghamari_correction";


    //meysam - city database table status
    public static final String KEY_DATABASE_TABLE_STATUS = "database_table_status";// 0 is empty, 1 is in processing, 2 is completed


    //meysam - for checking date and time
    public static final String KEY_VERSION_LAST_CHECKED_DATE_TIME = "version_last_checked_date_time";
    public static final String KEY_NOTIFICATION_LAST_CHECKED_DATE_TIME = "notification_last_checked_date_time";

    //meysam - charge
    public static final String KEY_CHARGE_RECENT_NUMBERS = "charge_recent_numbers";

    //meysam - run on startup
    public static final String KEY_RUN_ON_PHONE_STARTUP = "run_on_phone_startup";

    //meysam - show extended notification
    public static final String KEY_SHOW_EXTENDED_NOTIFICATION = "show_extended_notification";

    //meysam - show service triangles
    public static final String KEY_SHOW_SERVICE_TRIANGLES = "show_service_triangles";

    //meysam - show notification
    public static final String KEY_SHOW_NOTIFICATION = "show_notification";

    //meysam - weather last updated date
    public static final String KEY_WEATHER_LAST_UPDATED_DATE = "weather_last_updated_date";

    //meysam - news last category id
    public static final String KEY_NEWS_LAST_CATEGORY_ID = "news_last_category_id";

    //meysam - set going to another activity
    public static final String KEY_IS_GOING_TO_ANOTHER_ACTIVITY = "is_going_to_another_activity";

    //meysam - is access alert window permission granted
    public static final String KEY_IS_ACCESS_ALERT_WINDOW_PERMISSION_GRANTED = "is_access_alert_window_permission_granted";

    //meysam - is time picker tutorial showed
    public static final String KEY_IS_TIME_PICKER_TUTORIAL_SHOWED = "is_time_picker_tutorial_showed";

    public static final String KEY_STARTED = "app_started";
    public static final String KEY_CLOSED = "app_closed";

    // Constructor
    public SessionModel(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * Create login session
     * */
    public void saveItem(String itemName,Object item){
        if(item instanceof String)
        {
            removeItem(itemName);
            editor.putString(itemName, item.toString());

        }
        else if(item instanceof Float)
        {
            removeItem(itemName);
            editor.putFloat(itemName, new Float(item.toString()));
        }
        else if(item instanceof Double)
        {
            removeItem(itemName);
            editor.putFloat(itemName, new Float(item.toString()));
        }
        else if(item instanceof Integer)
        {
            removeItem(itemName);
            editor.putInt(itemName, new Integer(item.toString()));
        }
        else if(item instanceof BigInteger)
        {
            removeItem(itemName);
            editor.putLong(itemName, (long) item);

        }
        else if(item instanceof Boolean)
        {
            removeItem(itemName);
            editor.putBoolean(itemName, new Boolean(item.toString()));
        }
        else
        {
            removeItem(itemName);
            editor.putString(itemName, item.toString());
        }

        // commit changes
        editor.commit();
    }

    public void removeItem(String key){

        editor.remove(key);
        editor.apply();

        // commit changes
        editor.commit();
    }
    public String getStringItem(String key){

        return  pref.getString(key, null);
    }
    public String getStringItem(String key, String defaultValue){

        return  pref.getString(key, defaultValue);
    }
    public Boolean getBoolianItem(String key, Boolean defaultValue){

        return  pref.getBoolean(key, defaultValue);
    }

    public int getIntegerItem(String key, Integer defaultValue){
        return  pref.getInt(key, defaultValue);
    }

    public Boolean hasItem(String key)
    {
        if(pref.contains(key))
            return true;
        return false;
    }

    public void addToList(String key, String value, Integer listLimit)
    {
        Set<String> set = new HashSet<String>();
        ArrayList<String> list;
        if(pref.contains(key))
        {
            set = pref.getStringSet(key, null);
            list = new ArrayList<String>(set);
            if(list.size() == listLimit)
                list.remove(list.size());
            //Set the values

            set = new HashSet<String>(list);
            set.add(value);
        }
        else
        {
            //Set the values
            set.add(value);
        }


        editor.putStringSet(key, set);
        editor.commit();
    }

    public ArrayList getStringList(String key)
    {
        Set<String> set ;
        ArrayList<String> list;
        if(pref.contains(key))
        {
            set = pref.getStringSet(key, null);
            list = new ArrayList<String>(set);
        }
        else
        {
            list = new ArrayList<String>();

        }

        return list;

    }

    public void deleteAll()
    {
        editor.clear();
        editor.commit();
    }


}
