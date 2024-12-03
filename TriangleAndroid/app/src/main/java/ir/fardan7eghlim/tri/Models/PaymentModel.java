package ir.fardan7eghlim.tri.Models;

import android.content.Context;

import java.math.BigInteger;

import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.R;

/**
 * Created by Meysam on 3/7/2017.
 */

public class PaymentModel {

    public static String OPERATOR_IRANCELL = "irancell";
    public static String OPERATOR_HAMRAH_AVAL = "hamrahaval";
    public static String OPERATOR_RIGHTEL = "rightel";


    public static final int MESSAGE_PAYMENT_ZARINPAL_DEFECTIVE_INFORMATION = -1;
    public static final int MESSAGE_PAYMENT_ZARINPAL_INCORRECT_IP_OR_MERCHANT_CODE = -2;
    public static final int MESSAGE_PAYMENT_ZARINPAL_SHAPARAK_RESTRICTION_PAYMENT = -3;
    public static final int MESSAGE_PAYMENT_ZARINPAL_BELOW_SILVER_LEVEL = -4;
    public static final int MESSAGE_PAYMENT_ZARINPAL_REQUEST_NOT_FIND = -11;
    public static final int MESSAGE_PAYMENT_ZARINPAL_CAN_NOT_EDIT_REQUEST = -12;
    public static final int MESSAGE_PAYMENT_ZARINPAL_FINANCIAL_OPERATION_NOT_FIND = -21;
    public static final int MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_FAILED = -22;
    public static final int MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_PAYMENT_MISMATCH = -33;
    public static final int MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_COUNT_EXCEEDED = -34;
    public static final int MESSAGE_PAYMENT_ZARINPAL_METHOD_ACCESS_NOT_ALLOWED = -40;
    public static final int MESSAGE_PAYMENT_ZARINPAL_INCORRECT_ADDETIONAL_DATA = -41;
    public static final int MESSAGE_PAYMENT_ZARINPAL_ID_VALIDATION_TIME = -42;
    public static final int MESSAGE_PAYMENT_ZARINPAL_REQUEST_ARCHIVED= -54;
    public static final int MESSAGE_PAYMENT_ZARINPAL_OPERATION_SUCCESSFUL = 100;
    public static final int MESSAGE_PAYMENT_ZARINPAL_OPERATION_SUCCESSFUL_PAYMENT_VERIFICATION_ALREDY_DONE = 101;


    public static final int PAYMENT_STATUS_GHASEDAK_TRANSACTION_OK = 0;
    public static final int PAYMENT_STATUS_GHASEDAK_INVALID_USERNAME_PASSWORD = 1;
    public static final int PAYMENT_STATUS_GHASEDAK_INVALID_AMOUNT = 2;
    public static final int PAYMENT_STATUS_GHASEDAK_INVALID_MOBILE_NUMBER = 3;
    public static final int PAYMENT_STATUS_GHASEDAK_INACTIVE_SERVICE = 4;
    public static final int PAYMENT_STATUS_GHASEDAK_INSUFFICIENT_CREDIT = 5;
    public static final int PAYMENT_STATUS_GHASEDAK_USER_NOT_EXIST_OR_INACTIVE = 6;
    public static final int PAYMENT_STATUS_GHASEDAK_PURCHASE_CODE_INVALID_OR_NOT_EXIST = 7;
    public static final int PAYMENT_STATUS_GHASEDAK_TRANSACTION_NOT_EXIST = 8;
    public static final int PAYMENT_STATUS_GHASEDAK_TRANSACTION_IN_PROCESS = 9;
    public static final int PAYMENT_STATUS_GHASEDAK_TRANSACTION_ERROR = 10;
    public static final int PAYMENT_STATUS_GHASEDAK_REQUEST_ERROR = 11;
    public static final int PAYMENT_STATUS_GHASEDAK_DUPLICATE_PURCHASE_CODE = 12;
    public static final int PAYMENT_STATUS_GHASEDAK_INVALID_TRANSFER_CODE= 13;


    public BigInteger getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(BigInteger paymentId) {
        PaymentId = paymentId;
    }

    public String getPaymentGuid() {
        return PaymentGuid;
    }

