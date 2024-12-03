<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

Route::get('/', function () {
    return view('welcome');
});


//route::post('payments/store','PaymentController@store');
////////////////////////////////////////////////////////////////////////////////
Route::get('/payments/verification','PaymentController@verification');
Route::get('/payments/purchase/{unique_identifier}/{mobile_number}/{amount}/{service}', 'PaymentController@purchase');
Route::get('/getAPK', 'HomeController@getAPK');