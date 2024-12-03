<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 4/28/2018
 * Time: 12:20 PM
 */

namespace App;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
use DB;

class Payment extends Model
{
    use SoftDeletes;


    const MESSAGE_PAYMENT_ZARINPAL_DEFECTIVE_INFORMATION = -1;
    const MESSAGE_PAYMENT_ZARINPAL_INCORRECT_IP_OR_MERCHANT_CODE = -2;
    const MESSAGE_PAYMENT_ZARINPAL_SHAPARAK_RESTRICTION_PAYMENT = -3;
    const MESSAGE_PAYMENT_ZARINPAL_BELOW_SILVER_LEVEL = -4;
    const MESSAGE_PAYMENT_ZARINPAL_REQUEST_NOT_FIND = -11;
    const MESSAGE_PAYMENT_ZARINPAL_CAN_NOT_EDIT_REQUEST = -12;
    const MESSAGE_PAYMENT_ZARINPAL_FINANCIAL_OPERATION_NOT_FIND = -21;
    const MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_FAILED = -22;
    const MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_PAYMENT_MISMATCH = -33;
    const MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_COUNT_EXCEEDED = -34;
    const MESSAGE_PAYMENT_ZARINPAL_METHOD_ACCESS_NOT_ALLOWED = -40;
    const MESSAGE_PAYMENT_ZARINPAL_INCORRECT_ADDETIONAL_DATA = -41;
    const MESSAGE_PAYMENT_ZARINPAL_ID_VALIDATION_TIME = -42;
    const MESSAGE_PAYMENT_ZARINPAL_REQUEST_ARCHIVED= -54;
    const MESSAGE_PAYMENT_ZARINPAL_OPERATION_SUCCESSFUL = 100;
    const MESSAGE_PAYMENT_ZARINPAL_OPERATION_SUCCESSFUL_PAYMENT_VERIFICATION_ALREDY_DONE = 101;
    const MESSAGE_UNDEFINED_ERROR= -2000;


    const PAYMENT_STATUS_GHASEDAK_TRANSACTION_OK = 0;
    const PAYMENT_STATUS_GHASEDAK_INVALID_USERNAME_PASSWORD = 1;
    const PAYMENT_STATUS_GHASEDAK_INVALID_AMOUNT = 2;
    const PAYMENT_STATUS_GHASEDAK_INVALID_MOBILE_NUMBER = 3;
    const PAYMENT_STATUS_GHASEDAK_INACTIVE_SERVICE = 4;
    const PAYMENT_STATUS_GHASEDAK_INSUFFICIENT_CREDIT = 5;
    const PAYMENT_STATUS_GHASEDAK_USER_NOT_EXIST_OR_INACTIVE = 6;
    const PAYMENT_STATUS_GHASEDAK_PURCHASE_CODE_INVALID_OR_NOT_EXIST = 7;
    const PAYMENT_STATUS_GHASEDAK_TRANSACTION_NOT_EXIST = 8;
    const PAYMENT_STATUS_GHASEDAK_TRANSACTION_IN_PROCESS = 9;
    const PAYMENT_STATUS_GHASEDAK_TRANSACTION_ERROR = 10;
    const PAYMENT_STATUS_GHASEDAK_REQUEST_ERROR = 11;
    const PAYMENT_STATUS_GHASEDAK_DUPLICATE_PURCHASE_CODE = 12;
    const PAYMENT_STATUS_GHASEDAK_INVALID_TRANSFER_CODE= 13;


    /**
     * The attributes that should be mutated to dates.
     *
     * @var array
     */
    protected $dates = ['deleted_at'];
    protected $table = 'payments';
    protected $primaryKey = 'payment_id';

