package ir.fardan7eghlim.tri.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.appolica.flubber.Flubber;

import ir.fardan7eghlim.tri.R;


/**
 * Created by Amir on 5/7/2017.
 */

public class ProgressDialog {
    private Dialog pd;
    private Context context;

    public ProgressDialog(Context context) {
        this.context = context;
        pd= new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pd.setContentView(R.layout.progress_dialog);
    }

    public void show(){
        ImageView img_conatct_d_loading= pd.findViewById(R.id.img_conatct_d_loading);
        Flubber.with()
                .animation(Flubber.AnimationPreset.ROTATION)
                .repeatCount(9999999)
                .duration(20000)
                .force(5)
                .createFor(img_conatct_d_loading)
                .start();
        if(pd!=null && !pd.isShowing()) pd.show();
    }
    public void hide(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pd!=null && pd.isShowing()) pd.dismiss();
            }
        }, 600);
    }

    public void dismiss()
    {
        if(pd.isShowing())
            pd.hide();
        pd.dismiss();
    }

    public Boolean isShowing()
    {
        if(pd != null)
            if(pd.isShowing())
                return true;
        return false;
    }
}
