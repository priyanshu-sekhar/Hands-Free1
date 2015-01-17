package com.example.android.handsfree;

import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

import static java.lang.Thread.sleep;

/**
 * Created by priyanshu on 15-Jan-15.
 */
public class BackgroundService extends Service {

    private static final String TAG = "MyService";
    private TextToSpeech tts;
    Context context;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }



    @Override
    public void onCreate() {
        Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onCreate");

        /*Intent for HeadphoneListener*/
        IntentFilter receiverFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        HeadphoneListener receiver = new HeadphoneListener();
        registerReceiver( receiver, receiverFilter );
        /******************************/
//        instance=this;

    }




    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(getApplicationContext(),CallActionListener.isHeadsetPlugged+"",Toast.LENGTH_SHORT).show();
        String myText="";
        context = getApplicationContext();
        Log.i("onStart",getApplicationContext().toString());
        if (intent != null) {
            if (intent.getExtras() != null) {
                myText = intent.getExtras().getString("Name");
                Log.i("My", myText);
                CallActionListener.TextToSpeak=myText;
            }

        }

    }

    public BackgroundService() {
        super();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"My Service Stopped",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


}
