package ir.fardan7eghlim.tri.Models.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class customCheckedTextView extends android.support.v7.widget.AppCompatCheckedTextView {

    public customCheckedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public customCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public customCheckedTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/DimaTraffic.ttf");
            setTypeface(tf);
        }
    }

    //meysam - added to resolve memory leak in ranking list
    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return null;
    }

}