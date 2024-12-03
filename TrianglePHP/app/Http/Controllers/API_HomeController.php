<?php
/**
 * Created by PhpStorm.
 * User: Hooman
 * Date: 6/1/2017
 * Time: 11:41 AM
 */

namespace App\Http\Controllers;
use App\Advertisment;
use App\LogEvent;
use App\Question;
use App\UserCategory;
use App\UserQuestion;
use App\User;
use App\RequestResponseAPI;
use Illuminate\Http\Request;
use DB;
use Log;
use File;
use Redirect;
use App\UserFriend;
use Tymon\JWTAuth\Facades\JWTAuth;
use Route;
use Carbon\Carbon;

class API_HomeController extends Controller
{


    public function apiGetVersion(Request $request){
        $version = "1.00";
        $min_version = "0.01";//meysam - minimum version to force update...
//            $link = "https://cafebazaar.ir/app/ir.fardan7eghlim.luckylord/?l=fa";
        $link = "http://triii.ir/getAPK";
        //$link = "";

//        $message = "متن اضطراری";
//        $message = "به دلیل اعمال تغییرات در سرور ظرف امروز و فردا ممکن است در برنامه اختلال ایجاد شود - پیشاپیش از صبر و حوصله شما سپاسگذاریم";
        $message = "نسخه آزمایشی";

//        $message = "";


        return json_encode([
            'message'=>$message,
            'version'=>$version,
            'min_version'=>$min_version,
            'link'=>$link,
            'error' => 0,
            'tag' => RequestResponseAPI::TAG_HOME_GET_VERSION,

        ]);
    }
}