<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 2/25/2017
 * Time: 10:34 AM
 */

namespace App;

use Log;
use DB;

class RequestResponseAPI
{

    //tag codes

    //public
    const TAG_UNDEFINED = "undefined";

    // contact us
    const TAG_STORE_FEEDBACK = "store_feedback";

    //log
    const TAG_LOGS_STORE = "logs_store";

    //payment
    const TAG_PAYMENTS_INDEX = "payment_index";
    const TAG_PAYMENTS_LIST = "payment_list";

    //home
    const TAG_HOME_GET_VERSION="home_get_version";

    //feedback
    const TAG_FEEDBACK_STORE = "feedback_store";

    // ERROR codes
    //public
    const ERROR_UNDEFINED_CODE = -1;
    const ERROR_ITEM_EXIST_CODE = 1;
    const ERROR_INSERT_FAIL_CODE = 2;
    const ERROR_TOKEN_MISMACH_CODE = 3;
    const ERROR_DEFECTIVE_INFORMATION_CODE = 4;
    const ERROR_TOKEN_BLACKLISTED_CODE = 5;
    const ERROR_INVALID_FILE_SIZE_CODE = 6;
    const ERROR_UPDATE_FAIL_CODE = 7;
    const ERROR_DELETE_FAIL_CODE = 8;
    const ERROR_OPERATION_FAIL_CODE = 9;
    const ERROR_ITEM_NOT_EXIST_CODE = 10;
    const ERROR_UNAUTHORIZED_ACCESS_CODE = 11;
    const ERROR_WRONG_CHARSET = 12;
    const ERROR_TOKEN_INVALID = 13;
    const ERROR_CHARGE_SERVICE_DISABLED = 18;


    //user
    const ERROR_USER_EXIST_CODE = 14;
    const ERROR_EMAIL_EXIST_CODE = 15;


    //message
    const ERROR_INVALID_CODE_OF_INVITE_CODE = 16;

    /////////////////////////////////////////////////////////////
    public function initialize()
    {
        $this->Code = null;
        $this->Text = null;
    }

    public function initializeByCode($code)
    {
        $this->Code = $code;
        switch ($this->Code)
        {
            case self::ERROR_LOGIN_FAIL_CODE:
                $this->Text =  trans('messages.msg_ErrorLoginFail');
                break;
            case self::ERROR_REGISTER_FAIL_CODE:
                $this->Text =  trans('messages.msg_ErrorRegisterFail');
                break;
            case self::ERROR_USER_EXIST_CODE:
                $this->Text = trans('messages.msg_ErrorUserExist');
                break;
            case self::ERROR_TOKEN_MISMACH_CODE:
                $this->Text = trans('messages.msg_ErrorTokenMismach');
                break;
            case self::ERROR_DEFECTIVE_INFORMATION_CODE:
                $this->Text = trans('messages.msg_ErrorDefectiveInformation');
                break;
            case self::ERROR_TOKEN_BLACKLISTED_CODE:
                $this->Text = trans('messages.msg_ErrorTokenBlaklisted');
                break;
            case self::ERROR_INSERT_FAIL_CODE:
                $this->Text =  trans('messages.msg_ErrorRegisterFail');
                break;
            case self::ERROR_ITEM_EXIST_CODE:
                $this->Text = trans('messages.msg_ErrorItemExist');
                break;
            case self::ERROR_EMAIL_EXIST_CODE:
                $this->Text = trans('messages.msg_ErrorEmailExist');
                break;
            case self::ERROR_ITEM_NOT_EXIST_CODE:
                $this->Text = trans('messages.msg_ErrorItemNotExist');
                break;
            case self::ERROR_UNAUTHORIZED_ACCESS_CODE:
                $this->Text = trans('messages.msg_ErrorUnauthorizedAccess');
                break;
            case self::ERROR_EGG_ALREDY_GIVEN_CODE:
                $this->Text = trans('messages.msg_ErrorEggAlredyGiven');
                break;
            case self::ERROR_NOT_TIME_DRAW_CODE:
                $this->Text = trans('messages.msg_ErrorNotTimeDraw');
                break;
            case self::ERROR_NO_OPPONENT_AVAILABLE_CODE:
                $this->Text = trans('messages.msg_ErrorNoOpponentAvailable');
                break;
            case self::ERROR_INVALID_PUBLIC_KEY_CODE:
                $this->Text = trans('messages.msg_ErrorInvalidPublicKey');
                break;
            case self::ERROR_INVALID_CODE_OF_INVITE_CODE:
                $this->Text = trans('messages.msg_ErrorInvalidInviteCode');
                break;
            case self::ERROR_STATUS_BLOCKED_CODE:
                $this->Text = trans('messages.msg_StatusBlocked');
                break;
            case self::ERROR_STATUS_DECLINED_CODE:
                $this->Text = trans('messages.msg_StatusDeclined');
                break;
            case self::ERROR_STATUS_DELETED_CODE:
                $this->Text = trans('messages.msg_StatusDeleted');
                break;
            case self::ERROR_STATUS_ANSWERED_CODE:
                $this->Text = trans('messages.msg_ErrorGoalUserAnswered');
                break;
            case self::ERROR_OPPONENT_CANCELED_CODE:
                $this->Text = trans('messages.msg_ErrorOpponentCanceled');
                break;
            case self::ERROR_OPPONENT_ACCEPTED_CODE:
                $this->Text = trans('messages.msg_ErrorOpponentAccepted');
                break;
            case self::ERROR_TOKEN_INVALID:
                $this->Text = trans('messages.msg_ErrorTokenInvalid');
                break;
            case self::ERROR_CHARGE_SERVICE_DISABLED:
                $this->Text = trans('messages.msg_ErrorChargeServiceDisabled');
                break;
            default:
                $this->Text = trans('messages.msg_ErrorUndefined');
                break;
        }
    }
}