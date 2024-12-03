package ir.fardan7eghlim.tri.Controllers;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import ir.fardan7eghlim.tri.Models.LogModel;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.Utils.AppConfig;
import ir.fardan7eghlim.tri.Utils.RequestRespond;

/**
 * Created by Meysam on 12/21/2016.
 */

public class CurrencyController extends Observable {

    public Context cntx = null;

    public CurrencyController(Context cntx)
    {
        this.cntx = cntx;
    }
    public String lastGetTag;



    public void get()
    {
        try
        {
            String address = AppConfig.ParsijooGet;
            address += "?serviceType=price-API&query=Currency";

            lastGetTag = RequestRespond.TAG_CURRENCY_GET;
            ReqResOperation(Request.Method.GET,null,null, address,RequestRespond.TAG_CURRENCY_GET,null);
        }
        catch (Exception ex)
        {
//            LogModel logtmp = new LogModel(cntx);
//            log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//            log.setContollerName(this.getClass().getName());
//            log.insert();
            Utility.generateLogError(ex);

        }
    }

    private void ReqResOperation(int method,Map<String, String> headerParams,Map<String, String> bodyParams, String address, String tag, final Object obj)
    {



        if(method == Request.Method.GET)
        {


// prepare the RequestO

            StringRequest  getRequest = new StringRequest (Request.Method.GET, address,
                    new Response.Listener<String  >()
                    {
                        Object result = null;

                        @Override
                        public void onResponse(String  response) {
                            // display response
//                            Log.d("Response", response.toString());
                            RequestRespond rrm = new RequestRespond(cntx);
                            try {

                                if(lastGetTag != null)
                                {
                                    if(lastGetTag.equals(RequestRespond.TAG_CURRENCY_GET))
                                    {
                                        rrm.decodeJsonResponse(response.toString(),RequestRespond.RESPONSE_TYPE_XML,RequestRespond.TAG_CURRENCY_GET);
                                    }
                                    else
                                    {
                                        rrm.decodeJsonResponse(response.toString(),RequestRespond.RESPONSE_TYPE_JSON,null);
                                    }
                                    lastGetTag = null;
                                }
                                else
                                {
                                    rrm.decodeJsonResponse(response.toString(),RequestRespond.RESPONSE_TYPE_JSON,null);
                                }

                                // Check for error node in json
                                if (rrm.getError() == null || rrm.getError() == 0) {

                                    switch (rrm.getTag())
                                    {

                                        case RequestRespond.TAG_CURRENCY_GET:
                                            // successfully stored
                                            ArrayList<Object> s=new ArrayList<Object>();
                                            s.add(RequestRespond.TAG_CURRENCY_GET);
                                            s.add(rrm.getItem());
                                            result =s;
                                            break;
                                        default:
                                            break;
                                    }

                                } else {
                                    lastGetTag = null;
                                    if(rrm.getMust_logout())
                                    {
                                        result = false;
                                    }
                                    else
                                    {
                                        result = rrm.getError();
                                        // Error in login. Get the error message
                                        LogModel log = new LogModel(cntx);
                                        log.setErrorMessage("message: "+ rrm.getError_msg()+" CallStack: non, MeysamErrorCode:" + rrm.getError());
                                        log.setContollerName(this.getClass().getName());
                                        log.insert();
                                    }


                                }

                            } catch (Exception ex) {
                                // JSON error
//                    LogModel log = new LogModel(cntx);
//                    log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//                    log.setContollerName(this.getClass().getName());
//                    log.insert();
                                lastGetTag = null;
                                result = false;
                            }

                            setChanged();
                            notifyObservers(result);
//
                        }

                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Log.d("Error.Response", response);
                            Object result = false;
                            lastGetTag = null;
                            if(error instanceof AuthFailureError ||
                                    error instanceof ClientError)
                            {
//                                result = RequestRespond.ERROR_AUTH_FAIL_CODE;

                            }
                            else{
                                NetworkResponse response = error.networkResponse;
                                if (error instanceof ServerError && response != null) {

                                    String res = null;
                                    try {
                                        res = new String(response.data,
                                                HttpHeaderParser.parseCharset(response.headers));
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                    // Now you can use any deserializer to make sense of data

                                    try {
                                        JSONObject obj = new JSONObject(res);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

//                        LogModel log = new LogModel(cntx);
//                        log.setErrorMessage("header: "+res+" message: "+error.getMessage()+" CallStack: "+error.getStackTrace());
//                        log.setContollerName(this.getClass().getName());
//                        log.setUserId(null);
//                        log.insert();

                                }
                                else
                                {
//                        LogModel log = new LogModel(cntx);
//                        log.setErrorMessage("message: "+error.getMessage()+" CallStack: "+error.getStackTrace());
//                        log.setContollerName(this.getClass().getName());
//                        log.setUserId(null);
//                        log.insert();
                                }

                            }

                            setChanged();
                            notifyObservers(result);
                        }
                    }
            );
            // add it to the RequestQueue
            //meysam - volly retrying overrided
            RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                    Utility.TIMEOUT_TIME,
                    3,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            getRequest.setRetryPolicy(mRetryPolicy);

            AppController.getInstance().addToRequestQueue(getRequest);
        }
        else
        {
            final Map<String, String>[] local_params = new Map[2];
            local_params[0] = headerParams;
            local_params[1] = bodyParams;

            //meysam - POST request
            StringRequest sr = new StringRequest(method, address , new Response.Listener<String>() {

                Object result = null;
                @Override
                public void onResponse(String response) {

                    RequestRespond rrm = new RequestRespond(cntx);
                    try {

                        if(lastGetTag != null)
                        {
                            lastGetTag = null;
                        }

                        rrm.decodeJsonResponse(response.toString(),RequestRespond.RESPONSE_TYPE_JSON,null);


                        // Check for error node in json
                        if (rrm.getError() == null || rrm.getError() == 0) {

                            switch (rrm.getTag())
                            {

                                case RequestRespond.TAG_WEATHER_GET:
                                    // log successfully stored
                                    result = true;
                                    break;
                                default:

                                    break;
                            }

                        } else {
                            lastGetTag = null;
                            if(rrm.getMust_logout())
                            {
                                result = false;
                            }
                            else
                            {
                                result = rrm.getError();
                                // Error in login. Get the error message
                                LogModel log = new LogModel(cntx);
                                log.setErrorMessage("message: "+ rrm.getError_msg()+" CallStack: non, MeysamErrorCode:" + rrm.getError());
                                log.setContollerName(this.getClass().getName());
                                log.insert();
                            }


                        }

                    } catch (Exception ex) {
                        // JSON error
//                    LogModel log = new LogModel(cntx);
//                    log.setErrorMessage("message: "+ex.getMessage()+" CallStack: "+ex.getStackTrace());
//                    log.setContollerName(this.getClass().getName());
//                    log.insert();
                        lastGetTag = null;
                        result = false;
                    }

                    setChanged();
                    notifyObservers(result);
//
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Object result = false;
                    lastGetTag = null;
                    if(error instanceof AuthFailureError ||
                            error instanceof ClientError)
                    {
//                        result = RequestRespond.ERROR_AUTH_FAIL_CODE;

                    }
                    else{
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {

                            String res = null;
                            try {
                                res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            // Now you can use any deserializer to make sense of data

                            try {
                                JSONObject obj = new JSONObject(res);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                        LogModel log = new LogModel(cntx);
//                        log.setErrorMessage("header: "+res+" message: "+error.getMessage()+" CallStack: "+error.getStackTrace());
//                        log.setContollerName(this.getClass().getName());
//                        log.setUserId(null);
//                        log.insert();

                        }
                        else
                        {
//                        LogModel log = new LogModel(cntx);
//                        log.setErrorMessage("message: "+error.getMessage()+" CallStack: "+error.getStackTrace());
//                        log.setContollerName(this.getClass().getName());
//                        log.setUserId(null);
//                        log.insert();
                        }

                    }

                    setChanged();
                    notifyObservers(result);
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    if (local_params[1] == null) local_params[1] = new HashMap<>();
//                return local_params[1];
                    return checkParams(local_params[1]);
                }

                private Map<String, String> checkParams(Map<String, String> map){
                    Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
                        if(pairs.getValue()==null){
                            map.put(pairs.getKey(), "");

                            String message = "اخطار مقدار نال در پارامتر والی";
                            message += "لاگ";
                            message += "paramName:" + pairs.getKey();
                            Exception ex = new Exception(message);
                            Utility.generateLogError(ex);

                        }
                    }
                    return map;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    if (local_params[0] == null) local_params[0] = new HashMap<>();
                    return local_params[0];
                }
            };

            //meysam - volly retrying overrided
            RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                    Utility.TIMEOUT_TIME,
                    0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            sr.setRetryPolicy(mRetryPolicy);

            AppController.getInstance().addToRequestQueue(sr);

        }
        }

}
