package com.example.android.handsfree;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by priyanshu on 13-Jan-15.
 */

public class MyPhoneStateListener extends PhoneStateListener {
    public void onCallStateChanged(int state,String incomingNumber){
        switch(state){
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i("DEBUG", "IDLE");
//                AudioManager audio = (AudioManager) .getSystemService(Context.AUDIO_SERVICE);
//                audio.setRingerMode(0);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("DEBUG", "OFFHOOK");
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("DEBUG", "RINGING");
                break;
        }
    }
}