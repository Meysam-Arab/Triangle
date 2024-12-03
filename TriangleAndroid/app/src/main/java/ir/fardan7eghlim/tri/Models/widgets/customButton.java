package ir.fardan7eghlim.tri.Models.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Amir on 5/23/2017.
 */

public class customButton extends android.support.v7.widget.AppCompatButton {
    public customButton(Context context) {
        super(context);
        init();
    }

    public customButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public customButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/DimaTraffic.ttf");
            setTypeface(tf);
        }
    }
}
