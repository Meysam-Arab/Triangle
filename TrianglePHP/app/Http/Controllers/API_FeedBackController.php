<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/20/2017
 * Time: 4:07 PM
 */


namespace App\Http\Controllers;

use App\FeedBack;
use App\LogEvent;
use App\User;
use App\RequestResponseAPI;
use App\Utility;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use Validator;
//use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
class API_FeedBackController extends Controller
{
    public function apiStore(Request $request)
    {
		try {
        //validation
        if (!$request->has('tag') || !$request->has('description') ) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_FEEDBACK_STORE]);

        }

        $rules = [
            'title' => 'max:200',
            'description' => 'required',
            'email' => 'email',
            'phone' => 'numeric|digits_between:10,11'
        ];
        $v = Validator::make($request->all(), $rules);

        if ($v->fails()) {
            return json_encode(['error' => RequestResponseAPI::ERROR_DEFECTIVE_INFORMATION_CODE, 'tag' => RequestResponseAPI::TAG_FEEDBACK_STORE]);
        }
        ////////////////////////////////////////////
     

            $feedback = new FeedBack();
            $feedback->intializeByRequest($request);
            $feedback->store();

            try
            {
                Utility::sendMail("fardan7eghlim@gmail.com","انتقادات و پیشنهادات"," یک انتقاد و پیشنهاد جدید با متن مقابل ثبت شد: ".$feedback->description);
            }
            catch(Exception $e)
            {
                $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $e->getMessage() . " Stack Trace: " . $e->getTraceAsString());
                $logEvent->store();
            }

            return json_encode([ 'error' => 0, 'tag' => RequestResponseAPI::TAG_FEEDBACK_STORE]);
        } catch (\Exception $ex) {
            $logEvent = new LogEvent(null, Route::getCurrentRoute()->getActionName(), "Main Message: " . $ex->getMessage() . " Stack Trace: " . $ex->getTraceAsString());
            $logEvent->store();

            return json_encode(['error' => RequestResponseAPI::ERROR_OPERATION_FAIL_CODE, 'tag' => RequestResponseAPI::TAG_FEEDBACK_STORE]);

        }
    }
}