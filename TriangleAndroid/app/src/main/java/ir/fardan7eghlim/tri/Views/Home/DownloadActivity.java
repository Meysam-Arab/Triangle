package ir.fardan7eghlim.tri.Views.Home;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ir.fardan7eghlim.tri.Models.Utility.BaseActivity;
import ir.fardan7eghlim.tri.R;
import ir.fardan7eghlim.tri.Models.SQLiteHandler.DatabaseHandler;
import ir.fardan7eghlim.tri.Models.SessionModel;

public class DownloadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        Intent intent = getIntent();

        //دانلود
        final String link =intent.getStringExtra("link");

        Button btn_cafebazaar_download = (Button) findViewById(R.id.btn_download);
        if(link.equals(""))
        {
            btn_cafebazaar_download.setVisibility(View.GONE);
            TextView ltvDescription = findViewById(R.id.ltv_description);
            ltvDescription.setText(R.string.msg_VersionExpired_Updating);
        }
        else
        {
            btn_cafebazaar_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(browserIntent);

                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
        }

        Button btn_exit = (Button) findViewById(R.id.btn_Exit_d);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);
            }
        });
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
