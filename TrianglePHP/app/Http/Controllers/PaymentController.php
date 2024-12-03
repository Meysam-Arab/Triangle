<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 4/19/2017
 * Time: 6:17 PM
 */

namespace App\Http\Controllers;

use App\Payment;
use App\Utility;
use Redirect;
use Illuminate\Http\RedirectResponse;
use DB;
use Auth;
use App\OperationMessage;
use Validator;
use Exception;
use Route;
use Log;
use SoapClient;
use App\LogEvent;


class PaymentController extends Controller
{
    protected $payment;

    /**
     * PaymentController constructor.
     * @param Payment $payment
     */
    public function __construct(Payment $payment)
    {
        $this->payment = $payment;
    }

    public function purchase($unique_identifier, $mobile_number, $amount, $service)
    {

//        Log::info('in purchase'.json_encode($mobile_number).json_encode($amount).json_encode($service));
        // meysam - send to zarinpal to receive payment...
        try {

            //، meysam - get Authority from zarinpal site...
            $MerchantID = "f1ae5fea-5495-11e8-b304-005056a205be";//get from zarinpal ...
            $Amount = $amount;
            $Description = "خرید شارژ در اپ مثلث";
            $Email = "";
            $Mobile = "";
            $CallbackURL = "http://www.triii.ir/payments/verification";

            $data = array('MerchantID' => $MerchantID,
                'Amount' => $Amount,
                'Description' => $Description,
                'Email' => $Email,
                'Mobile' => $Mobile,
                'CallbackURL' => $CallbackURL);
            $jsonData = json_encode($data);
            $ch = curl_init('https://www.zarinpal.com/pg/rest/WebGate/PaymentRequest.json');
            curl_setopt($ch, CURLOPT_USERAGENT, 'ZarinPal Rest Api v1');
            curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
            curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonData);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_HTTPHEADER, array(
                'Content-Type: application/json',
                'Content-Length: ' . strlen($jsonData)
            ));
            $result = curl_exec($ch);

            $err = curl_error($ch);
            $result = json_decode($result, true);
            curl_close($ch);

