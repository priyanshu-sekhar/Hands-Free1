package com.example.android.handsfree;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

/**
 * Created by priyanshu on 13-Jan-15.
 */
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
public class MusicIntentReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        Log.i("test","onReceive");
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            String TAG="Plugged_State";
            switch (state) {
                case 0:
                    Log.d(TAG, "Headset is unplugged");
                    AudioManager audio =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                    audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    break;
                case 1:
                    Log.d(TAG, "Headset is plugged");
                    AudioManager resetAudio =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                    resetAudio.setRingerMode(0);
                    break;
                default:
                    Log.d(TAG, "I have no idea what the headset state is");
            }
        }
    }
}

