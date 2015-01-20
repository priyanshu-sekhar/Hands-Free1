package com.example.android.handsfree;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * Created by Rakesh Sarangi on 20-Jan-15.
 */
public class VolumeDialogPreference extends DialogPreference{

    public VolumeDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDialogLayoutResource(R.layout.slider_layout);
    }


}