            if ($err) {
                echo "cURL Error #:" . $err;
            } else {
                if ($result["Status"] == 100) {
                    /////store Authority in session////
                    // Via the global helper...
                    if(strlen($result["Authority"]) == 36)
                    {
                        ////store in database and retrieve in verification...
                        $payment = new Payment();
                        $payment->mobile_number = $mobile_number;
                        $payment->amount = $amount;
                        $payment->service = $service;
                        $payment->params = 0;
                        $payment->payment_status = $result["Status"];
                        $payment->credit_status = null;
                        $payment->description = $Description;
                        $payment->authority = $result["Authority"];
                        $payment->followup = -1;
                        $payment->transaction_id = '0';
                        $payment->unique_identifier = $unique_identifier;

                        $this->payment->initializeByObject($payment);
                        $this->payment->store();
                        /// ////////////////////////////////
                        return  new RedirectResponse('https://www.zarinpal.com/pg/StartPay/' . $result["Authority"]);
                    }
                    else
                    {
                        //return error
                        ////store in database and retrieve in verification...
                        $payment = new Payment();

                        $payment->mobile_number = $mobile_number;
                        $payment->amount = $amount;
                        $payment->service = $service;
                        $payment->params = 0;
                        $payment->payment_status = $result["Status"];
                        $payment->credit_status = null;
                        $payment->description = $Description;
                        $payment->authority = -1;
                        $payment->followup = -1;
                        $payment->transaction_id = '0';
                        $payment->unique_identifier = $unique_identifier;

                        $this->payment->initializeByObject($payment);
                        $this->payment->store();
                        ////// /////////////////////////
                        $message = new OperationMessage();
                        $message->Code = $result["Status"];
                        $message->Text = $payment->getMessage($result["Status"]);
                        return view('payment.result', ['payment' =>$payment,'message' =>$message]);
                    }
                } else {
                    ////store in database and retrieve in verification...
                    $payment = new Payment();

                    $payment->mobile_number = $mobile_number;
                    $payment->amount = $amount;
                    $payment->service = $service;
                    $payment->params = 0;
                    $payment->payment_status = $result["Status"];
                    $payment->credit_status = null;
                    $payment->description = $Description;
                    $payment->authority = -1;
                    $payment->followup = -1;
                    $payment->transaction_id = '0';
                    $payment->unique_identifier = $unique_identifier;

                    $this->payment->initializeByObject($payment);
                    $this->payment->store();
                    /// ////////////////////////////////
                    $message = new OperationMessage();
                    $message->Code = $result["Status"];
                    $message->Text =  $payment->getMessage($result["Status"]);
                    return view('payment.result', ['payment' =>$payment,'message' =>$message]);
                }
            }

        } catch (Exception $e) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
            $logEvent->store();
            $message = new OperationMessage();
            $message->initializeByCode(OperationMessage::OperationErrorCode);
            return view('payment.result', ['message' =>$message]);
        }
    }

    public function verification()
    {

//        //meysam - text view message...
//
//        $payment = new Payment();
//
//        $payment->mobile_number = '09171887437';
//        $payment->amount = 100;
//        $payment->service = 1;
//        $payment->params = 0;
//        $payment->payment_status = 100;
//        $payment->credit_status = null;
//        $payment->description = "تست";
//        $payment->authority = -1;
//        $payment->followup = -1;
//        $payment->transaction_id = '0';
//
//
//        $message = new OperationMessage();
//        $message->Text =  "خطایی در عملیات رخ داد"." - "." کارشناسان ما مشکل بوجود امده را بررسی می نمایند و در صورتی که شارژ برای شما ارسال نشده ظرف 24 ساعت آینده ارسال خواهد شد ";
//        $message->Code = -1;
//        return view('payment.result', ['payment' =>$payment, 'message'=> $message]);
        //////////////////////////////////////////


        try {

            $Status = $_GET['Status'];
            $Authority = $_GET['Authority'];

            /// /////////////////////////////////////////////////////
            //get stored payment...
            $temp_payment = new Payment();
            $temp_payment->authority = $Authority;
//            $payment = DB::table('payments')
//                ->where('authority', '=', $Authority)
//                ->where('deleted_at',null)
//                ->get()->first();
            $payment = $temp_payment->select()->first();
            ///////////////////////////
            $data = array('MerchantID' => 'f1ae5fea-5495-11e8-b304-005056a205be', 'Authority' => $Authority, 'Amount' => $payment->amount);
            $jsonData = json_encode($data);
            $ch = curl_init('https://www.zarinpal.com/pg/rest/WebGate/PaymentVerification.json');
            curl_setopt($ch, CURLOPT_USERAGENT, 'ZarinPal Rest Api v1');
            curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'POST');
            curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonData);
            curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
            curl_setopt($ch, CURLOPT_HTTPHEADER, array(
                'Content-Type: application/json',
                'Content-Length: ' . strlen($jsonData)
            ));

            $result = curl_exec($ch);
            $err = curl_error($ch);
            curl_close($ch);
            $result = json_decode($result, true);

            $payment->authority = $Authority;
            $payment->payment_status = $result["Status"];

            if ($err) {
                $message = new OperationMessage();
                $message->Text = $err;
                return view('payment.result', ['payment' =>$payment, 'message'=> $message]);

            } else {
                if ($result['Status'] == 100) {
                    $payment->payment_status = $result['Status'];
                    $payment->followup = $result['RefID'];
                } else {
                    $payment->credit_status = Payment::PAYMENT_STATUS_GHASEDAK_TRANSACTION_ERROR;
                    if($Status == "NOK")
                    {
                        //transaction failed.... insert to payment and return to payment page
                        $payment->payment_status = -22;
                        $payment->followup = "0";
                    }
                    else
                    {
                        $payment->payment_status = $result['Status'];
                        $payment->followup = $result['RefID'];
                    }
                }
            }
            ////////////////////////////////////////////////////////////


            //edit new payment here...
            Payment::updatePayment($payment);
            ////////////////////////////////////////// // meysam - send charge request to ghasedak
            if ($result['Status'] == 100)
            {
                try {
                    $opts = array(
                        'http' => array(
                            'user_agent' => 'PHPSoapClient'
                        )
                    );
                    $context = stream_context_create($opts);

                    $wsdlUrl = 'http://ws.elkapos.com/?WSDL';
                    $soapClientOptions = array(
                        'stream_context' => $context,
                        'cache_wsdl' => WSDL_CACHE_NONE
                    );

                    $price = $payment->amount;
                    $mobile = $payment->mobile_number;
                    $service = $payment->service;
                    $param = $payment->params;

                    $client = new SoapClient($wsdlUrl, $soapClientOptions);
                    $checkVatParameters = array(
                        'Username' => 'pos135632',
                        'Password' => 'ghj@453',
                        'MobileNumber'=> $mobile,
                        'Amount'   =>  $price ,
                        'Service' => $service,
                        'Params' => $param,
                        'UserOrderID' => $payment->payment_guid
                    );
                    $result = $client->Recharge($checkVatParameters);
                    $res = json_decode($result->RechargeResult);
                    $payment->transaction_id = $res->TransactionID;
                    $payment->credit_status = $res->ResultCode;
                    $payment->store();

//                    Log::info('result:'.json_encode($res));

                    if($res->ResultCode==0){
                        $payment->store();
                        $message = new OperationMessage();
                        $message->initializeByCode(OperationMessage::OperationSuccessCode);
                        return view('payment.result', ['payment' =>$payment, 'message'=> $message]);
                    }else
                    {
                        // meysam - send email to myself to check the mess...
                        // meysam - show message that we will fix the problem as soon as possible...
                        try
                        {
                            Utility::sendMail("fardan7eghlim@gmail.com","مشکل در پرداخت کاربر","خطایی در پرداخت کاربر با آی دی تراکنش ".$res->TransactionID." پیش آمده ");
                        }
                        catch(Exception $e)
                        {
                            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
                            $logEvent->store();
                        }
                        $message = new OperationMessage();
                        $message->Text = $res->Note . " - ". " کارشناسان ما مشکل بوجود امده را بررسی می نمایند و در صورتی که شارژ برای شما ارسال نشده ظرف 24 ساعت آینده ارسال خواهد شد ";
                        $message->Code = -1;
                        return view('payment.result', ['message'=> $message]);

                    }
                }
                catch(Exception $e)
                {
                    $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
                    $logEvent->store();
                    $message = new OperationMessage();
                    $message->initializeByCode(OperationMessage::OperationErrorCode);
                    return view('payment.result', [ 'message'=> $message]);
                }
            }
            else
            {
                $message = new OperationMessage();
                $message->Code = $result["Status"];
                $message->Text =  $payment->getMessage($result["Status"]);
                return view('payment.result', ['payment' =>$payment,'message' =>$message]);
            }

        }
        catch (Exception $e)
        {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
            $logEvent->store();
            $message = new OperationMessage();
            $message->initializeByCode(OperationMessage::OperationErrorCode);
            return view('payment.result', [ 'message'=> $message]);
        }
    }
}
