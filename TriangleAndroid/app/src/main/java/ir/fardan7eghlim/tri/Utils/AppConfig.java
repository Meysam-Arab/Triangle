package ir.fardan7eghlim.tri.Utils;

/**
 * Created by Meysam on 2/16/2017.
 */

public class AppConfig {

    ////////////////////////////////////////////////////////////////
//    private static final String Host_IP = "http://136.243.120.147/~loofi";
//    private static final String Host_IP = "http://87.236.209.155/~tri";
//    private static final String Host_IP = "http://loofi.net";
    private static final String Host_IP = "http://triii.ir";
//    private static final String Host_IP = "http://10.0.2.2:4797";
//    private static final String Host_IP = "http://10.0.2.2:8000";
//    private static final String Host_IP = "http://192.168.1.50:4797/tri/public";
    ////////////////////////////////////////////////////////////////


    // Server get specific link
    private static final String URL_PAYMENTS_INDEX = "/api/payments/apiIndex";
    private static final String URL_PAYMENTS_LIST = "/api/payments/apiList";

    // Server log store
    private static final String URL_LOGS_STORE = "/api/logs/apiStore";

    // Server parsijoo get
    private static final String URL_PARSIJOO_GET = "http://parsijoo.ir/api";

    // Server get version
    private static final String URL_HOME_GET_VERSION = "/api/home/apiGetVersion";

    // Server parseek rss feed get
    private static final String URL_PARSEEK_GET = "http://news.parseek.com/rss/";

    // Server purchase payment
    private static final String URL_PAYMENTS_PURCHASE = "/payments/purchase";

    // Server feedback
    private static final String URL_FEEDBACK_STORE = "/api/feedbacks/apiStore";

    // addresses
    public static final String PaymentsIndex = AppConfig.Host_IP + AppConfig.URL_PAYMENTS_INDEX;
    public static final String PaymentsList = AppConfig.Host_IP + AppConfig.URL_PAYMENTS_LIST;
    public static final String LogsStore = AppConfig.Host_IP + AppConfig.URL_LOGS_STORE;
    public static final String ParsijooGet = AppConfig.URL_PARSIJOO_GET;
    public static final String HomeGetVersion = AppConfig.Host_IP + AppConfig.URL_HOME_GET_VERSION;
    public static final String ParseekGet = AppConfig.URL_PARSEEK_GET;
    public static final String PaymentsPurchase = AppConfig.Host_IP + AppConfig.URL_PAYMENTS_PURCHASE;
    public static final String FeedbackStore = AppConfig.Host_IP + AppConfig.URL_FEEDBACK_STORE;




    public static String getHost()
    {
        return Host_IP;
    }
}
