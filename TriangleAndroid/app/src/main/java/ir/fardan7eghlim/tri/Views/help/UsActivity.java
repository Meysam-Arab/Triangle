package ir.fardan7eghlim.tri.Views.help;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ir.fardan7eghlim.tri.Controllers.FeedBackController;
import ir.fardan7eghlim.tri.Models.FeedBackModel;
import ir.fardan7eghlim.tri.Models.Utility.Utility;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Utils.ProgressDialog;
import ir.fardan7eghlim.tri.Utils.RequestRespond;

import static java.security.AccessController.getContext;

public class UsActivity extends AppCompatActivity implements Observer
{


    private LinearLayout llAttentra;
    private LinearLayout llLuckyLord;
    private LinearLayout llMorseCode;

    private FeedBackController fc;
    private ProgressDialog PD;

    private EditText cetTitle;
    private EditText cetDescription;
    private EditText cetPhone;
    private EditText cetEmail;

    private Button btnSendFeedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_us);

        fc = new FeedBackController(getApplicationContext());
        fc.addObserver(this);

        PD = new ProgressDialog(this);

        cetTitle = findViewById(R.id.cet_title);
        cetDescription = findViewById(R.id.cet_description);
        cetPhone = findViewById(R.id.cet_phone);
        cetEmail = findViewById(R.id.cet_email);

        btnSendFeedBack = findViewById(R.id.btn_send_feedback);
        btnSendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = cetTitle.getText().toString();
                String description = cetDescription.getText().toString();
                String phone = cetPhone.getText().toString();
                String email = cetEmail.getText().toString();

                if(description != null)
                {
                    if(!description.equals(""))
                    {
                        FeedBackModel feedback = new FeedBackModel();
                        feedback.setDescription(description);
                        if(title != null)
                            if(!title.equals(""))
                                feedback.setTitle(title);
                        if(phone != null)
                            if(!phone.equals(""))
                                feedback.setPhone(phone);
                        if(email != null)
                            if(!email.equals(""))
                                feedback.setEmail(email);
                        sendFeedBack(feedback);
                    }
                    else
                        Utility.displayToast(UsActivity.this,getString(R.string.error_invalid_description),Toast.LENGTH_SHORT);
                }
                else
                    Utility.displayToast(UsActivity.this,getString(R.string.error_invalid_description),Toast.LENGTH_SHORT);
            }
        });

        llAttentra = findViewById(R.id.ll_attentra);
        llLuckyLord = findViewById(R.id.ll_lucky_lord);
        llMorseCode = findViewById(R.id.ll_morse_code);

        llAttentra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.goToUrl(getApplicationContext(),"http://www.attentra.ir");
            }
        });
        llLuckyLord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.goToUrl(getApplicationContext(),"http://www.luckylord.ir");
            }
        });
        llMorseCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.goToUrl(getApplicationContext(),"https://cafebazaar.ir/app/com.wanderer.meysam.morsecode/?l=fa");
            }
        });

    }

    private void sendFeedBack(FeedBackModel feedback)
    {
        PD.show();
        fc.store(feedback);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        PD.hide();
        if (arg != null) {
            if (arg instanceof Boolean) {
                if (Boolean.parseBoolean(arg.toString()) == false) {
                    Utility.displayToast(UsActivity.this, getApplicationContext().getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
                }
            } else if (arg instanceof ArrayList) {

                if (((ArrayList) arg).get(0).toString().equals(RequestRespond.TAG_FEEDBACK_STORE)) {

                    clearFeedBackfields();
                    Utility.displayToast(UsActivity.this, getString(R.string.msg_OperationSuccess), Toast.LENGTH_SHORT);

                } else {
                    Utility.displayToast(UsActivity.this, getString(R.string.msg_MessageNotSpecified), Toast.LENGTH_SHORT);
                }

            } else if (arg instanceof Integer) {

                Utility.displayToast(UsActivity.this, new RequestRespond(UsActivity.this).getErrorCodeMessage(new Integer(arg.toString())), Toast.LENGTH_SHORT);

            } else {
                Utility.displayToast(UsActivity.this, getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
            }
        } else {
            Utility.displayToast(UsActivity.this, getString(R.string.error_weak_connection), Toast.LENGTH_SHORT);
        }

    }

    private void clearFeedBackfields()
    {
        cetTitle.setText(null);
        cetDescription.setText(null);
        cetEmail.setText(null);
        cetPhone.setText(null);
    }

    @Override
    protected void onDestroy() {
        fc.deleteObservers();
        if(PD != null)
        {
            PD.dismiss();
            PD = null;
        }
        super.onDestroy();
    }
}
