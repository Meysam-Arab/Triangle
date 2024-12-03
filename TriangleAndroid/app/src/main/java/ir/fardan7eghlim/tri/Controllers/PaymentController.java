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

public class PaymentController extends Observable {

    public Context cntx = null;

    public PaymentController(Context cntx)
    {
        this.cntx = cntx;
    }


    public void index(String uniqueIdenfifier)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

//            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("unique_identifier", uniqueIdenfifier);

            bodyParams.put("tag", RequestRespond.TAG_PAYMENTS_INDEX);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.PaymentsIndex,RequestRespond.TAG_PAYMENTS_INDEX,null);
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
    public void list(String operator)
    {
        try
        {
            // Post params to be sent to the server
            Map<String, String> headerParams = new HashMap<String, String>();
            Map<String, String> bodyParams = new HashMap<String, String>();

//            headerParams.put("Authorization", "Bearer "+ new SessionModel(cntx).getStoredToken());

            bodyParams.put("operator", operator);

            bodyParams.put("tag", RequestRespond.TAG_PAYMENTS_LIST);

            ReqResOperation(Request.Method.POST,headerParams,bodyParams, AppConfig.PaymentsList,RequestRespond.TAG_PAYMENTS_LIST,null);
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
        final Map<String, String>[] local_params = new Map[2];
        local_params[0] = headerParams;
        local_params[1] = bodyParams;


        StringRequest sr = new StringRequest(Request.Method.POST, address , new Response.Listener<String>() {

            Object result = null;
            @Override
            public void onResponse(String response) {

                RequestRespond rrm = new RequestRespond(cntx);
                try {


                    rrm.decodeJsonResponse(response.toString(),RequestRespond.RESPONSE_TYPE_JSON,null);


                    // Check for error node in json
                    if (rrm.getError() == null || rrm.getError() == 0) {

                        switch (rrm.getTag())
                        {

                            case RequestRespond.TAG_PAYMENTS_LIST:
                                ArrayList<Object> s=new ArrayList<Object>();
                                s.add(RequestRespond.TAG_PAYMENTS_LIST);
                                s.add(rrm.getItems());
                                result =s;
                                break;
                            case RequestRespond.TAG_PAYMENTS_INDEX:
                                s=new ArrayList<Object>();
                                s.add(RequestRespond.TAG_PAYMENTS_INDEX);
                                s.add(rrm.getItems());
                                result =s;
                                break;
                            default:

                                break;
                        }

                    } else {

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

                if(error instanceof AuthFailureError ||
                        error instanceof ClientError)
                {
//                    result = RequestRespond.ERROR_AUTH_FAIL_CODE;

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