    public void setPaymentGuid(String paymentGuid) {
        PaymentGuid = paymentGuid;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public Integer getService() {
        return Service;
    }

    public void setService(Integer service) {
        Service = service;
    }

    public String getFollowup() {
        return Followup;
    }

    public void setFollowup(String followup) {
        Followup = followup;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public Integer getParams() {
        return Params;
    }

    public void setParams(Integer params) {
        Params = params;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuthority() {
        return Authority;
    }

    public void setAuthority(String authority) {
        Authority = authority;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public DatabaseHandler getDb() {
        return db;
    }

    public void setDb(DatabaseHandler db) {
        this.db = db;
    }

    public Context getCntx() {
        return cntx;
    }

    public void setCntx(Context cntx) {
        this.cntx = cntx;
    }

    public Integer getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public Integer getCreditStatus() {
        return CreditStatus;
    }

    public void setCreditStatus(Integer creditStatus) {
        CreditStatus = creditStatus;
    }

    private BigInteger PaymentId;
    private String PaymentGuid;
    private String  Amount;
    private Integer  Service;
    private String Followup;
    private String MobileNumber;
    private Integer PaymentStatus;
    private Integer CreditStatus;
    private Integer Params;
    private String Description;
    private String Authority;
    private String CreatedAt;
    private String UpdatedAt;



    // SQLite database handler
    private DatabaseHandler db;

    private Context cntx;


    public PaymentModel()
    {
        this.PaymentId = null;
        this.PaymentGuid = null;
        this.Amount = null;
        this.Authority = null;
        this.CreatedAt = null;
        this.UpdatedAt = null;
        this.PaymentStatus = null;
        this.CreditStatus = null;
        this.Description = null;
        this.Followup = null;
        this.Service = null;
        this.Params = null;
        this.MobileNumber = null;

        this.db = null;
        this.cntx = null;


    }

    public PaymentModel(Context cntx)
    {
        this.PaymentId = null;
        this.PaymentGuid = null;
        this.Amount = null;
        this.Authority = null;
        this.CreatedAt = null;
        this.UpdatedAt = null;
        this.PaymentStatus = null;
        this.CreditStatus = null;
        this.Description = null;
        this.Followup = null;
        this.Service = null;
        this.Params = null;
        this.MobileNumber = null;

        this.db = null;

        this.cntx = cntx;

    }

    public String getMessage(int code)
    {
        String result = "";

        switch (code)
        {
            case MESSAGE_PAYMENT_ZARINPAL_BELOW_SILVER_LEVEL:
                result = cntx.getString(R.string.msg_PaymentZarinPalBelowSilverLevel);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_DEFECTIVE_INFORMATION:
                result = cntx.getString(R.string.msg_PaymentZarinPalDefectiveInformation);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_CAN_NOT_EDIT_REQUEST:
                result = cntx.getString(R.string.msg_PaymentZarinPalCanNotEditRequest);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_FINANCIAL_OPERATION_NOT_FIND:
                result = cntx.getString(R.string.msg_PaymentZarinPalFinancialOperationNotFind);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_ID_VALIDATION_TIME:
                result = cntx.getString(R.string.msg_PaymentZarinPalIdValidationTime);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_INCORRECT_ADDETIONAL_DATA:
                result = cntx.getString(R.string.msg_PaymentZarinPalIncorrectAdditionalData);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_INCORRECT_IP_OR_MERCHANT_CODE:
                result = cntx.getString(R.string.msg_PaymentZarinPalIncorrectIpOrMerchantCode);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_METHOD_ACCESS_NOT_ALLOWED:
                result = cntx.getString(R.string.msg_PaymentZarinPalMethodAccessNotAllowed);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_OPERATION_SUCCESSFUL:
                result = cntx.getString(R.string.msg_PaymentZarinPalOperationSuccessful);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_OPERATION_SUCCESSFUL_PAYMENT_VERIFICATION_ALREDY_DONE:
                result = cntx.getString(R.string.msg_PaymentZarinPalOperationSuccessfulPaymentVerificationAlredyDone);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_REQUEST_ARCHIVED:
                result = cntx.getString(R.string.msg_PaymentZarinPalRequestArchived);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_REQUEST_NOT_FIND:
                result = cntx.getString(R.string.msg_PaymentZarinPalRequestNotFind);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_SHAPARAK_RESTRICTION_PAYMENT:
                result = cntx.getString(R.string.msg_PaymentZarinPalShaparakRestrictionPayment);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_COUNT_EXCEEDED:
                result = cntx.getString(R.string.msg_PaymentZarinPalTransactionCountExceeded);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_FAILED:
                result = cntx.getString(R.string.msg_PaymentZarinPalTransactionFailed);
                break;
            case MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_PAYMENT_MISMATCH:
                result = cntx.getString(R.string.msg_PaymentZarinPalTransactionPaymentMismatch);
                break;
            case PAYMENT_STATUS_GHASEDAK_TRANSACTION_OK:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakTransactionOk);
                break;
            case PAYMENT_STATUS_GHASEDAK_INVALID_USERNAME_PASSWORD:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakInvalidUserNameOrPassword);
                break;
            case PAYMENT_STATUS_GHASEDAK_INVALID_AMOUNT:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakInvalidAmount);
                break;
            case PAYMENT_STATUS_GHASEDAK_INVALID_MOBILE_NUMBER:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakInvalidMobileNumber);
                break;
            case PAYMENT_STATUS_GHASEDAK_INACTIVE_SERVICE:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakInactiveService);
                break;
            case PAYMENT_STATUS_GHASEDAK_INSUFFICIENT_CREDIT:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakInsufficientCredit);
                break;
            case PAYMENT_STATUS_GHASEDAK_USER_NOT_EXIST_OR_INACTIVE:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakUserNotExistOrInactive);
                break;
            case PAYMENT_STATUS_GHASEDAK_PURCHASE_CODE_INVALID_OR_NOT_EXIST:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakPurchaseCodeInvalidOrNotExist);
                break;
            case PAYMENT_STATUS_GHASEDAK_TRANSACTION_NOT_EXIST:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakTransactionNotExist);
                break;
            case PAYMENT_STATUS_GHASEDAK_TRANSACTION_IN_PROCESS:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakTransactionInProcess);
                break;
            case PAYMENT_STATUS_GHASEDAK_TRANSACTION_ERROR:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakTransactionError);
                break;
            case PAYMENT_STATUS_GHASEDAK_REQUEST_ERROR:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakRequestError);
                break;
            case PAYMENT_STATUS_GHASEDAK_DUPLICATE_PURCHASE_CODE:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakDuplicatePurchaseCode);
                break;
            case PAYMENT_STATUS_GHASEDAK_INVALID_TRANSFER_CODE:
                result = cntx.getString(R.string.msg_PaymentStatusGhasedakInvalidTransferCode);
                break;
            default:
                result = cntx.getString(R.string.msg_MessageNotSpecified);
                break;
        }

        return result;
    }
}