    public function initialize()
    {
        $this->payment_id=null;
        $this->payment_guid=null;
        $this->mobile_number=null;
        $this->amount=null;
        $this->service=null;
        $this->params=null;
        $this->payment_status=null;
        $this->credit_status=null;
        $this->description=null;
        $this->authority=null;
        $this->followup=null;
        $this->transaction_id=null;
        $this->unique_identifier=null;
        $this->deleted_at=null;


    }
    public function initializeByObject(Payment $payment)
    {
        if( $payment->payment_id != null){
            $this->payment_id = $payment->payment_id;
        }
        if($payment->payment_guid != null){
            $this->payment_guid = $payment->payment_guid;
        }
        if($payment->mobile_number != null){
            $this->mobile_number = $payment->mobile_number;
        }
        if($payment->amount != null){
            $this->amount = $payment->amount;
        }
        if( $payment->service != null){
            $this->service = $payment->service;
        }
        if( $payment->params != null){
            $this->params = $payment->params;
        }
        if( $payment->payment_status != null){
            $this->payment_status = $payment->payment_status;
        }
        if( $payment->credit_status != null){
            $this->credit_status = $payment->credit_status;
        }
        if( $payment->description != null){
            $this->description = $payment->description;
        }
        if( $payment->followup != null){
            $this->followup = $payment->followup;
        }
        if( $payment->authority != null){
            $this->authority = $payment->authority;
        }
        if( $payment->transaction_id != null){
            $this->transaction_id = $payment->transaction_id;
        }
        if( $payment->unique_identifier != null){
            $this->unique_identifier = $payment->unique_identifier;
        }
    }
    public function initializeByRequest($request)
    {

        $this->payment_id=$request->input('payment_id');
        $this->payment_guid=$request->input('payment_guid');
        $this->mobile_number=$request->input('mobile_number');
        $this->amount=$request->input('amount');
        $this->service=$request->input('service');
        $this->params=$request->input('params');
        $this->payment_status=$request->input('payment_status');
        $this->credit_status=$request->input('credit_status');
        $this->description=$request->input('description');
        $this->authority=$request->input('authority');
        $this->followup=$request->input('followup');
        $this->transaction_id=$request->input('transaction_id');
        $this->unique_identifier=$request->input('unique_identifier');

    }
    public function getFullDetailPayment( $params1,$params2,$params3,$distinct=null)
    {

        $query = $this->newQuery();
        //
        if($params1!=null) {

            $query=\App\Utility::fillQueryAlias($query,$params1,$distinct);
        }
        $query =Self::makeWhere($query);

        //
        if($params2!=null) {
            $query=\App\Utility::fillQueryJoin($query,$params2);

        }
        //filtering
        if($params3!=null) {
            $query=\App\Utility::fillQueryFilter($query,$params3);
        }
        $payments = $query->get();
        return $payments;

//        return $query->get();
    }
    public function makeWhere($query){
        if($this->payment_id != null){
            $query->where('payments.'.'payment_id', '=', $this->payment_id);
        }
        if($this->payment_guid != null){
            $query->where('payments.'.'payment_guid', '=', $this->payment_guid);
        }
        if($this->mobile_number != null){
            $query->where('payments.'.'mobile_number', 'like', $this->mobile_number);
        }
        if($this->amount != null){
            $query->where('payments.'.'amount', '=', $this->amount);
        }
        if( $this->service != null){
            $query->where('payments.'.'service', '=', $this->service);
        }
        if( $this->description != null){
            $query->where('payments.'.'description', 'like', '%'.$this->description.'%');
        }
        if( $this->authority != null){
            $query->where('payments.'.'authority', '=', $this->authority);
        }
        if( $this->payment_status != null){
            $query->where('payments.'.'payment_status', '=', $this->payment_status);
        }
        if( $this->credit_status != null){
            $query->where('payments.'.'credit_status', '=', $this->credit_status);
        }
        if( $this->followup != null){
            $query->where('payments.'.'followup', '=', $this->followup);
        }
        if( $this->params != null){
            $query->where('payments.'.'params', '=', $this->params);
        }
        if( $this->transaction_id != null){
            $query->where('payments.'.'transaction_id', '=', $this->transaction_id);
        }
        if( $this->unique_identifier != null){
            $query->where('payments.'.'unique_identifier', 'like', $this->unique_identifier);
        }

        return $query;
    }
    public function select()
    {
        $query = $this->newQuery();
        if($this->payment_id != null){
            $query->where('payments.'.'payment_id', '=', $this->payment_id);
        }
        if($this->payment_guid != null){
            $query->where('payments.'.'payment_guid', '=', $this->payment_guid);
        }
        if($this->mobile_number != null){
            $query->where('payments.'.'mobile_number', 'like', $this->mobile_number);
        }
        if($this->amount != null){
            $query->where('payments.'.'amount', '=', $this->amount);
        }
        if( $this->service != null){
            $query->where('payments.'.'service', '=', $this->service);
        }
        if( $this->description != null){
            $query->where('payments.'.'description', 'like', '%'.$this->description.'%');
        }
        if( $this->authority != null){
            $query->where('payments.'.'authority', '=', $this->authority);
        }
        if( $this->payment_status != null){
            $query->where('payments.'.'payment_status', '=', $this->payment_status);
        }
        if( $this->credit_status != null){
            $query->where('payments.'.'credit_status', '=', $this->credit_status);
        }
        if( $this->followup != null){
            $query->where('payments.'.'followup', '=', $this->followup);
        }
        if( $this->params != null){
            $query->where('payments.'.'params', '=', $this->params);
        }
        if( $this->transaction_id != null){
            $query->where('payments.'.'transaction_id', '=', $this->transaction_id);
        }
        if( $this->unique_identifier != null){
            $query->where('payments.'.'unique_identifier', 'like', $this->unique_identifier);
        }

        $query->where('deleted_at', null);
        $payment = $query->get();

        return $payment;
    }
    public function store()
    {
        $this->payment_guid = uniqid('',true);
        $this->save();
    }
    public function find($id)
    {
        return $this->find($id);
    }
    public function findByIdAndGuid($id, $guid)
    {
        try
        {
            $query = $this->newQuery();
            $query->where('payment_id', '=', $id);
            $query->where('payment_guid', 'like', $guid);
            $payments = $query->get();
            if(count($payments)==0)
                return false;
            return $payments[0];
        }
        catch (\Exception $ex)
        {
            throw $ex;
        }
    }
    public function set($id,$guid)
    {
        $this->payment_id = $id;
        $this->payment_guid = $guid;
    }
//    public static function edit(Payment $payment)
//    {
//        DB::table('payments')
//            ->where('authority', $payment->authority)
//            ->where('deleted_at',null)
//            ->update(['payments.status' => $payment->status,
//                'payments.followup' => $payment->followup]);
//    }
    public static function updatePayment(Payment $payment)
    {
        $oldPayment = new Payment();
        $oldPayment = $oldPayment->findByIdAndGuid($payment -> payment_id,$payment-> payment_guid);

        if($payment->payment_id != null){
            $oldPayment->payment_id = $payment->payment_id;
        }
        if($payment->payment_guid != null){
            $oldPayment->payment_guid = $payment->payment_guid;
        }
        if($payment->mobile_number != null){
            $oldPayment->mobile_number = $payment->mobile_number;
        }
        if($payment->amount != null){
            $oldPayment->amount = $payment->amount;
        }
        if($payment->service != null){
            $oldPayment->service = $payment->service;
        }
        if($payment->description != null){
            $oldPayment->description = $payment->description;
        }
        if($payment->authority != null){
            $oldPayment->authority = $payment->authority;
        }
        if($payment->payment_status != null){
            $oldPayment->payment_status = $payment->payment_status;
        }
        if($payment->credit_status != null){
            $oldPayment->credit_status = $payment->credit_status;
        }
        if($payment->followup != null){
            $oldPayment->followup = $payment->followup;
        }
        if($payment->params != null){
            $oldPayment->params = $payment->params;
        }
        if($payment->transaction_id != null){
            $oldPayment->transaction_id = $payment->transaction_id;
        }
        if($payment->unique_identifier != null){
            $oldPayment->unique_identifier = $payment->unique_identifier;
        }

        $oldPayment->save();
    }
    public static function getMessage($code = null)
    {
        switch ($code)
        {
            case self::MESSAGE_PAYMENT_ZARINPAL_BELOW_SILVER_LEVEL:
                return  trans('messages.msg_PaymentZarinPalBelowSilverLevel');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_DEFECTIVE_INFORMATION:
                return  trans('messages.msg_PaymentZarinPalDefectiveInformation');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_CAN_NOT_EDIT_REQUEST:
                return  trans('messages.msg_PaymentZarinPalCanNotEditRequest');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_FINANCIAL_OPERATION_NOT_FIND:
                return  trans('messages.msg_PaymentZarinPalFinancialOperationNotFind');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_ID_VALIDATION_TIME:
                return  trans('messages.msg_PaymentZarinPalIdValidationTime');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_INCORRECT_ADDETIONAL_DATA:
                return  trans('messages.msg_PaymentZarinPalIncorrectAdditionalData');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_INCORRECT_IP_OR_MERCHANT_CODE:
                return trans('messages.msg_PaymentZarinPalIncorrectIpOrMerchantCode');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_METHOD_ACCESS_NOT_ALLOWED:
                return  trans('messages.msg_PaymentZarinPalMethodAccessNotAllowed');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_OPERATION_SUCCESSFUL:
                return  trans('messages.msg_PaymentZarinPalOperationSuccessful');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_OPERATION_SUCCESSFUL_PAYMENT_VERIFICATION_ALREDY_DONE:
                return trans('messages.msg_PaymentZarinPalOperationSuccessfulPaymentVerificationAlredyDone');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_REQUEST_ARCHIVED:
                return  trans('messages.msg_PaymentZarinPalRequestArchived');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_REQUEST_NOT_FIND:
                return  trans('messages.msg_PaymentZarinPalRequestNotFind');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_SHAPARAK_RESTRICTION_PAYMENT:
                return trans('messages.msg_PaymentZarinPalShaparakRestrictionPayment');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_COUNT_EXCEEDED:
                return  trans('messages.msg_PaymentZarinPalTransactionCountExceeded');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_FAILED:
                return  trans('messages.msg_PaymentZarinPalTransactionFailed');
                break;
            case self::MESSAGE_PAYMENT_ZARINPAL_TRANSACTION_PAYMENT_MISMATCH:
                return  trans('messages.msg_PaymentZarinPalTransactionPaymentMismatch');
                break;
            case self::MESSAGE_UNDEFINED_ERROR:
                return  trans('messages.msg_ErrorUndefined');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_TRANSACTION_OK:
                return  trans('messages.msg_PaymentStatusGhasedakTransactionOk');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_INVALID_USERNAME_PASSWORD:
                return  trans('messages.msg_PaymentStatusGhasedakInvalidUserNameOrPassword');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_INVALID_AMOUNT:
                return  trans('messages.msg_PaymentStatusGhasedakInvalidAmount');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_INVALID_MOBILE_NUMBER:
                return  trans('messages.msg_PaymentStatusGhasedakInvalidMobileNumber');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_INACTIVE_SERVICE:
                return  trans('messages.msg_PaymentStatusGhasedakInactiveService');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_INSUFFICIENT_CREDIT:
                return  trans('messages.msg_PaymentStatusGhasedakInsufficientCredit');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_USER_NOT_EXIST_OR_INACTIVE:
                return  trans('messages.msg_PaymentStatusGhasedakUserNotExistOrInactive');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_PURCHASE_CODE_INVALID_OR_NOT_EXIST:
                return  trans('messages.msg_PaymentStatusGhasedakPurchaseCodeInvalidOrNotExist');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_TRANSACTION_NOT_EXIST:
                return  trans('messages.msg_PaymentStatusGhasedakTransactionNotExist');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_TRANSACTION_IN_PROCESS:
                return  trans('messages.msg_PaymentStatusGhasedakTransactionInProcess');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_TRANSACTION_ERROR:
                return  trans('messages.msg_PaymentStatusGhasedakTransactionError');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_REQUEST_ERROR:
                return  trans('messages.msg_PaymentStatusGhasedakRequestError');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_DUPLICATE_PURCHASE_CODE:
                return  trans('messages.msg_PaymentStatusGhasedakDuplicatePurchaseCode');
                break;
            case self::PAYMENT_STATUS_GHASEDAK_INVALID_TRANSFER_CODE:
                return  trans('messages.msg_PaymentStatusGhasedakInvalidTransferCode');
                break;
            default:
                return  trans('messages.msg_ErrorItemNotExist');
                break;
        }
    }
    public static function isTokenExist($token)
    {
        $payments = DB::table('payments')
            ->where('authority', $token)
            ->get();
        if(sizeof($payments) > 0 )
            return true;
        return false;
    }
}