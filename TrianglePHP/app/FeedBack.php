<?php
/**
 * Created by PhpStorm.
 * User: Meysam
 * Date: 4/28/2018
 * Time: 12:14 PM
 */

namespace App;

use Carbon\Carbon;
use Illuminate\Http\Request;
use DB;
use Illuminate\Database\Eloquent\SoftDeletes;
use Illuminate\Database\Eloquent\Model;

class FeedBack extends Model
{
    use SoftDeletes;

    protected $table = 'feedback';
    protected $primaryKey = 'feedback_id';
    protected $dates = ['deleted_at'];
    public $timestamps = true;


    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = ['title','description','email','tel', 'mobile'];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    //these will not be in seassion...
    protected $hidden = [
        'created_at', 'updated_at', 'deleted_at',
    ];

    public function intialize()
    {
        $this -> feedback_id = null;
        $this -> feedback_guid = null;
        $this -> title = null;
        $this -> description = null;
        $this -> email = null;
        $this -> phone = null;

    }

    public function intializeByRequest(Request $request)
    {

        $this -> feedback_id = $request ->input('feedback_id');
        $this -> feedback_guid = $request ->input('feedback_guid');
        $this -> title = $request ->input('title');
        $this -> description = $request ->input('description');
        $this -> email = $request ->input('email');
        $this -> phone = $request ->input('phone');

    }

    public function store()
    {
        $this->feedback_guid = uniqid('',true);
        $this->save();

    }
}