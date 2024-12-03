package ir.fardan7eghlim.tri.Models.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by Amir on 5/23/2017.
 */

public class customTextView2 extends android.support.v7.widget.AppCompatTextView {
    public customTextView2(Context context) {
        super(context);
        init();
    }

    public customTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public customTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Far_Yekan.ttf");
            setTypeface(tf);
//            int mInputType = this.getInputType();
//            setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
//            setInputType(mInputType | InputType.TYPE_TE);
//            setInputType(mInputType | InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        }
    }
}
