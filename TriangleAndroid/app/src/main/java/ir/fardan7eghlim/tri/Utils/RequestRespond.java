package ir.fardan7eghlim.tri.Utils;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import ir.fardan7eghlim.tri.Models.CoinModel;
import ir.fardan7eghlim.tri.Models.CurrencyModel;
import ir.fardan7eghlim.tri.Models.DayModel;
import ir.fardan7eghlim.tri.Models.LogModel;
import ir.fardan7eghlim.tri.Models.NewsModel;
import ir.fardan7eghlim.tri.Models.PaymentModel;
import ir.fardan7eghlim.tri.Models.PriceModel;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.Models.Utility.XMLParser;
import ir.fardan7eghlim.tri.R;


/**
 * Created by Meysam on 2/16/2017.
 */

public class RequestRespond {

    //meysam - response type...
    public static final String RESPONSE_TYPE_JSON = "json";
    public static final String RESPONSE_TYPE_XML = "xml";

    /////////////////////////////

    // STATUS success code
    private static final int STATUS_SUCCESS_CODE = 1;

    // STATUS fail code
    private static final int STATUS_FAIL_CODE = 2;

    // STATUS error code
    private static final int STATUS_ERROR_CODE = 3;

    // STATUS undefined code
    private static final int STATUS_UNDEFINED_CODE = 0;

    ////////////////////////////////////////////////////////

    // tag codes

    //public
    public static final String TAG_UNDEFINED = "undefined";

    //payments
    public static final String TAG_PAYMENTS_INDEX = "payment_index";
    public static final String TAG_PAYMENTS_LIST = "payment_list";


    //log
    public static final String TAG_LOGS_STORE = "logs_store";

    //weather
    public static final String TAG_WEATHER_GET = "weather_get";

    //currency
    public static final String TAG_CURRENCY_GET = "currency_get";

    //coin
    public static final String TAG_COIN_GET = "coin_get";

    //home
    public static final String TAG_HOME_GET_VERSION="home_get_version";

    //rss news
    public static final String TAG_RSS_NEWS_GET = "rss_news_get";


    //feedback
    public static final String TAG_FEEDBACK_STORE = "feedback_store";

    ////////////////////////////////////////////////////////

    // ERROR codes
    //public
    public static final int ERROR_UNDEFINED_CODE = -1;
    public static final int ERROR_ITEM_EXIST_CODE = 1;
    public static final int ERROR_INSERT_FAIL_CODE = 2;
    public static final int ERROR_TOKEN_MISMACH_CODE = 3;
    public static final int ERROR_DEFECTIVE_INFORMATION_CODE = 4;
    public static final int ERROR_TOKEN_BLACKLISTED_CODE = 5;
    public static final int ERROR_INVALID_FILE_SIZE_CODE = 6;
    public static final int ERROR_UPDATE_FAIL_CODE = 7;
    public static final int ERROR_DELETE_FAIL_CODE = 8;
    public static final int ERROR_OPERATION_FAIL_CODE = 9;
    public static final int ERROR_ITEM_NOT_EXIST_CODE = 10;
    public static final int ERROR_UNAUTHORIZED_ACCESS_CODE = 11;
    public static final int ERROR_WRONG_CHARSET = 12;
    public static final int ERROR_TOKEN_INVALID = 13;
    public static final int ERROR_CHARGE_SERVICE_DISABLED = 18;


    //user
    public static final int ERROR_USER_EXIST_CODE = 14;
    public static final int ERROR_EMAIL_EXIST_CODE = 15;


    //message
    public static final int ERROR_INVALID_CODE_OF_INVITE_CODE = 16;

    //weather
    public static final int ERROR_GET_WEATHER = 17;

    /////////////////////////////////////////////////////////



    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
    public Boolean getMust_logout() {
        return must_logout;
    }

    public void setMust_logout(Boolean must_logout) {
        this.must_logout = must_logout;
    }


    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public Boolean getBoolianItem() {
        return boolianItem;
    }

