<?php

use Illuminate\Http\Request;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/


Route::post('/payments/apiList', 'API_PaymentController@apiList');
Route::post('/payments/apiIndex', 'API_PaymentController@apiIndex');


Route::post('/logs/apiStore', 'API_LogController@apiStore');

Route::post('/home/apiGetVersion', 'API_HomeController@apiGetVersion');

Route::post('/feedbacks/apiStore', 'API_FeedBackController@apiStore');
