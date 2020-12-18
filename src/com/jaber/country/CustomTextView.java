package com.jaber.country;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;


public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }


    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }


    public CustomTextView(Context context) {
        super(context);
        initialize();
    }


    private void initialize() {
        if ( !isInEditMode()) {

            // txtFont = (TextView) findViewById(R.id.txtFont);
            setTypeface(G.font);
            //setTextSize(G.size);
        }
    }
}
