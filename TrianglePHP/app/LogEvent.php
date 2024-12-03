<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 4/28/2018
 * Time: 7:22 PM
 */

namespace App;


use Illuminate\Database\Eloquent\Model;
use Illuminate\Notifications\Notifiable;
use Log;
use Illuminate\Database\Eloquent\SoftDeletes;
use DB;

class LogEvent extends Model
{
    use Notifiable;
    use SoftDeletes;

    protected $table = 'log_event';
    protected $primaryKey = 'log_event_id';
    protected $dates = ['deleted_at', 'updated_at', 'created_at'];


    /* The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'user_id', 'controller_and_action_name', 'error_message'
    ];

    function __construct($user_id, $controller_and_action_name, $error_message)
    {

        $this->user_id = $user_id;
        $this->controller_name_and_action_name = $controller_and_action_name;
        $this->error_message = $error_message;

    }

    public function store()
    {
        $this->log_event_guid = uniqid('', true);

        $this->save();
    }
}