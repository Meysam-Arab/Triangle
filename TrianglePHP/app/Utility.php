<?php
/**
 * Created by PhpStorm.
 * User: Amir
 * Date: 12/4/2016
 * Time: 10:13 AM
 */
namespace App;
use Log;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Mail;


class Utility extends Authenticatable
{
    //function for filtering query dynemically
    public static function fillQueryFilter($query,$params)
    {
        foreach ($params as $param) {
            switch ($param[0]) {
                case 'orderBy':
                    $query->orderBy($param[1], $param[2]);
                    break;
                case 'take':
                    $query->take($param[1]);
                    break;
                case 'skip':
                    $query->skip($param[1]);
                    break;
                case 'where':
                    $query->where($param[1], $param[2], $param[3]);
                    break;
                case 'groupBy':
                    $query->groupBy($param[1]);
                    break;
                case 'having':
                    $query->having($param[1], $param[2], $param[3]);
                    break;
                case 'orWhere':
                    $query->orWhere($param[1], $param[2]);
                    break;
                case 'whereRaw':
                    $query->whereRaw($param[1]);
                    break;
                case 'orWhereRaw':
                    $query->orWhereRaw($param[1]);
                    break;
                case 'between':
                    $query->whereBetween($param[1],[$param[2], $param[3]]);
                    break;
                case 'orbetween':
                    $query->orWhereBetween($param[1],[$param[2], $param[3]]);
                    break;
                case 'whereIn':
                    $query->whereIn($param[1], $param[2]);
                    break;
            }
        }
        return $query;
    }


    //function for filtering query dynemically
    public static function fillQueryJoin($query,$params)
    {
        foreach ($params as $param) {
            switch ($param[0]) {
                case 'join':
                    $query->join($param[1],$param[2][0],$param[2][1],$param[2][2]);
//                    $query->join($param[1], function ($query,$pt) {
//                        $query->on($pt[2][0], $pt[2][1], $pt[2][2]);
//                        foreach ($pt[3] as $item){
//                            $query->where($item[0], $item[1], $item[2]);
//                        }
//                      });
                    break;
                    case 'leftjoin':
                    $query->leftjoin($param[1],$param[2][0],$param[2][1],$param[2][2]);
                    break;
                case 'crossjoin':
                    $query->crossjoin($param[1],$param[2][0],$param[2][1],$param[2][2]);
                    break;
            }
        }
        return $query;
    }
    //function for aliasing query dynemically
    public static function fillQueryAlias($query,$params,$distinctFlag = NULL)
    {
        $temp=array();
        foreach ($params as $param) {
            switch ($param[0]) {
                case 'se':
                    array_push($temp,$param[1].'.'.$param[2]);
                    break;
                case 'st':
                    array_push($temp,$param[1].'.*');
                    break;
                case 'as':
                    array_push($temp,$param[1].'.'.$param[2].' as '.$param[3]);
                    break;
            }
        }
        IF($distinctFlag==true){
            $query-> select($temp)->distinct();
        }else
            $query-> select($temp);


        return $query;
    }
    //remove foreign guid
    public static function removeForeignGuid($list,$a,$b)
    {
        foreach ($list as $obj)
        {
            if($obj[$a]!=Auth()->user()->id)
            {
                $obj[$b]=null;
            }
        }
        return $list;
    }
    //size of file in link
    public static function curl_get_file_size($url) {
        // Assume failure.
        $result = -1;
        $curl = curl_init( $url );
        // Issue a HEAD request and follow any redirects.
        curl_setopt( $curl, CURLOPT_NOBODY, true );
        curl_setopt( $curl, CURLOPT_HEADER, true );
        curl_setopt( $curl, CURLOPT_RETURNTRANSFER, true );
        curl_setopt( $curl, CURLOPT_FOLLOWLOCATION, true );
        //curl_setopt( $curl, CURLOPT_USERAGENT, get_user_agent_string() );
        $data = curl_exec( $curl );
        curl_close( $curl );
        if( $data ) {
            $content_length = "unknown";
            $status = "unknown";

            if( preg_match( "/^HTTP\/1\.[01] (\d\d\d)/", $data, $matches ) ) {
                $status = (int)$matches[1];
            }

            if( preg_match( "/Content-Length: (\d+)/", $data, $matches ) ) {
                $content_length = (int)$matches[1];
            }
            // http://en.wikipedia.org/wiki/List_of_HTTP_status_codes
            if( $status == 200 || ($status > 300 && $status <= 308) ) {
                $result = $content_length;
            }
        }
        return $result;
    }

    public static function convert($string)
    {
        $western = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'];
        $estern = ['۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'];
        return str_replace($estern, $western, $string);
    }
    public static function cleanDateTime($dateTime)
    {
        $dateTime = str_replace("/", "", $dateTime);
        $dateTime = str_replace(" ", "", $dateTime);
        $dateTime = str_replace(":", "", $dateTime);
        $dateTime = str_replace("-", "", $dateTime);

        return $dateTime;
    }
    public static function randomPassword() {
        $alphabet = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890';
        $pass = array(); //remember to declare $pass as an array
        $alphaLength = strlen($alphabet) - 1; //put the length -1 in cache
        for ($i = 0; $i < 8; $i++) {
            $n = rand(0, $alphaLength);
            $pass[] = $alphabet[$n];
        }
        return implode($pass); //turn the array into a string
    }


    public  static  function sendMail($email, $title, $description){

        $messageTitle = " عنوان: ".$title.PHP_EOL;
        $messageText =" توضیحات: ".$description;
        Mail::raw($messageTitle, function($message) use ( $email,$messageText,$messageTitle) {
            $message->from('info@triii.ir', 'No Reply');
            $message->to($email)->subject('اطلاع رسانی برای مدیران سامانه');
            $message->setBody('<div style="font-family:IRANSans,\'B Yekan\',\'2 Yekan\',Yekan,Tahoma,\'Helvetica Neue\',Arial,sans-serif;background-color: #f3f3f3;display: block;height: 1000px;width: 966px;margin:10px auto;">
    <div style="background-color: #5cb85c;height: 30px;width: 570px;font-size: 20px;text-align:right;">
                     '.$messageTitle.'
                     <br/>
                    '.$messageText.'
                </div></div>
', 'text/html'); // for HTML rich messages
        });

    }
}
?>

