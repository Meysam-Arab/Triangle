<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 4/19/2017
 * Time: 6:17 PM
 */

namespace App\Http\Controllers;

use App\User;
use SoapClient;
use App\Payment;
use App\LogEvent;
use Illuminate\Http\Request;
use App\RequestResponseAPI;
use JWTAuth;
use Log;
use Route;


class API_PaymentController extends Controller
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


    public function apiIndex(Request $request)
    {
        ///////////////////check token validation/////////////
//        $token = null;
//        if (session('tokenRefreshed'))
//            $token = session('token');
//        else
//            $token = JWTAuth::parseToken()->getToken()->get();
//        $user = JWTAuth::parseToken()->authenticate($token);
        ////////////////////////////////////////////////////////
        ///  //validation
        if (!$request->has('unique_identifier'))
        {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_PAYMENTS_INDEX]);

        }
        ///////////////////////
        ///
//        $userRep = new User(new User());
//        if(!$userRep->exist($request->input('user_id'),$request->input('user_guid')))
//        {
//            return json_encode(['error' => RequestResponseAPI::ERROR_UNAUTHURIZED_USER_CODE, 'tag' => RequestResponseAPI::TAG_PAYMENTS_INDEX]);
//
//        }
        try {

//        select track data with
            $paramsObj1 = array(
                array("se", "payments", "payment_id"),
                array("se", "payments", "amount"),
                array("se", "payments", "description"),
                array("se", "payments", "authority"),
                array("se", "payments", "payment_status"),
                array("se", "payments", "credit_status"),
                array("se", "payments", "followup"),
                array("se", "payments", "mobile_number"),
                array("se", "payments", "service"),
                array("se", "payments", "params")

            );

            $paramsObj3 = array(
                array("whereRaw",
                    "payments.unique_identifier like '".$request->input('unique_identifier')."'"
                )

            );
            /////add deleted at condition to query/////////

            $paramsObj3[] =   array("whereRaw",
                "payments.deleted_at is null"
            );
            $paramsObj3[] = array("orderBy",
                "payments.payment_id", "DESC"
            );
            /// ///////////////////////////////////////

            $this->payment->initialize();

            $payments = $this->payment->getFullDetailPayment($paramsObj1, null, $paramsObj3);

//            unset($payments['payment_guid']);
//            unset($payments['created_at']);
//            unset($payments['updated_at']);
//            unset($payments['deleted_at']);

            return json_encode([ 'payments' => $payments, 'error' => 0, 'tag' => RequestResponseAPI::TAG_PAYMENTS_INDEX]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_PAYMENTS_INDEX]);
        }

    }

    public function apiList(Request $request)
    {
        ///////////////////check token validation/////////////
//        $token = null;
//        if (session('tokenRefreshed'))
//            $token = session('token');
//        else
//            $token = JWTAuth::parseToken()->getToken()->get();
//        $user = JWTAuth::parseToken()->authenticate($token);
        ////////////////////////////////////////////////////////
        ///  //validation
        if (!$request->has('operator'))
        {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_PAYMENTS_LIST]);

        }
        ///////////////////////

        try {

            // meysam - check status of operator from server
            /////////////////////////////////////////////////////
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

                $client = new SoapClient($wsdlUrl, $soapClientOptions);
                date_default_timezone_set("Asia/Tehran");

//                Log::info('sending request...');

                $result = $client->GetStatus();

//                Log::info('result service name:'.json_encode($result));
                $irancellEnable = false;
                $hamrahAvalEnable = false;
                $rightelEnable = false;
//                    Log::info('result service status:'.utf8_encode($result->GetStatusResult));
                $res = json_decode($result->GetStatusResult);
                if(count($res) > 0)
                {
                    for ($x = 0; $x < count($res); $x++) {
                        if(strcmp($res[$x]->ServiceName,'ایرانسل') == 0 )
                        {
                            //برای ایرانسل
                            if($res[$x]->ServiceStatus == 1)
                                $irancellEnable = true;
                        }
                        if(strcmp($res[$x]->ServiceName,'همراه اول') == 0 )
                        {
                            //برای همراه اول
                            if($res[$x]->ServiceStatus == 1)
                                $hamrahAvalEnable = true;
                        }
                        if(strcmp($res[$x]->ServiceName,'رایتل') == 0 )
                        {
                            //برای رایتل
                            if($res[$x]->ServiceStatus == 1)
                                $rightelEnable = true;
                        }
                    }
                }
                else
                {
                    return json_encode(['error' => RequestResponseAPI::ERROR_CHARGE_SERVICE_DISABLED, 'tag' => RequestResponseAPI::TAG_PAYMENTS_LIST]);
                }

//                Log::info('result:'.var_dump($res));

            }
            catch(Exception $e) {
                echo $e->getMessage();
            }
            ////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////

            $availableCharges = [];
            if(strcmp($request->input('operator'),'irancell') == 0)
            {
                if(!$irancellEnable)
                    return json_encode(['error' => RequestResponseAPI::ERROR_CHARGE_SERVICE_DISABLED, 'tag' => RequestResponseAPI::TAG_PAYMENTS_LIST]);

                $availableCharges = ['مبلغ دلخواه خود را وارد نمایید',
                    '1000',
                    '2000',
                    '5000',
                    '10000'];
            }
            else if(strcmp($request->input('operator'),'hamrahaval') == 0)
            {
                if(!$irancellEnable)
                    return json_encode(['error' => RequestResponseAPI::ERROR_CHARGE_SERVICE_DISABLED, 'tag' => RequestResponseAPI::TAG_PAYMENTS_LIST]);

                $availableCharges = ['1000',
                    '2000',
                    '5000',
                    '10000',
                    '20000'];
            }
            else if(strcmp($request->input('operator'),'rightel') == 0)
            {
                if(!$irancellEnable)
                    return json_encode(['error' => RequestResponseAPI::ERROR_CHARGE_SERVICE_DISABLED, 'tag' => RequestResponseAPI::TAG_PAYMENTS_LIST]);

                $availableCharges = ['2000',
                    '5000',
                    '10000',
                    '20000',
                    '50000'];
            }
            else
                return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_PAYMENTS_LIST]);


            return json_encode([ 'charges' => $availableCharges, 'error' => 0, 'tag' => RequestResponseAPI::TAG_PAYMENTS_LIST]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_PAYMENTS_LIST]);
        }

    }
}
