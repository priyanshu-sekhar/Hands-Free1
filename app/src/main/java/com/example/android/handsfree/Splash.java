package com.example.android.handsfree;

/**
 * Created by priyanshu on 09-Jan-15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
//import android.os.SystemProperties;


public class Splash extends Activity
{   /*Changes for Headset-Plugin listener*/
    private static final String LOG_TAG = Splash.class.getSimpleName();
//    private static final boolean DBG =
//            (PhoneGlobals.DBG_LEVEL >= 1) && (SystemProperties.getInt("ro.debuggable", 0) == 1);
//    private static final boolean VDBG = (PhoneGlobals.DBG_LEVEL >= 2);

    /*Changes for Headset-Plugin listener*/
    private View mDecorView;
    public static final String MyPrefs = "MyPrefs";
    private MusicIntentReceiver myHeadsetReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myHeadsetReceiver=new MusicIntentReceiver();
        IntentFilter filter=new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myHeadsetReceiver,filter);
        mDecorView=getWindow().getDecorView();
        hideSystemUI();
        // Runs for 2 secs then goes to next activity
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally{

                    SharedPreferences sp = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);
                    if(!sp.getBoolean("First", false))
                    {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("First", true);
                        editor.commit();
                        Intent intent = new Intent(Splash.this, LaunchTutorial.class);
                        startActivity(intent);
                    }

                    else
                    {
                        Intent intent = new Intent(Splash.this, MainPage.class);
                        startActivity(intent);
                    }


                }
            }
        };
        timer.start();
    }
    private void hideSystemUI(){
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_LOW_PROFILE
                        |View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    protected void onResume(){
        IntentFilter filter=new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myHeadsetReceiver,filter);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(myHeadsetReceiver);
        super.onPause();
    }
}



