package com.example.android.handsfree;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by priyanshu on 13-Jan-15.
 */
public class ServiceReceiver extends BroadcastReceiver{
    DBHandler mHandler;
    AudioManager audio;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("call","calling");
        Toast.makeText(context,"call",Toast.LENGTH_SHORT).show();
        audio = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        MyPhoneStateListener phoneListener=new MyPhoneStateListener();
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
        Log.i("call",tm.getCallState()+"   "+TelephonyManager.CALL_STATE_IDLE);
        switch (tm.getCallState()) {

            case TelephonyManager.CALL_STATE_RINGING:
                audio.setRingerMode(0);
                String phoneNr= intent.getStringExtra("incoming_number");
                mHandler = new DBHandler(context);
                Cursor pointer = mHandler.getQuery(DBReader.DBEntry.BLACKLIST_TABLE, phoneNr);
                pointer.moveToFirst();
                if(pointer.getCount()==0){
                    audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                else {
                    audio.setRingerMode(0);
                }

                break;
            case TelephonyManager.CALL_STATE_IDLE:
                audio.setRingerMode(0);
                break;


        }
    }
}
