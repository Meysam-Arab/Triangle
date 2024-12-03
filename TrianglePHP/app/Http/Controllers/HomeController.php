<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 5/11/2018
 * Time: 6:45 PM
 */


namespace App\Http\Controllers;

use Redirect;
use DB;
use Auth;
use Validator;
use Exception;
use Route;
use Log;


class HomeController extends Controller
{


    public  function getAPK()
    {
        try {
            $path = storage_path('app/triii.apk');

            return response()->file($path ,[
                'Content-Type'=>'application/vnd.android.package-archive',
                'Content-Disposition'=> 'attachment; filename="triii.apk"',
            ]) ;
            return response()->download(url($path));

        } catch (Exception $e) {

        }
    }
}