    public void setBoolianItem(Boolean boolianItem) {
        this.boolianItem = boolianItem;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
    /////////////////////////////////////////////
    private Integer error;
    private String token;
    private String error_msg;
    private String tag;
    private Boolean must_logout;
    private Boolean boolianItem;
    //////
    private Object item;
    private List<Object> items;

    ///////////////////////////////////////
    private Context cntx;

    ////////////////////////////////
    public RequestRespond(Context cntx) {
        this.error = null;
        this.error_msg = null;
        this.tag = null;
        this.token = null;
        this.must_logout = false;

        this.cntx = cntx;
    }

    public void decodeJsonResponse(String response, String responseType, String tag)
    {
        try
        {
            JSONObject jObj = null;
            String sObj = null;

            if(tag != null)
                this.setTag(tag);

            if(responseType.equals(RESPONSE_TYPE_JSON))
            {
                jObj = new JSONObject(response);
                this.error = jObj.getInt("error");
            }
            else
            {
                error = 0;
                if(response.equals("ERROR"))
                {
                    this.error = ERROR_GET_WEATHER;
                }
                sObj = response;
            }

            // Check for error node in json
            if (error == 0)
            {
                if(jObj != null)
                    if(jObj.has("tag"))
                        this.setTag( jObj.getString("tag"));
                switch (this.getTag().toLowerCase())
                {
                    case TAG_LOGS_STORE:
                    {
//                        this.token = jObj.getString("token");
//                        this.error = this.isTokenValid() == true?0:ERROR_TOKEN_MISMACH_CODE;
                        break;
                    }
                    case TAG_PAYMENTS_INDEX:
                    {
                        // user successfully logged in
                        // Now store the user in SQLite
//                        this.token = jObj.getString("token");

                        JSONArray payments = jObj.getJSONArray("payments");
                        PaymentModel payment;
                        this.items = new ArrayList<>();
                        for (int i = 0; i < payments.length(); i++) {
                            payment = new PaymentModel();
                            JSONObject item = payments.getJSONObject(i);
                            payment.setPaymentId(new BigInteger(item.getString("payment_id")));
                            payment.setAmount(item.getString("amount"));
                            payment.setDescription(item.getString("description"));
                            payment.setAuthority(item.getString("authority"));
                            payment.setPaymentStatus(new Integer(item.getString("payment_status")));
                            payment.setCreditStatus(new Integer(item.getString("credit_status")));
                            payment.setFollowup(item.getString("followup"));
                            payment.setMobileNumber(item.getString("mobile_number"));
                            payment.setService(Integer.valueOf(item.getString("service")));
                            payment.setParams(Integer.valueOf(item.getString("params")));

                            this.items.add(payment);
                        }

//                        this.error = this.isTokenValid() == true ? 0 : ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_PAYMENTS_LIST:
                    {
                        // user successfully logged in
                        // Now store the user in SQLite
//                        this.token = jObj.getString("token");

                        JSONArray charges = jObj.getJSONArray("charges");
                        this.items = new ArrayList<>();
                        for (int i = 0; i < charges.length(); i++) {
//                            payment = new PaymentModel();
//                            JSONObject item = charges.get(i);
//                            payment.setPaymentId(new BigInteger(item.getString("payment_id")));
//                            payment.setAmount(item.getString("amount"));
//                            payment.setDescription(item.getString("description"));
//                            payment.setPayload(item.getString("authority"));
//                            payment.setStatus(new Integer(item.getString("status")));

                            this.items.add(charges.get(i));
                        }

//                        this.error = this.isTokenValid() == true ? 0 : ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_WEATHER_GET:
                    {
                        // user successfully logged in
                        // Now store the user in SQLite
//                        this.token = jObj.getString("token");
                        XMLParser parser = new XMLParser();
                        Document doc = parser.getDomElement(sObj); // getting DOM element
                        this.items = new ArrayList<>();

                        NodeList nl = doc.getElementsByTagName("day");
                        // looping through all item nodes <item>
                        for (int i = 0; i < nl.getLength(); i++) {
                            Element e = (Element) nl.item(i);
                            DayModel day = new DayModel();
                            for (int j = 0; j < e.getChildNodes().getLength(); j++)
                            {
                                if(e.getChildNodes().item(j).getNodeValue() == null)
                                {
                                    //e.getChildNodes().item(j).getNodeValue().equals("\n")
                                    Node nd = e.getChildNodes().item(j);

                                    if(((Element) nd).getTagName().equals("city-name"))
                                        day.setCityName(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("day-name"))
                                        day.setDayName(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("status"))
                                        day.setStatus(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("symbol"))
                                        day.setSymbol(Integer.parseInt(nd.getChildNodes().item(0).getNodeValue()));
                                    if(((Element) nd).getTagName().equals("temp"))
                                        day.setTemp(Integer.parseInt(nd.getChildNodes().item(0).getNodeValue()));
                                    if(((Element) nd).getTagName().equals("max-temp"))
                                        day.setMaxTemp(Integer.parseInt(nd.getChildNodes().item(0).getNodeValue()));
                                    if(((Element) nd).getTagName().equals("min-temp"))
                                        day.setMinTemp(Integer.parseInt(nd.getChildNodes().item(0).getNodeValue()));
                                }
                            }
                            // adding HashList to ArrayList
                            this.items.add(day);
                        }
//                        this.error = this.isTokenValid() == true ? 0 : ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_CURRENCY_GET:
                    {
                        // user successfully logged in
                        // Now store the user in SQLite
//                        this.token = jObj.getString("token");
                        XMLParser parser = new XMLParser();
                        Document doc = parser.getDomElement(sObj); // getting DOM element
//                        this.items = new ArrayList<>();
                        PriceModel price = new PriceModel();
                        ArrayList<CurrencyModel> currencies = new ArrayList<>();

                        NodeList nl = doc.getElementsByTagName("price-service");
                        price.setPriceStatus(((Element) nl.item(0).getChildNodes().item(1)).getChildNodes().item(0).getNodeValue());
                        price.setPriceTime(((Element) nl.item(0).getChildNodes().item(2)).getChildNodes().item(0).getNodeValue());
//                        ((Element) nl.item(0).getChildNodes().item(1)).getTagName()
                        nl = doc.getElementsByTagName("item");
                        // looping through all item nodes <item>
                        for (int i = 0; i < nl.getLength(); i++) {
                            Element e = (Element) nl.item(i);
                            CurrencyModel currency = new CurrencyModel();
                            for (int j = 0; j < e.getChildNodes().getLength(); j++)
                            {
                                if(e.getChildNodes().item(j).getNodeValue() == null)
                                {
                                    //e.getChildNodes().item(j).getNodeValue().equals("\n")
                                    Node nd = e.getChildNodes().item(j);

                                    if(((Element) nd).getTagName().equals("name"))
                                        currency.setCurrencyName(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("price"))
                                        currency.setCurrencyPrice(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("change"))
                                        currency.setCurrencyChange(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("percent"))
                                        currency.setCurrencyPercent(nd.getChildNodes().item(0).getNodeValue());
                                }
                            }
                            currencies.add(currency);
                            // adding HashList to ArrayList
//                            this.items.add(day);
                        }
                        price.setCurrencies(currencies);
                        this.item = price;
//                        this.error = this.isTokenValid() == true ? 0 : ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_COIN_GET:
                    {
                        // user successfully logged in
                        // Now store the user in SQLite
//                        this.token = jObj.getString("token");
                        XMLParser parser = new XMLParser();
                        Document doc = parser.getDomElement(sObj); // getting DOM element
//                        this.items = new ArrayList<>();
                        PriceModel price = new PriceModel();
                        ArrayList<CoinModel> coins = new ArrayList<>();

                        NodeList nl = doc.getElementsByTagName("price-service");
                        price.setPriceStatus(((Element) nl.item(0).getChildNodes().item(1)).getChildNodes().item(0).getNodeValue());
                        price.setPriceTime(((Element) nl.item(0).getChildNodes().item(2)).getChildNodes().item(0).getNodeValue());
//                        ((Element) nl.item(0).getChildNodes().item(1)).getTagName()
                        nl = doc.getElementsByTagName("item");
                        // looping through all item nodes <item>
                        for (int i = 0; i < nl.getLength(); i++) {
                            Element e = (Element) nl.item(i);
                            CoinModel coin = new CoinModel();
                            for (int j = 0; j < e.getChildNodes().getLength(); j++)
                            {
                                if(e.getChildNodes().item(j).getNodeValue() == null)
                                {
                                    //e.getChildNodes().item(j).getNodeValue().equals("\n")
                                    Node nd = e.getChildNodes().item(j);

                                    if(((Element) nd).getTagName().equals("name"))
                                        coin.setCoinName(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("price"))
                                        coin.setCoinPrice(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("change"))
                                        coin.setCoinChange(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("percent"))
                                        coin.setCoinPercent(nd.getChildNodes().item(0).getNodeValue());
                                }
                            }
                            coins.add(coin);
                            // adding HashList to ArrayList
//                            this.items.add(day);
                        }
                        price.setCoins(coins);
                        this.item = price;
//                        this.error = this.isTokenValid() == true ? 0 : ERROR_TOKEN_MISMACH_CODE;

                        break;
                    }
                    case TAG_HOME_GET_VERSION:
                    {
                        this.items = new ArrayList<>();

                        this.items.add( jObj.getString("version"));
                        this.items.add( jObj.getString("link"));

                        if(jObj.getString("message").equals(""))
                        {
                            this.items.add("null");
                        }
                        else
                        {
                            this.items.add( jObj.getString("message"));
                        }
                        this.items.add( jObj.getString("min_version"));

                        this.error = 0;
                        break;
                    }
                    case TAG_RSS_NEWS_GET:
                    {
                        XMLParser parser = new XMLParser();
                        Document doc = parser.getDomElement(sObj); // getting DOM element
                        this.items = new ArrayList<>();

                        NodeList nl = doc.getElementsByTagName("item");
                        // looping through all item nodes <item>
                        for (int i = 1; i < nl.getLength(); i++)
                        {
                            Element e = (Element) nl.item(i);
                            NewsModel news = new NewsModel();
                            for (int j = 0; j < e.getChildNodes().getLength(); j++)
                            {
                                if(e.getChildNodes().item(j).getNodeValue() == null && e.getChildNodes().item(j).getNodeValue() != "\n")
                                {
                                    //e.getChildNodes().item(j).getNodeValue().equals("\n")
                                    Node nd = e.getChildNodes().item(j);

                                    if(((Element) nd).getTagName().equals("title"))
                                        news.setNewsTitle(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("link"))
                                        news.setNewsLink(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("description"))
                                        news.setNewsDescription(nd.getChildNodes().item(0).getNodeValue());
                                    if(((Element) nd).getTagName().equals("pubDate"))
                                        news.setNewsPublicationDate(nd.getChildNodes().item(0).getNodeValue());

                                }
                            }
                            // adding to ArrayList
                            this.items.add(news);
                        }
                        break;
                    }
                    case TAG_FEEDBACK_STORE:
                    {
                        this.error = 0;
                        break;
                    }
                    default:
                        break;
                }

            }
            else
            {
//                if(!this.error.equals(RequestRespondModel.ERROR_OPPONENT_ACCEPTED_CODE) &&
//                        !this.error.equals(RequestRespondModel.ERROR_OPPONENT_CANCELED_CODE))
//                {
                    // Error in login. Get the error message
                    this.error_msg = this.getErrorCodeMessage(new Integer(jObj.getString("error")));

//                    if(this.error == RequestRespondModel.ERROR_TOKEN_INVALID)
//                    {
//                        this.must_logout = true;
//                        SessionModel session = new SessionModel(AppController.getInstance().getApplicationContext());
//                        session.logoutUser();
//                    }

                    LogModel log = new LogModel(cntx);
                    log.setErrorMessage("message: "+ this.error_msg+" CallStack: non, MeysamErrorCode:" + this.getError());
                    log.setContollerName(this.getClass().getName());
//                    if(new SessionModel(cntx).isLoggedIn())
//                        log.setUserId(new SessionModel(this.cntx).getCurrentUser().getId());
                    log.insert();
//                }


            }

        }
        catch (JSONException e)
        {
            // JSON error
//            LogModel log = new LogModel(cntx);
//            log.setErrorMessage("message: "+ e.getMessage()+" CallStack: non");
//            log.setContollerName(this.getClass().getName());
//            log.setUserId(new SessionModel(this.cntx).getCurrentUser().getId());
//            log.insert();


            Utility.generateLogError(e);
        }
    }

    public String getErrorCodeMessage(int errorCode)
    {
        String result = "";
        switch (errorCode)
        {
            case ERROR_ITEM_EXIST_CODE:
                result = cntx.getResources().getString(R.string.error_item_exist);
                break;
            case ERROR_INSERT_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_insert_fail);
                break;
            case ERROR_USER_EXIST_CODE:
                result = cntx.getResources().getString(R.string.error_user_exist);
                break;

            case ERROR_TOKEN_MISMACH_CODE:
                result = cntx.getResources().getString(R.string.error_token_mismach);
                break;
            case ERROR_DEFECTIVE_INFORMATION_CODE:
                result = cntx.getResources().getString(R.string.error_defective_information);
                break;
            case ERROR_TOKEN_BLACKLISTED_CODE:
                result = cntx.getResources().getString(R.string.error_token_blacklisted);
                break;
            case ERROR_INVALID_FILE_SIZE_CODE:
                result = cntx.getResources().getString(R.string.error_invalid_file_size);
                break;
            case ERROR_UPDATE_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_update_fail);
                break;
            case ERROR_DELETE_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_delete_fail);
                break;
            case ERROR_OPERATION_FAIL_CODE:
                result = cntx.getResources().getString(R.string.error_operation_fail);
                break;

            case ERROR_ITEM_NOT_EXIST_CODE:
                result = cntx.getResources().getString(R.string.error_item_not_exist);
                break;
            case ERROR_EMAIL_EXIST_CODE:
                result = cntx.getResources().getString(R.string.error_email_exist);
                break;
            case ERROR_UNAUTHORIZED_ACCESS_CODE:
                result = cntx.getResources().getString(R.string.error_unauthorized_access);
                break;

            case ERROR_INVALID_CODE_OF_INVITE_CODE:
                result = cntx.getResources().getString(R.string.error_invalid_invite_code);
                break;
            case ERROR_WRONG_CHARSET:
                result = cntx.getResources().getString(R.string.error_wrong_char_set);
                break;
            case ERROR_TOKEN_INVALID:
                result = cntx.getResources().getString(R.string.error_token_invalid);
                break;
            case ERROR_CHARGE_SERVICE_DISABLED:
                result = cntx.getResources().getString(R.string.error_charge_service_disabled);
                break;
            case ERROR_GET_WEATHER:
                result = cntx.getResources().getString(R.string.error_weather_get);
                break;
            default:
                result = cntx.getResources().getSystem().getString(R.string.error_undefined);
                break;
        }

        return result;
    }


}
