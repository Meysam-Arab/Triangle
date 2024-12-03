<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<head>

    <style>

        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 5px;
            text-align: right;
        }
        table.payments {
            direction: rtl;
            background-color: #f1f1c1;
            margin-left: auto;
            margin-right: auto;
            font-size:1.2vw;
            text-align: center;
            /*width: 50%;*/
        }
        table.payments tr:nth-child(even) {
            background-color: #eee;
        }
        table.payments tr:nth-child(odd) {
            background-color:#fff;
        }
        table.payments th {
            background-color: #f1f1c1;

        }
        /* Use a media query to add a break point at 800px: */
        @media screen and (max-width:800px) {
            .left, .main, .right, table.payments {
                /*width: 100%; !* The width is 100%, when the viewport is 800px or smaller *!*/
                font-size: 2vw;

            }
        }
        /* Use a media query to add a break point at 800px: */
        @media screen and (max-width:600px) {
            .left, .main, .right, table.payments {
                width: 100%; /* The width is 100%, when the viewport is 800px or smaller */
                font-size: 3vw;

            }
        }
        /* Use a media query to add a break point at 800px: */
        @media screen and (max-width:400px) {
            .left, .main, .right, table.payments {
                width: 100%; /* The width is 100%, when the viewport is 800px or smaller */
                font-size: 4vw;

            }
        }
        /* Use a media query to add a break point at 800px: */
        @media screen and (max-width:200px) {
            .left, .main, .right, table.payments {
                width: 100%; /* The width is 100%, when the viewport is 800px or smaller */
                font-size: 8vw;

            }
        }
    </style>
</head>
<body>
<div style="text-align: center">
    {{--مثلث--}}
    <img src="{{URL::to('img/icon.png')}}" width="200" height="200" alt="مثلث">
    <br>
    <br>
    <br>
</div>




<table class="payments">
    @if(isset($message))
        @if (in_array($message->Code, \App\OperationMessage::RedMessages))

            <tr style="color: darkred;">
                <th style="text-align: center" colspan="2">{{$message->Text}}</th>

            </tr>

        @else
            <tr style="color: green">
                <th style="text-align: center" colspan="2">{{ $message->Text}}</th>
            </tr>
        @endif
    @endif
    @if(isset($payment))
    <tr>
        <th style="text-align: center" colspan="2">اطلاعات پرداخت</th>
    </tr>
    <tr>
        <td >وضعیت پرداخت : </td>
        <td>{{\App\Payment::getMessage($payment->payment_status)}}</td>
    </tr>
    <tr>
        <td>وضعیت خرید شارژ : </td>
        <td>{{\App\Payment::getMessage($payment->credit_status)}}</td>
    </tr>
    <tr>
        <td>شماره پیگیری :</td>
        <td>{{$payment->authority}} </td>
    </tr>
    <tr>
        <td>رسید دیجیتالی : </td>
        <td>{{$payment->payment_guid}}</td>
    </tr>
    <tr>
        <td>مبلغ :       </td>
        <td>{{round($payment->amount)}} تومان </td>
    </tr>
 @endif
</table>
<footer>
    <br>
    <br>
    <br>
    <div style="text-align: center;">
        <script src="https://cdn.zarinpal.com/trustlogo/v1/trustlogo.js" type="text/javascript"></script>
    </div>
</footer>
</body>